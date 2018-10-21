package at.michaelaltenburger.flatfinder.implementation.immoscout.page;

import at.michaelaltenburger.flatfinder.util.FlatFinderPage;
import at.michaelaltenburger.flatfinder.util.SeleniumUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ImmoHomePage extends FlatFinderPage {

    @FindBy(xpath = "//div[@id='at-gac-input']/input")
    private WebElement cityTextField;

    @FindBy(xpath = "//input[@type='submit']")
    private WebElement submitButton;

    public ImmoHomePage(SeleniumUtil util) {
        super(util);
    }

    public void navigate() {
        this.util.navigateTo("http://www.immobilienscout24.at/");
    }

    public void enterCity(String city) {
        this.cityTextField.sendKeys(city);
    }

    public ImmoSearchPage submit() {
        util.clickElement(submitButton);

        ImmoSearchPage searchPage = new ImmoSearchPage(util);
        PageFactory.initElements(util.getDriver(), searchPage);

        return searchPage;
    }
}
