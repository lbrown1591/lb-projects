/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.service;

/**
 *
 * @author softwareguild
 */
public class SuperHeroSightingsDuplicateUsernameException extends Exception{
    
     public SuperHeroSightingsDuplicateUsernameException(String message) {
        super(message);
    }

    public SuperHeroSightingsDuplicateUsernameException(String message, Throwable cause) {
        super(message, cause);
    }
}
