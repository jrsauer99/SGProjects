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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author apprentice
 */
public class SuperHeroDaoTest {

    SuperHeroDao dao;

    public SuperHeroDaoTest() {
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

        dao = ctx.getBean("superHeroDao", SuperHeroDao.class);

        //delete bridge table entries for super hero super powers
        dao.deleteAllSuperHeroSuperPowers();

        //delete brige table entries for super hero sighting
        dao.deleteAllSuperHerosInSighting();

        //delete all memberships from bridge table
        dao.deleteAllMemberships();

        //delete all sightings
        List<Sighting> sightings = dao.getAllSightings();
        for (Sighting currentSight : sightings) {
            dao.deleteSighting(currentSight.getSightingID());
        }

        // delete all orgs
        List<Organization> organizations = dao.getAllOrganizations();
        for (Organization currentOrg : organizations) {
            dao.deleteOrganization(currentOrg.getOrganizationID());
        }

        // delete all locations
        List<Location> locations = dao.getAllLocations();
        for (Location currentLoc : locations) {
            dao.deleteLocation(currentLoc.getLocationID());
        }
        //delete all coordinates
        List<Coordinate> coordinates = dao.getAllCoordinates();
        for (Coordinate currentCoord : coordinates) {
            dao.deleteCoordinate(currentCoord.getCoordinateID());
        }

        //delete all super heros
        List<SuperHero> supHeros = dao.getAllSuperHeros();
        for (SuperHero currentSupHero : supHeros) {
            dao.deleteSuperHero(currentSupHero.getSuperHeroID());
        }

        //delete all super powers
        List<SuperPower> supPows = dao.getAllSuperPowers();
        for (SuperPower currentSupPow : supPows) {
            dao.deleteSuperPower(currentSupPow.getSuperPowerID());
        }

    }

    @After
    public void tearDown() {
    }

    //==========================================================
    //CRUD TESTING
    //==========================================================
    //COORDINATES********************************************************************
    @Test
    public void addGetCoord() {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));
        dao.addCoordinate(coordinate);
        Coordinate coordFromDao = dao.getCoordinateByID(coordinate.getCoordinateID());
        assertEquals(coordFromDao, coordinate);
    }

    @Test
    public void deleteCoord() {
        //1. TEST DELETE COORD WITHOUT LOC REFERENCE
        Coordinate coordinate1 = new Coordinate();
        coordinate1.setLatitude(new BigDecimal("500"));
        coordinate1.setLongitude(new BigDecimal("550"));

        dao.addCoordinate(coordinate1);
        dao.deleteCoordinate(coordinate1.getCoordinateID());
        assertNull(dao.getCoordinateByID(coordinate1.getCoordinateID()));

    }

    @Test //SET COORD TO NULL
    public void deleteCoordREF() {
        //2. TEST DELETE COORD WITH LOC REFERENCE
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));

        dao.addCoordinate(coordinate);

        Location location = new Location();
        location.setLocName("My House");
        location.setDescription("A house");
        location.setStreet("1010 E Oak");
        location.setCity("Louisville");
        location.setState("Ky");
        location.setZipCode("40204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);

        dao.addLocation(location);
        System.out.println("the cood you are about to delete is");
        System.out.println(coordinate.getCoordinateID());
        //DELETE COORD
        dao.deleteCoordinate(coordinate.getCoordinateID());

        //CHECK THAT LOCATION HAS NULL COORD
        Location locFromDao = new Location();
        locFromDao = dao.getLocationByID(location.getLocationID());

        assertNull(locFromDao.getCoordinate());

    }

    @Test //TEST UPDATE LOCATION WITH NULL COORD
    public void deleteCoordREF2() {
        //2. TEST DELETE COORD WITH LOC REFERENCE
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));

        dao.addCoordinate(coordinate);

        Location location = new Location();
        location.setLocName("My House");
        location.setDescription("A house");
        location.setStreet("1010 E Oak");
        location.setCity("Louisville");
        location.setState("Ky");
        location.setZipCode("40204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);

        dao.addLocation(location);
        System.out.println("the cood you are about to delete is");
        System.out.println(coordinate.getCoordinateID());
        //DELETE COORD
        dao.deleteCoordinate(coordinate.getCoordinateID());

        //CHECK THAT LOCATION HAS NULL COORD
        Location locFromDao = new Location();
        locFromDao = dao.getLocationByID(location.getLocationID());

        location.setCountry("US");
        dao.updateLocation(locFromDao);

    }

    @Test
    public void updateCoord() {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));
        dao.addCoordinate(coordinate);
        coordinate.setLatitude(new BigDecimal("600"));
        dao.updateCoordinate(coordinate);
        Coordinate coordFromDao = dao.getCoordinateByID(coordinate.getCoordinateID());
        assertEquals(coordFromDao.getLatitude(), new BigDecimal("600"));

    }

    @Test
    public void getAllCoordss() {
        Coordinate coordinate1 = new Coordinate();
        coordinate1.setLatitude(new BigDecimal("500"));
        coordinate1.setLongitude(new BigDecimal("550"));
        dao.addCoordinate(coordinate1);

        Coordinate coordinate2 = new Coordinate();
        coordinate2.setLatitude(new BigDecimal("600"));
        coordinate2.setLongitude(new BigDecimal("650"));
        dao.addCoordinate(coordinate2);

        List<Coordinate> expectedResult = new ArrayList<>();
        expectedResult.add(coordinate1);
        expectedResult.add(coordinate2);
        assertEquals(dao.getAllCoordinates(), expectedResult);
        assertEquals(dao.getAllCoordinates().size(), 2);
    }

    //********LOCATION****************************************************************
    @Test
    public void addGetLocation() {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));

        dao.addCoordinate(coordinate);

        Location location = new Location();
        location.setLocName("My House");
        location.setDescription("A house");
        location.setStreet("1010 E Oak");
        location.setCity("Louisville");
        location.setState("Ky");
        location.setZipCode("40204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);
        dao.addLocation(location);
        Location locFromDao = dao.getLocationByID(location.getLocationID());
        assertEquals(locFromDao, location);
    }
    
     @Test
    public void addGetLocationNullCoord() {
//        Coordinate coordinate = new Coordinate();
//        coordinate.setLatitude(new BigDecimal("500"));
//        coordinate.setLongitude(new BigDecimal("550"));
//
//        dao.addCoordinate(coordinate);

        Location location = new Location();
        location.setLocName("My House");
        location.setDescription("A house");
        location.setStreet("1010 E Oak");
        location.setCity("Louisville");
        location.setState("Ky");
        location.setZipCode("40204");
        location.setCountry("USA");
        dao.addLocation(location);
        Location locFromDao = dao.getLocationByID(location.getLocationID());
        assertEquals(locFromDao, location);
        assertNull(locFromDao.getCoordinate());
    }

    @Test
    public void deleteLocation() {
        //DELETE LOCATION WITHOUT REFERENCE
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));

        dao.addCoordinate(coordinate);

        Location location = new Location();
        location.setLocName("My House");
        location.setDescription("A house");
        location.setStreet("1010 E Oak");
        location.setCity("Louisville");
        location.setState("Ky");
        location.setZipCode("40204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);

        dao.addLocation(location);

        Location locFromDao = dao.getLocationByID(location.getLocationID());
        assertEquals(locFromDao, location);
        dao.deleteLocation(location.getLocationID());

        assertNull(dao.getLocationByID(location.getLocationID()));
    }

    @Test
    public void deleteLocationREF() {
        //DELETE LOCATION WITH REFERENCE
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));
        dao.addCoordinate(coordinate);
        Location location = new Location();
        location.setLocName("My House");
        location.setDescription("A house");
        location.setStreet("1010 E Oak");
        location.setCity("Louisville");
        location.setState("Ky");
        location.setZipCode("40204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);
        dao.addLocation(location);
        //ADD SIGHTING TO REF LOC
        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setSightingDate(LocalDate.now());
        dao.addSighting(sighting);
        dao.deleteLocation(location.getLocationID());

        Sighting sightFromDao = new Sighting();
        sightFromDao = dao.getSightingByID(sighting.getSightingID());

        assertNull(sightFromDao.getLocation());

    }

    @Test
    public void updateLocation() {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));

        dao.addCoordinate(coordinate);

        Location location = new Location();
        location.setLocName("My House");
        location.setDescription("A house");
        location.setStreet("1010 E Oak");
        location.setCity("Louisville");
        location.setState("Ky");
        location.setZipCode("40204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);

        dao.addLocation(location);

        location.setDescription("The best house!");
        dao.updateLocation(location);

        Location locFromDao = dao.getLocationByID(location.getLocationID());
        assertEquals(locFromDao.getDescription(), "The best house!");

    }

    @Test //TEST LOC UPDATE WITH NULL COORD
    public void updateLocationNullCoord() {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));

        dao.addCoordinate(coordinate);

        Location location = new Location();
        location.setLocName("My House");
        location.setDescription("A house");
        location.setStreet("1010 E Oak");
        location.setCity("Louisville");
        location.setState("Ky");
        location.setZipCode("40204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);

        dao.addLocation(location);

        dao.deleteCoordinate(coordinate.getCoordinateID());

        Location locFromDao = dao.getLocationByID(location.getLocationID());

        assertNull(locFromDao.getCoordinate());

    }

    @Test
    public void getAllLocations() {
        Coordinate coordinate1 = new Coordinate();
        coordinate1.setLatitude(new BigDecimal("500"));
        coordinate1.setLongitude(new BigDecimal("550"));
        dao.addCoordinate(coordinate1);
        Location location1 = new Location();
        location1.setLocName("My House");
        location1.setDescription("A house");
        location1.setStreet("1010 E Oak");
        location1.setCity("Louisville");
        location1.setState("Ky");
        location1.setZipCode("40204");
        location1.setCountry("USA");
        location1.setCoordinate(coordinate1);
        dao.addLocation(location1);

        Coordinate coordinate2 = new Coordinate();
        coordinate2.setLatitude(new BigDecimal("600"));
        coordinate2.setLongitude(new BigDecimal("650"));
        dao.addCoordinate(coordinate2);
        Location location2 = new Location();
        location2.setLocName("My House 2");
        location2.setDescription("A house 2");
        location2.setStreet("1010 E Oak 2");
        location2.setCity("Louisville 2");
        location2.setState("Ky 2");
        location2.setZipCode("40204 2");
        location2.setCountry("USA 2");
        location2.setCoordinate(coordinate2);
        dao.addLocation(location2);

        assertEquals(dao.getAllLocations().size(), 2);
        List<Location> expectedResult = new ArrayList<>();
        expectedResult.add(location1);
        expectedResult.add(location2);
        assertEquals(dao.getAllLocations(), expectedResult);
    }

    //**********SUPER POWER**********************************************************
    @Test
    public void addGetSuperPower() {
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);
        SuperPower supPowFromDao = dao.getSuperPowerByID(superPower.getSuperPowerID());
        assertEquals(supPowFromDao, superPower);
    }

    @Test
    public void deleteSuperPower() {
        //1. TEST DELETE super POWER WITHOUT SUPER HERO REFERENCE
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("jumps");
        dao.addSuperPower(superPower);

        dao.deleteSuperPower(superPower.getSuperPowerID());

        assertNull(dao.getSuperPowerByID(superPower.getSuperPowerID()));

    }

    //NOTE : I AM NOW ALLOWING DELETE OF SUPERPOWERS. . .
    @Test
    public void deleteSuperPowerREF() {
        //1. TEST DELETE super POWER WITH SUPER HERO REFERENCE
        //SUPERPOWER1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("jumps");
        dao.addSuperPower(superPower);
        //SUPPERPOWER2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("spins");
        dao.addSuperPower(superPower2);
        //ADD SUPERPOWERS TO LIST
        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);
        superPowers.add(superPower2);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        dao.deleteSuperPower(superPower.getSuperPowerID());
        dao.deleteSuperPower(superPower2.getSuperPowerID());
        SuperHero supHeroFromDao = dao.getSuperHeroByID(superHero.getSuperHeroID());
        assertEquals(supHeroFromDao.getSuperPowers().size(), 0);
    }

    @Test
    public void updateSuperPower() {
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("jumps");
        dao.addSuperPower(superPower);
        superPower.setSuperPower("jumps 2X");
        dao.updateSuperPower(superPower);
        SuperPower supPowFromDao = dao.getSuperPowerByID(superPower.getSuperPowerID());
        assertEquals(supPowFromDao.getSuperPower(), "jumps 2X");

    }

    @Test
    public void addGetAllSuperPower() {
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("jumps");
        dao.addSuperPower(superPower2);
        assertEquals(dao.getAllSuperPowers().size(), 2);

        List<SuperPower> expectedResult = new ArrayList<>();
        expectedResult.add(superPower);
        expectedResult.add(superPower2);
        assertEquals(dao.getAllSuperPowers(), expectedResult);
    }

    //********SUPER HERO****************************************************************
    @Test
    public void addGetSuperHero() {
        //SUPERPOWER1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);
        //SUPPERPOWER2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("spins");
        dao.addSuperPower(superPower2);
        //ADD SUPERPOWERS TO LIST
        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);
        superPowers.add(superPower2);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        SuperHero supHeroFromDao = dao.getSuperHeroByID(superHero.getSuperHeroID());
        assertEquals(supHeroFromDao, superHero);
    }

    @Test
    public void deleteSuperHero() {
        //SUPERPOWER1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);
        //SUPPERPOWER2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("spins");
        dao.addSuperPower(superPower2);
        //ADD SUPERPOWERS TO LIST
        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);
        superPowers.add(superPower2);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        dao.deleteSuperHero(superHero.getSuperHeroID());

        assertNull(dao.getSuperHeroByID(superHero.getSuperHeroID()));
    }

    @Test
    public void updateSuperHero() {
        //SUPERPOWER1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);
        //SUPPERPOWER2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("spins");
        dao.addSuperPower(superPower2);
        //ADD SUPERPOWERS TO LIST
        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);
        superPowers.add(superPower2);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        superHero.setSuperHeroName("Bobby");
        dao.updateSuperHero(superHero);

        SuperHero supHeroFromDao = dao.getSuperHeroByID(superHero.getSuperHeroID());
        assertEquals(supHeroFromDao.getSuperHeroName(), "Bobby");

    }

    @Test
    public void updateSuperHeroPowers() {
        //SUPERPOWER1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);
        //SUPPERPOWER2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("spins");
        dao.addSuperPower(superPower2);
        //ADD SUPERPOWERS TO LIST
        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);
        superPowers.add(superPower2);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        //TEST UPDATE OF SUPERPOWERS
        superPowers.remove(superPower2);
        superHero.setSuperPowers(superPowers);
        dao.updateSuperHero(superHero);
        SuperHero supHeroFromDao2 = dao.getSuperHeroByID(superHero.getSuperHeroID());
        assertEquals(supHeroFromDao2.getSuperPowers(), superPowers);
    }

    @Test
    public void addGetAllSuperHero() {
        //SUPERPOWER1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);
        List<SuperPower> superPowers1 = new ArrayList<>();
        superPowers1.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers1);
        dao.addSuperHero(superHero);

        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("flys");
        dao.addSuperPower(superPower2);
        List<SuperPower> superPowers2 = new ArrayList<>();
        superPowers2.add(superPower2);

        SuperHero superHero2 = new SuperHero();
        superHero2.setSuperHeroName("Bobby");
        superHero2.setDescription("older man");
        superHero2.setSuperPowers(superPowers2);
        dao.addSuperHero(superHero2);

        assertEquals(dao.getAllSuperHeros().size(), 2);

        List<SuperHero> expectedResult = new ArrayList<>();
        expectedResult.add(superHero);
        expectedResult.add(superHero2);
        assertEquals(dao.getAllSuperHeros(), expectedResult);
    }

    //********ORGANIATIONS************************************************************
    @Test
    public void addGetOrgs() {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));

        dao.addCoordinate(coordinate);

        Location location = new Location();
        location.setLocName("My House");
        location.setDescription("A house");
        location.setStreet("1010 E Oak");
        location.setCity("Louisville");
        location.setState("Ky");
        location.setZipCode("40204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);

        dao.addLocation(location);

        Organization org = new Organization();
        org.setOrgName("My Org");
        org.setDescription("The best");
        org.setPhoneNumber("256-679-9079");
        org.setEmail("sauer@gmail.com");
        org.setLocation(location);

        //ADD SUPER HERO TO ORG MEMBERSHIPS
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        List<SuperHero> members = new ArrayList();
        members.add(superHero);

        org.setMembers(members);

        dao.addOrganization(org);

        Organization orgFromDao = dao.getOrganizationByID(org.getOrganizationID());
        assertEquals(org, orgFromDao);
    }

    @Test
    public void addGetOrgsNullLoc() {

        Organization org = new Organization();
        org.setOrgName("My Org");
        org.setDescription("The best");
        org.setPhoneNumber("256-679-9079");
        org.setEmail("sauer@gmail.com");
        //org.setLocation(location);

        //ADD SUPER HERO TO ORG MEMBERSHIPS
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        List<SuperHero> members = new ArrayList();
        members.add(superHero);

        org.setMembers(members);

        dao.addOrganization(org);

        Organization orgFromDao = dao.getOrganizationByID(org.getOrganizationID());
        assertEquals(org, orgFromDao);
        assertNull(orgFromDao.getLocation());
        
    }

    @Test
    public void addGetOrgsNOMemb() {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));

        dao.addCoordinate(coordinate);

        Location location = new Location();
        location.setLocName("My House");
        location.setDescription("A house");
        location.setStreet("1010 E Oak");
        location.setCity("Louisville");
        location.setState("Ky");
        location.setZipCode("40204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);

        dao.addLocation(location);

        Organization org = new Organization();
        org.setOrgName("My Org");
        org.setDescription("The best");
        org.setPhoneNumber("256-679-9079");
        org.setEmail("sauer@gmail.com");
        org.setLocation(location);

        //NO SUPER HERO IN ORG MEMBERSHIPS
        dao.addOrganization(org);

        Organization orgFromDao = dao.getOrganizationByID(org.getOrganizationID());
        assertEquals(org, orgFromDao);
    }

    @Test
    public void deleteOrg() {

        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));

        dao.addCoordinate(coordinate);

        Location location = new Location();
        location.setLocName("My House");
        location.setDescription("A house");
        location.setStreet("1010 E Oak");
        location.setCity("Louisville");
        location.setState("Ky");
        location.setZipCode("40204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);

        dao.addLocation(location);

        Organization org = new Organization();
        org.setOrgName("My Org");
        org.setDescription("The best");
        org.setPhoneNumber("256-679-9079");
        org.setEmail("sauer@gmail.com");
        org.setLocation(location);

        //ADD SUPER HERO TO ORG MEMBERSHIPS
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        List<SuperHero> members = new ArrayList();
        members.add(superHero);

        org.setMembers(members); //THIS ORG NOW HAS ONE MEMEBER

        dao.addOrganization(org);

        dao.deleteOrganization(org.getOrganizationID());

        assertNull(dao.getOrganizationByID(org.getOrganizationID()));
    }

    @Test
    public void updateOrg() {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));

        dao.addCoordinate(coordinate);

        Location location = new Location();
        location.setLocName("My House");
        location.setDescription("A house");
        location.setStreet("1010 E Oak");
        location.setCity("Louisville");
        location.setState("Ky");
        location.setZipCode("40204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);

        dao.addLocation(location);

        Organization org = new Organization();
        org.setOrgName("My Org");
        org.setDescription("The best");
        org.setPhoneNumber("256-679-9079");
        org.setEmail("sauer@gmail.com");
        org.setLocation(location);

        //ADD SUPER HERO TO ORG MEMBERSHIPS
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        List<SuperHero> members = new ArrayList();
        members.add(superHero);

        org.setMembers(members);

        dao.addOrganization(org);
        org.setDescription("New Descrip");
        dao.updateOrganization(org);
        Organization orgFromDao = dao.getOrganizationByID(org.getOrganizationID());
        assertEquals(orgFromDao.getDescription(), "New Descrip");

        //TRY UPDATING LOC
        Coordinate coordinate2 = new Coordinate();
        coordinate2.setLatitude(new BigDecimal("500"));
        coordinate2.setLongitude(new BigDecimal("550"));

        dao.addCoordinate(coordinate2);

        Location location2 = new Location();
        location2.setLocName("My House");
        location2.setDescription("A house");
        location2.setStreet("1010 E Oak");
        location2.setCity("Louisville");
        location2.setState("Ky");
        location2.setZipCode("40204");
        location2.setCountry("USA");
        location2.setCoordinate(coordinate2);
        dao.addLocation(location2);

        org.setLocation(location2);
        dao.updateOrganization(org);
        Organization orgFromDao1 = dao.getOrganizationByID(org.getOrganizationID());
        assertEquals(orgFromDao1.getLocation(), location2);

    }

    @Test //TEST UPDATE ORG WITH NULL LOC
    public void updateOrgNullLoc() {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));

        dao.addCoordinate(coordinate);

        Location location = new Location();
        location.setLocName("My House");
        location.setDescription("A house");
        location.setStreet("1010 E Oak");
        location.setCity("Louisville");
        location.setState("Ky");
        location.setZipCode("40204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);

        dao.addLocation(location);

        Organization org = new Organization();
        org.setOrgName("My Org");
        org.setDescription("The best");
        org.setPhoneNumber("256-679-9079");
        org.setEmail("sauer@gmail.com");
        org.setLocation(location);

        //ADD SUPER HERO TO ORG MEMBERSHIPS
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        List<SuperHero> members = new ArrayList();
        members.add(superHero);

        org.setMembers(members);

        dao.addOrganization(org);
        dao.deleteLocation(location.getLocationID());
        Organization orgFromDao2 = dao.getOrganizationByID(org.getOrganizationID());
        assertNull(orgFromDao2.getLocation());

    }

    public void getAllOrgs() {
        //ADD COORD1
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));
        dao.addCoordinate(coordinate);

        //ADD COORD2
        Coordinate coordinate2 = new Coordinate();
        coordinate2.setLatitude(new BigDecimal("600"));
        coordinate2.setLongitude(new BigDecimal("650"));
        dao.addCoordinate(coordinate2);

        //ADD LOCATION1
        Location location = new Location();
        location.setLocName("Your House");
        location.setDescription("the house");
        location.setStreet("101 E Oak");
        location.setCity("Losville");
        location.setState("K");
        location.setZipCode("4204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);
        dao.addLocation(location);

        //ADD LOCATION2
        Location location2 = new Location();
        location2.setLocName("My House");
        location2.setDescription("A house");
        location2.setStreet("1010 E Oak");
        location2.setCity("Louisville");
        location2.setState("Ky");
        location2.setZipCode("40204");
        location2.setCountry("USA");
        location2.setCoordinate(coordinate2);
        dao.addLocation(location2);

        //CREATE SUPERPOWER AND SUPERHERO 1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        //CREATE SUPER HERO AND SUPER POWER 2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("swims");
        dao.addSuperPower(superPower2);

        List<SuperPower> superPowers2 = new ArrayList<>();
        superPowers2.add(superPower2);
        superPowers2.add(superPower);

        SuperHero superHero2 = new SuperHero();
        superHero2.setSuperHeroName("Jay");
        superHero2.setDescription("young man");
        superHero2.setSuperPowers(superPowers2);
        dao.addSuperHero(superHero2);

        //ADD ORGANIZATIONS
        //ORG1
        Organization org = new Organization();
        org.setOrgName("My Org");
        org.setDescription("The best");
        org.setPhoneNumber("256-679-9079");
        org.setEmail("sauer@gmail.com");
        org.setLocation(location);
        //ADD SUPERHEROS TO ORG1
        List<SuperHero> members = new ArrayList();
        members.add(superHero);
        members.add(superHero2);
        org.setMembers(members);
        dao.addOrganization(org);

        //ORG2
        Organization org2 = new Organization();
        org2.setOrgName("My Org");
        org2.setDescription("The best");
        org2.setPhoneNumber("256-679-9079");
        org2.setEmail("sauer@gmail.com");
        org2.setLocation(location2);
        //ADD SUPERHEROS TO ORG1
        List<SuperHero> members2 = new ArrayList();
        members2.add(superHero);
        org2.setMembers(members2);
        dao.addOrganization(org2);

        //CHECK THAT SUP 1 IS IN 2 ORGS
        List<Organization> expectedResult = new ArrayList<>();
        expectedResult.add(org);
        expectedResult.add(org2);
        assertEquals(expectedResult, dao.getAllOrganizations());

    }

    //********SIGHTINGS************************************************************
    @Test
    public void addGetSightings() {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));

        dao.addCoordinate(coordinate);

        Location location = new Location();
        location.setLocName("My House");
        location.setDescription("A house");
        location.setStreet("1010 E Oak");
        location.setCity("Louisville");
        location.setState("Ky");
        location.setZipCode("40204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);

        dao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setSightingDate(LocalDate.now());

        //ADD SUPER HEROS TO SIGHTING
        //1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        List<SuperHero> superHeros = new ArrayList();
        superHeros.add(superHero);
        //2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("swims");
        dao.addSuperPower(superPower2);

        List<SuperPower> superPowers2 = new ArrayList<>();
        superPowers2.add(superPower2);

        SuperHero superHero2 = new SuperHero();
        superHero2.setSuperHeroName("Jay");
        superHero2.setDescription("young man");
        superHero2.setSuperPowers(superPowers2);
        dao.addSuperHero(superHero2);

        superHeros.add(superHero2);
        sighting.setSuperHeros(superHeros);

        dao.addSighting(sighting);

        Sighting sightFromDao = dao.getSightingByID(sighting.getSightingID());
        assertEquals(sighting, sightFromDao);
    }

    @Test
    public void deleteSighting() {

        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));

        dao.addCoordinate(coordinate);

        Location location = new Location();
        location.setLocName("My House");
        location.setDescription("A house");
        location.setStreet("1010 E Oak");
        location.setCity("Louisville");
        location.setState("Ky");
        location.setZipCode("40204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);

        dao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setSightingDate(LocalDate.now());

        //ADD SUPER HERO TO SIGHTING
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        List<SuperHero> superHeros = new ArrayList();
        superHeros.add(superHero);

        sighting.setSuperHeros(superHeros);

        dao.addSighting(sighting);

        dao.deleteSighting(sighting.getSightingID());

        assertNull(dao.getSightingByID(sighting.getSightingID()));
    }

    @Test
    public void updateSighting() {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));

        dao.addCoordinate(coordinate);

        Location location = new Location();
        location.setLocName("My House");
        location.setDescription("A house");
        location.setStreet("1010 E Oak");
        location.setCity("Louisville");
        location.setState("Ky");
        location.setZipCode("40204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);

        dao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setSightingDate(LocalDate.now());

        //ADD SUPER HERO TO SIGHTING
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        List<SuperHero> superHeros = new ArrayList();
        superHeros.add(superHero);

        sighting.setSuperHeros(superHeros);

        dao.addSighting(sighting);

        sighting.setSightingDate(LocalDate.parse("2017-01-01"));
        dao.updateSighting(sighting);
        Sighting sightFromDao = dao.getSightingByID(sighting.getSightingID());
        assertEquals(sightFromDao.getSightingDate(), LocalDate.parse("2017-01-01"));

    }

    @Test //UPDATE SIGHTING WITH NULL LOCATION
    public void updateSightingNullLoc() {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));

        dao.addCoordinate(coordinate);

        Location location = new Location();
        location.setLocName("My House");
        location.setDescription("A house");
        location.setStreet("1010 E Oak");
        location.setCity("Louisville");
        location.setState("Ky");
        location.setZipCode("40204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);

        dao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setSightingDate(LocalDate.now());

        //ADD SUPER HERO TO SIGHTING
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        List<SuperHero> superHeros = new ArrayList();
        superHeros.add(superHero);

        sighting.setSuperHeros(superHeros);

        dao.addSighting(sighting);

        dao.deleteLocation(location.getLocationID());
        Sighting sightFromDao = dao.getSightingByID(sighting.getSightingID());

        dao.updateSighting(sightFromDao);
        assertNull(sightFromDao.getLocation());
    }

    @Test
    public void getAllSightings() {
        //ADD COORD1
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));
        dao.addCoordinate(coordinate);

        //ADD COORD2
        Coordinate coordinate2 = new Coordinate();
        coordinate2.setLatitude(new BigDecimal("600"));
        coordinate2.setLongitude(new BigDecimal("650"));
        dao.addCoordinate(coordinate2);

        //ADD LOCATION1
        Location location = new Location();
        location.setLocName("Your House");
        location.setDescription("the house");
        location.setStreet("101 E Oak");
        location.setCity("Losville");
        location.setState("K");
        location.setZipCode("4204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);
        dao.addLocation(location);

        //ADD LOCATION2
        Location location2 = new Location();
        location2.setLocName("My House");
        location2.setDescription("A house");
        location2.setStreet("1010 E Oak");
        location2.setCity("Louisville");
        location2.setState("Ky");
        location2.setZipCode("40204");
        location2.setCountry("USA");
        location2.setCoordinate(coordinate2);
        dao.addLocation(location2);

        //SIGHTING 1
        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setSightingDate(LocalDate.now()); //DATE OF SIGHTING1!!!!!

        //ADD SUPER HEROS TO SIGHTING1
        //CREATE SUPERPOWER AND SUPERHERO 1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        List<SuperHero> superHeros = new ArrayList();
        superHeros.add(superHero); //ADD SUPER HERO1 TO LIST1 OF SUPERHEROS
        //CREATE SUPER HERO AND SUPER POWER 2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("swims");
        dao.addSuperPower(superPower2);

        List<SuperPower> superPowers2 = new ArrayList<>();
        superPowers2.add(superPower2);

        SuperHero superHero2 = new SuperHero();
        superHero2.setSuperHeroName("Jay");
        superHero2.setDescription("young man");
        superHero2.setSuperPowers(superPowers2);
        dao.addSuperHero(superHero2);

        superHeros.add(superHero2); //ADD SECOND SUPER HERO TO LIST1 
        sighting.setSuperHeros(superHeros); //ADD THE TWO SUPER HEROS TO SIHGITN 1

        dao.addSighting(sighting); //ADD FIRST SIGHTING WHICH HAS SH 1&2

        //SIGHTING 2
        Sighting sighting2 = new Sighting();
        sighting2.setLocation(location2);
        sighting2.setSightingDate(LocalDate.now()); //DATE FOR SECOND SIGHTING!!!!
        List<SuperHero> superHeros2 = new ArrayList();
        superHeros2.add(superHero);
        sighting2.setSuperHeros(superHeros2); //ADD SUPER HERO1 (ONLY) TO SIGHTING2

        dao.addSighting(sighting2); //ADD SECOND SIGHTING WHICH HAS SH 1 ONNLY

        //TEST FOR BOTH SIGHTINGS
        List<Sighting> expectedResult1 = new ArrayList<>();
        expectedResult1.add(sighting);
        expectedResult1.add(sighting2);
        assertEquals(expectedResult1, dao.getAllSightings());

    }

    //====================================================
    //ADDITIONAL METHODS
    //====================================================
    @Test
    public void getAllLocBySuperHero() {
        //ADD COORD1
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));
        dao.addCoordinate(coordinate);

        //ADD COORD2
        Coordinate coordinate2 = new Coordinate();
        coordinate2.setLatitude(new BigDecimal("600"));
        coordinate2.setLongitude(new BigDecimal("650"));
        dao.addCoordinate(coordinate2);

        //ADD LOCATION1
        Location location = new Location();
        location.setLocName("Your House");
        location.setDescription("the house");
        location.setStreet("101 E Oak");
        location.setCity("Losville");
        location.setState("K");
        location.setZipCode("4204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);
        dao.addLocation(location);

        //ADD LOCATION2
        Location location2 = new Location();
        location2.setLocName("My House");
        location2.setDescription("A house");
        location2.setStreet("1010 E Oak");
        location2.setCity("Louisville");
        location2.setState("Ky");
        location2.setZipCode("40204");
        location2.setCountry("USA");
        location2.setCoordinate(coordinate2);
        dao.addLocation(location2);

        //SIGHTING 1
        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setSightingDate(LocalDate.now());

        //ADD SUPER HEROS TO SIGHTING1
        //CREATE SUPERPOWER AND SUPERHERO 1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        List<SuperHero> superHeros = new ArrayList();
        superHeros.add(superHero); //ADD SUPER HERO1 TO LIST1 OF SUPERHEROS
        //CREATE SUPER HERO AND SUPER POWER 2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("swims");
        dao.addSuperPower(superPower2);

        List<SuperPower> superPowers2 = new ArrayList<>();
        superPowers2.add(superPower2);

        SuperHero superHero2 = new SuperHero();
        superHero2.setSuperHeroName("Jay");
        superHero2.setDescription("young man");
        superHero2.setSuperPowers(superPowers2);
        dao.addSuperHero(superHero2);

        superHeros.add(superHero2); //ADD SECOND SUPER HERO TO LIST1 
        sighting.setSuperHeros(superHeros); //ADD THE TWO SUPER HEROS TO SIHGITN 1

        dao.addSighting(sighting); //ADD FIRST SIGHTING WHICH HAS SH 1&2

        //SIGHTING 2
        Sighting sighting2 = new Sighting();
        sighting2.setLocation(location2);
        sighting2.setSightingDate(LocalDate.now());
        List<SuperHero> superHeros2 = new ArrayList();
        superHeros2.add(superHero);
        sighting2.setSuperHeros(superHeros2); //ADD SUPER HERO1 (ONLY) TO SIGHTING2

        dao.addSighting(sighting2); //ADD SECOND SIGHTING WHICH HAS SH 1 ONNLY

        //TEST THAT SUPER HERO 1 HAS 2 LOCS
        List<Location> superHero1Locations;
        superHero1Locations = dao.getAllLocBySuperHero(superHero.getSuperHeroID());
        //SET ALL LOCS FOR SUPER HERO 1, THIS SHOULD BE LOC 1 & 2
        List<Location> expectedResult1 = new ArrayList<>();
        expectedResult1.add(location);
        expectedResult1.add(location2);
        assertEquals(expectedResult1, superHero1Locations);

        //TEST THAT SUPER HERO 2 HAS 1 LOC
        List<Location> superHero2Locations;
        superHero2Locations = dao.getAllLocBySuperHero(superHero2.getSuperHeroID());
        //SET LOC FOR SUPER HERO 2, THIS SHOULD BE JUST LOC 1
        List<Location> expectedResult2 = new ArrayList<>();
        expectedResult2.add(location);
        assertEquals(expectedResult2, superHero2Locations);

    }

    public void getAllOrgsBySuperHeros() {
        //ADD COORD1
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));
        dao.addCoordinate(coordinate);

        //ADD COORD2
        Coordinate coordinate2 = new Coordinate();
        coordinate2.setLatitude(new BigDecimal("600"));
        coordinate2.setLongitude(new BigDecimal("650"));
        dao.addCoordinate(coordinate2);

        //ADD LOCATION1
        Location location = new Location();
        location.setLocName("Your House");
        location.setDescription("the house");
        location.setStreet("101 E Oak");
        location.setCity("Losville");
        location.setState("K");
        location.setZipCode("4204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);
        dao.addLocation(location);

        //ADD LOCATION2
        Location location2 = new Location();
        location2.setLocName("My House");
        location2.setDescription("A house");
        location2.setStreet("1010 E Oak");
        location2.setCity("Louisville");
        location2.setState("Ky");
        location2.setZipCode("40204");
        location2.setCountry("USA");
        location2.setCoordinate(coordinate2);
        dao.addLocation(location2);

        //CREATE SUPERPOWER AND SUPERHERO 1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        //CREATE SUPER HERO AND SUPER POWER 2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("swims");
        dao.addSuperPower(superPower2);

        List<SuperPower> superPowers2 = new ArrayList<>();
        superPowers2.add(superPower2);

        SuperHero superHero2 = new SuperHero();
        superHero2.setSuperHeroName("Jay");
        superHero2.setDescription("young man");
        superHero2.setSuperPowers(superPowers2);
        dao.addSuperHero(superHero2);

        //ADD ORGANIZATIONS
        //ORG1
        Organization org = new Organization();
        org.setOrgName("My Org");
        org.setDescription("The best");
        org.setPhoneNumber("256-679-9079");
        org.setEmail("sauer@gmail.com");
        org.setLocation(location);
        //ADD SUPERHEROS TO ORG1
        List<SuperHero> members = new ArrayList();
        members.add(superHero);
        members.add(superHero2);
        org.setMembers(members);
        dao.addOrganization(org);

        //ORG2
        Organization org2 = new Organization();
        org2.setOrgName("My Org");
        org2.setDescription("The best");
        org2.setPhoneNumber("256-679-9079");
        org2.setEmail("sauer@gmail.com");
        org2.setLocation(location2);
        //ADD SUPERHEROS TO ORG1
        List<SuperHero> members2 = new ArrayList();
        members2.add(superHero);
        org2.setMembers(members2);
        dao.addOrganization(org2);

        //CHECK THAT SUP 1 IS IN 2 ORGS
        List<Organization> expectedResultSup1 = new ArrayList<>();
        expectedResultSup1.add(org);
        expectedResultSup1.add(org2);
        assertEquals(expectedResultSup1, dao.getAllOrgsBySuperHeros(superHero.getSuperHeroID()));

        //CHECK THAT SUP 2 IS IN 1 ORG
        List<Organization> expectedResultSup2 = new ArrayList<>();
        expectedResultSup2.add(org);

        assertEquals(expectedResultSup2, dao.getAllOrgsBySuperHeros(superHero2.getSuperHeroID()));

    }

    @Test
    public void getAllSightingsByDate() {
        //ADD COORD1
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));
        dao.addCoordinate(coordinate);

        //ADD COORD2
        Coordinate coordinate2 = new Coordinate();
        coordinate2.setLatitude(new BigDecimal("600"));
        coordinate2.setLongitude(new BigDecimal("650"));
        dao.addCoordinate(coordinate2);

        //ADD LOCATION1
        Location location = new Location();
        location.setLocName("Your House");
        location.setDescription("the house");
        location.setStreet("101 E Oak");
        location.setCity("Losville");
        location.setState("K");
        location.setZipCode("4204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);
        dao.addLocation(location);

        //ADD LOCATION2
        Location location2 = new Location();
        location2.setLocName("My House");
        location2.setDescription("A house");
        location2.setStreet("1010 E Oak");
        location2.setCity("Louisville");
        location2.setState("Ky");
        location2.setZipCode("40204");
        location2.setCountry("USA");
        location2.setCoordinate(coordinate2);
        dao.addLocation(location2);

        //SIGHTING 1
        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setSightingDate(LocalDate.now()); //DATE OF SIGHTING1!

        //ADD SUPER HEROS TO SIGHTING1
        //CREATE SUPERPOWER AND SUPERHERO 1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        List<SuperHero> superHeros = new ArrayList();
        superHeros.add(superHero); //ADD SUPER HERO1 TO LIST1 OF SUPERHEROS
        //CREATE SUPER HERO AND SUPER POWER 2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("swims");
        dao.addSuperPower(superPower2);

        List<SuperPower> superPowers2 = new ArrayList<>();
        superPowers2.add(superPower2);

        SuperHero superHero2 = new SuperHero();
        superHero2.setSuperHeroName("Jay");
        superHero2.setDescription("young man");
        superHero2.setSuperPowers(superPowers2);
        dao.addSuperHero(superHero2);

        superHeros.add(superHero2); //ADD SECOND SUPER HERO TO LIST1 
        sighting.setSuperHeros(superHeros); //ADD THE TWO SUPER HEROS TO SIHGITN 1

        dao.addSighting(sighting); //ADD FIRST SIGHTING WHICH HAS SH 1&2

        //SIGHTING 2
        Sighting sighting2 = new Sighting();
        sighting2.setLocation(location2);
        sighting2.setSightingDate(LocalDate.now().minusDays(1)); //DATE FOR SECOND SIGHTING!!!!
        List<SuperHero> superHeros2 = new ArrayList();
        superHeros2.add(superHero);
        sighting2.setSuperHeros(superHeros2); //ADD SUPER HERO1 (ONLY) TO SIGHTING2

        dao.addSighting(sighting2); //ADD SECOND SIGHTING WHICH HAS SH 1 ONNLY

        //TEST FOR BOTH SIGHTINGS
        List<Sighting> expectedResult1 = new ArrayList<>();
        expectedResult1.add(sighting);
        assertEquals(expectedResult1, dao.getAllSightingsByDate(LocalDate.now()));

    }

    @Test
    public void getAllSuperHerosByLoc() {
        //ADD COORD1
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));
        dao.addCoordinate(coordinate);

        //ADD COORD2
        Coordinate coordinate2 = new Coordinate();
        coordinate2.setLatitude(new BigDecimal("600"));
        coordinate2.setLongitude(new BigDecimal("650"));
        dao.addCoordinate(coordinate2);

        //ADD LOCATION1
        Location location = new Location();
        location.setLocName("Your House");
        location.setDescription("the house");
        location.setStreet("101 E Oak");
        location.setCity("Losville");
        location.setState("K");
        location.setZipCode("4204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);
        dao.addLocation(location);

        //ADD LOCATION2
        Location location2 = new Location();
        location2.setLocName("My House");
        location2.setDescription("A house");
        location2.setStreet("1010 E Oak");
        location2.setCity("Louisville");
        location2.setState("Ky");
        location2.setZipCode("40204");
        location2.setCountry("USA");
        location2.setCoordinate(coordinate2);
        dao.addLocation(location2);

        //SIGHTING 1
        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setSightingDate(LocalDate.now()); //DATE OF SIGHTING1

        //ADD SUPER HEROS TO SIGHTING1
        //CREATE SUPERPOWER AND SUPERHERO 1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        List<SuperHero> superHeros = new ArrayList();
        superHeros.add(superHero); //ADD SUPER HERO1 TO LIST1 OF SUPERHEROS
        //CREATE SUPER HERO AND SUPER POWER 2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("swims");
        dao.addSuperPower(superPower2);

        List<SuperPower> superPowers2 = new ArrayList<>();
        superPowers2.add(superPower2);

        SuperHero superHero2 = new SuperHero();
        superHero2.setSuperHeroName("Jay");
        superHero2.setDescription("young man");
        superHero2.setSuperPowers(superPowers2);
        dao.addSuperHero(superHero2);

        superHeros.add(superHero2); //ADD SECOND SUPER HERO TO LIST1 
        sighting.setSuperHeros(superHeros); //ADD THE TWO SUPER HEROS TO SIHGITN 1

        dao.addSighting(sighting); //ADD FIRST SIGHTING WHICH HAS SH 1&2

        //SIGHTING 2
        Sighting sighting2 = new Sighting();
        sighting2.setLocation(location2);
        sighting2.setSightingDate(LocalDate.now()); //DATE FOR SECOND SIGHTING!!!!
        List<SuperHero> superHeros2 = new ArrayList();
        superHeros2.add(superHero);
        sighting2.setSuperHeros(superHeros2); //ADD SUPER HERO1 (ONLY) TO SIGHTING2

        dao.addSighting(sighting2); //ADD SECOND SIGHTING WHICH HAS SH 1 ONNLY

        //TEST LOC 1
        List<SuperHero> expectedResultLoc1 = new ArrayList<>();
        expectedResultLoc1.add(superHero);
        expectedResultLoc1.add(superHero2);
        assertEquals(expectedResultLoc1, dao.getAllSuperHerosByLoc(location.getLocationID()));

        //TEST LOC 2
        List<SuperHero> expectedResultLoc2 = new ArrayList<>();
        expectedResultLoc2.add(superHero);
        assertEquals(expectedResultLoc2, dao.getAllSuperHerosByLoc(location2.getLocationID()));

    }

    @Test
    public void getAllSuperHerosByOrg() {
        //ADD COORD1
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));
        dao.addCoordinate(coordinate);

        //ADD COORD2
        Coordinate coordinate2 = new Coordinate();
        coordinate2.setLatitude(new BigDecimal("600"));
        coordinate2.setLongitude(new BigDecimal("650"));
        dao.addCoordinate(coordinate2);

        //ADD LOCATION1
        Location location = new Location();
        location.setLocName("Your House");
        location.setDescription("the house");
        location.setStreet("101 E Oak");
        location.setCity("Losville");
        location.setState("K");
        location.setZipCode("4204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);
        dao.addLocation(location);

        //ADD LOCATION2
        Location location2 = new Location();
        location2.setLocName("My House");
        location2.setDescription("A house");
        location2.setStreet("1010 E Oak");
        location2.setCity("Louisville");
        location2.setState("Ky");
        location2.setZipCode("40204");
        location2.setCountry("USA");
        location2.setCoordinate(coordinate2);
        dao.addLocation(location2);

        //CREATE SUPERPOWER AND SUPERHERO 1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        //CREATE SUPER HERO AND SUPER POWER 2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("swims");
        dao.addSuperPower(superPower2);

        List<SuperPower> superPowers2 = new ArrayList<>();
        superPowers2.add(superPower2);

        SuperHero superHero2 = new SuperHero();
        superHero2.setSuperHeroName("Jay");
        superHero2.setDescription("young man");
        superHero2.setSuperPowers(superPowers2);
        dao.addSuperHero(superHero2);

        //ADD ORGANIZATIONS
        //ORG1
        Organization org = new Organization();
        org.setOrgName("My Org");
        org.setDescription("The best");
        org.setPhoneNumber("256-679-9079");
        org.setEmail("sauer@gmail.com");
        org.setLocation(location);
        //ADD SUPERHEROS TO ORG1
        List<SuperHero> members = new ArrayList();
        members.add(superHero);  //ORG 1 HAS SUPHERO 1&2
        members.add(superHero2);
        org.setMembers(members);
        dao.addOrganization(org);

        //ORG2
        Organization org2 = new Organization();
        org2.setOrgName("My Org");
        org2.setDescription("The best");
        org2.setPhoneNumber("256-679-9079");
        org2.setEmail("sauer@gmail.com");
        org2.setLocation(location2);
        //ADD SUPERHEROS TO ORG1
        List<SuperHero> members2 = new ArrayList();
        members2.add(superHero); //ORG 2 ONLY HAS SUPHERO 1
        org2.setMembers(members2);
        dao.addOrganization(org2);

        //CHECK THAT ORG 1 HAS 2 SUPER HEROS
        List<SuperHero> expectedResultOrg1 = new ArrayList<>();
        expectedResultOrg1.add(superHero);
        expectedResultOrg1.add(superHero2);
        assertEquals(expectedResultOrg1, dao.getAllSuperHerosByOrg(org.getOrganizationID()));

        //CHECK THAT ORG 2 HAS 1 SUPER HEROS
        List<SuperHero> expectedResultOrg2 = new ArrayList<>();
        expectedResultOrg2.add(superHero);
        assertEquals(expectedResultOrg2, dao.getAllSuperHerosByOrg(org2.getOrganizationID()));
    }

    //****************BRIDGE TABLE-DELETE-TESTING****************************
    @Test
    public void deleteMemberShipBridgeCheckOnOrgDel() {
        //ADD COORD1
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));
        dao.addCoordinate(coordinate);

        //ADD COORD2
        Coordinate coordinate2 = new Coordinate();
        coordinate2.setLatitude(new BigDecimal("600"));
        coordinate2.setLongitude(new BigDecimal("650"));
        dao.addCoordinate(coordinate2);

        //ADD LOCATION1
        Location location = new Location();
        location.setLocName("Your House");
        location.setDescription("the house");
        location.setStreet("101 E Oak");
        location.setCity("Losville");
        location.setState("K");
        location.setZipCode("4204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);
        dao.addLocation(location);

        //ADD LOCATION2
        Location location2 = new Location();
        location2.setLocName("My House");
        location2.setDescription("A house");
        location2.setStreet("1010 E Oak");
        location2.setCity("Louisville");
        location2.setState("Ky");
        location2.setZipCode("40204");
        location2.setCountry("USA");
        location2.setCoordinate(coordinate2);
        dao.addLocation(location2);

        //CREATE SUPERPOWER AND SUPERHERO 1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        //CREATE SUPER HERO AND SUPER POWER 2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("swims");
        dao.addSuperPower(superPower2);

        List<SuperPower> superPowers2 = new ArrayList<>();
        superPowers2.add(superPower2);

        SuperHero superHero2 = new SuperHero();
        superHero2.setSuperHeroName("Jay");
        superHero2.setDescription("young man");
        superHero2.setSuperPowers(superPowers2);
        dao.addSuperHero(superHero2);

        //ADD ORGANIZATIONS
        //ORG1
        Organization org = new Organization();
        org.setOrgName("My Org");
        org.setDescription("The best");
        org.setPhoneNumber("256-679-9079");
        org.setEmail("sauer@gmail.com");
        org.setLocation(location);
        //ADD SUPERHEROS TO ORG1
        List<SuperHero> members = new ArrayList();
        members.add(superHero);  //ORG 1 HAS SUPHERO 1&2
        members.add(superHero2);
        org.setMembers(members);
        dao.addOrganization(org);

        //ORG2
        Organization org2 = new Organization();
        org2.setOrgName("My Org");
        org2.setDescription("The best");
        org2.setPhoneNumber("256-679-9079");
        org2.setEmail("sauer@gmail.com");
        org2.setLocation(location2);
        //ADD SUPERHEROS TO ORG1
        List<SuperHero> members2 = new ArrayList();
        members2.add(superHero); //ORG 2 ONLY HAS SUPHERO 1
        org2.setMembers(members2);
        dao.addOrganization(org2);

        //DELETE ORG 1, AND GET ALL ORGS FOR SH2, THIS SHOULD BE EMPTY
        //BC SUPER HERO 2 WAS ONLY IN ORG 1
        dao.deleteOrganization(org.getOrganizationID());
        //CREATE EMPTY LIST
        List<SuperHero> expectedResultOrg = new ArrayList<>();
        assertEquals(dao.getAllOrgsBySuperHeros(superHero2.getSuperHeroID()), expectedResultOrg);

    }

    @Test
    public void SuperHeroSightingBridgeCheckOnSiDel() {
        //ADD COORD1
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));
        dao.addCoordinate(coordinate);

        //ADD COORD2
        Coordinate coordinate2 = new Coordinate();
        coordinate2.setLatitude(new BigDecimal("600"));
        coordinate2.setLongitude(new BigDecimal("650"));
        dao.addCoordinate(coordinate2);

        //ADD LOCATION1
        Location location = new Location();
        location.setLocName("Your House");
        location.setDescription("the house");
        location.setStreet("101 E Oak");
        location.setCity("Losville");
        location.setState("K");
        location.setZipCode("4204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);
        dao.addLocation(location);

        //ADD LOCATION2
        Location location2 = new Location();
        location2.setLocName("My House");
        location2.setDescription("A house");
        location2.setStreet("1010 E Oak");
        location2.setCity("Louisville");
        location2.setState("Ky");
        location2.setZipCode("40204");
        location2.setCountry("USA");
        location2.setCoordinate(coordinate2);
        dao.addLocation(location2);

        //SIGHTING 1
        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setSightingDate(LocalDate.now());

        //ADD SUPER HEROS TO SIGHTING1
        //CREATE SUPERPOWER AND SUPERHERO 1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        List<SuperHero> superHeros = new ArrayList();
        superHeros.add(superHero); //ADD SUPER HERO1 TO LIST1 OF SUPERHEROS
        //CREATE SUPER HERO AND SUPER POWER 2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("swims");
        dao.addSuperPower(superPower2);

        List<SuperPower> superPowers2 = new ArrayList<>();
        superPowers2.add(superPower2);

        SuperHero superHero2 = new SuperHero();
        superHero2.setSuperHeroName("Jay");
        superHero2.setDescription("young man");
        superHero2.setSuperPowers(superPowers2);
        dao.addSuperHero(superHero2);

        superHeros.add(superHero2); //ADD SECOND SUPER HERO TO LIST1 
        sighting.setSuperHeros(superHeros); //ADD THE TWO SUPER HEROS TO SIHGITN 1

        dao.addSighting(sighting); //ADD FIRST SIGHTING WHICH HAS SH 1&2

        //SIGHTING 2
        Sighting sighting2 = new Sighting();
        sighting2.setLocation(location2);
        sighting2.setSightingDate(LocalDate.now());
        List<SuperHero> superHeros2 = new ArrayList();
        superHeros2.add(superHero);
        sighting2.setSuperHeros(superHeros2); //ADD SUPER HERO1 (ONLY) TO SIGHTING2

        dao.addSighting(sighting2); //ADD SECOND SIGHTING WHICH HAS SH 1 ONNLY

        //DELETE SIGHTING 1, AND GET ALL SIGHTINGS FOR SH2, THIS SHOULD BE EMPTY
        //BC SUPER HERO 2 WAS ONLY IN SIGHTING 1
        dao.deleteSighting(sighting.getSightingID());
        //CREATE EMPTY LIST
        List<SuperHero> expectedResultSight = new ArrayList<>();
        assertEquals(dao.getAllOrgsBySuperHeros(superHero2.getSuperHeroID()), expectedResultSight);

    }

    @Test
    public void deleteMemberShipBridgeCheckOnSHDelete() {
        //ADD COORD1
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));
        dao.addCoordinate(coordinate);

        //ADD COORD2
        Coordinate coordinate2 = new Coordinate();
        coordinate2.setLatitude(new BigDecimal("600"));
        coordinate2.setLongitude(new BigDecimal("650"));
        dao.addCoordinate(coordinate2);

        //ADD LOCATION1
        Location location = new Location();
        location.setLocName("Your House");
        location.setDescription("the house");
        location.setStreet("101 E Oak");
        location.setCity("Losville");
        location.setState("K");
        location.setZipCode("4204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);
        dao.addLocation(location);

        //ADD LOCATION2
        Location location2 = new Location();
        location2.setLocName("My House");
        location2.setDescription("A house");
        location2.setStreet("1010 E Oak");
        location2.setCity("Louisville");
        location2.setState("Ky");
        location2.setZipCode("40204");
        location2.setCountry("USA");
        location2.setCoordinate(coordinate2);
        dao.addLocation(location2);

        //CREATE SUPERPOWER AND SUPERHERO 1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        //CREATE SUPER HERO AND SUPER POWER 2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("swims");
        dao.addSuperPower(superPower2);

        List<SuperPower> superPowers2 = new ArrayList<>();
        superPowers2.add(superPower2);

        SuperHero superHero2 = new SuperHero();
        superHero2.setSuperHeroName("Jay");
        superHero2.setDescription("young man");
        superHero2.setSuperPowers(superPowers2);
        dao.addSuperHero(superHero2);

        //ADD ORGANIZATIONS
        //ORG1
        Organization org = new Organization();
        org.setOrgName("My Org");
        org.setDescription("The best");
        org.setPhoneNumber("256-679-9079");
        org.setEmail("sauer@gmail.com");
        org.setLocation(location);
        //ADD SUPERHEROS TO ORG1
        List<SuperHero> members = new ArrayList();
        members.add(superHero);  //ORG 1 HAS SUPHERO 1&2
        members.add(superHero2);
        org.setMembers(members);
        dao.addOrganization(org);

        //ORG2
        Organization org2 = new Organization();
        org2.setOrgName("My Org");
        org2.setDescription("The best");
        org2.setPhoneNumber("256-679-9079");
        org2.setEmail("sauer@gmail.com");
        org2.setLocation(location2);
        //ADD SUPERHEROS TO ORG1
        List<SuperHero> members2 = new ArrayList();
        members2.add(superHero); //ORG 2 ONLY HAS SUPHERO 1
        org2.setMembers(members2);
        dao.addOrganization(org2);

        //DELETE SH 2 AND ASSERT ORG1 NOW ONLY HAS ONE MEMBER (SH1)
        dao.deleteSuperHero(superHero2.getSuperHeroID());
        Organization orgFromDao = dao.getOrganizationByID(org.getOrganizationID());
        assertEquals(orgFromDao.getMembers().size(), 1);
        assertEquals(orgFromDao.getMembers().get(0), superHero);
    }

    @Test
    public void SuperHeroSightingBridgeCheckOnSHDel() {
        //ADD COORD1
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(new BigDecimal("500"));
        coordinate.setLongitude(new BigDecimal("550"));
        dao.addCoordinate(coordinate);

        //ADD COORD2
        Coordinate coordinate2 = new Coordinate();
        coordinate2.setLatitude(new BigDecimal("600"));
        coordinate2.setLongitude(new BigDecimal("650"));
        dao.addCoordinate(coordinate2);

        //ADD LOCATION1
        Location location = new Location();
        location.setLocName("Your House");
        location.setDescription("the house");
        location.setStreet("101 E Oak");
        location.setCity("Losville");
        location.setState("K");
        location.setZipCode("4204");
        location.setCountry("USA");
        location.setCoordinate(coordinate);
        dao.addLocation(location);

        //ADD LOCATION2
        Location location2 = new Location();
        location2.setLocName("My House");
        location2.setDescription("A house");
        location2.setStreet("1010 E Oak");
        location2.setCity("Louisville");
        location2.setState("Ky");
        location2.setZipCode("40204");
        location2.setCountry("USA");
        location2.setCoordinate(coordinate2);
        dao.addLocation(location2);

        //SIGHTING 1
        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setSightingDate(LocalDate.now());

        //ADD SUPER HEROS TO SIGHTING1
        //CREATE SUPERPOWER AND SUPERHERO 1
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower("flys");
        dao.addSuperPower(superPower);

        List<SuperPower> superPowers = new ArrayList<>();
        superPowers.add(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperHeroName("Bob");
        superHero.setDescription("old man");
        superHero.setSuperPowers(superPowers);
        dao.addSuperHero(superHero);

        List<SuperHero> superHeros = new ArrayList();
        superHeros.add(superHero); //ADD SUPER HERO1 TO LIST1 OF SUPERHEROS
        //CREATE SUPER HERO AND SUPER POWER 2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPower("swims");
        dao.addSuperPower(superPower2);

        List<SuperPower> superPowers2 = new ArrayList<>();
        superPowers2.add(superPower2);

        SuperHero superHero2 = new SuperHero();
        superHero2.setSuperHeroName("Jay");
        superHero2.setDescription("young man");
        superHero2.setSuperPowers(superPowers2);
        dao.addSuperHero(superHero2);

        superHeros.add(superHero2); //ADD SECOND SUPER HERO TO LIST1 
        sighting.setSuperHeros(superHeros); //ADD THE TWO SUPER HEROS TO SIHGITN 1

        dao.addSighting(sighting); //ADD FIRST SIGHTING WHICH HAS SH 1&2

        //SIGHTING 2
        Sighting sighting2 = new Sighting();
        sighting2.setLocation(location2);
        sighting2.setSightingDate(LocalDate.now());
        List<SuperHero> superHeros2 = new ArrayList();
        superHeros2.add(superHero);
        sighting2.setSuperHeros(superHeros2); //ADD SUPER HERO1 (ONLY) TO SIGHTING2

        dao.addSighting(sighting2); //ADD SECOND SIGHTING WHICH HAS SH 1 ONNLY

        //DELETE SH 2 AND ASSERT SIGHTING1 NOW ONLY HAS ONE SH (SH1)
        dao.deleteSuperHero(superHero2.getSuperHeroID());
        Sighting sightFromDao = dao.getSightingByID(sighting.getSightingID());
        assertEquals(sightFromDao.getSuperHeros().size(), 1);
        assertEquals(sightFromDao.getSuperHeros().get(0), superHero);
    }

}
