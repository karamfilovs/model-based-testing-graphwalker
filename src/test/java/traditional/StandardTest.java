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
        inv.loginPage().login(EMAIL, PASSWORD);
        Assertions.assertEquals(EMAIL, inv.homePage().getUserPanelText());
        inv.homePage().clickLogoutLink();
        Assertions.assertEquals(COMPANY_NAME, inv.loginPage().getCompanyName());
    }

    @Test
    @DisplayName("Can navigate to home page via header")
    public void canNavigateToHomePageViaHeader() {
        inv.loginPage().login(EMAIL, PASSWORD);
        Assertions.assertEquals(EMAIL, inv.homePage().getUserPanelText());
        inv.homePage().clickCompanyLogo();
        Assertions.assertEquals(EMAIL, inv.homePage().getUserPanelText());
    }


    @Test
    @DisplayName("Can navigate to Items page")
    public void canNavigateToItemsPage() {
        inv.loginPage().login(EMAIL, PASSWORD);
        Assertions.assertEquals(EMAIL, inv.homePage().getUserPanelText());
        inv.itemsPage().clickItemsLink();
        Assertions.assertEquals("Артикули", inv.itemsPage().getHeadlineText());
    }

    @Test
    @DisplayName("Can search for existing items")
    public void canSearchForExistingItems() {
        inv.loginPage().login(EMAIL, PASSWORD);
        Assertions.assertEquals(EMAIL, inv.homePage().getUserPanelText());
        inv.itemsPage().clickItemsLink();
        Assertions.assertEquals("Артикули", inv.itemsPage().getHeadlineText());
        inv.itemsPage().clickSearchButton();
        inv.itemsPage().searchItem("test");
        Assertions.assertTrue(inv.itemsPage().isSearchTableDisplayed());
    }

    @Test
    @DisplayName("Can search for not-existing items")
    public void canSearchForNotExistingItems() {
        inv.loginPage().login(EMAIL, PASSWORD);
        Assertions.assertEquals(EMAIL, inv.homePage().getUserPanelText());
        inv.itemsPage().clickItemsLink();
        Assertions.assertEquals("Артикули", inv.itemsPage().getHeadlineText());
        inv.itemsPage().clickSearchButton();
        inv.itemsPage().searchItem("not-existing");
        Assertions.assertEquals("Не са намерени артикули, отговарящи на зададените критерии.", inv.itemsPage().getEmptyListText());
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
    @DisplayName("Can navigate between Login page and Forgotten password page")
    public void canNavigateBetweenLoginPageAndForgottenPasswordPage() {
        inv.loginPage().gotoPage();
        Assertions.assertEquals(COMPANY_NAME, inv.loginPage().getCompanyName());
        inv.loginPage().clickForgottenPasswordLink();
        Assertions.assertEquals("Възстановяване на парола", inv.resetPasswordPage().getPageHeaderText());
        inv.resetPasswordPage().clickReturnBackToLoginPageLink();
        Assertions.assertEquals(COMPANY_NAME, inv.loginPage().getCompanyName());
    }
}
