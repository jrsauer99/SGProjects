/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author apprentice
 */
public class Product {
    private String productName;
    private BigDecimal matCostSqFt;
    private BigDecimal laborCostSqFt;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
        hash = 41 * hash + Objects.hashCode(this.productName);
        hash = 41 * hash + Objects.hashCode(this.matCostSqFt);
        hash = 41 * hash + Objects.hashCode(this.laborCostSqFt);
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
        final Product other = (Product) obj;
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        if (!Objects.equals(this.matCostSqFt, other.matCostSqFt)) {
            return false;
        }
        if (!Objects.equals(this.laborCostSqFt, other.laborCostSqFt)) {
            return false;
        }
        return true;
    }
    
}
