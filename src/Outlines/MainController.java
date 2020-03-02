/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outlines;

import Main.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author Nick
 */
public class MainController implements Initializable {
    
 
    //Button IDs
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button LogOff;

    @FXML
    private Button ShowClientSchedule;

    @FXML
    private Button newAppointment;

    @FXML
    private Button newCustomer;

    @FXML
    private Button showMonthlySchedule;

    @FXML
    private Button showUserLogIns;

    @FXML
    private Button viewAppointments;

    @FXML
    private Button viewCustomers;

    @FXML
    private Button viewMonth;

    @FXML
    private Button viewWeek;
    
    
    @FXML
    private Label userWelcome;
    

    // Button handlers
   
    @FXML
    void LogOffClicked(ActionEvent event) {
        Main.ManageStage.createStage("Log In", "LogInFXML.fxml", true, event);
    }

    @FXML
    void ShowClientScheduleClicked(ActionEvent event) {
        Main.ManageStage.createStage("View Appointments by User", "ViewAppointmentsByUserFXML.fxml");
    }

    @FXML
    void showMonthlyTypesClicked(ActionEvent event) {
        Main.ManageStage.createStage("Show First Monthly Appointments with unique type", "ViewTypesFXML.fxml");
    }

    @FXML
    void showUserLogInsClicked(ActionEvent event) {
        Main.ManageStage.createStage("View Log Ins", "UserLogsFXML.fxml");
    }

    @FXML
    void viewAppointmentsClicked(ActionEvent event) {
    Main.ManageStage.createStage("View Appointments", "ViewAppointmentsFXML.fxml");
    }

    @FXML
    void viewCustomersClicked(ActionEvent event) {
    Main.ManageStage.createStage("View Customers", "ViewCustomersFXML.fxml");
    }
    

    @FXML
    void viewMonthClicked(ActionEvent event) {
        Main.ManageStage.createStage("View Appointments by month", "ViewAppointmentsByMonthFXML.fxml");
    }

    @FXML
    void viewWeekClicked(ActionEvent event) {
        Main.ManageStage.createStage("View Appointments by week", "ViewAppointmentsByWeekFXML.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userWelcome.setText("Welcome " + User.getUserName());
    }    
    
}
