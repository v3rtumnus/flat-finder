package at.michaelaltenburger.flatfinder.crawler.immoscout.page;

import at.michaelaltenburger.flatfinder.entity.PurchaseType;
import at.michaelaltenburger.flatfinder.entity.RealEstateType;
import at.michaelaltenburger.flatfinder.util.FlatFinderPage;
import at.michaelaltenburger.flatfinder.util.SeleniumUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ImmoSearchPage extends FlatFinderPage {

    @FindBy(xpath = "//div[@id='at-gac-input']/input")
    private WebElement cityTextField;

    @FindBy(xpath = "//input[@data-ng-model='area']")
    private WebElement minSquareMetresField;

    @FindBy(xpath = "//input[@data-ng-model='price']")
    private WebElement priceField;

    @FindBy(xpath = "//select[@data-ng-model='rooms']")
    private WebElement roomsSelect;

    @FindBy(xpath = "//input//following-sibling::span[text()='Miete']")
    private WebElement rentCheckbox;

    @FindBy(xpath = "//input//following-sibling::span[text()='Kaufen']")
    private WebElement salesCheckbox;

    @FindBy(id = "at-estate-type")
    private WebElement estateType;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    public ImmoSearchPage(SeleniumUtil util) {
        super(util);
    }

    public void setCriteria(RealEstateType type, PurchaseType purchaseType,
                            String city, String minSquareMetres, String minRooms, String maxPrice) {
        if(type == RealEstateType.FLAT) {
            new Select(estateType).selectByIndex(1);
        } else {
            new Select(estateType).selectByIndex(2);
        }
        if(purchaseType == PurchaseType.BUY) {
            rentCheckbox.click();
            salesCheckbox.click();
        }
        cityTextField.sendKeys(city);
        minSquareMetresField.sendKeys(minSquareMetres);
        new Select(roomsSelect).selectByValue(minRooms);
        priceField.sendKeys(maxPrice);
    }

    public ImmoSearchResultPage submitSearch() {
        submitButton.click();

        ImmoSearchResultPage searchResultPage = new ImmoSearchResultPage(util);
        PageFactory.initElements(util.getDriver(), searchResultPage);

        return searchResultPage;
    }
}
