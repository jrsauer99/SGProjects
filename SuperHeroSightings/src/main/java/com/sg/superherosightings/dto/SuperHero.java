/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author apprentice
 */
public class SuperHero {
    private int superHeroID;
    @NotEmpty(message = "You must supply a value for Super Hero Name.")
    @Length(max = 30, message = "Super Hero Name must be no more than 30 characters in length.")
    private String superHeroName;
    @NotEmpty(message = "You must supply a value for description.")
    @Length(max = 30, message = "Description must be no more than 30 characters in length.")
    private String description;
    
    private List<SuperPower> superPowers;

    public  SuperHero() {
        superPowers = new ArrayList<>();
    }
    
    public int getSuperHeroID() {
        return superHeroID;
    }

    public void setSuperHeroID(int superHeroID) {
        this.superHeroID = superHeroID;
    }

    public String getSuperHeroName() {
        return superHeroName;
    }

    public void setSuperHeroName(String superHeroName) {
        this.superHeroName = superHeroName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SuperPower> getSuperPowers() {
        return superPowers;
    }

    public void setSuperPowers(List<SuperPower> superPowers) {
        this.superPowers = superPowers;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + this.superHeroID;
        hash = 11 * hash + Objects.hashCode(this.superHeroName);
        hash = 11 * hash + Objects.hashCode(this.description);
        hash = 11 * hash + Objects.hashCode(this.superPowers);
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
        final SuperHero other = (SuperHero) obj;
        if (this.superHeroID != other.superHeroID) {
            return false;
        }
        if (!Objects.equals(this.superHeroName, other.superHeroName)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.superPowers, other.superPowers)) {
            return false;
        }
        return true;
    }
    
    


            
 
    
    
}

