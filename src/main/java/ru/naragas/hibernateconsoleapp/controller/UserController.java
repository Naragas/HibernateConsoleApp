package ru.naragas.hibernateconsoleapp.controller;


import ru.naragas.hibernateconsoleapp.dao.UserDAO;
import ru.naragas.hibernateconsoleapp.model.User;

/**
 * @author Naragas
 * @version 1.0
 * @created 7/31/2025
 */

public class UserController {
    private final UserDAO userDAO = new UserDAO();

    public void addUser(String name, String email, int age) {
        User newUser = new User(name, email, age);
        userDAO.addUser(newUser);
    }


    public void updateUser(int id, String name, String email, int age) {
        User user = userDAO.findUserById(id);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            userDAO.UpdateUser(user);
        } else {
            System.out.println("User not found");
        }

    }

    public void deleteUser(int id) {
    }

    public void showUserById(int id) {
    }

    public void showAllUser() {
    }
}
