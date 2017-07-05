/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dao;

import com.sguild.flooring.dto.State;
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
public class FlooringStateDaoTest {
    FlooringStateDao stateDao = new FlooringStateDaoImpl();
    
    public FlooringStateDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<State> stateList = stateDao.getAllState();
        for (State state:stateList ){
            stateDao.removeState(state);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addState method, of class FlooringStateDao.
     */
    @Test
    public void testAddGetState() {
        State state1 = new State();
         state1.setStateName("KY");
         state1.setTaxRate(new BigDecimal(10));
        
        stateDao.addState(state1);
        
        State fromDao = stateDao.getState("KY");
        
        assertEquals(state1,fromDao);
        
    }


    /**
     * Test of removeState method, of class FlooringStateDao.
     */
    @Test
    public void testRemoveState() {
        State state1 = new State();
         state1.setStateName("KY");
         state1.setTaxRate(new BigDecimal(10));
        
        stateDao.addState(state1);
        
        State state2 = new State();
         state2.setStateName("CA");
         state2.setTaxRate(new BigDecimal(12));
        
        stateDao.addState(state2);
        
        stateDao.removeState(state1);
        assertEquals(1,stateDao.getAllState().size());
        assertNull(stateDao.getState("KY"));
        
        stateDao.removeState(state2);
        assertEquals(0,stateDao.getAllState().size());
        assertNull(stateDao.getState("CA"));
        
        
    }

    /**
     * Test of getAllState method, of class FlooringStateDao.
     */
    @Test
    public void testGetAllState() {
        State state1 = new State();
         state1.setStateName("KY");
         state1.setTaxRate(new BigDecimal(10));
        
        stateDao.addState(state1);
        
        State state2 = new State();
         state2.setStateName("CA");
         state2.setTaxRate(new BigDecimal(12));
        
        stateDao.addState(state2);
        
        assertEquals(2,stateDao.getAllState().size()); 
    }

    /**
     * Test of loadState method, of class FlooringStateDao.
     */
    @Test
    public void testLoadState() {
    }

}
