package ru.stqa.pft.addressbook.tests;

import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

    @BeforeEach
    public void prepareContactEditing() { // Новый метод с названием prepareContactEditing
        appManager.goTo().homePage();
        if (appManager.contact().all().isEmpty()) {
            appManager.goTo().contactPage();
            appManager.contact().create(new ContactData()
                    .withFirstName("TestFirstName").withMiddleName("TestMiddleName")
                    .withLastName("TestLastName").withNickname("TestNickname")
                    .withCompany("TestCompany").withAddress("TestAddress")
                    .withFirstEmail("TestEmail").withGroup("GroupName")
                    .withHomePhone("123456").withMobilePhone("+79854612312").withWorkPhone("123456789"));
        }
    }

    @Test
    public void testContactEditing() {
        Contacts beforeContactList = appManager.contact().all();
        ContactData editedContact = beforeContactList.iterator().next();
        ContactData contactDataForEditing = new ContactData()
                .withId(editedContact.getId())
                .withFirstName("Test1Name").withMiddleName("Test2Name")
                .withLastName("Test3Name").withNickname("Test4Nickname")
                .withCompany("Test5Company").withAddress("Test6Address")
                .withFirstEmail("test7@mail.com").withGroup("GroupName")
                .withHomePhone("").withMobilePhone("").withWorkPhone("");

        appManager.contact().edit(contactDataForEditing);
        assertThat(appManager.contact().count(), equalTo(beforeContactList.size()));
        Contacts afterContactList = appManager.contact().all();
        assertThat(afterContactList, equalTo(beforeContactList.without(editedContact).withAdded(contactDataForEditing)));
    }
}

//import ru.stqa.pft.addressbook.model.ContactData;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.Comparator;
//import java.util.List;

//public class ContactModificationTests extends TestBase {
//
//    @Test
//    public void contactEditing() {
//        // Создаем объект для редактирования с начальными данными
//        ContactData originalContact = new ContactData("OriginalFirstName", "", "OriginalLastName", "Original", "OriginalCompany", "original@mail.com", "");
//
//        // Создаем объект с изменёнными данными
//        ContactData updatedContact = new ContactData("UpdatedFirstName", "", "UpdatedLastName", "Updated", "UpdatedCompany", "updated@mail.com", "");
//
//        // Если контакта нет, добавляем оригинал
//        if (!appManager.getContactHelper().isContactExist()) {
//            appManager.getNavigationHelper().goToContactPage();
//            appManager.getContactHelper().createContact(originalContact);
//        }
//
//        // Получаем список контактов до изменений
//        List<ContactData> beforeContactList = appManager.getContactHelper().getContactList();
//
//        // Начинаем редактирование первого контакта
//        appManager.getContactHelper().initContactEditing(0); // Меняем первый контакт
//
//        // Заполняем форму с изменёнными данными
//        appManager.getContactHelper().fillContactForm(updatedContact, false);
//
//        // Подтверждаем изменения
//        appManager.getContactHelper().submitContactEditing();
//
//        // Возвращаемся на главную страницу
//        appManager.getContactHelper().returnToHomePage();
//
//        // Получаем список контактов после изменений
//        List<ContactData> afterContactList = appManager.getContactHelper().getContactList();
//
//        // Утверждаем, что количество контактов не изменилось
//        Assertions.assertEquals(afterContactList.size(), beforeContactList.size());
//
//        // Заменяем первоначальный контакт на обновлённый
//        beforeContactList.set(0, updatedContact); // Замещаем первым элементом
//
//        // Сравниваем контакты после сортировки
//        Comparator<? super ContactData> byLastAndFirstName = Comparator.comparing(ContactData::getLastName)
//                .thenComparing(ContactData::getFirstName);
//
//        beforeContactList.sort(byLastAndFirstName);
//        afterContactList.sort(byLastAndFirstName);
//
//        // Ограничиваем сравнение только теми полями, которые отображаются в таблице
//        Assertions.assertTrue(compareContactsIgnoringExtraFields(beforeContactList, afterContactList));
//    }
//
//    // Метод для сравнения объектов, учитывая только отображаемые поля
//    private boolean compareContactsIgnoringExtraFields(List<ContactData> list1, List<ContactData> list2) {
//        if (list1.size() != list2.size()) return false;
//
//        for (int i = 0; i < list1.size(); i++) {
//            ContactData contact1 = list1.get(i);
//            ContactData contact2 = list2.get(i);
//
//            // Сравниваем только те поля, которые отображаются в таблице
//            if (!contact1.getFirstName().equals(contact2.getFirstName()) ||
//                    !contact1.getLastName().equals(contact2.getLastName()) ||
//                    !contact1.getCompany().equals(contact2.getCompany()) ||
//                    !contact1.getEmail().equals(contact2.getEmail())) {
//                return false;
//            }
//        }
//        return true;
//    }
//}




//package ru.stqa.pft.addressbook.tests;
//
//
//import org.testng.annotations.BeforeMethod;
//import ru.stqa.pft.addressbook.model.ContactData;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import ru.stqa.pft.addressbook.model.Contacts;
//
//import java.util.Comparator;
//import java.util.List;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//public class ContactModificationTests extends TestBase {
//
//    @BeforeMethod
//    public static void contactEditingPreconditionsCheck() {
//        appManager.goTo().HomePage();
//        if (appManager.contact().all().isEmpty()){
//            appManager.goTo().contactPage();
//            appManager.contact().create(new ContactData()
//                    .withFirstName("TestFirstName").withMiddleName("TestMiddleName")
//                    .withLastName("TestLastName").withNickname("TestNickname")
//                    .withCompany("TestCompany").withAddress("TestAddress").
//                    withFirstEmail("TestEmail").withGroup("GroupName")
//                    .withHomePhone("440036").withMobilePhone("+79998887766").withWorkPhone("88412256621"));
//        }
//    }
//
//    @Test
//    public void contactEditing () {
//        Contacts beforeContactList = (Contacts) appManager.contact().all();
//        ContactData editedContact = beforeContactList.iterator().next();
//        Assertions.assertNotNull(editedContact);
//        ContactData contactDataForEditing = (new ContactData().withId(editedContact.getId())
//                .withFirstName("Test1Name").withMiddleName("Test2Name")
//                .withLastName("Test3Name").withNickname("Test4Nickname")
//                .withCompany("Test5Company").withAddress("Test6Address")
//                .withFirstEmail("test7@mail.com").withGroup("GroupName")
//                .withHomePhone("")).withMobilePhone("").withWorkPhone("");
//
//        appManager.contact().modify(contactDataForEditing);
//        assertThat(appManager.contact().count(), equalTo(beforeContactList.size()));
//        Contacts afterContactList = (Contacts) appManager.contact().all();
//        assertThat(afterContactList, equalTo(beforeContactList.without(editedContact).withAdded(contactDataForEditing)));
//    }
//}


//    @Test
//    public void contactEditing() {
//        // Создаем объект для редактирования с начальными данными
//        ContactData originalContact = new ContactData("OriginalFirstName", "OriginalMiddleName", "OriginalLastName", "Original", "OriginalCompany", "original@mail.com", null);
//
//        // Создаем объект с изменёнными данными
//        ContactData updatedContact = new ContactData("UpdatedFirstName", "UpdatedMiddleName", "UpdatedLastName", "Updated", "UpdatedCompany", "updated@mail.com", null);
//
//        // Если контакта нет, добавляем оригинал
//        if (!appManager.getContactHelper().isContactExist()) {
//            appManager.getNavigationHelper().goToContactPage();
//            appManager.getContactHelper().createContact(originalContact);
//        }
//
//        // Получаем список контактов до изменений
//        List<ContactData> beforeContactList = appManager.getContactHelper().getContactList();
//
//        // Начинаем редактирование первого контакта
//        appManager.getContactHelper().initContactEditing(0); // Меняем первый контакт
//
//        // Заполняем форму с изменёнными данными
//        appManager.getContactHelper().fillContactForm(updatedContact, false);
//
//        // Подтверждаем изменения
//        appManager.getContactHelper().submitContactEditing();
//
//        // Возвращаемся на главную страницу
//        appManager.getContactHelper().returnToHomePage();
//
//        // Получаем список контактов после изменений
//        List<ContactData> afterContactList = appManager.getContactHelper().getContactList();
//
//        // Утверждаем, что количество контактов не изменилось
//        Assertions.assertEquals(afterContactList.size(), beforeContactList.size());
//
//        // Заменяем первоначальный контакт на обновлённый
//        beforeContactList.set(0, updatedContact); // Замещаем первым элементом
//
//        // Сравниваем контакты после сортировки
//        Comparator<? super ContactData> byLastAndFirstName = Comparator.comparing(ContactData::getLastName)
//                .thenComparing(ContactData::getFirstName);
//
//        beforeContactList.sort(byLastAndFirstName);
//        afterContactList.sort(byLastAndFirstName);
//
//        // Утверждаем, что списки равны
//        Assertions.assertEquals(beforeContactList, afterContactList);
//    }
//
//
//
//}