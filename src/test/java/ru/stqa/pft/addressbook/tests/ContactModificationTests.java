package ru.stqa.pft.addressbook.tests;


import ru.stqa.pft.addressbook.model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @Test
    public void contactEditing() {
        // Создаем объект для редактирования с начальными данными
        ContactData originalContact = new ContactData("OriginalFirstName", "OriginalMiddleName", "OriginalLastName", "Original", "OriginalCompany", "original@mail.com", null);

        // Создаем объект с изменёнными данными
        ContactData updatedContact = new ContactData("UpdatedFirstName", "UpdatedMiddleName", "UpdatedLastName", "Updated", "UpdatedCompany", "updated@mail.com", null);

        // Если контакта нет, добавляем оригинал
        if (!appManager.getContactHelper().isContactExist()) {
            appManager.getNavigationHelper().goToContactPage();
            appManager.getContactHelper().createContact(originalContact);
        }

        // Получаем список контактов до изменений
        List<ContactData> beforeContactList = appManager.getContactHelper().getContactList();

        // Начинаем редактирование первого контакта
        appManager.getContactHelper().initContactEditing(0); // Меняем первый контакт

        // Заполняем форму с изменёнными данными
        appManager.getContactHelper().fillContactForm(updatedContact, false);

        // Подтверждаем изменения
        appManager.getContactHelper().submitContactEditing();

        // Возвращаемся на главную страницу
        appManager.getContactHelper().returnToHomePage();

        // Получаем список контактов после изменений
        List<ContactData> afterContactList = appManager.getContactHelper().getContactList();

        // Утверждаем, что количество контактов не изменилось
        Assertions.assertEquals(afterContactList.size(), beforeContactList.size());

        // Заменяем первоначальный контакт на обновлённый
        beforeContactList.set(0, updatedContact); // Замещаем первым элементом

        // Сравниваем контакты после сортировки
        Comparator<? super ContactData> byLastAndFirstName = Comparator.comparing(ContactData::getLastName)
                .thenComparing(ContactData::getFirstName);

        beforeContactList.sort(byLastAndFirstName);
        afterContactList.sort(byLastAndFirstName);

        // Утверждаем, что списки равны
        Assertions.assertEquals(beforeContactList, afterContactList);
    }



}