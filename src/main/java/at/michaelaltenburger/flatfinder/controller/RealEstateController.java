package at.michaelaltenburger.flatfinder.controller;

import at.michaelaltenburger.flatfinder.entity.RealEstate;
import at.michaelaltenburger.flatfinder.service.RealEstateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class RealEstateController {
    private static final Logger log = LoggerFactory.getLogger(RealEstateController.class);

    private final RealEstateService realEstateService;

    @Autowired
    public RealEstateController(RealEstateService realEstateService) {
        this.realEstateService = realEstateService;
    }

    @PostMapping("/")
    public ResponseEntity checkRealEstates() {
        log.info("checking for real estates");

        realEstateService.checkForNewRealEstates();
        
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public List<RealEstate> getAllRealEstates() {
        log.info("retrieving all real estates");

        return realEstateService.findAll();
    }
}
