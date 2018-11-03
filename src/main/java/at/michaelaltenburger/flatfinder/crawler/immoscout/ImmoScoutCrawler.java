package at.michaelaltenburger.flatfinder.crawler.immoscout;

import at.michaelaltenburger.flatfinder.crawler.immoscout.page.ImmoSearchPage;
import at.michaelaltenburger.flatfinder.crawler.immoscout.page.ImmoSearchResultPage;
import at.michaelaltenburger.flatfinder.entity.PurchaseType;
import at.michaelaltenburger.flatfinder.entity.RealEstate;
import at.michaelaltenburger.flatfinder.entity.RealEstateType;
import at.michaelaltenburger.flatfinder.crawler.immoscout.page.ImmoDetailPage;
import at.michaelaltenburger.flatfinder.crawler.immoscout.page.ImmoHomePage;
import at.michaelaltenburger.flatfinder.util.RealEstateCrawler;
import at.michaelaltenburger.flatfinder.util.SeleniumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        List<RealEstate> realEstates = new ArrayList<>();

        for (String city : cities) {
            log.info("Crawling for flats to rent in {}", city);
            realEstates.addAll(getRealEstates(city, RealEstateType.FLAT, PurchaseType.RENT));

            log.info("Crawling for flats to buy in {}", city);
            realEstates.addAll(getRealEstates(city, RealEstateType.FLAT, PurchaseType.BUY));

            log.info("Crawling for houses to rent in {}", city);
            realEstates.addAll(getRealEstates(city, RealEstateType.HOUSE, PurchaseType.RENT));

            log.info("Crawling for houses to buy in {}", city);
            realEstates.addAll(getRealEstates(city, RealEstateType.HOUSE, PurchaseType.BUY));
        }

        log.info("Finished crawling Immobilienscout24");

        return realEstates;
    }

    private List<RealEstate> getRealEstates(String city, RealEstateType type, PurchaseType purchaseType) {
        this.homePage.initElements();
        this.homePage.navigate();

        ImmoSearchPage searchPage = this.homePage.submit();

        String maxPrice = purchaseType == PurchaseType.BUY ? maxSalesPrice : maxRent;

        searchPage.setCriteria(type, purchaseType, city,  minSquareMetres, minRooms, maxPrice);
        ImmoSearchResultPage searchResultPage = searchPage.submitSearch();

        List<RealEstate> realEstates = new ArrayList<>(searchResultPage.getProjects(type, purchaseType, city));

        Optional<ImmoDetailPage> detailPageOptional = searchResultPage.getFirstRealEstateOnPage();

        if(detailPageOptional.isPresent()) {
            ImmoDetailPage detailPage = detailPageOptional.get();

            try {
                realEstates.add(detailPage.mapToRealEstate(type, purchaseType));
            } catch(Exception e) {
                log.error("Exception during mapping of real estate", e);
            }

            while (detailPage.hasNext()) {
                detailPage = detailPage.goToNextRealEstate();

                try {
                    realEstates.add(detailPage.mapToRealEstate(type, purchaseType));
                } catch(Exception e) {
                    log.error("Exception during mapping of real estate", e);
                }
            }
        }

        return realEstates;
    }
}
