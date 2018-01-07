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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author softwareguild
 */
public class SightingDaoStubImpl implements SightingDao {

    Sighting sighting1;
    Sighting sighting2;

    List<Sighting> sightingList = new ArrayList<>();

    public SightingDaoStubImpl() {

        List<SuperPerson> heroList = new ArrayList<>();

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

        SuperPerson hero1 = new SuperPerson();
        hero1.setSuperPersonId(1);
        hero1.setName("Hero 1");
        hero1.setDescription("Hero Description 1");
        hero1.setOrganizations(organizationList);
        hero1.setPowers(powerList);

        SuperPerson hero2 = new SuperPerson();
        hero2.setSuperPersonId(2);
        hero2.setName("Hero 2");
        hero2.setDescription("Hero Description 2");
        hero2.setOrganizations(organizationList);
        hero2.setPowers(powerList);

        heroList.add(hero1);
        heroList.add(hero2);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld1 = LocalDate.parse("2016-01-01", formatter);
        LocalDate ld2 = LocalDate.parse("2016-02-02", formatter);

        sighting1.setLocation(location1);
        sighting1.setHeroesSpotted(heroList);
        sighting1.setDateOfSighting(ld1);
        sighting1.setSightingId(1);

        sighting2.setLocation(location1);
        sighting2.setHeroesSpotted(heroList);
        sighting2.setDateOfSighting(ld2);
        sighting2.setSightingId(2);

        sightingList.add(sighting1);
        sightingList.add(sighting2);
    }

    @Override
    public void addSighting(Sighting sighting) {
        return;
    }

    @Override
    public void deleteSighting(int sightingId) {
        return;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        return;
    }

    @Override
    public Sighting getSightingById(int id) {
        if (id == sighting1.getSightingId()) {
            return sighting1;
        } else {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        return sightingList;
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate ld) {
        if (ld.compareTo(sighting1.getDateOfSighting()) == 0) {
            List<Sighting> matchingSightings = new ArrayList<>();
            matchingSightings.add(sighting1);
            return matchingSightings;
        } else {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightingsBySuperPersonId(int superPersonId) {
        if (superPersonId == sighting1.getHeroesSpotted().get(0).getSuperPersonId() && superPersonId == sighting2.getHeroesSpotted().get(1).getSuperPersonId()) {
            return sightingList;
        } else {
            return null;
        }

    }

    @Override
    public List<Sighting> getAllSightingsByLocationId(int locationId) {
        if (locationId == sighting1.getLocation().getLocationId() && locationId == sighting2.getLocation().getLocationId()) {
            return sightingList;
        } else {
            return null;
        }
    }

}
