package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    static final String DB_URL = "jdbc:mysql://localhost:3306/KATA";
    static final String DB_USER = "root";
    static final String DB_PASS = "root";
    static Driver driver;
    private static SessionFactory sessionFactory;

    static Connection connection = null;

    public static Connection getConnection() {
        try {
            driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            return connection;
        } catch (SQLException e) {
            System.err.println("Что-то пошло не так");
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();

        // Hibernate settings equivalent to hibernate.cfg.xml's properties
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(Environment.URL, DB_URL);
        settings.put(Environment.USER, DB_USER);
        settings.put(Environment.PASS, DB_PASS);
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

        settings.put(Environment.SHOW_SQL, "true");

        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

        settings.put(Environment.HBM2DDL_AUTO, "none");

        configuration.setProperties(settings);

        configuration.addAnnotatedClass(User.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }


}
