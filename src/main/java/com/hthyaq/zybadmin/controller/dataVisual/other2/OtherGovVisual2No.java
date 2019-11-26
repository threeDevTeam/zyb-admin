package com.hthyaq.zybadmin.controller.dataVisual.other2;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hthyaq.zybadmin.common.utils.DoubleUtil;
import com.hthyaq.zybadmin.common.utils.cache.DataVisualCacheUtil;
import com.hthyaq.zybadmin.controller.dataVisual.vo.*;
import com.hthyaq.zybadmin.model.entity.AreaOfDic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/otherGovVisual2/no")
public class OtherGovVisual2No {
    //表2-29 职业健康监管资源统计分析表
    @GetMapping("/option1")
    public List<NameValue> option1(String year, String name1, String name2, String name3) {
        List<NameValue> list = Lists.newArrayList();
        //6个字段
        //行政区划、行政区划内用人单位数（个）、行政区划内从业人员数（人）、职业健康监管人员数（人）、取得执法资格证书职业健康监管人员数（人）、职业健康监管装备数量（台/套）、在用职业健康监管装备数量（台/套）
        list.add(new NameValue("用人单位数", RandomUtil.randomInt(1, 10000)));
        list.add(new NameValue("从业人员数", RandomUtil.randomInt(1, 10000)));
        list.add(new NameValue("职业健康监管人员数", RandomUtil.randomInt(1, 10000)));
        list.add(new NameValue("取得执法资格证书职业健康监管人员数", RandomUtil.randomInt(1, 10000)));
        return list;
    }

    @GetMapping("/option1Detail")
    public List<GovSix> option1Detail(String year, String name1, String name2, String name3) {
        List<GovSix> list = Lists.newArrayList();
        //6个字段
        //行政区划、行政区划内用人单位数（个）、行政区划内从业人员数（人）、职业健康监管人员数（人）、取得执法资格证书职业健康监管人员数（人）、职业健康监管装备数量（台/套）、在用职业健康监管装备数量（台/套）
        AreaOfDic areaOfDic = DataVisualCacheUtil.getAreaSelf(name1, name2, name3);
        GovSix govSeven = new GovSix();
        govSeven.setArea(areaOfDic.getName());
        govSeven.setVar1(RandomUtil.randomInt(1, 10000));
        govSeven.setVar2(RandomUtil.randomInt(1, 10000));
        govSeven.setVar3(RandomUtil.randomInt(1, 10000));
        govSeven.setVar4(RandomUtil.randomInt(1, 10000));
        govSeven.setVar5(RandomUtil.randomInt(1, 10000));
        govSeven.setVar6(RandomUtil.randomInt(1, 10000));
        list.add(govSeven);
        return list;
    }

    //表2-30 职业健康法规标准建设统计分析表
    @GetMapping("/option2")
    public Map<String, List<Integer>> option2(String year, String name1, String name2, String name3) {
        Map<String, List<Integer>> map = Maps.newHashMap();
        //新增、累计
        List<Integer> list1 = Lists.newArrayList();
        List<Integer> list2 = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            //累计
            int i1 = RandomUtil.randomInt(1, 1000);
            list1.add(i1);
            //新增
            list2.add(RandomUtil.randomInt(1, i1));
        }
        map.put("list1", list1);
        map.put("list2", list2);
        return map;
    }

    @GetMapping("/option2Detail")
    public List<Twenty> option2Detail(String year, String name1, String name2, String name3) {
        List<Twenty> list = Lists.newArrayList();
        AreaOfDic areaOfDic = DataVisualCacheUtil.getAreaSelf(name1, name2, name3);
        Twenty tmp = new Twenty();
        tmp.setName(areaOfDic.getName());
        tmp.setVar1(RandomUtil.randomInt(1, 10000));
        tmp.setVar2(RandomUtil.randomInt(1, 10000));
        tmp.setVar3(RandomUtil.randomInt(1, 10000));
        tmp.setVar4(RandomUtil.randomInt(1, 10000));
        tmp.setVar5(RandomUtil.randomInt(1, 10000));
        tmp.setVar6(RandomUtil.randomInt(1, 10000));
        list.add(tmp);
        return list;
    }

    //表2-31 职业健康监督执法统计分析表
    @GetMapping("/option3")
    public List<GovEight> option3(String year, String name1, String name2, String name3) {
        List<GovEight> list = Lists.newArrayList();
        //8个字段
        AreaOfDic areaOfDic = DataVisualCacheUtil.getAreaSelf(name1, name2, name3);
        GovEight govEight = new GovEight();
        govEight.setArea(areaOfDic.getName());
        govEight.setVar1(RandomUtil.randomInt(1, 10000));
        govEight.setVar2(RandomUtil.randomInt(1, 10000));
        govEight.setVar3(RandomUtil.randomInt(1, 10000));
        govEight.setVar4(RandomUtil.randomInt(1, 10000));
        govEight.setVar5(RandomUtil.randomInt(1, 10000));

        govEight.setVar6(RandomUtil.randomInt(1, 10000));

        govEight.setVar6(RandomUtil.randomInt(1, 10000));

        govEight.setVar7(RandomUtil.randomInt(1, 10000));
        govEight.setVar8(RandomUtil.randomInt(1, 10000));
        list.add(govEight);
        return list;
    }

    //表2-32 职业卫生“三同时”监督执法统计分析表
    @GetMapping("/option4")
    public List<GovSeven> option4(String year, String name1, String name2, String name3) {
        List<GovSeven> list = Lists.newArrayList();
        //7个字段
        AreaOfDic areaOfDic = DataVisualCacheUtil.getAreaSelf(name1, name2, name3);
        GovSeven govSeven = new GovSeven();
        govSeven.setArea(areaOfDic.getName());
        govSeven.setVar1(RandomUtil.randomInt(1, 10000));
        govSeven.setVar2(RandomUtil.randomInt(1, 10000));
        govSeven.setVar3(RandomUtil.randomInt(1, 10000));
        govSeven.setVar4(RandomUtil.randomInt(1, 10000));

        govSeven.setVar5(DoubleUtil.get(RandomUtil.randomDouble(1, 10000)));

        govSeven.setVar5((double)RandomUtil.randomInt(1, 10000));

        govSeven.setVar6(RandomUtil.randomInt(1, 10000));
        govSeven.setVar7(RandomUtil.randomInt(1, 10000));
        list.add(govSeven);
        return list;
    }

    //表2-33 技术服务机构监管情况统计分析表
    @GetMapping("/option5")
    public List<NameValue> option5(String year, String name1, String name2, String name3) {
        List<NameValue> list = Lists.newArrayList();
        //6个字段
        list.add(new NameValue("技术服务机构", RandomUtil.randomInt(1, 10000)));
        list.add(new NameValue("健康检查机构", RandomUtil.randomInt(1, 10000)));
        list.add(new NameValue("诊断机构", RandomUtil.randomInt(1, 10000)));
        return list;
    }

    @GetMapping("/option5Detail")
    public List<Twenty> option5Detail(String year, String name1, String name2, String name3) {
        List<Twenty> list = Lists.newArrayList();
        AreaOfDic areaOfDic = DataVisualCacheUtil.getAreaSelf(name1, name2, name3);
        Twenty tmp = new Twenty();
        tmp.setName(areaOfDic.getName());
        tmp.setVar1(RandomUtil.randomInt(1, 10000));
        tmp.setVar2(RandomUtil.randomInt(1, 10000));
        tmp.setVar3(RandomUtil.randomInt(1, 10000));
        tmp.setVar4(RandomUtil.randomInt(1, 10000));
        tmp.setVar5(RandomUtil.randomInt(1, 10000));
        tmp.setVar6(RandomUtil.randomInt(1, 10000));
        tmp.setVar7(RandomUtil.randomInt(1, 10000));
        tmp.setVar8(RandomUtil.randomInt(1, 10000));
        tmp.setVar9(RandomUtil.randomInt(1, 10000));
        tmp.setVar10(RandomUtil.randomInt(1, 10000));
        list.add(tmp);
        return list;
    }

    //表2-34 技术服务机构监管情况统计分析表（续）
    @GetMapping("/option6")
    public Map<String, List<Integer>> option6(String year, String name1, String name2, String name3) {
        AreaOfDic areaOfDic = DataVisualCacheUtil.getAreaSelf(name1, name2, name3);
        Map<String, List<Integer>> map = Maps.newHashMap();
        //检查机构数、处罚机构数、罚款金额、吊销资质数
        List<Integer> list1 = Lists.newArrayList();
        List<Integer> list2 = Lists.newArrayList();
        List<Integer> list3 = Lists.newArrayList();
        List<Integer> list4 = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            //
            int i1 = RandomUtil.randomInt(1, 1000);
            list1.add(i1);
            //
            int i2 = RandomUtil.randomInt(1, 1000);
            list2.add(i2);
            //
            int i3 = RandomUtil.randomInt(1, 1000);
            list3.add(i3);
            //
            int i4 = RandomUtil.randomInt(1, 1000);
            list4.add(i4);
        }
        map.put("list1", list1);
        map.put("list2", list2);
        map.put("list3", list3);
        map.put("list4", list4);
        return map;
    }

    @GetMapping("/option6Detail")
    public List<Twenty> option6Detail(String year, String name1, String name2, String name3) {
        List<Twenty> list = Lists.newArrayList();
        AreaOfDic areaOfDic = DataVisualCacheUtil.getAreaSelf(name1, name2, name3);
        Twenty tmp = new Twenty();
        tmp.setName(areaOfDic.getName());
        tmp.setVar1(RandomUtil.randomInt(1, 10000));
        tmp.setVar2(RandomUtil.randomInt(1, 10000));
        tmp.setVar3(RandomUtil.randomInt(1, 10000));
        tmp.setVar4(RandomUtil.randomInt(1, 10000));
        tmp.setVar5(RandomUtil.randomInt(1, 10000));
        tmp.setVar6(RandomUtil.randomInt(1, 10000));
        tmp.setVar7(RandomUtil.randomInt(1, 10000));
        tmp.setVar8(RandomUtil.randomInt(1, 10000));
        tmp.setVar9(RandomUtil.randomInt(1, 10000));
        tmp.setVar10(RandomUtil.randomInt(1, 10000));
        tmp.setVar11(RandomUtil.randomInt(1, 10000));
        tmp.setVar12(RandomUtil.randomInt(1, 10000));
        list.add(tmp);
        return list;
    }

    //表2-35 职业病危害事故统计分析表
    @GetMapping("/option7")
    public Map<String, List<Integer>> option7(String year, String name1, String name2, String name3) {
        AreaOfDic areaOfDic = DataVisualCacheUtil.getAreaSelf(name1, name2, name3);

        Map<String, List<Integer>> map = Maps.newHashMap();
        //事故数、事故人数、死亡人数
        List<Integer> list1 = Lists.newArrayList();
        List<Integer> list2 = Lists.newArrayList();
        List<Integer> list3 = Lists.newArrayList();

        //
        int i1 = RandomUtil.randomInt(1, 1000);
        list1.add(i1);
        //
        int i2 = RandomUtil.randomInt(1, 1000);
        list2.add(i2);
        //
        int i3 = RandomUtil.randomInt(1, 1000);
        list3.add(i3);

        map.put("list1", list1);
        map.put("list2", list2);
        map.put("list3", list3);
        return map;
    }

    @GetMapping("/option7Detail")
    public List<Twenty> option7Detail(String year, String name1, String name2, String name3) {
        List<Twenty> list = Lists.newArrayList();
        AreaOfDic areaOfDic = DataVisualCacheUtil.getAreaSelf(name1, name2, name3);
        Twenty tmp = new Twenty();
        tmp.setName(areaOfDic.getName());
        tmp.setVar1(RandomUtil.randomInt(1, 10000));
        tmp.setVar2(RandomUtil.randomInt(1, 10000));
        tmp.setVar3(RandomUtil.randomInt(1, 10000));
        tmp.setVar4(RandomUtil.randomInt(1, 10000));
        tmp.setVar5(RandomUtil.randomInt(1, 10000));
        tmp.setVar6(RandomUtil.randomInt(1, 10000));
        tmp.setVar7(RandomUtil.randomInt(1, 10000));
        tmp.setVar8(RandomUtil.randomInt(1, 10000));
        tmp.setVar9(RandomUtil.randomInt(1, 10000));
        tmp.setVar10(RandomUtil.randomInt(1, 10000));
        list.add(tmp);
        return list;
    }
}
