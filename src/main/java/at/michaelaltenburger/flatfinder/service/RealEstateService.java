package at.michaelaltenburger.flatfinder.service;

import at.michaelaltenburger.flatfinder.util.RealEstateCrawler;
import at.michaelaltenburger.flatfinder.util.SeleniumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RealEstateService {

    private SeleniumUtil seleniumUtil;

    public final ApplicationContext applicationContext;

    @Autowired
    public RealEstateService(ApplicationContext applicationContext,
                             SeleniumUtil seleniumUtil) {
        this.applicationContext = applicationContext;
        this.seleniumUtil = seleniumUtil;
    }

    public void checkForNewRealEstates() {
        seleniumUtil.initDriver();

        Map<String, RealEstateCrawler> crawlers = applicationContext.getBeansOfType(RealEstateCrawler.class);

        for (RealEstateCrawler crawler : crawlers.values()) {
            crawler.findRealEstates();
        }
    }
}
