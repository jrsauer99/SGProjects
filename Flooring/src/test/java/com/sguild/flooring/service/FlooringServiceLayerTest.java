/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.service;

import com.sguild.flooring.dto.Order;
import com.sguild.flooring.dto.Product;
import com.sguild.flooring.dto.State;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
public class FlooringServiceLayerTest {

    FlooringServiceLayer service;

    public FlooringServiceLayerTest() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        service
                = ctx.getBean("serviceLayer", FlooringServiceLayer.class);

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

    /**
     * Test of getAllOrdersOnDate method, of class FlooringServiceLayer.
     */
    @Test
    public void testGetAllOrdersOnDate() throws Exception {
        ArrayList<Order> testedResultList = new ArrayList<>(service.getAllOrdersOnDate(LocalDate.now()));

        assertEquals(2, testedResultList.size());
    }

    @Test
    public void testGetAllOrdersOnDateEXCEPTION() throws Exception {
        LocalDate futureDate = LocalDate.now().plusDays(500);
        try {
            service.getAllOrdersOnDate(futureDate);
            fail("Expected OrderDateDoesNotExistException");
        } catch (OrderDateDoesNotExistException e) {
            return;
        }
    }

    /**
     * Test of getNextOrderNum method, of class FlooringServiceLayer.
     */
    @Test
    public void testGetNextOrderNum() {
        assertTrue(4 == service.getNextOrderNum());
    }

    /**
     * Test of getAllStates method, of class FlooringServiceLayer.
     */
    @Test
    public void testGetAllStates() {
        assertTrue(service.getAllStates().size() == 1);

        ArrayList<State> stateList = new ArrayList<>(service.getAllStates());
        State state = stateList.get(0);

        assertTrue(state.getStateName().equals("GA"));
    }

    /**
     * Test of getAllProducts method, of class FlooringServiceLayer.
     */
    @Test
    public void testGetAllProducts() {
        assertTrue(service.getAllProducts().size() == 1);

        ArrayList<Product> productList = new ArrayList<>(service.getAllProducts());
        Product product = productList.get(0);

        assertTrue(product.getProductName().equals("Wood"));
    }

    /**
     * Test of performOrderCalcs method, of class FlooringServiceLayer.
     */
    @Test
    public void testPerformOrderCalcs() {
        Order daoOrder = service.getOrder(1);
        daoOrder = service.performOrderCalcs(daoOrder);
        BigDecimal matCost = daoOrder.getMatCost();
        BigDecimal labCost = daoOrder.getLaborCost();
        BigDecimal totalCost = daoOrder.getTotalCost();
        BigDecimal totalTax = daoOrder.getTotalTax();

        assertEquals(matCost, new BigDecimal(1000).setScale(2, RoundingMode.HALF_UP));
        assertEquals(labCost, new BigDecimal(1150).setScale(2, RoundingMode.HALF_UP));
        assertEquals(totalTax, new BigDecimal(129).setScale(2, RoundingMode.HALF_UP));
        assertEquals(totalCost, new BigDecimal(2279).setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * Test of addOrder method, of class FlooringServiceLayer.
     */
    @Test
    public void testAddOrder() {
        //PASS THRU, TESTED IN DAO
    }

    /**
     * Test of getOrder method, of class FlooringServiceLayer.
     */
    @Test
    public void testGetOrder() {
        //PASS THRU, TESTED IN DAO
        Order order = service.getOrder(1);
        assertNotNull(order);

        order = service.getOrder(113);
        assertNull(order);
    }

    /**
     * Test of getAllOrderDates method, of class FlooringServiceLayer.
     */
    
    @Test
    public void testGetAllOrderDates() {
        ArrayList<LocalDate> testedResultList = new ArrayList<>(service.getAllOrderDates());
        //TEST SORTING
        assertTrue(testedResultList.get(0).equals(LocalDate.now().minusDays(100)));
        assertTrue(testedResultList.get(1).equals(LocalDate.now()));
        assertTrue(testedResultList.get(2).equals(LocalDate.now()));
        //TEST SIZE
        assertEquals(3, testedResultList.size());
    }

    @Test
    public void testValidateOrderExists() {
        ArrayList<Order> allOrders = new ArrayList<>(service.getAllOrders());
        String message = "";

        try {//TEST BAD ORDER NUM
            service.validateOrderExists(LocalDate.now(), allOrders.get(0).getOrderNum() + 5000);
            fail("Expected OrderDoesNotExistException");
        } catch (OrderDoesNotExistException | OrderDateDoesNotExistException | OrderDateComboDoesNotExistException e) {
            message = e.getMessage();
            return;
        }

        assertEquals(message, "ERROR: This order does not exist in our system!\n");

        try {//TEST BAD ORDER DAT NUM COMBO (NUMBER EXISTS BUT NOT AT THE DATE)
            service.validateOrderExists(LocalDate.now().plusDays(1), allOrders.get(0).getOrderNum());
            fail("Expected OrderDoesNotExistException");
        } catch (OrderDoesNotExistException | OrderDateDoesNotExistException | OrderDateComboDoesNotExistException e) {
            message = e.getMessage();
            return;
        }

        assertEquals(message, "ERROR: This order date combination does not exist in our system!\n");

    }

    /**
     * Test of getAllOrderNums method, of class FlooringServiceLayer.
     */
    @Test
    public void testGetAllOrderNums() {
        ArrayList<Order> allOrderNums = new ArrayList<>(service.getAllOrders());
        allOrderNums = service.getAllOrderNums();
        //TEST SORTING
        assertEquals(1, allOrderNums.get(0));
        assertEquals(2, allOrderNums.get(1));
        assertEquals(3, allOrderNums.get(2));
    }

    /**
     * Test of editOrder method, of class FlooringServiceLayer.
     */
    @Test
    public void testEditOrder() {
        Order order;
        order = service.getOrder(1);

        LocalDate ld = LocalDate.now();
        String date = ld.toString();

        //CHECK CUSTOM NAME, AREA, DATE
        //CHECK NEW STATE, DEFAULT TAX RATE
        //CHECK NEW PRODUCT, DEFAULT MAT AND LAB COST
        HashMap<String, String> editOrderInfo = new HashMap<>();
        editOrderInfo.put("NAME", "Bob"); //LEAVE NAME TH SAME
        editOrderInfo.put("AREA", Integer.toString(150)); //EDIT THE AREA
        editOrderInfo.put("DATE", date);
        editOrderInfo.put("STATE", "GA");
        editOrderInfo.put("TAX_RATE", "");
        editOrderInfo.put("PRODUCT", "Wood");
        editOrderInfo.put("MAT_COST", "");
        editOrderInfo.put("LAB_COST", "");
        editOrderInfo.put("ORIG_NUM", order.getOrderNum().toString());
        editOrderInfo.put("ORIG_NAME", order.getCustomerName());
        editOrderInfo.put("ORIG_AREA", order.getArea().toString());
        editOrderInfo.put("ORIG_DATE", order.getOrderDate().toString());
        editOrderInfo.put("ORIG_STATE", order.getState());
        editOrderInfo.put("ORIG_TAX_RATE", order.getTaxRate().toString());
        editOrderInfo.put("ORIG_PRODUCT", order.getProduct());
        editOrderInfo.put("ORIG_MAT_COST", order.getMatCostSqFt().toString());
        editOrderInfo.put("ORIG_LAB_COST", order.getLaborCostSqFt().toString());
        editOrderInfo.put("GEN_NEW_NUM", Integer.toString(2));
        Order editedOrder = service.editOrder(editOrderInfo);

        assertTrue(editedOrder.getCustomerName().equals("Bob"));
        assertTrue(editedOrder.getArea().equals(new BigDecimal("150")));
        assertTrue(editedOrder.getOrderDate().equals(ld));
        assertTrue(editedOrder.getState().equals("GA"));
        assertTrue(editedOrder.getTaxRate().equals(new BigDecimal(11)));
        assertTrue(editedOrder.getProduct().equals("Wood"));
        assertTrue(editedOrder.getMatCostSqFt().equals(new BigDecimal(5)));
        assertTrue(editedOrder.getLaborCostSqFt().equals(new BigDecimal(23)));

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //EDIT STATE, CUSTOM TAX RATE
        //EDIT PRODUCT, CUSTOM MAT COST, DEFAULT LAB COST
        //make a temp Hashmap to pass to edit order method
        HashMap<String, String> editOrderInfo2 = new HashMap<>();
        editOrderInfo2.put("NAME", ""); //LEAVE NAME TH SAME
        editOrderInfo2.put("AREA", ""); //EDIT THE AREA
        editOrderInfo2.put("DATE", "");
        editOrderInfo2.put("STATE", "GA");
        editOrderInfo2.put("TAX_RATE", "5");
        editOrderInfo2.put("PRODUCT", "Wood");
        editOrderInfo2.put("MAT_COST", "12");
        editOrderInfo2.put("LAB_COST", "");
        editOrderInfo2.put("ORIG_NUM", order.getOrderNum().toString());
        editOrderInfo2.put("ORIG_NAME", order.getCustomerName());
        editOrderInfo2.put("ORIG_AREA", order.getArea().toString());
        editOrderInfo2.put("ORIG_DATE", order.getOrderDate().toString());
        editOrderInfo2.put("ORIG_STATE", order.getState());
        editOrderInfo2.put("ORIG_TAX_RATE", order.getTaxRate().toString());
        editOrderInfo2.put("ORIG_PRODUCT", order.getProduct());
        editOrderInfo2.put("ORIG_MAT_COST", order.getMatCostSqFt().toString());
        editOrderInfo2.put("ORIG_LAB_COST", order.getLaborCostSqFt().toString());
        editOrderInfo2.put("GEN_NEW_NUM", Integer.toString(2));
        Order editedOrder2 = service.editOrder(editOrderInfo2);

        assertTrue(editedOrder2.getCustomerName().equals(order.getCustomerName()));
        assertTrue(editedOrder2.getArea().equals(order.getArea()));
        assertTrue(editedOrder2.getOrderDate().equals(order.getOrderDate()));
        assertTrue(editedOrder2.getState().equals("GA"));
        assertTrue(editedOrder2.getTaxRate().equals(new BigDecimal(5)));
        assertTrue(editedOrder2.getProduct().equals("Wood"));
        assertTrue(editedOrder2.getMatCostSqFt().equals(new BigDecimal(12)));
        assertTrue(editedOrder2.getLaborCostSqFt().equals(new BigDecimal(23)));

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //CUSTOM TAX RATE ONLY
        //EDIT PROD DEFAULT MAT COST CUSTOM LAB COST
        HashMap<String, String> editOrderInfo3 = new HashMap<>();
        editOrderInfo3.put("NAME", ""); //LEAVE NAME TH SAME
        editOrderInfo3.put("AREA", ""); //EDIT THE AREA
        editOrderInfo3.put("DATE", "");
        editOrderInfo3.put("STATE", "");
        editOrderInfo3.put("TAX_RATE", "7.5");
        editOrderInfo3.put("PRODUCT", "Wood");
        editOrderInfo3.put("MAT_COST", "");
        editOrderInfo3.put("LAB_COST", "99");
        editOrderInfo3.put("ORIG_NUM", order.getOrderNum().toString());
        editOrderInfo3.put("ORIG_NAME", order.getCustomerName());
        editOrderInfo3.put("ORIG_AREA", order.getArea().toString());
        editOrderInfo3.put("ORIG_DATE", order.getOrderDate().toString());
        editOrderInfo3.put("ORIG_STATE", order.getState());
        editOrderInfo3.put("ORIG_TAX_RATE", order.getTaxRate().toString());
        editOrderInfo3.put("ORIG_PRODUCT", order.getProduct());
        editOrderInfo3.put("ORIG_MAT_COST", order.getMatCostSqFt().toString());
        editOrderInfo3.put("ORIG_LAB_COST", order.getLaborCostSqFt().toString());
        editOrderInfo3.put("GEN_NEW_NUM", Integer.toString(2));
        Order editedOrder3 = service.editOrder(editOrderInfo3);

        assertTrue(editedOrder3.getCustomerName().equals(order.getCustomerName()));
        assertTrue(editedOrder3.getArea().equals(order.getArea()));
        assertTrue(editedOrder3.getOrderDate().equals(order.getOrderDate()));
        assertTrue(editedOrder3.getState().equals("KY"));
        assertTrue(editedOrder3.getTaxRate().equals(new BigDecimal(7.5)));
        assertTrue(editedOrder3.getProduct().equals("Wood"));
        assertTrue(editedOrder3.getMatCostSqFt().equals(new BigDecimal(5)));
        assertTrue(editedOrder3.getLaborCostSqFt().equals(new BigDecimal(99)));

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //CUSTOM LAB COST AND MAT COST, PRODUCT NOT EDITED
        HashMap<String, String> editOrderInfo4 = new HashMap<>();
        editOrderInfo4.put("NAME", "");
        editOrderInfo4.put("AREA", "");
        editOrderInfo4.put("DATE", "");
        editOrderInfo4.put("STATE", "");
        editOrderInfo4.put("TAX_RATE", "");
        editOrderInfo4.put("PRODUCT", "");
        editOrderInfo4.put("MAT_COST", "58");
        editOrderInfo4.put("LAB_COST", "99");
        editOrderInfo4.put("ORIG_NUM", order.getOrderNum().toString());
        editOrderInfo4.put("ORIG_NAME", order.getCustomerName());
        editOrderInfo4.put("ORIG_AREA", order.getArea().toString());
        editOrderInfo4.put("ORIG_DATE", order.getOrderDate().toString());
        editOrderInfo4.put("ORIG_STATE", order.getState());
        editOrderInfo4.put("ORIG_TAX_RATE", order.getTaxRate().toString());
        editOrderInfo4.put("ORIG_PRODUCT", order.getProduct());
        editOrderInfo4.put("ORIG_MAT_COST", order.getMatCostSqFt().toString());
        editOrderInfo4.put("ORIG_LAB_COST", order.getLaborCostSqFt().toString());
        editOrderInfo4.put("GEN_NEW_NUM", Integer.toString(2));
        Order editedOrder4 = service.editOrder(editOrderInfo4);

        assertTrue(editedOrder4.getCustomerName().equals(order.getCustomerName()));
        assertTrue(editedOrder4.getArea().equals(order.getArea()));
        assertTrue(editedOrder4.getOrderDate().equals(order.getOrderDate()));
        assertTrue(editedOrder4.getState().equals(order.getState()));
        assertTrue(editedOrder4.getTaxRate().equals(order.getTaxRate()));
        assertTrue(editedOrder4.getProduct().equals(order.getProduct()));
        assertTrue(editedOrder4.getMatCostSqFt().equals(new BigDecimal(58)));
        assertTrue(editedOrder4.getLaborCostSqFt().equals(new BigDecimal(99)));
    }

    /**
     * Test of removeOrder method, of class FlooringServiceLayer.
     */
    @Test
    public void testRemoveOrder() {
        //PASS THRU, TESTED IN DAO
    }

    /**
     * Test of getAllOrders method, of class FlooringServiceLayer.
     */
    @Test//COULD COME BACK AN TEST THAT ITS SORTED
    public void testGetAllOrders() {
        ArrayList<Order> allOrders = new ArrayList<>(service.getAllOrders());
        assertEquals(3, allOrders.size());
        //TEST SORTING
        assertTrue(allOrders.get(0).getOrderNum().equals(1));
        assertTrue(allOrders.get(1).getOrderNum().equals(2));
        assertTrue(allOrders.get(2).getOrderNum().equals(3));
    }

    /**
     * Test of saveOrders method, of class FlooringServiceLayer.
     */
    @Test
    public void testSaveOrders() throws Exception {
    }

    /**
     * Test of loadData method, of class FlooringServiceLayer.
     */
    @Test
    public void testLoadData() throws Exception {
    }

}
