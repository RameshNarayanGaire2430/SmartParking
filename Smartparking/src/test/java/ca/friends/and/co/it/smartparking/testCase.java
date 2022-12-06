package ca.friends.and.co.it.smartparking;

import junit.framework.Assert;

import org.junit.Test;

public class testCase {
    @Test
    public void test_password_input_fail() {
        RegisterActivity validator = new RegisterActivity();
        Assert.assertFalse(validator.isValidPassword("Ramesh123"));
    }
    @Test
    public void test_password_input_true(){
        RegisterActivity validator = new RegisterActivity();
        Assert.assertTrue(validator.isValidPassword("Ramesh123@@"));
    }
    @Test
    public void test_password_input_fail_caps(){
        RegisterActivity validator = new RegisterActivity();
        Assert.assertFalse(validator.isValidPassword("ramesh123@"));
    }
    @Test
    public void test_password_input_true_2(){
        RegisterActivity validator = new RegisterActivity();
        Assert.assertTrue(validator.isValidPassword("Ramesh123#$"));
    }
    @Test
    public void test_email_fail(){
        RegisterActivity validator = new RegisterActivity();
        Assert.assertFalse(validator.emailValidation("Ramesh123"));
    }
    @Test
    public void test_email_true(){
        RegisterActivity validator = new RegisterActivity();
        Assert.assertFalse(validator.emailValidation("Ramesh123@gmail.com"));
    }
    @Test
    public void test_email_false(){
        RegisterActivity validator = new RegisterActivity();
        Assert.assertFalse(validator.emailValidation("Ramesh123@gmailcom"));
    }
}



