package org.example.pdfbox.test;

import java.util.Base64;

/**
 * @author zhouxs-a
 * @version 1.0
 * @description: TODO
 * @date 2023/11/9 20:42
 */
public class Main {

    public static void main(String[] args) {
        byte[] decodedBytes = Base64.getDecoder().decode("gZVEbo024y5Pw40qoF+Rw+hOpMYpssw==");
        String decodedString = new String(decodedBytes);
        System.out.println(decodedString);
    }
}
