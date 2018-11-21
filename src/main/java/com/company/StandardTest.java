package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StandardTest extends BaseTest {
    private final String COMPANY_NAME = "QA Ground";
    private final String EMAIL = "karamfilovs@gmail.com";
    private final String PASSWORD = "123456";

    @BeforeEach
    public void login() {
        inv.loginPage().gotoPage();
        inv.loginPage().verifyCompanyName(COMPANY_NAME);
        inv.loginPage().enterEmail(EMAIL);
        inv.loginPage().enterPassword(PASSWORD);
        inv.loginPage().clickLoginButton();
    }


    @Test
    public void canLoginSuccessfullyWithValidCredentials() {
        inv.homePage().verifyLoggedUser(EMAIL);
    }

    @Test
    public void canLogoutSuccessfully() {
        inv.homePage().verifyLoggedUser(EMAIL);
        inv.homePage().clickLogoutLink();
        inv.loginPage().verifyCompanyName(COMPANY_NAME);
    }

    @Test
    public void canNavigateToHomePageViaHeader() {
        inv.homePage().verifyLoggedUser(EMAIL);
        inv.homePage().clickCompanyLogo();
        inv.homePage().verifyLoggedUser(EMAIL);
    }


    @Test
    public void canNavigateToItemsPage() {
        inv.homePage().verifyLoggedUser(EMAIL);
        inv.itemsPage().clickItemsLink();
        inv.itemsPage().verifyHeadlineText("Артикули");
    }
}
