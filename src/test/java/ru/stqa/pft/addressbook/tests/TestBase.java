package ru.stqa.pft.addressbook.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.Browser;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;



public class TestBase {

    protected final ApplicationManager appManager = new ApplicationManager(Browser.CHROME);

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        appManager.initialize();
    }

    @AfterEach
    public void tearDown() {
        appManager.stop();
    }   }

