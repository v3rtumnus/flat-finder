package at.michaelaltenburger.flatfinder.util;

import at.michaelaltenburger.flatfinder.entity.RealEstate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ImmoScoutCrawler extends RealEstateCrawler {
    private static final Logger log = LoggerFactory.getLogger(ImmoScoutCrawler.class);

    @Override
    public List<RealEstate> findRealEstates() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        WebDriver webDriver = new ChromeDriver();

        log.info("Crawling Immobilienscout24");

        webDriver.get("http://www.immobilienscout24.at");
        String title = webDriver.getTitle();
        
        log.info(title);

        return Collections.emptyList();
    }
}
