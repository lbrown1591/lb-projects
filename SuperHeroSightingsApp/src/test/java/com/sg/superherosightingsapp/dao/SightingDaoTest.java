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
public class SightingDaoTest {

    SuperPersonDao superPersonDao;
    LocationDao locationDao;
    PowerDao powerDao;
    OrganizationDao organizationDao;
    SightingDao sightingDao;

    public SightingDaoTest() {
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

    /**
     * Test of addSighting and getSightingByID methods, of class SuperPersonDao.
     */
    @Test
    public void testAddGetSighting() {
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

        List<Power> powers = new ArrayList<>();
        powers.add(power);

        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);

        SuperPerson hero = new SuperPerson();
        hero.setName("BatMan");
        hero.setDescription("A Dark Knight");
        hero.setPowers(powers);
        hero.setOrganizations(organizations);
        superPersonDao.addSuperPerson(hero);

        List<SuperPerson> heros = new ArrayList<>();
        heros.add(hero);

        LocalDate ld = LocalDate.now();
        Sighting sighting = new Sighting();
        sighting.setDateOfSighting(ld);
        sighting.setHeroesSpotted(heros);
        sighting.setLocation(location);

        sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());

        
        Location locationDb = locationDao.findLocationForSighting(fromDao.getSightingId());
        fromDao.setLocation(locationDb);

        List<SuperPerson> herosDb = superPersonDao.findHerosForSighting(fromDao.getSightingId());

        for (SuperPerson currentHero : herosDb) {

            List<Power> powersCheck1 = powerDao.findPowersForHero(currentHero.getSuperPersonId());
            currentHero.setPowers(powersCheck1);

            List<Organization> organizationsCheck1 = organizationDao.findOrganizationsForHero(currentHero.getSuperPersonId());

            for (Organization currentOrganization : organizationsCheck1) {
                Location locationCheck1 = locationDao.findLocationForOrganization(currentOrganization.getOrganizationId());
                currentOrganization.setLocation(locationCheck1);
            }

            currentHero.setOrganizations(organizationsCheck1);

        }

        fromDao.setHeroesSpotted(herosDb);
        
        
        
        assertEquals(fromDao, sighting);

    }

    /**
     * Test of updateSighting, deleteSighting, getAllSightings methods, of class
     * SuperPersonDao.
     */
    @Test
    public void testUpdateDeleteSighting() {
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

        List<Power> powers = new ArrayList<>();
        powers.add(power);

        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);

        SuperPerson hero = new SuperPerson();
        hero.setName("BatMan");
        hero.setDescription("A Dark Knight");
        hero.setPowers(powers);
        hero.setOrganizations(organizations);
        superPersonDao.addSuperPerson(hero);

        List<SuperPerson> heros = new ArrayList<>();
        heros.add(hero);

        LocalDate ld = LocalDate.now();
        Sighting sighting = new Sighting();
        sighting.setDateOfSighting(ld);
        sighting.setHeroesSpotted(heros);
        sighting.setLocation(location);

        sightingDao.addSighting(sighting);

        Sighting originalSighting = sightingDao.getSightingById(sighting.getSightingId());

        ld = LocalDate.parse("2015-01-01");
        sighting.setDateOfSighting(ld);

        location.setLocationName("Missouri");
        location.setDescription("Near the middle of the USA");
        location.setLatitude(40);
        location.setLongitude(95);
        locationDao.addLocation(location);
        sighting.setLocation(location);

        SuperPerson hero2 = new SuperPerson();
        hero2.setName("Wonder Woman");
        hero2.setDescription("A incredible lady");
        hero2.setPowers(powers);
        hero2.setOrganizations(organizations);
        superPersonDao.addSuperPerson(hero2);

        List<SuperPerson> heros2 = new ArrayList<>();
        heros.add(hero2);
        sighting.setHeroesSpotted(heros2);

        sightingDao.updateSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());

        assertFalse(fromDao.equals(originalSighting));

        Location locationDb = locationDao.findLocationForSighting(fromDao.getSightingId());
        fromDao.setLocation(locationDb);

        List<SuperPerson> herosDb = superPersonDao.findHerosForSighting(fromDao.getSightingId());

        for (SuperPerson currentHero : herosDb) {

            List<Power> powersCheck2 = powerDao.findPowersForHero(currentHero.getSuperPersonId());
            currentHero.setPowers(powersCheck2);

            List<Organization> organizationsCheck2 = organizationDao.findOrganizationsForHero(currentHero.getSuperPersonId());

            for (Organization currentOrganization : organizationsCheck2) {
                Location locationCheck2 = locationDao.findLocationForOrganization(currentOrganization.getOrganizationId());
                currentOrganization.setLocation(locationCheck2);
            }

            currentHero.setOrganizations(organizationsCheck2);

        }

        fromDao.setHeroesSpotted(herosDb);
        assertEquals(fromDao, sighting);

        List<Sighting> sightings = sightingDao.getAllSightings();
        assertEquals(sightings.size(), 1);

        sightingDao.deleteSighting(sighting.getSightingId());
        sightings = sightingDao.getAllSightings();
        assertEquals(sightings.size(), 0);
    }

    /**
     * Test of getSightingsByDate method, of class SuperPersonDao.
     */
    @Test
    public void testGetSightingsByDate() {

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

        List<Power> powers = new ArrayList<>();
        powers.add(power);

        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);

        SuperPerson hero = new SuperPerson();
        hero.setName("BatMan");
        hero.setDescription("A Dark Knight");
        hero.setPowers(powers);
        hero.setOrganizations(organizations);
        superPersonDao.addSuperPerson(hero);

        List<SuperPerson> heros = new ArrayList<>();
        heros.add(hero);

        LocalDate ld = LocalDate.now();
        Sighting sighting = new Sighting();
        sighting.setDateOfSighting(ld);
        sighting.setHeroesSpotted(heros);
        sighting.setLocation(location);

        sightingDao.addSighting(sighting);

        LocalDate ld2 = LocalDate.parse("2017-01-01");
        Sighting sighting2 = new Sighting();
        sighting2.setDateOfSighting(ld2);
        sighting2.setHeroesSpotted(heros);
        sighting2.setLocation(location);
        sightingDao.addSighting(sighting2);

        Location location2 = new Location();
        location2.setLocationName("Michigan");
        location2.setDescription("Mittin");
        location2.setLatitude(50);
        location2.setLongitude(130);
        locationDao.addLocation(location2);

        Sighting sighting3 = new Sighting();
        sighting3.setDateOfSighting(ld2);
        sighting3.setHeroesSpotted(heros);
        sighting3.setLocation(location2);
        sightingDao.addSighting(sighting3);

        List<Sighting> fromDao = sightingDao.getSightingsByDate(ld2);

        assertEquals(fromDao.size(), 2);
        Sighting check1 = fromDao.get(0);
        
        Location locationDb = locationDao.findLocationForSighting(check1.getSightingId());
        check1.setLocation(locationDb);

        List<SuperPerson> herosDb = superPersonDao.findHerosForSighting(check1.getSightingId());

        for (SuperPerson currentHero : herosDb) {

            List<Power> powersCheck1 = powerDao.findPowersForHero(currentHero.getSuperPersonId());
            currentHero.setPowers(powersCheck1);

            List<Organization> organizationsCheck1 = organizationDao.findOrganizationsForHero(currentHero.getSuperPersonId());

            for (Organization currentOrganization : organizationsCheck1) {
                Location locationCheck1 = locationDao.findLocationForOrganization(currentOrganization.getOrganizationId());
                currentOrganization.setLocation(locationCheck1);
            }

            currentHero.setOrganizations(organizationsCheck1);

        }

        check1.setHeroesSpotted(herosDb);
        
        
        assertEquals(check1, sighting2);
        
        Sighting check2 = fromDao.get(1);
        
        Location locationDb2 = locationDao.findLocationForSighting(check2.getSightingId());
        check2.setLocation(locationDb2);

        List<SuperPerson> herosDb2 = superPersonDao.findHerosForSighting(check2.getSightingId());

        for (SuperPerson currentHero : herosDb2) {

            List<Power> powersCheck2 = powerDao.findPowersForHero(currentHero.getSuperPersonId());
            currentHero.setPowers(powersCheck2);

            List<Organization> organizationsCheck2 = organizationDao.findOrganizationsForHero(currentHero.getSuperPersonId());

            for (Organization currentOrganization : organizationsCheck2) {
                Location locationCheck2 = locationDao.findLocationForOrganization(currentOrganization.getOrganizationId());
                currentOrganization.setLocation(locationCheck2);
            }

            currentHero.setOrganizations(organizationsCheck2);

        }

        check2.setHeroesSpotted(herosDb2);
        
        assertEquals(check2, sighting3);
    }

    @Test
    public void testGetSightingsByHeroId() {

    }

}
