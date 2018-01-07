/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.controller;

import com.sg.superherosightingsapp.dto.User;
import com.sg.superherosightingsapp.service.SuperHeroSightingsDuplicateUsernameException;
import com.sg.superherosightingsapp.service.SuperHeroSightingsServiceLayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author softwareguild
 */
@Controller
public class UserController {

    SuperHeroSightingsServiceLayer service;
    private PasswordEncoder encoder;
   
   
    @Inject
    public UserController(SuperHeroSightingsServiceLayer service, PasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }

    
    
    @RequestMapping(value = "/displayUserList", method = RequestMethod.GET)
    public String displayUserList(Map<String, Object> model) {
        List users = service.getAllUsers();
        boolean error = service.checkForError();
        service.resetError();
        model.put("users", users);
        model.put("error", error);
        return "displayUserList";
    }
   

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(HttpServletRequest req) {
        User newUser = new User();
        
        newUser.setUsername(req.getParameter("username"));
        String clearPw = req.getParameter("password");
        String hashPw = encoder.encode(clearPw);
        newUser.setPassword(hashPw);

       
        newUser.addAuthority("ROLE_USER");
        if (null != req.getParameter("isAdmin")) {
            newUser.addAuthority("ROLE_ADMIN");
        }

        try{
       service.addUser(newUser);
        }catch(SuperHeroSightingsDuplicateUsernameException e){
        
        }

        return "redirect:displayUserList";
    }
   

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deletUser(@RequestParam("username") String username,
            Map<String, Object> model) {
        service.deleteUser(username);
        return "redirect:displayUserList";
    }
    
    
     @RequestMapping(value = "/displayEditUserForm", method = RequestMethod.GET)
    public String displayEditUserForm(HttpServletRequest request, Model model) {
       String username = request.getParameter("username");
       
        User user = service.getUserByUsername(username);
        ArrayList<String> authorities = service.getAuthoritiesByUsername(username);
        user.setAuthorities(authorities);
        boolean isAdmin = false;
        int size = authorities.size();
        if(size == 2){
        isAdmin = true;
        }
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("user", user);
        return "editUserForm";
    }
    
    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String editUser(HttpServletRequest request) {
        String originalUsername = request.getParameter("originalUsername");
        User user = service.getUserByUsername(originalUsername);
      
        user.setUsername(request.getParameter("username"));
        String isAdmin = request.getParameter("adminRole");
        if(isAdmin != null){
        user.addAuthority(isAdmin);
        user.addAuthority("ROLE_USER");
        }else{
        user.addAuthority("ROLE_USER");
        }
        
        String updatePassword = request.getParameter("change");
        if(updatePassword != null && updatePassword.equalsIgnoreCase("change")){
        String clearPw = request.getParameter("password");
        String hashPw = encoder.encode(clearPw);
        user.setPassword(hashPw);
        }
        service.deleteUser(originalUsername);
        try{
        service.addUser(user);
        }catch(SuperHeroSightingsDuplicateUsernameException e){
        }
        
        return "redirect:displayUserList";
    }
}
