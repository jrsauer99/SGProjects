/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dao;

import com.sguild.flooring.dto.State;
import java.util.ArrayList;

/**
 *
 * @author apprentice
 */
public interface FlooringStateDao {
    public State addState(State state);
    
    public State getState(String stateName);
    
    public State removeState(State state);
    
    public ArrayList getAllState();
    
    public ArrayList loadState() throws FlooringFilePersistenceException;
    
}
