package com.trustchain.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ByteUtilsTest {

    @Test
    public void random() {
        byte[] ret = ByteUtils.random(512);
//        Assert.assertTrue(Arrays.stream(ret));
    }
}