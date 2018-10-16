package at.michaelaltenburger.flatfinder.util;

import at.michaelaltenburger.flatfinder.entity.RealEstate;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class RealEstateCrawler {

    @Value("${criteria.minRooms}")
    Integer minRooms;

    @Value("${criteria.minSquareMetres}")
    Integer minSquareMetres;

    @Value("${criteria.maxSalesPrice}")
    Long maxSalesPrice;

    @Value("${criteria.maxRent}")
    Long maxRent;

    @Value("${criteria.cities}")
    List<String> cities;

    @Value("${chromedriver.path}")
    String chromeDriverPath;

    public abstract List<RealEstate> findRealEstates();
}
