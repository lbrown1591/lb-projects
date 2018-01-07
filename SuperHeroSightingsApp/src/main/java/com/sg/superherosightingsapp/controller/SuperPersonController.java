/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.controller;

import com.sg.superherosightingsapp.dto.Organization;
import com.sg.superherosightingsapp.dto.Power;
import com.sg.superherosightingsapp.dto.SuperPerson;
import com.sg.superherosightingsapp.service.SuperHeroSightingsDataIntegrityException;
import com.sg.superherosightingsapp.service.SuperHeroSightingsServiceLayer;
import java.util.ArrayList;
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
public class SuperPersonController {

    SuperHeroSightingsServiceLayer service;

    @Inject
    public SuperPersonController(SuperHeroSightingsServiceLayer service) {
        this.service = service;
    }

    @RequestMapping(value = "/displaySuperPersonPage", method = RequestMethod.GET)
    public String displaySuperPersonPage(Model model) {
        List<Organization> organizationList = service.getAllOrganizations();
        List<Power> powerList = service.getAllPowers();
        List<SuperPerson> heroList = service.getAllSuperPersons();
        
        model.addAttribute("hero", new SuperPerson()); 

        model.addAttribute("organizationList", organizationList);
        model.addAttribute("powerList", powerList);
        model.addAttribute("heroList", heroList);

        return "superPersons";
    }

    @RequestMapping(value = "/createSuperPerson", method = RequestMethod.POST)
    public String createSuperPerson(HttpServletRequest request) {

        SuperPerson hero = new SuperPerson();
        hero.setName(request.getParameter("superPersonName"));
        String[] stringPowers = request.getParameterValues("powerList[]");
        List<Power> powers = new ArrayList<>();
        for (String powerValue : stringPowers) {
            int currentPowerValue = Integer.parseInt(powerValue);
            Power currentPower = service.getPowerById(currentPowerValue);
            powers.add(currentPower);
        }
        String[] stringOrganizations = request.getParameterValues("organizationList[]");
        List<Organization> organizations = new ArrayList<>();
        for (String organizationValue : stringOrganizations) {
            int currentOrganizationValue = Integer.parseInt(organizationValue);
            Organization currentOrganization = service.getOrganizationById(currentOrganizationValue);
            organizations.add(currentOrganization);
        }
        hero.setDescription(request.getParameter("superPersonDescription"));
        hero.setOrganizations(organizations);
        hero.setPowers(powers);

        service.addSuperPerson(hero);

        return "redirect:displaySuperPersonPage";
    }

    @RequestMapping(value = "/deleteSuperPerson", method = RequestMethod.GET)
    public String deleteSuperPerson(HttpServletRequest request) {
        String superPersonIdParameter = request.getParameter("superPersonId");
        int superPersonId = Integer.parseInt(superPersonIdParameter);
        try {
            service.deleteSuperPerson(superPersonId);
        } catch (SuperHeroSightingsDataIntegrityException e) {
        }

        return "redirect:displaySuperPersonPage";
    }

    @RequestMapping(value = "/displayEditSuperPersonForm", method = RequestMethod.GET)
    public String displayEditSuperPersonForm(HttpServletRequest request, Model model) {
        String superPersonIdParameter = request.getParameter("superPersonId");
        int superPersonId = Integer.parseInt(superPersonIdParameter);
        SuperPerson hero = service.getSuperPersonById(superPersonId);
        List<Power> heroPowers = hero.getPowers();
        List<Organization> organizationList = service.getAllOrganizations();
        List<Power> powerList = service.getAllPowers();
        List<Organization> heroOrganizations = hero.getOrganizations();
        model.addAttribute("hero", hero);
        model.addAttribute("heroPowers", heroPowers);
        model.addAttribute("heroOrganizations", heroOrganizations);
        model.addAttribute("organizationList", organizationList);
        model.addAttribute("powerList", powerList);
        return "editSuperPersonForm";
    }
    
    @RequestMapping(value = "/editSuperPerson", method = RequestMethod.POST)
    public String editSuperPerson(HttpServletRequest request) {
        
        SuperPerson hero = new SuperPerson();
        
        int superPersonId = Integer.parseInt(request.getParameter("superPersonId"));
        hero = service.getSuperPersonById(superPersonId);
        
        hero.setName(request.getParameter("name"));
        hero.setDescription(request.getParameter("description"));
        
        String[] stringPowers = request.getParameterValues("powerList[]");
        List<Power> powers = new ArrayList<>();
        for (String powerValue : stringPowers) {
            int currentPowerValue = Integer.parseInt(powerValue);
            Power currentPower = service.getPowerById(currentPowerValue);
            powers.add(currentPower);
        }
        String[] stringOrganizations = request.getParameterValues("organizationList[]");
        List<Organization> organizations = new ArrayList<>();
        for (String organizationValue : stringOrganizations) {
            int currentOrganizationValue = Integer.parseInt(organizationValue);
            Organization currentOrganization = service.getOrganizationById(currentOrganizationValue);
            organizations.add(currentOrganization);
        }
        
         hero.setPowers(powers);
         hero.setOrganizations(organizations);
        
         service.updateSuperPerson(hero);
       
        return "redirect:displaySuperPersonPage";
    }
    
    @RequestMapping(value = "/displaySuperPersonDetails", method = RequestMethod.GET)
    public String displaySuperPersonDetails(HttpServletRequest request, Model model) {
        String superPersonIdParameter = request.getParameter("superPersonId");
        int superPersonId = Integer.parseInt(superPersonIdParameter);
        
        SuperPerson hero = service.getSuperPersonById(superPersonId);

        List<Power> powerList = hero.getPowers();
        List<Organization> organizationList = hero.getOrganizations();

        model.addAttribute("hero", hero);
        model.addAttribute("powerList", powerList);
        model.addAttribute("organizationList", organizationList);

        return "superPersonDetails";
    }

}
