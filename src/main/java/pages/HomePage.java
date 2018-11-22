package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomePage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);
    private static final String PAGE_URL = "/login";
    private static final String LOGOUT_URL = "/logout";

    @FindBy(xpath = "//div[@class='userpanel-header']")
    private WebElement userPanel;

    @FindBy(xpath = "//div[@id='logo']/a")
    private WebElement companyLogo;

    @FindBy(xpath = "//a[@class='selenium-button-logout button-logout']")
    private WebElement logoutLink;



    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigates to current page
     */
    public void gotoPage() {
        LOGGER.info("Navigate to: " + BASE_URL + PAGE_URL);
        navigateTo(PAGE_URL);
    }

    public void clickCompanyLogo(){
        LOGGER.info("Clicking on company logo/header");
        click(companyLogo);
    }

    /**
     * Navigates to /logout page
     */
    public void navigateToLogout() {
        LOGGER.info("Navigate to: " + BASE_URL + LOGOUT_URL);
        navigateTo(PAGE_URL);
    }

    /**
     * Clicks logout link from user panel section
     */
    public void clickLogoutLink(){
        LOGGER.info("Clicking Logout link");
        click(userPanel);
        click(logoutLink);
    }

    /**
     * Retrieves text from user panel
     * @return text
     */
    public String getUserPanelText(){
        return getText(userPanel);
    }

    /**
     *
     * @param expectedText
     */
    public void verifyLoggedUser(String expectedText){
        LOGGER.info("Checking that logging user is: " + expectedText);
        assertEquals(expectedText, userPanel.getText(), "Logged user name is not as expected");
    }
}
