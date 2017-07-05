/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dao;

import com.sguild.flooring.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author apprentice
 */
public class FlooringOrderDaoImpl implements FlooringOrderDao {

    private HashMap<Integer, Order> allOrdersMap = new HashMap<>();
    private HashMap<String, ArrayList> readErrorMap = new HashMap<>();
    private static final String DELIMITER = ",";

    @Override
    public Order addOrder(Order order) {
        Order newOrder = allOrdersMap.put(order.getOrderNum(), order);
        return newOrder;
    }

    @Override
    public Order getOrder(int orderNum) {
        return allOrdersMap.get(orderNum);
    }

    @Override
    public Order removeOrder(Order order) {
        Order removedOrder = allOrdersMap.remove(order.getOrderNum());
        return removedOrder;
    }

    @Override
    public ArrayList getAllOrders() {
        return new ArrayList<>(allOrdersMap.values());
    }

    @Override
    public ArrayList getAllOrderDates() {
        Map<LocalDate, List<Order>> mapByDate = new HashMap<>();

        mapByDate = allOrdersMap.values()
                .stream()
                .collect(Collectors.groupingBy(Order::getOrderDate));

        return new ArrayList<>(mapByDate.keySet());
    }

    @Override
    public HashMap loadOrders() throws FlooringFilePersistenceException {
        ArrayList<Integer> orderNumList = new ArrayList<>(); //TRACK ALL ORDER NUMS READ IN TO CHECK FOR DUPLICATES
        ArrayList<String> fileFormatErrList = new ArrayList<>();//FOR STORING LIST OF ORDER STRINGS WITH FORMAT ERRORS
        ArrayList<Order> duplicateList = new ArrayList<>(); //FOR STORING LIST OF ORDERS THAT ARE DUP
        ArrayList<String> duplicateListStr = new ArrayList<>(); //FOR STORING LIST OF TOKEN STRING THAT ARE DUP
        ArrayList<String> duplicateListStrNew = new ArrayList<>();//FOR STORING LIST OF TOKEN STRING WITH NEW NUM
        ArrayList<String> fileNameErrList = new ArrayList<>();//FOR STORING LIST OF FILE NAMES WITH ERRORS

        final String DELIM_FILENAME = "_";
        final String DELIM_FILEEXT = ".";
        final String DELIMITER = ",";
        String currentLine;
        Boolean duplicate = false;

        File[] files = new File("orders/").listFiles(); //ARRAY OF FILE OBJECTS
        for (File file : files) { //FOR EACH FILE IN ARRAY
            Boolean validFileName = true;
            //GET DATE FROM FILE NAME
            if (file.isFile()) { //VERIFY THAT FILE IS A FILE, NOT A DIR
                String[] filenameTokens;
                String[] orderTokens;
                String fileDateString = "";
                LocalDate fileDate;
                int i = 1;

                //TRY INSANTIATIING NEW MAP FOR EACH FILE
                filenameTokens = file.getName().split(DELIM_FILENAME);
                //VALIDATE DATE FORMAT
                try {
                    validFileNameTokSize(filenameTokens, file.getName());
                    //REMOVE FILE EXTENSION
                    fileDateString = filenameTokens[1].substring(0, filenameTokens[1].indexOf(DELIM_FILEEXT));
                    validFileNameFormat(fileDateString, file.getName());
                } catch (FlooringFileNameException e) {
                    fileNameErrList.add(file.getName());
                    System.out.println(e.getMessage());
                    validFileName = false;

                }

                if (validFileName) {
                    fileDate = LocalDate.parse(fileDateString, DateTimeFormatter.ofPattern("MMddyyyy"));

                    //READ FILE
                    Scanner scanner;

                    try {
                        //CREATE SCANNER FOR READING FILE
                        scanner = new Scanner(new BufferedReader(new FileReader("orders/" + file.getName())));
                    } catch (FileNotFoundException e) {
                        throw new FlooringFilePersistenceException("-_- Could not load order data into memory.", e);
                    }

                    while (scanner.hasNextLine()) {
                        Boolean validFields = true;
                        currentLine = scanner.nextLine();

                        orderTokens = currentLine.split(DELIMITER);

                        //VALIDATE FORMAT OF ORDER DATA
                        try {
                            validateFields(orderTokens, file.getName());
                        } catch (FlooringFileFormatException e) {
                            fileFormatErrList.add(currentLine + " (" + file.getName() + ")");
                            System.out.println(e.getMessage());
                            validFields = false;
                        }
                        if (validFields) {
                            //GET ORDER NUM
                            Integer orderNum = Integer.parseInt(orderTokens[0]);

                            //CHECK ORDER NUM FOR DUPLICATES
                            if (orderNumList.contains(orderNum)) {
                                //THROW EXECPTION OR JUST RENUMBER AND LOG???
                                duplicate = true;
                            } else {
                                duplicate = false;
                            }
                            //ADD NEW ORDER NUM TO NUM LIST
                            orderNumList.add(orderNum);

                            //INSTANTIATE ORDER WITH ORDER NUMBER
                            Order order = new Order(orderNum);

                            //SET ORDER DATE
                            order.setOrderDate(fileDate);

                            //SET CUST NAME
                            order.setCustomerName(orderTokens[1]);

                            //SET STATE
                            order.setState(orderTokens[2]);
                            order.setTaxRate(new BigDecimal(orderTokens[3]));

                            //SET PRODUCT TYPE
                            order.setProduct(orderTokens[4]);
                            order.setMatCostSqFt(new BigDecimal(orderTokens[6]));
                            order.setLaborCostSqFt(new BigDecimal(orderTokens[7]));

                            //SET AREA
                            order.setArea(new BigDecimal(orderTokens[5]));

                            //SET TOTALS
                            order.setMatCost(new BigDecimal(orderTokens[8]));
                            order.setLaborCost(new BigDecimal(orderTokens[9]));
                            order.setTotalTax(new BigDecimal(orderTokens[10]));
                            order.setTotalCost(new BigDecimal(orderTokens[11]));
                            //IF DUPLCATE IS FALSE:
                            if (!duplicate) {
                                allOrdersMap.put(order.getOrderNum(), order);
                            } else {
                                //IF DUPLICAT IS TRUE
                                duplicateListStr.add(currentLine + " (" + file.getName() + ")");
                                duplicateList.add(order);
                            }
                        }

                    }
                    scanner.close();
                }
            }
        }
        //RENUMBER DUPLCATES AND MOVE TO MAIN MAP
        int i = 0;
        for (Order o : duplicateList) {
            //GET NEXT ORDER NUM
            Integer nextOrderNum = Collections.max(orderNumList) + 1;
            //ASSIGN TO ORDER IN LIST
            duplicateList.get(i).setOrderNum(nextOrderNum);
            //ADD ORDER TOKEN StrING TO LIST, APPEND NEW NUM
            String msg = duplicateListStr.get(i) + " NEW NUM: " + nextOrderNum;
            duplicateListStrNew.add(msg);
            allOrdersMap.put(nextOrderNum, duplicateList.get(i));
            orderNumList.add(nextOrderNum);
            i++;
        }

        readErrorMap.put("DUPLICATE", duplicateListStrNew);
        readErrorMap.put("FILE_FORMAT", fileFormatErrList);
        readErrorMap.put("FILE_NAME", fileNameErrList);
        return readErrorMap;
    }

    @Override
    public void saveOrders() throws FlooringFilePersistenceException {
        //CREATE NEW MAP TO GROUP ALL ORDERS BY DATE
        Map<LocalDate, List<Order>> mapByDate = new HashMap<>();
        final String DELIM_FILENAME = "_";
        final String DELIM_FILEEXT = ".";

        //COLLECT ORDERS BY DATE AND FILL NEW MAP, KEY:DATE VALUES:LIST OF ORDERS
        mapByDate = allOrdersMap.values()
                .stream()
                .collect(Collectors.groupingBy(Order::getOrderDate));

        //GET A KEYSET OF THE DATES
        Set<LocalDate> keysDate = mapByDate.keySet();

        //FOR EACH DATE IN THE KEY SET
        for (LocalDate kd : keysDate) {
            //PARSE kd TO STRING IN CORRECT FORMAT TO NAME FILES!
            String dateFileName = kd.format(DateTimeFormatter.ofPattern("MMddyyyy"));
            String fullFileName = "Orders_" + dateFileName + ".txt";

            //WRITE TO ONE DATE FILE
            PrintWriter out;

            try {
                out = new PrintWriter(new FileWriter("orders/" + fullFileName));
            } catch (IOException e) {
                throw new FlooringFilePersistenceException("Could not save order data.", e);
            }

            //GET THIS LIST OF ORDERS FOR THIS DATE
            List<Order> orderList = new ArrayList<>();
            orderList = mapByDate.get(kd);
            //SORT ORDERS IN LIST SO FILE IS IN ORDER
            Collections.sort(orderList,
                    (o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));

            //FOR EACH ORDER IN THIS ORDER LIST PRINT
            for (Order order : orderList) {
                String orderText;
                orderText = order.getOrderNum()
                        + "," + order.getCustomerName() + "," + order.getState()
                        + "," + order.getTaxRate() + "," + order.getProduct()
                        + "," + order.getArea() + "," + order.getMatCostSqFt()
                        + "," + order.getLaborCostSqFt() + "," + order.getMatCost()
                        + "," + order.getLaborCost() + "," + order.getTotalTax() + "," + order.getTotalCost();
                //WRITE ORDER TO FILE FOR THIS DATE
                out.println(orderText);
                out.flush();
            }
            out.close();
        }

        //AFTER WRITING TO FILES, DELETE ANY FILES THAT WERE NOT IN MAP
        File[] files = new File("orders/").listFiles();

        //CHECK EACH FILE IN DIR FOR MATCH IN KEYSDATE, IF NO MATCH, DELETE
        for (File file : files) {
            String[] filenameTokens = file.getName().split(DELIM_FILENAME);
            if (filenameTokens.length==2){
                
                String fileDateString = filenameTokens[1].substring(0, filenameTokens[1].indexOf(DELIM_FILEEXT));

                if (!keysDate
                        .stream()
                        .anyMatch(k -> k.format(DateTimeFormatter.ofPattern("MMddyyyy")).equals(fileDateString))){
                    file.delete();
                }
            }
        }
    }

    private void validateFields(String[] currentTokens, String fileName) throws FlooringFileFormatException {
        if (currentTokens.length != 12) {
            throw new FlooringFileFormatException("**ERROR** ORDER: " + currentTokens[0] + " had incorrect number of data fields.  Order not loaded.");
        }
        try {
            Integer test1 = Integer.parseInt(currentTokens[0]);
        } catch (NumberFormatException e) {
            throw new FlooringFileFormatException("**ERROR** ORDER: " + currentTokens[0] + " NAME: " + currentTokens[1] + " FILE: " + fileName + " Order num could not be parsed to int. Order not loaded. ");
        }

        try {
            BigDecimal test2 = new BigDecimal(currentTokens[3]);
            BigDecimal test3 = new BigDecimal(currentTokens[6]);
            BigDecimal test4 = new BigDecimal(currentTokens[7]);
            BigDecimal test5 = new BigDecimal(currentTokens[5]);
            BigDecimal test6 = new BigDecimal(currentTokens[8]);
            BigDecimal test7 = new BigDecimal(currentTokens[9]);
            BigDecimal test8 = new BigDecimal(currentTokens[10]);
            BigDecimal test9 = new BigDecimal(currentTokens[11]);

        } catch (NumberFormatException e) {
            throw new FlooringFileFormatException("**ERROR** ORDER: " + currentTokens[0] + " NAME: " + currentTokens[1] + " FILE: " + fileName + " Order had fields that could not be parsed to Big Decimal. Order not loaded. ");

        }

        if (currentTokens[0].trim().equals("") || currentTokens[1].trim().equals("") || currentTokens[2].trim().equals("")
                || currentTokens[3].trim().equals("") || currentTokens[4].trim().equals("") || currentTokens[5].trim().equals("")
                || currentTokens[6].trim().equals("") || currentTokens[7].trim().equals("") || currentTokens[8].trim().equals("")
                || currentTokens[9].trim().equals("") || currentTokens[10].trim().equals("") || currentTokens[11].trim().equals("")) {
            throw new FlooringFileFormatException("**ERROR** ORDER: " + currentTokens[0] + " NAME: " + currentTokens[1] + " FILE: " + fileName + " Order had fields that were blank. Order not loaded. ");
        }

        if ((new BigDecimal(currentTokens[3])).compareTo(BigDecimal.ZERO) < 0
                || (new BigDecimal(currentTokens[5])).compareTo(BigDecimal.ZERO) < 0
                || (new BigDecimal(currentTokens[6])).compareTo(BigDecimal.ZERO) < 0
                || (new BigDecimal(currentTokens[7])).compareTo(BigDecimal.ZERO) < 0) {
            throw new FlooringFileFormatException("**ERROR: Pricing/Tax/Area for " + currentTokens[0] + " was less than 0. Product not loaded.");
        }

    }

    private void validFileNameFormat(String fileDateString, String fileName) throws FlooringFileNameException {
        try {

            LocalDate fileDate = LocalDate.parse(fileDateString, DateTimeFormatter.ofPattern("MMddyyyy"));
        } catch (DateTimeParseException e) {
            throw new FlooringFileNameException("**ERROR** Filename format error. File not loaded: " + fileName);
        }

    }

    private void validFileNameTokSize(String[] fileTokens, String fileName) throws FlooringFileNameException {
        if (fileTokens.length != 2) {
            throw new FlooringFileNameException("**ERROR** Filename format error. File not loaded: " + fileName);
        }
    }
}
