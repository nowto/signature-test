package com.trustchain;

import com.trustchain.util.ByteUtils;

import java.security.*;

/**
 * 代表一个size相关的一套用于生成密钥、签名、验签的javaapi组件
 */
public class SignatureSuite {


    /**
     * 使用的消息摘要算法
     */
    private static final String MESSAGE_DIGEST_ALGORITHM = "SHA256";

    /**
     * 用于签名验签的keySize
     */
    private static final int KEY_SIZE = 1024;

    /**
     * 数据大小
     * 用于生成key时为 keySize
     * 用于签名和验签时是数据大小
     */
    private int size;

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
     * @throws NoSuchAlgorithmException 不支持该算法
     */
    public SignatureSuite(String algorithm, int size) throws NoSuchAlgorithmException {
        // 初始化size
        this.size = size;

        String algorithmName = MESSAGE_DIGEST_ALGORITHM + "WITH" + algorithm;

        // 初始化KeyPairGenerator
        keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(size);

        // 初始化signSignature、verifySignature
        KeyPairGenerator signVerifyKeyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        signVerifyKeyPairGenerator.initialize(KEY_SIZE);
        KeyPair signVerifyKeyPair = signVerifyKeyPairGenerator.generateKeyPair();
        signSignature = Signature.getInstance(algorithmName);
        verifySignature = Signature.getInstance(algorithmName);
        try {
            signSignature.initSign(signVerifyKeyPair.getPrivate());
            verifySignature.initVerify(signVerifyKeyPair.getPublic());
        } catch (InvalidKeyException e) {
            //忽略
        }

        // 初始化 unsignedData、signedData
        this.unsignedData = ByteUtils.random(size);
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

    public void generateKeyPair() {
        getKeyPairGenerator().generateKeyPair();
    }

    public void sign() {
        try {
            getSignSignature().update(unsignedData);
            getSignSignature().sign();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
    }

    public void verify() {
        try {
            getVerifySignature().update(unsignedData);
            getVerifySignature().verify(signedData);
        } catch (SignatureException e) {
            e.printStackTrace();
        }
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
