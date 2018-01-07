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
import java.time.LocalDate;
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
public class LocationDaoTest {

    SuperPersonDao superPersonDao;
    LocationDao locationDao;
    PowerDao powerDao;
    OrganizationDao organizationDao;
    SightingDao sightingDao;

    public LocationDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");

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

    @Test
    public void testAddGetLocation() {
        Location location = new Location();
        location.setLocationName("Kansas");
        location.setDescription("Middle of the USA");
        location.setLatitude(39.0997);
        location.setLongitude(94.5786);

        locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(fromDao, location);

    }

    /**
     * Test of updateLocation, deleteLocation, getAllLocations methods, of class
     * SuperPersonDao.
     */
    @Test
    public void testUpdateDeleteLocation() {
        Location location = new Location();
        location.setLocationName("Kansas");
        location.setDescription("Middle of the USA");
        location.setLatitude(39.0997);
        location.setLongitude(94.5786);

        locationDao.addLocation(location);

        Location originalLocation = locationDao.getLocationById(location.getLocationId());

        location.setLocationName("Missouri");
        location.setDescription("Over there");
        location.setLatitude(2.2);
        location.setLongitude(3.3);
        locationDao.updateLocation(location);

        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(fromDao, location);
        assertFalse(originalLocation.equals(fromDao));

        List<Location> locations = locationDao.getAllLocations();
        assertEquals(locations.size(), 1);

        locationDao.deleteLocation(location.getLocationId());
        locations = locationDao.getAllLocations();
        assertEquals(locations.size(), 0);

    }

    /**
     * Test of findLocationForOrganization method, of class LocationDao.
     */
    @Test
    public void testFindLocationForOrganization() {
    }

    /**
     * Test of findLocationForSighting method, of class LocationDao.
     */
    @Test
    public void testFindLocationForSighting() {
    }

    /**
     * Test of getLocationsBySuperPersonId method, of class SuperPersonDao.
     */
    @Test
    public void testGetLocationsBySuperPersonId() {

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

        List<Power> powersList1 = new ArrayList<>();
        powersList1.add(power);

        List<Organization> organizationsList1 = new ArrayList<>();
        organizationsList1.add(organization);

        SuperPerson hero = new SuperPerson();
        hero.setName("BatMan");
        hero.setDescription("A Dark Knight");
        hero.setPowers(powersList1);
        hero.setOrganizations(organizationsList1);
        superPersonDao.addSuperPerson(hero);

        List<SuperPerson> heros = new ArrayList<>();
        heros.add(hero);

        LocalDate ld = LocalDate.now();
        Sighting sighting = new Sighting();
        sighting.setDateOfSighting(ld);
        sighting.setHeroesSpotted(heros);
        sighting.setLocation(location);

        sightingDao.addSighting(sighting);

        Location location2 = new Location();
        location2.setLocationName("Idaho");
        location2.setDescription("Potato Land");
        location2.setLatitude(50);
        location2.setLongitude(60);
        locationDao.addLocation(location2);

        LocalDate ld2 = LocalDate.parse("2015-01-01");
        Sighting sighting2 = new Sighting();
        sighting2.setDateOfSighting(ld2);
        sighting2.setHeroesSpotted(heros);
        sighting2.setLocation(location2);

        sightingDao.addSighting(sighting2);

        List<Location> fromDao = locationDao.getLocationsBySuperPersonId(hero.getSuperPersonId());

        assertEquals(fromDao.size(), 2);
        Location check1 = fromDao.get(0);
        assertEquals(check1, location);
        Location check2 = fromDao.get(1);
        assertEquals(check2, location2);

    }

}
