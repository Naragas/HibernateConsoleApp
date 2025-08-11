package ru.naragas.hibernateconsoleapp.dao;


import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.naragas.hibernateconsoleapp.model.User;
import ru.naragas.hibernateconsoleapp.util.HibernateUtilTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Naragas
 * @version 1.0
 * @created 8/11/2025
 */

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class UserDAOTest {

    private UserDAO userDAO;

    @Container
    public static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("mytestdb")
                    .withUsername("javatest")
                    .withPassword("javatest");


    @BeforeEach
    public void setUp() {
        String jdbcUrl = postgres.getJdbcUrl();
        String username = postgres.getUsername();
        String password = postgres.getPassword();

        HibernateUtilTest.configure(jdbcUrl, username, password);

        userDAO = new UserDAO();
        userDAO.clearUsersTable();

    }

    @Test
    void findUserByIdShouldReturnValidUser() {
        final String testUserName = "Test0";
        final String testUserMail = "Test0@gmail.com";
        final int testUserAge = 30;

        User newUser = new User(testUserName, testUserMail, testUserAge);

        userDAO.addUser(newUser);

        int newUserId = newUser.getId();

        User fetchedUser = userDAO.findUserById(newUserId);

        assertNotNull(fetchedUser);
        assertEquals(newUser.getId(), fetchedUser.getId());
        assertEquals(newUser.getName(), fetchedUser.getName());
        assertEquals(newUser.getEmail(), fetchedUser.getEmail());
        assertEquals(newUser.getAge(), fetchedUser.getAge());
    }


    @Test
    void addUserShouldPersistUser() {
        final String testUserName = "Test1";
        final String testUserMail = "Test1@gmail.com";
        final int testUserAge = 30;

        User newUser = new User(testUserName, testUserMail, testUserAge);

        boolean result = userDAO.addUser(newUser);
        assertTrue(result);

        User fetchedUser = userDAO.findUserById(newUser.getId());

        assertNotNull(fetchedUser);
        assertEquals(testUserName, fetchedUser.getName());
        assertEquals(testUserMail, fetchedUser.getEmail());
        assertEquals(testUserAge, fetchedUser.getAge());
    }

    @Test
    void updateUserShouldMergeUser() {
        final String testUserName = "Test2";
        final String testUserMail = "Test2@gmail.com";
        final int testUserAge = 30;

        final String newTestUserName = "Test3";
        final String newTestUserMail = "Test3@gmail.com";
        final int newTestUserAge = 45;

        var newUser = new User(testUserName, testUserMail, testUserAge);
        userDAO.addUser(newUser);

        var updatedUser = userDAO.findUserById(newUser.getId());

        updatedUser.setName(newTestUserName);
        updatedUser.setEmail(newTestUserMail);
        updatedUser.setAge(newTestUserAge);

        boolean result = userDAO.updateUser(updatedUser);

        var fetchedUser = userDAO.findUserById(updatedUser.getId());

        assertTrue(result);
        assertNotNull(fetchedUser);
        assertEquals(newTestUserName, fetchedUser.getName());
        assertEquals(newTestUserMail, fetchedUser.getEmail());
        assertEquals(newTestUserAge, fetchedUser.getAge());
    }

    @Test
    void removeUserShouldRemoveUser() {
        final String testUserName = "Test4";
        final String testUserMail = "Test4@gmail.com";
        final int testUserAge = 30;

        var newUser = new User(testUserName, testUserMail, testUserAge);
        userDAO.addUser(newUser);
        int userId = newUser.getId();

        var fetchedUser = userDAO.findUserById(userId);
        assertNotNull(fetchedUser);

        boolean result = userDAO.removeUser(fetchedUser);

        var fetchedUserAfterRemove = userDAO.findUserById(userId);

        assertTrue(result);
        assertNull(fetchedUserAfterRemove);
    }

    @Test
    void getAllUsersShouldReturnAllUsers() {
        final String testFirstUserName = "Test5";
        final String testFirstUserMail = "Test5@gmail.com";
        final int testFirstUserAge = 30;

        final String testSecondUserName = "Test6";
        final String testSecondUserMail = "Test6@gmail.com";
        final int testSecondUserAge = 40;

        final String testThirdUserName = "Test7";
        final String testThirdUserMail = "Test7@gmail.com";
        final int testThirdUserAge = 50;

        var firstNewUser = new User(testFirstUserName, testFirstUserMail, testFirstUserAge);
        var secondNewUser = new User(testSecondUserName, testSecondUserMail, testSecondUserAge);
        var thirdNewUser = new User(testThirdUserName, testThirdUserMail, testThirdUserAge);

        userDAO.addUser(firstNewUser);
        userDAO.addUser(secondNewUser);
        userDAO.addUser(thirdNewUser);

        var fetchedUsers = userDAO.getAllUsers();

        assertFalse(fetchedUsers.isEmpty());
        assertEquals(3, fetchedUsers.size());
        assertTrue(fetchedUsers.contains(firstNewUser));
        assertTrue(fetchedUsers.contains(secondNewUser));
        assertTrue(fetchedUsers.contains(thirdNewUser));
    }

}
