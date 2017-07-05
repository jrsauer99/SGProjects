/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author apprentice
 */
public interface UserIO {

    void print(String message);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);

    String readString(String prompt);

    public BigDecimal readUSCurrency(String prompt);

    public BigDecimal readBigDecimal(String prompt);

    public BigDecimal readPositiveBigDecimal(String prompt);

    public LocalDate readLocalDate(String prompt);

    public String readLocalDateEditMode(String prompt);

    public String readBigDecimalEditMode(String prompt);
}
