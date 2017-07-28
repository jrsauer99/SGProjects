package com.sg.superherosightings;

import com.sg.superherosightings.dto.Coordinate;
import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.service.ObjectDoesNotExistException;
import com.sg.superherosightings.service.SuperHeroServiceLayer;
import java.math.BigDecimal;
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
public class LocationController {

    SuperHeroServiceLayer service;

    @Inject
    public LocationController(SuperHeroServiceLayer service) {
        this.service = service;
    }

    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    public String displayLocationHome(Model model) {
        //GET ALL LOCATIONS
        List<Location> locationList = service.getAllLocations();
        Location location = new Location();
        Coordinate coordinate = new Coordinate();
        location.setCoordinate(coordinate);
        model.addAttribute("locationList", locationList);
        model.addAttribute("location", location); //ADDED FOR SPRING FORM

        return "locations";
    }

    @RequestMapping(value = "/createLocation", method = RequestMethod.POST)
    public String createLocation(@Valid @ModelAttribute("location") Location location, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Location> locationList = service.getAllLocations();
            model.addAttribute("locationList", locationList);
            return "locations";
        }

        try {
            service.addLocation(location, (location.getCoordinate().getLatitude()), (location.getCoordinate().getLongitude()));
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "There was a problem with the number format of your Coordinate.");
            return "errorPage";
        }

        return "redirect:locations";
    }

    @RequestMapping(value = "/displayLocationDetails", method = RequestMethod.GET)
    public String displayLocationDetails(HttpServletRequest request, Model model) {

        String locationIDStr = request.getParameter("locationID");
        String coordinateIDStr = request.getParameter("coordinateID");
        try {
            int locationID = Integer.parseInt(locationIDStr);
            int coordinateID = Integer.parseInt(coordinateIDStr);
            Location location = service.getLocationByID(locationID);
            Coordinate coordinate = service.getCoordinateByID(coordinateID);
            model.addAttribute("location", location);
            model.addAttribute("coordinate", coordinate);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "There was a problem with the number format of your Location or Coordinate ID.");
            return "errorPage";
        } catch (ObjectDoesNotExistException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "errorPage";
        }

        return "locationDetails";
    }

    @RequestMapping(value = "/editLocationForm", method = RequestMethod.GET)
    public String editLocationForm(HttpServletRequest request, Model model) {
        String locationIDStr = request.getParameter("locationID");
        //ADD VALIDATION HERE?
        try {
            int locationID = Integer.parseInt(locationIDStr);
            //ADD TRY CATCH HERE IN CASE DOESNT EXIST?
            Location location = service.getLocationByID(locationID);
            model.addAttribute("location", location);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "There was a problem with the number format of your Location ID.");
            return "errorPage";
        } catch (ObjectDoesNotExistException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "errorPage";
        }
        
        
        return "editLocationForm";
    }

    @RequestMapping(value = "/editLocation", method = RequestMethod.POST)
    public String editLocation(@Valid @ModelAttribute("location") Location location, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "editLocationForm";
        }

        try {
            service.updateLocation(location);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "There was a problem with the number format of your Coordinate.");
            return "errorPage";
        }

        return "redirect:locations";
    }

    @RequestMapping(value = "/deleteLocation", method = RequestMethod.GET)
    public String deleteLocation(HttpServletRequest request, Model model) {
        String locationIDStr = request.getParameter("locationID");
        try {
        int locationID = Integer.parseInt(locationIDStr);
        service.deleteLocation(locationID);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "There was a problem with the number format of your Location ID.");
            return "errorPage";
        }
        return "redirect:locations";
    }
}
