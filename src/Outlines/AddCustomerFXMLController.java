/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outlines;

import DataBase.DBConnection;
import Exceptions.MissingInputException;
import com.mysql.jdbc.Connection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


/**
 * FXML Controller class
 *
 * @author Nick
 */
public class AddCustomerFXMLController implements Initializable {

 @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private TextField addressField;

    @FXML
    private TextField cityField;

    @FXML
    private Button exitButton;
    
    @FXML
    private TextField countryField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField zipField;

    Connection conn = DBConnection.getConn();

    String countryID = "";
    String cityID = "";
    String addressID = "";
    String country = "";
    

    @FXML
    void addButtonClicked(ActionEvent event) throws MissingInputException {
        
        
        
        try{
        if(!nameField.getText().trim().isEmpty() && !phoneField.getText().trim().isEmpty() && !addressField.getText().trim().isEmpty()){ 
            String countrySQL = "Insert into country (country, createDate, createdBy, lastUpdateBy) "
         + "values (" + addQuotes(countryField.getText())  +  "," + addQuotes(Main.ManageTime.sqlTime()) + "," + addQuotes(Main.User.getUserName()) +  "," + addQuotes(Main.User.getUserName()) +  ")";
          
        //Check for country in db if none exists create country and pass id to countryID var either way
        if(!DataBase.Utilities.InDB("country", "country", countryField.getText(), true)){
        conn.createStatement().execute(countrySQL);
        }
        ResultSet rsCountry = conn.createStatement().executeQuery("Select countryId From country where country = " + addQuotes(countryField.getText()));
        if(rsCountry.next()){
        countryID = rsCountry.getString(1);
        }
        //Check for city in db if none exists create city and pass id to cityID var either way
        String citySQL = "Insert into city (city, countryId, createDate, createdBy, lastUpdateBy) "
            + "values (" + addQuotes(cityField.getText()) + "," + countryID +  "," + addQuotes(Main.ManageTime.sqlTime()) + "," + addQuotes(Main.User.getUserName()) +  "," + addQuotes(Main.User.getUserName()) +  ")";
        if(!DataBase.Utilities.InDB("city", "city", cityField.getText(), true)){
        conn.createStatement().execute(citySQL);
        };
        ResultSet rsCity = conn.createStatement().executeQuery("Select cityId From city where city = " + addQuotes(cityField.getText()));
        if(rsCity.next()){
        cityID = rsCity.getString(1);
        }
        //Check for address in db if none exists create address and pass id to addressID var either way
        String addressSQL = "Insert into address (address, address2, postalCode, phone, cityId, createDate, createdBy, lastUpdateBy) "
        + "values (" + addQuotes(addressField.getText())  +  "," + addQuotes(addressField.getText())  +  "," + addQuotes(zipField.getText()) + "," + 
        addQuotes(phoneField.getText()) + "," + cityID +  "," + addQuotes(Main.ManageTime.sqlTime()) + "," + addQuotes(Main.User.getUserName()) +  "," + 
        addQuotes(Main.User.getUserName()) +  ")";
        conn.createStatement().execute(addressSQL);
        ResultSet rsAddress = conn.createStatement().executeQuery("Select addressId From address where address = " + addQuotes(addressField.getText()));
        if(rsAddress.next()){
        addressID = rsAddress.getString(1);
        }
        
        String customerSQL = "Insert into customer (customerName, addressId, active, createDate, createdBy, lastUpdateBy) "
        + "values (" + addQuotes(nameField.getText())  +  "," + addressID + "," +  "1" +"," + addQuotes(Main.ManageTime.sqlTime()) +
                "," + addQuotes(Main.User.getUserName()) +  "," + addQuotes(Main.User.getUserName()) +  ")";
        if(!DataBase.Utilities.InDB("customer", "customerName", nameField.getText(), true)){
        conn.createStatement().execute(customerSQL);
        Main.ManageStage.createStage("View Customers", "ViewCustomersFXML.fxml", true, event);
        System.out.println(addressID);
        }else{
        ErrorFXMLController.setErrorMessage("Customer Name Already Exists");
        Main.ManageStage.createStage("Name Exists", "ErrorFXML.fxml");
        }
       }else{
            throw new MissingInputException();
      }
      }catch(SQLException e){
          ErrorFXMLController.setErrorMessage("Error : " + e.getMessage());
          Main.ManageStage.createStage("SQL Error", "ErrorFXML.fxml");
       }catch(MissingInputException e1){
          ErrorFXMLController.setErrorMessage(e1.getMessage());
          Main.ManageStage.createStage("Input Error", "ErrorFXML.fxml");
      }
    }

    @FXML
    void exitButtonClicked(ActionEvent event) {
        Main.ManageStage.createStage("View Customers", "ViewCustomersFXML.fxml", true, event);
    }
    private String addQuotes(String input){
        return ("'" + input + "'");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
