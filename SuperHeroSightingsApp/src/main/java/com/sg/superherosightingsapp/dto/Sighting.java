/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author softwareguild
 */
public class Sighting {
    
    private int sightingId;
    private Location location;
    private List<SuperPerson> heroesSpotted;
    private LocalDate dateOfSighting;

    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<SuperPerson> getHeroesSpotted() {
        return heroesSpotted;
    }

    public void setHeroesSpotted(List<SuperPerson> heroesSpotted) {
        this.heroesSpotted = heroesSpotted;
    }

    public LocalDate getDateOfSighting() {
        return dateOfSighting;
    }

    public void setDateOfSighting(LocalDate dateOfSighting) {
        this.dateOfSighting = dateOfSighting;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + this.sightingId;
        hash = 43 * hash + Objects.hashCode(this.location);
        hash = 43 * hash + Objects.hashCode(this.heroesSpotted);
        hash = 43 * hash + Objects.hashCode(this.dateOfSighting);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sighting other = (Sighting) obj;
        if (this.sightingId != other.sightingId) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.heroesSpotted, other.heroesSpotted)) {
            return false;
        }
        if (!Objects.equals(this.dateOfSighting, other.dateOfSighting)) {
            return false;
        }
        return true;
    }
    
}
