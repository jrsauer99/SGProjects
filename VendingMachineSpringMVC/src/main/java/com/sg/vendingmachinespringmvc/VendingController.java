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
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author apprentice
 */
@Controller
public class VendingController {

    private VendingServiceLayer service;

    //CONSTRUCTOR INJECTION
    @Inject
    public VendingController(VendingServiceLayer service) {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(Map<String, Object> model) {

        List<VendingItem> itemList = service.getAllVendingItems(); //GET ALL VENDING ITEMS TO LOAD TO WELCOME.JSP
        BigDecimal userMoney = new BigDecimal("0.00");
        String userMessage = "Welcome!";
        Boolean changeAvail = false;
        Change userChange = new Change(new BigDecimal("0.0"), new BigDecimal("0.0"));

        model.put("itemList", itemList);
        model.put("userMoney", userMoney);
        model.put("userMessage", userMessage);
        model.put("changeAvail", changeAvail);
        model.put("userChange", userChange);

        return "welcome";
    }

    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
    public String purchaseItem(Map<String, Object> model, HttpServletRequest request) {

        List<VendingItem> itemList = service.getAllVendingItems();
        String itemNum = request.getParameter("itemNum");
        Boolean changeAvail = Boolean.valueOf(request.getParameter("changeAvailPur"));
        String userMessage;
        Change userChange;
        BigDecimal userMoney;
        BigDecimal itemCost;

        if (changeAvail) { //IF TRUE, THERE IS CHANGE IN THE BOX, BALANCE IS 0, CHG MUST CLEAR FIRST
            userMoney = new BigDecimal("0.00");
            try { //VALIDATE HIDDEN INPUT
                BigDecimal userChangeCents = new BigDecimal(request.getParameter("hiddenUserChgPur"));
                userChange = service.calcChangeOnCancel(userChangeCents); //INCLUDE CHANGE 
                userMessage = "Please take your change!";
            } catch (NumberFormatException e) {
                userMessage = "Format Error with Change!";
                changeAvail = false;
                userChange = service.calcChangeOnCancel(new BigDecimal("0.00"));
            }

        } else { //PROCEED WITH TRANSACTION, GET TOTAL USER MONEY FROM HIDDEN FIELD
            try { //VALIDATE THAT ITEM IS IN INVENTORY AND USER HAS SUPPLIED ENOUGH FUNDS BEFORE COMPLETEING TRANS
                try { //VALIDATE USER MONEY & THROW EX W CUSTOM MESSAGE
                    userMoney = new BigDecimal(request.getParameter("hiddenUserMoneyPur"));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Format Error with Input $!");
                }

                VendingItem item = service.getItem(itemNum); //VALIDATES AVAILABILITY
                itemCost = service.getItem(itemNum).getItemCost();
                userChange = service.calcChange(itemCost, userMoney);
                service.decrementInventory(item); //TRANSACTION SUCCESSFUL
                if ((itemCost.compareTo(userMoney) == 0)) { //AFTER SUCCESSFUL TRANSACTION
                    changeAvail = false; //CHECK IF CHANGE IS DUE
                } else {
                    changeAvail = true;
                }
                userMessage = "Thank you!";
                itemNum = ""; //CLEAR ITEM NUM FROM INPUT FIELD
                userMoney = new BigDecimal("0.00"); //RESET USER MONEY
            } catch (NoItemInventoryException | InsufficientFundsException | InvalidSlotNumException | IllegalArgumentException e) {
                userChange = service.calcChangeOnCancel(new BigDecimal("0.00"));
                userMessage = e.getMessage();
                changeAvail = false;
                if (e.getClass() == IllegalArgumentException.class) {
                    userMoney = new BigDecimal("0.00");
                } else {
                    userMoney = new BigDecimal(request.getParameter("hiddenUserMoneyPur"));
                }
            }
        }
        model.put("itemList", itemList);
        model.put("itemNum", itemNum);
        model.put("userChange", userChange);
        model.put("userMessage", userMessage);
        model.put("userMoney", userMoney);
        model.put("changeAvail", changeAvail);

        return "welcome";
    }

    @RequestMapping(value = "/addMoney", method = RequestMethod.POST)
    public String addMoney(Map<String, Object> model, HttpServletRequest request) {
        List<VendingItem> itemList = service.getAllVendingItems();
        String itemNum;
        String userMessage;
        BigDecimal userMoney;
        Change userChange;
        BigDecimal addMoney;
        BigDecimal userChangeCents;
        Boolean changeAvail = Boolean.valueOf(request.getParameter("changeAvailMon"));
        itemNum = request.getParameter("hiddenItemNum");

        if (changeAvail) { //CHECK TO SEE IF THERE IS CHANGE IN THE CHG BOX
            userMoney = new BigDecimal("0.00");
            try {
                userChangeCents = new BigDecimal(request.getParameter("hiddenUserChg"));
                userChange = service.calcChangeOnCancel(userChangeCents);
                userMessage = "Please take your change!";
                changeAvail = true;
            } catch (NumberFormatException e) {
                userMessage = "Format Error with Change!";
                changeAvail = false;
                userChange = service.calcChangeOnCancel(new BigDecimal("0.00"));
            }
        } else { //IF NOT, ADD MONEY
            userChange = service.calcChangeOnCancel(new BigDecimal("0.00"));
            changeAvail = false;
            try { //VALIDATE USER MONEY INPUT
                addMoney = new BigDecimal(request.getParameter("addMoney"));
                userMoney = new BigDecimal(request.getParameter("hiddenUserMoney"));//GET CURRENT RUNNING TOTAL OF USER MONEY
                userMoney = userMoney.add(addMoney.setScale(2, RoundingMode.HALF_UP));
                userMessage = "Welcome!"; //INITIALIZE USER MESSAGE FOR NEW TRANSACTION
            } catch (NumberFormatException e) {
                userMessage = "Incorrect format of $ Input";
                userMoney = new BigDecimal("0.00");
            }

        }
        model.put("itemList", itemList);
        model.put("userMoney", userMoney);
        model.put("userMessage", userMessage);
        model.put("itemNum", itemNum);
        model.put("changeAvail", changeAvail);
        model.put("userChange", userChange);

        return "welcome";
    }

    @RequestMapping(value = "/getChange", method = RequestMethod.POST)
    public String getChange(Map<String, Object> model, HttpServletRequest request) {
        List<VendingItem> itemList = service.getAllVendingItems();
        Boolean changeAvail;
        Change userChange;
        String userMessage;
        BigDecimal userMoney;

        try { //VALIDATE USER MONEY
            userMoney = new BigDecimal(request.getParameter("hiddenUserMoneyChg"));
            if (userMoney.compareTo(new BigDecimal(0.0)) > 0) { //IF USER HAS A BALANCE
                userChange = service.calcChangeOnCancel(userMoney); //CALC USER CHANGE ON CANCEL
                changeAvail = true;
                userMessage = "Click Again to Take Your Change!";
            } else { //OTHERWISE, CLEAR MESSAGES AND INITIALIZE FOR NEXT PURCHASE
                userChange = new Change(new BigDecimal(0.0), new BigDecimal(0.00));
                userMessage = "Welcome!";
                changeAvail = false;
            }
        } catch (NumberFormatException e) {
            userMessage = "Incorrect format of $ Input";
            userChange = new Change(new BigDecimal(0.0), new BigDecimal(0.00));
            changeAvail = false;
        }

        userMoney = new BigDecimal("0.00");
        String itemNum = "";
        model.put("itemList", itemList);
        model.put("itemNum", itemNum);
        model.put("userChange", userChange);
        model.put("userMessage", userMessage);
        model.put("userMoney", userMoney);
        model.put("changeAvail", changeAvail);

        return "welcome";
    }
}
