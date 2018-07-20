package com.trustchain;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 针对一个签名算法的测试用例
 */
class SignatureTestCase {

    /**
     * 默认要测试的size
     */
    private static final int[] SIZES = {512, 1024, 2048, 3072, 4096, 7680, 15360};


    private static final int DEFAULT_SECONDS = 10;


    private int seconds = DEFAULT_SECONDS;


    private String algorithm;

    private List<SignatureSuite> suites;

    /**
     * 默认size使用[512, 1024, 2048, 3072, 4096, 7680, 15360]这几个等级
     *
     * @throws NoSuchAlgorithmException 如果不支持该算法
     */
    SignatureTestCase(String algorithm) throws NoSuchAlgorithmException {
        this.algorithm = algorithm;
        suites = new ArrayList<>();
        for (int size : SIZES) {
            SignatureSuite suite = new SignatureSuite(algorithm, size);
            suites.add(suite);
        }
    }

    /**
     * @param algorithm 签名算法名
     * @param size 如果<=0抛异常
     * @throws NoSuchAlgorithmException 不支持该算法
     */
    public SignatureTestCase(String algorithm, int size) throws NoSuchAlgorithmException {
        if (size <= 0) {
            throw new IllegalArgumentException("size 必须是一个正数");
        }

        this.algorithm = algorithm;
        suites = new ArrayList<>();
        SignatureSuite suite = new SignatureSuite(algorithm, size);
        suites.add(suite);
    }


    void test() {
        generateKeyTest();
        signTest();
        verifyTest();
    }

    private void generateKeyTest() {
        Metrics metrics = new Metrics(algorithm, AlgorithmOpType.GENERATE_KEYPAIR);
        for (SignatureSuite suite : suites) {
            long deadline = System.currentTimeMillis() + seconds * 1000;
            int size = suite.getSize();
            int count = 0;
            while (System.currentTimeMillis() < deadline) {
                suite.generateKeyPair();
                count++;
            }
            metrics.put(size, new Process(10, count));
        }
        metrics.print();
    }

    private void signTest() {
        Metrics metrics = new Metrics(algorithm, AlgorithmOpType.SIGN);
        for (SignatureSuite suite : suites) {
            long deadline = System.currentTimeMillis() + seconds * 1000;
            int size = suite.getSize();
            int count = 0;
            while (System.currentTimeMillis() < deadline) {
                suite.sign();
                count++;
            }
            metrics.put(size, new Process(10, count));
        }
        metrics.print();
    }

    private void verifyTest() {
        Metrics metrics = new Metrics(algorithm, AlgorithmOpType.VERIFY);
        for (SignatureSuite suite : suites) {
            long deadline = System.currentTimeMillis() + seconds * 1000;
            int size = suite.getSize();
            int count = 0;
            while (System.currentTimeMillis() < deadline) {
                suite.verify();
                count++;
            }
            metrics.put(size, new Process(10, count));
        }
        metrics.print();
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
