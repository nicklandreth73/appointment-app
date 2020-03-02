/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outlines;

import Main.LogIns;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Nick
 */
public class UserLogsFXMLController implements Initializable {

    String fileName = "src/DataBase/LogIns.txt";
    ObservableList<Main.LogIns> logs = FXCollections.observableArrayList();
    
    @FXML
    private TableView<Main.LogIns> logsTable;

    @FXML
    private TableColumn<Main.LogIns, String> logsColumn;
    
    @FXML
    void exitButtonClicked(ActionEvent event) {
        Main.ManageStage.exitStage(event);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       File file = new File(fileName);
       if(!file.exists()){
        System.out.println("bad path");
        }
        try {
            Scanner inputFile = new Scanner(file);
            while(inputFile.hasNextLine()){
                String line = inputFile.nextLine().toString();
                logs.add(new LogIns(line));
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    logsColumn.setCellValueFactory(new PropertyValueFactory<>("line"));
    
    logsTable.setItems(logs);
        
    }    
    
}
