package ru.stqa.pft.addressbook.appmanager;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


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
        } else if (selectedBrowser.equals(Browser.EDGE)) {
            webDriver = new EdgeDriver();
        }

        sessionHelper = new SessionHelper(webDriver);
        navigationHelper = new NavigationHelper(webDriver);
        groupHelper = new GroupHelper(webDriver);
        contactHelper = new ContactHelper(webDriver);
        webDriver.get("http://localhost/addressbook");
        sessionHelper.login("admin","secret");
    }

//

//    public void verifyGroupListInUI() {
//        if (Boolean.getBoolean("verifyUI")) {
//            // Получаем группы из UI до изменений
//            Groups beforeChange = groupHelper.all();
//
//            // Выполняем какое-либо действие (например, создание или модификацию группы)
//            // Это действие зависит от вашего сценария тестирования
//
//            // Получаем группы из UI после изменений
//            Groups afterChange = groupHelper.all();
//
//            // Преобразуем множества в списки для последовательного сравнения
//            List<GroupData> beforeList = new ArrayList<>(beforeChange);
//            List<GroupData> afterList = new ArrayList<>(afterChange);
//
//            // Сортируем списки по имени группы для надежного сравнения
//            Collections.sort(beforeList, Comparator.comparing(GroupData::getName));
//            Collections.sort(afterList, Comparator.comparing(GroupData::getName));
//
//            // Проверяем, что размер списков совпадает
//            assertThat(afterList.size(), equalTo(beforeList.size()));
//
//            // Проверяем, что каждая группа в списке после изменений соответствует группе до изменений
//            for (int i = 0; i < beforeList.size(); i++) {
//                GroupData before = beforeList.get(i);
//                GroupData after = afterList.get(i);
//
//                // Сравниваем основные свойства группы
//                assertThat(after.getName(), equalTo(before.getName()));
//                assertThat(after.getHeader(), equalTo(before.getHeader()));
//                assertThat(after.getFooter(), equalTo(before.getFooter()));
//            }
//        }
//    }


    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public ContactHelper contact() {
        return contactHelper;
    }

    public GroupHelper group() {
        return groupHelper;
    }

    public void stop() {
        sessionHelper.logout();
        webDriver.quit();
    }


    public void verifyGroupListInUI() {
        List<GroupData> uiGroups = getGroupList();
        List<String> uiNames = extractNames(uiGroups);
        List<String> expectedNames = extractNames(expectedGroups());
        assertThat(uiNames, equalTo(expectedNames));
    }



    public List<GroupData> getGroupList() {
        List<GroupData> groupsGetGroupList = new ArrayList <>();
        List<WebElement> elementsGroups = webDriver.findElements(By.cssSelector("span.group"));
        for (WebElement element: elementsGroups){
            String name = element.getText();
            int id = Integer.parseInt(Objects.requireNonNull(element.findElement(By.tagName("input")).getAttribute("value")));
            GroupData group = new GroupData(id, name, null, null);
            groupsGetGroupList.add(group);
        }
        return groupsGetGroupList;
    }

    private List<String> extractNames(@org.jetbrains.annotations.NotNull List<GroupData> groups) {
        return groups.stream().map(GroupData::getName).collect(Collectors.toList());
    }

    @Contract(value = " -> new", pure = true)
    private @NotNull List<GroupData> expectedGroups() {
        return new ArrayList<>();
    }
}