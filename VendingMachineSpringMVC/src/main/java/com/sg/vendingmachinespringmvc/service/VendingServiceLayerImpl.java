/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.service;

import com.sg.vendingmachinespringmvc.dao.VendingDao;
import com.sg.vendingmachinespringmvc.dto.Change;
import com.sg.vendingmachinespringmvc.dto.VendingItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author apprentice
 */
public class VendingServiceLayerImpl implements VendingServiceLayer{
    private VendingDao dao;
    
    //CONSTRUCTOR
    @Inject
    public VendingServiceLayerImpl(VendingDao dao) {
        this.dao = dao;
    }
    
    @Override
    public VendingItem getItem(String slotNum)throws NoItemInventoryException, InvalidSlotNumException {
        validateUserSlotNum(slotNum);
        validateAvailability(slotNum);
        return dao.getItem(slotNum);
    }
    
    //PASS THRU
    @Override
    public ArrayList<VendingItem> getAllVendingItems(){
        return dao.getAllVendingItems();
    }
    
    @Override
    public VendingItem decrementInventory(VendingItem vendItem) {
        dao.getItem(vendItem.getSlotNum()).reduceInventory();
        return vendItem;
    }

    @Override
    public Change calcChange(BigDecimal cost, BigDecimal userMoney) throws InsufficientFundsException  {
        validateUserFunds(cost, userMoney);
        Change userChange = new Change(cost, userMoney);
        return userChange;
    }

    @Override
    public Change calcChangeOnCancel(BigDecimal userMoney) {
        Change userChange = new Change(new BigDecimal(0), userMoney);
        return userChange;
    }
    
    private void validateAvailability(String userSlotNum) throws NoItemInventoryException {
        List<VendingItem> allItems = new ArrayList<>(dao.getAllVendingItems());
        if (!allItems 
                .stream()//grab the stream and pass to intermediate operations
                .filter(i->i.getNumInInventory()>0)
                .anyMatch(i -> i.getSlotNum().equals(userSlotNum))){
            throw new NoItemInventoryException ("SOLD OUT!");
        } 
        
    }
    
    private void validateUserFunds(BigDecimal itemCost, BigDecimal userMoney) throws InsufficientFundsException {
        BigDecimal amountShort;
        amountShort = itemCost.subtract(userMoney);
        
        if (itemCost.compareTo(userMoney) > 0){
            throw new InsufficientFundsException ("Please Deposit $" + amountShort);
        }
    }
    
    private void validateUserSlotNum(String userSlotNum) throws InvalidSlotNumException {
        List<VendingItem> allItems = new ArrayList<>(dao.getAllVendingItems());
         if (userSlotNum.equals("")){
            throw new InvalidSlotNumException ("Please Select an Item!");
        } else
        if (!allItems 
                .stream()//grab the stream and pass to intermediate operations
                .anyMatch(i -> i.getSlotNum().equals(userSlotNum))){
            throw new InvalidSlotNumException ("Not a valid slot number!");
        } 
    }
}
