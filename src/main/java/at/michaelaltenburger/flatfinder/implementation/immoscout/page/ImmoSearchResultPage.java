package at.michaelaltenburger.flatfinder.implementation.immoscout.page;

import at.michaelaltenburger.flatfinder.entity.PurchaseType;
import at.michaelaltenburger.flatfinder.entity.RealEstate;
import at.michaelaltenburger.flatfinder.entity.RealEstateType;
import at.michaelaltenburger.flatfinder.entity.Website;
import at.michaelaltenburger.flatfinder.util.FlatFinderPage;
import at.michaelaltenburger.flatfinder.util.SeleniumUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImmoSearchResultPage extends FlatFinderPage {

    @FindBy(xpath = "//div[@class='result-item-image' and not(@id)]")
    private WebElement resultLink;

    @FindBy(xpath = "//div[@id='developer-premium-listing']/following-sibling::div[@class='result-item-info']/h2/a")
    private List<WebElement> projectDescriptions;

    public ImmoSearchResultPage(SeleniumUtil util) {
        super(util);
    }

    public List<RealEstate> getProjects(RealEstateType type, PurchaseType purchaseType, String city) {
        List<RealEstate> projects = new ArrayList<>();

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(WebElement projectDescription : projectDescriptions) {
            String id = projectDescription.getAttribute("href");
            String title = projectDescription.getText();

            projects.add(new RealEstate(id, title, type, purchaseType, Website.IMMOSCOUT24, city, true));
        }

        return projects;
    }

    public ImmoDetailPage getFirstRealEstateOnPage() {
        //sleep to wait for result link
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        resultLink.click();

        ImmoDetailPage detailPage = new ImmoDetailPage(util);
        PageFactory.initElements(util.getDriver(), detailPage);

        return detailPage;
    }
}
