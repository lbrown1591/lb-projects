/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.controller;

import com.sg.superherosightingsapp.dto.Location;
import com.sg.superherosightingsapp.dto.Sighting;
import com.sg.superherosightingsapp.dto.SuperPerson;
import com.sg.superherosightingsapp.service.SuperHeroSightingsServiceLayer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author softwareguild
 */
@Controller
public class SightingController {

    SuperHeroSightingsServiceLayer service;

    @Inject
    public SightingController(SuperHeroSightingsServiceLayer service) {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String displayIndexPage(Model model) {
        List<Location> locationList = service.getAllLocations();
        List<SuperPerson> heroList = service.getAllSuperPersons();
        List<Sighting> sightingList = service.getAllSightings();
        sightingList.sort(Comparator.comparing(Sighting::getDateOfSighting).reversed());
        

        if (sightingList.size() > 10) {
            List<Sighting> mostRecent = new ArrayList<>();
            for(int i=0; i<10; i++){
            mostRecent.add(sightingList.get(i));
         }
            
            sightingList = mostRecent;
        }

        model.addAttribute("sightingList", sightingList);

        return "index";
    }

    @RequestMapping(value = "/displaySightingPage", method = RequestMethod.GET)
    public String displaySightingPage(Model model) {
        List<Location> locationList = service.getAllLocations();
        List<SuperPerson> heroList = service.getAllSuperPersons();
        List<Sighting> sightingList = service.getAllSightings();

        model.addAttribute("locationList", locationList);
        model.addAttribute("sightingList", sightingList);
        model.addAttribute("heroList", heroList);

        return "sightings";
    }

    @RequestMapping(value = "/createSighting", method = RequestMethod.POST)
    public String createSighting(HttpServletRequest request) {

        Sighting sighting = new Sighting();
        String date = request.getParameter("sightingDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(date, formatter);
        
        sighting.setDateOfSighting(ld);

        Location location = service.getLocationById(Integer.parseInt(request.getParameter("location")));
        sighting.setLocation(location);

        String[] stringHeroes = request.getParameterValues("heroList[]");
        List<SuperPerson> heroes = new ArrayList<>();
        for (String heroValue : stringHeroes) {
            int currentHeroValue = Integer.parseInt(heroValue);
            SuperPerson currentHero = service.getSuperPersonById(currentHeroValue);
            heroes.add(currentHero);
        }
        sighting.setHeroesSpotted(heroes);

        service.addSighting(sighting);

        return "redirect:displaySightingPage";
    }

    @RequestMapping(value = "/deleteSighting", method = RequestMethod.GET)
    public String deleteSighting(HttpServletRequest request) {
        String sightingIdParameter = request.getParameter("sightingId");
        int sightingId = Integer.parseInt(sightingIdParameter);

        service.deleteSighting(sightingId);

        return "redirect:displaySightingPage";
    }

    @RequestMapping(value = "/displayEditSightingForm", method = RequestMethod.GET)
    public String displayEditSightingForm(HttpServletRequest request, Model model) {

        String sightingIdParameter = request.getParameter("sightingId");
        int sightingId = Integer.parseInt(sightingIdParameter);
        Sighting sighting = service.getSightingById(sightingId);

        List<SuperPerson> sightingHeroes = sighting.getHeroesSpotted();
        List<Location> locationList = service.getAllLocations();
        List<SuperPerson> heroList = service.getAllSuperPersons();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate ld = sighting.getDateOfSighting();
        String date = ld.format(formatter);

        model.addAttribute("date", date);
        model.addAttribute("sighting", sighting);
        model.addAttribute("sightingHeroes", sightingHeroes);
        model.addAttribute("locationList", locationList);
        model.addAttribute("heroList", heroList);
        return "editSightingForm";
    }

    @RequestMapping(value = "/editSighting", method = RequestMethod.POST)
    public String editSighting(HttpServletRequest request) {

        Sighting sighting = new Sighting();

        int sightingId = Integer.parseInt(request.getParameter("sightingId"));
        sighting = service.getSightingById(sightingId);

        String date = request.getParameter("dateOfSighting");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(date, formatter);
        
        sighting.setDateOfSighting(ld);

        Location location = service.getLocationById(Integer.parseInt(request.getParameter("location")));
        sighting.setLocation(location);

        String[] stringHeroes = request.getParameterValues("heroList[]");
        List<SuperPerson> heroes = new ArrayList<>();
        for (String heroValue : stringHeroes) {
            int currentHeroValue = Integer.parseInt(heroValue);
            SuperPerson currentHero = service.getSuperPersonById(currentHeroValue);
            heroes.add(currentHero);
        }
        sighting.setHeroesSpotted(heroes);

        service.updateSighting(sighting);

        return "redirect:displaySightingPage";
    }
    
     @RequestMapping(value = "/displaySightingDetails", method = RequestMethod.GET)
    public String displaySightingDetails(HttpServletRequest request, Model model) {
        String sightingIdParameter = request.getParameter("sightingId");
        int sightingId = Integer.parseInt(sightingIdParameter);
        
        Sighting sighting = service.getSightingById(sightingId);
        
        List<SuperPerson> heroList = sighting.getHeroesSpotted();
        LocalDate ld = sighting.getDateOfSighting();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = ld.format(formatter);
       

        model.addAttribute("sighting", sighting);
        model.addAttribute("heroList", heroList);
        model.addAttribute("date", date);

        return "sightingDetails";
    }


}
