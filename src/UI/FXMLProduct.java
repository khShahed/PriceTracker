package UI;

import Models.Product;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by SHAHED-PC on 7/27/2017.
 */
public class FXMLProduct implements Initializable{
    @FXML
    private JFXTextField productNameTextBox;

    @FXML
    private JFXTextField productUrlTextBox;

    @FXML
    private JFXTextField currentPriceTextBox;

    @FXML
    private JFXButton addButton;
    private Product product;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentPriceTextBox.setDisable(true);
        productNameTextBox.setDisable(true);
        productUrlTextBox.setDisable(true);
        productNameTextBox.setText(product.getName());
        productUrlTextBox.setText(product.getUrl());
        currentPriceTextBox.setText(product.getCurrentPrice()+"");
    }
    public void addProductDetails(Product product){
        this.product = product;

    }
    public void addButtonClick(){

    }
}
