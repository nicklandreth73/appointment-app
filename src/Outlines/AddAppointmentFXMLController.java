/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outlines;

import Exceptions.MissingInputException;
import Exceptions.OutOfHoursException;
import Exceptions.StartEndException;
import Exceptions.TimeConflictException;
import com.mysql.jdbc.Connection;
import static java.lang.Math.abs;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Nick
 */
public class AddAppointmentFXMLController implements Initializable {
       
    ObservableList<String> customers = FXCollections.observableArrayList();
    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();
    Connection conn = DataBase.DBConnection.getConn();
    String customerID = "0";
    String addressID = "0";
    String startDateTimeString = "";
    String endDateTimeString = "";
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    boolean timeConflict = false;
    LocalTime startTime;
    LocalTime endTime;
    
    
    @FXML
    private TextField descriptionField;

    @FXML
    private TextField locationField;
    
    @FXML
    private TextField urlField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField typeField;
    
    @FXML
    private DatePicker datePicker;
 
    @FXML
    private Button addButton;
    
    @FXML
    private ComboBox<String> comboHourStart;
    
    @FXML
    private ComboBox<String> comboMinuteStart;
    
    @FXML
    private ComboBox<String> comboHourEnd;
    
    @FXML
    private ComboBox<String> comboMinuteEnd;
        
    @FXML
    private ComboBox<String> comboCustomer;

    @FXML
    void handleAddButton(ActionEvent event) {
            
        try {
    //Checks to make sure all required fields are filled before continuing        
            if(!comboCustomer.getSelectionModel().isEmpty() && !comboHourStart.getSelectionModel().isEmpty() && !comboMinuteStart.getSelectionModel().isEmpty() &&
                    !comboHourEnd.getSelectionModel().isEmpty() && !comboMinuteEnd.getSelectionModel().isEmpty() && datePicker.getValue() != null && !titleField.getText().trim().isEmpty()){
    //Sets date and time to gmt strings usable by the database
                setDateTime();
    //Sets customer ID to that of the customer selected    
             ResultSet rsCustomer = 
            conn.createStatement().executeQuery("select * from customer where customerName = " + DataBase.Utilities.addQuotes(comboCustomer.getValue()));
            if(rsCustomer.next()){
                customerID = rsCustomer.getString("customerId");
            }
    //Prevents appointment addition and shows alert if there is a window within 15 minutes of the appointment or appointment is out of hours
            ResultSet rsTimes = conn.createStatement().executeQuery("select start,end from appointment");
            while(rsTimes.next()){
                LocalDateTime start = rsTimes.getTimestamp("start").toLocalDateTime();
                LocalDateTime end = rsTimes.getTimestamp("end").toLocalDateTime();
                long startStartDiff = ChronoUnit.MINUTES.between(start, startDateTime);
                long endStartDiff = ChronoUnit.MINUTES.between(end, startDateTime);
                long startEndDiff = ChronoUnit.MINUTES.between(start, endDateTime);
                long floatDiff = ChronoUnit.MINUTES.between(startDateTime, endDateTime);
                System.out.println( start.compareTo(startDateTime) + " " +  end.compareTo(startDateTime) );
                if(floatDiff <=0){
                    throw new StartEndException();
                }
                if(abs(startStartDiff) <= 15 || abs(endStartDiff) <= 15 || abs(startEndDiff) <= 15 || (startDateTime.compareTo(start) >= 0 && startDateTime.compareTo(end) <= 0)){
                    throw new TimeConflictException(start.toLocalDate(), start.toLocalTime());
                }
            }
    //Checks Business hours           
                LocalTime businessOpenHour = LocalTime.of(8, 00);
                LocalTime businessCloseHour = LocalTime.of(20, 00);
                System.out.println( " business hours" + businessOpenHour + " " + businessCloseHour + " " + startTime + " " + endTime);
                if(startTime.compareTo(businessOpenHour) <=0 || endTime.compareTo(businessCloseHour) >=0){
                    throw new OutOfHoursException();
                }
    // Insertion SQL statement for appointment sql field
            conn.createStatement().execute(
            "Insert into appointment (customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdateBy) "
            + "values (" + customerID + ", " + Main.User.getUserID() + ","  + addQuotes(titleField.getText()) + "," + 
            addQuotes(descriptionField.getText()) + "," + addQuotes(locationField.getText()) + "," + addQuotes(comboCustomer.getValue()) + ","
            + addQuotes(typeField.getText()) + "," + addQuotes(urlField.getText()) + "," + addQuotes(startDateTimeString) + "," + addQuotes(endDateTimeString) +     
            "," + addQuotes(Main.ManageTime.sqlTime()) + "," + addQuotes(Main.User.getUserName()) +  "," + addQuotes(Main.User.getUserName()) +  ")");
            Main.ManageStage.createStage("View Appointments", "ViewAppointmentsFXML.fxml", true, event);
            }else{
                throw new MissingInputException();
            }
            } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }catch(MissingInputException e){
         ErrorFXMLController.setErrorMessage(e.getMessage());
         Main.ManageStage.createStage("Input Error", "ErrorFXML.fxml");
        }catch(TimeConflictException e1){
         ErrorFXMLController.setErrorMessage(e1.getMessage());
         Main.ManageStage.createStage("Time Conflict Error", "ErrorFXML.fxml");
        }catch(OutOfHoursException e2){
         ErrorFXMLController.setErrorMessage(e2.getMessage());
         Main.ManageStage.createStage("OutOfHoursError", "ErrorFXML.fxml");
        }catch(StartEndException e3){
         ErrorFXMLController.setErrorMessage(e3.getMessage());
         Main.ManageStage.createStage("Start End Error", "ErrorFXML.fxml");
        }
    }
    
    @FXML
    void handleExitButton(ActionEvent event) {
        Main.ManageStage.createStage("View Appointments", "ViewAppointmentsFXML.fxml", true, event);
    }
    private void setDateTime(){
                //takes variables from datePicker and combo boxes for use in creating date string
        int year = Integer.parseInt(datePicker.getValue().format(DateTimeFormatter.ofPattern("YYYY")));
        int month = Integer.parseInt(datePicker.getValue().format(DateTimeFormatter.ofPattern("MM")));
        int day = Integer.parseInt(datePicker.getValue().format(DateTimeFormatter.ofPattern("dd")));
        int startHour = Integer.parseInt(comboHourStart.getValue());
        int startMinute = Integer.parseInt(comboMinuteStart.getValue());
        int endHour = Integer.parseInt(comboHourEnd.getValue());
        int endMinute = Integer.parseInt(comboMinuteEnd.getValue());
        //creates and formats apt date string for database
       startDateTimeString = Main.ManageTime.userInputToGMT(year, month, day, startHour, startMinute);
       endDateTimeString = Main.ManageTime.userInputToGMT(year, month, day, endHour, endMinute);
       
       startDateTime = Main.ManageTime.userInputToGMTObject(year, month, day, startHour, startMinute);
       endDateTime = Main.ManageTime.userInputToGMTObject(year, month, day, endHour, endMinute);
       
       startTime = LocalTime.of(startHour, startMinute);
       endTime = LocalTime.of(endHour, endMinute);
       
        
    }
    private String addQuotes(String input){
        return ("'" + input + "'");
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Sets hours and minutes combo boxes with the following options
       hours.addAll("01", "02", "03","04","05","06","07","08","09","10","11",
                "12","13","14","15","16","17","18","19","20","21","22","23","24");
       minutes.addAll("00","15","30","45");
       comboHourStart.setItems(hours);
       comboMinuteStart.setItems(minutes);
       comboHourEnd.setItems(hours);
       comboMinuteEnd.setItems(minutes);
        try {
            // Sets customer combo box with existing customer options
           ResultSet rsCustomers = conn.createStatement().executeQuery("Select customerName from customer");
           while(rsCustomers.next()){
               customers.add(rsCustomers.getString("customerName"));
           }
        } catch (SQLException ex) {
           System.out.println(ex.getMessage());
        }
        comboCustomer.setItems(customers);
    }    
    
}
