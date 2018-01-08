/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author softwareguild
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {

    Item item1;
    Item item2;
    List<Item> itemList = new ArrayList<>();

    public VendingMachineDaoStubImpl() {
        BigDecimal cost1 = new BigDecimal("2.00");
        item1 = new Item("Doritos", cost1, 1, 1);

        itemList.add(item1);

        BigDecimal cost2 = new BigDecimal("1.00");
        item2 = new Item("KitKat", cost2, 0, 2);
        itemList.add(item2);
    }

    @Override
    public Item createItem(Item item) {
        if (item.equals(item1.getItemKey())) {
            return item1;
        } else {
            return null;
        }
    }

    @Override
    public Item deleteItem(int key) {
        if (key == item1.getItemKey()) {
            return item1;
        } else {
            return null;
        }
    }

    @Override
    public Item updateItem(Item item) {
        if (item.equals(item1)) {
            return item1;
        } else {
            return null;
        }
    }

    @Override
    public Item findItem(int itemKey) {
       if(itemKey == item1.getItemKey()){
            return item1;
        }else{
            return null;
        }
    }

    @Override
    public List<Item> readAllItems() {
         return itemList;
    }

}
