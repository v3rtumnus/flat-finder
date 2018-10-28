package at.michaelaltenburger.flatfinder.implementation.immoscout.page;

import at.michaelaltenburger.flatfinder.entity.PurchaseType;
import at.michaelaltenburger.flatfinder.entity.RealEstate;
import at.michaelaltenburger.flatfinder.entity.RealEstateType;
import at.michaelaltenburger.flatfinder.entity.Website;
import at.michaelaltenburger.flatfinder.util.FlatFinderPage;
import at.michaelaltenburger.flatfinder.util.SeleniumUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.net.URL;
import java.util.regex.Pattern;

public class ImmoDetailPage extends FlatFinderPage {

    @FindBy(xpath = "//span[text()='Nächste Immobilie']")
    private WebElement nextLink;

    @FindBy(tagName = "h1")
    private WebElement heading;

    @FindBy(xpath = "//span[@class='postalcode']")
    private WebElement postalCodeField;

    @FindBy(xpath = "//span[@class='city']")
    private WebElement cityField;

    @FindBy(xpath = "//div[text()='Fläche']/following-sibling::div")
    private WebElement squareMetresField;

    @FindBy(xpath = "//div[text()='Zimmer']/following-sibling::div")
    private WebElement roomsField;

    @FindBy(xpath = "//div[contains(@class,'keyfact-detail') and contains(@class,'price')]/div/div[@class='value']")
    private WebElement priceField;

    @FindBy(xpath = "//div[@id='at-expose-image']/img")
    private WebElement imageField;

    @FindBy(xpath = "//div[@class='at-header-logo']/a")
    private WebElement headerLogo;

    public ImmoDetailPage(SeleniumUtil util) {
        super(util);
    }

    public ImmoDetailPage goToNextRealEstate() {
        nextLink.click();

        ImmoDetailPage detailPage = new ImmoDetailPage(util);
        PageFactory.initElements(util.getDriver(), detailPage);

        return detailPage;
    }

    public RealEstate mapToRealEstate(RealEstateType type, PurchaseType purchaseType) {
        String id = Website.IMMOSCOUT24.name() + "-" + util.getDriver().getCurrentUrl().substring(
                util.getDriver().getCurrentUrl().lastIndexOf("/") + 1);

        String headingText = heading.getText();
        Double squareMetres = Double.valueOf(reformatNumber(squareMetresField.getText().split(" ")[0]));
        Double rooms = Double.valueOf(reformatNumber(roomsField.getText()));

        String city= cityField.getText();
        String postalCode = postalCodeField.getText();
        Double price = Double.valueOf(reformatNumber(priceField.getText()));

        String imageSource = imageField.getAttribute("src");

        return new RealEstate(id, headingText, squareMetres, rooms, type, Website.IMMOSCOUT24, city, postalCode, price, purchaseType,
                util.getDriver().getCurrentUrl(), imageSource, false);
    }

    public boolean hasNext() {
        return nextLink.isDisplayed();
    }

    public ImmoHomePage goToHomePage() {
        headerLogo.click();

        ImmoHomePage homePage = new ImmoHomePage(util);
        PageFactory.initElements(util.getDriver(), homePage);

        return homePage;
    }

    private String reformatNumber(String number) {
        return number.replace(".", "").replace(",", ".");
    }
}
