/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.mychatapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
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

    // ── Registration tests ────────────────────────────────────────────────────

    @Test
    public void testRegisterUser_validDetails_returnsSuccess() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27718693002");
        assertEquals("User registered successfully.", result);
    }

    @Test
    public void testRegisterUser_usernameTooLong_returnsFailure() {
        String result = login.registerUser("kyl_12", "Ch&&sec@ke99!", "+27718693002");
        assertFalse(result.equals("User registered successfully."),
            "Username over 5 chars should fail registration");
    }

    @Test
    public void testRegisterUser_usernameNoUnderscore_returnsFailure() {
        String result = login.registerUser("kyle1", "Ch&&sec@ke99!", "+27718693002");
        assertFalse(result.equals("User registered successfully."),
            "Username without underscore should fail registration");
    }

    @Test
    public void testRegisterUser_passwordTooWeak_returnsFailure() {
        String result = login.registerUser("kyl_1", "password", "+27718693002");
        assertFalse(result.equals("User registered successfully."),
            "Weak password should fail registration");
    }

    @Test
    public void testRegisterUser_invalidCellNumber_returnsFailure() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "0831234567");
        assertFalse(result.equals("User registered successfully."),
            "Number without +27 should fail registration");
    }

    // ── Login tests ───────────────────────────────────────────────────────────

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

    // ── Login status message tests ────────────────────────────────────────────

    @Test
    public void testReturnLoginStatus_successfulLogin_returnsWelcome() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27718693002");
        boolean loggedIn = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        String status    = login.returnLoginStatus(loggedIn);
        assertTrue(status.contains("kyl_1"),
            "Welcome message should contain the username");
    }

    @Test
    public void testReturnLoginStatus_failedLogin_returnsFailureMessage() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27718693002");
        boolean loggedIn = login.loginUser("kyl_1", "wrongpassword");
        String status    = login.returnLoginStatus(loggedIn);
        assertFalse(status.contains("Welcome"),
            "Failed login should not return a welcome message");
    }
}
