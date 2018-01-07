/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dao;

import com.sg.superherosightingsapp.dto.Location;
import java.util.List;

/**
 *
 * @author softwareguild
 */
public interface LocationDao {
    
    public void addLocation(Location location);

    public void deleteLocation(int locationId);

    public void updateLocation(Location location);

    public Location getLocationById(int id);

    public List<Location> getAllLocations();
    
    public Location findLocationForOrganization(int organizationId);
    
    public Location findLocationForSighting(int sightingId);
    
    public List<Location> getLocationsBySuperPersonId(int superPersonId);
}
