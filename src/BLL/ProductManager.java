package BLL;

import DAL.LogGateway;
import DAL.ProductGateway;
import Models.Product;

import java.util.ArrayList;

/**
 * Created by SHAHED-PC on 7/21/2017.
 */
public class ProductManager {
    ProductGateway productGateway;
    LogGateway logGateway;
    public ArrayList<Product> getProducts(){
        return new ProductGateway().getProducts();
    }
    public  String insert(Product product){
        logGateway = new LogGateway();
         String message = "";
         if(productGateway==null) productGateway = new ProductGateway();

         if(productGateway.isAlreadyExist(product)){
             message = "Product already exist!";
             logGateway.insert("Trying to re add"+product.getName()+" to database.");
         }
         else {
             if (productGateway.insert(product)){

                 logGateway.insert(product.getName()+" added to database.");
                 message = "Product added successfully!";
             }
             else {

                 logGateway.insert("Error! Can't add"+product.getName()+" to database.");
                 message = "Can not add product! Unknown error.";
             }
         }
         return message;
    }
    public boolean update(Product product){
        return  new ProductGateway().update(product);
    }
}
