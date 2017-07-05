/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dao;

import com.sguild.flooring.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author apprentice
 */
public class FlooringOrderDaoStubImpl implements FlooringOrderDao {

    private Order firstOrder;
    private Order secondOrder;
    private Order thirdOrder;
    private ArrayList<Order> orderList = new ArrayList<>();

    //CONSTRUCTOR
    public FlooringOrderDaoStubImpl() {
        //INSTATIATE FIRST ORDER
        firstOrder = new Order(1);
        firstOrder.setCustomerName("Jen");
        firstOrder.setOrderDate(LocalDate.now());
        firstOrder.setArea(new BigDecimal(100));
        firstOrder.setState("KY");
        firstOrder.setTaxRate(new BigDecimal(6));
        firstOrder.setProduct("Carpet");
        firstOrder.setMatCostSqFt(new BigDecimal(10));
        firstOrder.setLaborCostSqFt(new BigDecimal(11.5));

        orderList.add(firstOrder);

        secondOrder = new Order(3);
        secondOrder.setCustomerName("Andy");
        secondOrder.setOrderDate(LocalDate.now().minusDays(100));
        secondOrder.setArea(new BigDecimal(150));
        secondOrder.setState("CA");
        secondOrder.setTaxRate(new BigDecimal(25));
        secondOrder.setProduct("Flooring");
        secondOrder.setMatCostSqFt(new BigDecimal(5.5));
        secondOrder.setLaborCostSqFt(new BigDecimal(12));

        orderList.add(secondOrder);
        
        thirdOrder = new Order(2);
        thirdOrder.setCustomerName("Joe");
        thirdOrder.setOrderDate(LocalDate.now());
        thirdOrder.setArea(new BigDecimal(160));
        thirdOrder.setState("OH");
        thirdOrder.setTaxRate(new BigDecimal(4));
        thirdOrder.setProduct("Board");
        thirdOrder.setMatCostSqFt(new BigDecimal(6.5));
        thirdOrder.setLaborCostSqFt(new BigDecimal(14));

        orderList.add(thirdOrder);
        
    }

    @Override
    public Order addOrder(Order order) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (firstOrder.getOrderNum().equals(order.getOrderNum())) {
            return firstOrder;
        } else {
            return null;
        }

    }

    @Override
    public Order getOrder(int orderNum) {
        if (firstOrder.getOrderNum().equals(orderNum)) {
            return firstOrder;
        } else {
            return null;
        }

    }

    @Override
    public Order removeOrder(Order order) {
        if (firstOrder.getOrderNum().equals(order.getOrderNum())) {
            return firstOrder;
        } else {
            return null;
        }
    }

    @Override
    public ArrayList getAllOrders() {
        return orderList;
    }

    @Override
    public ArrayList getAllOrderDates() {
        ArrayList<LocalDate> dateList = new ArrayList<>();
        dateList.add(firstOrder.getOrderDate());
        dateList.add(secondOrder.getOrderDate());
        dateList.add(thirdOrder.getOrderDate());
        return dateList;
    }

    @Override
    public HashMap loadOrders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveOrders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
