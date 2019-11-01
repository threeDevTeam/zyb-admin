package com.hthyaq.zybadmin;

import com.hthyaq.zybadmin.common.utils.AntdDateUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.WordUtils;

public class T3 {
    public static void main(String[] args) throws ClassNotFoundException {
        String s = String.valueOf(AntdDateUtil.getInteger("2019-10-03"));

        System.out.println(s );
    }
}
