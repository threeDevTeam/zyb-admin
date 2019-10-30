package com.hthyaq.zybadmin;

import cn.hutool.core.util.RandomUtil;

import java.io.IOException;

public class ZZTest {
    public static void main(String[] args) throws IOException {
        int i=10;
        int j0 = RandomUtil.randomInt(0, i);
        int j1 = RandomUtil.randomInt(0, i - j0);
        int j2 = RandomUtil.randomInt(0, i - j0 - j1);
        int j3 = i - j0 - j1 - j2;
        System.out.println(j0+"-"+j1+"-"+j2+"-"+j3);
    }
}
