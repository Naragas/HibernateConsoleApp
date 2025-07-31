package ru.naragas.hibernateconsoleapp.dao;


import org.hibernate.Transaction;
import org.hibernate.Session;
import ru.naragas.hibernateconsoleapp.model.User;
import ru.naragas.hibernateconsoleapp.util.HibernateUtil;

/**
 * @version 1.0
 * @author Naragas
 * @created 7/31/2025
 */

public class UserDAO {

    public void addUser(User newUser) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            tx = session.beginTransaction();
            session.persist(newUser);
            tx.commit();
        }catch (Exception e){
            if(tx != null){
                tx.rollback();
                e.printStackTrace();
            }
        }
    }

    public void UpdateUser(User updatedUser) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            tx = session.beginTransaction();
            session.merge(updatedUser);
            tx.commit();
        } catch (Exception e) {
            if(tx != null){
                tx.rollback();
                e.printStackTrace();
            }
        }
    }

    public void DeleteUser(){}
    public void ShowAllUsers(){}
    public void ShowUser(){}


    public User findUserById(int id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.find(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
