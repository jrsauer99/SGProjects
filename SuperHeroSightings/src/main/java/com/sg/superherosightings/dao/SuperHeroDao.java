/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Coordinate;
import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Organization;
import com.sg.superherosightings.dto.Sighting;
import com.sg.superherosightings.dto.SuperHero;
import com.sg.superherosightings.dto.SuperPower;
import java.time.LocalDate;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author apprentice
 */
public interface SuperHeroDao {
   //Coordinate
    public void addCoordinate(Coordinate coordinate);
    public void deleteCoordinate(int coordinateID);
    public void updateCoordinate(Coordinate coordinate);
    public Coordinate getCoordinateByID(int coordinateID);
    public List<Coordinate> getAllCoordinates();
    
    //Location
    public void addLocation(Location location);
    public void deleteLocation(int locationID); 
    public void updateLocation(Location location);
    public Location getLocationByID(int locationID);
    public List<Location> getAllLocations();
    
    public List<Location> getAllLocBySuperHero (int superHeroID);
    
    //Organization
    public void addOrganization(Organization organization);
    public void deleteOrganization(int organizationID);
    public void updateOrganization(Organization organization);
    public Organization getOrganizationByID(int organizationID);
    public List<Organization> getAllOrganizations();
    
    public List<Organization> getAllOrgsBySuperHeros(int superHeroID);
     public Location findLocForOrg(Organization org);
    
    
    //Sighting
    public void addSighting(Sighting sighting);
    public void deleteSighting(int sightingID);
    public void updateSighting(Sighting sighting);
    public Sighting getSightingByID(int sightingID);
    public List<Sighting> getAllSightings();
    
    public List<Sighting> getAllSightingsByDate (LocalDate date);
    public List<Sighting> getLatestSightings();
    
    //SuperHero
    public void addSuperHero(SuperHero superHero);
    public void deleteSuperHero(int superHeroID);
    public void updateSuperHero(SuperHero superHero);
    public SuperHero getSuperHeroByID(int superHeroID);
    public List<SuperHero> getAllSuperHeros();
    
    public List<SuperHero> getAllSuperHerosByLoc(int locationID);
    public List<SuperHero> getAllSuperHerosByOrg(int organizationID);
    
    
    //SuperPower
    public void addSuperPower(SuperPower superPower);
    public void deleteSuperPower(int superPowerID);// throws ReferencedByOtherTableException;
    public void updateSuperPower(SuperPower superPower);
    public SuperPower getSuperPowerByID(int superPowerID);
    public List<SuperPower> getAllSuperPowers();
    
    public List<SuperPower> getSuperPowersBySuperHero(int superHeroID);
   
    //Memberships Brdige - Testing
    public void deleteAllMemberships();
    
    //SuperHeroSightings Bridge - Testing
    public void deleteAllSuperHerosInSighting();
    
    //SuperHeroSuperPower Bridge - Testing
    public void deleteAllSuperHeroSuperPowers();
    
}
