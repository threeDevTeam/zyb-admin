package com.hthyaq.zybadmin;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

public class ZZTest {
    public static void main(String[] args) throws IOException {

        List<String> list = Lists.newArrayList();
        String today = DateUtil.today();
        for (int i = 6; i > 0; i--) {
            list.add(DateUtil.offsetDay(DateUtil.date(), -i).toString("yyyy-MM-dd"));
        }
        list.add(today);
        System.out.println(list);
    }
}
