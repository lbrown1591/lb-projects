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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class SuperPersonDaoDbImpl implements SuperPersonDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    

    //Super person prepared statements
    private static final String SQL_INSERT_SUPER_PERSON
            = "insert into SuperPerson "
            + "(SuperName, Description) "
            + "values (?, ?)";
    

    private static final String SQL_INSERT_SUPER_PERSON_ORGANIZATION
            = "insert into SuperPersonOrganization (SuperPersonID, OrganizationID) values(?, ?)";

    private static final String SQL_INSERT_SUPER_PERSON_POWER
            = "insert into SuperPersonPower (SuperPersonID, PowerID) values(?, ?)";

    
    private static final String SQL_DELETE_SUPER_PERSON
            = "delete from SuperPerson where SuperPersonID = ?";

    //SuperPersonOrganization delete
    private static final String SQL_DELETE_SUPER_PERSON_ORGANIZATION
            = "delete from SuperPersonOrganization where SuperPersonID = ?";
    
    private static final String SQL_DELETE_SUPER_PERSON_ORGANIZATION_ORGANIZATION_ID
            = "delete from SuperPersonOrganization where OrganizationID = ?";

    //SuperPersonPower delete
    private static final String SQL_DELETE_SUPER_PERSON_POWER
            = "delete from SuperPersonPower where SuperPersonID = ?";

    //SuperPersonSighting delete
    private static final String SQL_DELETE_SUPER_PERSON_SIGHTING
            = "delete from SuperPersonSighting where SightingID = ?";
    
    private static final String SQL_DELETE_SUPER_PERSON_SIGHTING_SUPER_PERSON_ID
            = "delete from SuperPersonSighting where SuperPersonID = ?";
    
    private static final String SQL_UPDATE_SUPER_PERSON
            = "update SuperPerson set SuperName = ?, Description = ?"
            + "where SuperPersonID = ?";

    private static final String SQL_SELECT_SUPER_PERSON
            = "select SuperPerson.SuperPersonID, SuperPerson.SuperName, SuperPerson.Description from SuperPerson where SuperPersonID = ?";

    private static final String SQL_SELECT_ALL_SUPER_PERSONS
            = "select SuperPerson.SuperPersonId, SuperPerson.SuperName, SuperPerson.Description from SuperPerson";

  

    private static final String SQL_SELECT_LOCATION_BY_ORGANIZATION_ID
            = "select loc.LocationID, loc.LocationName, loc.LocationDescription, loc.Latitude, "
            + "loc.Longitude from Location loc "
            + "join Organization on loc.LocationID = Organization.LocationID where "
            + "Organization.OrganizationID = ?";

    private static final String SQL_SELECT_LOCATION_BY_SIGHTING_ID
            = "select loc.LocationID, loc.LocationName, loc.LocationDescription, loc.Latitude, "
            + "loc.Longitude from Location loc "
            + "inner join Sighting sighting on loc.LocationID = sighting.LocationID where "
            + "sighting.SightingID = ?";

    private static final String SQL_SELECT_SUPER_PERSONS_BY_SIGHTING_ID
            = "select hero.SuperPersonID, hero.SuperName, hero.Description from SuperPerson hero join SuperPersonSighting sps on SightingID "
            + "where hero.SuperPersonID = sps.SuperPersonID "
            + "and sps.SightingID  =  ?;";

    private static final String SQL_SELECT_ORGANIZATIONS_BY_SUPER_PERSON_ID
            = "select org.OrganizationID, org.OrganizationName, org.LocationID, org.Description, "
            + "org.OrganizationPhone, org.OrganizationEmail from Organization org join SuperPersonOrganization spo on SuperPersonID "
            + "where org.OrganizationID = spo.OrganizationID "
            + "and spo.SuperPersonID  =  ?;";

    private static final String SQL_SELECT_POWERS_BY_SUPER_PERSON_ID
            = "select pow.PowerID, pow.PowerName, pow.PowerDescription from "
            + "Power pow join SuperPersonPower spp on SuperPersonID "
            + "where pow.PowerID = spp.PowerID "
            + "and spp.SuperPersonID  =  ?;";
    
     private static final String SQL_SELECT_HEROS_BY_LOCATION_ID
            = "select hero.SuperPersonID, hero.SuperName, hero.Description from SuperPerson hero "
            + "inner join SuperPersonSighting sps on "
            + "hero.SuperPersonID = sps.SuperPersonID "
             + "inner join Sighting sighting on sighting.SightingID = sps.SightingID "
            + "where sighting.LocationID  =  ?;";
     
     
     private static final String SQL_SELECT_LOCATIONS_HERO_HAS_BEEN_SUPER_PERSON_ID
            = "select loc.LocationID, loc.LocationName, loc.LocationDescription, loc.Latitude, "
            + "loc.Longitude from Location loc "
            + "inner join Sighting sighting on "
            + "loc.LocationID = sighting.LocationID "
             + "inner join SuperPersonSighting sps on sighting.SightingID = sps.SightingID "
            + "where sps.SuperPersonId  =  ?;";
     
       private static final String SQL_SELECT_SIGHTINGS_BY_DATE
            = "select Sighting.SightingID, Sighting.LocationID, Sighting.SightingDate "
               + "from Sighting where SightingDate = ?";
       
       private static final String SQL_SELECT_ALL_MEMBERS_BY_ORGANIZATION
            = "select hero.SuperPersonID, hero.SuperName, hero.Description from SuperPerson hero "
               + "inner join SuperPersonOrganization spo on "
               + "hero.SuperPersonID = spo.SuperPersonID "
               + "where spo.OrganizationID = ?";
       
       private static final String SQL_SELECT_ALL_HEROS_BY_POWER_ID
            = "select hero.SuperPersonID, hero.SuperName, hero.Description from SuperPerson hero "
               + "inner join SuperPersonPower spp on "
               + "hero.SuperPersonID = spp.SuperPersonID "
               + "where spp.PowerID = ?";
    
    

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSuperPerson(SuperPerson superPerson) {
        jdbcTemplate.update(SQL_INSERT_SUPER_PERSON,
                superPerson.getName(),
                superPerson.getDescription());

        int superPersonId
                = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                        Integer.class);

        superPerson.setSuperPersonId(superPersonId);
        insertSuperPersonPower(superPerson);
        insertSuperPersonOrganization(superPerson);
        
        
        
    }

    @Override
    public void deleteSuperPerson(int superPersonId) {

        jdbcTemplate.update(SQL_DELETE_SUPER_PERSON_POWER, superPersonId);
        jdbcTemplate.update(SQL_DELETE_SUPER_PERSON_ORGANIZATION, superPersonId);
        jdbcTemplate.update(SQL_DELETE_SUPER_PERSON_SIGHTING_SUPER_PERSON_ID, superPersonId);

        jdbcTemplate.update(SQL_DELETE_SUPER_PERSON, superPersonId);

    }

    @Override
    public void updateSuperPerson(SuperPerson superPerson) {
        jdbcTemplate.update(SQL_UPDATE_SUPER_PERSON,
                superPerson.getName(),
                superPerson.getDescription(),
                superPerson.getSuperPersonId());
        
        jdbcTemplate.update(SQL_DELETE_SUPER_PERSON_POWER, superPerson.getSuperPersonId());
        jdbcTemplate.update(SQL_DELETE_SUPER_PERSON_ORGANIZATION, superPerson.getSuperPersonId());

        insertSuperPersonOrganization(superPerson);
        insertSuperPersonPower(superPerson);
    }

    @Override
    public SuperPerson getSuperPersonById(int id) {
        try {
            SuperPerson hero = jdbcTemplate.queryForObject(SQL_SELECT_SUPER_PERSON,
                    new SuperPersonMapper(),
                    id);
            return hero;

        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<SuperPerson> getAllSuperPersons() {
        List<SuperPerson> superHeros = jdbcTemplate.query(SQL_SELECT_ALL_SUPER_PERSONS,
                new SuperPersonMapper());

        return superHeros;

    }

     @Override
    public List<SuperPerson> findHerosForSighting(int sightingId) {
        return jdbcTemplate.query(SQL_SELECT_SUPER_PERSONS_BY_SIGHTING_ID,
                new SuperPersonMapper(),
                sightingId);
    }
   
    //Project Functionality
    @Override
    public List<SuperPerson> getSuperPersonsByLocationId(int locationId) {
        List<SuperPerson> superHeros = jdbcTemplate.query(SQL_SELECT_HEROS_BY_LOCATION_ID,
                new SuperPersonMapper(),
                locationId);
        
      return superHeros;
    }

 
    @Override
    public List<SuperPerson> getAllSuperPersonsByOrganizationId(int organizationId) {
        List<SuperPerson> superHeros = jdbcTemplate.query(SQL_SELECT_ALL_MEMBERS_BY_ORGANIZATION,
                new SuperPersonMapper(),
                organizationId);
        
        return superHeros;
    }
    
    @Override
    public List<SuperPerson> getAllSuperPersonsByPowerId(int powerId) {
        List<SuperPerson> superHeros = jdbcTemplate.query(SQL_SELECT_ALL_HEROS_BY_POWER_ID,
                new SuperPersonMapper(),
                powerId);
        
        return superHeros;
    }
    
    private void insertSuperPersonPower(SuperPerson hero) {
        final int heroId = hero.getSuperPersonId();
        final List<Power> powers = hero.getPowers();

        for (Power currentPower : powers) {
            jdbcTemplate.update(SQL_INSERT_SUPER_PERSON_POWER,
                    heroId,
                    currentPower.getPowerId());
        }
    }
    
     private void insertSuperPersonOrganization(SuperPerson hero) {
        final int heroId = hero.getSuperPersonId();
        final List<Organization> organizations = hero.getOrganizations();

        for (Organization currentOrganization : organizations) {
            jdbcTemplate.update(SQL_INSERT_SUPER_PERSON_ORGANIZATION,
                    heroId,
                    currentOrganization.getOrganizationId());
        }
    }

  

    private static final class SuperPersonMapper implements RowMapper<SuperPerson> {

        @Override
        public SuperPerson mapRow(ResultSet rs, int i) throws SQLException {

            SuperPerson superPerson = new SuperPerson();

            superPerson.setName(rs.getString("superName"));
            superPerson.setDescription(rs.getString("description"));
            superPerson.setSuperPersonId(rs.getInt("superPersonId"));

            return superPerson;
        }
    }

}
