package at.michaelaltenburger.flatfinder.implementation.immoscout;

import at.michaelaltenburger.flatfinder.entity.RealEstate;
import at.michaelaltenburger.flatfinder.implementation.immoscout.page.ImmoHomePage;
import at.michaelaltenburger.flatfinder.implementation.immoscout.page.ImmoSearchPage;
import at.michaelaltenburger.flatfinder.util.RealEstateCrawler;
import at.michaelaltenburger.flatfinder.util.SeleniumUtil;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ImmoScoutCrawler extends RealEstateCrawler {
    private static final Logger log = LoggerFactory.getLogger(ImmoScoutCrawler.class);

    private ImmoHomePage homePage;

    @Autowired
    public ImmoScoutCrawler(SeleniumUtil seleniumUtil) {
        this.homePage = new ImmoHomePage(seleniumUtil);
    }

    @Override
    public List<RealEstate> findRealEstates() {
        log.info("Crawling Immobilienscout24");

        this.homePage.initElements();
        this.homePage.navigate();

        for (String city : cities) {
            this.homePage.enterCity(city);

            ImmoSearchPage searchPage = this.homePage.submit();
        }

        return Collections.emptyList();
    }
}
