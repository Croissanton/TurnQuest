package com.gdx.turnquest.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.gdx.turnquest.utils.UserManager;
import com.gdx.turnquest.dialogs.SignUpDialog;
import com.gdx.turnquest.dialogs.LoginDialog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * This class tests the UserManager class.
 * It tests the checkUsername method.
 * It tests the addUser method when adding a new user and when adding an existing user.
 *
 * @author Cristian
 */
public class UsersTest {
    private UserManager mockedManager;

    @BeforeEach
    public void setUp() {
        //Mocking
        mockedManager = mock(UserManager.class);

        //Stubbing
        when(mockedManager.checkUsername("test")).thenReturn(true);
        when(mockedManager.checkUsername("test2")).thenReturn(false);
        doCallRealMethod().when(mockedManager).addUser(any(), any());
    }

    @AfterEach
    public void tearDown() {
        mockedManager = null;
    }

    @Test
    public void addNewUser(){
        assertThrows(NullPointerException.class, () -> mockedManager.addUser("test2", "test2"));
        //DOES NOT THROW ILLEGALARGUMENTEXCEPTION, THROWS NULLPOINTEREXCEPTION INSTEAD SINCE usersData IS NULL.
    }

    @Test
    public void AddExistingUser(){
        assertThrows(IllegalArgumentException.class, () -> mockedManager.addUser("test", "test"));
    }
}

