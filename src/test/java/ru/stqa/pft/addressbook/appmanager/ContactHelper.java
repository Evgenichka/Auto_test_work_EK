package ru.stqa.pft.addressbook.appmanager;

import ru.stqa.pft.addressbook.model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    // Методы для работы с элементами страницы

    protected void clickOnElement(By locator) {
        webDriver.findElement(locator).click();
    }

    protected void typeIntoField(By locator, String text) {
        WebElement element = webDriver.findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected boolean isElementPresent(By locator) {
        return !webDriver.findElements(locator).isEmpty();
    }

    // Методы для управления контактом

    public void returnToHomePage() {
        clickOnElement(By.linkText("home page"));
    }

    public void submitContactCreation() {
        clickOnElement(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void fillContactForm(ContactData contactData, boolean creationOrEditingForm) {
        typeIntoField(By.name("firstname"), contactData.getFirstName());
        typeIntoField(By.name("middlename"), contactData.getMiddleName());
        typeIntoField(By.name("lastname"), contactData.getLastName());
        typeIntoField(By.name("nickname"), contactData.getNickname());
        typeIntoField(By.name("company"), contactData.getCompany());
        typeIntoField(By.name("email"), contactData.getEmail());

        if (creationOrEditingForm) {
            if (contactData.getGroup() != null) {
                new Select(webDriver.findElement(By.name("new_group")))
                        .selectByVisibleText(contactData.getGroup());
            }
        } else {
            Assertions.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactDeletion() {
        clickOnElement(By.xpath("(//input[@value='Delete'])"));
    }

    public void selectContact(int index) {
        webDriver.findElements(By.name("selected[]")).get(index).click();
    }

    public void acceptContactDeletion() {
        Alert contactDeletionAlert = webDriver.switchTo().alert();
        Assertions.assertEquals("Delete 1 addresses?", contactDeletionAlert.getText());
        contactDeletionAlert.accept();
    }

    public void initContactEditing(int index) {
        webDriver.findElements(By.xpath("//img[@title='Edit']")).get(index).click();
    }

    public void submitContactEditing() {
        clickOnElement(By.name("update"));
    }

    public void createContact(ContactData contactData) {
        fillContactForm(contactData, true);
        submitContactCreation();
        returnToHomePage();
    }

    public boolean isContactExist() {
        return isElementPresent(By.name("selected[]"));
    }

     //Получение списка контактов

    public List<ContactData> getContactList() {
        List<ContactData> contactsGetContactList = new ArrayList<>();
        List<WebElement> elementsContacts = webDriver.findElements(By.cssSelector("tr[name='entry']"));
        for (WebElement element : elementsContacts) {
            String lastName = element.findElement(By.cssSelector("td:nth-of-type(2)")).getText();
            String firstName = element.findElement(By.cssSelector("td:nth-of-type(3)")).getText();
            ContactData contacts = new ContactData(firstName, null, lastName, null, null, null, null);
            contactsGetContactList.add(contacts);
        }
        return contactsGetContactList;
    }
}

//    public List<ContactData> getContactList() {
//        List<ContactData> contacts = new ArrayList<>();
//        List<WebElement> rows = webDriver.findElements(By.cssSelector("tr[name='entry']"));
//
//        for (WebElement row : rows) {
//            String firstName = row.findElement(By.cssSelector("td:nth-child(3)")).getText();
//            String middleName = ""; // Предположительно пустое значение
//            String lastName = row.findElement(By.cssSelector("td:nth-child(2)")).getText();
//            String nickname = ""; // Предположительно пустое значение
//            String company = ""; // Предположительно пустое значение
//            String email = ""; // Предположительно пустое значение
//            String group = ""; // Предположительно пустое значение
//
//            // Здесь можно добавить дополнительные поля, если они отображаются в таблице
//
//            ContactData contact = new ContactData(firstName, middleName, lastName, nickname, company, email, group);
//            contacts.add(contact);
//        }
//   return contacts;
//    }


    // Если контакты содержат данные
//        for (WebElement row : rows) {
//            String firstName = row.findElement(By.cssSelector("td:nth-child(3)")).getText();
//            String middleName = row.findElement(By.cssSelector("td:nth-child(4)")).getText();
//            String lastName = row.findElement(By.cssSelector("td:nth-child(2)")).getText();
//            String nickname = row.findElement(By.cssSelector("td:nth-child(5)")).getText();
//            String company = row.findElement(By.cssSelector("td:nth-child(6)")).getText();
//            String email = row.findElement(By.cssSelector("td:nth-child(7)")).getText();
//            String group = row.findElement(By.cssSelector("td:nth-child(8)")).getText();
//
//            ContactData contact = new ContactData(firstName, middleName, lastName, nickname, company, email, group);
//            contacts.add(contact);
//        }





//package ru.stqa.pft.addressbook.appmanager;
//
//import ru.stqa.pft.addressbook.model.ContactData;
//import org.junit.jupiter.api.Assertions;
//import org.openqa.selenium.Alert;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.Select;
//import ru.stqa.pft.addressbook.model.GroupData;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class ContactHelper extends HelperBase {
//
//    public List<ContactData> getContactList (){
//        List<ContactData> contactsGetContactList = new ArrayList <>();
//        List<WebElement> elementsContacts = webDriver.findElements(By.cssSelector("tr[name='entry']"));
//        for (WebElement element: elementsContacts ) {
//            String lastName = element.findElement(By.cssSelector("td:nth-of-type(2)")).getText();
//            String firstName = element.findElement(By.cssSelector("td:nth-of-type(3)")).getText();
//            ContactData contacts = new ContactData(firstName, null, lastName, null, null, null, null);
//            contactsGetContactList.add (contacts);
//        }
//        return contactsGetContactList;
//    };
//
//    public ContactHelper(WebDriver wd) {
//        super(wd);
//    }
//
//    public void returnToHomePage() {
//        clickOnElement(By.linkText("home page"));
//    }
//
//    public void submitContactCreation() {
//        clickOnElement(By.xpath("(//input[@name='submit'])[2]"));
//    }
//
//    public void fillContactForm(ContactData contactData, boolean creationOrEditingForm) {
//        typeIntoField(By.name("firstname"), contactData.getFirstName());
//        typeIntoField(By.name("middlename"), contactData.getMiddleName());
//        typeIntoField(By.name("lastname"), contactData.getLastName());
//        typeIntoField(By.name("nickname"), contactData.getNickname());
//        typeIntoField(By.name("company"), contactData.getCompany());
//        typeIntoField(By.name("email"), contactData.getEmail());
//
//        if (creationOrEditingForm) {
//            if (contactData.getGroup() != null) {
//                new Select(driver.findElement(By.name("new_group")))
//                        .selectByVisibleText(contactData.getGroup());
//            }
//        } else {
//            Assertions.assertFalse(isElementPresent(By.name("new_group")));
//        }
//    }
//
//    public void initContactDeletion() {
//        clickOnElement(By.xpath("(//input[@value='Delete'])"));
//    }
//
//    public void selectContact(int index) {
//        webDriver.findElements(By.name("selected[]")).get(index).click();
//    }
//
//    public void acceptContactDeletion() {
//        Alert contactDeletionAlert = webDriver.switchTo().alert();
//        assertEquals("Delete 1 addresses?", contactDeletionAlert.getText());
//        contactDeletionAlert.accept();
//    }
//
//    public void initContactEditing (int index) {
//        webDriver.findElements(By.xpath("//img[@title='Edit']")).get(index).click();
//        //clickOnElement(By.xpath("//img[@title='Edit']"));
//    }
//
//    public void submitContactEditing () {
//        clickOnElement(By.name("update"));
//    }
//
//    public void createContact(ContactData contactData) {
//        fillContactForm (contactData, true);
//        submitContactCreation();
//        returnToHomePage();
//    }
//
//    public boolean isContactExist() {
//        return isElementPresent(By.name("selected[]"));
//    }
//}

//package ru.stqa.pft.addressbook.appmanager;
//
//import ru.stqa.pft.addressbook.model.ContactData;
//import org.junit.jupiter.api.Assertions;
//import org.openqa.selenium.Alert;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.Select;
//import ru.stqa.pft.addressbook.model.GroupData;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class ContactHelper extends HelperBase {
//
//    public List<ContactData> getContactList() {
//        List<ContactData> contactsGetContactList = new ArrayList<>();
//        List<WebElement> elementsContacts = webDriver.findElements(By.cssSelector("tr[name='entry']"));
//        for (WebElement element : elementsContacts) {
//            String lastName = element.findElement(By.cssSelector("td:nth-of-type(2)")).getText();
//            String firstName = element.findElement(By.cssSelector("td:nth-of-type(3)")).getText();
//            ContactData contacts = new ContactData(firstName, null, lastName, null, null, null, null);
//            contactsGetContactList.add(contacts);
//        }
//        return contactsGetContactList;
//    }
//
//    public ContactHelper(WebDriver wd) {
//        super(wd);
//    }
//
//    public void returnToHomePage() {
//        clickOnElement(By.linkText("home page"));
//    }
//
//    public void submitContactCreation() {
//        clickOnElement(By.xpath("(//input[@name='submit'])[2]"));
//    }
//
//    public void fillContactForm(ContactData contactData, boolean creationOrEditingForm) {
//        typeIntoField(By.name("firstname"), contactData.getFirstName());
//        typeIntoField(By.name("middlename"), contactData.getMiddleName());
//        typeIntoField(By.name("lastname"), contactData.getLastName());
//        typeIntoField(By.name("nickname"), contactData.getNickname());
//        typeIntoField(By.name("company"), contactData.getCompany());
//        typeIntoField(By.name("email"), contactData.getEmail());
//
//        if (creationOrEditingForm) {
//            if (contactData.getGroup() != null) {
//                new Select(webDriver.findElement(By.name("new_group")))
//                        .selectByVisibleText(contactData.getGroup());
//            }
//        } else {
//            Assertions.assertFalse(isElementPresent(By.name("new_group")));
//        }
//    }
//
//    public void initContactDeletion() {
//        clickOnElement(By.xpath("(//input[@value='Delete'])"));
//    }
//
//    public void selectContact(int index) {
//        webDriver.findElements(By.name("selected[]")).get(index).click();
//    }
//
//    public void acceptContactDeletion() {
//        Alert contactDeletionAlert = webDriver.switchTo().alert();
//        assertEquals("Delete 1 addresses?", contactDeletionAlert.getText());
//        contactDeletionAlert.accept();
//    }
//
//    public void initContactEditing(int index) {
//        webDriver.findElements(By.xpath("//img[@title='Edit']")).get(index).click();
//    }
//
//    public void submitContactEditing() {
//        clickOnElement(By.name("update"));
//    }
//
//    public void createContact(ContactData contactData) {
//        fillContactForm(contactData, true);
//        submitContactCreation();
//        returnToHomePage();
//    }
//
//    public boolean isContactExist() {
//        return isElementPresent(By.name("selected[]"));
//    }
//}