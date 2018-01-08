/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.service;

import com.sg.vendingmachinespringmvc.dto.Change;
import com.sg.vendingmachinespringmvc.model.Item;
import java.math.BigDecimal;
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
public class VendingMachineServiceLayerTest {

    private VendingMachineServiceLayer service;
    private BigDecimal balance = new BigDecimal("0");
    private int itemSelection;
    private boolean recentPurchase;
    private boolean recentPurchaseAttempt;
    private String message;

    public VendingMachineServiceLayerTest() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        service = ctx.getBean("serviceLayer", VendingMachineServiceLayer.class);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        BigDecimal zero = new BigDecimal("0");
        service.insertFunds(zero);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of insertFunds method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testInsertFundsAndFindBalance() {
        BigDecimal money = service.findBalance();
        BigDecimal funds = new BigDecimal("1.00");
        service.insertFunds(funds);
        money = service.findBalance();
        assertTrue(funds.equals(money));
    }

    /**
     * Test of selectItem method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testSelectItemGetItemSelectionAndClearSelection() {

        service.selectItem(1);
        int selection = service.getItemSelection();

        assertEquals(1, selection);
        service.clearItemSelection();
        selection = service.getItemSelection();
        assertEquals(0, selection);

    }

    /**
     * Test of calculateChange method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testCalculateChange() {
        BigDecimal funds = new BigDecimal(".68");
        service.insertFunds(funds);
        Change testChange = service.calculateChange(funds);
        assertEquals(2, testChange.getNumOfQuarters());
        assertEquals(1, testChange.getNumOfDimes());
        assertEquals(1, testChange.getNumOfNickels());
        assertEquals(3, testChange.getNumOfPennies());
    }
    
    @Test
    public void testBuyItemInsufficientFunds() throws Exception {
        BigDecimal funds = new BigDecimal("0");
        service.insertFunds(funds);
        try {
            service.selectItem(1);
            service.buyItem();
            fail("Expected VendingMachineInsufficientFundsException was not thrown");
        } catch (VendingMachineInsufficientFundsException e) {
           
        }
    }
    
    @Test
    public void testBuyItemSufficientFunds() throws Exception {
        BigDecimal funds = new BigDecimal("2.00");
        BigDecimal zero = new BigDecimal("0");
        service.insertFunds(funds);
        service.selectItem(1);
        int inventory = service.findItem(1).getNumOfItems();
        assertEquals(inventory, 1);
        service.buyItem();
        inventory = service.findItem(1).getNumOfItems();
        assertEquals(inventory, 0);
        assertEquals(balance.compareTo(zero), 0);
        message = service.getMessage();
        assertTrue(message.equalsIgnoreCase("Thank you!!!"));
    }
    
    @Test
    public void testBuyItemOutOfStock() throws Exception {
        BigDecimal funds = new BigDecimal("4.00");
        service.insertFunds(funds);
        service.selectItem(1);
        service.buyItem();
        int inventory = service.findItem(1).getNumOfItems();
        assertEquals(inventory, 0);
        service.selectItem(1);
        try {
            service.buyItem();
            fail("Expected VendingMachineItemOutOfStockException was not thrown");
        } catch (VendingMachineItemOutOfStockException e) {
        }
    }
    
    

    /**
     * Test of createItem method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testCreateItem() {
        BigDecimal cost = new BigDecimal("1.00");
        Item newItem = new Item("Cookies", cost, 3, 3);
        service.createItem(newItem);
    }

    /**
     * Test of updateItem method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testUpdateItem() {
        Item item = service.findItem(1);
        item.setNumOfItems(5);
        int size = service.readAllItems().size();
        Item updated = service.updateItem(item);
        assertEquals(updated.getNumOfItems(), 5);
        assertEquals(size, service.readAllItems().size());
    }

    /**
     * Test of deleteItem method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testDeleteItem() {
        Item item = service.deleteItem(1);
        assertNotNull(item);
        item = service.deleteItem(700);
        assertNull(item);
    }

    /**
     * Test of findItem method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testFindItem() {
        Item item = service.findItem(1);
        assertNotNull(item);
        item = service.findItem(700);
        assertNull(item);
    }

    /**
     * Test of readAllItems method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testReadAllItems() {
        assertEquals(2, service.readAllItems().size());
    }

    

    /**
     * Test of verifyNotPurchaseAttempt method, of class
     * VendingMachineServiceLayer.
     */
    @Test
    public void testVerifyNotPurchaseAttempt() {
        service.verifyNotPurchaseAttempt();
        assertFalse(recentPurchaseAttempt);
        assertFalse(recentPurchase);
    }

    /**
     * Test of getMessage method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetAndSetMessage() {
        service.setMessage("Sold out!!");
        String m = service.getMessage();
        assertTrue(m.equalsIgnoreCase("Sold out!!"));
    }

    

    /**
     * Test of alertChangeNeeded method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testAlertChangeNeeded() {
        BigDecimal ten = new BigDecimal("10");
        service.insertFunds(ten);
        BigDecimal checkBalance = service.findBalance();
        assertEquals(checkBalance, ten);
        service.alertChangeNeeded();
        boolean check = service.checkChangeNeeded();
        assertTrue(check);
        checkBalance = service.findBalance();
        BigDecimal zero = new BigDecimal("0");
        assertEquals(zero, checkBalance);
    }
    
    @Test
    public void testSaveAndGetPastBalance() throws Exception{
        BigDecimal ten = new BigDecimal("10");
        BigDecimal eight = new BigDecimal("8");
        BigDecimal zero = new BigDecimal("0");
        service.insertFunds(ten);
        service.selectItem(1);
        BigDecimal balanceAfterPurchase = service.buyItem();
        service.savePastBalance(balanceAfterPurchase);
        assertEquals(balanceAfterPurchase.compareTo(eight), 0);
        service.alertChangeNeeded();
        BigDecimal pastBalance = service.getPastBalance();
        assertEquals(pastBalance.compareTo(balanceAfterPurchase), 0);
        service.alertChangeNeeded();
        BigDecimal currentBalance = service.findBalance();
        assertEquals(currentBalance.compareTo(zero), 0);
        
    }

}
