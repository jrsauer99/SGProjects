package com.sg.superherosightings;

import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.SuperHero;
import com.sg.superherosightings.dto.SuperPower;
import com.sg.superherosightings.service.ObjectDoesNotExistException;
import com.sg.superherosightings.service.SuperHeroServiceLayer;
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
public class SuperHeroController {

    SuperHeroServiceLayer service;

    @Inject
    public SuperHeroController(SuperHeroServiceLayer service) {
        this.service = service;
    }

    @RequestMapping(value = "/superHeros", method = RequestMethod.GET)
    public String displaySuperHeroHome(Model model) {

        List<SuperHero> superHeroList = service.getAllSuperHeros();
        List<SuperPower> superPowerList = service.getAllSuperPowers();
        List<SuperPower> superPowers = new ArrayList<>();
        SuperHero superHero = new SuperHero();
        SuperPower superPower = new SuperPower();
        superHero.setSuperPowers(superPowers);

        model.addAttribute("superHeroList", superHeroList);
        model.addAttribute("superHero", superHero); //ADDED FOR SPRING FORM
        model.addAttribute("superPower", superPower); //ADDED FOR SPRING FORM
        model.addAttribute("superPowerList", superPowerList); //ADDED FOR SUPERHERO OPTIONS BOX
        return "superHeros";
    }

    @RequestMapping(value = "/createSuperPower", method = RequestMethod.POST)
    public String createSuperPower(@Valid @ModelAttribute("superPower") SuperPower superPower, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<SuperHero> superHeroList = service.getAllSuperHeros();
            model.addAttribute("superHeroList", superHeroList);
            List<SuperPower> superPowerList = service.getAllSuperPowers();
            model.addAttribute("superPowerList", superPowerList);
            //MUT ADD SO THAT SUPER HERO SPRING FORM WORKS
            SuperHero superHero = new SuperHero();
            List<SuperPower> superPowers = new ArrayList<>();
            superHero.setSuperPowers(superPowers);
            model.addAttribute("superHero", superHero);

            return "superHeros";
        }
        service.addSuperPower(superPower);
        return "redirect:superHeros";
    }

    @RequestMapping(value = "/createSuperHero", method = RequestMethod.POST)
    public String createSuperHero(@Valid @ModelAttribute("superHero") SuperHero superHero, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            List<SuperHero> superHeroList = service.getAllSuperHeros();
            List<SuperPower> superPowerList = service.getAllSuperPowers();
            model.addAttribute("superHeroList", superHeroList);
            model.addAttribute("superPowerList", superPowerList);

            SuperPower superPower = new SuperPower();
            model.addAttribute("superPower", superPower);

            return "superHeros";
        }

        try {
            //GET THE SUPERPOWERS JUST SELECTED AND ADD TO THE MODEL!
            List<SuperPower> superPowers = new ArrayList<>();
            String[] selectedSuperPowers = request.getParameterValues("selectedSuperPowers[]");//request.getParameter("selectedSuperPowers[]");
            if (selectedSuperPowers != null) {
                for (String currentPowerStr : selectedSuperPowers) {
                    int superPowerID = Integer.parseInt(currentPowerStr);
                    SuperPower superPower = service.getSuperPowerByID(superPowerID);
                    superPowers.add(superPower);
                }
                superHero.setSuperPowers(superPowers);
            }
            service.addSuperHero(superHero);
        } catch (NumberFormatException e){
            model.addAttribute("errorMessage", "There was a problem with the number format of your Super Power ID.");
            return "errorPage";
        } catch (ObjectDoesNotExistException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "errorPage";
        }
        
        return "redirect:superHeros";
    }

    @RequestMapping(value = "/deleteSuperHero", method = RequestMethod.GET)
    public String deleteSuperHero(HttpServletRequest request, Model model) {
        String superHeroIDStr = request.getParameter("superHeroID");
        try {
            int superHeroID = Integer.parseInt(superHeroIDStr);
            service.deleteSuperHero(superHeroID);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "There was a problem with the number format of your Super Hero ID.");
            return "errorPage";
        }
        return "redirect:superHeros";
    }

    @RequestMapping(value = "/displaySuperHeroDetails", method = RequestMethod.GET)
    public String displaySuperHeroDetails(HttpServletRequest request, Model model) {

        String superHeroIDStr = request.getParameter("superHeroID");
        try {
            int superHeroID = Integer.parseInt(superHeroIDStr);
            SuperHero superHero = service.getSuperHeroByID(superHeroID);
            model.addAttribute("superHero", superHero);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "There was a problem with the number format of your Super Hero ID.");
            return "errorPage";
        } catch (ObjectDoesNotExistException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "errorPage";
        }
        return "superHeroDetails";
    }

    @RequestMapping(value = "/editSuperHeroForm", method = RequestMethod.GET)
    public String editSuperHeroForm(HttpServletRequest request, Model model) {
        String superHeroIDStr = request.getParameter("superHeroID");
        try {
            int superHeroID = Integer.parseInt(superHeroIDStr);
            SuperHero superHero = service.getSuperHeroByID(superHeroID);
            model.addAttribute("superHero", superHero);
            //ADDED TO GET SUPERPOWERS
            List<SuperPower> superPowerList = service.getAllSuperPowers();
            List<SuperPower> superHerosPowerList = service.getSuperPowersBySuperHero(superHeroID);//superHero.getSuperPowers();
            List<Integer> preSelectedPowers = new ArrayList<>();
            //FIND MATCHES
            for (SuperPower currentPower : superPowerList) {
                if (superHerosPowerList.contains(currentPower)) {
                    preSelectedPowers.add(1);
                } else {
                    preSelectedPowers.add(0);
                }
            }
            model.addAttribute("preSelectedPowers", preSelectedPowers);
            model.addAttribute("superPowerList", superPowerList);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "There was a problem with the number format of your SuperHero ID.");
            return "errorPage";
        } catch (ObjectDoesNotExistException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "errorPage";
        }

        return "editSuperHeroForm";
    }

    @RequestMapping(value = "/editSuperHero", method = RequestMethod.POST)
    public String editSuperHero(@Valid @ModelAttribute("superHero") SuperHero superHero, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            //NEED TO GET SUPER HERO SUPER POWERS ON THE MODEL
            List<SuperPower> superPowerList = service.getAllSuperPowers();
            List<SuperPower> superHerosPowerList = service.getSuperPowersBySuperHero(superHero.getSuperHeroID());
            List<Integer> preSelectedPowers = new ArrayList<>();
            //FIND MATCHES BETWEEN OVERALL LIST AND 
            for (SuperPower currentPower : superPowerList) {
                if (superHerosPowerList.contains(currentPower)) {
                    preSelectedPowers.add(1);
                } else {
                    preSelectedPowers.add(0);
                }
            }

            model.addAttribute("preSelectedPowers", preSelectedPowers);
            model.addAttribute("superPowerList", superPowerList);
           
            return "editSuperHeroForm";
        }
        
        try {
        
        List<SuperPower> superPowers = new ArrayList<>();
        String[] selectedSuperPowers = request.getParameterValues("selectedSuperPowers[]");
        if (selectedSuperPowers != null) {
            for (String currentPowerStr : selectedSuperPowers) {
                int superPowerID = Integer.parseInt(currentPowerStr);
                SuperPower superPower = service.getSuperPowerByID(superPowerID);
                superPowers.add(superPower);
            }
            superHero.setSuperPowers(superPowers);
        }
        service.updateSuperHero(superHero);
        model.addAttribute("superHero", superHero);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "There was a problem with the number format of your SuperHero ID.");
            return "errorPage";
        } catch (ObjectDoesNotExistException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "errorPage";
        }
        return "redirect:superHeros";
    }

}
