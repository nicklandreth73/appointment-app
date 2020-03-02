/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author Nick
 */
public class OutOfHoursException extends Exception{
    public OutOfHoursException(){
        super("The given time is outside of business hours. Business hours are between 8:00 and 20:00 military time and include weekends.");
    }
}
