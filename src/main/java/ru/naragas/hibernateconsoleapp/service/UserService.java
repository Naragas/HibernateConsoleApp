package ru.naragas.hibernateconsoleapp.service;


import lombok.RequiredArgsConstructor;
import ru.naragas.hibernateconsoleapp.dao.UserDAO;
import ru.naragas.hibernateconsoleapp.model.User;

import java.util.List;

/**
 * @author Naragas
 * @version 1.0
 * @created 8/5/2025
 */

@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;

    public boolean addUser(String name, String email, int age) {
        User user = new User(name, email, age);
        return userDAO.addUser(user);
    }
    public boolean updateUser(User updatedUser, String name, String email, int age) {
        updatedUser.setName(name);
        updatedUser.setEmail(email);
        updatedUser.setAge(age);
        return userDAO.updateUser(updatedUser);
    }

    public boolean removeUser(User deletedUser) {
        return userDAO.removeUser(deletedUser);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User findUserById(int id) {
        return userDAO.findUserById(id);
    }
}
