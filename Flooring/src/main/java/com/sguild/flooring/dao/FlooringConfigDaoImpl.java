/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dao;

import com.sguild.flooring.controller.FlooringController;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author apprentice
 */
public class FlooringConfigDaoImpl implements FlooringConfigDao {
    private HashMap<String, String> configOptions = new HashMap<>();    
    private String configFile;// = "config.txt";
    private static final String DELIMETER = "::";
    private Boolean isTraining;
    
    public FlooringConfigDaoImpl(String configFile) {
        this.configFile = configFile;
        //this.isTraining = this.getIsTraining();
        try{
        this.loadConfig();
        }catch(FlooringFilePersistenceException | FlooringConfigFileException e){
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public HashMap loadConfig() throws FlooringFilePersistenceException, FlooringConfigFileException {
        Scanner scanner;

        try {
            //CREATE SCANNER FOR READING FILE
            scanner = new Scanner(new BufferedReader(new FileReader(configFile)));
        } catch (FileNotFoundException e) {
            throw new FlooringFilePersistenceException("-_- Could not load config data into memory.", e);
        }

        //SKIP THE FIRST 4 LINES
        scanner.nextLine();
        scanner.nextLine();
        scanner.nextLine();
        scanner.nextLine();

        String currentLine;
        String[] currentTokens;
        currentLine = scanner.nextLine();
        currentTokens = currentLine.split(DELIMETER);//GIVES 2 TOKENS
        
        configOptions.put("MODE",currentTokens[1]);
        
        if(!currentTokens[1].equals("p") && !currentTokens[1].equals("t")){
            throw new FlooringConfigFileException("-_- Config File Format Error.");
        }else
        if(currentTokens[1].equals("p")){
            this.isTraining = false;
        }else
        if((currentTokens[1].equals("t"))){
            this.isTraining = true;
        }
            
        scanner.close();
        return configOptions;

    }

    @Override
    public Boolean getIsTraining() {
        return isTraining;
    }

    @Override
    public void setIsTraining(Boolean isTraining) {
        this.isTraining = isTraining;
    }


}
