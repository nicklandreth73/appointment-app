/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Nick
 */
public class ManageStage {
    private static String label;
    private static String outline;
    private static boolean closeParent = false;

//Creates modal stage with title 
    public static void createStage(String label, String outline) {
        ManageStage.label = label;
        ManageStage.outline = outline;
        try {
            FXMLLoader fxmlLoader;
            fxmlLoader = new FXMLLoader(MethodHandles.lookup().lookupClass().getResource("/Outlines/" + outline));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(label);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e3){
            System.out.println("Cant load new window, Exception: " + e3.getMessage());
        }
            }
    //Creates modal stage with title and option to close parent stage
        public static void createStage(String label, String outline, boolean closeParent, ActionEvent event) {
        ManageStage.label = label;
        ManageStage.outline = outline;
        try {
            FXMLLoader fxmlLoader;
            fxmlLoader = new FXMLLoader(MethodHandles.lookup().lookupClass().getResource("/Outlines/" + outline));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(label);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
            if(closeParent){
                exitStage(event);
            }
        }catch (Exception e3){
            System.out.println("Cant load new window, Exception: " + e3.getMessage());
        }
            }
        //closes stage that initiated the action
    public static void exitStage(ActionEvent event){
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
    }
}
