/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import Exceptions.NotInDBException;
import com.mysql.jdbc.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick
 */
public class Utilities {
      private static Connection conn = DBConnection.getConn();
    
    public static boolean InDB(String table, String field, String value){
       boolean success = true;
        try {
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM " + table + " where " + field + " = " + addQuotes(value));
         if(!rs.next()){
             success = false;
             throw new NotInDBException(value);
         }
       } catch (SQLException ex) {
           System.out.println(ex.getMessage());
       }  catch (NotInDBException e1) {
              Outlines.ErrorFXMLController.setErrorMessage(e1.getMessage());
              Main.ManageStage.createStage("Value not found", "SQLErrorFXML.fxml");
          }
       return success;
    }
        public static boolean InDB(String table, String field, String value, boolean message){
       boolean success = true;
        try {
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM " + table + " where " + field + " = " + addQuotes(value));
         if((!rs.next()) && message){
             success = false;
         }
       } catch (SQLException ex) {
           System.out.println(ex.getMessage());
       }
       return success;
    }
    
    public static String addQuotes(String input){
    return ("'" + input + "'");
    }
}
