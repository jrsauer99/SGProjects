/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc;

import com.sg.vendingmachinespringmvc.dto.Change;
import com.sg.vendingmachinespringmvc.dto.VendingItem;
import com.sg.vendingmachinespringmvc.service.InsufficientFundsException;
import com.sg.vendingmachinespringmvc.service.InvalidSlotNumException;
import com.sg.vendingmachinespringmvc.service.NoItemInventoryException;
import com.sg.vendingmachinespringmvc.service.VendingServiceLayer;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author apprentice
 */
@CrossOrigin //ALLOWS ENPOINTS TO ACCEPT CROSE ORIG REQUEST
@Controller
public class VendingControllerREST {

    private VendingServiceLayer service;

    @Inject
    public VendingControllerREST(VendingServiceLayer service) {
        this.service = service;
    }

    //LOAD ITEMS
    @RequestMapping(value = "/items", method = RequestMethod.GET)
    @ResponseBody //RETURN JSON
    public List<VendingItem> getItems() {
        return service.getAllVendingItems();
    }

    //PURCHASE ITEMS
    @RequestMapping(value = "/money/{userMoney}/item/{slotNum}", method = RequestMethod.GET)
    @ResponseBody
    public Change purchaseItem(@PathVariable("userMoney") String userMoney, @PathVariable("slotNum") String slotNum) throws NoItemInventoryException, InsufficientFundsException, InvalidSlotNumException, IllegalArgumentException {
        BigDecimal itemCost;
        Change userChange;

        try {
            BigDecimal userMoneyBD = new BigDecimal(userMoney);
            VendingItem item = service.getItem(slotNum); //VALIDATES AVAILABILITY
            itemCost = service.getItem(slotNum).getItemCost();
            userChange = service.calcChange(itemCost, userMoneyBD);
            service.decrementInventory(item); //TRANSACTION SUCCESSFUL
            return userChange;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Format Error with Input $!");
        }

    }

}
