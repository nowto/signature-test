package com.trustchain;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws NoSuchAlgorithmException {
        System.out.println(Security.getAlgorithms("MessageDigest"));
    }
}
