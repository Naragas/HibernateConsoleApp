package ru.naragas.hibernateconsoleapp.service;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.naragas.hibernateconsoleapp.dao.UserDAO;
import ru.naragas.hibernateconsoleapp.exception.UserRemoveException;
import ru.naragas.hibernateconsoleapp.model.User;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Naragas
 * @version 1.0
 * @created 8/6/2025
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDAO mockUserDAO;

    @InjectMocks
    private UserService userService;

    @Test
    void usersNoEmptyIfUsersAdded() {
        when(userService.getAllUsers()).thenReturn(
                Arrays.asList(
                        new User("testName1", "testMail1@gmail.com", 30),
                        new User("testName2", "testMail2@gmail.com", 30)
                )
        );

        var users = userService.getAllUsers();
        assertFalse(users.isEmpty(), "Users should not be empty");
        assertEquals(2, users.size(), "Users should have 2 elements");
        verify(mockUserDAO, times(1)).getAllUsers();
    }

    @Test
    void addUserShouldReturnTrueIfUserAdded() {
        final String testUserName = "testName3";
        final String testUserMail = "testMail3@gmail.com";
        final int testUserAge = 30;
        var expectedUser = new User(testUserName, testUserMail, testUserAge);
        when(mockUserDAO.addUser(expectedUser)).thenReturn(true);

        boolean result = userService.addUser(testUserName, testUserMail, testUserAge);
        assertTrue(result);
        verify(mockUserDAO, times(1)).addUser(expectedUser);
    }

    @Test
    void addUserShouldReturnFalseIfUserNotAdded() {
        final String testUserName = "testName4";
        final String testUserMail = "testMail4@gmail.com";
        final int testUserAge = 30;
        var expectedUser = new User(testUserName, testUserMail, testUserAge);

        when(mockUserDAO.addUser(expectedUser)).thenReturn(false);

        boolean result = userService.addUser(testUserName, testUserMail, testUserAge);
        assertFalse(result);
        verify(mockUserDAO, times(1)).addUser(expectedUser);
    }

    @Test
    void removeUserShouldReturnTrueIfUserRemoved() {
        final String testUserName = "testName5";
        final String testUserMail = "testMail5@gmail.com";
        final int testUserAge = 30;
        var userForRemove = new User(testUserName, testUserMail, testUserAge);
        when(mockUserDAO.removeUser(userForRemove)).thenReturn(true);

        boolean result = userService.removeUser(userForRemove);
        assertTrue(result);
        verify(mockUserDAO, times(1)).removeUser(userForRemove);
    }

    @Test
    void removeUserShouldThrowExceptionIfUserNotRemoved() {
        final String testUserName = "testName6";
        final String testUserMail = "testMail6@gmail.com";
        final int testUserAge = 30;
        var userForRemove = new User(testUserName, testUserMail, testUserAge);
        doThrow(new UserRemoveException("Failed to delete user"))
                .when(mockUserDAO).removeUser(userForRemove);
        assertThrows(UserRemoveException.class, () -> userService.removeUser(userForRemove));
        verify(mockUserDAO, times(1)).removeUser(userForRemove);
    }

    @Test
    void updateUserShouldReturnTrueIfUserUpdated() {
        final String oldTestUserName = "testName7";
        final String oldTestUserMail = "testMail7@gmail.com";
        final int oldTestUserAge = 30;

        final String newTestUserName = "testName8";
        final String newTestUserMail = "testMail8@gmail.com";
        final int newTestUserAge = 40;

        var userForUpdate = new User(oldTestUserName, oldTestUserMail, oldTestUserAge);

        when(mockUserDAO.updateUser(userForUpdate)).thenReturn(true);

        boolean result = userService.updateUser(userForUpdate, newTestUserName, newTestUserMail, newTestUserAge);

        assertTrue(result);
        assertEquals(newTestUserName, userForUpdate.getName());
        assertEquals(newTestUserMail, userForUpdate.getEmail());
        assertEquals(newTestUserAge, userForUpdate.getAge());
        verify(mockUserDAO, times(1)).updateUser(userForUpdate);
    }

    @Test
    void updateUserShouldThrowExceptionIfUserNotUpdated() {
        final String oldTestUserName = "testName9";
        final String oldTestUserMail = "testMail9@gmail.com";
        final int oldTestUserAge = 30;

        final String newTestUserName = "testName10";
        final String newTestUserMail = "testMail10@gmail.com";
        final int newTestUserAge = 40;

        var userForUpdate = new User(oldTestUserName, oldTestUserMail, oldTestUserAge);
        when(mockUserDAO.updateUser(userForUpdate)).thenReturn(false);

        boolean result = userService.updateUser(userForUpdate, newTestUserName, newTestUserMail, newTestUserAge);

        assertFalse(result);
        verify(mockUserDAO, times(1)).updateUser(userForUpdate);
    }

    @Test
    void findUserByIdShouldReturnUserIfUserFound() {
        final String testUserName = "testName11";
        final String testUserMail = "testMail11@gmail.com";
        final int testUserAge = 30;
        final int userId = 115;
        var userForFind = new User(testUserName, testUserMail, testUserAge);
        userForFind.setId(userId);

        when(mockUserDAO.findUserById(userId)).thenReturn(userForFind);

        var result = userService.findUserById(userId);

        assertSame(userForFind, result);
        verify(mockUserDAO, times(1)).findUserById(userId);
    }

    @Test
    void findUserByIdShouldReturnNullIfUserNotFound() {
        final int userId = 412;
        when(mockUserDAO.findUserById(userId)).thenReturn(null);

        var result = userService.findUserById(userId);

        assertNull(result);
        verify(mockUserDAO, times(1)).findUserById(userId);
    }


    @AfterEach
    void resetMocks() {
        Mockito.reset(mockUserDAO);
    }

}
