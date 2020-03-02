/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import DataBase.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Nick
 */
public class Software2Project extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
    ManageStage.createStage("Log In", "LogInFXML.fxml");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBConnection.startConnection();
        launch(args);
    }
    
}
