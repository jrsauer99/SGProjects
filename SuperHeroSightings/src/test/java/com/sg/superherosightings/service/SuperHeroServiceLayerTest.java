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
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author apprentice
 */
public class SuperHeroServiceLayerTest {

    SuperHeroServiceLayer service;

    public SuperHeroServiceLayerTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");

        service = ctx.getBean("superHeroServiceLayer", SuperHeroServiceLayer.class);

        //delete bridge table entries for super hero super powers
        service.deleteAllSuperHeroSuperPowers();

        //delete brige table entries for super hero sighting
        service.deleteAllSuperHerosInSighting();

        //delete all memberships from bridge table
        service.deleteAllMemberships();

        //delete all sightings
        List<Sighting> sightings = service.getAllSightings();
        for (Sighting currentSight : sightings) {
            service.deleteSighting(currentSight.getSightingID());
        }

        // delete all orgs
        List<Organization> organizations = service.getAllOrganizations();
        for (Organization currentOrg : organizations) {
            service.deleteOrganization(currentOrg.getOrganizationID());
        }

        // delete all locations
        List<Location> locations = service.getAllLocations();
        for (Location currentLoc : locations) {
            service.deleteLocation(currentLoc.getLocationID());
        }
        //delete all coordinates
        List<Coordinate> coordinates = service.getAllCoordinates();
        for (Coordinate currentCoord : coordinates) {
            service.deleteCoordinate(currentCoord.getCoordinateID());
        }

        //delete all super heros
        List<SuperHero> supHeros = service.getAllSuperHeros();
        for (SuperHero currentSupHero : supHeros) {
            service.deleteSuperHero(currentSupHero.getSuperHeroID());
        }

        //delete all super powers
        List<SuperPower> supPows = service.getAllSuperPowers();
        for (SuperPower currentSupPow : supPows) {
            service.deleteSuperPower(currentSupPow.getSuperPowerID());
        }

    }

    @After
    public void tearDown() {
    }

    //COORDINATES********************************************************************
    @Test
    public void addGetCoord() {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));
        service.addCoordinate(coordinate);
        try {
            Coordinate coordFromDao = service.getCoordinateByID(coordinate.getCoordinateID());
            assertEquals(coordFromDao, coordinate);
        } catch (ObjectDoesNotExistException e) {
            Assert.fail();
        }

    }

    @Test
    public void addGetCoordDNE() {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));
        service.addCoordinate(coordinate);
        try {
            Coordinate coordFromDao = service.getCoordinateByID(coordinate.getCoordinateID() + 1);
            assertEquals(coordFromDao, coordinate);
            Assert.fail();
        } catch (ObjectDoesNotExistException e) {

        }

    }

    @Test
    public void getAllCoordss() {
        Coordinate coordinate1 = new Coordinate();
        coordinate1.setLatitude(new BigDecimal("500"));
        coordinate1.setLongitude(new BigDecimal("550"));
        service.addCoordinate(coordinate1);

        Coordinate coordinate2 = new Coordinate();
        coordinate2.setLatitude(new BigDecimal("600"));
        coordinate2.setLongitude(new BigDecimal("650"));
        service.addCoordinate(coordinate2);

        List<Coordinate> expectedResult = new ArrayList<>();
        expectedResult.add(coordinate1);
        expectedResult.add(coordinate2);
        assertEquals(service.getAllCoordinates(), expectedResult);
        assertEquals(service.getAllCoordinates().size(), 2);
    }

    @Test
    public void addGetLocation() {

        try {

            Location location = new Location();
            location.setLocName("My House");
            location.setDescription("A house");
            location.setStreet("1010 E Oak");
            location.setCity("Louisville");
            location.setState("Ky");
            location.setZipCode("40204");
            location.setCountry("USA");
            service.addLocation(location, new BigDecimal("650"), new BigDecimal("550"));
            Location locFromDao = service.getLocationByID(location.getLocationID());
            assertEquals(locFromDao, location);

        } catch (ObjectDoesNotExistException e) {
            Assert.fail();
        }
    }

    @Test
    public void addGetLocationExistingCoord() {

        try {
            Coordinate coordinate = new Coordinate();
            coordinate.setLatitude(new BigDecimal("500"));
            coordinate.setLongitude(new BigDecimal("550"));

            service.addCoordinate(coordinate);

            Location location = new Location();
            location.setLocName("My House");
            location.setDescription("A house");
            location.setStreet("1010 E Oak");
            location.setCity("Louisville");
            location.setState("Ky");
            location.setZipCode("40204");
            location.setCountry("USA");
            service.addLocation(location, new BigDecimal("500"), new BigDecimal("550"));
            Location locFromDao = service.getLocationByID(location.getLocationID());
            assertEquals(locFromDao, location);
            assertEquals(service.getAllCoordinates().size(), 1);

        } catch (ObjectDoesNotExistException e) {
            Assert.fail();
        }
    }

    @Test
    public void addGetLocationAdditionalCoord() {

        try {
            Coordinate coordinate = new Coordinate();
            coordinate.setLatitude(new BigDecimal("500"));
            coordinate.setLongitude(new BigDecimal("550"));

            service.addCoordinate(coordinate);

            Location location = new Location();
            location.setLocName("My House");
            location.setDescription("A house");
            location.setStreet("1010 E Oak");
            location.setCity("Louisville");
            location.setState("Ky");
            location.setZipCode("40204");
            location.setCountry("USA");
            service.addLocation(location, new BigDecimal("700"), new BigDecimal("550"));
            Location locFromDao = service.getLocationByID(location.getLocationID());
            assertEquals(locFromDao, location);
            assertEquals(service.getAllCoordinates().size(), 2);

        } catch (ObjectDoesNotExistException e) {
            Assert.fail();
        }
    }

    @Test
    public void deleteLocation() {

        try {
            Location location = new Location();
            location.setLocName("My House");
            location.setDescription("A house");
            location.setStreet("1010 E Oak");
            location.setCity("Louisville");
            location.setState("Ky");
            location.setZipCode("40204");
            location.setCountry("USA");

            service.addLocation(location, new BigDecimal("700"), new BigDecimal("700"));

            service.deleteLocation(location.getLocationID());

            service.getLocationByID(location.getLocationID());
        } catch (ObjectDoesNotExistException e) {

        }
    }

    @Test
    public void updateLocation() {
        try {

            Location location = new Location();
            location.setLocName("My House");
            location.setDescription("A house");
            location.setStreet("1010 E Oak");
            location.setCity("Louisville");
            location.setState("Ky");
            location.setZipCode("40204");
            location.setCountry("USA");

            service.addLocation(location,new BigDecimal("500"),new BigDecimal("500"));

            location.setDescription("The best house!");
            service.updateLocation(location);

            Location locFromDao = service.getLocationByID(location.getLocationID());
            assertEquals(locFromDao.getDescription(), "The best house!");

        } catch (ObjectDoesNotExistException e) {

        }
    }
    
     @Test
    public void updateLocationExistingCoord() {
        try {
           Coordinate coordinate = new Coordinate();
           coordinate.setLatitude(new BigDecimal("500"));
           coordinate.setLongitude(new BigDecimal("550"));

          service.addCoordinate(coordinate);

            Location location = new Location();
            location.setLocName("My House");
            location.setDescription("A house");
            location.setStreet("1010 E Oak");
            location.setCity("Louisville");
            location.setState("Ky");
            location.setZipCode("40204");
            location.setCountry("USA");

            service.addLocation(location,new BigDecimal("500"),new BigDecimal("550"));

            location.setDescription("The best house!");
            service.updateLocation(location);

            Location locFromDao = service.getLocationByID(location.getLocationID());
            assertEquals(locFromDao.getDescription(), "The best house!");
            assertEquals(service.getAllCoordinates().size(),1);
        } catch (ObjectDoesNotExistException e) {

        }
    }
    
     @Test
    public void updateLocationNewCoord() {
        try {
           Coordinate coordinate = new Coordinate();
           coordinate.setLatitude(new BigDecimal("500"));
           coordinate.setLongitude(new BigDecimal("550"));

          service.addCoordinate(coordinate);

            Location location = new Location();
            location.setLocName("My House");
            location.setDescription("A house");
            location.setStreet("1010 E Oak");
            location.setCity("Louisville");
            location.setState("Ky");
            location.setZipCode("40204");
            location.setCountry("USA");

            service.addLocation(location,new BigDecimal("505"),new BigDecimal("550"));

            location.setDescription("The best house!");
            service.updateLocation(location);

            Location locFromDao = service.getLocationByID(location.getLocationID());
            assertEquals(locFromDao.getDescription(), "The best house!");
            assertEquals(service.getAllCoordinates().size(),2);
        } catch (ObjectDoesNotExistException e) {

        }
    }
    
    
     @Test
    public void updateLocationUpdateCoord() {
        try {

            Location location = new Location();
            location.setLocName("My House");
            location.setDescription("A house");
            location.setStreet("1010 E Oak");
            location.setCity("Louisville");
            location.setState("Ky");
            location.setZipCode("40204");
            location.setCountry("USA");

            service.addLocation(location,new BigDecimal("505"),new BigDecimal("550"));
            Coordinate coordinate = new Coordinate();
           coordinate.setLatitude(new BigDecimal("500"));
           coordinate.setLongitude(new BigDecimal("550"));
             
            location.setCoordinate(coordinate);
            service.updateLocation(location);

            Location locFromDao = service.getLocationByID(location.getLocationID());
            assertEquals(service.getAllCoordinates().size(),2);
        } catch (ObjectDoesNotExistException e) {

        }
    }
    
    

}
