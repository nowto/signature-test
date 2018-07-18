package com.trustchain;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class SignatureTestCase {




    private UnsignedData unsinedData;

    private byte[] signatureData;





    private List<SignatureSuite> suites;

    public SignatureTestCase(String algorithm) throws NoSuchAlgorithmException {
        suites = new ArrayList<>();
        for (int i = 512; i <= 15360; i *= 2) {
            SignatureSuite suite = new SignatureSuite(i, algorithm);
            suites.add(suite);
        }
    }




    private class KeyPairTest {

    }

    private class sighTest {

    }

    private class verifyTest {

    }
}
