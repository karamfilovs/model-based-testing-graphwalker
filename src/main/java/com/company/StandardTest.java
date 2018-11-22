package com.company;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StandardTest extends BaseTest {
    private final String COMPANY_NAME = "QA Ground";
    private final String EMAIL = "karamfilovs@gmail.com";
    private final String PASSWORD = "123456";

    @BeforeEach
    public void login() {
        inv.loginPage().gotoPage();
        Assertions.assertEquals(inv.loginPage().getCompanyName(), COMPANY_NAME);
        inv.loginPage().enterEmail(EMAIL);
        inv.loginPage().enterPassword(PASSWORD);
        inv.loginPage().clickLoginButton();
    }


    @Test
    public void canLoginSuccessfullyWithValidCredentials() {
        Assertions.assertEquals(inv.homePage().getUserPanelText(), EMAIL);
//        inv.homePage().verifyLoggedUser(EMAIL);
    }

    @Test
    public void canLogoutSuccessfully() {
        inv.homePage().verifyLoggedUser(EMAIL);
        inv.homePage().clickLogoutLink();
        Assertions.assertEquals(inv.loginPage().getCompanyName(), COMPANY_NAME);
//        inv.loginPage().verifyCompanyName(COMPANY_NAME);
    }

    @Test
    public void canNavigateToHomePageViaHeader() {
        Assertions.assertEquals(inv.homePage().getUserPanelText(), EMAIL);
//        inv.homePage().verifyLoggedUser(EMAIL);
        inv.homePage().clickCompanyLogo();
        Assertions.assertEquals(inv.homePage().getUserPanelText(), EMAIL);
//        inv.homePage().verifyLoggedUser(EMAIL);
    }


    @Test
    public void canNavigateToItemsPage() {
        inv.homePage().verifyLoggedUser(EMAIL);
        inv.itemsPage().clickItemsLink();
        Assertions.assertEquals(inv.itemsPage().getHeadlineText(), "Артикули");
//        inv.itemsPage().verifyHeadlineText("Артикули");
    }
}
