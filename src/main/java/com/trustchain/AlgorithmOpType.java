package com.trustchain;

import com.trustchain.util.NameUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 签名算法的各种操作
 */
public enum AlgorithmOpType {
    GENERATE_KEYPAIR("生成密钥对") {
        @Override
        protected Callable<Process> getTask(SignatureSuite suite, SignatureTestCase testCase) {
            Callable<Process> task = () -> {
                int count = 0;
                long deadline = System.currentTimeMillis() + testCase.getSeconds() * 1000;
                while (System.currentTimeMillis() < deadline) {
                    suite.generateKeyPair();
                    count++;
                }
                Process process = new Process(testCase.getSeconds(), count);
                return process;
            };
            return task;
        }
    },
    SIGN("签名") {
        @Override
        protected Callable<Process> getTask(SignatureSuite suite, SignatureTestCase testCase) {
            Callable<Process> task = () -> {
                int count = 0;
                long deadline = System.currentTimeMillis() + testCase.getSeconds() * 1000;
                while (System.currentTimeMillis() < deadline) {
                    suite.sign();
                    count++;
                }
                Process process = new Process(testCase.getSeconds(), count);
                return process;
            };
            return task;
        }
    },
    VERIFY("校验") {
        @Override
        protected Callable<Process> getTask(SignatureSuite suite, SignatureTestCase testCase) {
            Callable<Process> task = () -> {
                int count = 0;
                long deadline = System.currentTimeMillis() + testCase.getSeconds() * 1000;
                while (System.currentTimeMillis() < deadline) {
                    suite.verify();
                    count++;
                }
                Process process = new Process(testCase.getSeconds(), count);
                return process;
            };
            return task;
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
            Process process = process(suite, testCase);
            System.out.printf("Doing %d bit %s %s's for %ds: %d\n", keySize, this, testCase.getAlgorithm(), testCase.getSeconds(), process.getCount());
            metrics.put(keySize, process);
        }
        metrics.print();
        return metrics;
    }

    private Process process(SignatureSuite suite, SignatureTestCase testCase) {
        Callable<Process> task = getTask(suite, testCase);
        if (testCase.getNumThreads() == 1) {
            try {
                return task.call();
            } catch (Exception e) {
                return null;
            }
        } else {
            ExecutorService pool = Executors.newFixedThreadPool(testCase.getNumThreads());
            try {
                List<Future<Process>> futures = pool.invokeAll(Arrays.asList(task, task));
                pool.shutdown();
                List<Process> proceses = mapToProcess(futures);
                Process p = new Process(proceses);
                return p;
            } catch (InterruptedException e) {
                return null;
            }
        }
    }

    private List<Process> mapToProcess(List<Future<Process>> futures) {
        if (futures == null || futures.size() == 0) {
            return Collections.emptyList();
        } else {
            List<Process> processes = new ArrayList<>();
            for (Future<Process> future: futures) {
                try {
                    processes.add(future.get());
                } catch (Exception e) {
                    continue;
                }
            }
            return processes;
        }
    }

    abstract protected Callable<Process> getTask(SignatureSuite suite, SignatureTestCase testCase);

    @Override
    public String toString() {
        return NameUtils.getHumnName(this.name());
    }

    public String getOpName() {
        return opName;
    }
}
