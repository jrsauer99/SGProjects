/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author apprentice
 */
public class FlooringAuditDaoImpl implements FlooringAuditDao {

    public static final String AUDIT_FILE = "audit.txt";

    @Override
    public void writeAuditEntry(String entry) throws FlooringFilePersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true)); //true allows you to append to the audit file
        } catch (IOException ex) {
            throw new FlooringFilePersistenceException("Could not persist audit information", ex);
        }

        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.format(DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss")) + ", " + entry);
        out.flush();
    
    }
}
