package com.wuma;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by user on 17/4/12.
 */
public class StrTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String str="http://ssjj.com?apx=jj&tcketn=22222|8888|99999|&x=j";
        System.out.println(str);
        System.out.println(str.replace("|&","&"));
        System.out.println(str.replace("|", URLEncoder.encode("|","utf8")));
        System.out.println(str.substring(0,str.length()-1));
    }
}
