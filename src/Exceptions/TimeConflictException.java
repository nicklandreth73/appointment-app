/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Nick
 */
public class TimeConflictException extends Exception {
    public TimeConflictException(LocalDate conflictDate, LocalTime conflictTime){
        super("Conflicts with appointment starting at" + conflictDate + " " + conflictTime);
    }
}
