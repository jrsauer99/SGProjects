/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.service;

/**
 *
 * @author apprentice
 */
public class OrderDateComboDoesNotExistException extends Exception {
    
    public OrderDateComboDoesNotExistException(String message){
        super(message);
    }
    
    public OrderDateComboDoesNotExistException(String message, Throwable cause){
        super(message, cause);
    }
}
