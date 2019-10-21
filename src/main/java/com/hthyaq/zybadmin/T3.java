package com.hthyaq.zybadmin;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.WordUtils;

public class T3 {
    public static void main(String[] args) throws ClassNotFoundException {
        String classNameStr="444";

        System.out.println(DigestUtils.md5Hex(classNameStr));
    }
}
