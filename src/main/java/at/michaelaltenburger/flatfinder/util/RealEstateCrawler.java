package at.michaelaltenburger.flatfinder.util;

import at.michaelaltenburger.flatfinder.entity.RealEstate;
import at.michaelaltenburger.flatfinder.entity.SearchConfiguration;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public abstract class RealEstateCrawler {

    protected String minRooms;
    protected String minSquareMetres;
    protected String maxSalesPrice;
    protected String maxRent;
    protected List<String> cities;

    @Autowired
    protected SeleniumUtil util;

    public void initSearchConfiguration(SearchConfiguration configuration) {
        this.minRooms = configuration.getMinRooms();
        this.minSquareMetres = configuration.getMinSquareMetres();
        this.maxSalesPrice = configuration.getMaxSalesPrice();
        this.maxRent = configuration.getMaxRent();
        this.cities = configuration.getCities();
    }

    public abstract List<RealEstate> findRealEstates();
}
