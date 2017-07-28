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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import static java.util.Collections.emptyList;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author apprentice
 */
public class SuperHeroDaoJdbcImpl implements SuperHeroDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //================================================================
    //PREPARED STATEMENTS
    //================================================================
    //*****COORDINATES******
    //CRUD
    private static final String SQL_INSERT_COORDINATE
            = "insert into Coordinate (Latitude, Longitude)"
            + " values (?, ?)";

    private static final String SQL_DELETE_COORDINATE
            = "delete from Coordinate where CoordinateID = ?";

    private static final String SQL_UPDATE_COORDINATE
            = "update Coordinate set Latitude = ?, Longitude = ? "
            + " where CoordinateID  =  ?";

    private static final String SQL_SELECT_COORDINATE
            = "select * from Coordinate where CoordinateID = ?";

    private static final String SQL_SELECT_ALL_COORDINATES
            = "select * from Coordinate";

    //ADDITIONAL select coordinate by location id
    private static final String SQL_SELECT_COOR_BY_LOC_ID
            = "select co.CoordinateID, co.Latitude, co.Longitude"
            + " from Coordinate co"
            + " inner join Location loc on co.CoordinateID = loc.CoordinateID where"
            + " loc.LocationID = ?";

    //*****LOCATIONS******
    //CRUD
    private static final String SQL_INSERT_LOCATION
            = "insert into Location (LocName, Description, Street, City, State, ZipCode, Country, CoordinateID)"
            + " values (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_LOCATION
            = "delete from Location where LocationID = ?";

    private static final String SQL_UPDATE_LOCATION
            = "update Location set LocName=?, Description=?, Street=?, City=?, State=?, ZipCode=?, Country=?, CoordinateID=? "
            + " where LocationID  =  ?";

    private static final String SQL_SELECT_LOCATION
            = "select * from Location where LocationID = ?";

    private static final String SQL_SELECT_ALL_LOCATION
            = "select * from Location";
    //ADDITIONAL
    private static final String SELECT_LOCATIONS_BY_SUPERHERO_ID
            = "select loc.LocationID, loc.LocName, loc.Description, loc.Street, loc.City, loc.State, loc.ZipCode, loc.Country, loc.CoordinateID"
            + " from Location loc"
            + " inner join Sighting si on loc.LocationID = si.LocationID"
            + " inner join SuperHeroSighting shs on si.SightingID = shs.SightingID"
            + " inner join SuperHero sh on shs.SuperHeroID = sh.SuperHeroID"
            + " where sh.SuperHeroID  =  ?";

    private static final String SET_LOCATION_COORD_ID_NULL
            = "update Location loc set loc.CoordinateID = NULL"
            + " where loc.CoordinateID = ?";

    //SELECT LOCATION BY ORG ID
    private static final String SELECT_LOCATION_BY_ORGID
            = "select loc.LocationID, loc.LocName, loc.Description, loc.Street, loc.City, loc.State, loc.ZipCode, loc.Country, loc.CoordinateID"
            + " from Location loc"
            + " inner join Organization org ON loc.LocationID = org.LocationID"
            + " where org.OrganizationID = ?";

    //SELECT LOCATION BY SIGHTING ID
    private static final String SELECT_LOCATION_BY_SIGHTING_ID
            = "select loc.LocationID, loc.LocName, loc.Description, loc.Street, loc.City, loc.State, loc.ZipCode, loc.Country, loc.CoordinateID"
            + " from Location loc"
            + " inner join Sighting si ON loc.LocationID = si.LocationID"
            + " where si.SightingID = ?";

    //Organizations
    //CRUD
    private static final String SQL_INSERT_ORGANIZATION
            = "insert into Organization (OrgName, Description, PhoneNumber, Email, LocationID)"
            + " values (?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_ORGANIZATION
            = "delete from Organization where OrganizationID = ?";

    private static final String SQL_UPDATE_ORGANIZATION
            = "update Organization set OrgName=?, Description=?, PhoneNumber=?, Email=?, LocationID=? "
            + " where OrganizationID  =  ?";

    private static final String SQL_SELECT_ORGANIZATION
            = "select * from Organization where OrganizationID = ?";

    private static final String SQL_SELECT_ALL_ORGANIZATIONS
            = "select * from Organization";
    //ADDITIONAL
    private static final String SELECT_ORGANIZATIONS_BY_SUPERHERO
            = "select org.OrganizationID, org.OrgName, org.Description, org.PhoneNumber, org.Email, org.LocationID"
            + " from Organization org"
            + " inner join Memberships memb on org.OrganizationID = memb.OrganizationID"
            + " inner join SuperHero sh on memb.SuperHeroID = sh.SuperHeroID"
            + " where sh.SuperHeroID =  ?";

    //Sightings
    //CRUD
    private static final String SQL_INSERT_SIGHTING
            = "insert into Sighting (LocationID, SightingDate)"
            + " values (?, ?)";

    private static final String SQL_DELETE_SIGHTING
            = "delete from Sighting where SightingID = ?";

    private static final String SQL_UPDATE_SIGHTING
            = "update Sighting set LocationID=?, SightingDate=?"
            + " where SightingID  =  ?";

    private static final String SQL_SELECT_SIGHTING
            = "select * from Sighting where SightingID = ?";

    private static final String SQL_SELECT_ALL_SIGHTINGS
            = "select * from Sighting";
    //ADDITIONAL
    private static final String SQL_SELECT_SIGHTINGS_BY_DATE
            = "select * from Sighting si"
            + " where si.SightingDate  =  ?";
    private static final String SET_SIGHTING_LOCATION_ID_NULL
            = " update Sighting si set si.LocationID = NULL"
            + " where si.LocationID = ?";
    private static final String SQL_SELECT_SIGHTINGS_LATEST
            = "select * from Sighting si"
            + " ORDER BY si.SightingDate  DESC, si.SightingID DESC"
            + " LIMIT 0,10";

    //SuperHero
    //CRUD
    private static final String SQL_INSERT_SUPERHERO
            = "insert into SuperHero (SuperHeroName, Description)"
            + " values (?, ?)";

    private static final String SQL_DELETE_SUPERHERO
            = "delete from SuperHero where SuperHeroID = ?";

    private static final String SQL_UPDATE_SUPERHERO
            = "update SuperHero set SuperHeroName=?, Description=?"
            + " where SuperHeroID  =  ?";

    private static final String SQL_SELECT_SUPERHERO
            = "select * from SuperHero where SuperHeroID = ?";

    private static final String SQL_SELECT_ALL_SUPERHERO
            = "select * from SuperHero";
    private static final String SQL_DELETE_SIGHTING_HEROS_SHID
            = "delete from SuperHeroSighting where SuperHeroID = ?";
    //ADDITIONAL
    private static final String SELECT_SUPERHEROS_BY_LOC_ID
            = "select sh.SuperHeroID, sh.SuperHeroName, sh.Description"
            + " from Location loc"
            + " inner join Sighting si on loc.LocationID = si.LocationID"
            + " inner join SuperHeroSighting shs on si.SightingID = shs.sightingID"
            + " inner join SuperHero sh on shs.SuperHeroID = sh.SuperHeroID"
            + " where si.LocationID  =  ?";

    private static final String SELECT_SUPERHEROS_BY_ORG_ID
            = "select sh.SuperHeroID, sh.SuperHeroName, sh.Description"
            + " from SuperHero sh"
            + " inner join Memberships memb on sh.SuperHeroID = memb.SuperHeroID"
            + " inner join Organization org on memb.OrganizationID = org.OrganizationID"
            + " where org.OrganizationID  =  ?";

    //SuperPower
    //CRUD
    private static final String SQL_INSERT_SUPERPOWER
            = "insert into SuperPower (SuperPower)"
            + " values (?)";

    private static final String SQL_DELETE_SUPERPOWER
            = "delete from SuperPower where SuperPowerID = ?";

    private static final String SQL_UPDATE_SUPERPOWER
            = "update SuperPower set SuperPower=?"
            + " where SuperPowerID  =  ?";

    private static final String SQL_SELECT_SUPERPOWER
            = "select * from SuperPower where SuperPowerID = ?";

    private static final String SQL_SELECT_ALL_SUPERPOWER
            = "select * from SuperPower";
    //ADITIONAL
    //SELECT_SUPERPOWERS_BY_SUPERHEROID
    private static final String SQL_SELECT_SUPERPOWER_BY_SUPERHEROID
            = "select sp.SuperPowerID, sp.SuperPower"
            + " from SuperHeroSuperPower shsp"
            + " inner join SuperHero sh ON sh.SuperHeroID = shsp.SuperHeroID"
            + " inner join SuperPower sp ON sp.SuperPowerID = shsp.SuperPowerID"
            + " where sh.SuperHeroID = ?";

    //Memberships (BRIDGE)**********************************************
    //SQL_INSERT_ORG_MEMEBERS
    private static final String SQL_INSERT_ORG_MEMBERS
            = "insert into Memberships (OrganizationID, SuperHeroID)"
            + " values (?,?)";
    private static final String SQL_DELETE_ORG_MEMBERS
            = "delete from Memberships where OrganizationID = ?";
    private static final String SQL_SELECT_MEMBERS_BY_ORG_ID
            = "select sh.SuperHeroID, sh.SuperHeroName, sh.Description"
            + " from Memberships mem"
            + " inner join SuperHero sh ON sh.SuperHeroID = mem.SuperHeroID"
            + " where OrganizationID = ?";
    private static final String SQL_DELETE_ALL_MEMBERSHIPS
            = "delete from Memberships";
    private static final String SQL_DELETE_ORG_MEMBERS_SHID
            = "delete from Memberships where SuperHeroID = ?";
    private static final String SET_ORG_LOCATION_ID_NULL
            = " update Organization org set org.LocationID = NULL"
            + " where org.LocationID = ?";

    //SuperHeroSightings (BRIDGE)****************************************
    // SQL_INSERT_SUPERHEROS_IN_SIGHTING  
    private static final String SQL_INSERT_SUPERHEROS_IN_SIGHTING
            = "insert into SuperHeroSighting (SightingID, SuperHeroID)"
            + " values (?,?)";
    //SELECT_SUPERHEROS_BY_SIGHTINGID
    private static final String SELECT_SUPERHEROS_BY_SIGHTINGID
            = "select sh.SuperHeroID, sh.SuperHeroName, sh.Description"
            + " from SuperHeroSighting shs"
            + "  inner join SuperHero sh ON sh.SuperHeroID = shs.SuperHeroID"
            + "  where SightingID = ?";
    private static final String SQL_DELETE_SIGHTING_HEROS
            = "delete from SuperHeroSighting where SightingID = ?";
    private static final String SQL_DELETE_ALL_SUPER_HEROS_IN_SIGHTINGS
            = "delete from SuperHeroSighting";

    //SuperHeroSuperPower (BRIDGE) *************************************
    private static final String SQL_INSERT_SUPERHEROS_SUPERPOWERS
            = "insert into SuperHeroSuperPower (SuperHeroID, SuperPowerID)"
            + " values (?,?)";
    //SELECT_SUPERHEROS_BY_SUPERPOWER
    private static final String SQL_DELETE_SUPERHEROS_SUPERPOWERS
            = "delete from SuperHeroSuperPower where SuperHeroID = ?";
    private static final String SQL_DELETE_SUPERPOWERS_SUPERHEROS
            = "delete from SuperHeroSuperPower where SuperPowerID = ?";
    private static final String SQL_DELETE_ALL_SUPER_HEROS_SUPER_POWERS
            = "delete from SuperHeroSuperPower";
    //JSUT ADDED!!!
    private static final String SQL_SELECT_SUPER_POWERS_BY_SUPER_HERO
            ="SELECT sp.SuperPowerID, sp.SuperPower FROM SuperHeroSuperPower shsp"
            + " inner join SuperPower sp"
            + " on sp.SuperPowerID = shsp.SuperPowerID"
            + " where shsp.SuperHeroID = ?";

    //================================================================
    //METHODS
    //================================================================
    //*****COORDINATES****************************************************
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addCoordinate(Coordinate coordinate) {
        jdbcTemplate.update(SQL_INSERT_COORDINATE,
                coordinate.getLatitude(),
                coordinate.getLongitude());

        int coordinateId
                = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                        Integer.class);

        coordinate.setCoordinateID(coordinateId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteCoordinate(int coordinateId) { 
        try {
            jdbcTemplate.update(SQL_DELETE_COORDINATE, coordinateId);
        } catch (DataIntegrityViolationException e) {
            //SET_LOCATION_COORD_ID_NULL
            jdbcTemplate.update(SET_LOCATION_COORD_ID_NULL, coordinateId);
            jdbcTemplate.update(SQL_DELETE_COORDINATE, coordinateId);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateCoordinate(Coordinate coordinate) {
        jdbcTemplate.update(SQL_UPDATE_COORDINATE,
                coordinate.getLatitude(),
                coordinate.getLongitude(),
                coordinate.getCoordinateID());
    }

    @Override
    public Coordinate getCoordinateByID(int coordinateID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_COORDINATE,
                    new CoordinateMapper(),
                    coordinateID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Coordinate> getAllCoordinates() {
        List<Coordinate> emptyCoordList = new ArrayList<>();
        return jdbcTemplate.query(SQL_SELECT_ALL_COORDINATES,
                new CoordinateMapper());
    }

    //LOCATIONS***********************************************************
    //LOCATION HELPER METHOD1: THIS WILL RETURN THE COORDINATE FOR A GIVEN LOCATION
    private Coordinate findCoordinateForLoc(Location location) {

        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_COOR_BY_LOC_ID,
                    new CoordinateMapper(),
                    location.getLocationID());
        } catch (EmptyResultDataAccessException ex) {

            return null;
        }
    }

    //LOCAITON HELPER METHOD2: THIS WILL ASSOCIATE THE COORDINATE WITH THE LOCATION
    private Location associateLocationWithCoord(Location location) {
        location.setCoordinate(findCoordinateForLoc(location));
        return location;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addLocation(Location location) {
          //CHECK FOR NULL LOC
        Integer coordinateID;
        if (location.getCoordinate() == null){
            coordinateID = null;
        } else {
            coordinateID = location.getCoordinate().getCoordinateID();
        }
        
        jdbcTemplate.update(SQL_INSERT_LOCATION,
                location.getLocName(),
                location.getDescription(),
                location.getStreet(),
                location.getCity(),
                location.getState(),
                location.getZipCode(),
                location.getCountry(),
                coordinateID);

        location.setLocationID(jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteLocation(int locationID) {// throws ReferencedByOtherTableException {
            //SET_SIGHTING_LOCATION_ID TO NULL IN SIGHTING TABLE
            jdbcTemplate.update(SET_SIGHTING_LOCATION_ID_NULL, locationID);
            //SET ORG LOCATION ID TO NULL IN ORG TABLE
            jdbcTemplate.update(SET_ORG_LOCATION_ID_NULL, locationID);
            //DELETE LOCATION
            jdbcTemplate.update(SQL_DELETE_LOCATION, locationID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateLocation(Location location) {
        //CHECK FOR NULL COORD
        Integer coordinateID;
        if (location.getCoordinate() == null){
            coordinateID = null;
        } else {
            coordinateID = location.getCoordinate().getCoordinateID();
        }
        
        jdbcTemplate.update(SQL_UPDATE_LOCATION,
                location.getLocName(),
                location.getDescription(),
                location.getStreet(),
                location.getCity(),
                location.getState(),
                location.getZipCode(),
                location.getCountry(),
                coordinateID, 
                location.getLocationID());
    }

    @Override
    public Location getLocationByID(int locationID) {
        try {
            // get the properties from the loc table
            Location location = jdbcTemplate.queryForObject(SQL_SELECT_LOCATION,
                    new LocationMapper(),
                    locationID);
            // get the Coordinate for this location and set it on the loc
            associateLocationWithCoord(location);
            return location;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        // get all the locations
        List<Location> locList = jdbcTemplate.query(SQL_SELECT_ALL_LOCATION,
                new LocationMapper());
        for (Location loc : locList) {
            associateLocationWithCoord(loc);
        }
        return locList;
    }

    //ADDITIONAL LOCATION METHODS
    @Override
    public List<Location> getAllLocBySuperHero(int superHeroID) {
        // get all the locations by superheroID
        List<Location> locList = jdbcTemplate.query(SELECT_LOCATIONS_BY_SUPERHERO_ID,
                new LocationMapper(),
                superHeroID);
        for (Location loc : locList) {
            associateLocationWithCoord(loc);
        }
        return locList;
    }

    //******SUPER POWERS************************************************
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSuperPower(SuperPower superPower) {
        jdbcTemplate.update(SQL_INSERT_SUPERPOWER,
                superPower.getSuperPower());

        int superPowerId
                = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                        Integer.class);
        superPower.setSuperPowerID(superPowerId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSuperPower(int superPowerID){// throws ReferencedByOtherTableException {
            //DELETE_SUPERPOWERS_SUPERHEROS IN bRidGE
            jdbcTemplate.update(SQL_DELETE_SUPERPOWERS_SUPERHEROS, superPowerID);
            //DELETE SUPER POWER (CAN ONLY DEL SUPER POWERS THAT ARE NOT CURRENTLY REF) 
            jdbcTemplate.update(SQL_DELETE_SUPERPOWER, superPowerID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateSuperPower(SuperPower superPower) {
        jdbcTemplate.update(SQL_UPDATE_SUPERPOWER,
                superPower.getSuperPower(),
                superPower.getSuperPowerID());
    }

    @Override
    public SuperPower getSuperPowerByID(int superPowerID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_SUPERPOWER,
                    new SuperPowerMapper(),
                    superPowerID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<SuperPower> getAllSuperPowers() {
        return jdbcTemplate.query(SQL_SELECT_ALL_SUPERPOWER,
                new SuperPowerMapper());
    }
    
    @Override
    public List<SuperPower> getSuperPowersBySuperHero(int superHeroID){
        return jdbcTemplate.query(SQL_SELECT_SUPER_POWERS_BY_SUPER_HERO,
                new SuperPowerMapper(),
                superHeroID);
    }

    //SUPER HEROS*******************************
    //SUPER HERO HELPER METHOD1: THIS WILL RETURN THE SUPER POWER FOR A GIVEN SUPERHER
    private List<SuperPower> findSuperPowersForHero(SuperHero superHero) {
        return jdbcTemplate.query(SQL_SELECT_SUPERPOWER_BY_SUPERHEROID,
                new SuperPowerMapper(),
                superHero.getSuperHeroID());
    }

    //SUPER HERO HELPER METHOD2: THIS WILL ASSOCIATE THE SUPER POWER WITH THE SUPER HERO
    private SuperHero associateSuperPowersWithSupHero(SuperHero superHero) {
        superHero.setSuperPowers(findSuperPowersForHero(superHero));
        return superHero;
    }

    //SUPER HERO HELPER METHOD3: UPDATE SUPER HERO SUPER POWER BRIDGE TABLE
    private void insertSuperHeroSuperPowers(SuperHero superHero) {
        int superHeroId = superHero.getSuperHeroID();
        if (superHero.getSuperPowers() != null) {
            List<SuperPower> superPowers = superHero.getSuperPowers();
            // Update the  bridge table with an entry for 
            // each super power in superhero
            for (SuperPower currentPower : superPowers) {
                jdbcTemplate.update(SQL_INSERT_SUPERHEROS_SUPERPOWERS,
                        superHeroId,
                        currentPower.getSuperPowerID());
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSuperHero(SuperHero superHero) {
        jdbcTemplate.update(SQL_INSERT_SUPERHERO,
                superHero.getSuperHeroName(),
                superHero.getDescription());

        superHero.setSuperHeroID(jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class));
        //UPDATE SUPER HERO SUPER POWER BRIDGE TABLE
        insertSuperHeroSuperPowers(superHero);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSuperHero(int superHeroID) {
        //DELETE SUPER HEROS SUPER POWERS
        jdbcTemplate.update(SQL_DELETE_SUPERHEROS_SUPERPOWERS, superHeroID);
        //DELETE SUPER HEROS MEMBERSHIPS
        jdbcTemplate.update(SQL_DELETE_ORG_MEMBERS_SHID, superHeroID);
        //DELETE SUPER HEROS SIGHTINGS
        jdbcTemplate.update(SQL_DELETE_SIGHTING_HEROS_SHID, superHeroID);
        //DELETE SUPER HERO
        jdbcTemplate.update(SQL_DELETE_SUPERHERO, superHeroID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateSuperHero(SuperHero superHero) {
        jdbcTemplate.update(SQL_UPDATE_SUPERHERO,
                superHero.getSuperHeroName(),
                superHero.getDescription(),
                superHero.getSuperHeroID());

        //DELETE SUPER HEROS SUPER POWERS
        jdbcTemplate.update(SQL_DELETE_SUPERHEROS_SUPERPOWERS, superHero.getSuperHeroID());
        //RESET ORGS MEMBERS
        insertSuperHeroSuperPowers(superHero);
    }

    @Override
    public SuperHero getSuperHeroByID(int superHeroID) {
        try {
            // get the properties from the super hero table
            SuperHero superHero = jdbcTemplate.queryForObject(SQL_SELECT_SUPERHERO,
                    new SuperHeroMapper(),
                    superHeroID);
            // get the super power for this super hero and set it on the hero
            associateSuperPowersWithSupHero(superHero);
            return superHero;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<SuperHero> getAllSuperHeros() {
        List<SuperHero> supHeroList = jdbcTemplate.query(SQL_SELECT_ALL_SUPERHERO,
                new SuperHeroMapper());
        for (SuperHero supHero : supHeroList) {
            associateSuperPowersWithSupHero(supHero);
        }
        return supHeroList;
    }

    //ADDITIONAL SUPER HERO METHODS
    @Override
    public List<SuperHero> getAllSuperHerosByLoc(int locationID) {
        List<SuperHero> supHeroList = jdbcTemplate.query(SELECT_SUPERHEROS_BY_LOC_ID,
                new SuperHeroMapper(),
                locationID);
        for (SuperHero supHero : supHeroList) {
            associateSuperPowersWithSupHero(supHero);
        }
        return supHeroList;
    }

    @Override
    public List<SuperHero> getAllSuperHerosByOrg(int organizationID) {
        List<SuperHero> supHeroList = jdbcTemplate.query(SELECT_SUPERHEROS_BY_ORG_ID,
                new SuperHeroMapper(),
                organizationID);
        for (SuperHero supHero : supHeroList) {
            associateSuperPowersWithSupHero(supHero);
        }
        return supHeroList;
    }

    //ORGANIZATIONS*************************************************
    //ORGANIZATION HELPER METHOD1: THIS WILL RETURN THE LOCATION FOR A GIVEN ORG
    @Override
    public Location findLocForOrg(Organization org) {
        try {
            Location location = jdbcTemplate.queryForObject(SELECT_LOCATION_BY_ORGID,
                    new LocationMapper(),
                    org.getOrganizationID());
            associateLocationWithCoord(location);
            return location;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    //ORGANIZATION HELPER METHOD2: THIS WILL ASSOCIATE THE LOCATION WITH THE ORANIZATION
    private Organization associateLocationWithOrg(Organization organization) {
        organization.setLocation(findLocForOrg(organization));
        return organization;
    }

    //ORGANIZATION HELPER METHOD3: UPDATE MEMBERSHIP BRIDGE TABLE
    private void insertOrgMembers(Organization org) {
        final int orgId = org.getOrganizationID();
        if (org.getMembers() != null) {
            final List<SuperHero> superHeroMembers = org.getMembers();

            // Update the membership bridge table with an entry for 
            // each super hero in org
            for (SuperHero currentHero : superHeroMembers) {
                jdbcTemplate.update(SQL_INSERT_ORG_MEMBERS,
                        orgId,
                        currentHero.getSuperHeroID());
            }
        }
    }

    //ORGANIZATION HELPER METHOD4: GET MEMBERS FOR ORG - BRIDGETABEL
    private List<SuperHero> findMemberForOrg(Organization org) {
        List<SuperHero> memberList;
        try {
            memberList = jdbcTemplate.query(SQL_SELECT_MEMBERS_BY_ORG_ID,
                    new SuperHeroMapper(),
                    org.getOrganizationID());

            //need to associate my super heros with  super powers
            for (SuperHero currentHero : memberList) {
                associateSuperPowersWithSupHero(currentHero);
            }

            return memberList;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    //ORGANIZATION HELPER METHOD5: THIS WILL ASSOCIATE MEMBERHIP LIST WITH ORG
    private Organization associateMembersWithOrg(Organization organization) {
        if (findMemberForOrg(organization) != null) {
            organization.setMembers(findMemberForOrg(organization));
        }
        return organization;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addOrganization(Organization organization) {
         //CHECK FOR NULL LOC
        Integer locationID;
        if (organization.getLocation() == null){
            locationID = null;
        } else {
            locationID = organization.getLocation().getLocationID();
        }
        
        jdbcTemplate.update(SQL_INSERT_ORGANIZATION,
                organization.getOrgName(),
                organization.getDescription(),
                organization.getPhoneNumber(),
                organization.getEmail(),
                locationID);

        organization.setOrganizationID(jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class));
        //update memberships table
        insertOrgMembers(organization);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteOrganization(int organizationID) {
        //DELETE MEMBERSHIPS FOR THIS ORG
        jdbcTemplate.update(SQL_DELETE_ORG_MEMBERS, organizationID);
        //DELECT ORG
        jdbcTemplate.update(SQL_DELETE_ORGANIZATION, organizationID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateOrganization(Organization organization) {
        //CHECK FOR NULL LOC
        Integer locationID;
        if (organization.getLocation() == null){
            locationID = null;
        } else {
            locationID = organization.getLocation().getLocationID();
        }
        
        jdbcTemplate.update(SQL_UPDATE_ORGANIZATION,
                organization.getOrgName(),
                organization.getDescription(),
                organization.getPhoneNumber(),
                organization.getEmail(),
                locationID,
                organization.getOrganizationID());
        //DELETE ORGS MEMBERS
        jdbcTemplate.update(SQL_DELETE_ORG_MEMBERS, organization.getOrganizationID());
        //RESET ORGS MEMBERS
        insertOrgMembers(organization);
    }

    @Override
    public Organization getOrganizationByID(int organizationID) {
        try {
            Organization organization = jdbcTemplate.queryForObject(SQL_SELECT_ORGANIZATION,
                    new OrganizationsMapper(),
                    organizationID);

            associateLocationWithOrg(organization);
            associateMembersWithOrg(organization);

            return organization;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        List<Organization> orgList = jdbcTemplate.query(SQL_SELECT_ALL_ORGANIZATIONS,
                new OrganizationsMapper());
        for (Organization org : orgList) {
            associateLocationWithOrg(org);
            associateMembersWithOrg(org);
        }
        return orgList;
    }

    //ADDITIONAL
    @Override
    public List<Organization> getAllOrgsBySuperHeros(int superHeroID) {
        List<Organization> orgList = jdbcTemplate.query(SELECT_ORGANIZATIONS_BY_SUPERHERO,
                new OrganizationsMapper(), superHeroID);
        for (Organization org : orgList) {
            associateLocationWithOrg(org);
            associateMembersWithOrg(org);
        }
        return orgList;
    }

    //SIGHTINGS***************************************************************
    //SIGHTING HELPER METHOD1: THIS WILL RETURN THE LOCATION FOR A GIVEN SIGHTING
    private Location findLocForSight(Sighting sighting) {
        //ADDED******FIX***
        try {
            Location location = jdbcTemplate.queryForObject(SELECT_LOCATION_BY_SIGHTING_ID,
                    new LocationMapper(),
                    sighting.getSightingID());
            associateLocationWithCoord(location);
            return location;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    //SIGHTING HELPER METHOD2: THIS WILL ASSOCIATE THE LOCATION WITH THE SIGHTING
    private Sighting associateLocationWithSight(Sighting sighting) {
        sighting.setLocation(findLocForSight(sighting));
        return sighting;
    }

    //SIGHTING HELPER METHOD3: UPDATE SUPERHERO SIGHTING BRIDGE TABLE
    private void insertSightingHeros(Sighting sighting) {
        final int sightingId = sighting.getSightingID();
        if (sighting.getSuperHeros() != null) {
            final List<SuperHero> superHeros = sighting.getSuperHeros();
            // Update the  bridge table with an entry for 
            // each super hero in sighting
            for (SuperHero currentHero : superHeros) {
                jdbcTemplate.update(SQL_INSERT_SUPERHEROS_IN_SIGHTING,
                        sightingId,
                        currentHero.getSuperHeroID());
            }
        }
    }

    //SIGHTING HELPER METHOD4: GET SUPER HEROS FOR SIHGTING - BRIDGETABEL
    private List<SuperHero> findHerosForSighting(Sighting sighting) {
        List<SuperHero> memberList;// = new ArrayList();
        try {
            memberList = jdbcTemplate.query(SELECT_SUPERHEROS_BY_SIGHTINGID,
                    new SuperHeroMapper(),
                    sighting.getSightingID());
            //need to associate my super heros with  super power
            for (SuperHero currentHero : memberList) {
                associateSuperPowersWithSupHero(currentHero);
            }

            return memberList;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    //SUPERHERO HELPER METHOD5: THIS WILL ASSOCIATE SUPER HERO LIST WITH SIGHTING
    private Sighting associateSuperHerosWithSight(Sighting sighting) {
        if (findHerosForSighting(sighting) != null) {
            sighting.setSuperHeros(findHerosForSighting(sighting));
        }
        return sighting;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSighting(Sighting sighting) {
        jdbcTemplate.update(SQL_INSERT_SIGHTING,
                sighting.getLocation().getLocationID(),
                sighting.getSightingDate().toString());
        sighting.setSightingID(jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class));
        //update super hero sighting table
        insertSightingHeros(sighting);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSighting(int sightingID) {
        //DELETE SUPER HEROS FOR SIGHTING
        jdbcTemplate.update(SQL_DELETE_SIGHTING_HEROS, sightingID);
        //DELETE SIGHTING
        jdbcTemplate.update(SQL_DELETE_SIGHTING, sightingID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateSighting(Sighting sighting) {
        //CHECK FOR NULL LOC
        Integer locationID;
        if (sighting.getLocation() == null){
            locationID = null;
        } else {
            locationID = sighting.getLocation().getLocationID();
        }
        
        
        jdbcTemplate.update(SQL_UPDATE_SIGHTING,
                locationID,
                sighting.getSightingDate().toString(),
                sighting.getSightingID());
        //DELETE SIGHITNG HEROS FROM BRIDGE TABLE
        jdbcTemplate.update(SQL_DELETE_SIGHTING_HEROS, sighting.getSightingID());
        //RESET ORGS MEMBERS
        insertSightingHeros(sighting);

    }

    @Override
    public Sighting getSightingByID(int sightingID) {
        try {
            Sighting sighting = jdbcTemplate.queryForObject(SQL_SELECT_SIGHTING,
                    new SightingMapper(),
                    sightingID);
            associateLocationWithSight(sighting);
            associateSuperHerosWithSight(sighting);
            return sighting;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> sightingList = jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS,
                new SightingMapper());
        for (Sighting si : sightingList) {
            associateLocationWithSight(si);
            associateSuperHerosWithSight(si);
        }
        return sightingList;
    }

    //ADDITIONAL SIGHTING METHODS
    @Override
    public List<Sighting> getAllSightingsByDate(LocalDate date) {
        List<Sighting> sightingList = jdbcTemplate.query(SQL_SELECT_SIGHTINGS_BY_DATE,
                new SightingMapper(),
                date.toString());
        for (Sighting si : sightingList) {
            associateLocationWithSight(si);
            associateSuperHerosWithSight(si);
        }
        return sightingList;
    }
    
    //SQL_SELECT_SIGHTINGS_LATEST
    @Override
    public List<Sighting> getLatestSightings() {
        List<Sighting> sightingList = jdbcTemplate.query(SQL_SELECT_SIGHTINGS_LATEST,
                new SightingMapper());
        for (Sighting si : sightingList) {
            associateLocationWithSight(si);
            associateSuperHerosWithSight(si);
        }
        return sightingList;
    }
    
    //*******MEMBERSHIPS BRIDGE***********************
    @Override
    public void deleteAllMemberships() {
        jdbcTemplate.update(SQL_DELETE_ALL_MEMBERSHIPS);

    }

    //*******SUPER HERO SIGHTING BRIDGE*******************
    @Override
    public void deleteAllSuperHerosInSighting() {
        jdbcTemplate.update(SQL_DELETE_ALL_SUPER_HEROS_IN_SIGHTINGS);
    }

    @Override
    public void deleteAllSuperHeroSuperPowers() {
        jdbcTemplate.update(SQL_DELETE_ALL_SUPER_HEROS_SUPER_POWERS);
    }

    //================================================================
    //MAPPERS
    //================================================================
    //*****COORDINATES******
    private static final class CoordinateMapper implements RowMapper<Coordinate> {

        @Override
        public Coordinate mapRow(ResultSet rs, int i) throws SQLException {
            Coordinate coord = new Coordinate();
            coord.setCoordinateID(rs.getInt("CoordinateID"));
            coord.setLatitude(rs.getBigDecimal("Latitude"));
            coord.setLongitude(rs.getBigDecimal("Longitude"));

            return coord;
        }

    }

    //******LOCATIONS*******
    private static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location loc = new Location();
            loc.setLocationID(rs.getInt("LocationID"));
            loc.setLocName(rs.getString("LocName"));
            loc.setDescription(rs.getString("Description"));
            loc.setStreet(rs.getString("Street"));
            loc.setCity(rs.getString("City"));
            loc.setState(rs.getString("State"));
            loc.setZipCode(rs.getString("ZipCode"));
            loc.setCountry(rs.getString("Country"));

            return loc;
        }
    }

    //*****SUPER POWER*******
    private static final class SuperPowerMapper implements RowMapper<SuperPower> {

        @Override
        public SuperPower mapRow(ResultSet rs, int i) throws SQLException {
            SuperPower supPow = new SuperPower();
            supPow.setSuperPowerID(rs.getInt("SuperPowerID"));
            supPow.setSuperPower(rs.getString("SuperPower"));

            return supPow;
        }
    }

    //******SURE HERO********
    private static final class SuperHeroMapper implements RowMapper<SuperHero> {

        @Override
        public SuperHero mapRow(ResultSet rs, int i) throws SQLException {
            SuperHero supHero = new SuperHero();
            supHero.setSuperHeroID(rs.getInt("SuperHeroID"));
            supHero.setSuperHeroName(rs.getString("SuperHeroName"));
            supHero.setDescription(rs.getString("Description"));

            return supHero;
        }
    }

    //******SURE HERO********
    private static final class OrganizationsMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Organization org = new Organization();
            org.setOrganizationID(rs.getInt("OrganizationID"));
            org.setOrgName(rs.getString("OrgName"));
            org.setDescription(rs.getString("Description"));
            org.setPhoneNumber(rs.getString("PhoneNumber"));
            org.setEmail(rs.getString("Email"));

            return org;
        }
    }

    //******SIGHTINGS MAPPER********
    private static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSightingID(rs.getInt("SightingID"));
            sighting.setSightingDate(rs.getTimestamp("SightingDate").
                    toLocalDateTime().toLocalDate());

            Organization org = new Organization();
            return sighting;
        }
    }

}
