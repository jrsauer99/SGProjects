/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author apprentice
 */
public class VendingItem {

    private String slotNum; //CONSTRUCTOR
    private String itemName;
    private BigDecimal itemCost;
    private int numInInventory;

    public VendingItem(String slotNum, String itemName, BigDecimal itemCost, int numInInventory) {
        this.slotNum = slotNum;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.numInInventory = numInInventory;
    }

    
    
    public VendingItem(String slotNum) { // , int numInInventory
        this.slotNum = slotNum;
        //this.numInInventory = numInInventory;
    }

    public String getSlotNum() {
        return slotNum;
    }

    public void setSlotNum(String slotNum) {
        this.slotNum = slotNum;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public int getNumInInventory() {
        return numInInventory;
    }

    public void setNumInInventory(int numInInventory) {
        this.numInInventory = numInInventory;
    }

    public void reduceInventory() {
        if (this.numInInventory > 0) {
            this.numInInventory = this.numInInventory - 1;
        }
    }

//    @Override
//    public String toString() {
//        return " SLOT: " + this.slotNum + " | ITEM: " + this.itemName + " ";
//    }

    //**************************************
    //ADD EQUALS/HASHCODE METHODS FOR TESTING (ASSERT EQUALS)
    //INHERITED FROM OBJECT
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.slotNum);
        hash = 79 * hash + Objects.hashCode(this.itemName);
        hash = 79 * hash + Objects.hashCode(this.itemCost);
        hash = 79 * hash + this.numInInventory;

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VendingItem other = (VendingItem) obj;
        if (this.numInInventory != other.numInInventory) {
            return false;
        }

        if (!Objects.equals(this.slotNum, other.slotNum)) {
            return false;
        }
        if (!Objects.equals(this.itemName, other.itemName)) {
            return false;
        }

        if (!Objects.equals(this.itemCost, other.itemCost)) {
            return false;
        }
        return true;
    }
}
