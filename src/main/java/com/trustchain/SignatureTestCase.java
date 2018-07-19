package com.trustchain;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.*;

/**
 * 针对一个签名算法的测试用例
 */
public class SignatureTestCase {

    private static final int[] keySizes = {};
    private static final byte[] DEFAULT_UNSIGHED_DATA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes(Charset.forName("UTF-8"));

    private static final int DEFAULT_SECONDS = 10;

    private UnsignedData unsinedData = new UnsignedData(DEFAULT_UNSIGHED_DATA);

    private int seconds = DEFAULT_SECONDS;

    private byte[] signatureData;


    private String algorithm;

    private List<SignatureSuite> suites;

    /**
     * 未签名数据默认使用 "ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes(Charset.forName("UTF-8"))
     * @see SignatureTestCase#DEFAULT_UNSIGHED_DATA
     *
     * 默认keySize使用[512, 1024, 2048, 3072, 4096, 7680, 15360]这几个等级
     *
     * @throws NoSuchAlgorithmException 如果不支持该算法
     */
    public SignatureTestCase(String algorithm) throws NoSuchAlgorithmException {
        this.algorithm = algorithm;

        suites = new ArrayList<>();
        for (int i = 512; i <= 15360; i *= 2) {
            SignatureSuite suite = new SignatureSuite(algorithm, i, unsinedData.getData());
            suites.add(suite);
        }
    }

    /**
     * 默认keySize使用[512, 1024, 2048, 3072, 4096, 7680, 15360]这几个等级
     * @param algorithm 签名算法名
     * @param unsignedData 未签名数据 如果为null，使用 @see SignatureTestCase#DEFAULT_UNSIGHED_DATA
     * @throws NoSuchAlgorithmException
     */
    public SignatureTestCase(String algorithm, UnsignedData unsignedData) throws NoSuchAlgorithmException {
        this.algorithm = algorithm;
        this.unsinedData = unsignedData;

        suites = new ArrayList<>();
        for (int i = 512; i <= 15360; i *= 2) {
            SignatureSuite suite = new SignatureSuite(algorithm, i, unsignedData.getData());
            suites.add(suite);
        }
    }

    /**
     * @param algorithm 签名算法名
     * @param unsignedData 未签名数据 如果为null，使用 @see SignatureTestCase#DEFAULT_UNSIGHED_DATA
     * @param keySize 如果<=0抛异常
     * @throws NoSuchAlgorithmException
     */
    public SignatureTestCase(String algorithm, UnsignedData unsignedData, int keySize) throws NoSuchAlgorithmException {
        if (keySize <= 0) {
            throw new IllegalArgumentException("keySize 必须是一个正数");
        }
        this.algorithm = algorithm;
        this.unsinedData = unsignedData;

        suites = new ArrayList<>();
        SignatureSuite suite = new SignatureSuite(algorithm, keySize, unsignedData.getData());
        suites.add(suite);
    }


    public void test() {
        signTest();
    }

    private void generateKeyTest() {
        for (SignatureSuite suite : suites) {
            long deadline = System.currentTimeMillis() + seconds * 1000;
            int keySize = suite.getKeySize();
            int count = 0;
            while (System.currentTimeMillis() < deadline) {
                suite.getKeyPairGenerator();
                count++;
            }
            System.out.println("Doing " + keySize + " bit keyPair " + algorithm + "'s for " + seconds + "s: " + count + " " + keySize + " bit keyPair " + algorithm + "'s in " + 5 + "s");
        }
    }

    private void signTest() {

        for (SignatureSuite suite : suites) {
            int keySize = suite.getKeySize();
            int count = 0;
            long deadline = System.currentTimeMillis() + seconds * 1000;
            while (System.currentTimeMillis() < deadline) {
                Signature signSignature = suite.getSignSignature();
                try {
                    signSignature.update(unsinedData.getData());
                    signSignature.sign();
                    count++;
                } catch (SignatureException e) {

                }

            }
            System.out.println("Doing " + keySize + " bit keyPair " + algorithm + "'s for " + seconds + "s: " + count + " " + keySize + " bit keyPair " + algorithm + "'s in " + 5 + "s");
        }
    }
}
