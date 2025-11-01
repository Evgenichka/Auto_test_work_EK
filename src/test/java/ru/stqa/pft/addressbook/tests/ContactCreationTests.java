package ru.stqa.pft.addressbook.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeMethod;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.stqa.pft.addressbook.tests.TestBase.appManager;

public class ContactCreationTests extends TestBase {

    @BeforeMethod
    public void contactCreationPreconditionsCheck() {
        appManager.goTo().homePage();
    }

    @Test
    public void contactCreation() {
        // Получаем количество контактов ДО добавления нового
        Contacts beforeContactList = appManager.contact().all();

        // Создаем объект контакт с нужными полями
        ContactData contactThatWillBeCreated = new ContactData()
                .withFirstName("TestFirstName")
                .withMiddleName("TestMiddleName")
                .withLastName("TestLastName")
                .withNickname("TestNickname")
                .withCompany("TestCompany")
                .withAddress("TestAddress")
                .withFirstEmail("TestEmail")
                .withSecondEmail("TestEmailSecond")
                .withThirdEmail("TestEmailThird")

                // Используйте правильную группу!
                .withGroup("group1") // Или любое другое существующее имя группы

                .withHomePhone("123456")
                .withMobilePhone("+79854612312")
                .withWorkPhone("123456789");

        // Переходим на страницу создания контакта
        appManager.goTo().contactPage();

        // Создаем новый контакт
        appManager.contact().create(contactThatWillBeCreated);

        // Проверяем, что количество контактов увеличилось на единицу
        assertThat(appManager.contact().count(), equalTo(beforeContactList.size() + 1));

        // Получаем новые контакты после создания
        Contacts afterContactList = appManager.contact().all();

        // Проверяем, что созданный контакт находится в новом списке
        assertThat(
                afterContactList,
                equalTo(beforeContactList.withAdded(contactThatWillBeCreated.withId(
                        afterContactList.stream().mapToInt(ContactData::getId).max().getAsInt())))
        );
    }
}





