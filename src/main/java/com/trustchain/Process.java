package com.trustchain;

import java.util.List;

/**
 * 代表用户指定seconds的那段过程
 */
public class Process {
    /**
     * 实际用时
     */
    private double seconds;

    /**
     * 完成了多少次
     */
    private int count;

    public Process(double seconds, int count) {
        this.seconds = seconds;
        this.count = count;
    }

    public Process(List<Process> processes) {
        if (processes == null || processes.size() == 0) {
            this.seconds = 0;
            this.count = 0;
            return;
        }

        int sumSeconds = 0;
        int sumCount = 0;
        for (Process process : processes) {
            sumSeconds += process.seconds;
            sumCount += process.count;
        }
        this.seconds = sumSeconds / processes.size();
        this.count = sumCount;
    }
    /**
     * 频率
     * @return
     */
    public double frequency() {
        return  Math.round(count / seconds);
    }

    /**
     * 每次用时
     * @return
     */
    public double onceTime() {
        return seconds / count;
    }

    public int getCount() {
        return count;
    }
}
