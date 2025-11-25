package ru.stqa.pft.addressbook.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;

public class DbHelper {

    private final SessionFactory sessionFactory;

    public DbHelper() {
        // Создаем реестр с возможностью переопределить конфиг через системное свойство
        String hibernateConfigFile = System.getProperty("hibernate.config", "hibernate.cfg.xml");

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(hibernateConfigFile) // загружает настройки из указанного файла
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public Groups groups() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<GroupData> result = session.createQuery("from GroupData", GroupData.class).list();
            session.getTransaction().commit();
            return new Groups((Groups) result);
        }
    }

    public Contacts contacts() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<ContactData> result = session.createQuery(
                    "from ContactData where deprecated = '0000-00-00'", ContactData.class
            ).list();
            session.getTransaction().commit();
            return new Contacts((Contacts) result);
        }
    }
}
