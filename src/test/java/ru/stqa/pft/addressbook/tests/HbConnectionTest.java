package ru.stqa.pft.addressbook.tests;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HbConnectionTest {

    private static final SessionFactory sessionFactory;

    static {
        try {
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml") // Загружаем конфигурационный файл
                    .build();

            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @BeforeAll
    public static void setup() {
        // Здесь можем дополнительно подготовить окружение перед тестами
    }

    @Test
    public void testDatabaseAccess() throws Exception {
        Session session = sessionFactory.openSession(); // Открываем сессию
        Transaction tx = session.beginTransaction(); // Начинаем транзакцию

        // Используем типизированный запрос для предотвращения предупреждений
        List<ContactData> contacts = session.createQuery(
                "FROM ContactData WHERE deprecated = '0000-00-00'",
                ContactData.class
        ).list();

        assertNotNull(contacts); // Контакты не равны null
        assertFalse(contacts.isEmpty()); // Список контактов не пустой

        tx.commit(); // Подтверждаем транзакцию
        session.close(); // Закрываем сессию
    }
}


//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.service.ServiceRegistry;
//import org.junit.jupiter.api.Test;
//import ru.stqa.pft.addressbook.model.ContactData;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class HbConnectionTest {
//
//    protected static final SessionFactory sessionFactory;
//
//    static {
//        try {
//            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                    .configure("hibernate.cfg.xml")
//                    .build();
//
//            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
//            sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
//        } catch (Throwable ex) {
//            System.err.println("Initial SessionFactory creation failed." + ex);
//            throw new ExceptionInInitializerError(ex);
//        }
//    }
//
//    @Test
//    public void testDatabaseAccess() throws Exception {
//        Session session = sessionFactory.openSession(); // открываем сессию
//        Transaction tx = session.beginTransaction(); // начинаем транзакцию
//
//        // Используем createQuery с указанием класса, чтобы избежать предупреждения
//        List<ContactData> resultList = session.createQuery(
//                "FROM ContactData WHERE deprecated = '0000-00-00'",
//                ContactData.class
//        ).list();
//
//        assertNotNull(resultList); // проверяем, что результат не равен null
//        assertFalse(resultList.isEmpty()); // проверяем, что список не пуст
//
//        tx.commit(); // подтверждаем транзакцию
//        session.close(); // закрываем сессию
//    }
//}



//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import ru.stqa.pft.addressbook.model.ContactData;
//import ru.stqa.pft.addressbook.model.GroupData;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//public class HbConnectionTest {
//
//    private static SessionFactory sessionFactory;
//
//    /**
//     * Инициализация фабрики сессий Hibernate перед запуском всех тестов.
//     */
//    @BeforeAll
//    public static void setUp() {
//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure() // загружаем настройки из файла hibernate.cfg.xml
//                .build();
//        try {
//            sessionFactory = new MetadataSources(registry)
//                    .buildMetadata()
//                    .buildSessionFactory();
//        } catch (Exception e) {
//            e.printStackTrace();
//            StandardServiceRegistryBuilder.destroy(registry);
//        }
//    }
//
//    /**
//     * Завершение сессии и освобождение ресурсов после завершения всех тестов.
//     */
//    @AfterAll
//    public static void tearDown() {
//        if (sessionFactory != null && !sessionFactory.isClosed()) {
//            sessionFactory.close();
//        }
//    }
//
//    /**
//     * Проверяем успешное подключение к базе данных и получение списка групп.
//     */
//
//    @Test
//    public void testHbConnection() {
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        List<GroupData> groups = session.createQuery(
//                "FROM GroupData WHERE deprecated = '0000-00-00'",
//                GroupData.class
//        ).getResultList();
//        session.getTransaction().commit();
//        session.close();
//        assertFalse(groups.isEmpty(), "Список групп не должен быть пустым");
//    }
////    @Test
////    public void testHbConnection() {
////        Session session = sessionFactory.openSession();
////        session.beginTransaction();
//        List<GroupData> groups = session.createQuery("FROM GroupData WHERE deprecated = '0000-00-00'", GroupData.class).list();
//        session.getTransaction().commit();
//        session.close();
//        assertFalse(groups.isEmpty(), "Список групп не должен быть пустым");
//    }


//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//import ru.stqa.pft.addressbook.model.ContactData;
//import ru.stqa.pft.addressbook.model.GroupData;
//
//public class HbConnectionTest {
//
//    private static SessionFactory sessionFactory;
//
//    @BeforeAll
//    static void setUp() {
//        // A SessionFactory is set up once for an application!
//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure() // configures settings from hibernate.cfg.xml
//                .build();
//        try {
//            sessionFactory = new MetadataSources(registry)
//                    .buildMetadata()
//                    .buildSessionFactory();
//        } catch (Exception e) {
//            e.printStackTrace();
//            // The registry would be destroyed by the SessionFactory,
//            // but we had trouble building the SessionFactory
//            // so destroy it manually.
//            StandardServiceRegistryBuilder.destroy(registry);
//        }
//    }
//
//    @Test
//    void testHbConnection() {
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        List<ContactData> result = session.createQuery("from ContactData where deprecated = '0000-00-00'")
//                .list();
//        assertNotNull(result);
//        session.getTransaction().commit();
//        session.close();
//        for (ContactData contact : result) {
//            System.out.println(contact);
//            System.out.println(contact.getGroups());
//        }
//    }
//}