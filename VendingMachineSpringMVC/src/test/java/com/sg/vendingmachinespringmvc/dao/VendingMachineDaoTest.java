/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Item;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author softwareguild
 */
public class VendingMachineDaoTest {
    
    private VendingMachineDao dao = new VendingMachineDaoDbImpl();
    
    public VendingMachineDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        // ask Spring for our DAO
        ApplicationContext ctx
            = new ClassPathXmlApplicationContext(
                        "test-applicationContext.xml");
        dao = ctx.getBean("vendingMachineDao", VendingMachineDao.class);
        
        // remove all of the Contacts
        List<Item> items = dao.readAllItems();
        for (Item currentItem : items) {
            dao.deleteItem(currentItem.getItemKey());
        }
    }
        
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createItem method, of class VendingMachineDao.
     */
    @Test
    public void testCreateAndFindItem() {
        BigDecimal costOfItem = new BigDecimal("2.00");
        Item item = new Item("Reeses", costOfItem, 3, 1);
        
        dao.createItem(item);
        
        Item fromDao = dao.findItem(item.getItemKey());
        
        assertEquals(item, fromDao);
    }

    /**
     * Test of deleteItem method, of class VendingMachineDao.
     */
    @Test
    public void testDeleteItem() {
        
        BigDecimal costOfItem1 = new BigDecimal("2.00");
        Item item1 = new Item("Reeses", costOfItem1, 3, 1);
        dao.createItem(item1);
        
        BigDecimal costOfItem2 = new BigDecimal("3.00");
        Item item2 = new Item("KitKat", costOfItem2, 0, 2);
        dao.createItem(item2);
        
        dao.deleteItem(item1.getItemKey());
        assertEquals(1, dao.readAllItems().size());
        assertNull(dao.findItem(item1.getItemKey()));
        
        dao.deleteItem(item2.getItemKey());
        assertEquals(0, dao.readAllItems().size());
        assertNull(dao.findItem(item2.getItemKey()));
    }

    /**
     * Test of updateItem method, of class VendingMachineDao.
     */
    @Test
    public void testUpdateItem() {
         BigDecimal costOfItem = new BigDecimal("2.00");
        Item item = new Item("Reeses", costOfItem, 0, 1);
        
        dao.createItem(item);
        int inventoryOriginal = dao.findItem(1).getNumOfItems();
        
        item.setNumOfItems(10);
        
        dao.updateItem(item);
        int updateInventory = dao.findItem(1).getNumOfItems();
        
        assertNotEquals(inventoryOriginal, updateInventory);
    }

   
    /**
     * Test of readAllItems method, of class VendingMachineDao.
     */
    @Test
    public void testReadAllItems() {
        BigDecimal costOfItem1 = new BigDecimal("2.00");
        Item item1 = new Item("Reeses", costOfItem1, 3, 1);
        dao.createItem(item1);
        
        BigDecimal costOfItem2 = new BigDecimal("3.00");
        Item item2 = new Item("KitKat", costOfItem2, 0, 2);
        dao.createItem(item2);
        
        assertEquals(2, dao.readAllItems().size());
    }

    
    
}
