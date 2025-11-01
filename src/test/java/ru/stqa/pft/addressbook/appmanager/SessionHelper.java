package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionHelper extends HelperBase {

    public SessionHelper(WebDriver wd) {
        super(wd);
    }

    public void login(String username, String password) {
        typeIntoField(By.name("user"),username);
        typeIntoField(By.name("pass"), password);
        clickOnElement(By.xpath("//input[@value='Login']"));
    }

    public void logout() {
        clickOnElement(By.linkText("Logout"));
    }
}



