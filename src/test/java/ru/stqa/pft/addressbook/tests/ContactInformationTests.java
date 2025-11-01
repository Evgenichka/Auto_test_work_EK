package ru.stqa.pft.addressbook.tests;

import ru.stqa.pft.addressbook.model.ContactData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactInformationTests extends TestBase {

    @BeforeEach
    public void prepareContacts() {
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
    public void testContactEqualityOnForms() {
        ContactData contactList = appManager.contact().all().iterator().next();
        ContactData contactCheckEquality = appManager.contact().infoFromEditForm(contactList);

        assertThat(contactList.getFirstName(), equalTo(contactCheckEquality.getFirstName()));
        assertThat(contactList.getLastName(), equalTo(contactCheckEquality.getLastName()));
        assertThat(contactList.getAddress(), equalTo(contactCheckEquality.getAddress()));
        assertThat(contactList.getAllEmail(), equalTo(mergeEmails(contactCheckEquality)));
        assertThat(contactList.getAllPhones(), equalTo(mergePhones(contactCheckEquality)));
    }

    @Test
    public void testContactPhones() {
        ContactData contact = appManager.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = appManager.contact().infoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    }

    private String mergeEmails(ContactData contact) {
        return Stream.of(contact.getFirstEmail(), contact.getSecondEmail(), contact.getThirdEmail())
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining("\n"));
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream().filter(s -> !s.isEmpty())
                .map(ContactInformationTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

}

//import org.junit.jupiter.api.Test;
//import ru.stqa.pft.addressbook.model.ContactData;
//
//
//import java.util.Arrays;
//import java.util.stream.Collectors;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//public class ContactInformationTests extends TestBase {
//
//    @Test
//    public void testContactPhones() {
//        appManager.goTo().HomePage(); // Обращаемся к appManager
//        ContactData contact = appManager.contact().all().iterator().next();
//        ContactData contactInfoFromEditForm = appManager.contact().infoFromEditForm(contact);
//
//        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
//    }
//
//    private String mergePhones(ContactData contact) {
//        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
//                .stream().filter((s) -> !s.equals(""))
//                .map(ContactInformationTests::cleaned)
//                .collect(Collectors.joining("\n"));
//    }
//
//    public static String cleaned(String phone) {
//        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
//    }
//
//}
