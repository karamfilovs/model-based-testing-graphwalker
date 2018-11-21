package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemsPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemsPage.class);
    private static final String PAGE_URL = "/objects/manage";

    @FindBy(xpath = "//div[@id='headline']//h2")
    private WebElement headline;

    @FindBy(xpath = "//div[@id='tabs_objects']")
    private WebElement itemsLink;


    public ItemsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigates to current page
     */
    public void gotoPage() {
        LOGGER.info("Navigate to: " + BASE_URL + PAGE_URL);
        navigateTo(PAGE_URL);
    }

    /**
     * Verifies headline text
     * @param expectedText
     */
    public void verifyHeadlineText(String expectedText){
        LOGGER.info("Checking headline text is: " + expectedText);
        assertEquals(expectedText, headline.getText(), "Headline text is not as expected");
    }

    /**
     * Clicks on Items link
     */
    public void clickItemsLink(){
        LOGGER.info("Clicking Items link from main menu");
        click(itemsLink);
    }
}
