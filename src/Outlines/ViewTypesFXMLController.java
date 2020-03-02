/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outlines;

import DataBase.DBConnection;
import Main.AppointmentWithType;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Nick
 */
public class ViewTypesFXMLController implements Initializable {

//    ObservableList<Main.AppointmentWithType> aptList = FXCollections.observableArrayList();
//    String customerID = "";
//    String addressID = "";
    Connection conn = DBConnection.getConn();
    int year = 0;
    int month = 0;
    int day = 0;
    LocalDateTime date;
    String result = "";
    String dateString;
    String datePlusString;

    @FXML
    private DatePicker datePicker;
    
    @FXML
    private Label numbersLabel;

   @FXML
    void viewButtonClicked(ActionEvent event) {
//    appointmentTable.getItems().clear();
    year = Integer.parseInt(datePicker.getValue().format(DateTimeFormatter.ofPattern("YYYY")));
    month = Integer.parseInt(datePicker.getValue().format(DateTimeFormatter.ofPattern("MM")));
    day = Integer.parseInt(datePicker.getValue().format(DateTimeFormatter.ofPattern("dd")));
    date = LocalDateTime.of(year, month, day, 12, 00,00);
    LocalDateTime datePlus = ChronoUnit.DAYS.addTo(date, 31);
    dateString = date.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"));
    datePlusString = datePlus.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"));
    ResultSet rsAll;
    System.out.println(dateString + " : " + datePlusString);
        try {
            rsAll = conn.createStatement().executeQuery(
        "select count(distinct type) from appointment where end > " + "'" + dateString +"'" +
             " and start < " + "'" + datePlusString + "'" );
                while(rsAll.next()){
           result = rsAll.getString(1);
    }
    }catch (SQLException ex) {
            Logger.getLogger(ViewTypesFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        numbersLabel.setText("There are " + result + " unique appointment types in the given month");
    }
    @FXML
    void exitButtonClicked(ActionEvent event) {
        Main.ManageStage.exitStage(event);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
    }    
    
}
