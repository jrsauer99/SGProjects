/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.service;

import com.sguild.flooring.dao.FlooringConfigDao;
import com.sguild.flooring.dao.FlooringFilePersistenceException;
import com.sguild.flooring.dao.FlooringOrderDao;
import com.sguild.flooring.dao.FlooringProductDao;
import com.sguild.flooring.dao.FlooringStateDao;
import com.sguild.flooring.dto.Order;
import com.sguild.flooring.dto.Product;
import com.sguild.flooring.dto.State;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 *
 * @author apprentice
 */
public class FlooringServiceLayerImpl implements FlooringServiceLayer {

    private FlooringOrderDao orderDao;
    private FlooringProductDao productDao;
    private FlooringStateDao stateDao;
    private FlooringConfigDao configDao;
    private Boolean isTraining;
    
    public FlooringServiceLayerImpl(FlooringOrderDao orderDao, FlooringProductDao productDao, FlooringStateDao stateDao, FlooringConfigDao configDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.stateDao = stateDao;
        this.configDao = configDao;
    }

    @Override
    public ArrayList<Order> getAllOrdersOnDate(LocalDate orderDate) throws OrderDateDoesNotExistException {
        validateOrderDateExists(orderDate);
        ArrayList<Order> orderList = new ArrayList<>(orderDao.getAllOrders());
        ArrayList<Order> orderListOnDate;

        orderListOnDate = orderList
                .stream()
                .filter(o -> o.getOrderDate().equals(orderDate))
                .collect(Collectors.toCollection(ArrayList::new));

        Collections.sort(orderListOnDate,
                (o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));

        return orderListOnDate;
    }

    @Override
    public void validateOrderDateExists(LocalDate orderDate) throws OrderDateDoesNotExistException {
        ArrayList<Order> orderList = new ArrayList<>(orderDao.getAllOrders());
        if (!orderList
                .stream()
                .anyMatch(o -> o.getOrderDate().equals(orderDate))) {
            throw new OrderDateDoesNotExistException("ERROR: This order date does not exist in our system!\n");
        }
    }

    @Override
    public int getNextOrderNum() {
        ArrayList<Order> orderList = new ArrayList<>(orderDao.getAllOrders());
        ArrayList<Integer> orderNumList = new ArrayList<>();
        for (Order order : orderList) {
            orderNumList.add(order.getOrderNum());
        }
        Integer maxOrderNum = Collections.max(orderNumList);
        return maxOrderNum + 1;
    }

    @Override
    public ArrayList getAllStates() {
        return stateDao.getAllState();
    }

    @Override
    public ArrayList getAllProducts() {
        return productDao.getAllProduct();
    }

    @Override
    public Order performOrderCalcs(Order order) {
        BigDecimal matCost;
        BigDecimal laborCost;
        BigDecimal subTotal;
        BigDecimal totalTax;
        BigDecimal orderTotal;

        matCost = order.getMatCostSqFt().multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP);
        laborCost = order.getLaborCostSqFt().multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP);
        subTotal = matCost.add(laborCost).setScale(2, RoundingMode.HALF_UP);
        totalTax = order.getTaxRate().divide(new BigDecimal(100)).multiply(subTotal).setScale(2, RoundingMode.HALF_UP);
        orderTotal = subTotal.add(totalTax).setScale(2, RoundingMode.HALF_UP);

        order.setMatCost(matCost);
        order.setLaborCost(laborCost);
        order.setTotalTax(totalTax);
        order.setTotalCost(orderTotal);

        return order;
    }

    @Override
    public Order addOrder(Order order) {
        return orderDao.addOrder(order);
    }

    @Override
    public Order getOrder(Integer orderNum) {
        return orderDao.getOrder(orderNum);
    }

    @Override
    public void validateOrderExists(LocalDate orderDate, int orderNum) throws OrderDateDoesNotExistException, OrderDoesNotExistException, OrderDateComboDoesNotExistException {
        ArrayList<Order> orderList = new ArrayList<>(orderDao.getAllOrders());

        if (!orderList
                .stream()
                .anyMatch(o -> o.getOrderNum().equals(orderNum))) {
            throw new OrderDoesNotExistException("ERROR: This order does not exist in our system!\n");
        } else if (!orderList
                .stream()
                .filter(o -> o.getOrderNum().equals(orderNum))
                .anyMatch(o -> o.getOrderDate().equals(orderDate))) {
            throw new OrderDateComboDoesNotExistException("ERROR: This order date combination does not exist in our system!\n");
        }
    }
    
    @Override
    public ArrayList getAllOrderDates() {
        ArrayList<LocalDate> dateList = new ArrayList<>(orderDao.getAllOrderDates());
        Collections.sort(dateList);

        return dateList;
    }

    @Override
    public ArrayList getAllOrderNums() {
        ArrayList<Order> orderList = new ArrayList<>(orderDao.getAllOrders());
        ArrayList<Integer> orderNumList = new ArrayList<>();

        for (Order order : orderList) {
            orderNumList.add(order.getOrderNum());
        }

        Collections.sort(orderNumList);
        return orderNumList;
    }

    @Override
    public Order editOrder(HashMap orderEditInfo) {
        HashMap<String, String> infoMap = new HashMap<>(orderEditInfo);
        Order newOrder;
        //CHECK FOR ORDER NUM CHANGE
        if (infoMap.get("GEN_NEW_NUM").equals("1")) {
            newOrder = new Order(this.getNextOrderNum());
        } else {
            newOrder = new Order(Integer.parseInt(infoMap.get("ORIG_NUM")));
        }

        //SET NEW ORDER TO ORIGINAL ORDER PROPS
        newOrder.setCustomerName(infoMap.get("ORIG_NAME"));
        newOrder.setArea(new BigDecimal(infoMap.get("ORIG_AREA")));
        newOrder.setOrderDate(LocalDate.parse(infoMap.get("ORIG_DATE")));
        newOrder.setState(infoMap.get("ORIG_STATE"));
        newOrder.setTaxRate(new BigDecimal(infoMap.get("ORIG_TAX_RATE")));
        newOrder.setProduct(infoMap.get("ORIG_PRODUCT"));
        newOrder.setMatCostSqFt(new BigDecimal(infoMap.get("ORIG_MAT_COST")));
        newOrder.setLaborCostSqFt(new BigDecimal(infoMap.get("ORIG_LAB_COST")));

        //EDIT CUSTOMER NAME
        if (!infoMap.get("NAME").equals("")) {
            newOrder.setCustomerName(infoMap.get("NAME"));
        }

        //EDIT AREA
        if (!infoMap.get("AREA").equals("")) {
            newOrder.setArea(new BigDecimal(infoMap.get("AREA")));
        }

        //EDIT DATE
        if (!infoMap.get("DATE").equals("")) {
            newOrder.setOrderDate(LocalDate.parse(infoMap.get("DATE")));
        }

        //EDIT STATE
        //EDIT STATE TAX RATE
        if (!infoMap.get("TAX_RATE").equals("")) {
            newOrder.setTaxRate(new BigDecimal(infoMap.get("TAX_RATE")));
        }
        //CHECK FOR CUSTOM
        if (infoMap.get("STATE").equals("CUSTOM")) {
            newOrder.setState(infoMap.get("CUST_STATE"));
        } else //EDIT STATE
        if (!infoMap.get("STATE").equals("")) {
            newOrder.setState(infoMap.get("STATE"));
            if (infoMap.get("TAX_RATE").equals("")) {
                //SET DEFAULT TAX RATE FOR NEW STATE
                State state = stateDao.getState(infoMap.get("STATE"));
                newOrder.setTaxRate(state.getTaxRate());
            }
        }

        //IF MAT COST OR LAB COST ARE EDITED
        if ((!infoMap.get("MAT_COST").equals(""))) {
            newOrder.setMatCostSqFt(new BigDecimal(infoMap.get("MAT_COST")));
        }
        if (!infoMap.get("LAB_COST").equals("")) {
            //SET CUSTOM LAB COST
            newOrder.setLaborCostSqFt(new BigDecimal(infoMap.get("LAB_COST")));
        }

        //CHECK FOR CUSTOM PRODUCT
        if (infoMap.get("PRODUCT").equals("CUSTOM")) {
            newOrder.setProduct(infoMap.get("CUST_PRODUCT"));
        } else if (!infoMap.get("PRODUCT").equals("")) {//PRODUCT IS EDITED C
            //EDIT PRODUCT
            newOrder.setProduct(infoMap.get("PRODUCT"));
            //GET NEW PRODUCT TO ACCES DEFAULT INFO
            Product product = productDao.getProduct(infoMap.get("PRODUCT"));
            if (infoMap.get("MAT_COST").equals("")) {//MAT COST NOT C
                //DEFAULT MAT COST   
                newOrder.setMatCostSqFt(product.getMatCostSqFt());
            }
            if (infoMap.get("LAB_COST").equals("")) {//LAB COST NOT C
                //DEFAULT LAB COST
                newOrder.setLaborCostSqFt(product.getLaborCostSqFt());
            }
        }
        return newOrder;
    }

    @Override
    public Order removeOrder(Order order
    ) {
        return orderDao.removeOrder(order);
    }

    @Override
    public void saveOrders() throws FlooringFilePersistenceException {
        orderDao.saveOrders();
    }

    @Override
    public void loadData() throws FlooringFilePersistenceException {
        orderDao.loadOrders();
        stateDao.loadState();
        productDao.loadProduct();
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> orderList = new ArrayList<>(orderDao.getAllOrders());
        Collections.sort(orderList,
                (o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
        return orderList;
    }

    @Override
    public Order overrideTotal(Order order, BigDecimal newTotal) {
        order.setTotalCost(newTotal);
        return order;
    }

    @Override
    public Boolean getIsTraining() {
        return isTraining;
        
    }

    //BEANS ARE ACCESSING SETTERS
    @Override
    public void setIsTraining(Boolean isTraining) {
        this.isTraining = isTraining;
    }

    
}
