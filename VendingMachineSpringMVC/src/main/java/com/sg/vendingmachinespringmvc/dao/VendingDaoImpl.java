/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.dto.VendingItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author apprentice
 */
public class VendingDaoImpl implements VendingDao {
    
    private LinkedHashMap<String, VendingItem> vendItemMap = new LinkedHashMap<>();
    
    public VendingDaoImpl(){
        
        vendItemMap.put("A1", new VendingItem("A1", "Snickers", new BigDecimal("1.50"), 10));
        vendItemMap.put("2", new VendingItem("2", "Skittles", new BigDecimal("3.50"), 12));
        vendItemMap.put("3", new VendingItem("3", "Chips", new BigDecimal("1.25"), 6));
        vendItemMap.put("4", new VendingItem("4", "Onion Rings", new BigDecimal("2.50"), 0));
        vendItemMap.put("5", new VendingItem("5", "Dorittos", new BigDecimal("1.75"), 13));
        vendItemMap.put("6", new VendingItem("6", "Cookies", new BigDecimal("2.25"), 10));
        vendItemMap.put("7", new VendingItem("7", "Twix", new BigDecimal("1.50"), 7));
        vendItemMap.put("8", new VendingItem("8", "Sun Chips", new BigDecimal("2.00"), 9));
        vendItemMap.put("9", new VendingItem("9", "Potatoes", new BigDecimal("1.50"), 10));
        //vendItemMap.put("10", new VendingItem("10", "Potatoes2", new BigDecimal("1.50"), 7));
    }
    
    @Override
    public VendingItem addItem(VendingItem vendItem) {
        VendingItem newItem = vendItemMap.put(vendItem.getSlotNum(), vendItem);
        return newItem;
    }

    @Override
    public VendingItem removeItem(String slotNum) {
        VendingItem removedItem = vendItemMap.remove(slotNum);
        return removedItem;
    }

    @Override
    public VendingItem getItem(String slotNum) {
        return vendItemMap.get(slotNum);
    }

    @Override
    public ArrayList<VendingItem> getAllVendingItems() {
        return new ArrayList<VendingItem>(vendItemMap.values());
    }
    
}
