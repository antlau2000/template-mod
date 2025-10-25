package com.example.database;

import com.example.entity.MessageEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseManager {
    private static SessionFactory sessionFactory;

    public static void initialize() {
        try {
            Configuration configuration = new Configuration();
            configurePostgres(configuration);
            configureHibernate(configuration);
            configuration.addAnnotatedClass(MessageEntity.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    private static void configurePostgres(Configuration configuration) {
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/minecraft");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "postgres");
    }

    private static void configureHibernate(Configuration configuration) {
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperty("hibernate.show_sql", "true");
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void saveMessage(MessageEntity message) {
        try (Session session = getSession()) {
            session.beginTransaction();
            session.persist(message);
            session.getTransaction().commit();
        }
    }
}
