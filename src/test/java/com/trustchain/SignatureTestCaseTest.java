package com.trustchain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class SignatureTestCaseTest {

    SignatureTestCase signatureTestCase;
    @Before
    public void setUp() throws Exception {
        signatureTestCase = new SignatureTestCase("rsa");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testTest() {
        signatureTestCase.test();
    }
}