package at.michaelaltenburger.flatfinder.implementation.immoscout;

import at.michaelaltenburger.flatfinder.entity.PurchaseType;
import at.michaelaltenburger.flatfinder.entity.RealEstate;
import at.michaelaltenburger.flatfinder.entity.RealEstateType;
import at.michaelaltenburger.flatfinder.implementation.immoscout.page.ImmoDetailPage;
import at.michaelaltenburger.flatfinder.implementation.immoscout.page.ImmoHomePage;
import at.michaelaltenburger.flatfinder.implementation.immoscout.page.ImmoSearchPage;
import at.michaelaltenburger.flatfinder.implementation.immoscout.page.ImmoSearchResultPage;
import at.michaelaltenburger.flatfinder.util.RealEstateCrawler;
import at.michaelaltenburger.flatfinder.util.SeleniumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

        List<RealEstate> realEstates = new ArrayList<>();

        this.homePage.initElements();
        this.homePage.navigate();

        for (String city : cities) {
            realEstates.addAll(getRealEstates(city, RealEstateType.FLAT, PurchaseType.RENT));
            //realEstates.addAll(getRealEstates(city, RealEstateType.FLAT, PurchaseType.BUY));
            //realEstates.addAll(getRealEstates(city, RealEstateType.HOUSE, PurchaseType.RENT));
            //realEstates.addAll(getRealEstates(city, RealEstateType.HOUSE, PurchaseType.BUY));
        }

        log.info("Finished crawling Immobilienscout24");

        return realEstates;
    }

    private List<RealEstate> getRealEstates(String city, RealEstateType type, PurchaseType purchaseType) {
        ImmoSearchPage searchPage = this.homePage.submit();

        String maxPrice = purchaseType == PurchaseType.BUY ? maxSalesPrice : maxRent;

        searchPage.setCriteria(type, purchaseType, city,  minSquareMetres, minRooms, maxPrice);
        ImmoSearchResultPage searchResultPage = searchPage.submitSearch();

        List<RealEstate> realEstates = new ArrayList<>(searchResultPage.getProjects(type, purchaseType, city));

        ImmoDetailPage detailPage = searchResultPage.getFirstRealEstateOnPage();

        realEstates.add(detailPage.mapToRealEstate(type, purchaseType));

        while (detailPage.hasNext()) {
            detailPage = detailPage.goToNextRealEstate();

            realEstates.add(detailPage.mapToRealEstate(type, purchaseType));
        }

        detailPage.goToHomePage();

        return realEstates;
    }
}
