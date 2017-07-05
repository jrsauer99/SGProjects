/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dao;

/**
 *
 * @author apprentice
 */
public class FlooringFileFormatException extends Exception {
     
    public FlooringFileFormatException(String message){
        super(message);
    }
    
    public FlooringFileFormatException(String message, Throwable cause){
        super(message, cause);
    }
}
