package at.michaelaltenburger.flatfinder.service;

import at.michaelaltenburger.flatfinder.dao.RealEstateRepository;
import at.michaelaltenburger.flatfinder.dao.SearchConfigurationRepository;
import at.michaelaltenburger.flatfinder.entity.*;
import at.michaelaltenburger.flatfinder.util.RealEstateCrawler;
import at.michaelaltenburger.flatfinder.util.SeleniumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RealEstateService {
    private static final Logger log = LoggerFactory.getLogger(RealEstateService.class);

    private final SeleniumUtil seleniumUtil;
    private final ApplicationContext applicationContext;
    private final SearchConfigurationRepository searchConfigurationRepository;
    private final RealEstateRepository realEstateRepository;

    @Autowired
    public RealEstateService(ApplicationContext applicationContext,
                             SeleniumUtil seleniumUtil,
                             SearchConfigurationRepository searchConfigurationRepository,
                             RealEstateRepository realEstateRepository) {
        this.applicationContext = applicationContext;
        this.seleniumUtil = seleniumUtil;
        this.searchConfigurationRepository = searchConfigurationRepository;
        this.realEstateRepository = realEstateRepository;
    }

    @Async
    public void checkForNewRealEstates() {
        Optional<SearchConfiguration> searchConfiguration = searchConfigurationRepository.findById(1L);

        if(!searchConfiguration.isPresent()) {
            log.error("No search configuration found, aborting ...");
            return;
        }

        seleniumUtil.initDriver();

        try {
            List<RealEstate> realEstates = crawlWebsitesForRealEstates(searchConfiguration.get());
            List<String> ids = realEstates.stream().map(RealEstate::getId).collect(Collectors.toList());

            removeOutdatedRealEstates(ids);
            addRealEstates(realEstates);
        } catch (Exception e) {
            log.error("An exception occured", e);
        } finally {
            seleniumUtil.close();
        }
    }


    public List<RealEstate> findAll() {
        return realEstateRepository.findAll();
    }

    public List<RealEstate> findAll(RealEstateState state) {
        return realEstateRepository.findByStateIs(state);
    }

    public List<RealEstate> findAll(RealEstateState state, RealEstateType realEstateType, PurchaseType purchaseType) {
        return realEstateRepository.findByStateIsAndTypeIsAndPurchaseTypeIs(state, realEstateType, purchaseType);
    }

    public RealEstate updateState(String id, RealEstateState state) {
        Optional<RealEstate> realEstateOptional = realEstateRepository.findById(id);

        if(realEstateOptional.isPresent()) {
            RealEstate realEstate = realEstateOptional.get();
            realEstate.setState(state);

            realEstateRepository.save(realEstate);

            return realEstate;
        }

        return null;
    }

    private List<RealEstate> crawlWebsitesForRealEstates(SearchConfiguration configuration) {
        log.info("Crawling all known sites");

        List<RealEstate> realEstates = new ArrayList<>();
        Map<String, RealEstateCrawler> crawlers = applicationContext.getBeansOfType(RealEstateCrawler.class);

        for (RealEstateCrawler crawler : crawlers.values()) {
            crawler.initSearchConfiguration(configuration);
            realEstates.addAll(crawler.findRealEstates());
        }

        log.info("Finished crawling all known sites");

        return realEstates;
    }

    private void removeOutdatedRealEstates(List<String> ids) {
        log.info("Looking for outdated real estates");

        int deletedRealEstates = realEstateRepository.deleteByIdNotIn(ids);

        log.info("Deleted {} outdated real estates", deletedRealEstates);
    }

    private void addRealEstates(List<RealEstate> realEstates) {
        log.info("Inserting new real estates");
        
        List<String> realEstateIds = realEstates
                .stream()
                .map(RealEstate::getId)
                .collect(Collectors.toList());

        List<String> existingRealEstatesIds = realEstateRepository.findByIdIn(realEstateIds)
                .stream()
                .map(RealEstate::getId)
                .collect(Collectors.toList());

        realEstates = realEstates
                .stream()
                .filter(realEstate -> !existingRealEstatesIds.contains(realEstate.getId()))
                .collect(Collectors.toList());

        realEstateRepository.saveAll(realEstates);

        log.info("Inserted {} real estates", realEstates.size());
    }
}
