/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.advice;

import com.sguild.flooring.dao.FlooringAuditDao;
import com.sguild.flooring.dao.FlooringFilePersistenceException;
import com.sguild.flooring.dto.Order;
import java.util.ArrayList;
import java.util.HashMap;
import org.aspectj.lang.JoinPoint;

/**
 *
 * @author apprentice
 */
public class LoggingAdvice {

    FlooringAuditDao auditDao;

    public LoggingAdvice(FlooringAuditDao auditDao) {
        this.auditDao = auditDao;
    }

    public void createReadWarningAudit(JoinPoint jp, HashMap result) {
        HashMap<String, ArrayList> myErrorMap = new HashMap<>(result);
        ArrayList<String> myDuplicate = new ArrayList<>(myErrorMap.get("DUPLICATE"));
        ArrayList<String> myFileFormatErr = new ArrayList<>(myErrorMap.get("FILE_FORMAT"));
        ArrayList<String> myFileNameErr = new ArrayList<>(myErrorMap.get("FILE_NAME"));
        String auditEntry = "";

        if (!myDuplicate.isEmpty()) {
            auditEntry += "**WARNING THE FOLLOWING ORDERS HAD DUPLICATE IDS AND WERE AUTO RENUMBERED**\n";
            for (String s : myDuplicate) {
                auditEntry += "                      " + s + " \n";
            }
        }

        if (!myFileFormatErr.isEmpty()) {
            auditEntry += "\n                   **WARNING THE FOLLOWING ORDERS DID NOT MEET INPUT FORMAT REQUIREMENTS AND WERE NOT ADDED\n";
            for (String s : myFileFormatErr) {
                auditEntry += "                     " + s + " \n";
            }
        }

        if (!myFileNameErr.isEmpty()) {
            auditEntry += "\n                   **WARNING THE FOLLOWING FILES DID NOT MEET INPUT FORMAT REQUIREMENTS AND WERE NOT ADDED\n";
            for (String s : myFileNameErr) {
                auditEntry += "                     " + s + " \n";
            }
        }

        try {
            auditDao.writeAuditEntry(auditEntry);
        } catch (FlooringFilePersistenceException e) {
            System.err.println(
                    "ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }

    public void createExceptionEntry(JoinPoint jp, Exception ex) {

        String auditEntry;
        try {
            auditEntry = "METHOD: " + jp.getSignature().getName() + " | ";
            auditEntry += "**ERROR** ";
            auditEntry += "EXCEPTION: " + ex.getClass().getSimpleName();
            auditDao.writeAuditEntry(auditEntry);
        } catch (FlooringFilePersistenceException e) {
            System.err.println("ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }

    public void createSuccessAuditEntry(JoinPoint jp, Order result) {
        String auditEntry;
        Object[] args = jp.getArgs();
        auditEntry = "METHOD: " + jp.getSignature().getName() + " | ";
        try {
            if (args[0].getClass() == Order.class) {
                auditEntry += args[0];
                auditDao.writeAuditEntry(auditEntry);
            }

        } catch (FlooringFilePersistenceException e) {
            System.err.println("ERROR: Could not create audit entry in LoggingAdvice.");
        }

    }

    public void createSaveAuditEntry(JoinPoint jp) {
        String auditEntry;
        Object[] args = jp.getArgs();
        auditEntry = "METHOD: " + jp.getSignature().getName() + " | ";
        try {
            auditDao.writeAuditEntry(auditEntry);
        } catch (FlooringFilePersistenceException e) {
            System.err.println("ERROR: Could not create audit entry in LoggingAdvice.");
        }

    }

    public void createReadWarnProdStAudit(JoinPoint jp, ArrayList result) {
        ArrayList<String> myProdErr = new ArrayList<>(result);
        String auditEntry = "";
        try {
            if (!myProdErr.isEmpty()) {
                auditEntry += "**WARNING THE FOLLOWING PRODUCTS/STATES HAD FORMAT ERRORS AND WERE NOT LOADED**\n";
                for (String s : myProdErr) {
                    auditEntry += "                      " + s + " \n";
                }
                auditDao.writeAuditEntry(auditEntry);
            }

        } catch (FlooringFilePersistenceException e) {
            System.err.println("ERROR: Could not create audit entry in LoggingAdvice.");
        }

    }
}
