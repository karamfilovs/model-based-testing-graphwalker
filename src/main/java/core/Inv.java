package core;

import pages.HomePage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.ItemsPage;
import pages.LoginPage;
import pages.ResetPasswordPage;

import java.io.File;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class Inv {
    private static final Logger LOGGER = LoggerFactory.getLogger(Inv.class);
    private WebDriver driver;

    //Drivers paths
    private final String WINDOWS_CHROME_DRIVER_PATH = "src\\main\\resources\\webdrivers\\chromedriver.exe";
    private final String WINDOWS_FIREFOX_DRIVER_PATH = "src\\main\\resources\\webdrivers\\geckodriver.exe";
    private final String LINUX_CHROME_DRIVER_PATH = "src/main/resources/webdrivers/linux-chromedriver-v2.42";
    private final String LINUX_FIREFOX_DRIVER_PATH = "src/main/resources/webdrivers/linux-geckodriver-v0.22.0";

    //pages
    private HomePage homePage;
    private LoginPage loginPage;
    private ItemsPage itemsPage;
    private ResetPasswordPage resetPasswordPage;


    public void startBrowser(String browser) {
        if (SystemUtils.IS_OS_LINUX) {
            if (browser.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", LINUX_CHROME_DRIVER_PATH);
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(isHeadless());
                options.addArguments("--window-size=1920,1080");
                driver = new ChromeDriver(options);
            } else if (browser.equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver", LINUX_FIREFOX_DRIVER_PATH);
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("-width=1920");
                options.addArguments("-height=1080");
                options.setHeadless(isHeadless());
                driver = new FirefoxDriver(options);
            } else {
                throw new RuntimeException("Not supported browser");
            }
        } else {
            if (browser.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", WINDOWS_CHROME_DRIVER_PATH);
                driver = new ChromeDriver();
                driver.manage().window().maximize();
            } else if (browser.equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver", WINDOWS_FIREFOX_DRIVER_PATH);
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
            } else {
                throw new RuntimeException("Not supported browser");
            }


            LOGGER.info("***************** STARTING TEST *****************");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().deleteAllCookies();
        }
    }

    private boolean isHeadless() {
        return System.getProperty("headless").equalsIgnoreCase("on") ? true : false;
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
