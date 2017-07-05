/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dao;

import com.sguild.flooring.dto.Product;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author apprentice
 */
public class FlooringProductDaoStubImpl implements FlooringProductDao {
    private Product onlyProduct;
    private ArrayList<Product> productList = new ArrayList<>();
    
    //ADD CONSTRUCTOR
    public FlooringProductDaoStubImpl(){
        onlyProduct = new Product();
        onlyProduct.setProductName("Wood");
        onlyProduct.setMatCostSqFt(new BigDecimal(5));
        onlyProduct.setLaborCostSqFt(new BigDecimal(23));
        
        productList.add(onlyProduct);
    }
    
    @Override
    public Product addProduct(Product product) {
        if (onlyProduct.getProductName().equals(product.getProductName())) {
            return onlyProduct;
        } else {
            return null;
        }
    }

    @Override
    public Product getProduct(String productName) {
        if (onlyProduct.getProductName().equals(productName)) {
            return onlyProduct;
        } else {
            return null;
        }
    }

    @Override
    public Product removeProduct(Product product) {
        if (onlyProduct.getProductName().equals(product.getProductName())) {
            return onlyProduct;
        } else {
            return null;
        }
    }

    @Override
    public ArrayList getAllProduct() {
        return productList;
    }

    @Override
    public ArrayList loadProduct() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
