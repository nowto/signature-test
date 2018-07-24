package com.trustchain.util;

import org.junit.Test;

public class ByteUtilsTest {

    @Test
    public void random() {
        byte[] ret = ByteUtils.randomBytes(512);
//        Assert.assertTrue(Arrays.stream(ret));
    }
}