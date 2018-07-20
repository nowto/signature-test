package com.trustchain;

import org.kohsuke.args4j.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 启动类
 */
public class App {

    @Option(name = "-seconds", usage = "运行秒数")
    private int seconds = 10;

    @Argument
    private List<String> algorithms = new ArrayList<>();


    public static void main(String[] args) {
        new App().doMain(args);
    }

    private void doMain(String[] args){
        init(args);
        startTest();
    }



    private void init(String[] args) {
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
    }

    private void startTest() {
        for (String algorithm : algorithms) {
            SignatureTestCase signatureTestCase;
            try {
                signatureTestCase = new SignatureTestCase(algorithm);
                signatureTestCase.setSeconds(this.seconds);
            } catch (NoSuchAlgorithmException e) {
                System.out.println("不支持该算法： " + algorithm);
                continue;
            }

            signatureTestCase.test();
        }
    }
}
