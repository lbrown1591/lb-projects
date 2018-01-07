/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.service;

import com.sg.superherosightingsapp.dto.Location;
import com.sg.superherosightingsapp.dto.Organization;
import com.sg.superherosightingsapp.dto.Power;
import com.sg.superherosightingsapp.dto.Sighting;
import com.sg.superherosightingsapp.dto.SuperPerson;
import com.sg.superherosightingsapp.dto.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author softwareguild
 */
public interface SuperHeroSightingsServiceLayer {

    public void addSuperPerson(SuperPerson superPerson);

    public void deleteSuperPerson(int superPersonId) throws SuperHeroSightingsDataIntegrityException;

    public void updateSuperPerson(SuperPerson superPerson);

    public SuperPerson getSuperPersonById(int id);

    public List<SuperPerson> getAllSuperPersons();

    public void addOrganization(Organization organization);

    public void deleteOrganization(int organizationId) throws SuperHeroSightingsDataIntegrityException;

    public void updateOrganization(Organization organization);

    public Organization getOrganizationById(int id);

    public List<Organization> getAllOrganizations();

    public void addPower(Power power);

    public void deletePower(int powerId) throws SuperHeroSightingsDataIntegrityException;

    public void updatePower(Power power);

    public Power getPowerById(int id);

    public List<Power> getAllPowers();

    public void addLocation(Location location);

    public void deleteLocation(int locationId) throws SuperHeroSightingsDataIntegrityException;

    public void updateLocation(Location location);

    public Location getLocationById(int id);

    public List<Location> getAllLocations();

    public void addSighting(Sighting sighting);

    public void deleteSighting(int sightingId);

    public void updateSighting(Sighting sighting);

    public Sighting getSightingById(int id);

    public List<Sighting> getAllSightings();

    public List<SuperPerson> getSuperPersonsByLocationId(int locationId);

    public List<Location> getLocationsBySuperPersonId(int superPersonId);

    public List<Sighting> getSightingsByDate(LocalDate ld);

    public List<SuperPerson> getAllSuperPersonsByOrganizationId(int organizationId);

    public List<Organization> getAllOrganizationsBySuperPersonId(int superPersondId);

    public boolean checkForError();

    public void resetError();

    public User addUser(User newUser)throws SuperHeroSightingsDuplicateUsernameException;

    public void deleteUser(String username);

    public List<User> getAllUsers();

    public User getUserByUsername(String username);

    public ArrayList<String> getAuthoritiesByUsername(String username);

}
