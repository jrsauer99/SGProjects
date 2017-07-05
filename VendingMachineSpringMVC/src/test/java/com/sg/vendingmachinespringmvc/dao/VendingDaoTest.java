/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.dto.VendingItem;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
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
public class VendingDaoTest {

    private VendingDao dao;

  
    public VendingDaoTest() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("vendingDao", VendingDao.class);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<VendingItem> vendList = dao.getAllVendingItems();
        for (VendingItem vendItem : vendList) {
            dao.removeItem(vendItem.getSlotNum());
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddGetItem() {
        VendingItem item1 = new VendingItem("006");
        item1.setItemName("Coke");
        item1.setItemCost(new BigDecimal("1.5"));
        item1.setNumInInventory(8);
        dao.addItem(item1);

        VendingItem fromDao = dao.getItem(item1.getSlotNum());
        assertEquals(item1, fromDao);
    }

    /**
     * Test of getAllVendingItems method, of class VendingDao.
     */
    @Test
    public void testGetAllVendingItems() {

        VendingItem item1 = new VendingItem("001");
        item1.setItemName("Butterfinger");
        item1.setItemCost(new BigDecimal("3.5"));
        item1.setNumInInventory(8);
        dao.addItem(item1);

        VendingItem item2 = new VendingItem("002");
        item2.setItemName("Gum");
        item2.setItemCost(new BigDecimal("7.5"));
        item2.setNumInInventory(5);
        dao.addItem(item2);

        assertEquals(2, dao.getAllVendingItems().size());

    }

    @Test
    public void removeItem() {
        VendingItem item1 = new VendingItem("001");
        item1.setItemName("Butterfinger");
        item1.setItemCost(new BigDecimal("3.5"));
        item1.setNumInInventory(8);
        dao.addItem(item1);

        VendingItem item2 = new VendingItem("002");
        item2.setItemName("Gum");
        item2.setItemCost(new BigDecimal("7.5"));
        item2.setNumInInventory(5);
        dao.addItem(item2);

        dao.removeItem(item1.getSlotNum());
        assertEquals(1, dao.getAllVendingItems().size());
        assertNull(dao.getItem(item1.getSlotNum()));

        dao.removeItem(item2.getSlotNum());
        assertEquals(0, dao.getAllVendingItems().size());
        assertNull(dao.getItem(item2.getSlotNum()));
    }

}
