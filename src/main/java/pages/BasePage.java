package pages;

import enums.Checked;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class BasePage {
    public static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);

    protected final String BASE_URL = "http://st2016.inv.bg";

    protected WebDriver driver;

    @FindBy(how = How.XPATH, using = "//h2")
    private WebElement headerText;



    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies h2 text
     *
     * @param expectedText page header text
     */
    public void verifyHeaderText(String expectedText) {
        LOGGER.info("Verifying h2 text is:" + expectedText);
        Assertions.assertEquals(expectedText, getText(headerText), "Header text is not as expected");
    }



    protected void typeTextWithActionsBuilder(WebElement element, String text) {
        waitForElementVisibility(element);
        Actions builder = new Actions(driver);
        builder.click(element).sendKeys(text).perform();
    }


    protected void typeText(WebElement element, String text) {
        waitForElementVisibility(element);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Navigates to specific url appended to base url
     *
     * @param url page url extension after the base url
     */
    protected void navigateTo(String url) {
        driver.navigate().to(BASE_URL + url);
    }

    protected void click(WebElement element) {
        waitForElementVisibility(element);
        element.click();
    }

    protected void clickWithActionsBuilder(WebElement element) {
        Actions builder = new Actions(driver);
        builder.moveToElement(element).click();
    }

    protected String getTitle() {
        return driver.getTitle();
    }

    public void clickBrowserBack() {
        driver.navigate().back();
    }

    public void clickBrowserForward() {
        driver.navigate().forward();
    }

    public boolean isDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    protected void switchToActiveElement() {
        driver.switchTo().activeElement();
    }

    public void refreshBrowser() {
        driver.navigate().refresh();
    }


    /**
     * Method used for checking a checkbox or radio button
     *
     * @param checkboxToCheck the element you want to be checked
     * @param isChecked       enums.Checked.YES or enums.Checked.NO depending on if you want it checked or no
     */
    protected void checkCheckbox(WebElement checkboxToCheck, Checked isChecked) {
        if (!checkboxToCheck.isSelected() && isChecked == Checked.YES) {
            checkboxToCheck.click();
        } else if (checkboxToCheck.isSelected() && isChecked == Checked.NO) {
            checkboxToCheck.click();
        }
    }

    /**
     * Method used for selecting a radio button within multiple radio buttons based on the
     * visible text
     *
     * @param allRadioButtons           a list of all available radio buttons from which you would like to select
     * @param optionVisibleTextToSelect the option that you want to be selected from the multiple radio buttons
     */
    public void selectRadioButton(List<WebElement> allRadioButtons, String optionVisibleTextToSelect) {
        for (WebElement curRadioButton : allRadioButtons) {
            if (curRadioButton.getText().toLowerCase().contains(optionVisibleTextToSelect.toLowerCase())) {
                curRadioButton.click();
                return;
            }
        }

        throw new RuntimeException("Was unable to find a radio button with the specified text: " + optionVisibleTextToSelect);
    }

    /**
     * Waits for a specified element to be visible to work with it for a specified time frame
     *
     * @param elementToBeVisible
     * @return
     */
    protected WebElement waitForElementVisibility(WebElement elementToBeVisible) {
        waitForFullPageOrJsAjaxToLoad();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 25);
        WebElement foundElementAfterWait = wait.until(ExpectedConditions.visibilityOf(elementToBeVisible));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return foundElementAfterWait;

    }

    /**
     * Waits for a specified element to be visible to work with it for a specified time frame
     *
     * @param elementToBeVisible
     * @return
     * @int timeOutInSeconds  wait for specific time
     */
    protected WebElement waitForElementVisibility(WebElement elementToBeVisible, int timeOutInSeconds) {
        waitForFullPageOrJsAjaxToLoad();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        WebElement foundElementAfterWait = wait.until(ExpectedConditions.visibilityOf(elementToBeVisible));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return foundElementAfterWait;

    }


    /**
     * Waits for a specified element to be clickable to work with it for a specified time frame
     * *
     *
     * @param elementToBeVisible
     * @return
     */
    protected WebElement waitForElementToBeClickable(WebElement elementToBeVisible) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 25);
        WebElement foundElementAfterWait = wait.until(ExpectedConditions.elementToBeClickable(elementToBeVisible));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return foundElementAfterWait;
    }

    /**
     * Gets the text of a specified Web Element
     *
     * @param elementToGetText the element you want to get the text of
     * @return the containing text of the specified element
     */
    protected String getText(WebElement elementToGetText) {
        waitForElementVisibility(elementToGetText);
        return elementToGetText.getText();
    }


    /**
     * Selects an option from a dropdown based on the visible text
     *
     * @param dropDown                  the drop down WebElement
     * @param optionVisibleTextToSelect the option in the dropdown you would like to see selected
     */
    public void selectDropDownOptionByVisibleText(WebElement dropDown, String optionVisibleTextToSelect) {
        waitForElementVisibility(dropDown);
        Select select = new Select(dropDown);
        select.selectByVisibleText(optionVisibleTextToSelect);
    }

    /**
     * gets an option from a dropdown based on the visible text
     *
     * @param dropDown                  the drop down WebElement
     * @return returns selected value as string
     */
    public String getDropDownOption(WebElement dropDown) {
        waitForElementVisibility(dropDown);
        Select select = new Select(dropDown);
        return getText(select.getFirstSelectedOption());
    }



    public boolean waitForFullPageOrJsAjaxToLoad() {

        WebDriverWait wait = new WebDriverWait(driver, 30);

        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    // no jQuery present
                    return true;
                }
            }
        };

        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };

        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }


    public void quickLogout() {
        navigateTo("/logout");

    }

    /**
     * wait specific time in miliseconds.
     *
     * @param timeInMillisecond
     */
    public void waitTime(int timeInMillisecond) {

        LOGGER.info("Waiting " + timeInMillisecond / 1000 + " seconds");
        try {

            Thread.sleep(timeInMillisecond);
        } catch (Exception a) {
            throw new RuntimeException("Couldn't wait time  in milliseconds " + timeInMillisecond);
        }

    }


    /**
     * Takes screenshot of the current screen
     *
     * @param className Name of the class from which it was invoked
     * @param method    Test method name
     * @param timestamp Current time stamp
     */
    public void takeScreenshot(String className, String method, LocalTime timestamp) {
        if (driver instanceof TakesScreenshot) {
            TakesScreenshot screenshotTakingDriver = (TakesScreenshot) this.driver;
            try {
                File localScreenshots = new File(new File("target"), "screenshots");
                if (!localScreenshots.exists() || !localScreenshots.isDirectory()) {
                    localScreenshots.mkdirs();
                }
                File screenshot = new File(localScreenshots, className + "_" + method + "_" + timestamp.getHour() + "." + timestamp.getMinute() + ".png");
                FileUtils.copyFile(screenshotTakingDriver.getScreenshotAs(OutputType.FILE), screenshot);
                LOGGER.info("Screenshot for class={} method={} saved in: {}", className, method, screenshot.getAbsolutePath());
            } catch (Exception e1) {
                LOGGER.error("Unable to take screenshot", e1);
            }
        } else {
            LOGGER.info("Driver '{}' can't take screenshots so skipping it.", driver.getClass());
        }
    }

    protected List<WebElement> getTableRowsByCriteria(WebElement table, String criteria) {
        List<WebElement> rows = getTableRows(table);
        List<WebElement> filteredRows = rows.stream()
                .filter(x -> x.getText().contains(criteria))
                .collect(Collectors.toList());
        LOGGER.info("Rows after filtering:");
        filteredRows.forEach(row -> LOGGER.info(row.getText()));
        return filteredRows;
    }



    protected List<WebElement> getTableRows(WebElement table) {
        Validate.notNull(table, "Table element should not be null");
        List<WebElement> rows = table.findElements(By.tagName("tr"))   // get table rows
                .stream()
                .collect(Collectors.toList());
        return rows;
    }

    private List<WebElement> getCells(WebElement row) {
        Validate.notNull(row, "Row element should not be null");
        List<WebElement> cells = row.findElements(By.tagName("td"))   // get table rows
                .stream()
                .collect(Collectors.toList());
        return cells;
    }


    protected List<WebElement> getTableCells(WebElement table){
        List<WebElement> cells = new ArrayList<>();
        getTableRows(table).forEach(row -> cells.addAll(getCells(row)));
        return cells;
    }

    /**
     * Scrolling down to element
     * @param element target element
     */
    protected void scrollDownToElement(WebElement element) {
        LOGGER.info("Scrolling down");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //This will scroll the page till the element is found
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

}
