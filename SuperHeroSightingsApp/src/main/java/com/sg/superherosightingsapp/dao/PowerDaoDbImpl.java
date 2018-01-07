/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dao;

import com.sg.superherosightingsapp.dto.Power;
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
public class PowerDaoDbImpl implements PowerDao{
    
     private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_POWER
            = "insert into Power (PowerName, PowerDescription) values (?, ?)";

    private static final String SQL_DELETE_POWER
            = "delete from Power where PowerID = ?";

    private static final String SQL_UPDATE_POWER
            = "update Power set PowerName = ?, PowerDescription = ? where PowerID =  ?";

    private static final String SQL_SELECT_POWER
            = "select Power.PowerID, Power.PowerName, Power.PowerDescription from Power where PowerID = ?";

    private static final String SQL_SELECT_ALL_POWERS
            = "select Power.PowerID, Power.PowerName, Power.PowerDescription from Power";
    
      private static final String SQL_SELECT_POWERS_BY_SUPER_PERSON_ID
            = "select pow.PowerID, pow.PowerName, pow.PowerDescription from "
            + "Power pow join SuperPersonPower spp on SuperPersonID "
            + "where pow.PowerID = spp.PowerID "
            + "and spp.SuperPersonID  =  ?;";
      
       private static final String SQL_DELETE_SUPER_PERSON_POWER_ID
            = "delete from SuperPersonPower where PowerID = ?";

    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addPower(Power power) {

        jdbcTemplate.update(SQL_INSERT_POWER,
                power.getPowerName(),
                power.getPowerDescription());

        int powerId
                = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                        Integer.class);

        power.setPowerId(powerId);

    }

    @Override
    public void deletePower(int powerId) {
        jdbcTemplate.update(SQL_DELETE_SUPER_PERSON_POWER_ID, powerId);
        jdbcTemplate.update(SQL_DELETE_POWER, powerId);
    }

    @Override
    public void updatePower(Power power) {
        jdbcTemplate.update(SQL_UPDATE_POWER,
                power.getPowerName(),
                power.getPowerDescription(),
                power.getPowerId());
    }

    @Override
    public Power getPowerById(int id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_POWER,
                    new PowerMapper(),
                    id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Power> getAllPowers() {
        return jdbcTemplate.query(SQL_SELECT_ALL_POWERS,
                new PowerMapper());
    }
    
    @Override
    public List<Power> findPowersForHero(int heroId) {
        List<Power> powers = jdbcTemplate.query(SQL_SELECT_POWERS_BY_SUPER_PERSON_ID,
                new PowerMapper(),
                heroId);
        return powers;
    }
    
    private static final class PowerMapper implements RowMapper<Power> {

        @Override
        public Power mapRow(ResultSet rs, int i) throws SQLException {
            Power power = new Power();
            power.setPowerName(rs.getString("powerName"));
            power.setPowerDescription(rs.getString("powerDescription"));
            power.setPowerId(rs.getInt("powerId"));

            return power;
        }
    }

    
    
}
