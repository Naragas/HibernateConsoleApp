package ru.naragas.hibernateconsoleapp.controller;


import ru.naragas.hibernateconsoleapp.dao.UserDAO;
import ru.naragas.hibernateconsoleapp.model.User;

import java.util.List;

/**
 * @author Naragas
 * @version 1.0
 * @created 7/31/2025
 */

public class UserController {
    private final UserDAO userDAO = new UserDAO();

    public boolean addUser(String name, String email, int age) {
        User newUser = new User(name, email, age);
        if (!userDAO.addUser(newUser)) {
            return false;
        }
        return true;
    }


    public boolean updateUser(User updatedUser, String name, String email, int age) {
        updatedUser.setName(name);
        updatedUser.setEmail(email);
        updatedUser.setAge(age);
        return userDAO.UpdateUser(updatedUser);
    }

    public User getUserById(int id) {
        return userDAO.findUserById(id);
    }

    public void deleteUser(User deletedUser) {
        userDAO.removeUser(deletedUser);
    }

    public List<User> showAllUser() {
        return userDAO.getAllUsers();
    }
}
