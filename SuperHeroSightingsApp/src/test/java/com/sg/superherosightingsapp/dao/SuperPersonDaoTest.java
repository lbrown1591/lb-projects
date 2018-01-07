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
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author softwareguild
 */
public class SuperPersonDaoTest {

    SuperPersonDao superPersonDao;
    LocationDao locationDao;
    PowerDao powerDao;
    OrganizationDao organizationDao;
    SightingDao sightingDao;
    

    public SuperPersonDaoTest() {
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
    public void testAddGetSuperPerson() {
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

        SuperPerson fromDao = superPersonDao.getSuperPersonById(hero.getSuperPersonId());

    }

    /**
     * Test of updateSuperPerson, deleteSuperPerson, getAllSuperPersons methods,
     * of class SuperPersonDao.
     */
    @Test
    public void testUpdateDeleteSuperPerson() {
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

        SuperPerson originalHero = superPersonDao.getSuperPersonById(hero.getSuperPersonId());
        hero.setName("SUPER MAN");
        hero.setDescription("A hero from another world");

        superPersonDao.updateSuperPerson(hero);

        SuperPerson fromDao = superPersonDao.getSuperPersonById(hero.getSuperPersonId());
        List<Power> powersfd = powerDao.findPowersForHero(fromDao.getSuperPersonId());
        fromDao.setPowers(powersfd);
        List<Organization> organizationsfd = organizationDao.findOrganizationsForHero(fromDao.getSuperPersonId());
        
        for(Organization currentOrganization : organizationsfd){
            Location locationfd = locationDao.findLocationForOrganization(currentOrganization.getOrganizationId());
            currentOrganization.setLocation(locationfd);
        }
        
        fromDao.setOrganizations(organizationsfd);

        assertEquals(fromDao, hero);
        assertFalse(fromDao.equals(originalHero));

        List<SuperPerson> heros = superPersonDao.getAllSuperPersons();
        assertEquals(heros.size(), 1);
        superPersonDao.deleteSuperPerson(hero.getSuperPersonId());
        heros = superPersonDao.getAllSuperPersons();
        assertEquals(heros.size(), 0);

    }
    
    
    
    
    
    /**
     * Test of getSuperPersonsByLocationId method, of class SuperPersonDao.
     */
    @Test
    public void testGetSuperPersonsByLocationId() {
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
        
        
        Power power2 = new Power();
        power2.setPowerName("Super Strength");
        power2.setPowerDescription("Lift more than body should");

        powerDao.addPower(power2);

        Location location2 = new Location();
        location2.setLocationName("Missouri");
        location2.setDescription("hmm");
        location2.setLatitude(40);
        location2.setLongitude(40);
        locationDao.addLocation(location2);

        Organization organization2 = new Organization();
        organization2.setOrganizationName("Power Puff Girls");
        organization2.setOrganizationDescription("With the professor");
        organization2.setLocation(location2);
        organization2.setPhone("(222)222-2222");
        organization2.setEmail("ppg@girls.com");
        organizationDao.addOrganization(organization2);

        List<Power> powersList2 = new ArrayList<>();
        powersList2.add(power2);

        List<Organization> organizationsList2 = new ArrayList<>();
        organizationsList2.add(organization2);

        SuperPerson hero2 = new SuperPerson();
        hero2.setName("Wonder Woman");
        hero2.setDescription("Wow");
        hero2.setPowers(powersList2);
        hero2.setOrganizations(organizationsList2);
        superPersonDao.addSuperPerson(hero2);
        
        List<SuperPerson> herosList2 = new ArrayList<>();
        herosList2.add(hero2);

        LocalDate ld2 = LocalDate.parse("2015-01-01");
        Sighting sighting2 = new Sighting();
        sighting2.setDateOfSighting(ld2);
        sighting2.setHeroesSpotted(herosList2);
        sighting2.setLocation(location);
        
        sightingDao.addSighting(sighting2);
        
        List<SuperPerson> fromDao = superPersonDao.getSuperPersonsByLocationId(location.getLocationId());
      
        assertEquals(fromDao.size(), 2);
        SuperPerson check = fromDao.get(0);
        
        
        List<Power> powersCheck = powerDao.findPowersForHero(check.getSuperPersonId());
        check.setPowers(powersCheck);
        List<Organization> organizationsCheck = organizationDao.findOrganizationsForHero(check.getSuperPersonId());
        
        for(Organization currentOrganization : organizationsCheck){
            Location locationCheck = locationDao.findLocationForOrganization(currentOrganization.getOrganizationId());
            currentOrganization.setLocation(locationCheck);
        }
        
        check.setOrganizations(organizationsCheck);

        
        assertEquals(check, hero);
        SuperPerson check2 = fromDao.get(1);
        List<Power> powersCheck2 = powerDao.findPowersForHero(check2.getSuperPersonId());
        check2.setPowers(powersCheck2);
        List<Organization> organizationsCheck2 = organizationDao.findOrganizationsForHero(check2.getSuperPersonId());
        
        for(Organization currentOrganization : organizationsCheck2){
            Location locationCheck2 = locationDao.findLocationForOrganization(currentOrganization.getOrganizationId());
            currentOrganization.setLocation(locationCheck2);
        }
        
        check2.setOrganizations(organizationsCheck2);
        
        assertEquals(check2, hero2);
    }

    

   

    /**
     * Test of getAllSuperPersonsByOrganizationId method, of class
     * SuperPersonDao.
     */
    @Test
    public void testGetAllSuperPersonsByOrganizationId() {
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
        
        
        Power power2 = new Power();
        power2.setPowerName("X-Ray vision");
        power.setPowerDescription("The ability to see through solid objects");

        powerDao.addPower(power2);

        
        List<Power> powers2 = new ArrayList<>();
        powers2.add(power2);

        
        SuperPerson hero2 = new SuperPerson();
        hero2.setName("Spider Man");
        hero2.setDescription("A bug bite gone wrong");
        hero2.setPowers(powers2);
        hero2.setOrganizations(organizations);
        superPersonDao.addSuperPerson(hero2);
        
        List<SuperPerson> fromDao = superPersonDao.getAllSuperPersonsByOrganizationId(organization.getOrganizationId());
        
        
        
    }

   
}
