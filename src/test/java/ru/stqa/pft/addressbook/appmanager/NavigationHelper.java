package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void contactPage() {
        if (isElementPresent(By.tagName("h1"))
                && webDriver.findElement(By.tagName("h1")).getText().equals("Edit / add address book entry")
                && isElementPresent(By.name("submit"))){
            return;
        }
        clickOnElement(By.linkText("add new"));
    }

    public void groupPage() {
        if (isElementPresent(By.tagName("h1"))
                && webDriver.findElement(By.tagName("h1")).getText().equals("Groups")
                && isElementPresent(By.name("new"))) {
            return;
        }
        clickOnElement(By.linkText("groups"));
    }

    public void homePage() {
        if (isElementPresent(By.id("maintable"))){
            return;
        }
        clickOnElement(By.linkText("home"));
    }
}