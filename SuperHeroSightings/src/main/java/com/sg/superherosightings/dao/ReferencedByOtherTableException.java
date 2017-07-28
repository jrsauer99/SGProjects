/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

/**
 *
 * @author apprentice
 */
public class ReferencedByOtherTableException extends Exception {
    public ReferencedByOtherTableException  (String message) {
        super(message);
    }

    public ReferencedByOtherTableException  (String message, Throwable cause) {
        super(message, cause);
    }
}
