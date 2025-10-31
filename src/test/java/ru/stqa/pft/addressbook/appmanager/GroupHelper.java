package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GroupHelper extends HelperBase {

    public GroupHelper(WebDriver wd) {
        super(wd);
    }

    private Groups groupCache = null;

    // Возвращает полный список групп
    public Groups all() {
        if (groupCache != null) {
            return new Groups(groupCache);
        }

        groupCache = new Groups();
        List<WebElement> elementsGroups = wd.findElements(By.cssSelector("span.group"));
        for (WebElement element : elementsGroups) {
            String name = element.getText();
            int id = Integer.parseInt(Objects.requireNonNull(element.findElement(By.tagName("input")).getAttribute("value")));
            groupCache.add(new GroupData().withId(id).withName(name));
        }
        return new Groups(groupCache);
    }

    // Возвращает количество групп
    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    // Создаёт новую группу
    public void create(GroupData groupData) {
        initGroupCreation();
        fillGroupForm(groupData);
        submitGroupCreation();
        groupCache = null;
        returnToGroupPage();
    }


    // Редактирует существующую группу
    public void edit(GroupData group) {
        selectGroupById(group.getId());
        initGroupEditing();
        fillGroupForm(group);
        submitGroupEditing();
        groupCache = null;
        returnToGroupPage();
    }

    // Удаляет группу
    public void delete(GroupData group) {
        selectGroupById(group.getId());
        submitGroupDeletion();
        groupCache = null;
        returnToGroupPage();
    }

    // Возвращает на страницу групп
    public void returnToGroupPage() {
        clickOnElement(By.linkText("group page"));
    }

    // Отправляет форму создания группы
    public void submitGroupCreation() {
        clickOnElement(By.name("submit"));
    }

    // Начинает процесс создания группы
    public void initGroupCreation() {
        clickOnElement(By.name("new"));
    }

    // Заполняет форму группы
    public void fillGroupForm(GroupData groupData) {
        typeIntoField(By.name("group_name"), groupData.getName());
        typeIntoField(By.name("group_header"), groupData.getHeader());
        typeIntoField(By.name("group_footer"), groupData.getFooter());
    }

    // Удаляет группу
    public void submitGroupDeletion() {
        clickOnElement(By.name("delete"));
    }

    // Инициирует редактирование группы
    public void initGroupEditing() {
        clickOnElement(By.name("edit"));
    }

    // Подтверждает редактирование группы
    public void submitGroupEditing() {
        clickOnElement(By.name("update"));
    }

    // Выбирает группу по идентификатору
    public void selectGroupById(int id) {
        wd.findElement(By.cssSelector(String.format("input[value='%s']", id))).click();
    }

    // Проверяет наличие хотя бы одной группы
    public boolean isGroupExist() {
        return isElementPresent(By.name("selected[]"));
    }

    // Вспомогательные методы для работы с элементами страницы
    protected void clickOnElement(By locator) {
        wd.findElement(locator).click();
    }

    protected void typeIntoField(By locator, String text) {
        WebElement element = wd.findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected boolean isElementPresent(By locator) {
        return !wd.findElements(locator).isEmpty();
    }

    // Очистка кэша групп
    public void resetCache() {
        groupCache = null;
    }

    public void modify(GroupData group) {
        selectGroupById(group.getId());
        initGroupEditing();
        fillGroupForm(group);
        submitGroupEditing();
        groupCache = null;
        returnToGroupPage();
    }
}







//package ru.stqa.pft.addressbook.appmanager;



//
//import org.openqa.selenium.JavascriptExecutor;
//import ru.stqa.pft.addressbook.model.GroupData;
//import ru.stqa.pft.addressbook.model.Groups;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.Random;
//
//public class GroupHelper extends HelperBase {
//
//    protected WebDriver wd;
//
//    public GroupHelper(WebDriver wd) {
//        super(wd);
//        this.wd = wd;
//    }
//
//    public void returnToGroupPage() {
//        clickOnElement(By.linkText("group page"));
//    }
//
//    public void submitGroupCreation() {
//        clickOnElement(By.name("submit"));
//    }
//
//    public void fillGroupForm(GroupData groupData) {
//        typeIntoField(By.name("group_name"), groupData.getName());
//        typeIntoField(By.name("group_header"), groupData.getHeader());
//        typeIntoField(By.name("group_footer"), groupData.getFooter());
//    }
//
//    public void initGroupCreation() {
//        clickOnElement(By.name("new"));
//    }
//
//    public void submitGroupDeletion() {
//        clickOnElement(By.name("delete"));
//    }
//
//    public void selectGroupById(int id) {
//        webDriver.findElement(By.cssSelector(String.format("input[value='%s']", id))).click();
//    }
//
//    public void selectGroup(int index) {
//        webDriver.findElements(By.name("selected[]")).get(index).click();
//    }
//
//    public void initGroupModification() {
//        clickOnElement(By.name("edit"));
//    }
//
//    public void submitGroupEditing() {
//        clickOnElement(By.name("update"));
//    }
//
//    public void create(GroupData group) {
//        click(By.linkText("groups"));
//        initGroupCreation();
//        fillGroupForm(group);
//        submitGroupCreation();
//        returnToGroupPage();
//    }
//
//    private void click(By locator) {
//        wd.findElement(locator).click();
//    }
//
////    public void modify(GroupData group) {
////        click(By.linkText("groups"));
////        selectGroup(group.getId());
////        initGroupModification();
////        fillGroupForm(group);
////        submitGroupEditing();
////        returnToGroupPage();
////    }
//
//    public int count() {
//        return getGroupCount();
//    }
//
//    public boolean isGroupExist() {
//        return isElementPresent(By.name("selected[]"));
//    }
//
//    public int getGroupCount() {
//        return webDriver.findElements(By.name("selected[]")).size();
//    }
//
//    public List<GroupData> getGroupList() {
//        List<GroupData> groupsGetGroupList = new ArrayList<>();
//        List<WebElement> elementsGroups = webDriver.findElements(By.cssSelector("span.group"));
//        for (WebElement element : elementsGroups) {
//            String name = element.getText();
//            int id = Integer.parseInt(Objects.requireNonNull(element.findElement(By.tagName("input")).getAttribute("value")));
//            GroupData group = new GroupData(id, name, null, null);
//            groupsGetGroupList.add(group);
//        }
//        return groupsGetGroupList;
//    }
//
//    private Groups groupCache = null;
//
//    public Groups all() {
//        if (groupCache != null) {
//            return new Groups(groupCache);
//        }
//
//        groupCache = new Groups();
//        List<WebElement> elementsGroups = webDriver.findElements(By.cssSelector("span.group"));
//        for (WebElement element : elementsGroups) {
//            String name = element.getText();
//            int id = Integer.parseInt(Objects.requireNonNull(element.findElement(By.tagName("input")).getAttribute("value")));
//            groupCache.add(new GroupData().withId(id).withName(name));
//        }
//        return new Groups(groupCache);
//    }
//
//    public void modify(GroupData group) {
//        // Переходим на страницу групп
//        click(By.linkText("groups"));
//
//        // Выбираем группу для редактирования
//        selectGroup(group.getId());
//
//        // Инициируем редактирование выбранной группы
//        initGroupModification();
//
//        // Заполняем форму редактирования группы
//        fillGroupForm(group);
//
//        // Подтверждаем изменения
//        submitGroupEditing();
//
//        // Возвращаемся на страницу групп
//        returnToGroupPage();
//    }
//
//    public void edit(GroupData group) {
//        selectGroupById(group.getId());
//        initGroupEditing();
//        fillGroupForm(group);
//        submitGroupEditing();
//        groupCache = null;
//        returnToGroupPage();
//    }
//
//    private void initGroupEditing() {
//
//            clickOnElement(By.name("edit"));
//
//    }
//
//
//    public void delete(GroupData group) {
//        selectGroupById(group.getId());
//        submitGroupDeletion();
//        groupCache = null;
//        returnToGroupPage();
//    }
//    private void waitForElementVisibility(By by, long timeoutInSeconds) {
//        WebDriverWait wait = new WebDriverWait(wd, timeoutInSeconds);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
//    }
//
//    private void waitForElementDisappearance(By by, long timeoutInSeconds) {
//        WebDriverWait wait = new WebDriverWait(wd, timeoutInSeconds);
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
//    }
//
//    private void clearAndType(By by, String value) {
//        WebElement element = wd.findElement(by);
//        element.clear();
//        element.sendKeys(value);
//    }
//
//    private boolean isGroupExistsWithName(String groupName) {
//        List<WebElement> groups = wd.findElements(By.cssSelector("span.group"));
//        for (WebElement group : groups) {
//            if (group.getText().equals(groupName)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private GroupData chooseRandomGroup(Groups groups) {
//        Random random = new Random();
//        return groups.iterator().next(random.nextInt(groups.size()));
//    }
//
//    private void waitForPageLoaded(long timeoutInSeconds) {
//        WebDriverWait wait = new WebDriverWait(wd, timeoutInSeconds);
//        wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
//    }
//
//    private void waitForDialogDisappear(long timeoutInSeconds) {
//        WebDriverWait wait = new WebDriverWait(wd, timeoutInSeconds);
//        wait.until(ExpectedConditions.alertIsNotPresent());
//    }}
