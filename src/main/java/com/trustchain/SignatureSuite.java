package com.trustchain;

import java.security.*;

public class SignatureSuite {
    private int keySize;

    private static final String MESSAGE_DIGEST_ALGORITHM = "SHA256";

    private KeyPairGenerator keyPairGenerator;

    private KeyPair keyPair;

    private Signature signSignature;

    private Signature verifySignature;


    public SignatureSuite(int keySize, String algorithm) throws NoSuchAlgorithmException {
        this.keySize = keySize;

        String algorithmName = MESSAGE_DIGEST_ALGORITHM + "WITH" + algorithm;

        keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keySize);
        keyPair = keyPairGenerator.generateKeyPair();

        signSignature = Signature.getInstance(algorithmName);
        verifySignature = Signature.getInstance(algorithmName);

        try {
            signSignature.initSign(keyPair.getPrivate());
            verifySignature.initVerify(keyPair.getPublic());
        } catch (InvalidKeyException e) {
            //忽略
        }
    }

    public int getKeySize() {
        return keySize;
    }

    public KeyPairGenerator getKeyPairGenerator() {
        return keyPairGenerator;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    public Signature getSignSignature() {
        return signSignature;
    }

    public Signature getVerifySignature() {
        return verifySignature;
    }
}
