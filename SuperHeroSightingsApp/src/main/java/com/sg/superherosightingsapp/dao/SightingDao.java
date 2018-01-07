/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dao;

import com.sg.superherosightingsapp.dto.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author softwareguild
 */
public interface SightingDao {
    
     public void addSighting(Sighting sighting);

    public void deleteSighting(int sightingId);

    public void updateSighting(Sighting sighting);

    public Sighting getSightingById(int id);

    public List<Sighting> getAllSightings();
    
    public List<Sighting> getSightingsByDate(LocalDate ld);
    
    public List<Sighting> getAllSightingsBySuperPersonId(int superPersonId);
    
    public List<Sighting> getAllSightingsByLocationId(int locationId) ;
}
