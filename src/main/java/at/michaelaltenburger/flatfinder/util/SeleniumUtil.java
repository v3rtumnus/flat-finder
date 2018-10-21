package at.michaelaltenburger.flatfinder.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SeleniumUtil {

    @Value("${chromedriver.path}")
    private String chromeDriverPath;

    private WebDriver driver;

    public void initDriver() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        this.driver = new ChromeDriver();
    }

    public void close() {
        driver.close();
    }

    public void navigateTo(String url) {
        driver.navigate().to(url);
    }

    public void clickElement(WebElement element) {
        element.click();
    }

    public WebDriver getDriver() {
        return driver;
    }

}
