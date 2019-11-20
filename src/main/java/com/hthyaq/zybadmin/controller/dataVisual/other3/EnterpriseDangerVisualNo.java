package com.hthyaq.zybadmin.controller.dataVisual.other3;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hthyaq.zybadmin.common.utils.cache.DataVisualCacheUtil;
import com.hthyaq.zybadmin.controller.dataVisual.vo.DangerThree;
import com.hthyaq.zybadmin.controller.dataVisual.vo.Scroll;
import com.hthyaq.zybadmin.model.entity.AreaOfDic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enterpriseDangerVisual/no")
public class EnterpriseDangerVisualNo {
    /*
        来源：表2-40 企业职业病危害风险分级及管控措施
     */
    @GetMapping("/scroll")
    public List<Scroll> scroll(String name1, String name2, String name3, String name) {
        List<Scroll> list = Lists.newArrayList();
        //模拟数据
        AreaOfDic areaOfDic = DataVisualCacheUtil.getAreaSelf(name1, name2, name3);
        Scroll scroll = new Scroll();
        scroll.setKey(areaOfDic.getId());
        scroll.setAreaName(areaOfDic.getName());
        scroll.setLevelInt(RandomUtil.randomInt(1, 5));
        scroll.setCount(RandomUtil.randomInt(1, 500));
        if (scroll.getLevelInt().equals(1)) {
            scroll.setLevel("轻微风险(Ⅰ级)");
        } else if (scroll.getLevelInt().equals(2)) {
            scroll.setLevel("低度风险(Ⅱ级)");
        } else if (scroll.getLevelInt().equals(3)) {
            scroll.setLevel("中度风险(Ⅲ级)");
        } else if (scroll.getLevelInt().equals(4)) {
            scroll.setLevel("高度风险(Ⅳ级)");
        }

        list.add(scroll);
        return list;
    }

    /*
来源：表2-44 企业职业病危害风险分布情况
*/
    @GetMapping("/option2")
    public Map<String, List<Integer>> option2(String name1, String name2, String name3, String name) {
        Map<String, List<Integer>> map = Maps.newHashMap();
        int year = LocalDateTime.now().getYear();
        List<Integer> yearList = Lists.newArrayList();
        yearList.add(year - 2);
        yearList.add(year - 1);
        yearList.add(year);
        //模拟数据
        int size = yearList.size();
        //zero
        List<Integer> zeroList = Lists.newArrayList();
        for (int j = 0; j < size; j++) {
            zeroList.add(RandomUtil.randomInt(1, 100));
        }
        map.put("zero", zeroList);
        //
        for (int i = 0; i < size; i++) {
            List<Integer> tmp = Lists.newArrayList();
            for (int j = 0; j < size; j++) {
                tmp.add(RandomUtil.randomInt(1, 1000));
            }
            if (i == 0) {
                map.put("one", tmp);
            } else if (i == 1) {
                map.put("two", tmp);
            } else if (i == 2) {
                map.put("three", tmp);
            }
        }
        map.put("yearList", yearList);
        return map;
    }

    @GetMapping("/option2Detail")
    public List<DangerThree> option2Detail(String name1, String name2, String name3, String name) {
        List<DangerThree> list = Lists.newArrayList();
        int currentYear = LocalDateTime.now().getYear();
        for (int i = currentYear; i > (currentYear - 3); i--) {
            DangerThree tmp = new DangerThree();
            tmp.setYear(i);
            tmp.setVar1(RandomUtil.randomInt(1, 1000));
            tmp.setVar2(RandomUtil.randomInt(1, 1000));
            tmp.setVar3(RandomUtil.randomInt(1, 1000));
            list.add(tmp);
        }
        return list;
    }
}
