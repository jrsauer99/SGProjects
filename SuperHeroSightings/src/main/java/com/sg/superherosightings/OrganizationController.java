package com.sg.superherosightings;

import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Organization;
import com.sg.superherosightings.dto.Sighting;
import com.sg.superherosightings.dto.SuperHero;
import com.sg.superherosightings.dto.SuperPower;
import com.sg.superherosightings.service.ObjectDoesNotExistException;
import com.sg.superherosightings.service.SuperHeroServiceLayer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
 * @author
 */
@Controller
public class OrganizationController {

    SuperHeroServiceLayer service;

    @Inject
    public OrganizationController(SuperHeroServiceLayer service) {
        this.service = service;
    }

    @RequestMapping(value = "/organizations", method = RequestMethod.GET)
    public String displayHomePage(Model model) {
        List<Location> locationList = service.getAllLocations();
        List<Organization> orgList = service.getAllOrganizations();
        List<SuperHero> superHeroList = service.getAllSuperHeros();
        Organization organization = new Organization(); //FOR SPRING FORM
        model.addAttribute("locationList", locationList);
        model.addAttribute("organization", organization);
        model.addAttribute("orgList", orgList);
        model.addAttribute("superHeroList", superHeroList);
        return "organizations";
    }

    @RequestMapping(value = "/createOrganization", method = RequestMethod.POST)
    public String createOrganization(@Valid @ModelAttribute("organization") Organization organization, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            //WAS
            List<Location> locationList = service.getAllLocations();
            List<Organization> orgList = service.getAllOrganizations();
            List<SuperHero> superHeroList = service.getAllSuperHeros();
            model.addAttribute("superHeroList", superHeroList);
            model.addAttribute("locationList", locationList);
            model.addAttribute("orgList", orgList);

            return "organizations";
        }

        try {
            //LOCATION
            String selectedLocationStr = request.getParameter("selectedLocation");//reques
            int selectedLocation = Integer.parseInt(selectedLocationStr);
            Location location = service.getLocationByID(selectedLocation);
            organization.setLocation(location);
            //MEMBERS
            List<SuperHero> superHeros = new ArrayList<>();
            String[] selectedSuperHeros = request.getParameterValues("selectedSuperHeros[]");//request.getParameter("selectedSuperPowers[]");
            if (selectedSuperHeros != null) {
                for (String currentHeroStr : selectedSuperHeros) {
                    int superHeroID = Integer.parseInt(currentHeroStr);
                    SuperHero superHero = service.getSuperHeroByID(superHeroID);
                    superHeros.add(superHero);
                }
            }
            organization.setMembers(superHeros);

            service.addOrganization(organization);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "There was a problem with the number format of your Organization ID.");
            return "errorPage";
        } catch (ObjectDoesNotExistException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "errorPage";
        }

        return "redirect:organizations";
    }

    @RequestMapping(value = "/displayOrganizationDetails", method = RequestMethod.GET)
    public String displayOrgDetails(HttpServletRequest request, Model model) {

        String organizationIDStr = request.getParameter("organizationID");
        try {
            int organizationID = Integer.parseInt(organizationIDStr);
            Organization organization = service.getOrganizationByID(organizationID);
            model.addAttribute("organization", organization);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "There was a problem with the number format of your Organization ID.");
            return "errorPage";
        } catch (ObjectDoesNotExistException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "errorPage";
        }    
        
        return "organizationDetails";
    }

    @RequestMapping(value = "/deleteOrganization", method = RequestMethod.GET)
    public String deleteOrganization(HttpServletRequest request, Model model) {
        String organizationIDStr = request.getParameter("organizationID");
        try {
            int organizationID = Integer.parseInt(organizationIDStr);
            service.deleteOrganization(organizationID);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "There was a problem with the number format of your Organization ID.");
            return "errorPage";
        }
        return "redirect:organizations";
    }

    @RequestMapping(value = "/editOrganizationForm", method = RequestMethod.GET)
    public String editOrganizationForm(HttpServletRequest request, Model model) {
        String organizationIDStr = request.getParameter("organizationID");
        try {
            int organizationID = Integer.parseInt(organizationIDStr);
            Organization organization = service.getOrganizationByID(organizationID);
            model.addAttribute("organization", organization);
            //ADDED TO GET MEMEBERS (SUPER HEROS)
            List<SuperHero> superHeroList = service.getAllSuperHeros();
            List<SuperHero> memberList = service.getAllSuperHerosByOrg(organizationID); //GET SUPER HEROS BY ORG!
            List<Integer> preSelectedHeros = new ArrayList<>();
            //FIND MATCHES
            for (SuperHero currentHero : superHeroList) {
                if (memberList.contains(currentHero)) {
                    preSelectedHeros.add(1);
                } else {
                    preSelectedHeros.add(0);
                }
            }
            //GET LOCATIONS
            List<Location> locationList = service.getAllLocations();
            String locationStrID = request.getParameter("locationID");
            //HANDLE A NULL LOCATION
            if (!locationStrID.equals("")) {
                int preselectedLocationID = Integer.parseInt(locationStrID);
                model.addAttribute("preselectedLocationID", preselectedLocationID);
            } else {
                int preselectedLocationID = 0;
                model.addAttribute("preselectedLocationID", preselectedLocationID);
            }

            model.addAttribute("locationList", locationList);
            model.addAttribute("preSelectedHeros", preSelectedHeros);
            model.addAttribute("superHeroList", superHeroList);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "There was a problem with the number format of your ID.");
            return "errorPage";
        } catch (ObjectDoesNotExistException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "errorPage";
        }

        return "editOrganizationForm";
    }

    @RequestMapping(value = "/editOrganization", method = RequestMethod.POST)
    public String editOrganization(@Valid @ModelAttribute("organization") Organization organization, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            //PUT SUPER HEROS ON THE MODEL & SELECT 
            List<SuperHero> superHeroList = service.getAllSuperHeros();

            List<SuperHero> memberList = service.getAllSuperHerosByOrg(organization.getOrganizationID());
            List<Integer> preSelectedHeros = new ArrayList<>();
            //FIND MATCHES BETWEEN OVERALL LIST AND 
            for (SuperHero currentHero : superHeroList) {
                if (memberList.contains(currentHero)) {
                    preSelectedHeros.add(1);
                } else {
                    preSelectedHeros.add(0);
                }
            }
            //PUT LOCATIONS ON THE MODEL
            List<Location> locationList = service.getAllLocations();
            Location location = service.getLocationByOrg(organization); //MAY HAVE ISSUES!!
            int preselectedLocationID = location.getLocationID();

            model.addAttribute("preselectedLocationID", preselectedLocationID);
            model.addAttribute("locationList", locationList);
            model.addAttribute("superHeroList", superHeroList);
            model.addAttribute("preSelectedHeros", preSelectedHeros);
            model.addAttribute("memberList", memberList);

            return "editOrganizationForm";
            //return "errorPage";
        }

        try {
            //LOCATION
            String selectedLocationStr = request.getParameter("selectedLocation");//reques
            int selectedLocation = Integer.parseInt(selectedLocationStr);
            Location location = service.getLocationByID(selectedLocation);
            organization.setLocation(location);
            //MEMBERS
            List<SuperHero> superHeros = new ArrayList<>();
            String[] selectedSuperHeros = request.getParameterValues("selectedSuperHeros[]");//request.getParameter("selectedSuperPowers[]");
            if (selectedSuperHeros != null) {
                for (String currentHeroStr : selectedSuperHeros) {
                    int superHeroID = Integer.parseInt(currentHeroStr);
                    SuperHero superHero = service.getSuperHeroByID(superHeroID);
                    superHeros.add(superHero);
                }
            }
            organization.setMembers(superHeros);

            service.updateOrganization(organization);
            model.addAttribute("organization", organization);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "There was a problem with the number format of your ID.");
            return "errorPage";
        } catch (ObjectDoesNotExistException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "errorPage";
        }

        return "redirect:organizations";
    }

}
