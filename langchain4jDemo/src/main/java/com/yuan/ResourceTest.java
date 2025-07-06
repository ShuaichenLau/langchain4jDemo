package com.yuan;

import java.io.InputStream;

public class ResourceTest {
    public static void main(String[] args) {
        try (InputStream is = ResourceTest.class.getClassLoader().getResourceAsStream("zhaozhi-prompt-template.txt")) {
            if (is != null) {
                System.out.println("Resource found!");
                // 可以在这里读取内容
                // byte[] bytes = is.readAllBytes();
                // System.out.println(new String(bytes));
            } else {
                System.err.println("Resource NOT found!");
                System.err.println("Current working directory: " + System.getProperty("user.dir"));
                // 打印类路径，帮助调试
                // String classpath = System.getProperty("java.class.path");
                // System.out.println("Classpath: " + classpath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}