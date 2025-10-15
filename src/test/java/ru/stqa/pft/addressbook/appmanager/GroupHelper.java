package ru.stqa.pft.addressbook.appmanager;

import ru.stqa.pft.addressbook.model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GroupHelper extends HelperBase {


    // Добавляем поле для драйвера
    protected WebDriver wd;
    public GroupHelper(WebDriver wd) {

        super(wd);
        this.wd = wd; // Сохраняем ссылку на драйвер
    }

    public void returnToGroupPage() {
        clickOnElement(By.linkText("group page"));
    }

    public void submitGroupCreation() {
        clickOnElement(By.name("submit"));
    }

    public void fillGroupForm(GroupData groupData) {
        typeIntoField(By.name("group_name"), groupData.getName());
        typeIntoField(By.name("group_header"), groupData.getHeader());
        typeIntoField(By.name("group_footer"), groupData.getFooter());
    }

    public void initGroupCreation() {
        clickOnElement(By.name("new"));
    }

    public void submitGroupDeletion() {
        clickOnElement(By.name("delete"));
    }

    public void selectGroup(int index) {
        webDriver.findElements(By.name("selected[]")).get(index).click();
    }

    public void initGroupModification() {
        clickOnElement(By.name("edit"));
    }

    public void submitGroupEditing() {
        clickOnElement(By.name("update"));
    }

//    public void createGroup(GroupData groupData) {
//        initGroupCreation();
//        fillGroupForm(groupData);
//        submitGroupCreation();
//        returnToGroupPage();
//    }
// Локальное определение метода click
    private void click(By locator) {
        wd.findElement(locator).click();
}
    // Метод для создания новой группы
    public void create(GroupData group) {
        // Переходим на страницу групп
        click(By.linkText("groups"));

        // Инициируем создание новой группы
        click(By.name("new"));

        // Заполняем форму группы
        fillGroupForm(group);

        // Подтверждаем создание группы
        submitGroupCreation();

        // Возвращаемся на страницу групп
        returnToGroupPage();}

    public void modify(GroupData group) {
        // Переходим на страницу групп
        click(By.linkText("groups"));

        // Выбираем группу для редактирования
        selectGroup(group.getId());

        // Инициируем редактирование выбранной группы
        initGroupModification();

        // Заполняем форму редактирования группы
        fillGroupForm(group);

        // Подтверждаем изменения
        submitGroupEditing();

        // Возвращаемся на страницу групп
        returnToGroupPage();
    }

    public int count() {
        return getGroupCount();
    }

    public boolean isGroupExist() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getGroupCount() {
        return webDriver.findElements(By.name("selected[]")).size();
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

    // Метод для получения всех групп из UI
//    public Groups all() {
//        List<GroupData> groups = new ArrayList<>();
//        List<WebElement> rows = webDriver.findElements(By.cssSelector("table tr"));
//        for (WebElement row : rows.subList(1, rows.size())) { // Пропускаем первую строку (заголовок таблицы)
//            String name = row.findElement(By.tagName("td")).getText();
//            int id = Integer.parseInt(row.findElement(By.tagName("a")).getAttribute("href").replaceAll("[^\\d]", ""));
//            groups.add(new GroupData(id, name, null, null)); // Создаём объект GroupData с известными полями
//        }
//        return new Groups(groups);
//    }

    public Groups all() {
        List<GroupData> groups = new ArrayList<>();
        List<WebElement> rows = webDriver.findElements(By.cssSelector("div#content div.group"));
        for (WebElement row : rows) {
            String name = row.getText();
            int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));
            groups.add(new GroupData(id, name, null, null));
        }
        return new Groups(groups);
    }
}
