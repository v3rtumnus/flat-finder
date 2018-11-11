package at.michaelaltenburger.flatfinder.controller;

import at.michaelaltenburger.flatfinder.entity.PurchaseType;
import at.michaelaltenburger.flatfinder.entity.RealEstate;
import at.michaelaltenburger.flatfinder.entity.RealEstateState;
import at.michaelaltenburger.flatfinder.entity.RealEstateType;
import at.michaelaltenburger.flatfinder.service.RealEstateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
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

    @GetMapping("/{state}")
    public List<RealEstate> getAllRealEstates(@PathVariable("state") String state) {
        RealEstateState realEstateState = RealEstateState.valueOf(state.toUpperCase());

        log.info("retrieving real estates in state {}", realEstateState);

        return realEstateService.findAll(realEstateState);
    }

    @GetMapping("/{state}{realEstateType}/{}")
    public List<RealEstate> getAllRealEstates(@PathVariable("state") RealEstateState state,
                                              @PathVariable("realEstateType") RealEstateType realEstateType,
                                              @PathVariable("purchaseType") PurchaseType purchaseType) {
        log.info("retrieving all real estates");

        return realEstateService.findAll(state, realEstateType, purchaseType);
    }
}
