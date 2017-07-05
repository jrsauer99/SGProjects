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
public class FlooringFilePersistenceException extends Exception {

    public FlooringFilePersistenceException(String message) {
        super(message);
    }

    public FlooringFilePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
