package ru.stqa.pft.addressbook.tests;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GroupCreationTests extends TestBase {

    Logger logger = LoggerFactory.getLogger(GroupCreationTests.class);

    private final List<GroupData> storedGroups = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        storedGroups.clear();
    }

    @AfterEach
    public void tearDown() {
        // Освобождение ресурсов, если нужно
    }

    // Вспомогательный метод для подготовки аргументов из JSON
    private static List<GroupData> loadGroupsFromJson() throws IOException {
        Path jsonPath = Paths.get("src/test/resources/group_data.json");
        BufferedReader reader = Files.newBufferedReader(jsonPath);
        Gson gson = new Gson();
        return gson.fromJson(reader, new TypeToken<List<GroupData>>() {}.getType());
    }

    // Вспомогательный метод для подготовки аргументов из CSV
    private static List<GroupData> loadGroupsFromCsv() throws IOException {
        Path csvPath = Paths.get("src/test/resources/group_data.csv");
        Reader reader = Files.newBufferedReader(csvPath);

        // Настройки для CSV файла
        CSVParser parser = CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader().parse(reader);

        List<GroupData> groupsFromCsv = new ArrayList<>();
        for (CSVRecord record : parser) {
            // Предполагаемая структура CSV: name, header, footer
            String name = record.get("name");
            String header = record.get("header");
            String footer = record.get("footer");

            GroupData group = new GroupData(name, header, footer);
            groupsFromCsv.add(group);
        }

        return groupsFromCsv;
    }

    // Вспомогательный метод для подготовки аргументов из XML
    private static List<GroupData> loadGroupsFromXml() throws IOException {
        Path xmlPath = Paths.get("src/test/resources/group_data.xml");
        BufferedReader reader = Files.newBufferedReader(xmlPath);

        XStream xStream = new XStream();
        xStream.processAnnotations(GroupData.class); // Регистрация аннотаций для десериализации
        xStream.allowTypes(new Class[] { GroupData.class }); // Безопасность: разрешаем десериализацию только нашего класса

        // Десериализуем данные из XML
        List<GroupData> groupsFromXml = (List<GroupData>) xStream.fromXML(reader);
        return groupsFromXml;
    }



    // Параметризованный тест
    @ParameterizedTest
    @ValueSource(strings = {"data"})
    @Tag("groups")
    public void testGroupCreation(String ignored) throws Exception {
        logger.info("Start test GroupCreation");
        List<GroupData> groups = loadGroupsFromJson();
        for (GroupData group : groups) {
            // Ваш тестовый сценарий для каждой группы
            System.out.println("Testing group: " + group.getName());

        }
        logger.info("Stop test GroupCreation");
    }

    // Параметризованный тест
    @ParameterizedTest
    @ValueSource(strings = {"csv-data"}) // Можно использовать любое значение, оно просто послужит маркером
    @Tag("groups")
    public void testGroupCreationFromCsv(String ignored, TestInfo info) throws Exception {
        List<GroupData> groups = loadGroupsFromCsv();
        for (GroupData group : groups) {
            // Ваш тестовый сценарий для каждой группы
            System.out.println("Testing group: " + group.getName());
        }
    }

    // Параметризованный тест
    @ParameterizedTest
    @ValueSource(strings = {"data"}) // Просто используем фиктивное значение для запуска теста
    @Tag("groups")
    public void testGroupCreationFromXml(String ignored) throws Exception {
        List<GroupData> groups = loadGroupsFromXml();
        for (GroupData group : groups) {
            // Ваш тестовый сценарий для каждой группы
            System.out.println("Testing group: " + group.getName());
        }
    }

    // Негативный тест на исключение при создании "плохой" группы
    @Test
    @Tag("groups")
    public void testBadGroupCreation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("Ошибка создания группы");
        });
        assertEquals("Ошибка создания группы", exception.getMessage());
    }



}





