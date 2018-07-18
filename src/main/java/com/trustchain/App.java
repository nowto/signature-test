package com.trustchain;

import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    /**
     *
     */
    private static final byte[] DEFAULT_UNSIGHED_DATA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes(Charset.forName("UTF-8"));

    private UnsignedData unsignedData;

    @Option(name = "-seconds", usage = "运行秒数")
    private int seconds = 10;


    @Option(name = "-file", usage = "要签名的文件")
    private File file;

    @Option(name = "-content", usage = "要签名的一段字符串, 如果file被指定，该项不生效")
    private String content;

    @Option(name = "-encoding", usage = "要签名的一段字符串的编码，默认utf-8")
    private String endoding = "UTF-8";

    @Argument
    private List<String> algorithms = new ArrayList<>();

    public static final String ALGORITHM = "RSA";
    public static final String SHA256_WITH = "SHA256";
    private static byte[] data = new byte[0];
    public static final int KEYSIZE = 512;



    static {
        try {
            data = "Hello World".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        new App().doMain(args);
    }

    private void doMain(String[] args){
        initArgs(args);
        checkAlgorithms();
        initUnsighedData();
    }

    private void initArgs(String[] args) {
        ParserProperties properties = ParserProperties.defaults().withUsageWidth(80);
        CmdLineParser parser = new CmdLineParser(this, properties);

        try {
            parser.parseArgument(args);
            if (algorithms.isEmpty()) {
                throw new CmdLineException(parser, "No arguments is given");
            }
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("[options...] arguments...");
            parser.printUsage(System.err);
            System.err.println();
            return;
        }
        System.out.println(this);
    }

    private void initUnsighedData() {
        if (file != null && file.exists()) {
            try {
                unsignedData = new UnsignedData(file);
            } catch (IOException e) {
                System.err.println("您指定的文件不存在");
            }
        } else if (StringUtils.isNotEmpty(content)) {
            try {
                unsignedData = new UnsignedData(content, endoding);
            } catch (UnsupportedEncodingException e) {
                System.err.println("不支持的encoding");
            }
        } else {
            unsignedData = new UnsignedData(DEFAULT_UNSIGHED_DATA);
        }
    }

    private void checkAlgorithms() {
        Security.getAlgorithms("Signature");
    }


    @Override
    public String toString() {
        return "App{" +
                "seconds=" + seconds +
                ", file=" + file +
                ", content='" + content + '\'' +
                ", endoding='" + endoding + '\'' +
                ", algorithms=" + algorithms +
                '}';
    }
}
