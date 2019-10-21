package com.hthyaq.zybadmin.controller.dataVisual;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hthyaq.zybadmin.controller.dataVisual.vo.NameValue;
import com.hthyaq.zybadmin.controller.dataVisual.vo.Scroll;
import com.hthyaq.zybadmin.controller.dataVisual.vo.StrList;
import com.hthyaq.zybadmin.model.entity.AreaOfDic;
import com.hthyaq.zybadmin.model.entity.BaseOfDic;
import com.hthyaq.zybadmin.model.entity.IndustryOfDic;
import com.hthyaq.zybadmin.service.AreaOfDicService;
import com.hthyaq.zybadmin.service.BaseOfDicService;
import com.hthyaq.zybadmin.service.IndustryOfDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/nationDangerVisual")
public class NationDangerVisual {
    @Autowired
    AreaOfDicService areaOfDicService;
    @Autowired
    IndustryOfDicService industryOfDicService;
    @Autowired
    BaseOfDicService baseOfDicService;

    /*
        来源：表2-40 企业职业病危害风险分级及管控措施
     */
    @GetMapping("/scroll")
    public Map<String, List<Scroll>> scroll() {
        Map<String, List<Scroll>> map = Maps.newHashMap();
        List<Scroll> data1List = Lists.newArrayList();
        List<Scroll> data2List = Lists.newArrayList();
        List<Scroll> data3List = Lists.newArrayList();
        List<Scroll> data4List = Lists.newArrayList();

        //模拟数据
        List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1));
        for (AreaOfDic tmp : areaList) {
            Scroll scroll = new Scroll();
            scroll.setKey(tmp.getId());
            scroll.setAreaName(tmp.getName());
            scroll.setLevelInt(RandomUtil.randomInt(1, 5));
            scroll.setCount(RandomUtil.randomInt(1, 500));
            if (scroll.getLevelInt().equals(1)) {
                scroll.setLevel("轻微风险(Ⅰ级)");
                data1List.add(scroll);
            } else if (scroll.getLevelInt().equals(2)) {
                scroll.setLevel("低度风险(Ⅱ级)");
                data2List.add(scroll);
            } else if (scroll.getLevelInt().equals(3)) {
                scroll.setLevel("中度风险(Ⅲ级)");
                data3List.add(scroll);
            } else if (scroll.getLevelInt().equals(4)) {
                scroll.setLevel("高度风险(Ⅳ级)");
                data4List.add(scroll);
            }
        }
        map.put("one", data1List);
        map.put("two", data2List);
        map.put("three", data3List);
        map.put("four", data4List);
        return map;
    }

    /*
        来源：表2-45 区域职业病危害风险分级及管控措施
     */
    @GetMapping("/option1")
    public Map<String, List<NameValue>> option1() {
        Map<String, List<NameValue>> map = Maps.newHashMap();
        List<NameValue> data0List = Lists.newArrayList();
        List<NameValue> data1List = Lists.newArrayList();
        List<NameValue> data2List = Lists.newArrayList();
        List<NameValue> data3List = Lists.newArrayList();
        List<NameValue> data4List = Lists.newArrayList();
        //模拟数据
        List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1));
        areaList.forEach(tmp -> {
            NameValue nameValue = new NameValue();
            nameValue.setName(tmp.getName());
            nameValue.setValue(RandomUtil.randomInt(0, 5));
            if (nameValue.getValue().equals(0)) {
                data0List.add(nameValue);
            } else if (nameValue.getValue().equals(1)) {
                data1List.add(nameValue);
            } else if (nameValue.getValue().equals(2)) {
                data2List.add(nameValue);
            } else if (nameValue.getValue().equals(3)) {
                data3List.add(nameValue);
            } else if (nameValue.getValue().equals(4)) {
                data4List.add(nameValue);
            }
        });
        map.put("zero", data0List);
        map.put("one", data1List);
        map.put("two", data2List);
        map.put("three", data3List);
        map.put("four", data4List);
        return map;
    }

    /*
    来源：表2-44 企业职业病危害风险分布情况
 */
    @GetMapping("/option2")
    public Map<String, List<Integer>> option2() {
        Map<String, List<Integer>> map = Maps.newHashMap();
        List<Integer> yearList = Lists.newArrayList(2017, 2018, 2019);
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
                tmp.add(RandomUtil.randomInt(1, 100));
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

    /*
    来源：表2-41 企业职业病危害风险分布情况（按行政区划统计）
     */
    @GetMapping("/option3")
    public StrList option3() {
        StrList strList = new StrList();
        List<String> flagList = Lists.newArrayList();
        List<Integer> data0List = Lists.newArrayList();
        List<Integer> data1List = Lists.newArrayList();
        List<Integer> data2List = Lists.newArrayList();
        List<Integer> data3List = Lists.newArrayList();
        //模拟数据
        List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1).notIn("name", "澳门", "台湾"));
        areaList.forEach(tmp -> {
            flagList.add(tmp.getName());
            data0List.add(RandomUtil.randomInt(1, 1000));
            data1List.add(RandomUtil.randomInt(1, 1000));
            data2List.add(RandomUtil.randomInt(1, 1000));
            data3List.add(RandomUtil.randomInt(1, 1000));
        });

        strList.setFlagList(flagList);
        strList.setZero(data0List);
        strList.setOne(data1List);
        strList.setTwo(data2List);
        strList.setThree(data3List);
        return strList;
    }

    /*
来源：表2-41 企业职业病危害风险分布情况（按行业统计）
 */
    @GetMapping("/option4")
    public StrList option4() {
        StrList strList = new StrList();
        List<String> flagList = Lists.newArrayList();
        List<Integer> data0List = Lists.newArrayList();
        List<Integer> data1List = Lists.newArrayList();
        List<Integer> data2List = Lists.newArrayList();
        List<Integer> data3List = Lists.newArrayList();
        //模拟数据
        List<IndustryOfDic> list = industryOfDicService.list(new QueryWrapper<IndustryOfDic>().eq("topid", -1));
        list.forEach(tmp -> {
            flagList.add(tmp.getName());
            data0List.add(RandomUtil.randomInt(1, 1000));
            data1List.add(RandomUtil.randomInt(1, 1000));
            data2List.add(RandomUtil.randomInt(1, 1000));
            data3List.add(RandomUtil.randomInt(1, 1000));
        });

        strList.setFlagList(flagList);
        strList.setZero(data0List);
        strList.setOne(data1List);
        strList.setTwo(data2List);
        strList.setThree(data3List);
        return strList;
    }

    /*
来源：表2-41 企业职业病危害风险分布情况（按登记注册类型统计）
*/
    @GetMapping("/option5")
    public StrList option5() {
        StrList strList = new StrList();
        List<String> flagList = Lists.newArrayList();
        List<Integer> data0List = Lists.newArrayList();
        List<Integer> data1List = Lists.newArrayList();
        List<Integer> data2List = Lists.newArrayList();
        List<Integer> data3List = Lists.newArrayList();
        //模拟数据
        List<BaseOfDic> list = baseOfDicService.list(new QueryWrapper<BaseOfDic>().eq("flag", "登记注册类型"));
        list.forEach(tmp -> {
            flagList.add(tmp.getBigName());
            data0List.add(RandomUtil.randomInt(1, 1000));
            data1List.add(RandomUtil.randomInt(1, 1000));
            data2List.add(RandomUtil.randomInt(1, 1000));
            data3List.add(RandomUtil.randomInt(1, 1000));
        });

        strList.setFlagList(flagList);
        strList.setZero(data0List);
        strList.setOne(data1List);
        strList.setTwo(data2List);
        strList.setThree(data3List);
        return strList;
    }

    /*
        词云
     */
    @GetMapping("/tagCloud")
    public List<NameValue> tagCloud() {
        List<NameValue> list = Lists.newArrayList();
        list.add(new NameValue("职业病", RandomUtil.randomInt(1000, 10000)));
        list.add(new NameValue("职业健康", RandomUtil.randomInt(1000, 10000)));
        list.add(new NameValue("尘肺病", RandomUtil.randomInt(1000, 10000)));
        list.add(new NameValue("职业病体检", RandomUtil.randomInt(1000, 10000)));
        list.add(new NameValue("工伤鉴定", RandomUtil.randomInt(1000, 10000)));
        list.add(new NameValue("职业病防治法", RandomUtil.randomInt(1000, 10000)));
        return list;
    }
}
