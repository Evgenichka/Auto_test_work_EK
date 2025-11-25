package ru.stqa.pft.addressbook.tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import org.openqa.selenium.remote.Browser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestBase {

    // Создали enum Browser, если его раньше не было
    public enum Browser {
        CHROME,
        FIREFOX,
        EDGE,
        SAFARI
    }

    // Статический экземпляр менеджера приложений
    protected static final ApplicationManager appManager;

    // Переменная для хранения путей к файлам конфигурации
    private static final String CONFIG_FILE_PATH = System.getProperty("config.file", "test-config.properties");

    /**
     * Инициализация ApplicationManager с параметрами из конфигурационного файла
     */
    static {
        // Загружаем конфигурационные данные
        Properties properties = loadConfiguration(CONFIG_FILE_PATH);

        // Устанавливаем значение браузера (если есть необходимость выбрать другой браузер из конфигурации)
        Browser browserType = Browser.valueOf(properties.getProperty("browser.type", "CHROME")); // Используем valueOf()

        // Создаем менеджер приложений с указанным браузером
        appManager = new ApplicationManager(browserType);
    }

    /**
     * Метод для загрузки конфигурационного файла
     *
     * @param fileName Имя файла конфигурации
     * @return Объект Properties с прочитанными значениями
     */
    private static Properties loadConfiguration(String fileName) {
        Properties properties = new Properties();
        try (InputStream inputStream = TestBase.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IOException("Файл конфигурации не найден: " + fileName);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    @BeforeEach
    public void setUp() {
        appManager.initialize();
    }

    @AfterEach
    public void tearDown() {
        appManager.stop();
    }
}

//import org.junit.jupiter.api.*;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
//import org.openqa.selenium.remote.Browser;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//public class TestBase {
//
//    // Создали enum Browser, если его раньше не было
//    public enum Browser {
//        CHROME,
//        FIREFOX,
//        EDGE,
//        SAFARI
//    }
//
//    // Статический экземпляр менеджера приложений
//    protected static final ApplicationManager appManager;
//
//    // Переменная для хранения путей к файлам конфигурации
//    private static final String CONFIG_FILE_PATH = System.getProperty("config.file", "test-config.properties");
//
//    /**
//     * Инициализация ApplicationManager с параметрами из конфигурационного файла
//     */
//    static {
//        // Загружаем конфигурационные данные
//        Properties properties = loadConfiguration(CONFIG_FILE_PATH);
//
//        // Устанавливаем значение браузера (если есть необходимость выбрать другой браузер из конфигурации)
//        Browser browserType = Browser.valueOf(properties.getProperty("browser.type", "CHROME")); // Используем valueOf()
//
//        // Создаем менеджер приложений с указанным браузером
//        appManager = new ApplicationManager(browserType);
//    }
//
//    /**
//     * Метод для загрузки конфигурационного файла
//     *
//     * @param fileName Имя файла конфигурации
//     * @return Объект Properties с прочитанными значениями
//     */
//    private static Properties loadConfiguration(String fileName) {
//        Properties properties = new Properties();
//        try (InputStream inputStream = TestBase.class.getClassLoader().getResourceAsStream(fileName)) {
//            if (inputStream == null) {
//                throw new IOException("Файл конфигурации не найден: " + fileName);
//            }
//            properties.load(inputStream);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return properties;
//    }
//
//    @BeforeEach
//    public void setUp() {
//        appManager.initialize();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        appManager.stop();
//    }
//}
//    protected static final ApplicationManager appManager = new ApplicationManager(Browser.CHROME);
//
//    @BeforeEach
//    public void setUp() {
//        appManager.initialize();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        appManager.stop();
//    }   }





