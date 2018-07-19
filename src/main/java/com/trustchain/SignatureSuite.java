package com.trustchain;

import java.security.*;

public class SignatureSuite {
    private int keySize;

    private static final String MESSAGE_DIGEST_ALGORITHM = "SHA256";

    private KeyPairGenerator keyPairGenerator;

    private Signature signSignature;

    private Signature verifySignature;


    private byte[] unsignedData;

    private byte[] signedData;

    public SignatureSuite(String algorithm, int keySize, byte[] unsignedData) throws NoSuchAlgorithmException {
        this.keySize = keySize;

        String algorithmName = MESSAGE_DIGEST_ALGORITHM + "WITH" + algorithm;

        keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        signSignature = Signature.getInstance(algorithmName);
        verifySignature = Signature.getInstance(algorithmName);

        try {
            signSignature.initSign(keyPair.getPrivate());
            verifySignature.initVerify(keyPair.getPublic());
        } catch (InvalidKeyException e) {
            //忽略
        }

        this.unsignedData = unsignedData;
        try {
            signSignature.update(this.unsignedData);
            this.signedData = signSignature.sign();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
    }

    public int getKeySize() {
        return keySize;
    }

    public KeyPairGenerator getKeyPairGenerator() {
        return keyPairGenerator;
    }


    public Signature getSignSignature() {
        return signSignature;
    }

    public Signature getVerifySignature() {
        return verifySignature;
    }
}
