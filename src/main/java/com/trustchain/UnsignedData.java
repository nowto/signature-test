package com.trustchain;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class UnsignedData extends Data {

    public UnsignedData(byte[] data) {
        super(data);
    }

    public UnsignedData(File file) throws IOException {
        super(FileUtils.readFileToByteArray(file));
    }

    public UnsignedData(String content) throws UnsupportedEncodingException {
        this(content, "UTF-8");
    }

    public UnsignedData(String content, String charset) throws UnsupportedEncodingException {
        super(content.getBytes(charset));
    }

    public UnsignedData(String content, Charset charset) {
        super(content.getBytes(charset));
    }

}
