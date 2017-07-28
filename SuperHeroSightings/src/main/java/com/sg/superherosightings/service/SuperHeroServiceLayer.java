/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dto.Coordinate;
import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Organization;
import com.sg.superherosightings.dto.Sighting;
import com.sg.superherosightings.dto.SuperHero;
import com.sg.superherosightings.dto.SuperPower;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface SuperHeroServiceLayer {
    public void addLocation(Location location, BigDecimal latitude, BigDecimal longitude);
    public List<Location> getAllLocations();
    public Location getLocationByID(int locationID) throws ObjectDoesNotExistException;
    public void addCoordinate(Coordinate coordinate);
    public Coordinate getCoordinateByID(int coordinateID) throws ObjectDoesNotExistException;
    public List<Coordinate> getAllCoordinates();
    public void deleteLocation(int locationID);
    public void updateLocation(Location location);
    
    public List<SuperHero> getAllSuperHeros();
    public void addSuperPower(SuperPower superPower);
    public void addSuperHero(SuperHero superHero);
    public List<SuperPower> getAllSuperPowers();
    public SuperPower getSuperPowerByID(int superPowerID) throws ObjectDoesNotExistException;
    public void deleteSuperHero(int superHeroID);
    public SuperHero getSuperHeroByID(int superHeroID) throws ObjectDoesNotExistException;
    public void updateSuperHero(SuperHero superHero);
    public List<SuperPower> getSuperPowersBySuperHero(int superHeroID);
    
    public void addSighting(Sighting sighting);
    public List<Sighting> getAllSightings();
    public void deleteSighting(int sightingID);
    public Sighting getSightingByID(int sightingID) throws ObjectDoesNotExistException;
    
    public List<Organization> getAllOrganizations();
    public void addOrganization(Organization organization);
    public Organization getOrganizationByID(int organizationID) throws ObjectDoesNotExistException;
    public void deleteOrganization(int organizationID);
     public List<SuperHero> getAllSuperHerosByOrg(int organizationID);
     public Location getLocationByOrg(Organization organization);
     public void updateOrganization(Organization organization);
     
     public List<Sighting> getLatestSightings();
     public void updateSighting(Sighting sighting);
    
     //FOR TESTING
      public void deleteAllSuperHeroSuperPowers();
      public void deleteAllSuperHerosInSighting();
      public void deleteAllMemberships();
      public void deleteCoordinate(int coordinateID);
      public void deleteSuperPower(int superPowerID);
        

        

        
       

       

       
     
}
