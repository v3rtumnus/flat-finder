package at.michaelaltenburger.flatfinder.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RealEstateCrawlingJob {
    private static final Logger log = LoggerFactory.getLogger(RealEstateCrawlingJob.class);

    private final RealEstateService realEstateService;

    @Autowired
    public RealEstateCrawlingJob(RealEstateService realEstateService) {
        this.realEstateService = realEstateService;
    }

    @Scheduled(cron = "${cron.expression.crawling}")
    public void crawlForRealEstates() {
        log.info("Starting with crawling for real estates");

        realEstateService.checkForNewRealEstates();
    }
}
