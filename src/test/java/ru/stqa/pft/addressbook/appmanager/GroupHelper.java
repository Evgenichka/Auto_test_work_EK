package ru.stqa.pft.addressbook.appmanager;

import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

public class GroupHelper extends HelperBase {

    public GroupHelper(WebDriver wd) {
        super(wd);
    }

    public void returnToGroupPage() {
        clickOnElement(By.linkText("group page"));
    }

    public void submitGroupCreation() {
        clickOnElement(By.name("submit"));
    }

    public void fillGroupForm(GroupData groupData) {
        typeIntoField(By.name("group_name"),groupData.getName());
        typeIntoField(By.name("group_header"),groupData.getHeader());
        typeIntoField(By.name("group_footer"),groupData.getFooter());
    }

    public void initGroupCreation() {
        clickOnElement(By.name("new"));
    }

    public void submitGroupDeletion() {
        clickOnElement(By.name("delete"));
    }

    public void selectGroupById(int id) {
        webDriver.findElement(By.cssSelector(String.format("input[value='%s']",id))).click();
    }

    public void initGroupEditing() {
        clickOnElement(By.name("edit"));
    }

    public void submitGroupEditing() {
        clickOnElement(By.name("update"));
    }

    public void create(GroupData groupData) {
        initGroupCreation();
        fillGroupForm(groupData);
        submitGroupCreation();
        groupCache = null;
        returnToGroupPage();
    }

    public void edit(GroupData group) {
        selectGroupById(group.getId());
        initGroupEditing();
        fillGroupForm(group);
        submitGroupEditing();
        groupCache = null;
        returnToGroupPage();
    }

    public void delete(GroupData group) {
        selectGroupById(group.getId());
        submitGroupDeletion();
        groupCache = null;
        returnToGroupPage();
    }

    public boolean isGroupExist() {
        return isElementPresent(By.name("selected[]"));
    }

    public int count() {
        return webDriver.findElements(By.name("selected[]")).size();
    }

    private Groups groupCache = null;

    public Groups all() {
        if (groupCache != null){
            return new Groups(groupCache);
        }

        groupCache = new Groups();
        List<WebElement> elementsGroups = webDriver.findElements(By.cssSelector("span.group"));
        for (WebElement element: elementsGroups){
            String name = element.getText();
            int id = Integer.parseInt(Objects.requireNonNull(element.findElement(By.tagName("input")).getAttribute("value")));
            groupCache.add(new GroupData().withId(id).withName(name));
        }
        return new Groups(groupCache);
    }
}
