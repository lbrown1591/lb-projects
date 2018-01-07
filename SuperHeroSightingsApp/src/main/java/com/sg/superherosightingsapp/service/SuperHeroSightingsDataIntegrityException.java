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
public class SuperHeroSightingsDataIntegrityException extends Exception{
    
    public SuperHeroSightingsDataIntegrityException(String message) {
        super(message);
    }

    public SuperHeroSightingsDataIntegrityException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
