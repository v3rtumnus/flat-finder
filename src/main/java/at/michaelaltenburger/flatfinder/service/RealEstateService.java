package at.michaelaltenburger.flatfinder.service;

import at.michaelaltenburger.flatfinder.util.RealEstateCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RealEstateService {

    public final ApplicationContext applicationContext;

    @Autowired
    public RealEstateService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void checkForNewRealEstates() {
        Map<String, RealEstateCrawler> crawlers = applicationContext.getBeansOfType(RealEstateCrawler.class);

        for (RealEstateCrawler crawler : crawlers.values()) {
            crawler.findRealEstates();
        }
    }
}
