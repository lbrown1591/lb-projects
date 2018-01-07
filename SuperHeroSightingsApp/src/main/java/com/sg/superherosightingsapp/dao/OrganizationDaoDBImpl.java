/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dao;

import com.sg.superherosightingsapp.dto.Organization;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author softwareguild
 */
public class OrganizationDaoDBImpl implements OrganizationDao {
    
    
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    

    private static final String SQL_INSERT_ORGANIZATION
            = "insert into Organization (OrganizationName, LocationID, Description, OrganizationPhone, OrganizationEmail) values (?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_ORGANIZATION
            = "delete from Organization where OrganizationID = ?";

    private static final String SQL_DELETE_ORGANIZATIONS_BY_LOCATION_ID
            = "delete from Organization where LocationID = ?";

    private static final String SQL_UPDATE_ORGANIZATION
            = "update Organization set OrganizationName = ?, LocationID= ?, Description = ?, OrganizationPhone = ?, "
            + "OrganizationEmail = ? where OrganizationID =  ?";

    private static final String SQL_SELECT_ORGANIZATION
            = "select Organization.OrganizationID, Organization.OrganizationName, Organization.LocationID, Organization.Description, "
            + "Organization.OrganizationPhone, Organization.OrganizationEmail from Organization where Organization.OrganizationID = ?";

    private static final String SQL_SELECT_ALL_ORGANIZATIONS
            = "select Organization.OrganizationID, Organization.OrganizationName, Organization.LocationID, Organization.Description, "
            + "Organization.OrganizationPhone, Organization.OrganizationEmail from Organization";
    
    private static final String SQL_DELETE_SUPER_PERSON_ORGANIZATION_ORGANIZATION_ID
            = "delete from SuperPersonOrganization where OrganizationID = ?";
    
      private static final String SQL_SELECT_ORGANIZATIONS_BY_SUPER_PERSON_ID
            = "select org.OrganizationID, org.OrganizationName, org.LocationID, org.Description, "
            + "org.OrganizationPhone, org.OrganizationEmail from Organization org join SuperPersonOrganization spo on SuperPersonID "
            + "where org.OrganizationID = spo.OrganizationID "
            + "and spo.SuperPersonID  =  ?;";
      
       private static final String SQL_SELECT_ORGANIZATIONS_BY_LOCATION_ID
            = "select org.OrganizationID, org.OrganizationName, org.LocationID, org.Description, "
            + "org.OrganizationPhone, org.OrganizationEmail from Organization org "
            + "where org.LocationID  =  ?;";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addOrganization(Organization organization) {
        jdbcTemplate.update(SQL_INSERT_ORGANIZATION,
                organization.getOrganizationName(),
                organization.getLocation().getLocationId(),
                organization.getOrganizationDescription(),
                organization.getPhone(),
                organization.getEmail());

        int organizationId
                = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                        Integer.class);

        organization.setOrganizationId(organizationId);
    }

    @Override
    public void deleteOrganization(int organizationId) {
        jdbcTemplate.update(SQL_DELETE_SUPER_PERSON_ORGANIZATION_ORGANIZATION_ID, organizationId);
        jdbcTemplate.update(SQL_DELETE_ORGANIZATION, organizationId);
    }

    @Override
    public void updateOrganization(Organization organization) {
        jdbcTemplate.update(SQL_UPDATE_ORGANIZATION,
                organization.getOrganizationName(),
                organization.getLocation().getLocationId(),
                organization.getOrganizationDescription(),
                organization.getPhone(),
                organization.getEmail(),
                organization.getOrganizationId());
    }

    @Override
    public Organization getOrganizationById(int id) {
        try {
            Organization organization = jdbcTemplate.queryForObject(SQL_SELECT_ORGANIZATION,
                    new OrganizationMapper(),
                    id);
            return organization;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        List<Organization> organizations = jdbcTemplate.query(SQL_SELECT_ALL_ORGANIZATIONS,
                new OrganizationMapper());
     
        return organizations;
    }
    
    @Override
    public List<Organization> findOrganizationsForHero(int heroId) {
        List<Organization> organizations = jdbcTemplate.query(SQL_SELECT_ORGANIZATIONS_BY_SUPER_PERSON_ID,
                new OrganizationMapper(),
                heroId);
        return organizations;
    }

    @Override
    public List<Organization> getAllOrganizationsBySuperPersonId(int superPersonId) {
         List<Organization> organizations =  jdbcTemplate.query(SQL_SELECT_ORGANIZATIONS_BY_SUPER_PERSON_ID,
                new OrganizationMapper(),
                superPersonId);
         
         return organizations;
    }
    
     @Override
    public List<Organization> getAllOrganizationsByLocationId(int locationId) {
         List<Organization> organizations =  jdbcTemplate.query(SQL_SELECT_ORGANIZATIONS_BY_LOCATION_ID,
                new OrganizationMapper(),
                locationId);
         
         return organizations;
    }
    
    
     
    
    
    
    
    private static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Organization organization = new Organization();

            organization.setOrganizationName(rs.getString("organizationName"));
            //How do I set Location from Location ID? helper method
            organization.setOrganizationDescription(rs.getString("description"));
            organization.setPhone(rs.getString("organizationPhone"));
            organization.setEmail(rs.getString("organizationEmail"));
            organization.setOrganizationId(rs.getInt("organizationId"));

            return organization;
        }
    }
}