/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.controller;

import com.sguild.flooring.dao.FlooringFilePersistenceException;
import com.sguild.flooring.dto.Order;
import com.sguild.flooring.dto.Product;
import com.sguild.flooring.dto.State;
import com.sguild.flooring.service.FlooringServiceLayer;
import com.sguild.flooring.service.OrderDateComboDoesNotExistException;
import com.sguild.flooring.service.OrderDateDoesNotExistException;
import com.sguild.flooring.service.OrderDoesNotExistException;
import com.sguild.flooring.ui.FlooringView;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author apprentice
 */
public class FlooringController {

    private FlooringServiceLayer service;
    private FlooringView view;

    public FlooringController(FlooringServiceLayer service, FlooringView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int userOption;
        
        if(service.getIsTraining()){
        view.displayBanner("TRAINING MODE");
        }
        
        try {
            service.loadData();

            do {
                userOption = view.printMainMenuAndGetSelection();
                switch (userOption) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        service.saveOrders();
                        view.displayBanner("ORDERS SAVED");
                        break;
                    case 6:
                        listAllOrders();
                        break;
                    case 7:
                        keepGoing = false;
                        break;
                }
            } while (keepGoing);
            view.displayBanner("GOODBYE!");
        } catch (FlooringFilePersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

    private void displayOrders() {
        try {
            LocalDate orderDate = view.userInputDate();
            view.displayOrdersOnDate(service.getAllOrdersOnDate(orderDate), orderDate);
        } catch (OrderDateDoesNotExistException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void addOrder() {
        Integer orderNum = service.getNextOrderNum();
        ArrayList<State> stateList = new ArrayList<>(service.getAllStates());
        ArrayList<Product> productList = new ArrayList<>(service.getAllProducts());
        Order newOrder;
        Order completeOrder;
        int userOption;

        newOrder = view.getNewOrderInfo(orderNum, stateList, productList);
        completeOrder = service.performOrderCalcs(newOrder);
        userOption = view.displayOrderOpt2(completeOrder, "Add Order", "Cancel Order");
        switch (userOption) {
            case 1:
                service.addOrder(completeOrder);
                view.displayBanner("ORDER " + completeOrder.getOrderNum() + " SUCCESSFULLY ADDED");
                break;
            case 2:
                view.displayBanner("ORDER " + completeOrder.getOrderNum() + " CANCELED");
                break;
            default:
                break;
        }
    }

    private void editOrder() {
        Boolean isValid = false;
        int userOption;
        int userOption2;
        ArrayList<State> stateList = new ArrayList<>(service.getAllStates());
        ArrayList<Product> productList = new ArrayList<>(service.getAllProducts());
        HashMap<String, String> editOrderInfo = new HashMap<>();

        while (!isValid) {
            try {
                LocalDate orderDate = view.userInputDate();
                service.validateOrderDateExists(orderDate);
                Integer orderNum = view.userInputOrderNum();
                service.validateOrderExists(orderDate, orderNum);
                isValid = true;
                //GET ORDER TO EDIT
                Order order = service.getOrder(orderNum);
                //DECALRE A NEW ORDER TO EDIT
                Order editedOrder;
                //GET THE ORDER EDIT INFO
                editOrderInfo = view.userInputEditInfo(order, stateList, productList);
                //EDIT THE ORIGINAL ORDER
                editedOrder = service.editOrder(editOrderInfo);
                //PERFORM CALCS ON MODIFIED ORDER
                service.performOrderCalcs(editedOrder);
                //DISPLAY MODIFIED ORDER AND GET USER CONFIRM
                userOption2 = view.displayOrderOpt3(editedOrder, "Apply Changes", "Cancel Changes", "Apply Changes and Override Total");
                switch (userOption2) {
                    case 1:
                        service.removeOrder(order);
                        service.addOrder(editedOrder);
                        break;
                    case 2:
                        break;
                    case 3:
                        editedOrder = service.overrideTotal(editedOrder, view.overRideTotal());
                        service.addOrder(editedOrder);
                        view.displayOrder(editedOrder);
                        break;
                    default:
                        break;
                }
            } catch (OrderDateDoesNotExistException | OrderDoesNotExistException | OrderDateComboDoesNotExistException e) {
                view.displayErrorMessage(e.getMessage());
                isValid = false;
                userOption = view.displayUserOptOut("Try Again", "Return to Main Menu");
                switch (userOption) {
                    case 1:
                        break;
                    case 2:
                        isValid = true;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void removeOrder() {
        Boolean isValid = false;
        int userOption;
        int userOption2;
        ArrayList<State> stateList = new ArrayList<>(service.getAllStates());
        ArrayList<Product> productList = new ArrayList<>(service.getAllProducts());
        while (!isValid) {
            try {
                LocalDate orderDate = view.userInputDate();
                service.validateOrderDateExists(orderDate);
                Integer orderNum = view.userInputOrderNum();
                service.validateOrderExists(orderDate, orderNum);
                isValid = true;

                Order order = service.getOrder(orderNum);
                userOption2 = view.displayOrderOpt2(order, "Remove Order", "Cancel Remove Order");
                switch (userOption2) {
                    case 1:
                        service.removeOrder(order);
                        break;
                    case 2:
                        break;
                    default:
                        break;
                }
            } catch (OrderDateDoesNotExistException | OrderDoesNotExistException | OrderDateComboDoesNotExistException e) {
                view.displayErrorMessage(e.getMessage());
                isValid = false;
                userOption = view.displayUserOptOut("Try Again", "Return to Main Menu");
                switch (userOption) {
                    case 1:
                        break;
                    case 2:
                        isValid = true;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void listAllOrders() {
        view.diplayAllOrders(service.getAllOrders());
    }
}
