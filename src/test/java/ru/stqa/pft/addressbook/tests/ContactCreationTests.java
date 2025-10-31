package ru.stqa.pft.addressbook.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.stqa.pft.addressbook.tests.TestBase.appManager;

public class ContactCreationTests extends TestBase {

    @BeforeEach
    public void goToHomePage() {
        appManager.goTo().HomePage();
    }

    @Test
    public void testContactCreation() {
        Contacts beforeContactList = (Contacts) appManager.contact().all();
        ContactData contactThatWillBeCreated = new ContactData()
                .withFirstName("Иван")
                .withMiddleName("Иванович")
                .withLastName("Иванов")
                .withNickname("ivanov")
                .withCompany("Яндекс")
                .withAddress("Москва, ул. Тверская, д. 1")
                .withFirstEmail("ivan@example.com")
                .withSecondEmail("ivan.second@example.com")
                .withThirdEmail("ivan.third@example.com")
                .withGroup("Тестовая группа")
                .withHomePhone("+7 (495) 123-45-67")
                .withMobilePhone("+7 (985) 461-23-12")
                .withWorkPhone("+7 (495) 987-65-43");

        appManager.goTo().contactPage();
        appManager.contact().create(contactThatWillBeCreated);

        assertThat(appManager.contact().count(), equalTo(beforeContactList.size() + 1));
        Contacts afterContactList = (Contacts) appManager.contact().all();

        OptionalInt maxIdOptional = afterContactList.stream().filter(Objects::nonNull)
                .mapToInt(ContactData::getId)
                .max();

        int maxId = maxIdOptional.orElseThrow(() -> new RuntimeException("No valid IDs found."));

        assertThat(afterContactList, equalTo(beforeContactList.withAdded(contactThatWillBeCreated.withId(maxId))));
    }
}





//package ru.stqa.pft.addressbook.tests;
//
//import org.testng.annotations.BeforeMethod;
//import ru.stqa.pft.addressbook.model.ContactData;
//import org.junit.jupiter.api.Test;
//import ru.stqa.pft.addressbook.model.Contacts;
//
//import java.util.Objects;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static ru.stqa.pft.addressbook.tests.TestBase.appManager;
//
//public class ContactCreationTests extends TestBase {
//
//    @BeforeMethod
//    public void contactCreation() {
//        appManager.goTo().HomePage();
//    }
//
//
//
//    @Test
//    public void contactCreation() {
//        Contacts beforeContactList = (Contacts) appManager.contact().all();
//        ContactData contactThatWillBeCreated = new ContactData()
//                .withFirstName("TestFirstName").withMiddleName("TestMiddleName")
//                .withLastName("TestLastName").withNickname("TestNickname")
//                .withCompany("TestCompany").withAddress("TestAddress")
//                .withFirstEmail("TestEmail").withSecondEmail("TestEmailSecond").withThirdEmail("TestEmailThird")
//                .withGroup("GroupName").withHomePhone("123456").withMobilePhone("+79854612312").withWorkPhone("123456789");
//
//        appManager.goTo().contactPage();
//        appManager.contact().create(contactThatWillBeCreated);
//
//        assertThat(appManager.contact().count(), equalTo(beforeContactList.size() + 1));
//        Contacts afterContactList = (Contacts) appManager.contact().all();
//
//        assertThat(afterContactList, equalTo(beforeContactList.withAdded(contactThatWillBeCreated.withId(afterContactList.stream().filter(Objects::nonNull)
//                .mapToInt(ContactData::getId).max().getAsInt()))));
//    }
//}
//    @Test
//    public void contactCreation() {
//        List<ContactData> beforeContactList = appManager.getContactHelper().getContactList();
//        appManager.getNavigationHelper().goToContactPage();
//        ContactData contactForList = new ContactData(
//                "TestFirstName",
//                "TestMiddleName",
//                "TestLastName",
//                "TestNickname",
//                "TestCompany",
//                "TestEmail",
//                "TestGroupName"
//        );
//        appManager.getContactHelper().fillContactForm(contactForList, true);
//        appManager.getContactHelper().submitContactCreation();
//        appManager.getContactHelper().returnToHomePage();
//        List<ContactData> afterContactList = appManager.getContactHelper().getContactList();
//        Assertions.assertEquals(afterContactList.size(), beforeContactList.size() + 1);
//
//        beforeContactList.add(contactForList);
//        // Измените comparator, добавив методы доступа
//        Comparator<? super ContactData> byLastAndFirstName = Comparator.comparing(ContactData::getLastName)
//                .thenComparing(ContactData::getFirstName);
//
//        beforeContactList.sort(byLastAndFirstName);
//        afterContactList.sort(byLastAndFirstName);
//        Assertions.assertEquals(beforeContactList, afterContactList);
//    }
//}
