package com.sg.superherosightings;

import com.sg.superherosightings.dto.Sighting;
import com.sg.superherosightings.service.SuperHeroServiceLayer;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author 
 */
@Controller
public class MainController {
    
    SuperHeroServiceLayer service;

    @Inject
    public MainController(SuperHeroServiceLayer service) {
        this.service = service;
    }
    
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String displayHomePage(Model model) {
        List<Sighting> sightingList = service.getLatestSightings();
        model.addAttribute("sightingList", sightingList);
        
        return "home";
    }
    
}
