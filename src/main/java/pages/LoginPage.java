package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);
    private static final String PAGE_URL = "/login";

    @FindBy(id = "loginusername")
    private WebElement emailField;

    @FindBy(id = "loginpassword")
    private WebElement passwordField;

    @FindBy(id = "loginsubmit")
    private WebElement loginButton;


    @FindBy(id = "firstloginalert2")
    private WebElement loginPageAlert;

    @FindBy(xpath = "//div[@id='wellcome']")
    private WebElement companyNameDiv;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigates to current page
     */
    public void gotoPage() {
        LOGGER.info("Navigate to: " + BASE_URL + PAGE_URL);
        navigateTo(PAGE_URL);
    }

    public LoginPage enterEmail(String email){
        LOGGER.info("Entering email: " + email);
        typeText(emailField, email);
        return this;
    }

    public LoginPage enterPassword(String password){
        LOGGER.info("Entering password: " + password);
        typeText(passwordField, password);
        return this;
    }

    /**
     *
     * @return
     */
    public LoginPage clickLoginButton(){
        LOGGER.info("Clicking Login button");
        click(loginButton);
        return this;
    }

    /**
     *
     * @param email
     * @param password
     */
    public void login(String email, String password){
        enterEmail(email).enterPassword(password).clickLoginButton();
    }

    /**
     *
     * @param expectedText
     */
    public void verifyLoginPageAlertText(String expectedText){
        LOGGER.info("Checking Login page default alert text");
        assertEquals(expectedText, loginPageAlert.getText(), "Alert text is not as expected");
    }

    /**
     *
     * @param expectedText
     */
    public void verifyCompanyName(String expectedText){
        LOGGER.info("Checking company name is: " + expectedText);
        assertEquals(expectedText, companyNameDiv.getText(), "Company name text is not as expected");
    }

}
