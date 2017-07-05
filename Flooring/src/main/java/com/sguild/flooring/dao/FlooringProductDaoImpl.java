/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dao;

import com.sguild.flooring.dto.Product;
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
public class FlooringProductDaoImpl implements FlooringProductDao {

    private Map<String, Product> productMap = new HashMap<>();
    private static final String PRODUCT_FILE = "Products.txt";
    private static final String DELIMETER = ",";

    @Override
    public Product addProduct(Product product) {
        Product newProduct = productMap.put(product.getProductName(), product); //ADDS newStudent to our map
        return newProduct;
    }

    @Override
    public Product getProduct(String productName) {
        return productMap.get(productName);
    }

    @Override
    public Product removeProduct(Product product) {
        Product removedProduct = productMap.remove(product.getProductName());
        return removedProduct;
    }

    @Override
    public ArrayList getAllProduct() {
        return new ArrayList<Product>(productMap.values());
    }

    @Override
    public ArrayList loadProduct() throws FlooringFilePersistenceException {
        Scanner scanner;
        ArrayList<String> productErrList = new ArrayList<>();
        
        try {
            //CREATE SCANNER FOR READING FILE
            scanner = new Scanner(new BufferedReader(new FileReader("products/" + PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringFilePersistenceException("-_- Could not load tax data into memory.", e);
        }
        String currentLine;
        String[] currentTokens;
        String fileName = PRODUCT_FILE;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMETER);

            try {
                validateFields(currentTokens, fileName);
                Product currentProduct = new Product();
                currentProduct.setProductName(currentTokens[0]);
                currentProduct.setMatCostSqFt(new BigDecimal(currentTokens[1]));
                currentProduct.setLaborCostSqFt(new BigDecimal(currentTokens[2]));
                productMap.put(currentProduct.getProductName(), currentProduct);
            } catch (FlooringFileFormatException e) {
                productErrList.add(currentLine);
                System.out.println(e.getMessage());
            }

        }
        scanner.close();
        return productErrList;
    }

    private void validateFields(String[] currentTokens, String fileName) throws FlooringFileFormatException {
        if (currentTokens.length != 3) {
            throw new FlooringFileFormatException("**ERROR** PRODUCT: " + currentTokens[0] + " had incorrect number of data fields.  Product not loaded.");
        }
        try {
            BigDecimal test = new BigDecimal(currentTokens[1]);
            BigDecimal test2 = new BigDecimal(currentTokens[2]);
        } catch (NumberFormatException e) {
            throw new FlooringFileFormatException("**ERROR** PRODUCT: " + currentTokens[0] + " pricing rate could not be parsed to Big Decimal. Product not loaded. ");
        }

        if ((new BigDecimal(currentTokens[1])).compareTo(BigDecimal.ZERO) < 0
                || (new BigDecimal(currentTokens[2])).compareTo(BigDecimal.ZERO) < 0) {
            throw new FlooringFileFormatException("**ERROR: Pricing for " + currentTokens[0] + " was less than 0. Product not loaded.");
        }
    }

}
