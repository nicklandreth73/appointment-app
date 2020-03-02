/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outlines;

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
public class EditCustomerFXMLController implements Initializable {


    Connection conn = DataBase.DBConnection.getConn();
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addressField;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField oldNameField;
    
    @FXML
    private TextField newNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button submitButton;

    @FXML
    private TextField zipField;


    @FXML
    void cancelButtonClicked(ActionEvent event) {
        Main.ManageStage.createStage("View Customers", "ViewCustomersFXML.fxml", true, event);
    }

    @FXML
    void submitButtonClicked(ActionEvent event) {
        
        if(DataBase.Utilities.InDB("customer", "customerName", oldNameField.getText())){
        if(Main.ConfirmBox.display("Confirm Update", "Are you sure you want to update the records for " + oldNameField.getText())){
            try {
                ResultSet rsCustomer = conn.createStatement().executeQuery("Select * from customer where customerName = " + DataBase.Utilities.addQuotes(oldNameField.getText()));
                String addressID = "0";
                String customerID = "0";
                if(rsCustomer.next()){
                    addressID = rsCustomer.getString("addressId");
                    customerID = rsCustomer.getString("customerId");
                    if(!(newNameField.getText().trim().isEmpty())){
                    conn.createStatement().execute("update customer set customerName =" + DataBase.Utilities.addQuotes(newNameField.getText())
                            + "where customerId = " + customerID);
                    }
                }
                System.out.println(addressID + " : " + customerID);
                ResultSet rsAddress = conn.createStatement().executeQuery("Select * from address where addressId =" + addressID);
                if(rsAddress.next()){
                    if(!(addressField.getText().trim().isEmpty())){
              conn.createStatement().execute("update address set address =" + DataBase.Utilities.addQuotes(addressField.getText()) +"where addressId = " + addressID);
                    }
                if(!(zipField.getText().trim().isEmpty())){
              conn.createStatement().execute("update address set postalCode =" + DataBase.Utilities.addQuotes(zipField.getText()) +"where addressId = " + addressID);
                    }
                if(!(phoneField.getText().trim().isEmpty())){
              conn.createStatement().execute("update address set phone =" + DataBase.Utilities.addQuotes(phoneField.getText()) +"where addressId = " + addressID);
                    }
               }
               Main.ManageStage.createStage("View Customers", "ViewCustomersFXML.fxml", true, event);
            } catch (SQLException ex) {
               System.out.println(ex.getMessage());
            }
        }
      }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
