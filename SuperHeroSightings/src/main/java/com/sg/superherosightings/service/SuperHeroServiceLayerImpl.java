/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dao.SuperHeroDao;
import com.sg.superherosightings.dto.Coordinate;
import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Organization;
import com.sg.superherosightings.dto.Sighting;
import com.sg.superherosightings.dto.SuperHero;
import com.sg.superherosightings.dto.SuperPower;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author apprentice
 */
public class SuperHeroServiceLayerImpl implements SuperHeroServiceLayer {

    SuperHeroDao dao;

    @Inject
    public SuperHeroServiceLayerImpl(SuperHeroDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addLocation(Location location, BigDecimal latitude, BigDecimal longitude) {
        Boolean coordExists = false;
        //SEARCH TO SEE IF THERE IS AN EXISTING COORD WITH THIS ID
        List<Coordinate> coordList = dao.getAllCoordinates();

        for (Coordinate currentCoord : coordList) { //RATHER THAN THIS COULD DO A DIRECT QUERY
            if (currentCoord.getLatitude().compareTo(latitude) == 0 && currentCoord.getLongitude().compareTo(longitude) == 0) {
                //THIS COORD ALREADY EXISTS IN THE DB
                location.setCoordinate(currentCoord);
                dao.addLocation(location);
                coordExists = true;
                break;
            }
        }

        if (!coordExists) {
            //THIS COOD DOES NOT EXIST IN THE DB, CREATE NEW
            Coordinate coordinate = new Coordinate();
            coordinate.setLatitude(latitude);
            coordinate.setLongitude(longitude);
            dao.addCoordinate(coordinate);
            location.setCoordinate(coordinate);
            dao.addLocation(location);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Location> getAllLocations() {
        return dao.getAllLocations();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Location getLocationByID(int locationID) throws ObjectDoesNotExistException {
        if (dao.getLocationByID(locationID)==null){
            throw new ObjectDoesNotExistException("The location you are trying to access does not exist.");
        }
        return dao.getLocationByID(locationID);
        
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteLocation(int locationID) {
        dao.deleteLocation(locationID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateLocation(Location location) {
        //SEARCH TO SEE IF THERE IS AN EXISTING COORD WITH THIS ID
        List<Coordinate> coordList = dao.getAllCoordinates();
        BigDecimal latitude = location.getCoordinate().getLatitude();
        BigDecimal longitude = location.getCoordinate().getLongitude();
        Boolean coordExists = false;

        for (Coordinate currentCoord : coordList) { //RATHER THAN THIS COULD DO A DIRECT QUERY
            if (currentCoord.getLatitude().compareTo(latitude) == 0 && currentCoord.getLongitude().compareTo(longitude) == 0) {
                //THIS COORD ALREADY EXISTS IN THE DB
                location.setCoordinate(currentCoord);
                coordExists = true;
                break;
            }
        }

        if (!coordExists) {
            //THIS COOD DOES NOT EXIST IN THE DB, CREATE NEW
            Coordinate coordinate = new Coordinate();
            coordinate.setLatitude(location.getCoordinate().getLatitude());
            coordinate.setLongitude(location.getCoordinate().getLongitude());
            dao.addCoordinate(coordinate);
            location.setCoordinate(coordinate);
        }

        dao.updateLocation(location);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addCoordinate(Coordinate coordinate) {
        dao.addCoordinate(coordinate);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Coordinate getCoordinateByID(int coordinateID) throws ObjectDoesNotExistException {
        if (dao.getCoordinateByID(coordinateID)==null){
            throw new ObjectDoesNotExistException("The coordinate you are trying to access does not exist.");
        }
        return dao.getCoordinateByID(coordinateID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Coordinate> getAllCoordinates() {
        return dao.getAllCoordinates();
    }

    //SUPERHEROS***************************************************************************
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<SuperHero> getAllSuperHeros() {
        return dao.getAllSuperHeros();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSuperPower(SuperPower superPower) {
        dao.addSuperPower(superPower);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSuperHero(SuperHero superHero) {
        dao.addSuperHero(superHero);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<SuperPower> getAllSuperPowers() {
        return dao.getAllSuperPowers();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public SuperPower getSuperPowerByID(int superPowerID) throws ObjectDoesNotExistException{
        if(dao.getSuperPowerByID(superPowerID)== null){
            throw new ObjectDoesNotExistException("The location you are trying to access does not exist.");
        }
        
        return dao.getSuperPowerByID(superPowerID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSuperHero(int superHeroID) {
        dao.deleteSuperHero(superHeroID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public SuperHero getSuperHeroByID(int superHeroID) throws ObjectDoesNotExistException{
        if(dao.getSuperHeroByID(superHeroID)== null){
            throw new ObjectDoesNotExistException("The location you are trying to access does not exist.");
        }
        
        return dao.getSuperHeroByID(superHeroID);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateSuperHero(SuperHero superHero){
        dao.updateSuperHero(superHero);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<SuperPower> getSuperPowersBySuperHero(int superHeroID){
        return dao.getSuperPowersBySuperHero(superHeroID);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSighting(Sighting sighting){
        dao.addSighting(sighting);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Sighting> getAllSightings(){
        return dao.getAllSightings();
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSighting(int sightingID){
        dao.deleteSighting(sightingID);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Sighting getSightingByID(int sightingID) throws ObjectDoesNotExistException{
       if (dao.getSightingByID(sightingID)==null){
            throw new ObjectDoesNotExistException("The location you are trying to access does not exist.");
        }
        
        return dao.getSightingByID(sightingID);
    }
    
    //ORGANZATIONS*******************************************************
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Organization> getAllOrganizations(){
        return dao.getAllOrganizations();
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addOrganization(Organization organization){
        dao.addOrganization(organization);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Organization getOrganizationByID(int organizationID) throws ObjectDoesNotExistException{
        if (dao.getOrganizationByID(organizationID)==null){
            throw new ObjectDoesNotExistException("The location you are trying to access does not exist.");
        }
        
        return dao.getOrganizationByID(organizationID);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteOrganization(int organizationID){
        dao.deleteOrganization(organizationID);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
     public List<SuperHero> getAllSuperHerosByOrg(int organizationID){
         return dao.getAllSuperHerosByOrg(organizationID);
     }
     
     @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
     public Location getLocationByOrg(Organization organization){
         return dao.findLocForOrg(organization);
     }
     
     @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
     public void updateOrganization(Organization organization){
         dao.updateOrganization(organization);
     }
     
      @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
     public List<Sighting> getLatestSightings(){
         return dao.getLatestSightings();
     }
     
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
     public void updateSighting(Sighting sighting){
         dao.updateSighting(sighting);
     }
     
//ADDED FOR TESTING
     @Override
     public void deleteAllSuperHeroSuperPowers(){
         dao.deleteAllSuperHeroSuperPowers();
     }
      @Override
     public void deleteAllSuperHerosInSighting(){
         dao.deleteAllSuperHerosInSighting();
     }
     @Override 
     public void deleteAllMemberships(){
         dao.deleteAllMemberships();
     }
     @Override 
     public void deleteCoordinate(int coordinateID){
         dao.deleteCoordinate(coordinateID);
     }
     @Override 
     public void deleteSuperPower(int superPowerID){
         dao.deleteSuperPower(superPowerID);
     }
     
     
}
