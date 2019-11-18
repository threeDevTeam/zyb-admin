package com.hthyaq.zybadmin.controller.dataVisual.other2;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.hthyaq.zybadmin.common.utils.cache.DataVisualCacheUtil;
import com.hthyaq.zybadmin.controller.dataVisual.vo.Scroll;
import com.hthyaq.zybadmin.model.entity.AreaOfDic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/otherDangerVisual2/no")
public class OtherDangerVisual2No {
    /*
        来源：表2-40 企业职业病危害风险分级及管控措施
     */
    @GetMapping("/scroll")
    public List<Scroll> scroll(String name1, String name2, String name3) {
        List<Scroll> list= Lists.newArrayList();
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
}
