/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dao;

import com.sguild.flooring.dto.State;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author apprentice
 */
public class FlooringStateDaoImpl implements FlooringStateDao {

    private Map<String, State> stateMap = new HashMap<>();
    private static final String TAX_FILE = "Taxes.txt";
    private static final String DELIMETER = ",";

    @Override
    public State addState(State state) {
        State newState = stateMap.put(state.getStateName(), state); //ADDS newStudent to our map
        return newState;

    }

    @Override
    public State getState(String stateName) {
        return stateMap.get(stateName);
    }

    @Override
    public State removeState(State state) {
        State removedState = stateMap.remove(state.getStateName());
        return removedState;
    }

    @Override
    public ArrayList getAllState() {
        return new ArrayList<State>(stateMap.values());
    }

    @Override
    public ArrayList loadState() throws FlooringFilePersistenceException {
        Scanner scanner;
        ArrayList<String> stateErrList = new ArrayList<>();
        
        try {
            //CREATE SCANNER FOR READING FILE
            scanner = new Scanner(new BufferedReader(new FileReader("taxes/" + TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringFilePersistenceException("-_- Could not load tax data into memory.", e);
        }
        String currentLine;
        String[] currentTokens;
        String fileName = TAX_FILE;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMETER);

            try {
                validateFields(currentTokens, fileName);
                State currentState = new State();
                currentState.setStateName(currentTokens[0]);
                currentState.setTaxRate(new BigDecimal(currentTokens[1]));
                stateMap.put(currentState.getStateName(), currentState);
            } catch (FlooringFileFormatException e) {
                stateErrList.add(currentLine);
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
        return stateErrList;
    }

    private void validateFields(String[] currentTokens, String fileName) throws FlooringFileFormatException {
        if (currentTokens.length != 2) {
            throw new FlooringFileFormatException("**ERROR** STATE: " + currentTokens[0] + " had incorrect number of data fields.  State not loaded.");
        }
        try {
            BigDecimal test = new BigDecimal(currentTokens[1]);
        } catch (NumberFormatException e) {
            throw new FlooringFileFormatException("**ERROR** STATE: " + currentTokens[0] + " taxt rate could not be parsed to Big Decimal. State not loaded. ");
        }

        if ((new BigDecimal(currentTokens[1])).compareTo(BigDecimal.ZERO) < 0) {
            throw new FlooringFileFormatException("**ERROR: Tax rate for " + currentTokens[0] + " was less than 0. State not loaded.");
        }
    }
}
