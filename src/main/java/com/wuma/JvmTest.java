package com.wuma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wuma on 2017/2/25.
 */
public class JvmTest {
    public static String st_static = "hello static";
    public final String st_final = "hello final";
    public static final String st_final_str = "hello final static";

    public static void main(String[] args) throws IOException {
        JvmTest jvmTest=new JvmTest();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String str=br.readLine();
            if (str.length() == 0) {
                if (jvmTest.st_final==str){
                    System.out.println("1");
                }
                if (jvmTest.st_static==str){
                    System.out.println("2");
                }
                if (jvmTest.st_final_str==str){
                    System.out.println("3");
                }
                break;
            }
        }
    }
}
