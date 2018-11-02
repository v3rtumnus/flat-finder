package at.michaelaltenburger.flatfinder.service;

import at.michaelaltenburger.flatfinder.dao.RealEstateRepository;
import at.michaelaltenburger.flatfinder.entity.RealEstate;
import at.michaelaltenburger.flatfinder.entity.RealEstateState;
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
import java.util.stream.Collectors;

@Service
public class RealEstateService {
    private static final Logger log = LoggerFactory.getLogger(RealEstateService.class);

    private final SeleniumUtil seleniumUtil;
    private final ApplicationContext applicationContext;
    private final RealEstateRepository realEstateRepository;

    @Autowired
    public RealEstateService(ApplicationContext applicationContext,
                             SeleniumUtil seleniumUtil,
                             RealEstateRepository realEstateRepository) {
        this.applicationContext = applicationContext;
        this.seleniumUtil = seleniumUtil;
        this.realEstateRepository = realEstateRepository;
    }

    @Async
    public void checkForNewRealEstates() {
        seleniumUtil.initDriver();

        List<RealEstate> realEstates = crawlWebsitesForRealEstates();
        List<String> ids = realEstates.stream().map(RealEstate::getId).collect(Collectors.toList());

        seleniumUtil.close();

        removeOutdatedRealEstates(ids);
        addRealEstates(realEstates);
    }


    public List<RealEstate> findAll() {
        return realEstateRepository.findAll();
    }

    public List<RealEstate> findSavedRealEstates() {
        return realEstateRepository.findByStateIs(RealEstateState.SAVED);
    }

    public List<RealEstate> findFavoriteRealEstates() {
        return realEstateRepository.findByStateIs(RealEstateState.FAVORITE);
    }

    public List<RealEstate> findArchivedRealEstates() {
        return realEstateRepository.findByStateIs(RealEstateState.ARCHIVED);
    }

    private List<RealEstate> crawlWebsitesForRealEstates() {
        log.info("Crawling all known sites");

        List<RealEstate> realEstates = new ArrayList<>();
        Map<String, RealEstateCrawler> crawlers = applicationContext.getBeansOfType(RealEstateCrawler.class);

        for (RealEstateCrawler crawler : crawlers.values()) {
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

        List<RealEstate> existingRealEstates = realEstateRepository.findByIdIn(realEstateIds);

        realEstates.removeAll(existingRealEstates);

        realEstateRepository.saveAll(realEstates);

        log.info("Inserted {} real estates", realEstates.size());
    }
}
