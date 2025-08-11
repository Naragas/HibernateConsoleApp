package ru.naragas.hibernateconsoleapp.util;


import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.naragas.hibernateconsoleapp.model.User;

/**
 * @author Naragas
 * @version 1.0
 * @created 8/11/2025
 */

@UtilityClass
public class HibernateUtilTest {

    @Getter
    private static SessionFactory sessionFactory;

    public static void configure(String jdbcUrl, String username, String password) {
        shutdown();

        try {
            Configuration configuration = new Configuration()
                    .addAnnotatedClass(User.class)
                    .setProperty("hibernate.connection.url", jdbcUrl)
                    .setProperty("hibernate.connection.username", username)
                    .setProperty("hibernate.connection.password", password);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("SessionFactory creation failed: " + e);
        }
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

}
