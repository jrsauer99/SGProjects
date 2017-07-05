/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.ui;

import com.sguild.flooring.dto.Order;
import com.sguild.flooring.dto.Product;
import com.sguild.flooring.dto.State;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author apprentice
 */
public class FlooringView {

    UserIO io;

    public FlooringView(UserIO io) {
        this.io = io;
    }

    public void displayBanner(String bannerMessage) {
        io.print("======== " + bannerMessage + " ========");
    }

    public int printMainMenuAndGetSelection() {
        io.print("");
        io.print("============ MAIN MENU ============");
        io.print("1. Display Orders");
        io.print("2. Add Orders");
        io.print("3. Edit Order");
        io.print("4. Remove Order");
        io.print("5. Save Changes");
        io.print("6. List All Orders");
        io.print("7. Exit");

        return io.readInt("Please choose from the selections above", 1, 7);
    }

    public LocalDate userInputDate() {
        return io.readLocalDate("Enter the date you would like to search (MM-DD-YYYY)");
    }

    public void displayOrdersOnDate(ArrayList<Order> orderList, LocalDate orderDate) {
        io.print("");
        io.print("======= ORDERS (" + orderDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + ") =======");
        for (Order order : orderList) {
            io.print("ORDER: " + order.getOrderNum() + " | NAME: " + order.getCustomerName() + " | STATE: " + order.getState()
                    + " | TAX RATE: " + order.getTaxRate() + "%" + " | PRODUCT: " + order.getProduct()
                    + " | AREA: " + order.getArea() + " SQ-FT" + " | MATERIAL COST: $" + order.getMatCost() + " | LABOR COST: $" + order.getLaborCost()
                    + " | TOTAL TAX: $" + order.getTotalTax() + " | ORDER TOTAL: $" + order.getTotalCost());
        }
        io.print("");
    }

    public Order getNewOrderInfo(int orderNum, ArrayList stateList, ArrayList productList) {
        ArrayList<State> checkStateList = new ArrayList<>(stateList);
        ArrayList<Product> checkProductList = new ArrayList<>(productList);
        boolean stateIsValid = false;
        boolean productIsValid = false;
        boolean nameIsBlank = true;

        String customerName = io.readString("Please enter Customer Name:");

        do {
            if (customerName.trim().length() == 0) {
                io.print("Customer name required!");
                customerName = io.readString("Please re-enter Customer Name:");
                nameIsBlank = true;
            } else {
                nameIsBlank = false;
            }
        } while (nameIsBlank);

        BigDecimal area = io.readPositiveBigDecimal("Please enter the area (SQ-FT):");
        LocalDate date = io.readLocalDate("Please enter the Order date:");

        Order order = new Order(orderNum);
        order.setCustomerName(customerName);
        order.setArea(area);
        order.setOrderDate(date);

        //USER INPUT STATE
        String userState = io.readString("Enter the state abbreviation (Example: KY)");
        while (!stateIsValid) {
            try {
                validateUserStateExists(userState, checkStateList);
                stateIsValid = true;
            } catch (FlooringUserInputException e) {
                this.displayErrorMessage(e.getMessage());
                stateIsValid = false;
                io.print("VALID STATES:");
                for (State s : checkStateList) {
                    io.print(s.getStateName());
                }
                userState = io.readString("Enter the State abreviation from the list above:");
            }
        }
        String userStateValid = userState;
        State state = checkStateList
                .stream()
                .filter(s -> s.getStateName().toLowerCase().equals(userStateValid.toLowerCase()))
                .findFirst().get();
        order.setState(state.getStateName());
        order.setTaxRate(state.getTaxRate());

        //USER INPUT PRODUCT
        String userProduct = io.readString("Enter the product type:");
        while (!productIsValid) {
            try {
                validateUserProductExists(userProduct, checkProductList);
                productIsValid = true;
            } catch (FlooringUserInputException e) {
                this.displayErrorMessage(e.getMessage());
                productIsValid = false;
                io.print("VALID PRODUCTS:");
                for (Product p : checkProductList) {
                    io.print(p.getProductName());
                }
                userProduct = io.readString("Enter the product from the list above:");
            }
        }
        String validUserProduct = userProduct;
        Product product = checkProductList
                .stream()
                .filter(s -> s.getProductName().toLowerCase().equals(validUserProduct.toLowerCase()))
                .findFirst().get();
        order.setProduct(product.getProductName());
        order.setMatCostSqFt(product.getMatCostSqFt());
        order.setLaborCostSqFt(product.getLaborCostSqFt());

        return order;
    }

    public int displayOrderOpt2(Order order, String opt1, String opt2) {
        this.displayOrder(order);
        io.print("");
        io.print("1. " + opt1);
        io.print("2. " + opt2);
        int userVerify = io.readInt("Please choose from the options above.", 1, 2);
        return userVerify;
    }

    public int displayOrderOpt3(Order order, String opt1, String opt2, String opt3) {
        this.displayOrder(order);
        io.print("");
        io.print("1. " + opt1);
        io.print("2. " + opt2);
        io.print("3. " + opt3);

        int userVerify = io.readInt("Please choose from the options above.", 1, 3);
        return userVerify;
    }

    public int userInputOrderNum() {
        return io.readInt("Enter the Order Number:");
    }

    public int displayUserOptOut(String opt1, String opt2) {
        io.print("1. " + opt1);
        io.print("2. " + opt2);
        return io.readInt("Choose from the options above", 1, 2);
    }

    public HashMap userInputEditInfo(Order order, ArrayList stateList, ArrayList productList) {
        Boolean stateIsValid = false;
        Boolean productIsValid = false;
        ArrayList<State> checkStateList = new ArrayList<>(stateList);
        ArrayList<Product> checkProductList = new ArrayList<>(productList);

        io.print("NOTE: To leave a property unchanged, hit 'Enter'");
        String newName = io.readString("Enter customer name (WAS: " + order.getCustomerName() + "):");
        String newArea = io.readBigDecimalEditMode("Enter area (WAS: " + order.getArea() + " SQ-FT):");
        String newDate = io.readLocalDateEditMode("Enter order date (WAS: " + order.getOrderDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + ")");

        //GET STATE RELATED INFO
        String customState = "";
        String newState = io.readString("Enter the state abbreviation (WAS: " + order.getState() + ")");
        if (!newState.equals("")) {
            thisStLoop:
            while (!stateIsValid) {
                if (newState.equals("CUSTOM")) {
                    customState = io.readString("Enter your custom state name:");
                    stateIsValid = true;
                    break thisStLoop;
                }
                try {

                    validateUserStateExists(newState, checkStateList);
                    stateIsValid = true;
                } catch (FlooringUserInputException e) {
                    this.displayErrorMessage(e.getMessage());
                    stateIsValid = false;
                    io.print("VALID STATES:");
                    for (State s : checkStateList) {
                        io.print(s.getStateName());
                    }
                    io.print("CUSTOM");
                    newState = io.readString("Enter the State abreviation (or CUSTOM) from the list above:");
                }
            }
        }
        String newTaxRate = io.readBigDecimalEditMode("Enter new tax rate, (if state is edited above, re-enter only if different from new State default) (WAS " + order.getTaxRate() + "%)");

        //PRODUCT RELATED INFO
        String customProduct = "";
        String newProduct = io.readString("Enter the product type (WAS: " + order.getProduct() + ")");
        if (!newProduct.equals("")) {
            thisLoop:
            while (!productIsValid) {
                if (newProduct.equals("CUSTOM")) {
                    customProduct = io.readString("Enter your custom product name:");
                    productIsValid = true;
                    break thisLoop;
                }
                try {
                    validateUserProductExists(newProduct, checkProductList);
                    productIsValid = true;
                } catch (FlooringUserInputException e) {
                    this.displayErrorMessage(e.getMessage());
                    productIsValid = false;
                    io.print("VALID PRODUCTS:");
                    for (Product p : checkProductList) {
                        io.print(p.getProductName());
                    }
                    io.print("CUSTOM");
                    newProduct = io.readString("Enter the product (or CUSTOM) from the list above:");
                }
            }
        }
        String newMatCost = io.readBigDecimalEditMode("Enter new material cost (If product is edited above, re-enter only if different from new product default) (WAS $" + order.getMatCostSqFt() + " PER SQ-FT)");
        String newLabCost = io.readBigDecimalEditMode("Enter new labor cost (If product is edited above, re-enter only if different from new product default) (WAS $" + order.getLaborCostSqFt() + " PER SQ-FT)");

        io.print("Would you like to generate a new Order Number?");
        io.print("1. Yes");
        io.print("2. No");
        int newProductNum = io.readInt("Enter selection from options above.", 1, 2);

        HashMap<String, String> editOrderInfo = new HashMap<>();
        editOrderInfo.put("NAME", newName);
        editOrderInfo.put("AREA", newArea);
        editOrderInfo.put("DATE", newDate);
        editOrderInfo.put("STATE", newState.toUpperCase());
        editOrderInfo.put("TAX_RATE", newTaxRate);
        editOrderInfo.put("PRODUCT", newProduct);
        editOrderInfo.put("MAT_COST", newMatCost);
        editOrderInfo.put("LAB_COST", newLabCost);
        editOrderInfo.put("ORIG_NUM", order.getOrderNum().toString());
        editOrderInfo.put("ORIG_NAME", order.getCustomerName());
        editOrderInfo.put("ORIG_AREA", order.getArea().toString());
        editOrderInfo.put("ORIG_DATE", order.getOrderDate().toString());
        editOrderInfo.put("ORIG_STATE", order.getState());
        editOrderInfo.put("ORIG_TAX_RATE", order.getTaxRate().toString());
        editOrderInfo.put("ORIG_PRODUCT", order.getProduct());
        editOrderInfo.put("ORIG_MAT_COST", order.getMatCostSqFt().toString());
        editOrderInfo.put("ORIG_LAB_COST", order.getLaborCostSqFt().toString());
        editOrderInfo.put("GEN_NEW_NUM", Integer.toString(newProductNum));
        editOrderInfo.put("CUST_PRODUCT", customProduct);
        editOrderInfo.put("CUST_STATE", customState);
        return editOrderInfo;
    }

    public int askUserToVerify() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void diplayAllOrders(ArrayList<Order> orderList) {
        io.print("ALL ORDERS IN SYSTEM");
        for (Order order : orderList) {
            io.print("ORDER: " + order.getOrderNum() + " DATE:" + order.getOrderDate());
        }
    }

    public void displayErrorMessage(String message) {
        io.print(message);
    }

    private void validateUserStateExists(String userState, ArrayList stateList) throws FlooringUserInputException {
        ArrayList<State> checkStateList = new ArrayList<>(stateList);
        if (!checkStateList
                .stream()
                .anyMatch(o -> o.getStateName().toLowerCase().equals(userState.toLowerCase()))) {
            throw new FlooringUserInputException("The state input entered does not exist in the system.");
        }
    }

    private void validateUserProductExists(String userProduct, ArrayList productList) throws FlooringUserInputException {
        ArrayList<Product> checkProductList = new ArrayList<>(productList);
        if (!checkProductList
                .stream()
                .anyMatch(o -> o.getProductName().toLowerCase().equals(userProduct.toLowerCase()))) {
            throw new FlooringUserInputException("The product input entered does not exist in the system.");
        }
    }

    public void displayOrder(Order order) {
        io.print("\n======== ORDER " + order.getOrderNum() + " SUMMARY ========");
        io.print("ORDER NUMBER: " + order.getOrderNum());
        io.print("CUSTOMER NAME: " + order.getCustomerName());
        io.print("AREA: " + order.getArea() + " (SQ-FT)");
        io.print("DATE: " + order.getOrderDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
        io.print("STATE: " + order.getState());
        io.print("TAX RATE: " + order.getTaxRate() + "%");
        io.print("PRODUCT: " + order.getProduct());
        io.print("MATERIAL COST: $" + order.getMatCostSqFt() + " (PER SQ-FT)");
        io.print("LABOR COST: $" + order.getLaborCostSqFt() + " (PER SQ-FT)");
        io.print("TOTAL MATERIAL COST: $" + order.getMatCost());
        io.print("TOTAL LABOR COST: $" + order.getLaborCost());
        io.print("TOTAL TAX: $" + order.getTotalTax());
        io.print("ORDER TOTAL: $" + order.getTotalCost());
    }

    public BigDecimal overRideTotal() {
        return io.readUSCurrency("Enter the custom total amount:");
    }

}
