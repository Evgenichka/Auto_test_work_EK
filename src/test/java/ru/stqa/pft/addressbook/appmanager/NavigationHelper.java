package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

//   /

    public void GroupPage() {
        if (isElementPresent(By.tagName("h1"))
                && wd.findElement(By.tagName("h1")).getText().equals("Groups")
                && isElementPresent(By.name("new"))) {
            return;
        }
        clickOnElement(By.linkText("groups"));
    }

    public void HomePage() {
        if (isElementPresent(By.id("maintable"))){
            return;
        }
        clickOnElement(By.linkText("home"));
    }

    public void groupPage() {
        wd.navigate().to("страница-групп-вашего-приложения");
    }


    public void contactPage() {
        if (isElementPresent(By.tagName("h1"))
                && wd.findElement(By.tagName("h1")).getText().equals("Edit / add address book entry")
                && isElementPresent(By.name("submit"))){
            return;
    }
            clickOnElement(By.linkText("add new"));
        }
    }


