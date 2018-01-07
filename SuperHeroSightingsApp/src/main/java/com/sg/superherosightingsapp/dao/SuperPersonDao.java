/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dao;

import com.sg.superherosightingsapp.dto.Location;
import com.sg.superherosightingsapp.dto.Organization;
import com.sg.superherosightingsapp.dto.Power;
import com.sg.superherosightingsapp.dto.Sighting;
import com.sg.superherosightingsapp.dto.SuperPerson;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author softwareguild
 */
public interface SuperPersonDao {
    
    //SuperPerson CRUD
    public void addSuperPerson(SuperPerson superPerson);

    public void deleteSuperPerson(int superPersonId);

    public void updateSuperPerson(SuperPerson superPerson);

    public SuperPerson getSuperPersonById(int id);

    public List<SuperPerson> getAllSuperPersons();
    
    public List<SuperPerson> findHerosForSighting(int sightingId);
    
    public List<SuperPerson> getSuperPersonsByLocationId(int locationId);
  
    public List<SuperPerson> getAllSuperPersonsByOrganizationId(int organizationId);
    
    public List<SuperPerson> getAllSuperPersonsByPowerId(int powerId);
 
}
