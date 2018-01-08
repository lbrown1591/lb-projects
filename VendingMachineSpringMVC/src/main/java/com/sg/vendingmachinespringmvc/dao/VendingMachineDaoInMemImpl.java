/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author softwareguild
 */
public class VendingMachineDaoInMemImpl implements VendingMachineDao {

    private Map<Integer, Item> vendingMachine = new HashMap<>();
    boolean needsLoading = true;

    @Override
    public Item createItem(Item item) {
        Item newItem = vendingMachine.put(item.getItemKey(), item);
        return newItem;
    }

    @Override
    public Item deleteItem(int key) {
        Item deletedItem = vendingMachine.remove(key);
        return deletedItem;
    }

    @Override
    public Item updateItem(Item item) {
        int id = item.getItemKey();
        vendingMachine.put(id, item);
        return item;
    }

    @Override
    public Item findItem(int itemKey) {
        return vendingMachine.get(itemKey);
    }

    @Override
    public List<Item> readAllItems() {
        if (needsLoading) {
            loadMachine();
            needsLoading = false;
        }

        ArrayList<Item> allItems = new ArrayList(
                vendingMachine.values());
        return allItems;
    }

   
    private void loadMachine() {
        BigDecimal cost1 = new BigDecimal("1.85");
        Item item1 = new Item("Snickers", cost1, 9, 1);
        vendingMachine.put(1, item1);
        BigDecimal cost2 = new BigDecimal("1.50");
        Item item2 = new Item("M & Ms", cost2, 2, 2);
        vendingMachine.put(2, item2);
        BigDecimal cost3 = new BigDecimal("2.10");
        Item item3 = new Item("Pringles", cost3, 5, 3);
        vendingMachine.put(3, item3);
        Item item4 = new Item("Reese's", cost1, 4, 4);
        vendingMachine.put(4, item4);
        BigDecimal cost5 = new BigDecimal("1.25");
        Item item5 = new Item("Pretzels", cost5, 9, 5);
        vendingMachine.put(5, item5);
        BigDecimal cost6 = new BigDecimal("1.95");
        Item item6 = new Item("Twinkies", cost6, 3, 6);
        vendingMachine.put(6, item6);
        BigDecimal cost7 = new BigDecimal("1.75");
        Item item7 = new Item("Doritos", cost7, 11, 7);
        vendingMachine.put(7, item7);
        Item item8 = new Item("Almond Joy", cost1, 0, 8);
        vendingMachine.put(8, item8);
        Item item9 = new Item("Trident", cost6, 6, 9);
        vendingMachine.put(9, item9);

    }

}
