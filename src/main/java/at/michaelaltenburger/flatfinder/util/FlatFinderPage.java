package at.michaelaltenburger.flatfinder.util;

import org.openqa.selenium.support.PageFactory;

public abstract class FlatFinderPage {

    protected SeleniumUtil util;

    public FlatFinderPage(SeleniumUtil util) {
        this.util = util;
        initElements();
    }

    public void initElements() {
        PageFactory.initElements(this.util.getDriver(), this);
    }
}
