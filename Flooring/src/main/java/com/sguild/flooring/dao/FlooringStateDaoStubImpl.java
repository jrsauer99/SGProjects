/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dao;

import com.sguild.flooring.dto.State;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author apprentice
 */
public class FlooringStateDaoStubImpl implements FlooringStateDao {
    private State onlyState;
    private ArrayList<State> stateList = new ArrayList<>();
    
    public FlooringStateDaoStubImpl(){
        onlyState = new State();
        
        onlyState.setStateName("GA");
        onlyState.setTaxRate(new BigDecimal(11));
        
        stateList.add(onlyState);
    }
    
    @Override
    public State addState(State state) {
        if (onlyState.getStateName().equals(state.getStateName())) {
            return onlyState;
        } else {
            return null;
        }
    }

    @Override
    public State getState(String stateName) {
        if (onlyState.getStateName().equals(stateName)) {
            return onlyState;
        } else {
            return null;
        }
    }

    @Override
    public State removeState(State state) {
        if (onlyState.getStateName().equals(state.getStateName())) {
            return onlyState;
        } else {
            return null;
        }
    }

    @Override
    public ArrayList getAllState() {
        return stateList;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList loadState() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
