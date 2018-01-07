/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dao;

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
public class SightingDaoDbImpl implements SightingDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_SIGHTING
            = "insert into Sighting (LocationID, SightingDate) values (?, ?)";

    private static final String SQL_DELETE_SIGHTING
            = "delete from Sighting where SightingID = ?";

    private static final String SQL_DELETE_SIGHTINGS_BY_LOCATION_ID
            = "delete from Sighting where LocationID = ?";
    
    private static final String SQL_DELETE_SUPER_PERSON_SIGHTING
            = "delete from SuperPersonSighting where SuperPersonSighting.SightingID = ?";

    private static final String SQL_UPDATE_SIGHTING
            = "update Sighting set LocationID = ?, SightingDate = ? where SightingID =  ?";

    private static final String SQL_SELECT_SIGHTING
            = "select Sighting.SightingID, Sighting.LocationID, Sighting.SightingDate from Sighting where SightingID = ?";

    private static final String SQL_SELECT_ALL_SIGHTINGS
            = "select Sighting.SightingID, Sighting.LocationID, Sighting.SightingDate from Sighting";

    private static final String SQL_SELECT_SIGHTINGS_BY_DATE
            = "select Sighting.SightingID, Sighting.LocationID, Sighting.SightingDate "
               + "from Sighting where SightingDate = ?";
    
       private static final String SQL_INSERT_SUPER_PERSON_SIGHTING
            = "insert into SuperPersonSighting (SuperPersonID, SightingID) values(?, ?)";
       
       private static final String SQL_SELECT_SIGHTINGS_BY_SUPER_PERSON_ID
            = "select sighting.SightingID, sighting.LocationID, sighting.SightingDate "
            + "from Sighting sighting inner join SuperPersonSighting sps where "
            + "sighting.SightingID = sps.SightingID "
            + "and sps.SuperPersonID  =  ?;";
       
        private static final String SQL_SELECT_SIGHTINGS_BY_LOCATION_ID
            = "select Sighting.SightingID, Sighting.LocationID, Sighting.SightingDate "
               + "from Sighting where Sighting.LocationID = ?";

      
    
   @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSighting(Sighting sighting) {
        jdbcTemplate.update(SQL_INSERT_SIGHTING,
                sighting.getLocation().getLocationId(),
                java.sql.Date.valueOf(sighting.getDateOfSighting()));

        int sightingId
                = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                        Integer.class);

        sighting.setSightingId(sightingId);
        insertSuperPersonSighting(sighting);

    }

    @Override
    public void deleteSighting(int sightingId) {
        jdbcTemplate.update(SQL_DELETE_SUPER_PERSON_SIGHTING, sightingId);
        jdbcTemplate.update(SQL_DELETE_SIGHTING, sightingId);
    }

    @Override
    public void updateSighting(Sighting sighting) {
        jdbcTemplate.update(SQL_UPDATE_SIGHTING,
                sighting.getLocation().getLocationId(),
                java.sql.Date.valueOf(sighting.getDateOfSighting()),
                sighting.getSightingId());
        jdbcTemplate.update(SQL_DELETE_SUPER_PERSON_SIGHTING, sighting.getSightingId());
        insertSuperPersonSighting(sighting);

    }

    @Override
    public Sighting getSightingById(int id) {
        try {
            Sighting sighting = jdbcTemplate.queryForObject(SQL_SELECT_SIGHTING,
                    new SightingMapper(),
                    id);
           
            return sighting;

        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        return jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS,
                new SightingMapper());
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate ld) {
           List<Sighting> sightings = jdbcTemplate.query(SQL_SELECT_SIGHTINGS_BY_DATE,
                new SightingMapper(),
                 java.sql.Date.valueOf(ld));
           return sightings;
    }
    
 
    @Override
    public List<Sighting> getAllSightingsBySuperPersonId(int superPersonId) {
         List<Sighting> sightings =  jdbcTemplate.query(SQL_SELECT_SIGHTINGS_BY_SUPER_PERSON_ID,
                new SightingMapper(),
                superPersonId);
         
         return sightings;
    }
    
     @Override
    public List<Sighting> getAllSightingsByLocationId(int locationId) {
         List<Sighting> sightings =  jdbcTemplate.query(SQL_SELECT_SIGHTINGS_BY_LOCATION_ID,
                new SightingMapper(),
                locationId);
         
         return sightings;
    }
    
     private void insertSuperPersonSighting(Sighting sighting) {
        final int sightingId = sighting.getSightingId();
        final List<SuperPerson> heros = sighting.getHeroesSpotted();

        for (SuperPerson currentHero : heros) {
            jdbcTemplate.update(SQL_INSERT_SUPER_PERSON_SIGHTING,
                    currentHero.getSuperPersonId(),
                    sightingId);
        }
    }

    private static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {

            Sighting sighting = new Sighting();

            sighting.setDateOfSighting(rs.getDate("SightingDate").toLocalDate());
            sighting.setSightingId(rs.getInt("SightingID"));

            return sighting;
        }
    }

}
