/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dao;

import com.sg.superherosightingsapp.dto.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author softwareguild
 */
public interface UserDao {
    
    
 public User addUser(User newUser);

 public void deleteUser(String username);
 
 public List<User> getAllUsers();
 
 public User getUserByUsername(String username);
 
 public ArrayList<String> getAuthoritiesByUsername(String username);
 
}
