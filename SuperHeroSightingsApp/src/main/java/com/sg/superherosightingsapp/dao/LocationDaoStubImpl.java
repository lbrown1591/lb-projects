/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dao;

import com.sg.superherosightingsapp.dto.Location;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author softwareguild
 */
public class LocationDaoStubImpl implements LocationDao{
    
    Location location1;
    Location location2;
    
    List<Location> locationList = new ArrayList<>();
    
    public LocationDaoStubImpl(){
        location1.setLocationId(1);
        location1.setLocationName("Location 1");
        location1.setLatitude(1);
        location1.setLongitude(1.1);
        
        locationList.add(location1);
        
        location2.setLocationId(2);
        location2.setLocationName("Location 2");
        location2.setLatitude(2);
        location2.setLongitude(2.2);
        
        locationList.add(location2);
        
    }

    @Override
    public void addLocation(Location location) {
       return;
    }

    @Override
    public void deleteLocation(int locationId) {
      return;
    }

    @Override
    public void updateLocation(Location location) {
       return;
    }

    @Override
    public Location getLocationById(int id) {
        if(id == location1.getLocationId()){
            return location1;
        }else{
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
       return locationList;
    }

    @Override
    public Location findLocationForOrganization(int organizationId) {
       return location1;
    }

    @Override
    public Location findLocationForSighting(int sightingId) {
       return location1;
    }

    @Override
    public List<Location> getLocationsBySuperPersonId(int superPersonId) {
      return locationList;
    }
    
}
