package com.trustchain;

import com.trustchain.util.NameUtils;

import java.util.List;

/**
 * 签名算法的各种操作
 */
public enum AlgorithmOpType {
    GENERATE_KEYPAIR("生成密钥对") {
        @Override
        protected Process processLoop(SignatureSuite suite, int seconds) {
            int count = 0;
            long deadline = System.currentTimeMillis() + seconds * 1000;
            while (System.currentTimeMillis() < deadline) {
                suite.generateKeyPair();
                count++;
            }
            Process process = new Process(10, count);
            process.print();
            return process;
        }
    },
    SIGN("签名") {
        @Override
        protected Process processLoop(SignatureSuite suite, int seconds) {
            int count = 0;
            long deadline = System.currentTimeMillis() + seconds * 1000;
            while (System.currentTimeMillis() < deadline) {
                suite.sign();
                count++;
            }
            Process process = new Process(10, count);
            process.print();
            return process;
        }
    },
    VERIFY("校验") {
        @Override
        protected Process processLoop(SignatureSuite suite, int seconds) {
            int count = 0;
            long deadline = System.currentTimeMillis() + seconds * 1000;
            while (System.currentTimeMillis() < deadline) {
                suite.verify();
                count++;
            }
            Process process = new Process(10, count);
            process.print();
            return process;
        }
    };

    /**
     * 操作名
     */
    private String opName;

    /**
     *
     * @param opName 操作名
     */
    AlgorithmOpType(String opName) {
        this.opName = opName;
    }

    public Metrics test(SignatureTestCase testCase) {
        List<SignatureSuite> suites = testCase.getSuites();
        Metrics metrics = new Metrics(testCase.getAlgorithm(), this);
        for (SignatureSuite suite : suites) {
            int keySize = suite.getKeySize();
            metrics.put(keySize, process(suite, testCase));
        }
        return metrics;
    }

    private Process process(SignatureSuite suite, SignatureTestCase testCase) {
        return processLoop(suite, testCase.getSeconds());
    }

    abstract protected Process processLoop(SignatureSuite suite, int seconds);

    @Override
    public String toString() {
        return NameUtils.getHumnName(this.name());
    }

    public String getOpName() {
        return opName;
    }
}
