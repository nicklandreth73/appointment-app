/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outlines;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Nick
 */
public class ErrorFXMLController implements Initializable {


    @FXML
    private Label errorLabel;

    @FXML
    private Button okButton;
    
    private static String errorMessage = "error";

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static void setErrorMessage(String error) {
        errorMessage = error;
    }

    @FXML
    void okButtonClicked(ActionEvent event) {
        Main.ManageStage.exitStage(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      errorLabel.setText(errorMessage);
    }    
    
}
