package com.gdx.turnquest.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.gdx.turnquest.utils.UserManager;
import com.gdx.turnquest.dialogs.SignUpDialog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class UsersTest {
    private UserManager mockedManager;

    @BeforeEach
    public void setUp() {
        //Mocking
        mockedManager = mock(UserManager.class);

        //Stubbing
        when(mockedManager.checkUsername("test")).thenReturn(true);
        when(mockedManager.checkUsername("test2")).thenReturn(false);
        when(mockedManager.checkUser("test", "test")).thenReturn(true);
        when(mockedManager.checkUser("test", "test2")).thenReturn(false);
    }

    @AfterEach
    public void tearDown() {
        mockedManager = null;
    }

    @Test
    public void addNewUser(){
        assertDoesNotThrow(() -> {
            mockedManager.addUser("test2", "test2");
        });
    }

    @Test
    public void AddExistingUser(){
/*        assertThrows(IllegalArgumentException.class, () -> {
            mockedManager.addUser("test", "test");
        });*/
        //THIS SHOULD WORK SINCE addUser CALLS checkUsername
        mockedManager.addUser("test", "test");
        verify(mockedManager).checkUsername("test"); //This checks if the method was called, it says it wasn't.
    }
}

