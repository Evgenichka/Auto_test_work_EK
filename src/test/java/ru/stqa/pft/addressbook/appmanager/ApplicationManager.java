package ru.stqa.pft.addressbook.appmanager;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
    private final Properties properties;
    WebDriver wd;

    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private final String browser;
    private ContactHelper contactHelper;
    private DbHelper dbHelper;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

        dbHelper = new DbHelper();

        if ("".equals(properties.getProperty("selenium.server"))) {
            switch (browser.toLowerCase()) {
                case "firefox":
                    System.setProperty("webdriver.gecko.driver", "/path/to/geckodriver"); // укажите полный путь к geckodriver
                    FirefoxOptions optionsFirefox = new FirefoxOptions();
                    wd = new FirefoxDriver(optionsFirefox);
                    break;
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver"); // укажите полный путь к chromedriver
                    ChromeOptions optionsChrome = new ChromeOptions();
                    wd = new ChromeDriver(optionsChrome);
                    break;
                case "ie":
                    System.setProperty("webdriver.ie.driver", "/path/to/IEDriverServer.exe"); // укажите полный путь к IEDriverServer
                    InternetExplorerOptions optionsIE = new InternetExplorerOptions();
                    wd = new InternetExplorerDriver(optionsIE);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown browser: " + browser);
            }
        } else {
            URL remoteURL = new URL(properties.getProperty("selenium.server"));

            switch (browser.toLowerCase()) {
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setCapability("platformName", Platform.fromString(System.getProperty("platform", "WIN10")).toString());
                    wd = new RemoteWebDriver(remoteURL, firefoxOptions);
                    break;
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setCapability("platformName", Platform.fromString(System.getProperty("platform", "WIN10")).toString());
                    wd = new RemoteWebDriver(remoteURL, chromeOptions);
                    break;
                case "ie":
                    InternetExplorerOptions ieOptions = new InternetExplorerOptions();
                    ieOptions.setCapability("platformName", Platform.fromString(System.getProperty("platform", "WIN10")).toString());
                    wd = new RemoteWebDriver(remoteURL, ieOptions);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown browser: " + browser);
            }
        }

        wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        wd.get(properties.getProperty("web.baseUrl"));

        groupHelper = new GroupHelper(wd);
        contactHelper = new ContactHelper(wd);
        navigationHelper = new NavigationHelper(wd);
        SessionHelper sessionHelper = new SessionHelper(wd);
        sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
    }

//    public void init() throws IOException {
//        String target = System.getProperty("target", "local");
//        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
//
//        dbHelper = new DbHelper();
//
//        if ("".equals(properties.getProperty("selenium.server"))) {
//            if (browser.equals(BrowserType.FIREFOX)) {
//                wd = new FirefoxDriver();
//            } else if (browser.equals(BrowserType.CHROME)) {
//                wd = new ChromeDriver();
//            } else if (browser.equals(BrowserType.IE)) {
//                wd = new InternetExplorerDriver();
//            }
//        } else {
//            DesiredCapabilities capabilities = new DesiredCapabilities();
//            capabilities.setBrowserName(browser);
//            capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "win7")));
//            wd = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
//        }
//        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
//        wd.get(properties.getProperty("web.baseUrl"));
//        groupHelper = new GroupHelper(wd);
//        contactHelper = new ContactHelper(wd);
//        navigationHelper = new NavigationHelper(wd);
//        SessionHelper sessionHelper = new SessionHelper(wd);
//        sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
//    }

    public void stop() {
        wd.quit();
    }

    public GroupHelper group() {
        return groupHelper;
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public ContactHelper contact() {
        return contactHelper;
    }

    public DbHelper db() {
        return dbHelper;
    }

    public byte[] takeScreenshot() {
        return ((TakesScreenshot) wd).getScreenshotAs(OutputType.BYTES);
    }
}