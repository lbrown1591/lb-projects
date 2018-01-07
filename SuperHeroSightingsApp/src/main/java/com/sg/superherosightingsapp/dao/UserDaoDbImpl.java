/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dao;

import com.sg.superherosightingsapp.dto.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author softwareguild
 */
public class UserDaoDbImpl implements UserDao {

    private static final String SQL_INSERT_USER
            = "insert into users (username, password, enabled) values (?, ?, 1)";
    private static final String SQL_INSERT_AUTHORITY
            = "insert into authorities (username, authority) values (?, ?)";
    private static final String SQL_DELETE_USER
            = "delete from users where username = ?";
    private static final String SQL_DELETE_AUTHORITIES
            = "delete from authorities where username = ?";
    private static final String SQL_GET_ALL_USERS
            = "select * from users";
    private static final String SQL_SELECT_USER
            = "select user.user_id, user.username, user.password "
            + "from users user where username = ?";
    private static final String SQL_SELECT_AUTHORITIES_BY_USERNAME
            = "select aut.authority from authorities aut "
            + "where aut.username  =  ?";
    private static final String SQL_UPDATE_LOCATION
            = "update users set username = ?, password = ? "
            + "where username =  ?";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUser(User newUser) {
        jdbcTemplate.update(SQL_INSERT_USER,
                newUser.getUsername(),
                newUser.getPassword());
        newUser.setId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class));

       
        ArrayList<String> authorities = newUser.getAuthorities();
        for (String authority : authorities) {
            jdbcTemplate.update(SQL_INSERT_AUTHORITY,
                    newUser.getUsername(),
                    authority);
        }

        return newUser;
    }

    @Override
    public void deleteUser(String username) {
        
        jdbcTemplate.update(SQL_DELETE_AUTHORITIES, username);
        jdbcTemplate.update(SQL_DELETE_USER, username);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(SQL_GET_ALL_USERS, new UserMapper());
    }

    @Override
    public User getUserByUsername(String username) {

        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_USER,
                    new UserMapper(), username);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public ArrayList<String> getAuthoritiesByUsername(String username) {

        String sql = "select aut.authority from authorities aut where aut.username  = '" + username + "'";

        List<String> data = jdbcTemplate.queryForList(sql, String.class);
        ArrayList<String> authorities = new ArrayList<>(data);

        return authorities;
    }

    private static final class UserMapper implements RowMapper<User> {

        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }

    }
    
}
