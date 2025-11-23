package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;



public class ContactCreationTests {

    private final List<ContactData> storedContacts = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        storedContacts.clear();
    }

    @AfterEach
    public void tearDown() {
        // Освобождение ресурсов
    }

//    // Метод-дата-провайдер для чтения из XML
//    private static Stream<Arguments> validContactsFromXml() throws IOException {
//        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
//            XStream xStream = new XStream();
//            xStream.processAnnotations(ContactData.class);
//            xStream.allowTypes(new Class[]{ContactData.class});
//            @SuppressWarnings("unchecked") // здесь подавляем предупреждение, т.к. XStream возвращает Object
//            List<ContactData> contacts = (List<ContactData>) xStream.fromXML(reader);
//            return contacts.stream().map(Arguments::of);
//        }
//    }



    // Вспомогательный метод для подготовки аргументов из JSON
    private static List<ContactData> loadContactsFromJson() throws IOException {
        Path jsonPath = Paths.get("src/test/resources/contacts_data.json");
        BufferedReader reader = Files.newBufferedReader(jsonPath);
        Gson gson = new Gson();
        return gson.fromJson(reader, new TypeToken<List<ContactData>>() {}.getType());
    }

    // Вспомогательный метод для подготовки аргументов из XML
    private static List<ContactData> loadContactsFromXml() throws IOException {
        Path xmlPath = Paths.get("src/test/resources/contacts_data.xml");
        BufferedReader reader = Files.newBufferedReader(xmlPath);

        XStream xStream = new XStream();
        xStream.processAnnotations(ContactData.class); // Регистрация аннотаций для десериализации
        xStream.allowTypes(new Class[] { ContactData.class }); // Безопасность: разрешаем десериализацию только нашего класса

        // Десериализуем данные из XML
        List<ContactData> contactsFromXml = (List<ContactData>) xStream.fromXML(reader);
        return contactsFromXml;
    }

    // Вспомогательный метод для подготовки аргументов из CSV
    private static List<ContactData> loadContactsFromCsv() throws IOException {
        Path csvPath = Paths.get("src/test/resources/contacts_data.csv");
        BufferedReader reader = Files.newBufferedReader(csvPath);

        // Настройки для CSV файла
        CSVParser parser = CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader().parse(reader);

        List<ContactData> contactsFromCsv = new ArrayList<>();
        for (CSVRecord record : parser) {
            // Предполагаемая структура CSV: firstName,lastName,address,homePhone,mobilePhone,email
            String firstName = record.get("firstName");
            String lastName = record.get("lastName");
            String address = record.get("address");
            String homePhone = record.get("homePhone");
            String mobilePhone = record.get("mobilePhone");
            String email = record.get("email");

            ContactData contact = new ContactData(firstName, lastName, address, homePhone, mobilePhone, email);
            contactsFromCsv.add(contact);
        }

        return contactsFromCsv;
    }


    // Параметризованный тест JSON
    @ParameterizedTest
    @ValueSource(strings = {"data"})
    @Tag("contacts")
    public void testContactsCreation(String ignored) throws Exception {
        List<ContactData> contacts = loadContactsFromJson();
        for (ContactData contact : contacts) {
            // Используй правильные геттеры
            System.out.println("Testing contact: " + contact.getFirstName() + " " + contact.getLastName() + " " + contact.getAddress() + " " + contact.getHomePhone() + " " + contact.getMobilePhone() + " " + contact.getEmail());
        }
    }

    // Параметризованный тест для XML
    @ParameterizedTest
    @ValueSource(strings = {"data"})
    @Tag("contacts_xml")
    public void testContactsCreationFromXml(String ignored) throws Exception {
        List<ContactData> contacts = loadContactsFromXml();
        for (ContactData contact : contacts) {
            System.out.println("Testing contact from XML: " + contact.getFirstName() + " " + contact.getLastName() + " " + contact.getAddress() + " " + contact.getHomePhone() + " " + contact.getMobilePhone() + " " + contact.getEmail());
        }
    }

    // Параметризованный тест для CSV
    @ParameterizedTest
    @ValueSource(strings = {"data"})
    @Tag("contacts_csv")
    public void testContactsCreationFromCsv(String ignored) throws Exception {
        List<ContactData> contacts = loadContactsFromCsv();
        for (ContactData contact : contacts) {
            System.out.println("Testing contact from CSV: " + contact.getFirstName() + " " + contact.getLastName() + " " + contact.getAddress() + " " + contact.getHomePhone() + " " + contact.getMobilePhone() + " " + contact.getEmail());
        }
    }



    @Test
    @Tag("contacts")
    public void testBadContactCreation() {
        ContactData badContact = new ContactData().withFirstName("invalid").withEmail("bad@email");
        storedContacts.add(badContact);
        assertThat(storedContacts.size(), equalTo(1));
    }
}




//public class ContactCreationTests {
//
//    private final List<ContactData> storedContacts = new ArrayList<>();
//
//    @BeforeEach
//    public void setUp() {
//        storedContacts.clear();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        // Освобождение ресурсов
//    }
//
//    // Метод-дата-провайдер для чтения из XML
//    private static Stream<Arguments> validContactsFromXml() throws IOException {
//        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
//            XStream xStream = new XStream();
//            xStream.processAnnotations(ContactData.class);
//            xStream.allowTypes(new Class[]{ContactData.class});
//            List<ContactData> contacts = (List<ContactData>) xStream.fromXML(reader);
//            return contacts.stream().map(Arguments::of);
//        }
//    }
//
//    // Метод-дата-провайдер для чтения из JSON
//    private static Stream<Arguments> validContactsFromJson() throws IOException {
//        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
//            Gson gson = new Gson();
//            List<ContactData> contacts = gson.fromJson(reader, new TypeToken<List<ContactData>>(){}.getType());
//            return contacts.stream().map(Arguments::of);
//        }
//    }
//
//    // Основной тест с созданием контакта
//    @ParameterizedTest
//    @MethodSource({"validContactsFromXml", "validContactsFromJson"})
//    @Tag("contacts")
//    public void testContactCreation(ContactData contact) {
//        int initialSize = storedContacts.size();
//        storedContacts.add(contact);
//        assertThat(storedContacts.size(), equalTo(initialSize + 1));
//    }
//
//    // Негативный тест
//    @Test
//    @Tag("contacts")
//    public void testBadContactCreation() {
//        // Сквозной тест с ошибочным контактом
//        ContactData badContact = new ContactData().withFirstName("invalid").withEmail("bad@email");
//        storedContacts.add(badContact);
//        assertThat(storedContacts.size(), equalTo(1));
//    }
//}

//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.testng.annotations.BeforeMethod;
//import ru.stqa.pft.addressbook.model.ContactData;
//import ru.stqa.pft.addressbook.model.Contacts;
//
//import java.io.File;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.OptionalInt;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static ru.stqa.pft.addressbook.tests.TestBase.appManager;
//
//public class ContactCreationTests extends TestBase {
//
//    @BeforeMethod
//    public void contactCreationPreconditionsCheck() {
//        appManager.goTo().homePage();
//    }
//
//    @Test
//    public void contactCreation() {
//        // Получаем количество контактов ДО добавления нового
//        Contacts beforeContactList = appManager.contact().all();
//
//        String baseDirectory = System.getProperty("user.dir");
//        File photo = new File(baseDirectory + "/src/resources/kat.jpg");
//        //File photo = new File("D:\\БАРАНЦЕВ\\Neo_test\\Auto_test_work_2\\src\\resources\\kat.jpg");
//        //File photo = new File("src/resources/kat.jpg");
//        // Создаем объект контакт с нужными полями
//        ContactData contactThatWillBeCreated = new ContactData()
//                .withFirstName("Ivan")
//                .withMiddleName("Ivanovich")
//                .withLastName("Kuznetsov")
//                .withNickname("TestNickname")
//                .withCompany("TestCompany")
//                .withAddress("TestAddress")
//                .withFirstEmail("TestEmail")
//                .withSecondEmail("TestEmailSecond")
//                .withThirdEmail("TestEmailThird")
//                .withGroup("Group444")
//                .withPhoto(photo.getAbsolutePath()) // Или любое другое существующее имя группы
//                .withHomePhone("123455")
//                .withMobilePhone("+79855512312")
//                .withWorkPhone("123456755");
//
//        // Переходим на страницу создания контакта
//        appManager.goTo().contactPage();
//
//
//
//
//        // Создаем новый контакт
//        appManager.contact().create(contactThatWillBeCreated);
//
//        // Проверяем, что количество контактов увеличилось на единицу
//        assertThat(appManager.contact().count(), equalTo(beforeContactList.size() + 1));
//
//        // Получаем новые контакты после создания
//        Contacts afterContactList = appManager.contact().all();
//
//        // Проверяем, что созданный контакт находится в новом списке
//        assertThat(
//                afterContactList,
//                equalTo(beforeContactList.withAdded(contactThatWillBeCreated.withId(
//                        afterContactList.stream().mapToInt(ContactData::getId).max().getAsInt())))
//        );
//    }


//    @Test
//    public void testCurrentDir() {
//        File currentDir = new File(".");
//        System.out.println(currentDir.getAbsolutePath());
//        File photo = new File("src/resources/kat.jpg");
//        System.out.println(photo.getAbsolutePath());
//        System.out.println(photo.exists());
//
//    }






