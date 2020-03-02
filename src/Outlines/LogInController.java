/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outlines;

import DataBase.DBConnection;
import Main.User;
import com.mysql.jdbc.Connection;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.abs;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Nick
 */
public class LogInController implements Initializable {

    //FXML IDs
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label incorrectLog;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Button submitButton;

    //File Variables for log
    String fileName = "src/DataBase/LogIns.txt";

    File file = new File(fileName);

    //Other Variables
    String tempUserPass = "userpass";

    ResourceBundle crb = ResourceBundle.getBundle("Resources/Nat", Locale.getDefault());
    Connection conn = DBConnection.getConn();
    @FXML
    void submitClicked(ActionEvent event) {

        
        //SQL set up for username and password verification
        
        PreparedStatement ps = null;
        String sql = "select * from user where userName = ? and password = ?";
//Checks that username and password match database and exits function if they do not
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username.getText());
            ps.setString(2, password.getText());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                try {
                    incorrectLog.setText(crb.getString("Invalid") + " " + crb.getString("Entry"));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet userRS = conn.createStatement().executeQuery("SELECT userName,userId FROM user Where userName = " + "'" + username.getText() + "'");
            if( userRS.next()){
            User.setUserID(userRS.getString("userId"));
            User.setUserName(userRS.getString("userName"));
            }

        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        }
        
 //opens main menu
        Main.ManageStage.createStage("Main Menu", "MainFXML.fxml", true, event);
 //Checks for appointments and launches window if there are any
        try{
               ResultSet timesRS = conn.createStatement().executeQuery("Select start from appointment");
               while(timesRS.next()){
                LocalDateTime start = Main.ManageTime.sqlToLocalTimeObject(timesRS.getTimestamp("start"));
                LocalDateTime now = LocalDateTime.now();
                Long startDiff = ChronoUnit.MINUTES.between(start, now);
                if(abs(startDiff) < 17){
                    ErrorFXMLController.setErrorMessage("You have an appointment at " + start.toLocalTime());
                    Main.ManageStage.createStage("Appointment Alert", "ErrorFXML.fxml");
                }
            }
        }catch (SQLException e2) {
            System.out.println(e2.getMessage());
        }
//logs user log in time and date   
         recordLogIn();
         System.out.println(User.getUserID() + " " + User.getUserName());
    }
//logs user log in time and date 
    private void recordLogIn(){
        try{
            FileWriter fwriter = new FileWriter(fileName, true);
            PrintWriter outputFile = new PrintWriter(fwriter);
            outputFile.println("User: " + User.getUserName() +" Logged in at: " +LocalDateTime.now());
            outputFile.close();
} catch (IOException e) {
            System.out.println("Cant write to log file, Exception: " + e.getMessage());
}
    }

@Override
        public void initialize(URL url, ResourceBundle rb) {
        submitButton.setText(crb.getString("Submit"));
        username.setPromptText(crb.getString("Username"));
        usernameLabel.setText(crb.getString("Username"));
        password.setPromptText(crb.getString("Password"));
        passwordLabel.setText(crb.getString("Password"));
        
        

        
    
    
    }
}
