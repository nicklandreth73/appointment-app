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
public class DateSelectionException extends Exception {
    public DateSelectionException(){
        super("Incomplete Date: You must set all date and time selectors when you change any date or time selector");
    }
}
