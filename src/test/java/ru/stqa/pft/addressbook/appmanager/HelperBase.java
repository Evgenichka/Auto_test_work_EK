package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Random;

public class HelperBase {

    protected WebDriver wd;

    public HelperBase(WebDriver wd) {
        this.wd = wd;
    }

    private GroupData chooseRandomGroup(Groups groups) {
        Random random = new Random();
        int randomIndex = random.nextInt(groups.size());
        return groups.get(randomIndex);
    }

    protected void typeIntoField(By locator, String text) {
        if (text != null) {
            String existingText = wd.findElement(locator).getAttribute("value");
            if (!text.equals(existingText)) {
                wd.findElement(locator).clear();
                wd.findElement(locator).sendKeys(text);
            }
        }
    }

    protected void clickOnElement(By locator) {
        wd.findElement(locator).click();
    }

    public boolean isAlertPresent() {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException ignored) {
            return false;
        }
    }

    protected boolean isElementPresent(By locator) {
        try {
            wd.findElement(locator);
            return true;
        } catch (NoSuchElementException ignored) {
            return false;
        }
    }
}


//package ru.stqa.pft.addressbook.appmanager;
//
//import org.openqa.selenium.*;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import ru.stqa.pft.addressbook.model.GroupData;
//import ru.stqa.pft.addressbook.model.Groups;
//
//import java.time.Duration;
//import java.util.Random;
//
//
//public class HelperBase {
//
//    protected WebDriver webDriver;
//
////    public HelperBase(WebDriver webDriver) {
////        this.webDriver = webDriver;
////    }
//    protected WebDriver wd;
//
//    public HelperBase(WebDriver wd) {
//        this.wd = wd;
//    }
//    private GroupData chooseRandomGroup(Groups groups) {
//        Random random = new Random();
//        int randomIndex = random.nextInt(groups.size());
//        return groups.get(randomIndex);
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
//    }
//
//    protected void typeIntoField(By locator, String text) {
//        if (text != null) {
//            String existingText = webDriver.findElement(locator).getAttribute("value");
//            if (! text.equals(existingText)) {
//                webDriver.findElement(locator).clear();
//                webDriver.findElement(locator).sendKeys(text);
//            }
//        }
//    }
//
//    protected void clickOnElement(By locator) {
//        webDriver.findElement (locator).click();
//    }
//
//    public boolean isAlertPresent(){
//        try {
//            webDriver.switchTo().alert();
//            return true;
//        } catch (NoAlertPresentException e) {
//            return false;
//        }
//    }
//
//    protected boolean isElementPresent(By locator) {
//        try {
//            webDriver.findElement(locator);
//            return true;
//        } catch (NoSuchElementException noSuchElementException){
//            return false;
//        }
//    }
//
//
//
//
//}