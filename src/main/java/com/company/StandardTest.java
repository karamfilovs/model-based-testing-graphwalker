package com.company;

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
    @DisplayName("Can login successfully")
    @Tag("high")
    public void canLoginSuccessfullyWithValidCredentials() {
        inv.loginPage().gotoPage();
        Assertions.assertEquals(inv.loginPage().getCompanyName(), COMPANY_NAME);
        inv.loginPage().enterEmail(EMAIL);
        inv.loginPage().enterPassword(PASSWORD);
        inv.loginPage().clickLoginButton();
        Assertions.assertEquals(inv.homePage().getUserPanelText(), EMAIL);
    }

    @Test
    @DisplayName("Can logout successfully")
    @Tag("high")
    public void canLogoutSuccessfully() {
        inv.loginPage().gotoPage();
        Assertions.assertEquals(inv.loginPage().getCompanyName(), COMPANY_NAME);
        inv.loginPage().enterEmail(EMAIL);
        inv.loginPage().enterPassword(PASSWORD);
        inv.loginPage().clickLoginButton();
        Assertions.assertEquals(inv.homePage().getUserPanelText(), EMAIL);
        inv.homePage().clickLogoutLink();
        Assertions.assertEquals(inv.loginPage().getCompanyName(), COMPANY_NAME);
    }

    @Test
    public void canNavigateToHomePageViaHeader() {
        inv.loginPage().gotoPage();
        Assertions.assertEquals(inv.loginPage().getCompanyName(), COMPANY_NAME);
        inv.loginPage().enterEmail(EMAIL);
        inv.loginPage().enterPassword(PASSWORD);
        inv.loginPage().clickLoginButton();
        Assertions.assertEquals(inv.homePage().getUserPanelText(), EMAIL);
        inv.homePage().clickCompanyLogo();
        Assertions.assertEquals(inv.homePage().getUserPanelText(), EMAIL);
    }


    @Test
    public void canNavigateToItemsPage() {
        inv.loginPage().gotoPage();
        Assertions.assertEquals(inv.loginPage().getCompanyName(), COMPANY_NAME);
        inv.loginPage().enterEmail(EMAIL);
        inv.loginPage().enterPassword(PASSWORD);
        inv.loginPage().clickLoginButton();
        Assertions.assertEquals(inv.homePage().getUserPanelText(), EMAIL);
        inv.itemsPage().clickItemsLink();
        Assertions.assertEquals(inv.itemsPage().getHeadlineText(), "Артикули");
    }

    @Test
    public void cantResetPasswordWithInvalidEmail(){
        inv.loginPage().gotoPage();
        Assertions.assertEquals(inv.loginPage().getCompanyName(), COMPANY_NAME);
        inv.loginPage().clickForgottenPasswordLink();
        inv.resetPasswordPage().enterEmail("al@al");
        inv.resetPasswordPage().clickSendButton();
        Assertions.assertEquals("Въведеният имейл е невалиден", inv.resetPasswordPage().getInvalidEmailErrorMessage());
    }

    @Test
    public void cantResetPasswordWithWrongEmail(){
        inv.loginPage().gotoPage();
        Assertions.assertEquals(inv.loginPage().getCompanyName(), COMPANY_NAME);
        inv.loginPage().clickForgottenPasswordLink();
        inv.resetPasswordPage().enterEmail("al@pragmatic.bg");
        inv.resetPasswordPage().clickSendButton();
        Assertions.assertEquals("Грешно потребителско име или e-mail адрес. Моля, опитайте отново.", inv.resetPasswordPage().getWrongEmailErrorMessage());
    }

    @Test
    public void canNavigateBetweenLoginPageAndForgottenPasswordPage(){
        inv.loginPage().gotoPage();
        Assertions.assertEquals(inv.loginPage().getCompanyName(), COMPANY_NAME);
        inv.loginPage().clickForgottenPasswordLink();
        Assertions.assertEquals("Възстановяване на парола", inv.resetPasswordPage().getPageHeaderText());
        inv.resetPasswordPage().clickReturnBackToLoginPageLink();
        Assertions.assertEquals(inv.loginPage().getCompanyName(), COMPANY_NAME);
    }
}
