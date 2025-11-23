package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class HelperBase {

    protected WebDriver webDriver;

    public HelperBase(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    protected void typeIntoField(By locator, String text) {
        if (text != null) {
            String existingText = webDriver.findElement(locator).getAttribute("value");
            if (! text.equals(existingText)) {
                webDriver.findElement(locator).clear();
                webDriver.findElement(locator).sendKeys(text);
            }
        }
    }

    protected void clickOnElement(By locator) {
        webDriver.findElement (locator).click();
    }

    protected void attach(By locator, File file) {

        if (file!=null){
            webDriver.findElement(locator).sendKeys(file.getAbsolutePath());
        }
        webDriver.findElement (locator).click();
    }

    public boolean isAlertPresent(){
        try {
            webDriver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    protected boolean isElementPresent(By locator) {
        try {
            webDriver.findElement(locator);
            return true;
        } catch (NoSuchElementException noSuchElementException){
            return false;
        }
    }
}
