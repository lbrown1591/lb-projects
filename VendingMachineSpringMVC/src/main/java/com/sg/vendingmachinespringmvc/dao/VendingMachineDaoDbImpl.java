/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Item;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author softwareguild
 */
public class VendingMachineDaoDbImpl implements VendingMachineDao {

    private static final String SQL_INSERT_ITEM
            = "insert into item "
            + "(item_name, inventory, item_price) "
            + "values (?, ?, ?)";

    private static final String SQL_DELETE_ITEM
            = "delete from item where item_id = ?";

    private static final String SQL_SELECT_ITEM
            = "select * from item where item_id = ?";

    private static final String SQL_UPDATE_ITEM
            = "update item set "
            + "item_name = ?, inventory = ?, item_price = ? "
            + "where item_id = ?";

    private static final String SQL_SELECT_ALL_ITEMS
            = "select * from item";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Item createItem(Item item) {
        jdbcTemplate.update(SQL_INSERT_ITEM,
                item.getName(),
                item.getNumOfItems(),
                item.getCost());

        int newId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class);

        item.setItemKey(newId);
        return item;
    }

    @Override
    public Item deleteItem(int key) {
        try {
            Item itemToBeDeleted = jdbcTemplate.queryForObject(SQL_SELECT_ITEM,
                    new ItemMapper(), key);
            jdbcTemplate.update(SQL_DELETE_ITEM, key);
            return itemToBeDeleted;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Item updateItem(Item item) {
        jdbcTemplate.update(SQL_UPDATE_ITEM,
                item.getName(),
                item.getNumOfItems(),
                item.getCost(),
                item.getItemKey());
        return item;
    }

    @Override
    public Item findItem(int itemKey) {
         try {
            Item item = jdbcTemplate.queryForObject(SQL_SELECT_ITEM,
                    new ItemMapper(), itemKey);
            return item;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Item> readAllItems() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ITEMS, 
                              new ItemMapper());
    }

    private static final class ItemMapper implements RowMapper<Item> {

        public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
            Item item = new Item();
            item.setItemKey(rs.getInt("item_id"));
            item.setName(rs.getString("item_name"));
            item.setNumOfItems(rs.getInt("inventory"));
            item.setCost(rs.getBigDecimal("item_price"));

            return item;
        }
    }

}
