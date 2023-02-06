package ca.friends.and.co.it.smartparking;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import junit.framework.Assert;

import org.junit.Test;

public class ValidationTest {
    @Test
    public void test_password_input_fail() {
        RegisterActivity validator = new RegisterActivity();
        assertFalse(validator.isValidPassword("Ramesh123"));
    }
    @Test
    public void test_password_input_true(){
        RegisterActivity validator = new RegisterActivity();
        assertTrue(validator.isValidPassword("Ramesh123@@"));
    }
    @Test
    public void test_password_input_fail_caps(){
        RegisterActivity validator = new RegisterActivity();
        assertFalse(validator.isValidPassword("ramesh123@"));
    }
    @Test
    public void test_password_input_true_2(){
        RegisterActivity validator = new RegisterActivity();
        assertTrue(validator.isValidPassword("Ramesh123#$"));
    }
    @Test
    public void test_email_fail(){
        RegisterActivity validator = new RegisterActivity();
        assertFalse(validator.emailValidation("Ramesh123"));
    }
    @Test
    public void test_email_true(){
        RegisterActivity validator = new RegisterActivity();
        assertFalse(validator.emailValidation("Ramesh123@gmail.com"));
    }
    @Test
    public void test_email_false(){
        RegisterActivity validator = new RegisterActivity();
        assertFalse(validator.emailValidation("Ramesh123@gmailcom"));
    }
}



