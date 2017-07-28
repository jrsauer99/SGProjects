/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dto;

import java.util.Objects;
import javax.validation.Valid;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author apprentice
 */
public class Location {

    private int locationID;
    @NotEmpty(message = "You must supply a value for Location Name.")
    @Length(max = 30, message = "Location Name must be no more than 30 characters in length.")
    private String locName;
    @NotEmpty(message = "You must supply a value for Description.")
    @Length(max = 100, message = "Description must be no more than 100 characters in length.")
    private String description;
   @NotEmpty(message = "You must supply a value for Street.")
    @Length(max = 30, message = "Street must be no more than 30 characters in length.")
    private String street;
    @NotEmpty(message = "You must supply a value for City.")
    @Length(max = 30, message = "City must be no more than 30 characters in length.")
   private String city;
    @NotEmpty(message = "You must supply a value for State.")
    @Length(max = 30, message = "State must be no more than 30 characters in length.")
    private String state;
    @NotEmpty(message = "You must supply a value for Zip Code.")
    @Length(max = 30, message = "Zip Code must be no more than 30 characters in length.")
    private String zipCode;
    @NotEmpty(message = "You must supply a value for Country.")
    @Length(max = 30, message = "Country must be no more than 30 characters in length.")
    private String Country;
//    @NotEmpty(message = "You must supply values for Coordinates.")
//    @Length(max = 30, message = "Coordinate must be no more than 30 characters in length.")
    @Valid
    private Coordinate coordinate;

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.locationID;
        hash = 97 * hash + Objects.hashCode(this.locName);
        hash = 97 * hash + Objects.hashCode(this.description);
        hash = 97 * hash + Objects.hashCode(this.street);
        hash = 97 * hash + Objects.hashCode(this.city);
        hash = 97 * hash + Objects.hashCode(this.state);
        hash = 97 * hash + Objects.hashCode(this.zipCode);
        hash = 97 * hash + Objects.hashCode(this.Country);
        hash = 97 * hash + Objects.hashCode(this.coordinate);
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
        final Location other = (Location) obj;
        if (this.locationID != other.locationID) {
            return false;
        }
        if (!Objects.equals(this.locName, other.locName)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.zipCode, other.zipCode)) {
            return false;
        }
        if (!Objects.equals(this.Country, other.Country)) {
            return false;
        }
        if (!Objects.equals(this.coordinate, other.coordinate)) {
            return false;
        }
        return true;
    }

}
