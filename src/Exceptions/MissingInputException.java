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
public class MissingInputException extends Exception {
   public MissingInputException(){
        super("Please fill out all required fields marked with the *");
    }
}
