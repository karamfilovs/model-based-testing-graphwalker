package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemsPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemsPage.class);
    private static final String PAGE_URL = "/objects/manage";

    @FindBy(xpath = "//div[@id='headline']//h2")
    private WebElement headline;

    @FindBy(xpath = "//div[@id='tabs_objects']")
    private WebElement itemsLink;

    @FindBy(xpath = "//div[@id='emptylist']")
    private WebElement emptyList;

    @FindBy(id = "searchbtn")
    private WebElement searchButton;

    @FindBy(id = "searchbtn2")
    private WebElement closeSearchButton;

    @FindBy(id = "table_controls")
    private WebElement searchTable;

    @FindBy(name = "nm")
    private WebElement itemNameField;


    @FindBy(name = "s")
    private WebElement triggerSearchItemButton;


    public ItemsPage(WebDriver driver) {
        super(driver);
    }


    /**
     * Retrieves headline text
     *
     * @return text
     */
    public String getHeadlineText() {
        return getText(headline);
    }

    /**
     * Clicks on Items link
     */
    public void clickItemsLink() {
        LOGGER.info("Clicking Items link from main menu");
        click(itemsLink);
    }


    /**
     * Clicks Search button
     *
     * @return this
     */
    public ItemsPage clickSearchButton() {
        LOGGER.info("Clicking Search button");
        click(searchButton);
        return this;
    }

    /**
     * Clicks close search button
     *
     * @return this
     */
    public ItemsPage clickCloseSearchButton() {
        LOGGER.info("Clicking Close Search button");
        click(closeSearchButton);
        return this;
    }

    /**
     * Clicks Search button in Table
     * @return this
     */
    public ItemsPage triggerSearchItemButton(){
        LOGGER.info("Clicking trigger search item button");
        click(triggerSearchItemButton);
        return this;
    }

    /**
     * Returns true if search table is visible
     *
     * @return true/false
     */
    public boolean isSearchTableDisplayed() {
        return searchTable.isDisplayed();
    }

    public void searchItem(String itemName) {
        enterItemName(itemName).triggerSearchItemButton();
    }

    public ItemsPage enterItemName(String itemName) {
        LOGGER.info("Entering item name: " + itemName);
        typeText(itemNameField, itemName);
        return this;
    }

    /**
     * Retrieves text from empty list
     * @return
     */
    public String getEmptyListText(){
        return emptyList.getText();
    }

}
