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
    private static final int[] KEYSIZES = {512, 1024, 2048, 3072, 4096, 7680, 15360};


    private static final int DEFAULT_SECONDS = 10;


    private int seconds = DEFAULT_SECONDS;


    private String algorithm;

    private List<SignatureSuite> suites;
    // 并发进程数量
    private int numThreads = 1;

    /**
     * 默认size使用[512, 1024, 2048, 3072, 4096, 7680, 15360]这几个等级
     *
     * @throws NoSuchAlgorithmException 如果不支持该算法
     */
    SignatureTestCase(String algorithm) throws NoSuchAlgorithmException {
        this.algorithm = algorithm;
        suites = new ArrayList<>();
        for (int keySize : KEYSIZES) {
            SignatureSuite suite = new SignatureSuite(algorithm, keySize);
            suites.add(suite);
        }
    }

    /**
     * @param algorithm 签名算法名
     * @param keySize 如果<=0抛异常
     * @throws NoSuchAlgorithmException 不支持该算法
     */
    public SignatureTestCase(String algorithm, int keySize) throws NoSuchAlgorithmException {
        if (keySize <= 0) {
            throw new IllegalArgumentException("size 必须是一个正数");
        }

        this.algorithm = algorithm;
        suites = new ArrayList<>();
        SignatureSuite suite = new SignatureSuite(algorithm, keySize);
        suites.add(suite);
    }


    void test() {
        AlgorithmOpType.GENERATE_KEYPAIR.test(this).print();
        AlgorithmOpType.SIGN.test(this).print();
        AlgorithmOpType.VERIFY.test(this).print();
    }


    void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public List<SignatureSuite> getSuites() {
        return suites;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setNumThreads(int numThreads) {
        if (numThreads <= 0) {
            throw new IllegalArgumentException("numTherads must be positive number, but you give [" + numThreads + "]");
        }
        this.numThreads = numThreads;
    }

    public int getNumThreads() {
        return numThreads;
    }
}
