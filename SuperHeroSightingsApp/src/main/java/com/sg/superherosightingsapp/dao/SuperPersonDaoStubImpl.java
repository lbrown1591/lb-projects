/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dao;

import com.sg.superherosightingsapp.dto.Location;
import com.sg.superherosightingsapp.dto.Organization;
import com.sg.superherosightingsapp.dto.Power;
import com.sg.superherosightingsapp.dto.SuperPerson;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author softwareguild
 */
public class SuperPersonDaoStubImpl implements SuperPersonDao{
    
    SuperPerson hero1;
    SuperPerson hero2;
    
    List<SuperPerson> heroList = new ArrayList<>();
    
    SuperPersonDaoStubImpl(){
        Location location1 = new Location();
        location1.setLocationId(1);
        location1.setLocationName("Location 1");
        location1.setLatitude(1);
        location1.setLongitude(1.1);
        
        Organization organization1 = new Organization();
        organization1.setOrganizationId(1);
        organization1.setOrganizationName("Organization 1");
        organization1.setOrganizationDescription("Organization Description1");
        organization1.setPhone("999-333-1000");
        organization1.setEmail("org1@yahoo.com");
        organization1.setLocation(location1);
        
        Organization organization2 = new Organization();
        organization2.setOrganizationId(2);
        organization2.setOrganizationName("Organization 2");
        organization2.setOrganizationDescription("Organization Description2");
        organization2.setPhone("222-222-2222");
        organization2.setEmail("org2@yahoo.com");
        organization2.setLocation(location1);
        List<Organization> organizationList = new ArrayList<>();
        
        organizationList.add(organization1);
        organizationList.add(organization2);
        
        Power power1 = new Power();
        Power power2 = new Power();
        List<Power> powerList = new ArrayList<>();
        power1.setPowerId(1);
        power1.setPowerName("Power 1");
        power1.setPowerDescription("Power Description1");
        
        powerList.add(power1);
        
        power2.setPowerId(2);
        power2.setPowerName("Power 2");
        power2.setPowerDescription("Power Description2");
        
        powerList.add(power2);
        
        
        hero1.setSuperPersonId(1);
        hero1.setName("Hero 1");
        hero1.setDescription("Hero Description 1");
        hero1.setOrganizations(organizationList);
        hero1.setPowers(powerList);
       
        hero2.setSuperPersonId(2);
        hero2.setName("Hero 2");
        hero2.setDescription("Hero Description 2");
        hero2.setOrganizations(organizationList);
        hero2.setPowers(powerList);
    
        heroList.add(hero1);
        heroList.add(hero2);
    }

    @Override
    public void addSuperPerson(SuperPerson superPerson) {
        return;
    }

    @Override
    public void deleteSuperPerson(int superPersonId) {
        return;
    }

    @Override
    public void updateSuperPerson(SuperPerson superPerson) {
        return;
    }

    @Override
    public SuperPerson getSuperPersonById(int id) {
        if(id == hero1.getSuperPersonId()){
            return hero1;
        }else{
            return null;
        }
    }

    @Override
    public List<SuperPerson> getAllSuperPersons() {
        return heroList;
    }

    @Override
    public List<SuperPerson> findHerosForSighting(int sightingId) {
        return heroList;
    }

    @Override
    public List<SuperPerson> getSuperPersonsByLocationId(int locationId) {
       return heroList;
    }

    @Override
    public List<SuperPerson> getAllSuperPersonsByOrganizationId(int organizationId) {
        return heroList;
    }

    @Override
    public List<SuperPerson> getAllSuperPersonsByPowerId(int powerId) {
       return heroList;
    }
    
}
