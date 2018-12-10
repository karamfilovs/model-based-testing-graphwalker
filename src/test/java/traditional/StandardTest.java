package traditional;

import org.junit.jupiter.api.*;

public class StandardTest extends BaseTest {
    private final String COMPANY_NAME = "QA Ground";
    private final String EMAIL = "karamfilovs@gmail.com";
    private final String PASSWORD = "123456";

    @BeforeEach
    public void login() {
        //Move all login steps here to avoid duplication
    }


    @Test
    @DisplayName("Can login successfully with valid credentials")
    @Tag("high")
    public void canLoginSuccessfullyWithValidCredentials() {
        inv.loginPage().gotoPage();
        Assertions.assertEquals(COMPANY_NAME, inv.loginPage().getCompanyName());
        inv.loginPage().enterEmail(EMAIL);
        inv.loginPage().enterPassword(PASSWORD);
        inv.loginPage().clickLoginButton();
        Assertions.assertEquals(EMAIL, inv.homePage().getUserPanelText());
    }

    @Test
    @DisplayName("Can logout successfully")
    @Tag("high")
    public void canLogoutSuccessfully() {
        inv.loginPage().gotoPage();
        Assertions.assertEquals(COMPANY_NAME, inv.loginPage().getCompanyName());
        inv.loginPage().enterEmail(EMAIL);
        inv.loginPage().enterPassword(PASSWORD);
        inv.loginPage().clickLoginButton();
        Assertions.assertEquals(EMAIL, inv.homePage().getUserPanelText());
        inv.homePage().clickLogoutLink();
        Assertions.assertEquals(COMPANY_NAME, inv.loginPage().getCompanyName());
    }

    @Test
    public void canNavigateToHomePageViaHeader() {
        inv.loginPage().gotoPage();
        Assertions.assertEquals(COMPANY_NAME, inv.loginPage().getCompanyName());
        inv.loginPage().enterEmail(EMAIL);
        inv.loginPage().enterPassword(PASSWORD);
        inv.loginPage().clickLoginButton();
        Assertions.assertEquals(EMAIL, inv.homePage().getUserPanelText());
        inv.homePage().clickCompanyLogo();
        Assertions.assertEquals(EMAIL, inv.homePage().getUserPanelText());
    }


    @Test
    public void canNavigateToItemsPage() {
        inv.loginPage().gotoPage();
        Assertions.assertEquals(COMPANY_NAME, inv.loginPage().getCompanyName());
        inv.loginPage().enterEmail(EMAIL);
        inv.loginPage().enterPassword(PASSWORD);
        inv.loginPage().clickLoginButton();
        Assertions.assertEquals(EMAIL, inv.homePage().getUserPanelText());
        inv.itemsPage().clickItemsLink();
        Assertions.assertEquals("Артикули", inv.itemsPage().getHeadlineText());
    }

    @Test
    @DisplayName("Cant reset password with invalid email")
    public void cantResetPasswordWithInvalidEmail() {
        inv.loginPage().gotoPage();
        Assertions.assertEquals(COMPANY_NAME, inv.loginPage().getCompanyName());
        inv.loginPage().clickForgottenPasswordLink();
        inv.resetPasswordPage().enterEmail("invalid@email");
        inv.resetPasswordPage().clickSendButton();
        Assertions.assertEquals("Въведеният имейл е невалиден", inv.resetPasswordPage().getInvalidEmailErrorMessage());
    }

    @Test
    @DisplayName("Cant reset password with invalid email")
    public void cantResetPasswordWithWrongEmail() {
        inv.loginPage().gotoPage();
        Assertions.assertEquals(COMPANY_NAME, inv.loginPage().getCompanyName());
        inv.loginPage().clickForgottenPasswordLink();
        inv.resetPasswordPage().enterEmail("al@pragmatic.bg");
        inv.resetPasswordPage().clickSendButton();
        Assertions.assertEquals("Грешно потребителско име или e-mail адрес. Моля, опитайте отново.", inv.resetPasswordPage().getWrongEmailErrorMessage());
    }

    @Test
    public void canNavigateBetweenLoginPageAndForgottenPasswordPage() {
        inv.loginPage().gotoPage();
        Assertions.assertEquals(COMPANY_NAME, inv.loginPage().getCompanyName());
        inv.loginPage().clickForgottenPasswordLink();
        Assertions.assertEquals("Възстановяване на парола", inv.resetPasswordPage().getPageHeaderText());
        inv.resetPasswordPage().clickReturnBackToLoginPageLink();
        Assertions.assertEquals(COMPANY_NAME, inv.loginPage().getCompanyName());
    }
}
