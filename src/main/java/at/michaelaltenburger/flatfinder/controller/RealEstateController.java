package at.michaelaltenburger.flatfinder.controller;

import at.michaelaltenburger.flatfinder.service.RealEstateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RealEstateController {
    private static final Logger log = LoggerFactory.getLogger(RealEstateController.class);

    private final RealEstateService realEstateService;

    @Autowired
    public RealEstateController(RealEstateService realEstateService) {
        this.realEstateService = realEstateService;
    }

    @PostMapping("/check")
    public String checkRealEstates() {
        log.info("checking for real estates");

        realEstateService.checkForNewRealEstates();
        
        return "redirect:index.html";
    }
}
