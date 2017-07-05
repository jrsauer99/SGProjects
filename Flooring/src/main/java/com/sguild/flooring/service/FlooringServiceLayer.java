/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.service;

import com.sguild.flooring.dao.FlooringFilePersistenceException;
import com.sguild.flooring.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface FlooringServiceLayer {

    public ArrayList getAllOrdersOnDate(LocalDate orderDate) throws OrderDateDoesNotExistException;

    public void validateOrderDateExists(LocalDate orderDate) throws OrderDateDoesNotExistException;

    public int getNextOrderNum();

    public List getAllStates();

    public List getAllProducts();

    public Order performOrderCalcs(Order order);

    public Order addOrder(Order order);
    
    public Order getOrder(Integer orderNum);

    public ArrayList getAllOrderDates();

    public void validateOrderExists(LocalDate orderDate, int orderNum) throws OrderDateDoesNotExistException, OrderDoesNotExistException, OrderDateComboDoesNotExistException;

    public ArrayList getAllOrderNums();
    
    public Order editOrder(HashMap orderEditInfo);
    
    public Order removeOrder(Order order);
    
    public ArrayList<Order> getAllOrders();

    public void saveOrders() throws FlooringFilePersistenceException;
    
    public void loadData() throws FlooringFilePersistenceException;
    
    public Order overrideTotal(Order order, BigDecimal newTotal);

    public Boolean getIsTraining();
    
    public void setIsTraining(Boolean isTraining);
}
