package com.hthyaq.zybadmin.controller.dataVisual.nation;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hthyaq.zybadmin.common.utils.DoubleUtil;
import com.hthyaq.zybadmin.common.utils.cache.DataVisualCacheUtil;
import com.hthyaq.zybadmin.controller.dataVisual.vo.*;
import com.hthyaq.zybadmin.model.entity.AreaOfDic;
import com.hthyaq.zybadmin.model.entity.BaseOfDic;
import com.hthyaq.zybadmin.model.entity.IndustryOfDic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

//默认条件：当前年份
@RestController
@RequestMapping("/nationDangerVisual/no")
public class NationDangerVisualNo {
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
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);
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
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);
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

    @GetMapping("/option1Detail")
    public List<DangerFour> option1Detail() {
        List<DangerFour> list = Lists.newArrayList();
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);
        for (AreaOfDic areaOfDic : areaList) {
            DangerFour tmp = new DangerFour();
            tmp.setName(areaOfDic.getName());
            double d = RandomUtil.randomDouble(0.0, 5.0);
            double d2 = DoubleUtil.get(d);
            tmp.setVar1(d2);
            if (d2 < 0.8) {
                tmp.setVar2("暂无风险");
            } else if (d2 > 0.8 && d2 < 1.75) {
                tmp.setVar2("轻微风险(Ⅰ级)");
            } else if (d2 > 1.75 && d2 < 2.3) {
                tmp.setVar2("低度风险(Ⅱ级)");
            } else if (d2 > 2.3 && d < 4.0) {
                tmp.setVar2("中度风险(Ⅲ级)");
            } else {
                tmp.setVar2("高度风险(Ⅳ级)");
            }
            tmp.setVar3(RandomUtil.randomInt(1, 1000));
            tmp.setVar4(RandomUtil.randomInt(1, 1000));
            list.add(tmp);
        }
        return list;
    }

    /*
    来源：表2-44 企业职业病危害风险分布情况
 */
    @GetMapping("/option2")
    public Map<String, List<Integer>> option2() {
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
    public List<DangerThree> option2Detail() {
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
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);
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

    @GetMapping("/option3Detail")
    public List<DangerFour> option3Detail() {
        List<DangerFour> list = Lists.newArrayList();
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);
        for (AreaOfDic areaOfDic : areaList) {
            DangerFour tmp = new DangerFour();
            tmp.setName(areaOfDic.getName());
            tmp.setVar1(RandomUtil.randomInt(1, 1000));
            tmp.setVar2(RandomUtil.randomInt(1, 1000));
            tmp.setVar3(RandomUtil.randomInt(1, 1000));
            tmp.setVar4(RandomUtil.randomInt(1, 1000));

            list.add(tmp);
        }
        return list;
    }

    /*
来源：表2-41 企业职业病危害风险分布情况（按行业统计）
 */
    @GetMapping("/option4")
    public Map<String, List<Integer>> option4() {
        List<IndustryOfDic> list = DataVisualCacheUtil.getIndustryList();
        int size = list.size();

        Map<String, List<Integer>> map = Maps.newHashMap();
        //'暂无风险', '低度风险(Ⅰ级)', '中度风险(Ⅱ级)', '高度风险(Ⅲ级)'
        List<Integer> list1 = Lists.newArrayList();
        List<Integer> list2 = Lists.newArrayList();
        List<Integer> list3 = Lists.newArrayList();
        List<Integer> list4 = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            //
            int i1 = RandomUtil.randomInt(1, 100);
            list1.add(i1);
            //
            int i2 = RandomUtil.randomInt(1, 100);
            list2.add(i2);
            //
            int i3 = RandomUtil.randomInt(1, 100);
            list3.add(i3);
            //
            int i4 = RandomUtil.randomInt(1, 100);
            list4.add(i4);
        }
        map.put("list1", list1);
        map.put("list2", list2);
        map.put("list3", list3);
        map.put("list4", list4);
        return map;
    }

    @GetMapping("/option4Detail")
    public List<DangerFour> option4Detail() {
        List<DangerFour> list = Lists.newArrayList();
        List<IndustryOfDic> industryList = DataVisualCacheUtil.getIndustryList();
        for (IndustryOfDic industryOfDic : industryList) {
            DangerFour tmp = new DangerFour();
            tmp.setName(industryOfDic.getName());
            tmp.setVar1(RandomUtil.randomInt(1, 1000));
            tmp.setVar2(RandomUtil.randomInt(1, 1000));
            tmp.setVar3(RandomUtil.randomInt(1, 1000));
            tmp.setVar4(RandomUtil.randomInt(1, 1000));
            list.add(tmp);
        }
        return list;
    }

    /*
来源：表2-41 企业职业病危害风险分布情况（按登记注册类型统计）
*/
    @GetMapping("/option5")
    public Map<String, List<Integer>> option5() {
        List<BaseOfDic> list = DataVisualCacheUtil.getRegisterTypeList();
        int size = list.size();

        Map<String, List<Integer>> map = Maps.newHashMap();
        //'暂无风险', '低度风险(Ⅰ级)', '中度风险(Ⅱ级)', '高度风险(Ⅲ级)'
        List<Integer> list1 = Lists.newArrayList();
        List<Integer> list2 = Lists.newArrayList();
        List<Integer> list3 = Lists.newArrayList();
        List<Integer> list4 = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            //
            int i1 = RandomUtil.randomInt(1, 100);
            list1.add(i1);
            //
            int i2 = RandomUtil.randomInt(1, 100);
            list2.add(i2);
            //
            int i3 = RandomUtil.randomInt(1, 100);
            list3.add(i3);
            //
            int i4 = RandomUtil.randomInt(1, 100);
            list4.add(i4);
        }
        map.put("list1", list1);
        map.put("list2", list2);
        map.put("list3", list3);
        map.put("list4", list4);
        return map;
    }

    @GetMapping("/option5Detail")
    public List<DangerFour> option5Detail() {
        List<DangerFour> list = Lists.newArrayList();
        List<BaseOfDic> registerTypeList = DataVisualCacheUtil.getRegisterTypeList();
        for (BaseOfDic baseOfDic : registerTypeList) {
            DangerFour tmp = new DangerFour();
            tmp.setName(baseOfDic.getBigName());
            tmp.setVar1(RandomUtil.randomInt(1, 1000));
            tmp.setVar2(RandomUtil.randomInt(1, 1000));
            tmp.setVar3(RandomUtil.randomInt(1, 1000));
            tmp.setVar4(RandomUtil.randomInt(1, 1000));
            list.add(tmp);
        }
        return list;
    }

    /*
来源：表2-46 区域职业病危害风险分布情况
*/
    @GetMapping("/option6")
    public StrList option6() {
        StrList strList = new StrList();
        List<String> flagList = Lists.newArrayList();
        List<Integer> data0List = Lists.newArrayList();
        List<Integer> data1List = Lists.newArrayList();
        List<Integer> data2List = Lists.newArrayList();
        List<Integer> data3List = Lists.newArrayList();
        //模拟数据
        List<AreaOfDic> list = DataVisualCacheUtil.getAreaChildren("国家",null,null);
        for (AreaOfDic tmp : list) {
            flagList.add(tmp.getName());
            int i = tmp.getChildNum();
            if (i > 0) {
                int j0 = RandomUtil.randomInt(0, i);
                int j1 = RandomUtil.randomInt(0, i - j0);
                int j2 = RandomUtil.randomInt(0, i - j0 - j1);
                int j3 = i - j0 - j1 - j2;
                data0List.add(j0);
                data1List.add(j1);
                data2List.add(j2);
                data3List.add(j3);
            } else {
                data0List.add(0);
                data1List.add(0);
                data2List.add(0);
                data3List.add(0);
            }

        }
        strList.setFlagList(flagList);
        strList.setZero(data0List);
        strList.setOne(data1List);
        strList.setTwo(data2List);
        strList.setThree(data3List);
        return strList;
    }

    @GetMapping("/option6Detail")
    public List<DangerFour> option6Detail() {
        List<DangerFour> list = Lists.newArrayList();
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);
        for (AreaOfDic areaOfDic : areaList) {
            DangerFour tmp = new DangerFour();
            tmp.setName(areaOfDic.getName());
            int i = areaOfDic.getChildNum();
            if (i > 0) {
                int j0 = RandomUtil.randomInt(0, i);
                int j1 = RandomUtil.randomInt(0, i - j0);
                int j2 = RandomUtil.randomInt(0, i - j0 - j1);
                int j3 = i - j0 - j1 - j2;
                tmp.setVar1(j0);
                tmp.setVar2(j1);
                tmp.setVar3(j2);
                tmp.setVar4(j3);
            } else {
                tmp.setVar1(0);
                tmp.setVar2(0);
                tmp.setVar3(0);
                tmp.setVar4(0);
            }
            list.add(tmp);
        }
        return list;
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
