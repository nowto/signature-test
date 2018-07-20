package com.trustchain;

import com.trustchain.util.NameUtils;

/**
 * 签名算法的各种操作
 */
public enum AlgorithmOpType {
    GENERATE_KEYPAIR("生成密钥对"),
    SIGN("签名"),
    VERIFY("校验");

    /**
     * 操作名
     */
    private String opName;

    AlgorithmOpType(String opName) {
        this.opName = opName;
    }

    @Override
    public String toString() {
        return NameUtils.getHumnName(this.name());
    }

    public String getOpName() {
        return opName;
    }
}
