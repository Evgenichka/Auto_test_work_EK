package ru.stqa.pft.addressbook.appmanager;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.tests.TestBase;

public class ApplicationManager {

    private final TestBase.Browser selectedBrowser;
    public WebDriver webDriver;

    private SessionHelper sessionHelper;
    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private ContactHelper contactHelper;

    // Оставляем единственный конструктор, принимающий тип браузера
    public ApplicationManager(TestBase.Browser browserType) {
        this.selectedBrowser = browserType;
    }

    // Метод инициализации
    public void initialize() {
        // Здесь выбираем драйвер в зависимости от переданного типа браузера
        switch (selectedBrowser) {
            case CHROME:
                webDriver = new ChromeDriver();
                webDriver.manage().window().maximize();
                break;
            case FIREFOX:
                webDriver = new FirefoxDriver();
                break;
            case EDGE:
                webDriver = new EdgeDriver();
                break;
            default:
                throw new Error("Unsupported browser type: " + selectedBrowser);
        }

        // Инициализируем вспомогательные объекты
        sessionHelper = new SessionHelper(webDriver);
        navigationHelper = new NavigationHelper(webDriver);
        groupHelper = new GroupHelper(webDriver);
        contactHelper = new ContactHelper(webDriver);

        // Переходим на сайт и выполняем авторизацию
        webDriver.get("http://localhost/addressbook/");
        sessionHelper.login("admin", "secret");
    }

    // Остальная логика остается прежней
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

//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.remote.Browser;
//import ru.stqa.pft.addressbook.tests.TestBase;
//
//public class ApplicationManager {
//
//    private final Browser selectedBrowser;
//    public WebDriver webDriver;
//
//    private SessionHelper sessionHelper;
//
//    private NavigationHelper navigationHelper;
//
//    private GroupHelper groupHelper;
//
//    private ContactHelper contactHelper;
//
//    public ApplicationManager(Browser selectedBrowser) {
//        this.selectedBrowser = selectedBrowser;
//    }
//
//    public ApplicationManager(TestBase.Browser browserType) {
//    }
//
//    public void initialize() {
//        if (selectedBrowser.equals(Browser.FIREFOX)) {
//            webDriver = new FirefoxDriver();
//        } else if (selectedBrowser.equals(Browser.CHROME)) {
//            webDriver = new ChromeDriver();
//            webDriver.manage().window().maximize();
//        } else if (selectedBrowser.equals(Browser.EDGE)) {
//            webDriver = new EdgeDriver();
//        }
//
//        sessionHelper = new SessionHelper(webDriver);
//        navigationHelper = new NavigationHelper(webDriver);
//        groupHelper = new GroupHelper(webDriver);
//        contactHelper = new ContactHelper(webDriver);
//        webDriver.get("http://localhost/");
//        sessionHelper.login("admin","secret");
//    }
//
//    public void stop() {
//        sessionHelper.logout();
//        webDriver.quit();
//    }
//
//    public NavigationHelper goTo() {
//        return navigationHelper;
//    }
//
//    public GroupHelper group() {
//        return groupHelper;
//    }
//
//    public ContactHelper contact() {
//        return contactHelper;
//    }
//}
