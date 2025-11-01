package ru.stqa.pft.addressbook.tests;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.Browser;





public class TestBase {

    protected static final ApplicationManager appManager = new ApplicationManager(Browser.CHROME);

    @BeforeEach
    public void setUp() {
        appManager.initialize();
    }

    @AfterEach
    public void tearDown() {
        appManager.stop();
    }   }



