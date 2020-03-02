/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outlines;

import DataBase.DBConnection;
import com.mysql.jdbc.Connection;
import java.net.URL;
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
public class DeleteCustomerFXMLController implements Initializable {

    Connection conn = DBConnection.getConn();
    
    
    @FXML
    private URL location;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField customerNameField;

    @FXML
    private Button deleteButton;


    @FXML
    void cancelButtonClicked(ActionEvent event) {
        Main.ManageStage.createStage("View Customers", "ViewCustomersFXML.fxml", true, event);
    }

    @FXML
    void deleteButtonClicked(ActionEvent event) {
        
        if(DataBase.Utilities.InDB("customer", "customerName", customerNameField.getText())){
            if(Main.ConfirmBox.display("Confirm Delete", "Are you sure you want to delete this customer?")){
                try {
                conn.createStatement().execute("Delete from customer where customerName = " + DataBase.Utilities.addQuotes(customerNameField.getText()));
                Main.ManageStage.createStage("View Customers", "ViewCustomersFXML.fxml", true, event);
                } catch (SQLException ex) {
                ErrorFXMLController.setErrorMessage(ex.getMessage());
                Main.ManageStage.createStage("SQL Error", "ErrorFXML.fxml");
                }
            }else{
                System.out.println("Delete aborted");
            }
    }else {
    ErrorFXMLController.setErrorMessage("Customer does not exist");
    Main.ManageStage.createStage("Not in DB", "ErrorFXML.fxml");
}
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
