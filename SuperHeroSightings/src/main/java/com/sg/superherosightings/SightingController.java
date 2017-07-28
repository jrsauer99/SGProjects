package com.sg.superherosightings;

import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Sighting;
import com.sg.superherosightings.dto.SuperHero;
import com.sg.superherosightings.service.ObjectDoesNotExistException;
import com.sg.superherosightings.service.SuperHeroServiceLayer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
 * @author
 */
@Controller
public class SightingController {

    private SuperHeroServiceLayer service;

    @Inject
    public SightingController(SuperHeroServiceLayer service) {
        this.service = service;
    }

    @RequestMapping(value = "/sightings", method = RequestMethod.GET)
    public String displayHomePage(Model model) {
        //NEXT ADDD ALL SIGHTINGS!
        List<Sighting> sightingList = service.getAllSightings();
        List<Location> locationList = service.getAllLocations();
        List<SuperHero> superHeroList = service.getAllSuperHeros();
        model.addAttribute("sightingList", sightingList);
        model.addAttribute("locationList", locationList);
        model.addAttribute("superHeroList", superHeroList);
        return "sightings";
    }

    //CREATE SIGHTING
    @RequestMapping(value = "/createSighting", method = RequestMethod.POST)
    public String createSighting(HttpServletRequest request, Model model) {
        //GET LOCATION
        try {
            String selectedLocationStr = request.getParameter("selectedLocation");//reques
            int selectedLocation = Integer.parseInt(selectedLocationStr);
            Location location = service.getLocationByID(selectedLocation);
            //GET DATE
            String userDateStr = request.getParameter("date");
            LocalDate ld = LocalDate.parse(userDateStr, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            //GET SUPER HEROS
            List<SuperHero> superHeros = new ArrayList<>();
            String[] selectedSuperHeros = request.getParameterValues("selectedSuperHeros[]");//request.getParameter("selectedSuperPowers[]");
            if (selectedSuperHeros != null) {
                for (String currentHeroStr : selectedSuperHeros) {
                    int superHeroID = Integer.parseInt(currentHeroStr);
                    SuperHero superHero = service.getSuperHeroByID(superHeroID);
                    superHeros.add(superHero);
                }
            }
            //CREATE NEW SIGHTING
            Sighting sighting = new Sighting();
            sighting.setLocation(location);
            sighting.setSightingDate(ld);
            sighting.setSuperHeros(superHeros);

            service.addSighting(sighting);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "There was an error parsing your ID.");
            return "errorPage";
        } catch (ObjectDoesNotExistException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "errorPage";
        }
        return "redirect:sightings";
    }

    @RequestMapping(value = "/deleteSighting", method = RequestMethod.GET)
    public String deleteSighting(HttpServletRequest request, Model model) {
        String sightingIDStr = request.getParameter("sightingID");
        try {
            int sightingID = Integer.parseInt(sightingIDStr);
            service.deleteSighting(sightingID);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "There was a problem with the number format of your Super Hero ID.");
            return "errorPage";
        }
        return "redirect:sightings";
    }

    @RequestMapping(value = "/displaySightingDetails", method = RequestMethod.GET)
    public String displaySightingDetails(HttpServletRequest request, Model model) {

        String sightingIDStr = request.getParameter("sightingID");
        try {
            int sightingID = Integer.parseInt(sightingIDStr);
            Sighting sighting = service.getSightingByID(sightingID);
            model.addAttribute("sighting", sighting);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "There was a problem with the number format of your Sighting ID.");
            return "errorPage";
        } catch (ObjectDoesNotExistException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "errorPage";
        }
        
        
        return "sightingDetails";
    }

    @RequestMapping(value = "/editSightingForm", method = RequestMethod.GET)
    public String editSightingForm(HttpServletRequest request, Model model) {
        String sightingIDStr = request.getParameter("sightingID");
        try {
            int sightingID = Integer.parseInt(sightingIDStr);
            Sighting sighting = service.getSightingByID(sightingID);
            model.addAttribute("sighting", sighting);

            //GET SUPERHEROS
            List<SuperHero> superHeroList = service.getAllSuperHeros();
            List<SuperHero> sightingHerosList = sighting.getSuperHeros();//superHero.getSuperPowers();
            List<Integer> preSelectedHeros = new ArrayList<>();
            //FIND MATCHES
            for (SuperHero currentHero : superHeroList) {
                if (sightingHerosList.contains(currentHero)) {
                    preSelectedHeros.add(1);
                } else {
                    preSelectedHeros.add(0);
                }
            }
            model.addAttribute("sightingID", sightingID);
            model.addAttribute("preSelectedHeros", preSelectedHeros);
            model.addAttribute("superHeroList", superHeroList);
            //GEt LOCATION
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
            
            
            //
            //int preselectedLocationID = location.getLocationID();
            //model.addAttribute("preselectedLocationID", preselectedLocationID);
            Location location = sighting.getLocation();
            model.addAttribute("location", location);
            model.addAttribute("locationList", locationList);

            //GET DATE
            LocalDate ld = sighting.getSightingDate();
            String sightingDate = ld.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

            model.addAttribute("sightingDate", sightingDate);

        } catch (NumberFormatException ex) {
            model.addAttribute("errorMessage", "There was an error parsing your ID.");
            return "errorPage";
        } catch (ObjectDoesNotExistException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "errorPage";
        }

        return "editSightingForm";
    }

    @RequestMapping(value = "/editSighting", method = RequestMethod.POST)
    public String editSighting(Model model, HttpServletRequest request) {
        try {
            //get sighting by id
            String sightingIDStr = request.getParameter("sightingID");
            //ADD TRY CATCH!!
            int sightingID = Integer.parseInt(sightingIDStr);
            Sighting sighting = service.getSightingByID(sightingID);

            List<SuperHero> superHeros = new ArrayList<>();
            String[] selectedSuperHeros = request.getParameterValues("selectedSuperHeros[]");
            if (selectedSuperHeros != null) {
                for (String currentHeroStr : selectedSuperHeros) {
                    int superHeroID = Integer.parseInt(currentHeroStr);
                    SuperHero superHero = service.getSuperHeroByID(superHeroID);
                    superHeros.add(superHero);
                }
                sighting.setSuperHeros(superHeros);
            }

            //LOCATION
            String selectedLocation = request.getParameter("selectedLocation");
            int selectedLocationID = Integer.parseInt(selectedLocation);
            Location location = service.getLocationByID(selectedLocationID);
            sighting.setLocation(location);

            //DATE
            //WILL CHANGE VALIDATION LATER FOR BETTER USER EXPERIENCE
            String userDateStr = request.getParameter("date");
            LocalDate ld = LocalDate.parse(userDateStr, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            sighting.setSightingDate(ld);
            model.addAttribute("sighting", sighting);
            model.addAttribute("dateError", "Incorrect Date Format");

            service.updateSighting(sighting);
            model.addAttribute("sighting", sighting);
        } catch (DateTimeParseException e) {

            model.addAttribute("errorMessage", "There was an issue with your Date.");
        } catch (NumberFormatException ex) {
            model.addAttribute("errorMessage", "There was an error parsing your ID.");
            return "errorPage";
        } catch (ObjectDoesNotExistException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "errorPage";
        }

        return "redirect:sightings";
    }

}
