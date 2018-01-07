/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightingsapp.controller;

import com.sg.superherosightingsapp.dto.Location;
import com.sg.superherosightingsapp.dto.Organization;
import com.sg.superherosightingsapp.service.SuperHeroSightingsDataIntegrityException;
import com.sg.superherosightingsapp.service.SuperHeroSightingsServiceLayer;
import java.util.List;
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
public class OrganizationController {
    
     SuperHeroSightingsServiceLayer service;

    @Inject
    public OrganizationController(SuperHeroSightingsServiceLayer service) {
        this.service = service;
    }

    @RequestMapping(value = "/displayOrganizationPage", method = RequestMethod.GET)
    public String displayOrganizationPage(Model model) {
       List<Organization> organizationList = service.getAllOrganizations();
       boolean error = service.checkForError();
       service.resetError();
       
       List<Location> locationList = service.getAllLocations();
        model.addAttribute("organizationList", organizationList);
        model.addAttribute("locationList", locationList);
        model.addAttribute("organization", new Organization());
        model.addAttribute("error", error);
        
        return "organizations";
    }
    
    
    @RequestMapping(value = "/createOrganization", method = RequestMethod.POST)
    public String createOrganization(HttpServletRequest request) {

         Organization organization = new Organization();
        organization.setOrganizationName(request.getParameter("organizationName"));
        organization.setOrganizationDescription(request.getParameter("organizationDescription"));
        organization.setPhone(request.getParameter("phone"));
        organization.setEmail(request.getParameter("email"));
        String locationId = request.getParameter("location");
        int id = Integer.parseInt(locationId);
        Location location = service.getLocationById(id);
        organization.setLocation(location);
        
        
        service.addOrganization(organization);

        return "redirect:displayOrganizationPage";
    }
    
    @RequestMapping(value = "/deleteOrganization", method = RequestMethod.GET)
    public String deleteOrganization(HttpServletRequest request) {
        String organizationIdParameter = request.getParameter("organizationId");
        int organizationId = Integer.parseInt(organizationIdParameter);
        try {
            service.deleteOrganization(organizationId);
        } catch (SuperHeroSightingsDataIntegrityException e) {
        }
        return "redirect:displayOrganizationPage";
    }
    
    
    @RequestMapping(value = "/displayEditOrganizationForm", method = RequestMethod.GET)
    public String displayEditOrganizationForm(HttpServletRequest request, Model model) {
        String organizationIdParameter = request.getParameter("organizationId");
        int organizationId = Integer.parseInt(organizationIdParameter);
        Organization organization = service.getOrganizationById(organizationId);
        List<Location> locationList = service.getAllLocations();
        model.addAttribute("organization", organization);
        model.addAttribute("locationList", locationList);
        
        return "editOrganizationForm";
    }
    
     @RequestMapping(value = "/editOrganization", method = RequestMethod.POST)
    public String editOrganization(HttpServletRequest request) {
        
        Organization organization = new Organization();
        int organizationId = Integer.parseInt(request.getParameter("organizationId"));
        organization = service.getOrganizationById(organizationId);
        
        organization.setOrganizationName(request.getParameter("organizationName"));
        organization.setOrganizationDescription(request.getParameter("organizationDescription"));
        organization.setPhone(request.getParameter("phone"));
        organization.setEmail(request.getParameter("email"));
        int locationId = Integer.parseInt(request.getParameter("locationList"));  
        Location location = service.getLocationById(locationId);
        organization.setLocation(location);
        service.updateOrganization(organization);

        return "redirect:displayOrganizationPage";
    }
    
    @RequestMapping(value = "/displayOrganizationDetails", method = RequestMethod.GET)
    public String displayOrganizationDetails(HttpServletRequest request, Model model) {
        String organizationIdParameter = request.getParameter("organizationId");
        int organizationId = Integer.parseInt(organizationIdParameter);

        Organization organization = service.getOrganizationById(organizationId);

        model.addAttribute("organization", organization);

        return "organizationDetails";
    }

    
}
