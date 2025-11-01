package ru.stqa.pft.addressbook.tests;

import ru.stqa.pft.addressbook.model.ContactData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
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

//    @Test
//    public void testReverseContactInformationComparison() {
//        // Предварительная подготовка: открываем домашнюю страницу
//        appManager.goTo().homePage();
//
//        // Выбор первого контакта из списка (для примера возьмем первый контакт)
//        List<ContactData> contacts = appManager.contact().all();
//        ContactData selectedContact = contacts.get(0);
//
//        // Открытие формы редактирования и получение полных данных контакта
//        ContactData detailedContactInfo = appManager.contact().infoFromEditForm(selectedContact);
//
//        // Сбор информации о контакте с главной страницы
//        String homePageInfo = appManager.contact().infoFromHomepage(selectedContact);
//
//        // Сравниваем данные с главной страницы с деталями из формы редактирования
//        assertThat(cleanedUp(homePageInfo), equalTo(detailedContactInfo.getFullDetails()));
//    }
//
//    private String cleanedUp(String data) {
//        // Функция для нормализации текста (убираем лишние пробелы и символы)
//        return data.trim().replaceAll("\\s+", " ");
//    }
//
//}

