/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dto;

import java.math.BigDecimal;

/**
 *
 * @author apprentice
 */
public class Change {

    private int numQuarters;
    private int numDimes;
    private int numNickels;
    private int numPennies;
    private int totalCents;

    //CONSTRUCTOR
    public Change(BigDecimal cost, BigDecimal userMoney) {
        this.totalCents = userMoney.subtract(cost).multiply(new BigDecimal(100)).toBigInteger().intValueExact();

        int remaining;
        int moneyInQuarters;
        int moneyInDimes;
        int moneyInNickels;

        this.numQuarters = totalCents / 25;
        moneyInQuarters = numQuarters * 25;
        remaining = totalCents - moneyInQuarters;

        this.numDimes = remaining / 10;
        moneyInDimes = numDimes * 10;
        remaining -= moneyInDimes;

        this.numNickels = remaining / 5;
        moneyInNickels = numNickels * 5;
        remaining -= moneyInNickels;

        this.numPennies = remaining;

    }

    public int getNumQuarters() {
        return numQuarters;
    }

    public void setNumQuarters(int numQuarters) {
        this.numQuarters = numQuarters;
    }

    public int getNumDimes() {
        return numDimes;
    }

    public void setNumDimes(int numDimes) {
        this.numDimes = numDimes;
    }

    public int getNumNickels() {
        return numNickels;
    }

    public void setNumNickels(int numNickels) {
        this.numNickels = numNickels;
    }

    public int getNumPennies() {
        return numPennies;
    }

    public void setNumPennies(int numPennies) {
        this.numPennies = numPennies;
    }

    public int getTotalCents() {
        return totalCents;
    }

    @Override
    public String toString() {
        return (new BigDecimal(this.totalCents).divide(new BigDecimal(100)).setScale(2)).toString();
    }
}
