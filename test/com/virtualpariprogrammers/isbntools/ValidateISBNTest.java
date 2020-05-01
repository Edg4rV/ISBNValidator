package com.virtualpariprogrammers.isbntools;

import com.virtualpairprogrammers.isbntools.ValidateISBN;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidateISBNTest {

    @Test
    public void checkAValid10DigitISBN() {
        ValidateISBN validator = new ValidateISBN();
       boolean result = validator.checkISBN("0140449116");
       assertTrue("first value",result);
       result =validator.checkISBN("0140177396");
       assertTrue("second value",result);
    }
    @Test
    public void checkAValid13DigitISBN() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("9781853260087");
        assertTrue("first value",result);
        result =validator.checkISBN("9781853267338");
        assertTrue("second value",result);
    }
    @Test
    public void TenDigitISBNNumbersEndingInAnXAreValid() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("012000030x");
        assertTrue(result);
    }

    @Test
    public void checkAnInvalidISBN() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("140449117");
        //fail();
        assertFalse(result);
    }

    @Test
    public void checkAnInvalid13DigitISBN() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("9781853260086");
        assertFalse(result);

    }

    @Test(expected = NumberFormatException.class)
    public void nineDigitISBNsAreNotAllowed() {
        ValidateISBN validator = new ValidateISBN();
         validator.checkISBN("123456789");
    }

    @Test(expected = NumberFormatException.class)
    public void nonNumericISBNsAreNotAllowed() {
        ValidateISBN validator = new ValidateISBN();
        validator.checkISBN("helloworld");
    }
}
