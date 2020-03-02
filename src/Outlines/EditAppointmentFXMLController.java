/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outlines;

import Exceptions.DateSelectionException;
import Exceptions.MissingInputException;
import Exceptions.NotInDBException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Nick
 */
public class EditAppointmentFXMLController implements Initializable {
       
    ObservableList<String> customers = FXCollections.observableArrayList();
    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();
    Connection conn = DataBase.DBConnection.getConn();
    String customerID = "0";
    String addressID = "0";
    String appointmentID ="0";
    String startDateTimeString = "";
    String endDateTimeString = "";
    String newCustomerID = "0";
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    LocalTime startTime;
    LocalTime endTime;
            
    
    @FXML
    private TextField descriptionField;

    @FXML
    private TextField locationField;
    
    @FXML
    private TextField urlField;

    @FXML
    private TextField oldTitleField;
    
    @FXML
    private TextField titleField;

    @FXML
    private TextField typeField;
    
    @FXML
    private DatePicker datePicker;
    
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
    void addButtonClicked(ActionEvent event) {
            
        try {
            if(!oldTitleField.getText().trim().isEmpty()){
            if(DataBase.Utilities.InDB("appointment", "title", oldTitleField.getText(), true)){
            if(Main.ConfirmBox.display("Confirm Update", "Are you sure you want to update the records for " + oldTitleField.getText())){
            
//Getting IDs needed for update statements
            ResultSet rsCustomer;
            ResultSet rsAppointment = 
            conn.createStatement().executeQuery("select * from appointment where title = " + addQuotes(oldTitleField.getText()));
            if(rsAppointment.next()){
                customerID = rsAppointment.getString("customerId");
                appointmentID = rsAppointment.getString("appointmentId");
            }
            
//Updating each appointment field where IDs match title from oldtitlefield if not empty
            if(!titleField.getText().trim().isEmpty())
            conn.createStatement().execute("update appointment set title =" + addQuotes(titleField.getText()) +  "where appointmentId =" + appointmentID );
            if(!descriptionField.getText().trim().isEmpty())
            conn.createStatement().execute("update appointment set description =" + addQuotes(descriptionField.getText()) +  "where appointmentId =" + appointmentID );
            if(!locationField.getText().trim().isEmpty())
            conn.createStatement().execute("update appointment set location =" + addQuotes(locationField.getText()) +  "where appointmentId =" + appointmentID );
            if(!urlField.getText().trim().isEmpty())
            conn.createStatement().execute("update appointment set url =" + addQuotes(urlField.getText()) +  "where appointmentId =" + appointmentID );
            if(!typeField.getText().trim().isEmpty())
            conn.createStatement().execute("update appointment set type =" + addQuotes(typeField.getText()) +  "where appointmentId =" + appointmentID );
            
//Checks if any fields are updated and returns an error if some but not all fields are updated
            if(datePicker.getValue() != null || !comboHourStart.getSelectionModel().isEmpty() || !comboMinuteStart.getSelectionModel().isEmpty() 
            || !comboHourEnd.getSelectionModel().isEmpty() || !comboMinuteEnd.getSelectionModel().isEmpty()){
            if(datePicker.getValue() != null && !comboHourStart.getSelectionModel().isEmpty() && !comboMinuteStart.getSelectionModel().isEmpty() 
            && !comboHourEnd.getSelectionModel().isEmpty() && !comboMinuteEnd.getSelectionModel().isEmpty()){
                setDateTime();
//Prevents appointment addition and shows alert if there is a window within 15 minutes of the appointment or appointment is outside business hours           
            ResultSet rsTimes = conn.createStatement().executeQuery("select start,end from appointment");
            while(rsTimes.next()){
                LocalDateTime start = rsTimes.getTimestamp("start").toLocalDateTime();
                LocalDateTime end = rsTimes.getTimestamp("end").toLocalDateTime();
                long startStartDiff = ChronoUnit.MINUTES.between(start, startDateTime);
                long endStartDiff = ChronoUnit.MINUTES.between(end, startDateTime);
                long startEndDiff = ChronoUnit.MINUTES.between(start, endDateTime);
                long floatDiff = ChronoUnit.MINUTES.between(startDateTime, endDateTime);
                if(floatDiff <=0){
                    throw new StartEndException();
                }
                if(abs(startStartDiff) < 16 || abs(endStartDiff) < 16 || abs(startEndDiff) < 16){
                    throw new TimeConflictException(start.toLocalDate(), start.toLocalTime());
                }
            }
//Checks Business hours           
                LocalTime businessOpenHour = LocalTime.of(8, 00);
                LocalTime businessCloseHour = LocalTime.of(20, 00);
                System.out.println(businessOpenHour + " " + businessCloseHour + " " + startTime + " " + endTime);
                if(startTime.compareTo(businessOpenHour) <=0 || endTime.compareTo(businessCloseHour) >=0){
                    throw new OutOfHoursException();
                }
//Changes appointment times            
                conn.createStatement().execute("update appointment set start = " + addQuotes(startDateTimeString) + "," + " end = " + addQuotes(endDateTimeString)
                + " where appointmentId = " + appointmentID);
            }else{
                    throw new DateSelectionException();
            }
            }
            if(!comboCustomer.getSelectionModel().isEmpty()){
                rsCustomer = conn.createStatement().executeQuery("select * from customer where customerName =" + addQuotes(comboCustomer.getValue()));
                if(rsCustomer.next()){
                    newCustomerID = rsCustomer.getString("customerId");
                }
                System.out.println("update appointment set customerId = " + newCustomerID + " where appointmentId = " + appointmentID + ";");
                conn.createStatement().execute("update appointment set customerId = " + newCustomerID + " where appointmentId =" + appointmentID);
            }
            }
            }else{
               throw new NotInDBException(oldTitleField.getText());
            }
            }else{
                throw new MissingInputException();
            }
            Main.ManageStage.createStage("View Appointments", "ViewAppointmentsFXML.fxml", true, event);
            } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }catch(NotInDBException e){
         ErrorFXMLController.setErrorMessage(e.getMessage());
         Main.ManageStage.createStage("Input Error", "ErrorFXML.fxml");
        }catch(DateSelectionException e2){
         ErrorFXMLController.setErrorMessage(e2.getMessage());
         Main.ManageStage.createStage("Input Error", "ErrorFXML.fxml");
        } catch (MissingInputException e3) {
         ErrorFXMLController.setErrorMessage(e3.getMessage());
         Main.ManageStage.createStage("Input Error", "ErrorFXML.fxml");
        }catch(TimeConflictException e4){
         ErrorFXMLController.setErrorMessage(e4.getMessage());
         Main.ManageStage.createStage("Time Conflict Error", "ErrorFXML.fxml");
        }catch(OutOfHoursException e5){
         ErrorFXMLController.setErrorMessage(e5.getMessage());
         Main.ManageStage.createStage("OutOfHoursError", "ErrorFXML.fxml");
        }catch(StartEndException e6){
         ErrorFXMLController.setErrorMessage(e6.getMessage());
         Main.ManageStage.createStage("Start End Error", "ErrorFXML.fxml");
        }
        
    }
    
    @FXML
    void exitButtonClicked(ActionEvent event) {
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
                "12","13","14","15","16","17","18","19","20","21","22","23");
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
