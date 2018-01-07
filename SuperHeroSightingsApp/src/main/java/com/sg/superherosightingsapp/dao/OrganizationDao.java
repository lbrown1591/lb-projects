/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dao;

import com.sg.superherosightingsapp.dto.Organization;
import java.util.List;

/**
 *
 * @author softwareguild
 */
public interface OrganizationDao {
    
     //Organization CRUD
    
    public void addOrganization(Organization organization);

    public void deleteOrganization(int organizationId);

    public void updateOrganization(Organization organization);

    public Organization getOrganizationById(int id);

    public List<Organization> getAllOrganizations();
    
    public List<Organization> findOrganizationsForHero(int heroId);
    
    public List<Organization> getAllOrganizationsBySuperPersonId(int superPersonId);
    
    public List<Organization> getAllOrganizationsByLocationId(int locationId);
}
