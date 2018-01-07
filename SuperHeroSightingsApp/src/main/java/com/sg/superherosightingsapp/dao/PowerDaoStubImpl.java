/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dao;

import com.sg.superherosightingsapp.dto.Power;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author softwareguild
 */
public class PowerDaoStubImpl implements PowerDao{
    
    Power power1;
    Power power2;
    List<Power> powerList = new ArrayList<>();
    
    public PowerDaoStubImpl(){
    
        power1.setPowerId(1);
        power1.setPowerName("Power 1");
        power1.setPowerDescription("Power Description1");
        
        powerList.add(power1);
        
        power2.setPowerId(2);
        power2.setPowerName("Power 2");
        power2.setPowerDescription("Power Description2");
        
        powerList.add(power2);
    }

    @Override
    public void addPower(Power power) {
       return;
    }

    @Override
    public void deletePower(int powerId) {
       return;
    }

    @Override
    public void updatePower(Power power) {
       return;
    }

    @Override
    public Power getPowerById(int id) {
          if(id == power1.getPowerId()){
            return power1;
        }else{
            return null;
        }
    }

    @Override
    public List<Power> getAllPowers() {
        return powerList;
    }

    @Override
    public List<Power> findPowersForHero(int heroId) {
        return powerList;
    }
    
}
