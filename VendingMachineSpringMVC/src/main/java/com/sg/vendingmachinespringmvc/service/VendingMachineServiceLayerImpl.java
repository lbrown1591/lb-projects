/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.service;

import com.sg.vendingmachinespringmvc.dao.VendingMachineDao;
import com.sg.vendingmachinespringmvc.dto.Change;
import com.sg.vendingmachinespringmvc.model.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author softwareguild
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    private VendingMachineDao dao;
    private BigDecimal balance = new BigDecimal("0");
    private int itemSelection;
    private Change currentChange;
    private boolean recentPurchase = false;
    private boolean recentPurchaseAttempt = false;
    private boolean changeRequested = false;
    private BigDecimal pastBalance = new BigDecimal("0");
    private String message;


    public VendingMachineServiceLayerImpl(VendingMachineDao dao) {
        this.dao = dao;
    }

    @Override
    public BigDecimal insertFunds(BigDecimal funds) {
        balance = balance.add(funds);
        return balance;
    }

    @Override
    public BigDecimal buyItem() throws VendingMachineItemOutOfStockException, VendingMachineInsufficientFundsException {
        Item itemToBePurchased = findItem(itemSelection);

        BigDecimal cost = itemToBePurchased.getCost();
        checkInventory(itemToBePurchased);
        checkFunds(cost, balance);

        updateInventory(itemToBePurchased);
        balance = balance.subtract(cost);
        message = "Thank you!!!";
       
        recentPurchase = true;
        return balance;
    }

    @Override
    public BigDecimal findBalance() {
        return balance;
    }

    @Override
    public Change calculateChange(BigDecimal balance) {
        BigDecimal hundred = new BigDecimal(100);
        balance = balance.multiply(hundred);
        int balanceInPennies = balance.intValue();
        Change changeDue = new Change(balanceInPennies);
        currentChange = changeDue;

        return changeDue;
    }

    @Override
    public Item createItem(Item item) {
        return dao.createItem(item);
    }

    @Override
    public Item updateItem(Item item) {
        return dao.updateItem(item);
    }

    @Override
    public Item deleteItem(Integer key) {
        return dao.deleteItem(key);
    }

    @Override
    public Item findItem(Integer itemKey) {
        return dao.findItem(itemKey);
    }

    @Override
    public List<Item> readAllItems() {
        return dao.readAllItems();
    }

    @Override
    public void selectItem(Integer itemKey) {
        itemSelection = itemKey;
    }

    @Override
    public int getItemSelection() {
        return itemSelection;
    }

    @Override
    public void verifyNotPurchaseAttempt() {
        recentPurchase = false;
        recentPurchaseAttempt = false;
    }

    @Override
    public String getMessage() {
        if (recentPurchase || recentPurchaseAttempt) {
            return message;
        } else {
            return null;
        }

    }

    private void checkInventory(Item item) throws VendingMachineItemOutOfStockException {
        if (item.getNumOfItems() == 0) {
            throw new VendingMachineItemOutOfStockException("Sold out!!");
        }

    }

    private void checkFunds(BigDecimal costOfItem, BigDecimal balace) throws VendingMachineInsufficientFundsException {
        if (costOfItem.compareTo(balace) == 1) {
            BigDecimal amountShort = costOfItem.subtract(balace).setScale(2);
            throw new VendingMachineInsufficientFundsException("Please deposit: $" + amountShort);
        }
    }

    private void updateInventory(Item item) {
        int currentInventory = item.getNumOfItems();
        currentInventory--;
        item.setNumOfItems(currentInventory);
        updateItem(item);

    }

    private void updateBalanceAfterPurchase() {
        BigDecimal zero = new BigDecimal("0");
        balance = zero;
    }

    @Override
    public void clearItemSelection() {
        selectItem(0);
    }

    
    @Override
    public void setMessage(String errorMessage) {
        message = errorMessage;
        recentPurchaseAttempt = true;
    }

    @Override
    public void alertChangeNeeded() {
        changeRequested = true;
        updateBalanceAfterPurchase();
    }

    @Override
    public boolean checkChangeNeeded() {
        if (changeRequested) {
            changeRequested = false;
            return true;
        } else {
            return false;
        }
       
    }
    
    @Override
    public void savePastBalance(BigDecimal lastBalance){
        pastBalance = lastBalance;
    }
    
     @Override
    public BigDecimal getPastBalance(){
        return pastBalance;
    }


}
