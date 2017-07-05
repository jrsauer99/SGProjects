/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.dto.VendingItem;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author apprentice
 */
public class VendingDaoStubImpl implements VendingDao {

    VendingItem onlyItem;
    VendingItem secondItem;
    ArrayList<VendingItem> vendingList = new ArrayList<>();

    //TO INITIALIZE THESE NEW VARIABLES ABOVE, CREATE A CLASS LEVEL CONSTRUCTOR
    public VendingDaoStubImpl() {
        onlyItem = new VendingItem("001");
        onlyItem.setItemName("Snickers");
        onlyItem.setItemCost(new BigDecimal(3.5));
        onlyItem.setNumInInventory(6);
        vendingList.add(onlyItem);  //ADD THIS ITEM TO THE vendingList

        secondItem = new VendingItem("002");
        secondItem.setItemName("Snickerdoodles");
        secondItem.setItemCost(new BigDecimal(4.5));
        secondItem.setNumInInventory(0);
        vendingList.add(secondItem);

    }

    @Override
    public VendingItem addItem(VendingItem vendItem) {

        if (vendItem.getSlotNum().equals(onlyItem.getSlotNum())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public VendingItem removeItem(String slotNum) {
        if (slotNum.equals(onlyItem.getSlotNum())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public VendingItem getItem(String slotNum) {
        if (slotNum.equals(onlyItem.getSlotNum())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<VendingItem> getAllVendingItems() {
        return vendingList;
    }

}
