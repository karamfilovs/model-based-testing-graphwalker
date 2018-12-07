package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResetPasswordPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResetPasswordPage.class);
    private static final String PAGE_URL = "/password-reset?subdomain=st2016";

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(xpath = "//h4")
    private WebElement pageHeader;

    @FindBy(xpath = "//div[@class='alert selenium-message alert-danger sticky']")
    private WebElement wrongEmailErrorMessage;

    @FindBy(xpath = "//a[@class='forgotten-password-back-action']")
    private WebElement returnBackToLoginPageLink;

    @FindBy(id = "submit")
    private WebElement sendButton;

    @FindBy(xpath = "//p[@class='control-label error ']")
    private WebElement invalidEmailFormatErrorMessage;


    public ResetPasswordPage(WebDriver driver) {
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
     * Returns invalid email error message
     * @return text
     */
    public String getInvalidEmailErrorMessage(){
        return invalidEmailFormatErrorMessage.getText();
    }

    /**
     * Returns page header h4
     * @return text
     */
    public String getPageHeaderText(){
        return pageHeader.getText();
    }

    /**
     * Returns wrong email error message
     * @return text
     */
    public String getWrongEmailErrorMessage(){
        return wrongEmailErrorMessage.getText();
    }

    /**
     * Clicks Send button
     */
    public void clickSendButton(){
        LOGGER.info("Clicking Send button");
        click(sendButton);
    }

    /**
     * Clicks Return back to Login page link
     */
    public void clickReturnBackToLoginPageLink(){
        LOGGER.info("Clicking Return back to login page link");
        click(returnBackToLoginPageLink);
    }

    /**
     * Enters email in the email field
     * @param email email of the user
     */
    public void enterEmail(String email){
        LOGGER.info("Entering email: " + email);
        typeText(emailField, email);
    }
}
