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
public class FlooringDuplicateIDException extends Exception {
    
    public FlooringDuplicateIDException(String message){
        super(message);
    }
    
    public FlooringDuplicateIDException(String message, Throwable cause){
        super(message, cause);
    }
}
