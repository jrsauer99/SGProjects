/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dao;

import com.sguild.flooring.dto.Product;
import java.math.BigDecimal;
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
public class FlooringProductDaoTest {
    FlooringProductDao productDao = new FlooringProductDaoImpl();
    
    
    public FlooringProductDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Product> productList = productDao.getAllProduct();
        for (Product product:productList ){
            productDao.removeProduct(product);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addProduct method, of class FlooringProductDao.
     */
    @Test
    public void testAddGetProduct() {
        Product product1 = new Product();
        product1.setProductName("Carpet");
        product1.setMatCostSqFt(new BigDecimal(10));
        product1.setLaborCostSqFt(new BigDecimal(15.5));
        
        productDao.addProduct(product1);
        
        Product fromDao = productDao.getProduct("Carpet");
        
        assertEquals(product1,fromDao);
    }


    /**
     * Test of removeProduct method, of class FlooringProductDao.
     */
    @Test
    public void testRemoveProduct() {
        Product product1 = new Product();
        product1.setProductName("Carpet");
        product1.setMatCostSqFt(new BigDecimal(10));
        product1.setLaborCostSqFt(new BigDecimal(15.5));
        
        productDao.addProduct(product1);
        
         Product product2 = new Product();
        product2.setProductName("Laminate");
        product2.setMatCostSqFt(new BigDecimal(11));
        product2.setLaborCostSqFt(new BigDecimal(20));
        
        productDao.addProduct(product2);
        
        productDao.removeProduct(product1);
        assertEquals(1,productDao.getAllProduct().size());
        assertNull(productDao.getProduct("Carpet"));
        
        productDao.removeProduct(product2);
        assertEquals(0,productDao.getAllProduct().size());
        assertNull(productDao.getProduct("Laminate"));
        
        
        
    }

    /**f getAllProduct method, of class FlooringProductDao.
     */
    @Test
    public void testGetAllProduct() {
        Product product1 = new Product();
        product1.setProductName("Carpet");
        product1.setMatCostSqFt(new BigDecimal(10));
        product1.setLaborCostSqFt(new BigDecimal(15.5));
        
        productDao.addProduct(product1);
        
         Product product2 = new Product();
        product2.setProductName("Laminate");
        product2.setMatCostSqFt(new BigDecimal(11));
        product2.setLaborCostSqFt(new BigDecimal(20));
        
        productDao.addProduct(product2);
        
        assertEquals(2,productDao.getAllProduct().size());    
    }

    /**
     * Test of loadProduct method, of class FlooringProductDao.
     */
    @Test
    public void testLoadProduct() {
    }

    
}
