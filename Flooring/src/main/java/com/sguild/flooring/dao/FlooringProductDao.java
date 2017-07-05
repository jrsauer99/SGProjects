/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dao;

import com.sguild.flooring.dto.Product;
import java.util.ArrayList;

/**
 *
 * @author apprentice
 */
public interface FlooringProductDao {
    public Product addProduct(Product product);
    
    public Product getProduct(String productName);
    
    public Product removeProduct(Product product);
    
    public ArrayList getAllProduct();
    
    public ArrayList loadProduct() throws FlooringFilePersistenceException;
    
}
