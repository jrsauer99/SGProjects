/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.dto.VendingItem;
import java.util.ArrayList;

/**
 *
 * @author apprentice
 */
public interface VendingDao {
    public VendingItem addItem(VendingItem vendItem);
    
    VendingItem getItem(String slotNum);
    
    public ArrayList<VendingItem> getAllVendingItems();
    
    public VendingItem removeItem(String slotNum);  
}
