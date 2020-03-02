/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import com.mysql.jdbc.Connection;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 *
 * @author Nick
 */
public class ManageTime {
    private static Connection conn = DataBase.DBConnection.getConn();
    
    public static String sqlTime(){
        ZonedDateTime currentLocalZDT = ZonedDateTime.now();
        ZonedDateTime currentToGDT = currentLocalZDT.withZoneSameInstant(ZoneId.of("GMT"));
        
        String sqlTime = currentToGDT.toLocalDate().toString() + " " + currentToGDT.toLocalTime().toString();
        return sqlTime;
    }
    
    
    public static String userInputToGMT(int year, int month, int day, int hour, int minute){
        LocalDate userDate = LocalDate.of(year, month, day);
        LocalTime userTime =  LocalTime.of(hour, minute);
        ZoneId userZoneID = ZoneId.systemDefault();
        ZonedDateTime userZDT = ZonedDateTime.of(userDate, userTime, userZoneID);
        ZoneId GMTZoneID = ZoneId.of("GMT");
        
        ZonedDateTime userToGMT = userZDT.withZoneSameInstant(GMTZoneID);
        String convertedString = userToGMT.toLocalDate().toString() + " " + userToGMT.toLocalTime() + ":00";
        
        return convertedString;
    }
        public static LocalDateTime userInputToGMTObject(int year, int month, int day, int hour, int minute){
        LocalDate userDate = LocalDate.of(year, month, day);
        LocalTime userTime =  LocalTime.of(hour, minute);
        ZoneId userZoneID = ZoneId.systemDefault();
        ZonedDateTime userZDT = ZonedDateTime.of(userDate, userTime, userZoneID);
        ZoneId GMTZoneID = ZoneId.of("GMT");
        
        ZonedDateTime userToGMT = userZDT.withZoneSameInstant(GMTZoneID);
        LocalDateTime userToGMTLDT = userToGMT.toLocalDateTime();
        
        return userToGMTLDT;
    }
        public static LocalTime userInputToGMTHour(int hour, int minute){
        LocalDate userDate = LocalDate.now();
        LocalTime userTime =  LocalTime.of(hour, minute);
        ZoneId userZoneID = ZoneId.systemDefault();
        ZonedDateTime userZDT = ZonedDateTime.of(userDate, userTime, userZoneID);
        ZoneId GMTZoneID = ZoneId.of("GMT");
        ZonedDateTime userToGMT = userZDT.withZoneSameInstant(GMTZoneID);
        LocalTime userToGMTLT = userToGMT.toLocalTime();
        
        return userToGMTLT;
    }
    public static LocalDate userInputToGMTDate(int year, int month, int day){
        LocalDate userDate = LocalDate.of(year, month, day);
        LocalTime userTime =  LocalTime.of(12, 00);
        ZoneId userZoneID = ZoneId.systemDefault();
        ZonedDateTime userZDT = ZonedDateTime.of(userDate, userTime, userZoneID);
        ZoneId GMTZoneID = ZoneId.of("GMT");
        ZonedDateTime userToGMT = userZDT.withZoneSameInstant(GMTZoneID);
        LocalDate userToGMTLD = userToGMT.toLocalDate();
        
        return userToGMTLD;
    }
    
    public static String sqlToLocalTime(Timestamp sqlTime){
    LocalDateTime gmtDateTime = sqlTime.toLocalDateTime();
    ZonedDateTime gmtZDT = ZonedDateTime.of(gmtDateTime, ZoneId.of("GMT"));
    ZoneId userZoneID = ZoneId.systemDefault();
    ZonedDateTime sqlToLocal = gmtZDT.withZoneSameInstant(userZoneID);
    String sqlToLocalString = sqlToLocal.toLocalDate().toString() + " " + sqlToLocal.toLocalTime().toString();
    return sqlToLocalString;
    
    }    
    
    public static LocalDateTime sqlToLocalTimeObject(Timestamp sqlTime){
    LocalDateTime gmtDateTime = sqlTime.toLocalDateTime();
    ZonedDateTime gmtZDT = ZonedDateTime.of(gmtDateTime, ZoneId.of("GMT"));
    ZoneId userZoneID = ZoneId.systemDefault();
    ZonedDateTime sqlToLocal = gmtZDT.withZoneSameInstant(userZoneID);
    LocalDateTime sqlToLocalDateTime = sqlToLocal.toLocalDateTime();
    
   

    return sqlToLocalDateTime;
    }
    
    
    }
    