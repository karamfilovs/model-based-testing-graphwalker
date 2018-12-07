package com.company;

import core.Inv;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.jupiter.api.Assertions;

@GraphWalker(value = "random(edge_coverage(100))", start = "e_GoToLoginPage")
public class ResetPasswordModelTest extends ExecutionContext implements ForgottenPassword {
    private Inv inv;

    public ResetPasswordModelTest() {
        inv = new Inv();
        inv.startBrowser(System.getProperty("browser"));
    }


    @Override
    public void e_ClickForgottenPasswordLink() {
        inv.loginPage().clickForgottenPasswordLink();
    }

    @Override
    public void e_SubmitInvalidEmail() {
        inv.resetPasswordPage().enterEmail("al@pragmatic");
        inv.resetPasswordPage().clickSendButton();
    }

    @Override
    public void v_ResetPasswordPage() {
        Assertions.assertEquals("Възстановяване на парола", inv.resetPasswordPage().getPageHeaderText());
    }

    @Override
    public void v_WrongEmailErrorMessage() {
        Assertions.assertEquals("Грешно потребителско име или e-mail адрес. Моля, опитайте отново.", inv.resetPasswordPage().getWrongEmailErrorMessage());

    }

    @Override
    public void e_ClickReturnToLoginPageLink() {
        inv.resetPasswordPage().clickReturnBackToLoginPageLink();

    }

    @Override
    public void e_SubmitWrongEmail() {
        inv.resetPasswordPage().enterEmail("al@pragmatic.bg");
        inv.resetPasswordPage().clickSendButton();

    }

    @Override
    public void v_InvalidEmailErrorMessage() {
        Assertions.assertEquals("Въведеният имейл е невалиден", inv.resetPasswordPage().getInvalidEmailErrorMessage());

    }

    @Override
    public void v_LoginPage() {
        Assertions.assertEquals("QA Ground", inv.loginPage().getCompanyName());

    }

    @Override
    public void e_GoToLoginPage() {
        inv.loginPage().gotoPage();
    }
}
