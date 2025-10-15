package ru.stqa.pft.addressbook.appmanager;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApplicationManager {

    private final Browser selectedBrowser;
    public WebDriver webDriver;

    private SessionHelper sessionHelper;
    @Getter
    private NavigationHelper navigationHelper;
    @Getter
    private GroupHelper groupHelper;
    @Getter
    private ContactHelper contactHelper;


    public ApplicationManager(Browser selectedBrowser) {
        this.selectedBrowser = selectedBrowser;
    }


    public void initialize() {
        if (selectedBrowser.equals(Browser.FIREFOX)) {
            webDriver = new FirefoxDriver();
        } else if (selectedBrowser.equals(Browser.CHROME)) {
            webDriver = new ChromeDriver();
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

//

    public void verifyGroupListInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            // Получаем группы из UI до изменений
            Groups beforeChange = groupHelper.all();

            // Выполняем какое-либо действие (например, создание или модификацию группы)
            // Это действие зависит от вашего сценария тестирования

            // Получаем группы из UI после изменений
            Groups afterChange = groupHelper.all();

            // Преобразуем множества в списки для последовательного сравнения
            List<GroupData> beforeList = new ArrayList<>(beforeChange);
            List<GroupData> afterList = new ArrayList<>(afterChange);

            // Сортируем списки по имени группы для надежного сравнения
            Collections.sort(beforeList, Comparator.comparing(GroupData::getName));
            Collections.sort(afterList, Comparator.comparing(GroupData::getName));

            // Проверяем, что размер списков совпадает
            assertThat(afterList.size(), equalTo(beforeList.size()));

            // Проверяем, что каждая группа в списке после изменений соответствует группе до изменений
            for (int i = 0; i < beforeList.size(); i++) {
                GroupData before = beforeList.get(i);
                GroupData after = afterList.get(i);

                // Сравниваем основные свойства группы
                assertThat(after.getName(), equalTo(before.getName()));
                assertThat(after.getHeader(), equalTo(before.getHeader()));
                assertThat(after.getFooter(), equalTo(before.getFooter()));
            }
        }
    }

    // Метод для навигации
    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public ContactHelper contact() {
        return contact();
    }

    // Метод для работы с группами
    public GroupHelper group() {
        return groupHelper;
    }

//    // Метод для работы с базой данных
//    public DbHelper db() {
//        return dbHelper;
//    }



    public void stop() {
        sessionHelper.logout();
        webDriver.quit();
    }


}