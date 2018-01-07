/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.service;

import com.sg.superherosightingsapp.dao.LocationDao;
import com.sg.superherosightingsapp.dao.OrganizationDao;
import com.sg.superherosightingsapp.dao.PowerDao;
import com.sg.superherosightingsapp.dao.SightingDao;
import com.sg.superherosightingsapp.dao.SuperPersonDao;
import com.sg.superherosightingsapp.dao.UserDao;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author softwareguild
 */
public class SuperHeroSightingsServiceLayerTest {

    SuperPersonDao superPersonDao;
    LocationDao locationDao;
    PowerDao powerDao;
    OrganizationDao organizationDao;
    SightingDao sightingDao;
    UserDao userDao;

    private SuperHeroSightingsServiceLayer service;

   

    public SuperHeroSightingsServiceLayerTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
      
      
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addSuperPerson method, of class SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testAddSuperPerson() {
    }

    /**
     * Test of deleteSuperPerson method, of class
     * SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testDeleteSuperPerson() {
    }

    /**
     * Test of updateSuperPerson method, of class
     * SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testUpdateSuperPerson() {
    }

    /**
     * Test of getSuperPersonById method, of class
     * SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testGetSuperPersonById() {
    }

    /**
     * Test of getAllSuperPersons method, of class
     * SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testGetAllSuperPersons() {
    }

    /**
     * Test of addOrganization method, of class SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testAddOrganization() {
    }

    /**
     * Test of deleteOrganization method, of class
     * SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testDeleteOrganization() {
    }

    /**
     * Test of updateOrganization method, of class
     * SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testUpdateOrganization() {
    }

    /**
     * Test of getOrganizationById method, of class
     * SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testGetOrganizationById() {
    }

    /**
     * Test of getAllOrganizations method, of class
     * SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testGetAllOrganizations() {
    }

    /**
     * Test of addPower method, of class SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testAddPower() {
    }

    /**
     * Test of deletePower method, of class SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testDeletePower() {
    }

    /**
     * Test of updatePower method, of class SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testUpdatePower() {
    }

    /**
     * Test of getPowerById method, of class SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testGetPowerById() {
    }

    /**
     * Test of getAllPowers method, of class SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testGetAllPowers() {
    }

    /**
     * Test of addLocation method, of class SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testAddLocation() {
    }

    /**
     * Test of deleteLocation method, of class SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testDeleteLocation() {
    }

    /**
     * Test of updateLocation method, of class SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testUpdateLocation() {
    }

    /**
     * Test of getLocationById method, of class SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testGetLocationById() {
    }

    /**
     * Test of getAllLocations method, of class SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testGetAllLocations() {
    }

    /**
     * Test of addSighting method, of class SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testAddSighting() {
    }

    /**
     * Test of deleteSighting method, of class SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testDeleteSighting() {
    }

    /**
     * Test of updateSighting method, of class SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testUpdateSighting() {
    }

    /**
     * Test of getSightingById method, of class SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testGetSightingById() {
    }

    /**
     * Test of getAllSightings method, of class SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testGetAllSightings() {
    }

    /**
     * Test of getSuperPersonsByLocationId method, of class
     * SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testGetSuperPersonsByLocationId() {
    }

    /**
     * Test of getLocationsBySuperPersonId method, of class
     * SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testGetLocationsBySuperPersonId() {
    }

    /**
     * Test of getSightingsByDate method, of class
     * SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testGetSightingsByDate() {
    }

    /**
     * Test of getAllSuperPersonsByOrganizationId method, of class
     * SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testGetAllSuperPersonsByOrganizationId() {
    }

    /**
     * Test of getAllOrganizationsBySuperPersonId method, of class
     * SuperHeroSightingsServiceLayer.
     */
    @Test
    public void testGetAllOrganizationsBySuperPersonId() {
    }

}
