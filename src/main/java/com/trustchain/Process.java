package com.trustchain;

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

    /**
     * 频率
     * @return
     */
    public double frequency() {
        return count / seconds;
    }

    /**
     * 每次用时
     * @return
     */
    public double onceTime() {
        return seconds / count;
    }

    void print() {
//        System.out.println("你好啊");
    }
}
