/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Nick
 */
public class DBConnection {
    
    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//3.227.166.251/U06ESb";
    
    // JBDC URL
    private static final String jdbcUrl = protocol + vendorName + ipAddress;
    
    // Driver Interface Reference
    private static final String mySqlJdbcDriver = "com.mysql.jdbc.Driver";
    
   private static Connection conn = null;
   
    
    
    private static final String username = "U06ESb";
    private static final String password = "53688740749";
    
    public static Connection startConnection(){
        try{
            Class.forName(mySqlJdbcDriver);
            conn = (Connection)DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connection Successful");
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static Connection getConn() {
        return conn;
    }
    
    
    public static void closeConnection(){
       try{
           conn.close();
           System.out.println("Connection closed");
       }catch(SQLException e){
           System.out.println(e.getMessage());
       }
    }
}
