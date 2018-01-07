/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.controller;

import com.sg.superherosightingsapp.dto.Location;
import com.sg.superherosightingsapp.service.SuperHeroSightingsDataIntegrityException;
import com.sg.superherosightingsapp.service.SuperHeroSightingsServiceLayer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author softwareguild
 */
@Controller
public class LocationController {

    SuperHeroSightingsServiceLayer service;

    @Inject
    public LocationController(SuperHeroSightingsServiceLayer service) {
        this.service = service;
    }

    @RequestMapping(value = "/displayLocationPage", method = RequestMethod.GET)
    public String displayLocationPage(Model model) {
        List<Location> locationList = service.getAllLocations();
        boolean error = service.checkForError();
        service.resetError();

        model.addAttribute("locationList", locationList);
        model.addAttribute("location", new Location());
        model.addAttribute("error", error);

        return "locations";
    }

    @RequestMapping(value = "/createLocation", method = RequestMethod.POST)
    public String createLocation(@Valid @ModelAttribute Location location, BindingResult result) {

        /*
        location.setLocationName(request.getParameter("locationName"));
        location.setDescription(request.getParameter("locationDescription"));
        double latitude = Double.parseDouble(request.getParameter("latitude"));
        location.setLatitude(latitude);
        double longitude = Double.parseDouble(request.getParameter("longitude"));
        location.setLongitude(longitude);
         */
        if (result.hasErrors()) {
            return "locations";
        }

        service.addLocation(location);

        return "redirect:displayLocationPage";
    }

    @RequestMapping(value = "/displayLocationDetails", method = RequestMethod.GET)
    public String displayLocationDetails(HttpServletRequest request, Model model) {
        String locationIdParameter = request.getParameter("locationId");
        int locationId = Integer.parseInt(locationIdParameter);

        Location location = service.getLocationById(locationId);

        model.addAttribute("location", location);

        return "locationDetails";
    }

    @RequestMapping(value = "/deleteLocation", method = RequestMethod.GET)
    public String deleteLocation(HttpServletRequest request) {
        String locationIdParameter = request.getParameter("locationId");
        int locationId = Integer.parseInt(locationIdParameter);
        try {
            service.deleteLocation(locationId);
        } catch (SuperHeroSightingsDataIntegrityException e) {
        }
        return "redirect:displayLocationPage";
    }

    @RequestMapping(value = "/displayEditLocationForm", method = RequestMethod.GET)
    public String displayEditLocationForm(HttpServletRequest request, Model model) {

        String locationIdParameter = request.getParameter("locationId");
        int locationId = Integer.parseInt(locationIdParameter);
        Location location = service.getLocationById(locationId);
        model.addAttribute("location", location);

        return "editLocationForm";
    }

    @RequestMapping(value = "/editLocation", method = RequestMethod.POST)
    public String editLocation(@Valid @ModelAttribute("location") Location location, BindingResult result) {

        if (result.hasErrors()) {
            return "editLocationForm";
        }

        service.updateLocation(location);

        return "redirect:displayLocationPage";
    }
}
