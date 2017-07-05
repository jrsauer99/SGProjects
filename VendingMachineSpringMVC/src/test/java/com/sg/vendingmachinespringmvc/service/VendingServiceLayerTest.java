/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.service;

import com.sg.vendingmachinespringmvc.dao.VendingDao;
import com.sg.vendingmachinespringmvc.dto.Change;
import com.sg.vendingmachinespringmvc.dto.VendingItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author apprentice
 */
public class VendingServiceLayerTest {
    private VendingServiceLayer service;
    
    
    public VendingServiceLayerTest() {
         ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        service = ctx.getBean("vendingServiceLayer", VendingServiceLayer.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
     @Test
    public void testGetItemNoInvExc() throws Exception {
        boolean expectedResult;
        try {
            service.getItem("002");//note slot 002 has an inventory of 0
            expectedResult = false;
            //fail("Expected NoItemInventoryException");
        } catch (NoItemInventoryException e) {
            expectedResult=true;
            return;
        }
        assertTrue(expectedResult);
    }
 
    
    
     /**
     * Test of getAllVendingItems method, of class VendingServiceLayer.
     */
    @Test
    public void testGetAllVendingItems() {
        //PASS THRU
        assertEquals(2, service.getAllVendingItems().size());
    }

    /**
     * Test of decrementInventory method, of class VendingServiceLayer.
     */
    @Test
    public void testDecrementInventory() throws Exception {
        VendingItem vendItem = service.getItem("001");
        assertEquals(service.decrementInventory(vendItem).getNumInInventory(), 5);
    }

    /**
     * Test of calcChange method, of class VendingServiceLayer.
     */
    @Test
    public void testCalcChange() throws Exception {
        BigDecimal cost = new BigDecimal(1.0);
        BigDecimal userMoney = new BigDecimal(2.47);

        Change myChange = service.calcChange(cost, userMoney);

        assertEquals(5, myChange.getNumQuarters());
        assertEquals(2, myChange.getNumDimes());
        assertEquals(2, myChange.getNumPennies());
    }
    
    @Test
    public void testCalcChangeOnCancel(){
       // BigDecimal cost = new BigDecimal(1.0);
        BigDecimal userMoney = new BigDecimal(2.47);

        Change myChange = service.calcChangeOnCancel(userMoney);

        assertEquals(9, myChange.getNumQuarters());
        assertEquals(2, myChange.getNumDimes());
        assertEquals(2, myChange.getNumPennies());
    }

    @Test
    public void testCalcChangeInsuffFunds() throws Exception {
        BigDecimal userMoney = new BigDecimal(1.0);
        BigDecimal cost = new BigDecimal(2.47);

        try{
        Change myChange = service.calcChange(cost, userMoney);
        fail("Expected InsufficientFundsException");
        } catch (InsufficientFundsException e){
            return;
        }
    }


    
}
