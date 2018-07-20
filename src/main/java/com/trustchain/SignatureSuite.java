package com.trustchain;

import java.security.*;

/**
 * 代表一个size相关的一套用于生成密钥、签名、验签的javaapi组件
 */
public class SignatureSuite {
    private int size;

    /**
     * 使用的消息摘要算法
     */
    private static final String MESSAGE_DIGEST_ALGORITHM = "SHA256";


    private KeyPairGenerator keyPairGenerator;

    private Signature signSignature;

    private Signature verifySignature;


    /**
     * 未签名数据
     */
    private byte[] unsignedData;

    /**
     * 签名后数据
     */
    private byte[] signedData;

    /**
     *
     * @param algorithm 算法名
     * @param size
     * @param unsignedData 未签名数据
     * @throws NoSuchAlgorithmException 不支持该算法
     */
    public SignatureSuite(String algorithm, int size, byte[] unsignedData) throws NoSuchAlgorithmException {
        this.size = size;

        String algorithmName = MESSAGE_DIGEST_ALGORITHM + "WITH" + algorithm;

        keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(size);
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

    public int getSize() {
        return size;
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
