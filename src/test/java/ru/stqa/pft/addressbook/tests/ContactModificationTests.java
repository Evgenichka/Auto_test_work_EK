package ru.stqa.pft.addressbook.tests;

import org.junit.jupiter.api.Assertions;
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
                    .withFirstEmail("TestEmail").withGroup("HP")
                    .withHomePhone("123456").withMobilePhone("+79854612312").withWorkPhone("123456789"));
        }
    }

    @Test
    public void testContactEditing() {
        Contacts beforeContactList = appManager.contact().all();
        ContactData editedContact = beforeContactList.iterator().next();
        Assertions.assertNotNull(editedContact);
        ContactData contactDataForEditing = new ContactData()
                .withId(editedContact.getId())
                .withFirstName("Test1Name").withMiddleName("Test2Name")
                .withLastName("Test3Name").withNickname("Test4Nickname")
                .withCompany("Test5Company").withAddress("Test6Address")
                .withFirstEmail("test7@mail.com").withGroup("HP")
                .withHomePhone("344444")
                .withMobilePhone("3334242343")
                .withWorkPhone("23421421");

        appManager.contact().update(contactDataForEditing);
        assertThat(appManager.contact().count(), equalTo(beforeContactList.size()));
        Contacts afterContactList = appManager.contact().all();
        assertThat(afterContactList, equalTo(beforeContactList.without(editedContact).withAdded(contactDataForEditing)));
    }
}

