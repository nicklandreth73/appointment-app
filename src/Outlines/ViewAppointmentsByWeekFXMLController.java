/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outlines;

import DataBase.DBConnection;
import Main.Appointments;
import Main.Customers;
import com.mysql.jdbc.Connection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nick
 */
public class ViewAppointmentsByWeekFXMLController implements Initializable {

    String customerID = "";
    String addressID = "";
    int year = 0;
    int month = 0;
    int day = 0;
    LocalDateTime date;
    
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
    private DatePicker datePicker;



    @FXML
    void viewButtonClicked(ActionEvent event) {
    appointmentTable.getItems().clear();
    year = Integer.parseInt(datePicker.getValue().format(DateTimeFormatter.ofPattern("YYYY")));
    month = Integer.parseInt(datePicker.getValue().format(DateTimeFormatter.ofPattern("MM")));
    day = Integer.parseInt(datePicker.getValue().format(DateTimeFormatter.ofPattern("dd")));
    date = Main.ManageTime.userInputToGMTObject(year, month, day, 12, 00);
                    try{
            Connection con = DBConnection.getConn();
            ResultSet rsAppointment = con.createStatement().executeQuery("select * from appointment");
            while(rsAppointment.next()){    
            customerID = rsAppointment.getString("customerId");
            ResultSet rsCustomer = con.createStatement().executeQuery("select * from customer where customerId = " + customerID);
                while(rsCustomer.next()){
                addressID = rsCustomer.getString("addressId");
                ResultSet rsAddress = con.createStatement().executeQuery("select * from address where addressId = " + addressID);
                while(rsAddress.next()){
                long dateDiff = ChronoUnit.DAYS.between(date, rsAppointment.getTimestamp("end").toLocalDateTime());
                System.out.println(dateDiff);
                if(dateDiff >= 0 && dateDiff <= 7){
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
    
    ObservableList<Main.Appointments> aptList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  

    customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
    contactColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
    titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
    endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
    
    appointmentTable.setItems(aptList);
                
        
                
                
                
        
    }    
    
}
