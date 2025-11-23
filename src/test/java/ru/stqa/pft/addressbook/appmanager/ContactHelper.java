package ru.stqa.pft.addressbook.appmanager;


import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactHelper extends HelperBase {

    private Contacts contactsCache = null;

    public Contacts all(){
        if (contactsCache != null){
            return new Contacts(contactsCache);
        }

        contactsCache = new Contacts();
        List<WebElement> rows = webDriver.findElements(By.name("entry"));
        for (WebElement row: rows ) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            int id = Integer.parseInt(Objects.requireNonNull(cells.get(0).findElement(By.tagName("input")).getAttribute("value")));
            String lastName = cells.get(1).getText();
            String firstName = cells.get(2).getText();
            String address = cells.get(3).getText();
            String allEmails = cells.get(4).getText();
            String allPhones = cells.get(5).getText();
            contactsCache.add(new ContactData().withId(id).withFirstName(firstName)
                    .withLastName(lastName).withAddress(address)
                    .withAllEmails(allEmails).withAllPhones(allPhones));
        }
        return new Contacts(contactsCache);
    }

    public int count() {
        return webDriver.findElements(By.name("selected[]")).size();
    }

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

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
        typeIntoField(By.name("address"),contactData.getAddress());
        typeIntoField(By.name("email"), contactData.getFirstEmail());
        typeIntoField(By.name("email2"), contactData.getSecondEmail());
        typeIntoField(By.name("email3"), contactData.getThirdEmail());
        typeIntoField(By.name("home"), contactData.getHomePhone());
        typeIntoField(By.name("mobile"), contactData.getMobilePhone());
        typeIntoField(By.name("work"), contactData.getWorkPhone());
    //    attach(By.name("photo"), contactData.getPhoto());

        if (creationOrEditingForm){
            if (contactData.getGroup() !=null) {
                new Select(webDriver.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
            }
        } else {
            Assertions.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactDeletion() {
        clickOnElement(By.xpath("(//input[@value='Delete'])"));
    }

    public void selectContactById (int id){
        webDriver.findElement(By.cssSelector(String.format("input[value='%s']",id))).click();
    }

    public void initContactEditingById (int id){
        webDriver.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']",id))).click();
    }

    public ContactData infoFromEditForm (ContactData contactList){
        initContactEditingById(contactList.getId());
        String firstname = webDriver.findElement(By.name("firstname")).getAttribute("value");
        String lastname = webDriver.findElement(By.name("lastname")).getAttribute("value");
        //String middlename = webDriver.findElement(By.name("middlename")).getAttribute("value");
        //String nickname = webDriver.findElement(By.name("nickname")).getAttribute("value");
        // String company = webDriver.findElement(By.name("company")).getAttribute("value");
        String address = webDriver.findElement(By.name("address")).getText();
        String firstEmail = webDriver.findElement(By.name("email")).getAttribute("value");
        String secondEmail = webDriver.findElement(By.name("email2")).getAttribute("value");
        String thirdEmail = webDriver.findElement(By.name("email3")).getAttribute("value");
        String home = webDriver.findElement(By.name("home")).getAttribute("value");
        String mobile = webDriver.findElement(By.name("mobile")).getAttribute("value");
        String work = webDriver.findElement(By.name("work")).getAttribute("value");
        webDriver.navigate().back();
        return new ContactData().withId(contactList.getId()).withFirstName(firstname).withLastName(lastname)
                .withAddress(address).withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work)
                .withFirstEmail(firstEmail).withSecondEmail(secondEmail).withThirdEmail(thirdEmail);
    }

    public void acceptContactDeletion() {
        Alert contactDeletionAlert = webDriver.switchTo().alert();
        assertEquals("Delete 1 addresses?", contactDeletionAlert.getText());
        contactDeletionAlert.accept();
    }

    public void submitContactEditing () {
        clickOnElement(By.name("update"));
    }

    public void create(ContactData contact) {
        fillContactForm (contact, true);
        submitContactCreation();
        contactsCache = null;
        returnToHomePage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        initContactDeletion();
        acceptContactDeletion();
        contactsCache = null;
    }

    public void edit(ContactData contactDataForEditing) {
        initContactEditingById (contactDataForEditing.getId());
        fillContactForm(contactDataForEditing, false);
        submitContactEditing();
        contactsCache = null;
        returnToHomePage();
    }

    public boolean isContactExist() {
        return isElementPresent(By.name("selected[]"));
    }
}

