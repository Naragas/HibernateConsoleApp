package ru.naragas.hibernateconsoleapp.console;


import ru.naragas.hibernateconsoleapp.controller.UserController;
import ru.naragas.hibernateconsoleapp.dao.UserDAO;
import ru.naragas.hibernateconsoleapp.model.User;
import ru.naragas.hibernateconsoleapp.service.UserService;

import java.util.List;
import java.util.Scanner;

/**
 * @author Naragas
 * @version 1.0
 * @created 7/31/2025
 */

public class ConsoleUI {

    private final static String ADD_USER_MESSAGE = "1. Add User";
    private final static String EDIT_USER_MESSAGE = "2. Edit User";
    private final static String DELETE_USER_MESSAGE = "3. Delete User";
    private final static String SHOW_ONE_USER_MESSAGE = "4. Show User";
    private final static String SHOW_ALL_USERS_MESSAGE = "5. Show list of all Users";
    private final static String EXIT_MESSAGE = "0. Exit";
    private final static String SELECT_OPTION_MESSAGE = "Select option \n";
    private final static String NAME_REQUEST = "Enter name: ";
    private final static String EMAIL_REQUEST = "Enter Email: ";
    private final static String AGE_REQUEST = "Enter age: ";
    private final static String ID_REQUEST = "Enter ID: ";
    private final static String USER_NOT_FOUND = "User not found";

    private final UserController userController = new UserController(new UserService(new UserDAO()));


    public void showMenu() {
        while (true) {
            System.out.println();
            System.out.println(ADD_USER_MESSAGE);
            System.out.println(EDIT_USER_MESSAGE);
            System.out.println(DELETE_USER_MESSAGE);
            System.out.println(SHOW_ONE_USER_MESSAGE);
            System.out.println(SHOW_ALL_USERS_MESSAGE);
            System.out.println(EXIT_MESSAGE);
            System.out.println();
            int option = readInt(SELECT_OPTION_MESSAGE);

            switch (option) {
                case 1 -> addUser();
                case 2 -> editUser();
                case 3 -> deleteUser();
                case 4 -> showUser();
                case 5 -> showAllUser();
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void addUser() {
        String name = readNonEmptyString(NAME_REQUEST);
        String email = readEmail(EMAIL_REQUEST);
        int age = readInt(AGE_REQUEST);
        if (userController.addUser(name, email, age)) {
            System.out.println("User added successfully");
        } else {
            System.out.println(USER_NOT_FOUND);
        }
    }

    private void editUser() {
        User userForUpdate = checkUserInDbById();

        if (userForUpdate == null) {
            System.out.println(USER_NOT_FOUND);
        } else {
            String name = readNonEmptyString(NAME_REQUEST);
            String email = readEmail(EMAIL_REQUEST);
            int age = readInt(AGE_REQUEST);

            if (userController.updateUser(userForUpdate, name, email, age)) {
                System.out.println("User edited successfully");
            } else {
                System.out.println(USER_NOT_FOUND);
            }
        }
    }


    private void deleteUser() {
        User userForDelete = checkUserInDbById();

        if (userForDelete == null) {
            System.out.println(USER_NOT_FOUND);
        } else {
            userController.deleteUser(userForDelete);
            System.out.println("User deleted successfully");
        }
    }

    private void showUser() {
        User userForShow = checkUserInDbById();

        if (userForShow == null) {
            System.out.println(USER_NOT_FOUND);
        } else {
            System.out.println(userForShow);
        }

    }

    private void showAllUser() {
        List<User> allUsers = userController.showAllUser();
        if (allUsers.isEmpty()) {
            System.out.println(USER_NOT_FOUND);
        } else {
            for (User user : allUsers) {
                System.out.println(user);
            }
        }
    }

    private int readInt(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter an integer.");
            }
        }
    }

    private String readNonEmptyString(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Error: Field cannot be empty.");
        }
    }

    private String readEmail(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(message);
            String email = scanner.nextLine().trim();
            if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                return email;
            }
            System.out.println("Error: Please enter a valid email.");
        }
    }

    private User checkUserInDbById() {
        int id = readInt(ID_REQUEST);
        return userController.getUserById(id);
    }


}
