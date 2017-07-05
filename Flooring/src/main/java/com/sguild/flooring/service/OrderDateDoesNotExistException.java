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
public class OrderDateDoesNotExistException extends Exception {

    public OrderDateDoesNotExistException(String message) {
        super(message);
    }

    public OrderDateDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
