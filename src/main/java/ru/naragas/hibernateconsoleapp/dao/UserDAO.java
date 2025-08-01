package ru.naragas.hibernateconsoleapp.dao;



import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.naragas.hibernateconsoleapp.model.User;
import ru.naragas.hibernateconsoleapp.util.HibernateUtil;

import java.util.Collections;
import java.util.List;

/**
 * @version 1.0
 * @author Naragas
 * @created 7/31/2025
 */

public class UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public boolean addUser(User newUser) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            tx = session.beginTransaction();
            session.persist(newUser);
            tx.commit();
        }catch (ConstraintViolationException  e){
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (IllegalStateException ex) {
                    logger.error("Rollback impossible: transaction already closed.");
                }
            }
            if(e.getCause() instanceof org.postgresql.util.PSQLException){
                System.out.println("Email is already in the database.");
            } else {
                logger.warn("Error: {}", e.getCause().getMessage());
            }
            return false;
        }
        return true;
    }

    public boolean UpdateUser(User updatedUser) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            tx = session.beginTransaction();
            session.merge(updatedUser);
            tx.commit();
        } catch (Exception e) {
            if(tx != null){
                tx.rollback();
            }
            logger.warn("Error: {}", e.getMessage());
            return false;
        }
        return true;
    }

    public boolean removeUser(User deletedUser) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            tx = session.beginTransaction();

            session.remove(deletedUser);

            tx.commit();
            return true;

        } catch (Exception e) {
            if(tx != null){
                tx.rollback();
            }

            return false;
        }
    }

    public List<User> getAllUsers(){
        Transaction tx = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            tx = session.beginTransaction();
            List<User> allUsers = session.createQuery("from User", User.class).getResultList();
            tx.commit();
            return allUsers;
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public User findUserById(int id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.find(User.class, id);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            return null;
        }
    }
}
