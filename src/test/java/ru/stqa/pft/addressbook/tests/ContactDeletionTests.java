package ru.stqa.pft.addressbook.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

    private final String GROUP_NAME = "HP";

    @BeforeEach
    public void prepareContactDeletion() {
        appManager.goTo().homePage();

        // Проверка существования группы, если нет - создаем
        Groups allGroups = appManager.group().all();
        boolean groupExists = allGroups.stream()
                .anyMatch(g -> g.getName().equals(GROUP_NAME));
        if (!groupExists) {
            appManager.goTo().groupPage();
            appManager.group().create(new GroupData().withName(GROUP_NAME));
        }

        // Проверка существования контакта, если нет - создаем
        if (appManager.contact().all().isEmpty()) {
            appManager.goTo().contactPage();
            ContactData newContact = new ContactData()
                    .withFirstName("Test1")
                    .withMiddleName("TestMiddleName1")
                    .withLastName("Test1")
                    .withNickname("TestNickname1")
                    .withCompany("TestCompany1")
                    .withAddress("TestAddress1")
                    .withFirstEmail("TestEmail1")
                    .withHomePhone("123456")
                    .withMobilePhone("+79854612312")
                    .withWorkPhone("123456789")
                    .withGroup(GROUP_NAME);

            appManager.contact().create(newContact);
        }
    }

    @Test
    public void testContactDeletion() {
        Contacts beforeContactList = appManager.contact().all();
        ContactData deletedContact = beforeContactList.iterator().next();

        Assertions.assertNotNull(deletedContact);
        appManager.contact().delete(deletedContact);
        appManager.goTo().homePage();

        assertThat(appManager.contact().count(), equalTo(beforeContactList.size() - 1));
        Contacts afterContactList = appManager.contact().all();
        assertThat(afterContactList, equalTo(beforeContactList.without(deletedContact)));
    }
}


//import org.junit.jupiter.api.Assertions;
//import ru.stqa.pft.addressbook.model.ContactData;
//import ru.stqa.pft.addressbook.model.Contacts;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//public class ContactDeletionTests extends TestBase {
//
//    @BeforeEach
//    public void prepareContactDeletion() {
//        appManager.goTo().homePage();
//        if (appManager.contact().all().isEmpty()) {
//            appManager.goTo().contactPage();
//            appManager.contact().create(new ContactData()
//                    .withFirstName("Test1").withMiddleName("TestMiddleName1")
//                    .withLastName("Test1").withNickname("TestNickname1")
//                    .withCompany("TestCompany1").withAddress("TestAddress1")
//                    .withFirstEmail("TestEmail1").withGroup("HP")
//                    .withHomePhone("123456").withMobilePhone("+79854612312").withWorkPhone("123456789"));
//        }
//    }
//
//    @Test
//    public void testContactDeletion() {
//        Contacts beforeContactList = appManager.contact().all();
//        ContactData deletedContact = beforeContactList.iterator().next();
//
//        Assertions.assertNotNull(deletedContact);
//        appManager.contact().delete(deletedContact);
//        appManager.goTo().homePage();
//        assertThat(appManager.contact().count(), equalTo(beforeContactList.size() - 1));
//        Contacts afterContactList = appManager.contact().all();
//        assertThat(afterContactList, equalTo(beforeContactList.without(deletedContact)));
//    }
//}
//


