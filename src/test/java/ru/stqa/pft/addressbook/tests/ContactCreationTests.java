package ru.stqa.pft.addressbook.tests;

import ru.stqa.pft.addressbook.model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    @Test
    public void contactCreation() {
        List<ContactData> beforeContactList = appManager.getContactHelper().getContactList();
        appManager.getNavigationHelper().goToContactPage();
        ContactData contactForList = new ContactData(
                "TestFirstName",
                "TestMiddleName",
                "TestLastName",
                "TestNickname",
                "TestCompany",
                "TestEmail",
                "TestGroupName"
        );
        appManager.getContactHelper().fillContactForm(contactForList, true);
        appManager.getContactHelper().submitContactCreation();
        appManager.getContactHelper().returnToHomePage();
        List<ContactData> afterContactList = appManager.getContactHelper().getContactList();
        Assertions.assertEquals(afterContactList.size(), beforeContactList.size() + 1);

        beforeContactList.add(contactForList);
        // Измените comparator, добавив методы доступа
        Comparator<? super ContactData> byLastAndFirstName = Comparator.comparing(ContactData::getLastName)
                .thenComparing(ContactData::getFirstName);

        beforeContactList.sort(byLastAndFirstName);
        afterContactList.sort(byLastAndFirstName);
        Assertions.assertEquals(beforeContactList, afterContactList);
    }
}
