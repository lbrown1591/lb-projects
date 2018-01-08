/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Item;
import java.util.List;

/**
 *
 * @author softwareguild
 */
public interface VendingMachineDao {
    /**
     * Adds the given Item to the Vending Machine.
     * 
     * 
     * @param item Item to be added to the Vending Machine
     * @return the Item object previously associated with the given id
     * if it exists, null otherwise
     * 
     */
    
    Item createItem(Item item);
    
     /**
     * Removes from the Vending Machine the Item associated with the given key number. Returns
     * the Item object that is being removed or null if there is no Item
     * associated with the given key
     *
     * @param key key number of Item to be removed
     * @return Item object that was removed or null if no Item was
     * associated with the given id
     *
     */
    Item deleteItem(int key);
    
     /**
     * Allows the program to edit the properties of the Item
     * that's passed in. Returns the updated
     * Item object. 
     *
     * @param item Item to be updated
     * @return Item object that was edited or null if no Item was
     * associated with the given id
     * 
     */
    Item updateItem(Item item); 
    
    
    
    /**
     * Finds Item in the VendingMachine with the given itemKey int value
     * that's passed in. Returns the Item object.
     *
     * @param itemKey key of item to be found
     * @return Item object that was found or null if no Item was
     * associated with the given id
     * 
     */
    
    Item findItem(int itemKey); 
    
    
    /**
     * Returns a List containing all items that are in the VendingMachine
     * in the Vending Machine.
     *
     * @return a List of Items that are available in the Vending Machine
     * 
     */

    List<Item> readAllItems();
    
    
    
}
