/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.service;

import com.sg.vendingmachinespringmvc.dto.Change;
import com.sg.vendingmachinespringmvc.dto.VendingItem;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author apprentice
 */
public interface VendingServiceLayer {
    public VendingItem getItem(String slotNum) throws NoItemInventoryException, InvalidSlotNumException;

    //PASS THRU
    public ArrayList<VendingItem> getAllVendingItems();

    VendingItem decrementInventory(VendingItem vendItem);

    Change calcChange(BigDecimal cost, BigDecimal userMoney) throws InsufficientFundsException;

    Change calcChangeOnCancel(BigDecimal userMoney);
}
