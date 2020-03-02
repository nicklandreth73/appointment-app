/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outlines;

import DataBase.DBConnection;
import Main.Customers;
import com.mysql.jdbc.Connection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Nick
 */
public class ViewCustomersFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */   
    @FXML
    private TableView<Main.Customers> CustomerTable;
    
        @FXML
    private TableColumn<Main.Customers, String> nameColumn;

    @FXML
    private TableColumn<Main.Customers, String> phoneColumn;
    
        @FXML
    private TableColumn<Main.Customers, String> addressColumn;

    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;



    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button exitButton;


    @FXML
    void addButtonClicked(ActionEvent event) {
        Main.ManageStage.createStage("AddCustomer", "AddCustomerFXML.fxml", true, event);
    }

    @FXML
    void deleteButtonClicked(ActionEvent event) {
                Main.ManageStage.createStage("Delete Customer", "DeleteCustomerFXML.fxml", true, event);
    }

    @FXML
    void editButtonClicked(ActionEvent event) {
        Main.ManageStage.createStage("Edit Customer", "EditCustomerFXML.fxml", true, event);
    }

    @FXML
    void exitButtonClicked(ActionEvent event) {
//                Node source = (Node) event.getSource();
//                Stage stage = (Stage) source.getScene().getWindow();
//                stage.close();
            Main.ManageStage.exitStage(event);
    }
    
    ObservableList<Main.Customers> custList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
            Connection con = DBConnection.getConn();
            
            ResultSet rsCustomer = con.createStatement().executeQuery("select * from customer");
            while(rsCustomer.next()){
                String addressID = rsCustomer.getString("addressId");
                ResultSet rsAddress = con.createStatement().executeQuery("select * from address where addressId = " + addressID);
                while(rsAddress.next()){
                custList.add(new Customers(rsCustomer.getString("customerName"),rsAddress.getString("address"), rsAddress.getString("phone")));
            }
            }
        }catch(SQLException e){
            System.out.println("SQL Exception caugth: " + e.getMessage());
        } 

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        
        CustomerTable.setItems(custList);
    }    
    
}
