package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;

public class ApplicationManager {

    private final Browser selectedBrowser;
    public WebDriver webDriver;

    private SessionHelper sessionHelper;

    private NavigationHelper navigationHelper;

    private GroupHelper groupHelper;

    private ContactHelper contactHelper;

    public ApplicationManager(Browser selectedBrowser) {
        this.selectedBrowser = selectedBrowser;
    }

    public void initialize() {
        if (selectedBrowser.equals(Browser.FIREFOX)) {
            webDriver = new FirefoxDriver();
        } else if (selectedBrowser.equals(Browser.CHROME)) {
            webDriver = new ChromeDriver();
            webDriver.manage().window().maximize();
        } else if (selectedBrowser.equals(Browser.EDGE)) {
            webDriver = new EdgeDriver();
        }

        sessionHelper = new SessionHelper(webDriver);
        navigationHelper = new NavigationHelper(webDriver);
        groupHelper = new GroupHelper(webDriver);
        contactHelper = new ContactHelper(webDriver);
        webDriver.get("http://localhost/");
        sessionHelper.login("admin","secret");
    }

    public void stop() {
        sessionHelper.logout();
        webDriver.quit();
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public GroupHelper group() {
        return groupHelper;
    }

    public ContactHelper contact() {
        return contactHelper;
    }
}
