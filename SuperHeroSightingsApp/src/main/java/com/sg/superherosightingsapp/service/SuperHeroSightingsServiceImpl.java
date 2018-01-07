/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.service;

import com.sg.superherosightingsapp.dao.LocationDao;
import com.sg.superherosightingsapp.dao.OrganizationDao;
import com.sg.superherosightingsapp.dao.PowerDao;
import com.sg.superherosightingsapp.dao.SightingDao;
import com.sg.superherosightingsapp.dao.SuperPersonDao;
import com.sg.superherosightingsapp.dao.UserDao;
import com.sg.superherosightingsapp.dto.Location;
import com.sg.superherosightingsapp.dto.Organization;
import com.sg.superherosightingsapp.dto.Power;
import com.sg.superherosightingsapp.dto.Sighting;
import com.sg.superherosightingsapp.dto.SuperPerson;
import com.sg.superherosightingsapp.dto.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author softwareguild
 */
public class SuperHeroSightingsServiceImpl implements SuperHeroSightingsServiceLayer {

    SuperPersonDao superPersonDao;
    LocationDao locationDao;
    OrganizationDao organizationDao;
    PowerDao powerDao;
    SightingDao sightingDao;
    UserDao userDao;
    private JdbcTemplate jdbcTemplate;
    boolean error = false;

    public SuperHeroSightingsServiceImpl(SuperPersonDao superPersonDao, LocationDao locationDao,
            OrganizationDao organizationDao, PowerDao powerDao, SightingDao sightingDao, UserDao userDao) {
        this.superPersonDao = superPersonDao;
        this.locationDao = locationDao;
        this.organizationDao = organizationDao;
        this.powerDao = powerDao;
        this.sightingDao = sightingDao;
        this.userDao = userDao;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addSuperPerson(SuperPerson superPerson) {
        superPersonDao.addSuperPerson(superPerson);

    }

    @Override
    public void deleteSuperPerson(int superPersonId) throws SuperHeroSightingsDataIntegrityException {
        List<Sighting> sightings = sightingDao.getAllSightingsBySuperPersonId(superPersonId);
        if (sightings.isEmpty()) {
            superPersonDao.deleteSuperPerson(superPersonId);
        } else {
            throw new SuperHeroSightingsDataIntegrityException("The hero you'd like to delete currently is associated with a Sighting. "
                    + "Please alter any entries currently relying on this hero before deleting.");
        }
    }

    @Override
    public void updateSuperPerson(SuperPerson superPerson) {
        superPersonDao.updateSuperPerson(superPerson);

    }

    @Override
    public SuperPerson getSuperPersonById(int id) {
        SuperPerson hero = superPersonDao.getSuperPersonById(id);
        List<Organization> organizations = organizationDao.findOrganizationsForHero(id);
        associateLocationsWithOrganizations(organizations);
        hero.setOrganizations(organizations);
        List<Power> powers = powerDao.findPowersForHero(id);
        hero.setPowers(powers);

        return hero;
    }

    @Override
    public List<SuperPerson> getAllSuperPersons() {
        List<SuperPerson> heros = superPersonDao.getAllSuperPersons();

        heros = associateOrganizationsAndPowersWithHeros(heros);

        return heros;
    }

    @Override
    public void addOrganization(Organization organization) {
        organizationDao.addOrganization(organization);
    }

    @Override
    public void deleteOrganization(int organizationId) throws SuperHeroSightingsDataIntegrityException {
        List<SuperPerson> heros = superPersonDao.getAllSuperPersonsByOrganizationId(organizationId);
        if (!heros.isEmpty()) {
            error = true;
            throw new SuperHeroSightingsDataIntegrityException("The organization you'd like to delete currently has heros in it. "
                    + "Please alter any entries currently associated with this organization before deleting.");
        }
        organizationDao.deleteOrganization(organizationId);
    }

    @Override
    public void updateOrganization(Organization organization) {

        organizationDao.updateOrganization(organization);
    }

    @Override
    public Organization getOrganizationById(int id) {
        Organization organization = organizationDao.getOrganizationById(id);

        Location location = locationDao.findLocationForOrganization(organization.getOrganizationId());
        organization.setLocation(location);

        return organization;
    }

    @Override
    public List<Organization> getAllOrganizations() {
        List<Organization> organizations = organizationDao.getAllOrganizations();
        organizations = associateLocationsWithOrganizations(organizations);
        return organizations;
    }

    @Override
    public void addPower(Power power) {
        powerDao.addPower(power);
    }

    @Override
    public void deletePower(int powerId) throws SuperHeroSightingsDataIntegrityException {
        List<SuperPerson> heros = superPersonDao.getAllSuperPersonsByPowerId(powerId);
        if (heros.isEmpty()) {
            powerDao.deletePower(powerId);
        } else {
            throw new SuperHeroSightingsDataIntegrityException("The power you'd like to delete is currently associated with heros. "
                    + "Please alter any entries currently relying on this power before deleting.");
        }

        powerDao.deletePower(powerId);

    }

    @Override
    public void updatePower(Power power) {
        powerDao.updatePower(power);
    }

    @Override
    public Power getPowerById(int id) {
        return powerDao.getPowerById(id);
    }

    @Override
    public List<Power> getAllPowers() {
        return powerDao.getAllPowers();
    }

    @Override
    public void addLocation(Location location) {
        locationDao.addLocation(location);
    }

    @Override
    public void deleteLocation(int locationId) throws SuperHeroSightingsDataIntegrityException {
        checkLocationForRelationships(locationId);
        locationDao.deleteLocation(locationId);
    }

    @Override
    public void updateLocation(Location location) {
        locationDao.updateLocation(location);
    }

    @Override
    public Location getLocationById(int id) {
        return locationDao.getLocationById(id);
    }

    @Override
    public List<Location> getAllLocations() {

        return locationDao.getAllLocations();
    }

    @Override
    public void addSighting(Sighting sighting) {
        sightingDao.addSighting(sighting);
    }

    @Override
    public void deleteSighting(int sightingId) {
        sightingDao.deleteSighting(sightingId);
    }

    @Override
    public void updateSighting(Sighting sighting) {
        sightingDao.updateSighting(sighting);
    }

    @Override
    public Sighting getSightingById(int id) {
        Sighting sighting = sightingDao.getSightingById(id);
        Location location = locationDao.findLocationForSighting(id);
        sighting.setLocation(location);
        List<SuperPerson> heros = superPersonDao.findHerosForSighting(id);
        heros = associateOrganizationsAndPowersWithHeros(heros);
        sighting.setHeroesSpotted(heros);
        return sighting;
    }

    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> sightings = sightingDao.getAllSightings();
        return associateLocationsAndHerosWithSightings(sightings);

    }

    @Override
    public List<SuperPerson> getSuperPersonsByLocationId(int locationId) {
        List<SuperPerson> superHeros = superPersonDao.getSuperPersonsByLocationId(locationId);
        superHeros = associateOrganizationsAndPowersWithHeros(superHeros);
        return superHeros;
    }

    @Override
    public List<Location> getLocationsBySuperPersonId(int superPersonId) {
        return locationDao.getLocationsBySuperPersonId(superPersonId);
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate ld) {
        List<Sighting> sightings = sightingDao.getSightingsByDate(ld);
        return associateLocationsAndHerosWithSightings(sightings);
    }

    @Override
    public List<SuperPerson> getAllSuperPersonsByOrganizationId(int organizationId) {
        List<SuperPerson> heros = superPersonDao.getAllSuperPersonsByOrganizationId(organizationId);
        return associateOrganizationsAndPowersWithHeros(heros);
    }

    @Override
    public List<Organization> getAllOrganizationsBySuperPersonId(int superPersondId) {
        List<Organization> organizations = organizationDao.getAllOrganizationsBySuperPersonId(superPersondId);
        return associateLocationsWithOrganizations(organizations);
    }

    @Override
    public boolean checkForError() {
        return error;
    }

    @Override
    public void resetError() {
        error = false;
    }

    private List<SuperPerson>
            associateOrganizationsAndPowersWithHeros(List<SuperPerson> herosList) {

        for (SuperPerson currentHero : herosList) {
            List<Organization> organizations = organizationDao.findOrganizationsForHero(currentHero.getSuperPersonId());
            organizations = associateLocationsWithOrganizations(organizations);
            currentHero.setOrganizations(organizations);
            List<Power> powers = powerDao.findPowersForHero(currentHero.getSuperPersonId());
            currentHero.setPowers(powers);
        }
        return herosList;
    }

    private List<Sighting>
            associateLocationsWithSightings(List<Sighting> sightingsList) {

        for (Sighting currentSighting : sightingsList) {

            Location currentLocation = locationDao.findLocationForSighting(currentSighting.getSightingId());
            currentSighting.setLocation(currentLocation);

        }
        return sightingsList;
    }

    private List<Organization> associateLocationsWithOrganizations(List<Organization> organizationsList) {

        for (Organization currentOrganization : organizationsList) {

            Location currentLocation = locationDao.findLocationForOrganization(currentOrganization.getOrganizationId());
            currentOrganization.setLocation(currentLocation);

        }
        return organizationsList;
    }

    private List<Sighting> associateLocationsAndHerosWithSightings(List<Sighting> sightingsList) {

        for (Sighting currentSighting : sightingsList) {
            Location location = locationDao.findLocationForSighting(currentSighting.getSightingId());
            currentSighting.setLocation(location);
            List<SuperPerson> heros = superPersonDao.findHerosForSighting(currentSighting.getSightingId());
            heros = associateOrganizationsAndPowersWithHeros(heros);
            currentSighting.setHeroesSpotted(heros);
        }
        return sightingsList;
    }

    private void checkLocationForRelationships(int locationId) throws SuperHeroSightingsDataIntegrityException {
        List<Sighting> sightings = sightingDao.getAllSightingsByLocationId(locationId);

        if (!sightings.isEmpty()) {
            error = true;
            throw new SuperHeroSightingsDataIntegrityException("The location you'd like to delete is currently assigned to a Sighting. "
                    + "Please alter any entries currently relying on this location before deleting.");
        }

        List<Organization> organizations = organizationDao.getAllOrganizationsByLocationId(locationId);

        if (!organizations.isEmpty()) {
            error = true;
            throw new SuperHeroSightingsDataIntegrityException("The location you'd like to delete is currently assigned to a Organization. "
                    + "Please alter any entries currently relying on this location before deleting.");
        }

    }

    @Override
    public User addUser(User newUser) throws SuperHeroSightingsDuplicateUsernameException {
        List<User> users = userDao.getAllUsers();

        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(newUser.getUsername())) {
                error = true;
                throw new SuperHeroSightingsDuplicateUsernameException("That username is already in use. Please try another username.");
            }
        }
        return userDao.addUser(newUser);
    }

    @Override
    public void deleteUser(String username) {
        userDao.deleteUser(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public ArrayList<String> getAuthoritiesByUsername(String username) {
        return userDao.getAuthoritiesByUsername(username);
    }

}
