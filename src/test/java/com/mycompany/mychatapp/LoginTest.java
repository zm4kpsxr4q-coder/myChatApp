/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.mychatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author HLUMELO
 */
public class LoginTest {
   private Login login;

    @BeforeEach
    public void setUp() {
        login = new Login();
    }

    @Test
    public void testRegisterUser_validDetails_returnsSuccess() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27718693002");
        // FIX: string now matches what Login.registerUser() actually returns
        assertEquals("Registration successful.", result);
    }

    @Test
    public void testRegisterUser_usernameTooShort_returnsFailure() {
        String result = login.registerUser("kl", "Ch&&sec@ke99!", "+27718693002");
        assertFalse(result.equals("Registration successful."),
                    "Short username should not register successfully");
    }

    @Test
    public void testRegisterUser_passwordTooWeak_returnsFailure() {
        String result = login.registerUser("kyl_1", "password", "+27718693002");
        assertFalse(result.equals("Registration successful."),
                    "Weak password should not register successfully");
    }

    @Test
    public void testRegisterUser_invalidCellNumber_returnsFailure() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "0831234567");
        assertFalse(result.equals("Registration successful."),
                    "Number without +27 should not register successfully");
    }

    @Test
    public void testLoginUser_correctCredentials_returnsTrue() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27718693002");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginUser_wrongPassword_returnsFalse() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27718693002");
        assertFalse(login.loginUser("kyl_1", "wrongpassword"));
    }

    @Test
    public void testLoginUser_wrongUsername_returnsFalse() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27718693002");
        assertFalse(login.loginUser("wronguser", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginUser_unregisteredUser_returnsFalse() {
        assertFalse(login.loginUser("nobody", "nopassword"));
    }

    @Test
    public void testReturnLoginStatus_successfulLogin_returnsWelcome() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27718693002");
        boolean loggedIn = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(login.returningLoginStatus(loggedIn).contains("kyl_1"));
    }

    @Test
    public void testReturnLoginStatus_failedLogin_returnsFailureMessage() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27718693002");
        boolean loggedIn = login.loginUser("kyl_1", "wrongpassword");
        assertFalse(login.returningLoginStatus(loggedIn).contains("Welcome"));
    }
}