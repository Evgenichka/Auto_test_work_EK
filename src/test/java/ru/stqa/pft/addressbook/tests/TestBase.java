package ru.stqa.pft.addressbook.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class TestBase {

    Logger logger = LoggerFactory.getLogger(TestBase.class);

    protected static final ApplicationManager app =
            new ApplicationManager(System.getProperty("browser", "firefox")); // Устанавливаем браузер по умолчанию

    /**
     * Метод выполняется один раз перед всеми тестами.
     */
    @BeforeAll
    public static void setUp() throws Exception {
        app.init();
    }

    /**
     * Метод выполняется один раз после завершения всех тестов.
     */
    @AfterAll
    public static void tearDown() {
        app.stop();
    }

    /**
     * Метод выполняется перед каждым тестом.
     *
     * @param m Текущий выполняющийся метод теста.
     */
    @BeforeEach
    public void logTestStart() {
        logger.info("Start test {} with parameters {}", m.getName(), Arrays.asList(p));
    }

    /**
     * Метод выполняется после каждого теста.
     *
     * @param m Текущий выполняющийся метод теста.
     */
    @AfterEach
    public void logTestStop() {
        logger.info("Stop test {}", m.getName());
    }

    /**
     * Вспомогательный метод для сравнения списков групп UI и DB.
     */
    public void verifyGroupListInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Groups dbGroups = app.db().groups();
            Groups uiGroups = app.group().all();
            assertThat(uiGroups, equalTo(dbGroups.stream()
                    .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
                    .collect(Collectors.toSet())));
        }
    }
}



//package ru.stqa.pft.addressbook.tests;
//
//import org.hamcrest.CoreMatchers;
//import org.hamcrest.MatcherAssert;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.edge.EdgeDriver;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
//import ru.stqa.pft.addressbook.model.GroupData;
//import ru.stqa.pft.addressbook.model.Groups;
//
//import java.lang.reflect.Method;
//import java.util.Arrays;
//import java.util.stream.Collectors;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//@Listeners(MyTestListener.class)
//public class TestBase {
//
//    Logger logger = LoggerFactory.getLogger(TestBase.class);
//
//    protected static final ApplicationManager app =
//            new ApplicationManager(System.getProperty("browser", "firefox")); // Устанавливаем браузер по умолчанию
//
//    @BeforeSuite
//    public void setUp(ITestContext context) throws Exception {
//        app.init();
//        context.setAttribute("app", app);
//    }
//
//    @AfterSuite(alwaysRun = true)
//    public void tearDown() {
//        app.stop();
//    }
//
//    @BeforeMethod
//    public void logTestStart(Method m, Object[] p) {
//        logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p));
//    }
//
//    @AfterMethod(alwaysRun = true)
//    public void logTestStop(Method m) {
//        logger.info("Stop test " + m.getName());
//    }
//
//    public void verifyGroupListInUI() {
//        if (Boolean.getBoolean("verifyUI")) {
//            Groups dbGroups = app.db().groups();
//            Groups uiGroups = app.group().all();
//            assertThat(uiGroups, equalTo(dbGroups.stream()
//                    .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
//                    .collect(Collectors.toSet())));
//        }
//    }
//}



//package ru.stqa.pft.addressbook.tests;
//
//import org.hamcrest.CoreMatchers;
//import org.hamcrest.MatcherAssert;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.testng.ITestContext;
//import org.testng.annotations.*;
//import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
//import ru.stqa.pft.addressbook.model.GroupData;
//import ru.stqa.pft.addressbook.model.Groups;
//
//import java.lang.reflect.Method;
//import java.util.Arrays;
//import java.util.stream.Collectors;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//@Listeners(MyTestListener.class)
//public class TestBase {
//
//  Logger logger = LoggerFactory.getLogger(TestBase.class);
//
//  protected static final ApplicationManager app
//          = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));
//
//  @BeforeSuite
//  public void setUp(ITestContext context) throws Exception {
//    app.init();
//    context.setAttribute("app", app);
//  }
//
//  @AfterSuite(alwaysRun = true)
//  public void tearDown() {
//    app.stop();
//  }
//
//  @BeforeMethod
//  public void logTestStart(Method m, Object[] p) {
//    logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p));
//  }
//
//  @AfterMethod(alwaysRun = true)
//  public void logTestStop(Method m) {
//    logger.info("Stop test " + m.getName());
//  }
//
//  public void verifyGroupListInUI() {
//    if (Boolean.getBoolean("verifyUI")) {
//      Groups dbGroups = app.db().groups();
//      Groups uiGroups = app.group().all();
//      assertThat(uiGroups, equalTo(dbGroups.stream()
//              .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
//              .collect(Collectors.toSet())));
//    }
//  }
//}
