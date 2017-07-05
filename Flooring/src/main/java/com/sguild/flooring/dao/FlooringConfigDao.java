/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dao;

import java.util.HashMap;

/**
 *
 * @author apprentice
 */
public interface FlooringConfigDao {
    
    public HashMap loadConfig() throws FlooringFilePersistenceException, FlooringConfigFileException;

    public Boolean getIsTraining();
    
    public void setIsTraining(Boolean isTraining);
}
