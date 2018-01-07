/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dao;

import com.sg.superherosightingsapp.dto.Location;
import com.sg.superherosightingsapp.dto.Organization;
import com.sg.superherosightingsapp.dto.Power;
import com.sg.superherosightingsapp.dto.Sighting;
import com.sg.superherosightingsapp.dto.SuperPerson;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author softwareguild
 */
public class OrganizationDaoTest {

    OrganizationDao organizationDao;
    LocationDao locationDao;
    PowerDao powerDao;
    SuperPersonDao superPersonDao;
    SightingDao sightingDao;

    public OrganizationDaoTest() {

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

       superPersonDao = ctx.getBean("superPersonDao", SuperPersonDao.class);
        locationDao = ctx.getBean("locationDao", LocationDao.class);
        powerDao = ctx.getBean("powerDao", PowerDao.class);
        organizationDao = ctx.getBean("organizationDao", OrganizationDao.class);
        sightingDao = ctx.getBean("sightingDao", SightingDao.class);

        // delete all Super Persons
        List<SuperPerson> heros = superPersonDao.getAllSuperPersons();
        for (SuperPerson currentHero : heros) {
            superPersonDao.deleteSuperPerson(currentHero.getSuperPersonId());
        }

        List<Power> powers = powerDao.getAllPowers();
        for (Power currentPower : powers) {
            powerDao.deletePower(currentPower.getPowerId());
        }

        List<Organization> organizations = organizationDao.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            organizationDao.deleteOrganization(currentOrganization.getOrganizationId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting currentSighting : sightings) {
            sightingDao.deleteSighting(currentSighting.getSightingId());
        }
        
         List<Location> locations = locationDao.getAllLocations();
        for (Location currentLocation : locations) {
            locationDao.deleteLocation(currentLocation.getLocationId());
        }

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addOrganization and getOrganizationById methods, of class
     * SuperPersonDao.
     */
    @Test
    public void testAddGetOrganization() {
        Location location = new Location();
        location.setLocationName("Kansas");
        location.setDescription("Middle of the USA");
        location.setLatitude(39.0997);
        location.setLongitude(94.5786);
        locationDao.addLocation(location);

        Organization organization = new Organization();
        organization.setOrganizationName("Doom Patrol");
        organization.setOrganizationDescription("A tall dark tower");
        organization.setLocation(location);
        organization.setPhone("(444)444-4444");
        organization.setEmail("dp@doom.com");
        organizationDao.addOrganization(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());

        Location location1 = locationDao.findLocationForOrganization(fromDao.getOrganizationId());
        fromDao.setLocation(location1);
        
        assertEquals(fromDao, organization);

    }

    /**
     * Test of updateOrganization, deleteOrganization, getAllOrganization
     * methods, of class SuperPersonDao.
     */
    @Test
    public void testUpdateDeleteOrganization() {
        Location location = new Location();
        location.setLocationName("Kansas");
        location.setDescription("Middle of the USA");
        location.setLatitude(39.0997);
        location.setLongitude(94.5786);
        locationDao.addLocation(location);

        Organization organization = new Organization();
        organization.setOrganizationName("Doom Patrol");
        organization.setOrganizationDescription("A tall dark tower");
        organization.setLocation(location);
        organization.setPhone("(444)444-4444");
        organization.setEmail("dp@doom.com");
        organizationDao.addOrganization(organization);

        Organization originalOrganization = organizationDao.getOrganizationById(organization.getOrganizationId());

        organization.setEmail("doooom@doom.com");
        organization.setOrganizationName("DOOM RAGE");
        organization.setPhone("(999)999-9999");
        organization.setOrganizationDescription("Doom squad");

        organizationDao.updateOrganization(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());

        Location locationDb = locationDao.findLocationForOrganization(fromDao.getOrganizationId());
        fromDao.setLocation(locationDb);
        assertEquals(fromDao, organization);
        assertFalse(originalOrganization.equals(fromDao));

        List<Organization> organizations = organizationDao.getAllOrganizations();
        assertEquals(organizations.size(), 1);

        organizationDao.deleteOrganization(organization.getOrganizationId());
        organizations = organizationDao.getAllOrganizations();
        assertEquals(organizations.size(), 0);

    }
    
     @Test
     public void testFindOrganizationsForHero(){
     
     };
    
    /**
     * Test of getAllOrganizationsBySuperPersonId method, of class
     * SuperPersonDao.
     */
    @Test
    public void testGetAllOrganizationsBySuperPersonId() {
        Power power = new Power();
        power.setPowerName("Flying");
        power.setPowerDescription("The ability to travel in air");

        powerDao.addPower(power);

        Location location = new Location();
        location.setLocationName("Kansas");
        location.setDescription("Middle of the USA");
        location.setLatitude(39.0997);
        location.setLongitude(94.5786);
        locationDao.addLocation(location);

        Organization organization = new Organization();
        organization.setOrganizationName("Doom Patrol");
        organization.setOrganizationDescription("A tall dark tower");
        organization.setLocation(location);
        organization.setPhone("(444)444-4444");
        organization.setEmail("dp@doom.com");
        organizationDao.addOrganization(organization);
        
        
        Location location2 = new Location();
        location2.setLocationName("Missouri");
        location2.setDescription("MO");
        location2.setLatitude(40);
        location2.setLongitude(96);
        locationDao.addLocation(location2);
        
        Organization organization2 = new Organization();
        organization2.setOrganizationName("Marvel Squad");
        organization2.setOrganizationDescription("Fancy HQ");
        organization2.setLocation(location2);
        organization2.setPhone("(333)333-3333");
        organization2.setEmail("marvel@squad.com");
        organizationDao.addOrganization(organization2);
        

        List<Power> powers = new ArrayList<>();
        powers.add(power);

        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        organizations.add(organization2);

        SuperPerson hero = new SuperPerson();
        hero.setName("BatMan");
        hero.setDescription("A Dark Knight");
        hero.setPowers(powers);
        hero.setOrganizations(organizations);
        superPersonDao.addSuperPerson(hero);
        
        List<Organization> fromDao = organizationDao.getAllOrganizationsBySuperPersonId(hero.getSuperPersonId());
        assertEquals(fromDao.size(), 2);
        Organization check1 = fromDao.get(0);
        Location location1Db= locationDao.findLocationForOrganization(check1.getOrganizationId());
        check1.setLocation(location1Db);
        assertEquals(check1, organization);
        Organization check2 = fromDao.get(1);
        
        Location location2Db = locationDao.findLocationForOrganization(check2.getOrganizationId());
        check2.setLocation(location2);
        assertEquals(check2, organization2);
    }


   
}
