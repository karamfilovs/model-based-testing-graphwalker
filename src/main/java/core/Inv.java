package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.HomePage;
import pages.ItemsPage;
import pages.LoginPage;
import pages.ResetPasswordPage;

import java.io.File;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class Inv {
    private static final Logger LOGGER = LoggerFactory.getLogger(Inv.class);
    private WebDriver driver;

    //pages
    private HomePage homePage;
    private LoginPage loginPage;
    private ItemsPage itemsPage;
    private ResetPasswordPage resetPasswordPage;


    public void startBrowser(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            throw new InvalidArgumentException("Not supported browser");
        }

        LOGGER.info("***************** STARTING TEST *****************");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
    }


    public void quit() {
        driver.quit();
    }


    //lazy instantiating methods
    public HomePage homePage() {
        if (homePage == null) {
            homePage = new HomePage(driver);
            return homePage;
        } else {
            return homePage;
        }
    }

    public LoginPage loginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(driver);
            return loginPage;
        } else {
            return loginPage;
        }
    }

    public ItemsPage itemsPage() {
        if (itemsPage == null) {
            itemsPage = new ItemsPage(driver);
            return itemsPage;
        } else {
            return itemsPage;
        }
    }

    public ResetPasswordPage resetPasswordPage() {
        if (resetPasswordPage == null) {
            resetPasswordPage = new ResetPasswordPage(driver);
            return resetPasswordPage;
        } else {
            return resetPasswordPage;
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
            TakesScreenshot screenshotTakingDriver = (TakesScreenshot) driver;
            try {
                File localScreenshots = new File(new File("target"), "screenshots");
                if (!localScreenshots.exists() || !localScreenshots.isDirectory()) {
                    localScreenshots.mkdirs();
                }
                File screenshot = new File(localScreenshots, className + "_" + method + "_" + timestamp.getHour() + "." + timestamp.getMinute() + "." + timestamp.getSecond() + ".png");
                FileUtils.copyFile(screenshotTakingDriver.getScreenshotAs(OutputType.FILE), screenshot);
                LOGGER.info("Screenshot for class={} method={} saved in: {}", className, method, screenshot.getAbsolutePath());
            } catch (Exception e1) {
                LOGGER.error("Unable to take screenshot", e1);
            }
        } else {
            LOGGER.info("Driver '{}' can't take screenshots so skipping it.", driver.getClass());
        }
    }
}
