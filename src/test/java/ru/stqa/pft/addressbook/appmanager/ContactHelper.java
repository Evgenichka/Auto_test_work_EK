package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.pft.addressbook.model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    // Добавляем метод для получения всех контактов
    public Collection<ContactData> all() {
        List<ContactData> contacts = new ArrayList<>();
        List<WebElement> rows = webDriver.findElements(By.cssSelector("#theform tbody tr"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() > 0) { // Исключаем первую строку с заголовком
                String firstName = cells.get(2).getText();
                String lastName = cells.get(1).getText();
                contacts.add(new ContactData().withFirstName(firstName).withLastName(lastName));
            }
        }
        return contacts;
    }

    // Добавляем метод для получения информации о контакте из формы редактирования
    public ContactData infoFromEditForm(ContactData contact) {
        editContactByIndex(contact); // Открываем форму редактирования первого контакта
        String firstName = webDriver.findElement(By.name("firstname")).getAttribute("value");
        String lastName = webDriver.findElement(By.name("lastname")).getAttribute("value");
        String address = webDriver.findElement(By.name("address")).getAttribute("value");
        String homePhone = webDriver.findElement(By.name("home")).getAttribute("value");
        String mobilePhone = webDriver.findElement(By.name("mobile")).getAttribute("value");
        String workPhone = webDriver.findElement(By.name("work")).getAttribute("value");
        cancel(); // Отменяем редактирование, чтобы вернуться назад
        return new ContactData()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withAddress(address)
                .withHomePhone(homePhone)
                .withMobilePhone(mobilePhone)
                .withWorkPhone(workPhone);
    }

    // Вспомогательные методы для открытия формы редактирования и отмены
    private void editContactByIndex(ContactData contact) {
        // Так как у нас нет id, открываем первый контакт
        webDriver.findElement(By.cssSelector("input[type='checkbox'][name='selected[]']")).click();
        webDriver.findElement(By.cssSelector("img[alt='Edit']")).click();
    }

    private void cancel() {
        webDriver.navigate().back();
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
        // Ждём появления элемента выпадающего списка
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(By.name("new_group")));

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

