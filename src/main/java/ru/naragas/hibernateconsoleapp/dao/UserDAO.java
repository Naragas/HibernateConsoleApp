package ru.naragas.hibernateconsoleapp.dao;



import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import ru.naragas.hibernateconsoleapp.exception.UserRemoveException;
import ru.naragas.hibernateconsoleapp.model.User;
import ru.naragas.hibernateconsoleapp.util.HibernateUtil;

import java.util.Collections;
import java.util.List;

/**
 * @version 1.0
 * @author Naragas
 * @created 7/31/2025
 */

@Slf4j
public class UserDAO {

    public boolean addUser(User newUser) {
        log.info("Adding new user: " + newUser);
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
                    log.error("Rollback impossible: transaction already closed.");
                }
            }

            Throwable cause = e.getCause();
            if (cause instanceof PSQLException) {
                PSQLException pgException = (PSQLException) cause;
                String sqlState = pgException.getSQLState();

                switch (sqlState) {
                    case "23505":
                        System.out.println("Email is already in the database.");
                        break;
                    case "23514":
                        System.out.println("Age must be a positive number.");
                        break;
                    default:
                        System.out.println("Database error: " + pgException.getMessage());
                }

                log.warn("PostgreSQL Error [{}]: {}", sqlState, pgException.getMessage());
            } else {
                log.warn("Unknown DB error: {}", e.getMessage());
            }

            return false;
        }
        return true;
    }

    public boolean updateUser(User updatedUser) {
        log.info("Updating user: " + updatedUser);
        Transaction tx = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            tx = session.beginTransaction();
            session.merge(updatedUser);
            tx.commit();
        } catch (Exception e) {
            if(tx != null){
                tx.rollback();
            }
            log.warn("Error: {}", e.getMessage());
            return false;
        }
        return true;
    }

    public boolean removeUser(User deletedUser) {
        log.info("Removing user: " + deletedUser);
        Transaction tx = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            tx = session.beginTransaction();

            session.remove(deletedUser);

            tx.commit();

        } catch (Exception e) {
            if(tx != null){
                tx.rollback();
            }

            throw new UserRemoveException("Failed to delete user", e);

        }
        return true;
    }

    public List<User> getAllUsers(){
        log.info("Getting all users");
        Transaction tx = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            tx = session.beginTransaction();
            List<User> allUsers = session.createQuery("from User", User.class).getResultList();
            tx.commit();
            return allUsers;
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public User findUserById(int id) {
        log.info("Finding user by id: " + id);
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            User findedUser = session.find(User.class, id);
            log.info("Finding user by id: " + findedUser);
            return findedUser;
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            return null;
        }
    }
}
