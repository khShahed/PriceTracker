/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import BLL.PriceHistoryManager;
import BLL.ProductManager;
import Models.PriceHistory;
import Models.Product;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.Locale.Category;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author SHAHED-PC
 */
public class FXMLDashboardController implements Initializable {
    @FXML
    private AnchorPane dashboard;
    @FXML
    private Menu fileMenu;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private Menu helpMenu;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private JFXButton addButton;
    @FXML
    private JFXTextField linkTextField;
    @FXML
    private Label messageLabel;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, String> nameTableColumn;
    @FXML
    private TableColumn<Product, String> urlTableColumn;
    @FXML
    private TableColumn<Product, Double> currentPriceTableColumn;
    @FXML
    private TableView<PriceHistory> priceHistroryTableView;
    @FXML
    private TableColumn<PriceHistory, String> dateTableColumn;
    @FXML
    private TableColumn<PriceHistory, Double> priceTableColumn;
    @FXML
    private LineChart<Category,Number> priceLineChart;
    private Stage stage;
    private Parent root;
    private ProductManager productManager;
    private ObservableList<Product> productObservableList;
    private ObservableList<PriceHistory> priceHistoryObservableList;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productManager = new ProductManager();
        exitMenuItem.setOnAction(e->confirmExit());

        //Filling Product Tableview
        productObservableList = FXCollections.observableArrayList();
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        urlTableColumn.setCellValueFactory(new PropertyValueFactory<>("url"));
        currentPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("currentPrice"));
        productObservableList.addAll(productManager.getProducts());
        productTableView.setItems(productObservableList);

        //Setting Product Tableview Actions
        productTableView.setOnMouseClicked(event -> onProductSelect());
        //Setting PriceHistory tableview
        priceHistoryObservableList = FXCollections.observableArrayList();
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceHistroryTableView.setItems(priceHistoryObservableList);

         new Thread()
         {
             public void Run(){
                 while (true){
                     productObservableList.clear();
                     priceHistroryTableView.getItems().clear();
                     productObservableList.addAll(productManager.getProducts());
                     priceHistroryTableView.setItems(priceHistoryObservableList);
                     try {
                         Thread.sleep(5000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }
             }
         }.start();

    }

    public void onAddButtonClick()
    {


        String url = linkTextField.getText();
        url = url.toLowerCase();
        if(url.length()==0){
            messageLabel.setTextFill(Color.rgb(255,0,0));
            messageLabel.setText("Enter an URL");
            return;
        }
        if(url.contains("daraz.com.bd")){
            messageLabel.setTextFill(Color.rgb(0,255,0));
            messageLabel.setText("Working on it.");
            Product product = getProduct(url);
            if(product==null){
                messageLabel.setTextFill(Color.rgb(255,0,0));
                messageLabel.setText("Invalid URL");
                return;
            }
            messageLabel.setTextFill(Color.rgb(0,255,0));
            messageLabel.setText("Valid URL. working on it.");
            String message = new ProductManager().insert(product);
            if(message.equals("Product already exist!")){
                messageLabel.setTextFill(Color.rgb(0,255,255));
                messageLabel.setText("Product already exist!");
            }
            else if(message.equals("Product added successfully!")){
                messageLabel.setTextFill(Color.rgb(0,255,0));
                messageLabel.setText("Product added successfully!");
            }
            else if(message.equals("Can not add product! Unknown error.")){
                messageLabel.setTextFill(Color.rgb(255,0,0));
                messageLabel.setText("Error! Can't add product");
            }

        }
        else{
            messageLabel.setTextFill(Color.rgb(255,0,0));
            messageLabel.setText("Now we only support daraz.com!");
        }
        linkTextField.setText("");
    }
    public Product getProduct(String url){
        String productName="";
        double currentPrice = 0;
        Document document = null;
        try {
            document = Jsoup.connect(url).userAgent("Chrome").timeout(5000).get();
        } catch (Exception e) {
            return null;
        }
        //Fetching name
        try {
            Element name = document.select("div.details.-validate-size > span > h1.title").first();
            productName = name.text().toString();
            //System.out.println(productName);
        }
        catch (Exception exception){
            return null;
        }
        //Fetching Price
        try {
            Elements elements = document.getElementsByAttributeValue("dir","ltr");
            //System.out.println( elements.size());
            if(elements.size()==2){
                currentPrice = Double.parseDouble(elements.get(0).attr("data-price"));
            }
            else if(elements.size()==1){
                currentPrice = Double.parseDouble(elements.get(0).attr("data-price"));
            }
        }catch (Exception exception){
            return  null;
        }
        //System.out.println(currentPrice);
        //System.out.println(url);
        return new Product(productName,url,currentPrice);
    }
    public void confirmExit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation Dialog");
        alert.setHeaderText("Exit");
        alert.setContentText("Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            System.exit(0);
        } else {

        }
    }
    public void onProductSelect(){
        if (productTableView.getSelectionModel().getSelectedItem() != null) {
            loadPriceHistoryTable();
            loadPriceHistoryGraph();
        }
    }
    public void loadPriceHistoryTable(){
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        priceHistoryObservableList.clear();
        priceHistoryObservableList.addAll(new PriceHistoryManager().getPriceHistoriesByProuctId(selectedProduct.getId()));
    }
    public void loadPriceHistoryGraph(){
        priceLineChart.getData().clear();
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Price");

        XYChart.Series series = new XYChart.Series();
        series.setName("Date vs Price");
        for(PriceHistory priceHistory:priceHistoryObservableList){
            series.getData().add(new XYChart.Data(priceHistory.getDate(),priceHistory.getPrice()));
        }
        priceLineChart.getData().add(series);


    }
}
