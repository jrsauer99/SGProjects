/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author apprentice
 */
public class Order {

    private Integer orderNum;
    private String customerName;
    private LocalDate orderDate;
    private String state;
    private BigDecimal taxRate;
    private BigDecimal area;
    private String product;
    private BigDecimal matCostSqFt;
    private BigDecimal laborCostSqFt;

    private BigDecimal matCost;
    private BigDecimal laborCost;
    private BigDecimal totalTax;
    private BigDecimal totalCost;

    public Order(int orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getMatCost() {
        return matCost;
    }

    public void setMatCost(BigDecimal matCost) {
        this.matCost = matCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public BigDecimal getMatCostSqFt() {
        return matCostSqFt;
    }

    public void setMatCostSqFt(BigDecimal matCostSqFt) {
        this.matCostSqFt = matCostSqFt;
    }

    public BigDecimal getLaborCostSqFt() {
        return laborCostSqFt;
    }

    public void setLaborCostSqFt(BigDecimal laborCostSqFt) {
        this.laborCostSqFt = laborCostSqFt;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.orderNum);
        hash = 89 * hash + Objects.hashCode(this.customerName);
        hash = 89 * hash + Objects.hashCode(this.orderDate);
        hash = 89 * hash + Objects.hashCode(this.state);
        hash = 89 * hash + Objects.hashCode(this.taxRate);
        hash = 89 * hash + Objects.hashCode(this.area);
        hash = 89 * hash + Objects.hashCode(this.product);
        hash = 89 * hash + Objects.hashCode(this.matCostSqFt);
        hash = 89 * hash + Objects.hashCode(this.laborCostSqFt);
        hash = 89 * hash + Objects.hashCode(this.matCost);
        hash = 89 * hash + Objects.hashCode(this.laborCost);
        hash = 89 * hash + Objects.hashCode(this.totalTax);
        hash = 89 * hash + Objects.hashCode(this.totalCost);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.orderNum, other.orderNum)) {
            return false;
        }
        if (!Objects.equals(this.orderDate, other.orderDate)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.matCostSqFt, other.matCostSqFt)) {
            return false;
        }
        if (!Objects.equals(this.laborCostSqFt, other.laborCostSqFt)) {
            return false;
        }
        if (!Objects.equals(this.matCost, other.matCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.totalTax, other.totalTax)) {
            return false;
        }
        if (!Objects.equals(this.totalCost, other.totalCost)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ORDER: " + orderNum + " |NAME: " + customerName + " |DATE: "
                + orderDate + " |STATE: " + state + " |Total: $" + totalCost;
    }
}
