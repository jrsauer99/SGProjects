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
public class FlooringConfigFileException extends Exception{
    
    public FlooringConfigFileException(String message){
        super(message);
    }
    
    public FlooringConfigFileException(String message, Throwable cause){
        super(message, cause);
    }
}
