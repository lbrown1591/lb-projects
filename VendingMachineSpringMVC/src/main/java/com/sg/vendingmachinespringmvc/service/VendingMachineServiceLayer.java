/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.service;

import com.sg.vendingmachinespringmvc.dto.Change;
import com.sg.vendingmachinespringmvc.model.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author softwareguild
 */
public interface VendingMachineServiceLayer {
 
    
    public BigDecimal insertFunds(BigDecimal funds);
    
    public BigDecimal buyItem()
            throws VendingMachineInsufficientFundsException, VendingMachineItemOutOfStockException;
    
    public BigDecimal findBalance();
    
    public Change calculateChange (BigDecimal balance);
    
    public Item createItem(Item item);
    
    public Item updateItem(Item item);
    
    public Item deleteItem(Integer key);
    
    public Item findItem(Integer itemKey);
    
    public List<Item> readAllItems();
    
    public void selectItem(Integer itemKey);
    
    public int getItemSelection();
    
    public void verifyNotPurchaseAttempt();
    
    public String getMessage();
    
    public void clearItemSelection();
    
    public void setMessage(String message);
    
    public void alertChangeNeeded();
    
    public boolean checkChangeNeeded();
    
    public void savePastBalance(BigDecimal balance);
    
    public BigDecimal getPastBalance();
}
