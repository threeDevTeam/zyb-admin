package com.hthyaq.zybadmin;

import com.hthyaq.zybadmin.common.utils.AntdDateUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.WordUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Formatter;

public class T3 {
    public static void main(String[] args) {
        String user_key = "eed2863a242541ee9da5515f5a656a0b";   //实际使用时客户需修改为自己的user_key
        String user_secret = "aa260da228f74b23b5751c966ae34674";   //实际使用时客户需修改为自己的user_secret
        long timestamp = Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000));
        String data = user_key + timestamp;
        String hmac = encode(data, user_secret);
        System.out.println(hmac);

    }
    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);

        }
        return formatter.toString();

    }
    private static String encode(String data, String key) {
        String token = null;
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            token = toHexString(mac.doFinal(data.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();

        }
        return token;
    }
}
