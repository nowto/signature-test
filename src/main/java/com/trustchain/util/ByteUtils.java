package com.trustchain.util;

import java.util.Random;

public class ByteUtils {
    private static final int BITS_ONEBYTE = 8;

    public static byte[] randomBytes(int bits) {
        byte[] bytes = new byte[len(bits)];

        Random ran = new Random();
        ran.nextBytes(bytes);
        return bytes;
    }

    private static int len(int bits) {
        int quotient = bits / BITS_ONEBYTE;
        int remainder = bits % BITS_ONEBYTE;
        return remainder == 0 ? quotient : quotient + 1;
    }
}
