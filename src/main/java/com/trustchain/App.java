package com.trustchain;

import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 启动类
 */
public class App {
    /**
     *
     */


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
        initAndCheck(args);
        startTest();
    }



    private void initAndCheck(String[] args) {
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
        initUnsighedData();
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
        }
    }

    private void startTest() {
        for (String algorithm : algorithms) {
            SignatureTestCase signatureTestCase;
            try {
                signatureTestCase = new SignatureTestCase(algorithm);
            } catch (NoSuchAlgorithmException e) {
                System.out.println("不支持该算法： " + algorithm);
                return;
            }

            signatureTestCase.test();
        }
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
