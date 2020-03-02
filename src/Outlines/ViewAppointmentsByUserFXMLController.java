/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outlines;

import DataBase.DBConnection;
import Main.Appointments;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Nick
 */
public class ViewAppointmentsByUserFXMLController implements Initializable {
    ObservableList<String> users = FXCollections.observableArrayList();
    ObservableList<Appointments> aptList = FXCollections.observableArrayList();
    String customerID = "";
    String addressID = "";
    Connection con = DBConnection.getConn();
    String userName ="";
    String userID = "";
    
    @FXML
    private ComboBox<String> comboUser;
    
    @FXML
    private TableView<Main.Appointments> appointmentTable;

    @FXML
    private TableColumn<Main.Appointments, String> contactColumn;

    @FXML
    private TableColumn<Main.Appointments, String> customerColumn;

    @FXML
    private TableColumn<Main.Appointments, String> descriptionColumn;
    
    @FXML
    private TableColumn<Main.Appointments, String> startColumn;

    @FXML
    private TableColumn<Main.Appointments, String> titleColumn;
    
    @FXML
    private TableColumn<Main.Appointments, String> endColumn;




    @FXML
    void selectButtonClicked(ActionEvent event) {
          userName = comboUser.getValue();
          appointmentTable.getItems().clear();
                try{
            
            ResultSet rsUser = con.createStatement().executeQuery("select * from user where userName = " + DataBase.Utilities.addQuotes(userName));
            if(rsUser.next()){
                userID = rsUser.getString("userId");
            }
            ResultSet rsAppointment = con.createStatement().executeQuery("select * from appointment");
            while(rsAppointment.next()){
            if(userID.equals(rsAppointment.getString("userId"))){
            customerID = rsAppointment.getString("customerId");
            ResultSet rsCustomer = con.createStatement().executeQuery("select * from customer where customerId = " + customerID);
                while(rsCustomer.next()){
                addressID = rsCustomer.getString("addressId");
                ResultSet rsAddress = con.createStatement().executeQuery("select * from address where addressId = " + addressID);
                while(rsAddress.next()){
                aptList.add(new Appointments(
                        rsCustomer.getString("customerName"), 
                        rsAddress.getString("phone"),
                        rsAppointment.getString("title"),
                        rsAppointment.getString("description"),
                        Main.ManageTime.sqlToLocalTime(rsAppointment.getTimestamp("start")),
                        Main.ManageTime.sqlToLocalTime(rsAppointment.getTimestamp("end"))
                ));
                }
                }
            }
            }
        }catch(SQLException e){
            System.out.println("SQL Exception caugth: " + e.getMessage());
        }
    appointmentTable.setItems(aptList);
    }

    @FXML
    void exitButtonClicked(ActionEvent event) {
        Main.ManageStage.exitStage(event);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Sets user combo box with existing user options
           ResultSet rsUsers = con.createStatement().executeQuery("Select userName from user");
           while(rsUsers.next()){
               users.add(rsUsers.getString("userName"));
           }
        } catch (SQLException ex) {
           System.out.println(ex.getMessage());
        }
        comboUser.setItems(users);
    
    customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
    contactColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
    titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
    endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
    
    appointmentTable.setItems(aptList);
                
        
                
                
                
        
    }    
    
}
