package com.hthyaq.zybadmin.controller.dataVisual.yuqing;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hthyaq.zybadmin.controller.dataVisual.vo.NameValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/yuQingVisual/no")
public class YuQingVisualNo {
    //获取近七天的日期
    private List<String> getSevenDate() {
        List<String> list = Lists.newArrayList();
        String today = DateUtil.today();
        for (int i = 6; i > 0; i--) {
            list.add(DateUtil.offsetDay(DateUtil.date(), -i).toString("yyyy-MM-dd"));
        }
        list.add(today);
        return list;
    }

    @GetMapping("/option1")
    public Map<String, Object> option1() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("date", getSevenDate());
        ArrayList<Integer> list = Lists.newArrayList();
        for (int i = 0; i < 7; i++) {
            list.add(RandomUtil.randomInt(1, 100));
        }
        map.put("data", list);

        return map;
    }

    @GetMapping("/option2")
    public Map<String, Object> option2() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("date", getSevenDate());
        ArrayList<Integer> list = Lists.newArrayList();
        for (int i = 0; i < 7; i++) {
            list.add(RandomUtil.randomInt(1, 200));
        }
        map.put("data", list);

        return map;
    }

    @GetMapping("/option3")
    public List<NameValue> option3() {
        List<NameValue> list = Lists.newArrayList();
        list.add(new NameValue("积极态度", RandomUtil.randomInt(1, 10000)));
        list.add(new NameValue("中立态度", RandomUtil.randomInt(1, 10000)));
        list.add(new NameValue("消极态度", RandomUtil.randomInt(1, 10000)));
        return list;
    }

    @GetMapping("/option4")
    public Map<String, List<Integer>> option4() {
        Map<String, List<Integer>> map = Maps.newHashMap();
        //来源
        List<Integer> list = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            list.add(RandomUtil.randomInt(1, 10000));
        }
        map.put("list", list);
        return map;
    }
}
