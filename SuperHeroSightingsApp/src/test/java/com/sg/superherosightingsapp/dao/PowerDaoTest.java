/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dao;

import com.sg.superherosightingsapp.dto.Power;
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
public class PowerDaoTest {

    PowerDao powerDao;

    public PowerDaoTest() {
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

        powerDao = ctx.getBean("powerDao", PowerDao.class
        );
        
        
        List<Power> powers = powerDao.getAllPowers();
        for (Power currentPower : powers) {
            powerDao.deletePower(currentPower.getPowerId());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addPower and getPowerByID methods, of class SuperPersonDao.
     */
    @Test
    public void testAddGetPower() {
        Power power = new Power();
        power.setPowerName("Flying");
        power.setPowerDescription("The ability to travel in air");

        powerDao.addPower(power);

        Power fromDao = powerDao.getPowerById(power.getPowerId());

        assertEquals(fromDao, power);

    }

    /**
     * Test of updatePower, deletePower, getAllPowers methods, of class
     * SuperPersonDao.
     */
    @Test
    public void testUpdateDeletePower() {
        Power power = new Power();
        power.setPowerName("Flying");
        power.setPowerDescription("The ability to travel in air");

        powerDao.addPower(power);

        Power originalPower = powerDao.getPowerById(power.getPowerId());

        power.setPowerName("Super strength");
        power.setPowerDescription("The ability to lift things much greater than ones body would normally allow");

        powerDao.updatePower(power);

        Power fromDao = powerDao.getPowerById(power.getPowerId());

        assertEquals(fromDao, power);
        assertFalse(originalPower.equals(fromDao));

        List<Power> powers = powerDao.getAllPowers();
        assertEquals(powers.size(), 1);

        powerDao.deletePower(power.getPowerId());
        powers = powerDao.getAllPowers();
        assertEquals(powers.size(), 0);

    }

    @Test
    public void testFindPowersForHero() {
        
    }

    
}
