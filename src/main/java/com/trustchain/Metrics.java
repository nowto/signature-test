package com.trustchain;

import java.util.Map;
import java.util.TreeMap;

/**
 * 代表一个操作性能分析结果
 */
public class Metrics {
    private String algorithm;
    AlgorithmOpType opType;
    TreeMap<Integer, Process> processes = new TreeMap<>();

    public Metrics(String algorithm, AlgorithmOpType opType) {
        this.algorithm = algorithm;
        this.opType = opType;
    }

    public void put(int keySize, Process process) {
        processes.put(keySize, process);
    }

    public void print() {
        System.out.println("\t\t\t" + opType + "\t" + opType + "/s");
        for (Map.Entry<Integer, Process> processEntry : processes.entrySet()) {
            System.out.printf("%s\t%d bits\t%f\t%f", algorithm, processEntry.getKey(), processEntry.getValue().onceTime(), processEntry.getValue().frequency());
        }
    }
}
