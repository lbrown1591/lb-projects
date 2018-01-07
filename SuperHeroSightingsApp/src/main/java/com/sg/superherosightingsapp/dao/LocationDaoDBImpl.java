/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dao;

import com.sg.superherosightingsapp.dto.Location;
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
public class LocationDaoDBImpl implements LocationDao {

    private JdbcTemplate jdbcTemplate;
    SightingDao sightingDao;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public void setSightingDao(SightingDao sightingDao) {
        this.sightingDao = sightingDao;
    }
    
    private static final String SQL_INSERT_LOCATION
            = "insert into Location (LocationName, LocationDescription, Latitude, "
            + "Longitude) values (?, ?, ?, ?)";

    private static final String SQL_DELETE_LOCATION
            = "delete from Location where LocationID = ?";

    private static final String SQL_UPDATE_LOCATION
            = "update Location set LocationName = ?, LocationDescription = ?, "
            + "Latitude = ?, Longitude = ? where LocationID =  ?";

    private static final String SQL_SELECT_LOCATION
            = "select Location.LocationId, Location.LocationName, Location.LocationDescription, Location.Latitude, "
            + "Location.Longitude from Location where LocationID = ?";

    private static final String SQL_SELECT_ALL_LOCATIONS
            = "select Location.LocationId, Location.LocationName, Location.LocationDescription, Location.Latitude, "
            + "Location.Longitude from Location";

    private static final String SQL_DELETE_SIGHTINGS_BY_LOCATION_ID
            = "delete from Sighting where LocationID = ?";

    private static final String SQL_DELETE_ORGANIZATIONS_BY_LOCATION_ID
            = "delete from Organization where LocationID = ?";

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

    private static final String SQL_SELECT_LOCATIONS_HERO_HAS_BEEN_SUPER_PERSON_ID
            = "select loc.LocationID, loc.LocationName, loc.LocationDescription, loc.Latitude, "
            + "loc.Longitude from Location loc "
            + "inner join Sighting sighting on "
            + "loc.LocationID = sighting.LocationID "
            + "inner join SuperPersonSighting sps on sighting.SightingID = sps.SightingID "
            + "where sps.SuperPersonId  =  ?;";
    
   

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addLocation(Location location) {
        jdbcTemplate.update(SQL_INSERT_LOCATION,
                location.getLocationName(),
                location.getDescription(),
                location.getLatitude(),
                location.getLongitude());

        int locationId
                = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                        Integer.class);

        location.setLocationId(locationId);
    }

    @Override
    public void deleteLocation(int locationId) {

        jdbcTemplate.update(SQL_DELETE_LOCATION, locationId);
           

    }

    @Override
    public void updateLocation(Location location) {
        jdbcTemplate.update(SQL_UPDATE_LOCATION,
                location.getLocationName(),
                location.getDescription(),
                location.getLatitude(),
                location.getLongitude(),
                location.getLocationId());
    }

    @Override
    public Location getLocationById(int id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION,
                    new LocationMapper(),
                    id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        return jdbcTemplate.query(SQL_SELECT_ALL_LOCATIONS,
                new LocationMapper());
    }

    @Override
    public Location findLocationForOrganization(int organizationId) {
        Location location = jdbcTemplate.queryForObject(SQL_SELECT_LOCATION_BY_ORGANIZATION_ID,
                new LocationMapper(),
                organizationId);

        return location;
    }

    @Override
    public Location findLocationForSighting(int sightingId) {
        Location location = jdbcTemplate.queryForObject(SQL_SELECT_LOCATION_BY_SIGHTING_ID,
                new LocationMapper(),
                sightingId);
        return location;
    }

    @Override
    public List<Location> getLocationsBySuperPersonId(int superPersonId) {
        List<Location> locations = jdbcTemplate.query(SQL_SELECT_LOCATIONS_HERO_HAS_BEEN_SUPER_PERSON_ID,
                new LocationMapper(),
                superPersonId);
        return locations;
    }

    private static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location location = new Location();
            location.setLocationName(rs.getString("locationName"));
            location.setDescription(rs.getString("locationDescription"));
            location.setLatitude(rs.getDouble("latitude"));
            location.setLongitude(rs.getDouble("longitude"));
            location.setLocationId(rs.getInt("locationId"));

            return location;
        }
    }
}
