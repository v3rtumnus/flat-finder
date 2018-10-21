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
    protected Integer minRooms;

    @Value("${criteria.minSquareMetres}")
    protected Integer minSquareMetres;

    @Value("${criteria.maxSalesPrice}")
    protected Long maxSalesPrice;

    @Value("${criteria.maxRent}")
    protected Long maxRent;

    @Value("${criteria.cities}")
    protected List<String> cities;

    public abstract List<RealEstate> findRealEstates();
}
