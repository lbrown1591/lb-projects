/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.dto;

import java.util.Objects;

/**
 *
 * @author softwareguild
 */
public class Power {
    
    private int powerId;
    private String powerName;
    private String powerDescription;

    public int getPowerId() {
        return powerId;
    }

    public void setPowerId(int powerId) {
        this.powerId = powerId;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public String getPowerDescription() {
        return powerDescription;
    }

    public void setPowerDescription(String powerDescription) {
        this.powerDescription = powerDescription;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.powerId;
        hash = 67 * hash + Objects.hashCode(this.powerName);
        hash = 67 * hash + Objects.hashCode(this.powerDescription);
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
        final Power other = (Power) obj;
        if (this.powerId != other.powerId) {
            return false;
        }
        if (!Objects.equals(this.powerName, other.powerName)) {
            return false;
        }
        if (!Objects.equals(this.powerDescription, other.powerDescription)) {
            return false;
        }
        return true;
    }
}
