package ru.naragas.hibernateconsoleapp.util;



import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.naragas.hibernateconsoleapp.model.User;

/**
 * @version 1.0
 * @author Naragas
 * @created 7/31/2025
 */

@UtilityClass
public class HibernateUtil {

    @Getter
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration().addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("SessionFactory creation failed: " + e);
        }
    }
}
