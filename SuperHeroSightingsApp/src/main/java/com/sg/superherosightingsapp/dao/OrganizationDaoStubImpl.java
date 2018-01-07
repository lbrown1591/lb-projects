/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dao;

import com.sg.superherosightingsapp.dto.Location;
import com.sg.superherosightingsapp.dto.Organization;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author softwareguild
 */
public class OrganizationDaoStubImpl implements OrganizationDao {

    Organization organization1;
    Organization organization2;

    List<Organization> organizationList = new ArrayList<>();

    public OrganizationDaoStubImpl() {
        Location location1 = new Location();
        location1.setLocationId(1);
        location1.setLocationName("Location 1");
        location1.setLatitude(1);
        location1.setLongitude(1.1);
        
        organization1.setOrganizationId(1);
        organization1.setOrganizationName("Organization 1");
        organization1.setOrganizationDescription("Organization Description1");
        organization1.setPhone("999-333-1000");
        organization1.setEmail("org1@yahoo.com");
        organization1.setLocation(location1);
        
        organizationList.add(organization1);
        
        organization2.setOrganizationId(2);
        organization2.setOrganizationName("Organization 2");
        organization2.setOrganizationDescription("Organization Description2");
        organization2.setPhone("222-222-2222");
        organization2.setEmail("org2@yahoo.com");
        organization2.setLocation(location1);
        
        organizationList.add(organization2);
    }

    @Override
    public void addOrganization(Organization organization) {
        return;
    }

    @Override
    public void deleteOrganization(int organizationId) {
        return;
    }

    @Override
    public void updateOrganization(Organization organization) {
        return;
    }

    @Override
    public Organization getOrganizationById(int id) {
         if(id == organization1.getOrganizationId()){
            return organization1;
        }else{
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationList;
    }

    @Override
    public List<Organization> findOrganizationsForHero(int heroId) {
       return organizationList;
    }

    @Override
    public List<Organization> getAllOrganizationsBySuperPersonId(int superPersonId) {
        return organizationList;
    }

    @Override
    public List<Organization> getAllOrganizationsByLocationId(int locationId) {
      return organizationList;
    }

}
