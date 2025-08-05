package ru.naragas.hibernateconsoleapp.controller;


import ru.naragas.hibernateconsoleapp.dao.UserDAO;
import ru.naragas.hibernateconsoleapp.model.User;
import ru.naragas.hibernateconsoleapp.service.UserService;

import java.util.List;

/**
 * @author Naragas
 * @version 1.0
 * @created 7/31/2025
 */

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public boolean addUser(String name, String email, int age) {
        return userService.addUser(name, email, age);
    }


    public boolean updateUser(User updatedUser, String name, String email, int age) {
        return userService.updateUser(updatedUser, name, email, age);
    }

    public User getUserById(int id) {
        return userService.findUserById(id);
    }

    public void deleteUser(User deletedUser) {
        userService.removeUser(deletedUser);
    }

    public List<User> showAllUser() {
        return userService.getAllUsers();
    }
}
