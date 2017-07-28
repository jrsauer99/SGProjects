/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dto;

import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 *
 * @author apprentice
 */
public class Coordinate {
    private int coordinateID;
    @NotNull(message = "You must supply a value for Latitude.")
    //@BigDecimal()
    private BigDecimal latitude;
    @NotNull(message = "You must supply a value for Longitude.")
    private BigDecimal longitude;

    public int getCoordinateID() {
        return coordinateID;
    }

    public void setCoordinateID(int coordinateID) {
        this.coordinateID = coordinateID;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.coordinateID;
        hash = 59 * hash + Objects.hashCode(this.latitude);
        hash = 59 * hash + Objects.hashCode(this.longitude);
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
        final Coordinate other = (Coordinate) obj;
        if (this.coordinateID != other.coordinateID) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        return true;
    }

   
   
    
}
