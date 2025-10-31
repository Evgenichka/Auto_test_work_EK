package ru.stqa.pft.addressbook.appmanager;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    private Contacts contactsCache = null;

    // Возвращает полный список контактов
    public List<ContactData> all() {
        if (contactsCache != null && !contactsCache.isEmpty()) {
            return new ArrayList<>(contactsCache); // Возвращаем копию кэшированных данных
        }

        contactsCache = new Contacts();
        List<WebElement> rows = wd.findElements(By.name("entry"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (!row.equals(rows.get(0)) && cells.size() >= 6) {
                try {
                    int id = Integer.parseInt(
                            Objects.requireNonNull(cells.get(0).findElement(By.tagName("input"))
                                    .getAttribute("value")));

                    String lastName = cells.get(1).getText();
                    String firstName = cells.get(2).getText();
                    String address = cells.get(3).getText();
                    String allEmails = cells.get(4).getText();
                    String allPhones = cells.get(5).getText();

                    contactsCache.add(new ContactData()
                            .withFirstName(firstName)
                            .withLastName(lastName)
                            .withAddress(address)
                            .withAllEmails(allEmails)
                            .withId(id)
                            .withAllPhones(allPhones));
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка преобразования ID: " + e.getMessage());
                }
            }
        }
        return new ArrayList<>(contactsCache); // Возврат копии списка контактов
    }

    // Получает подробную информацию о конкретном контакте из формы редактирования
    public ContactData infoFromEditForm(ContactData contact) {
        openEditFormForContact(contact);
        String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String homePhone = wd.findElement(By.name("home")).getAttribute("value");
        String mobilePhone = wd.findElement(By.name("mobile")).getAttribute("value");
        String workPhone = wd.findElement(By.name("work")).getAttribute("value");
        String firstEmail = wd.findElement(By.name("email")).getAttribute("value");
        String secondEmail = wd.findElement(By.name("email2")).getAttribute("value");
        String thirdEmail = wd.findElement(By.name("email3")).getAttribute("value");
        cancel(); // Закрытие формы редактирования
        return new ContactData()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withAddress(address)
                .withHomePhone(homePhone)
                .withMobilePhone(mobilePhone)
                .withWorkPhone(workPhone)
                .withFirstEmail(firstEmail)
                .withSecondEmail(secondEmail)
                .withThirdEmail(thirdEmail);
    }

    // Открывает форму редактирования конкретного контакта
    private void openEditFormForContact(ContactData contact) {
        selectContactById(contact.getId()); // Выбираем нужный контакт
        wd.findElement(By.cssSelector(String.format("a[href*='%s/edit.php']", contact.getId()))).click();
    }

    // Переходит обратно на главную страницу
    public void returnToHomePage() {
        clickOnElement(By.linkText("home page"));
    }

    // Подтверждение удаления контакта
    public void confirmContactDeletion() {
        Alert alert = wd.switchTo().alert();
        alert.accept();
    }

    // Удаляет выбранный контакт
    public void deleteSelectedContact() {
        clickOnElement(By.xpath("//input[@value='Delete']"));
        confirmContactDeletion();
    }

    // Заполняет форму создания нового контакта
    public void fillContactForm(ContactData contactData, boolean creationOrEditingForm) {
        typeIntoField(By.name("firstname"), contactData.getFirstName());
        typeIntoField(By.name("lastname"), contactData.getLastName());
        typeIntoField(By.name("address"), contactData.getAddress());
        typeIntoField(By.name("home"), contactData.getHomePhone());
        typeIntoField(By.name("mobile"), contactData.getMobilePhone());
        typeIntoField(By.name("work"), contactData.getWorkPhone());
        typeIntoField(By.name("email"), contactData.getFirstEmail());
        typeIntoField(By.name("email2"), contactData.getSecondEmail());
        typeIntoField(By.name("email3"), contactData.getThirdEmail());

        if (creationOrEditingForm) {
            if (contactData.getGroup() != null) {
                new Select(wd.findElement(By.name("new_group")))
                        .selectByVisibleText(contactData.getGroup());
            }
        }
    }

    // Отправляет данные контакта
    public void submitContactCreation() {
        clickOnElement(By.xpath("//input[@name='submit']"));
    }

    // Сохраняет изменения при редактировании контакта
    public void submitContactUpdate() {
        clickOnElement(By.name("update"));
    }

    // Проверяет существование хотя бы одного контакта
    public boolean isAnyContactPresent() {
        return isElementPresent(By.name("selected[]"));
    }

    // Выбирайте контакт по индексу
    public void selectContactByIndex(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    // Выбирайте контакт по уникальному идентификатору
    public void selectContactById(int id) {
        wd.findElement(By.cssSelector(String.format("input[value='%s']", id))).click();
    }

    // Завершение процесса редактирования путем возврата на предыдущую страницу
    private void cancel() {
        wd.navigate().back();
    }

    // Создание нового контакта
    public void createContact(ContactData contactData) {
        fillContactForm(contactData, true);
        submitContactCreation();
        returnToHomePage();
    }

    // Редактирование существующего контакта
    public void modify(ContactData contactDataForEditing) {
        initContactEditingById(contactDataForEditing.getId());
        fillContactForm(contactDataForEditing, false);
        submitContactEditing();
        returnToHomePage();
    }

    // Удаление выбранного контакта
    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        initContactDeletion();
        acceptContactDeletion();
        returnToHomePage();
    }

    // Вспомогательные методы для работы с элементами страницы
    protected void clickOnElement(By locator) {
        wd.findElement(locator).click();
    }

    protected void typeIntoField(By locator, String text) {
        WebElement element = wd.findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected boolean isElementPresent(By locator) {
        return !wd.findElements(locator).isEmpty();
    }

    // Чистка кеша после операций над списком контактов
    public void resetCache() {
        contactsCache = null;
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public void initContactDeletion() {
        clickOnElement(By.xpath("(//input[@value='Delete'])"));
    }

    public void initContactEditingById(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public void acceptContactDeletion() {
        Alert contactDeletionAlert = wd.switchTo().alert();
        assertEquals("Delete 1 addresses?", contactDeletionAlert.getText());
        contactDeletionAlert.accept();
    }

    public void submitContactEditing() {
        clickOnElement(By.name("update"));
    }

    public void create(ContactData contact) {
        fillContactForm (contact, true);
        submitContactCreation();
        contactsCache = null;
        returnToHomePage();
    }
}




//package ru.stqa.pft.addressbook.appmanager;
//
//import org.junit.jupiter.api.Assertions;
//import org.openqa.selenium.*;
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import ru.stqa.pft.addressbook.model.ContactData;
//import ru.stqa.pft.addressbook.model.Contacts;
//
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class ContactHelper extends HelperBase {
//
//    public ContactHelper(WebDriver wd) {
//        super(wd);
//    }
//
//    private Contacts contactsCache = null;
//
//    // Возвращает полный список контактов
//    public List<ContactData> all() {
//        if (contactsCache != null && !contactsCache.isEmpty()) {
//            return new ArrayList<>(contactsCache); // Возвращаем копию кэшированных данных
//        }
//
//        contactsCache = new Contacts();
//        List<WebElement> rows = webDriver.findElements(By.cssSelector("#theform tbody tr"));
//
//        for (WebElement row : rows) {
//            List<WebElement> cells = row.findElements(By.tagName("td"));
//
//            // Пропускаем первую строку с заголовками и проверяем количество ячеек
//            if (!row.equals(rows.get(0)) && cells.size() >= 6) {
//                try {
//                    int id = Integer.parseInt(
//                            Objects.requireNonNull(cells.get(0).findElement(By.tagName("input"))
//                                    .getAttribute("value")));
//
//                    String lastName = cells.get(1).getText();
//                    String firstName = cells.get(2).getText();
//                    String address = cells.get(3).getText();
//                    String allEmails = cells.get(4).getText();
//                    String allPhones = cells.get(5).getText();
//
//                    contactsCache.add(new ContactData()
//                            .withFirstName(firstName)
//                            .withLastName(lastName)
//                            .withAddress(address)
//                            .withAllEmails(allEmails)
//                            .withId(id)
//                            .withAllPhones(allPhones));
//                } catch (NumberFormatException e) {
//                    System.err.println("Ошибка преобразования ID: " + e.getMessage());
//                }
//            }
//        }
//
//        return new ArrayList<>(contactsCache); // Возврат копии списка контактов
//    }
//
//    // Получает подробную информацию о конкретном контакте из формы редактирования
//    public ContactData infoFromEditForm(ContactData contact) {
//        openEditFormForContact(contact);
//        String firstName = webDriver.findElement(By.name("firstname")).getAttribute("value");
//        String lastName = webDriver.findElement(By.name("lastname")).getAttribute("value");
//        String address = webDriver.findElement(By.name("address")).getAttribute("value");
//        String homePhone = webDriver.findElement(By.name("home")).getAttribute("value");
//        String mobilePhone = webDriver.findElement(By.name("mobile")).getAttribute("value");
//        String workPhone = webDriver.findElement(By.name("work")).getAttribute("value");
//        cancel(); // Закрытие формы редактирования
//        return new ContactData()
//                .withFirstName(firstName)
//                .withLastName(lastName)
//                .withAddress(address)
//                .withHomePhone(homePhone)
//                .withMobilePhone(mobilePhone)
//                .withWorkPhone(workPhone);
//    }
//
//    // Открывает форму редактирования конкретного контакта
//    private void openEditFormForContact(ContactData contact) {
//        selectContactById(contact.getId()); // Выбираем нужный контакт
//        webDriver.findElement(By.cssSelector(String.format("a[href*='%s/edit.php']", contact.getId()))).click();
//    }
//
//    // Переходит обратно на главную страницу
//    public void returnToHomePage() {
//        clickOnElement(By.linkText("home page"));
//    }
//
//    // Подтверждение удаления контакта
//    public void confirmContactDeletion() {
//        Alert alert = webDriver.switchTo().alert();
//        alert.accept();
//    }
//
//    // Удаляет выбранный контакт
//    public void deleteSelectedContact() {
//        clickOnElement(By.xpath("//input[@value='Delete']"));
//        confirmContactDeletion();
//    }
//
//    // Заполняет форму создания нового контакта
//    public void fillContactForm(ContactData contactData, boolean creationOrEditingForm) {
//        typeIntoField(By.name("firstname"), contactData.getFirstName());
//        typeIntoField(By.name("lastname"), contactData.getLastName());
//        typeIntoField(By.name("address"), contactData.getAddress());
//        typeIntoField(By.name("home"), contactData.getHomePhone());
//        typeIntoField(By.name("mobile"), contactData.getMobilePhone());
//        typeIntoField(By.name("work"), contactData.getWorkPhone());
//        typeIntoField(By.name("email"), contactData.getFirstEmail());
//        typeIntoField(By.name("email2"), contactData.getSecondEmail());
//        typeIntoField(By.name("email3"), contactData.getThirdEmail());
//        //typeIntoField(By.name("email"), contactData.getEmail());
//
//        if (creationOrEditingForm) {
//            if (contactData.getGroup() != null) {
//                new Select(webDriver.findElement(By.name("new_group")))
//                        .selectByVisibleText(contactData.getGroup());
//            }
//        }  else {
//            Assertions.assertFalse(isElementPresent(By.name("new_group")));
//        }
//    }
//
//    // Отправляет данные контакта
//    public void submitContactCreation() {
//        clickOnElement(By.xpath("//input[@name='submit']"));
//    }
//
//    // Сохраняет изменения при редактировании контакта
//    public void submitContactUpdate() {
//        clickOnElement(By.name("update"));
//    }
//
//    // Проверяет существование хотя бы одного контакта
//    public boolean isAnyContactPresent() {
//        return isElementPresent(By.name("selected[]"));
//    }
//
//    // Выберите контакт по индексу
//    public void selectContactByIndex(int index) {
//        webDriver.findElements(By.name("selected[]")).get(index).click();
//    }
//
//    // Выберите контакт по уникальному идентификатору
//    public void selectContactById(int id) {
//        webDriver.findElement(By.cssSelector(String.format("input[value='%s']", id))).click();
//    }
//
//    // Завершение процесса редактирования путем возврата на предыдущую страницу
//    private void cancel() {
//        webDriver.navigate().back();
//    }
//
//    // Создание нового контакта
//    public void createContact(ContactData contactData) {
//        fillContactForm(contactData, true);
//        submitContactCreation();
//        returnToHomePage();
//    }
//
////    // Редактирование существующего контакта
////    public void updateContact(ContactData updatedContact) {
////        selectContactById(updatedContact.getId());
////        openEditFormForContact(updatedContact);
////        fillContactForm(updatedContact, false);
////        submitContactUpdate();
////        returnToHomePage();
////    }
////
////    // Удаление выбранного контакта
////    public void removeContact(int id) {
////        selectContactById(id);
////        deleteSelectedContact();
////        returnToHomePage();
////    }
//
//    // Базовые вспомогательные методы для работы с веб-элементами
//
//    protected void clickOnElement(By locator) {
//        webDriver.findElement(locator).click();
//    }
//
//    protected void typeIntoField(By locator, String text) {
//        WebElement element = webDriver.findElement(locator);
//        element.clear();
//        element.sendKeys(text);
//    }
//
//    protected boolean isElementPresent(By locator) {
//        return !webDriver.findElements(locator).isEmpty();
//    }
//
//    // Дополнительные методы для ожидания элементов
//
//    protected void waitUntilElementIsClickable(By locator) {
//        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
//        wait.until(ExpectedConditions.elementToBeClickable(locator));
//    }
//
//    // Чистка кеша после операций над списком контактов
//    public void resetCache() {
//        contactsCache = null;
//    }
//
//    public int count() {
//        return webDriver.findElements(By.name("selected[]")).size();
//    }
//
//
////    public void create(ContactData contact) {
////        fillContactForm (contact, true);
////        submitContactCreation();
////        contactsCache = null;
////        returnToHomePage();
////    }
//
//    public void modify(ContactData contactDataForEditing) {
//        initContactEditingById (contactDataForEditing.getId());
//        fillContactForm(contactDataForEditing, false);
//        submitContactEditing();
//        contactsCache = null;
//        returnToHomePage();
//    }
//
//    public void delete(ContactData contact) {
//        selectContactById(contact.getId());
//        initContactDeletion();
//        acceptContactDeletion();
//        contactsCache = null;
//    }
//    public void initContactDeletion() {
//        clickOnElement(By.xpath("(//input[@value='Delete'])"));
//    }
//
//
//
//    public void initContactEditingById (int id){
//        webDriver.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']",id))).click();
//    }
//
//
//
//    public void acceptContactDeletion() {
//        Alert contactDeletionAlert = webDriver.switchTo().alert();
//        assertEquals("Delete 1 addresses?", contactDeletionAlert.getText());
//        contactDeletionAlert.accept();
//    }
//
//    public void submitContactEditing () {
//        clickOnElement(By.name("update"));
//    }
//
//
//}

