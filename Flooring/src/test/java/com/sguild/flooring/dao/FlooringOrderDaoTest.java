/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dao;

import com.sguild.flooring.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author apprentice
 */
public class FlooringOrderDaoTest {

    FlooringOrderDao orderDao = new FlooringOrderDaoImpl();

    public FlooringOrderDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<Order> orderList = orderDao.getAllOrders();
        for (Order order : orderList) {
            orderDao.removeOrder(order);
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addOrder method, of class FlooringOrderDao.
     */
    @Test
    public void testAddGetOrder() {
        Order order = new Order(1);
        order.setCustomerName("Jen");
        order.setOrderDate(LocalDate.now());
        order.setProduct("Carpet");
        order.setMatCostSqFt(new BigDecimal(10));
        order.setLaborCostSqFt(new BigDecimal(15.5));
        order.setState("KY");
        order.setTaxRate(new BigDecimal(6));

        orderDao.addOrder(order);

        Order fromDao = orderDao.getOrder(1);

        assertEquals(order, fromDao);
    }

    /**
     * Test of removeOrder method, of class FlooringOrderDao.
     */
    @Test
    public void testRemoveOrder() {
        Order order1 = new Order(1);
        order1.setCustomerName("Jen");
        order1.setOrderDate(LocalDate.now());
        order1.setProduct("Carpet");
        order1.setMatCostSqFt(new BigDecimal(10));
        order1.setLaborCostSqFt(new BigDecimal(15.5));
        order1.setState("KY");
        order1.setTaxRate(new BigDecimal(6));

        orderDao.addOrder(order1);

        Order order2 = new Order(2);
        order2.setCustomerName("Andrew");
        order2.setOrderDate(LocalDate.now());
        order2.setProduct("Laminate");
        order2.setMatCostSqFt(new BigDecimal(11));
        order2.setLaborCostSqFt(new BigDecimal(13));
        order2.setState("CA");
        order2.setTaxRate(new BigDecimal(8));

        orderDao.addOrder(order2);
        
        orderDao.removeOrder(order1);
        assertEquals(1,orderDao.getAllOrders().size());
        assertNull(orderDao.getOrder(1));
        
        orderDao.removeOrder(order2);
        assertEquals(0,orderDao.getAllOrders().size());
        assertNull(orderDao.getOrder(2));
    }

    /**
     * Test of getAllOrders method, of class FlooringOrderDao.
     */
    @Test
    public void testGetAllOrders() {
        Order order1 = new Order(1);
        order1.setCustomerName("Jen");
        order1.setOrderDate(LocalDate.now());
        order1.setProduct("Carpet");
        order1.setMatCostSqFt(new BigDecimal(10));
        order1.setLaborCostSqFt(new BigDecimal(15.5));
        order1.setState("KY");
        order1.setTaxRate(new BigDecimal(6));

        orderDao.addOrder(order1);

        Order order2 = new Order(2);
        order2.setCustomerName("Andrew");
        order2.setOrderDate(LocalDate.now());
        order2.setProduct("Laminate");
        order2.setMatCostSqFt(new BigDecimal(11));
        order2.setLaborCostSqFt(new BigDecimal(13));
        order2.setState("CA");
        order2.setTaxRate(new BigDecimal(8));

        orderDao.addOrder(order2);

        assertEquals(2, orderDao.getAllOrders().size());

    }

    /**
     * Test of loadOrders method, of class FlooringOrderDao.
     */
    @Test
    public void testLoadOrders() {
    }

    /**
     * Test of saveOrders method, of class FlooringOrderDao.
     */
    @Test
    public void testSaveOrders() {
    }
}
