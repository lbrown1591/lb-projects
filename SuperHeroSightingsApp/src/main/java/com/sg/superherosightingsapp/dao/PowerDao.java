/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dao;

import com.sg.superherosightingsapp.dto.Power;
import java.util.List;

/**
 *
 * @author softwareguild
 */
public interface PowerDao {
    
     public void addPower(Power power);

    public void deletePower(int powerId);

    public void updatePower(Power power);

    public Power getPowerById(int id);

    public List<Power> getAllPowers();
    
    public List<Power> findPowersForHero(int heroId);
    
}
