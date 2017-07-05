/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dao;

import com.sguild.flooring.dto.Order;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author apprentice
 */
public interface FlooringOrderDao {
    public Order addOrder(Order order);
    
    public Order getOrder(int orderNum);
    
    public ArrayList getAllOrderDates();
    
    public Order removeOrder(Order order);
    
    public ArrayList getAllOrders();
    
    public HashMap loadOrders() throws FlooringFilePersistenceException;
    
    public void saveOrders() throws FlooringFilePersistenceException;
    
}
