package ru.naragas.hibernateconsoleapp.console;


import ru.naragas.hibernateconsoleapp.controller.UserController;

import java.util.Scanner;

/**
 * @version 1.0
 * @author Naragas
 * @created 7/31/2025
 */

public class ConsoleUI {

    private final static String ADD_USER_MESSAGE = "1. Add User";
    private final static String EDIT_USER_MESSAGE = "2. Edit User";
    private final static String DELETE_USER_MESSAGE = "3. Delete User";
    private final static String SHOW_ONE_USER_MESSAGE = "4. Show User";
    private final static String SHOW_ALL_USERS_MESSAGE = "5. Show list of all Users";
    private final static String EXIT_MESSAGE = "0. Exit";
    private final static String SELECT_OPTION_MESSAGE = "Select option";

    private final UserController userController = new UserController();


    public void showMenu() {
        while (true) {
            System.out.println(ADD_USER_MESSAGE);
            System.out.println(EDIT_USER_MESSAGE);
            System.out.println(DELETE_USER_MESSAGE);
            System.out.println(SHOW_ONE_USER_MESSAGE);
            System.out.println(SHOW_ALL_USERS_MESSAGE);
            System.out.println(EXIT_MESSAGE);
            int option = readInt(SELECT_OPTION_MESSAGE);

            switch (option) {
                case 1 -> addUser();
                case 2 -> editUser();
                case 3 -> deleteUser();
                case 4 -> showUser();
                case 5 -> showAllUser();
                case 0 -> { return; }
            }
        }
    }

    private void addUser() {
        String name = readNonEmptyString("Enter name: ");
        String email = readEmail("Enter Email: ");
        int age = readInt("Enter Age: ");
        userController.addUser(name, email,age);
        System.out.println("User added successfully");
    }

    private void editUser() {
        int id = readInt("Enter ID: ");
        String name = readNonEmptyString("Enter name: ");
        String email = readEmail("Enter Email: ");
        int age = readInt("Enter Age: ");
        userController.updateUser(id, name, email, age);
        System.out.println("User edited successfully");
    }
    private void deleteUser() {
        int id = readInt("Enter ID: ");
        userController.deleteUser(id);
        System.out.println("User deleted successfully");
    }
    private void showUser() {
        int id = readInt("Enter ID: ");
        userController.showUserById(id);
    }
    private void showAllUser() {
        userController.showAllUser();
    }


    private int readInt(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
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
            System.out.println("Ошибка: поле не может быть пустым.");
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
            System.out.println("Ошибка: введите корректный email.");
        }
    }


}
