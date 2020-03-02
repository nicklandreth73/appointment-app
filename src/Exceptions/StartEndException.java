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
public class StartEndException extends Exception{
    public StartEndException(){
        super("Start appointment time must be earlier than end appointment time");
    }
}
