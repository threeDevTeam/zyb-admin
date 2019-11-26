package com.hthyaq.zybadmin.controller.dataVisual.nationyes;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hthyaq.zybadmin.common.utils.cache.DataVisualCacheUtil;
import com.hthyaq.zybadmin.controller.dataVisual.vo.GovEight;
import com.hthyaq.zybadmin.controller.dataVisual.vo.GovSeven;
import com.hthyaq.zybadmin.controller.dataVisual.vo.GovSix;
import com.hthyaq.zybadmin.controller.dataVisual.vo.Twenty;
import com.hthyaq.zybadmin.model.entity.AreaOfDic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/nationGovVisual/yes")
public class NationGovVisualYes {
    //表2-29 职业健康监管资源统计分析表
    @GetMapping("/option1")
    public List<GovSix> option1(String year) {
        List<GovSix> list = Lists.newArrayList();
        //6个字段
        //行政区划、行政区划内用人单位数（个）、行政区划内从业人员数（人）、职业健康监管人员数（人）、取得执法资格证书职业健康监管人员数（人）、职业健康监管装备数量（台/套）、在用职业健康监管装备数量（台/套）
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);
        for (AreaOfDic areaOfDic : areaList) {
            GovSix govSeven = new GovSix();
            govSeven.setArea(areaOfDic.getName());
            govSeven.setVar1(RandomUtil.randomInt(1, 10000));
            govSeven.setVar2(RandomUtil.randomInt(1, 10000));
            govSeven.setVar3(RandomUtil.randomInt(1, 10000));
            govSeven.setVar4(RandomUtil.randomInt(1, 10000));
            govSeven.setVar5(RandomUtil.randomInt(1, 10000));
            govSeven.setVar6(RandomUtil.randomInt(1, 10000));
            list.add(govSeven);
        }
        return list;
    }

    //表2-30 职业健康法规标准建设统计分析表
    //这里行政区划是倒序
    //['香港', '新疆', '宁夏', '青海', '甘肃', '陕西', '西藏', '云南', '贵州', '四川', '重庆', '海南', '广西', '广东', '湖南', '湖北', '河南', '山东', '江西', '福建', '安徽', '浙江', '江苏', '上海', '黑龙江', '吉林', '辽宁', '内蒙古', '山西', '河北', '天津', '北京']
    @GetMapping("/option2")
    public Map<String, List<Integer>> option2(String year) {
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);

        Map<String, List<Integer>> map = Maps.newHashMap();
        //印发法律法规累计、印发规范性文件累计、印发标准累计
        List<Integer> list1 = Lists.newArrayList();
        List<Integer> list2 = Lists.newArrayList();
        List<Integer> list3 = Lists.newArrayList();
        //模拟数据
        int size = areaList.size();
        for (int i = size - 1; i > -1; i--) {
            list1.add(RandomUtil.randomInt(1, 1000));
            list2.add(RandomUtil.randomInt(1, 1000));
            list3.add(RandomUtil.randomInt(1, 1000));
        }
        map.put("list1", list1);
        map.put("list2", list2);
        map.put("list3", list3);
        return map;
    }

    @GetMapping("/option2Detail")
    public List<Twenty> option2Detail(String year) {
        List<Twenty> list = Lists.newArrayList();
        List<String> nationList = DataVisualCacheUtil.getAreaStrChildren("国家",null,null);
        for (String s : nationList) {
            Twenty tmp = new Twenty();
            tmp.setName(s);
            tmp.setVar1(RandomUtil.randomInt(1, 10000));
            tmp.setVar2(RandomUtil.randomInt(1, 10000));
            tmp.setVar3(RandomUtil.randomInt(1, 10000));
            tmp.setVar4(RandomUtil.randomInt(1, 10000));
            tmp.setVar5(RandomUtil.randomInt(1, 10000));
            tmp.setVar6(RandomUtil.randomInt(1, 10000));
            list.add(tmp);
        }
        return list;
    }

    //表2-31 职业健康监督执法统计分析表
    @GetMapping("/option3")
    public List<GovEight> option3(String year) {
        List<GovEight> list = Lists.newArrayList();
        //8个字段
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);
        for (AreaOfDic areaOfDic : areaList) {
            GovEight govEight = new GovEight();
            govEight.setArea(areaOfDic.getName());
            govEight.setVar1(RandomUtil.randomInt(1, 10000));
            govEight.setVar2(RandomUtil.randomInt(1, 10000));
            govEight.setVar3(RandomUtil.randomInt(1, 10000));
            govEight.setVar4(RandomUtil.randomInt(1, 10000));
            govEight.setVar5(RandomUtil.randomInt(1, 10000));
            govEight.setVar6(RandomUtil.randomInt(1, 10000));
            govEight.setVar7(RandomUtil.randomInt(1, 10000));
            govEight.setVar8(RandomUtil.randomInt(1, 10000));
            list.add(govEight);
        }
        return list;
    }

    //表2-32 职业卫生“三同时”监督执法统计分析表
    @GetMapping("/option4")
    public List<GovSeven> option4(String year) {
        List<GovSeven> list = Lists.newArrayList();
        //7个字段
        //行政区划、行政区划内用人单位数（个）、行政区划内从业人员数（人）、职业健康监管人员数（人）、取得执法资格证书职业健康监管人员数（人）、职业健康监管装备数量（台/套）、在用职业健康监管装备数量（台/套）
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);
        for (AreaOfDic areaOfDic : areaList) {
            GovSeven govSeven = new GovSeven();
            govSeven.setArea(areaOfDic.getName());
            govSeven.setVar1(RandomUtil.randomInt(1, 10000));
            govSeven.setVar2(RandomUtil.randomInt(1, 10000));
            govSeven.setVar3(RandomUtil.randomInt(1, 10000));
            govSeven.setVar4(RandomUtil.randomInt(1, 10000));
            govSeven.setVar5((double) RandomUtil.randomInt(1, 10000));
            govSeven.setVar6(RandomUtil.randomInt(1, 10000));
            govSeven.setVar7(RandomUtil.randomInt(1, 10000));
            list.add(govSeven);
        }
        return list;
    }

    //表2-33 技术服务机构监管情况统计分析表
    @GetMapping("/option5")
    public Map<String, List<Integer>> option5(String year) {
        Map<String, List<Integer>> map = Maps.newHashMap();
        //技术服务机构、健康检查机构、诊断机构
        List<Integer> list1 = Lists.newArrayList();
        List<Integer> list2 = Lists.newArrayList();
        List<Integer> list3 = Lists.newArrayList();
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);
        int size = areaList.size();
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

        }
        map.put("list1", list1);
        map.put("list2", list2);
        map.put("list3", list3);
        return map;
    }

    @GetMapping("/option5Detail")
    public List<Twenty> option5Detail(String year) {
        List<Twenty> list = Lists.newArrayList();
        List<String> nationList = DataVisualCacheUtil.getAreaStrChildren("国家",null,null);
        for (String s : nationList) {
            Twenty tmp = new Twenty();
            tmp.setName(s);
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
        }
        return list;
    }

    //表2-34 技术服务机构监管情况统计分析表（续）
    @GetMapping("/option6")
    public Map<String, List<Integer>> option6(String year) {
        List<AreaOfDic> areaList =DataVisualCacheUtil.getAreaChildren("国家",null,null);
        int size = areaList.size();

        Map<String, List<Integer>> map = Maps.newHashMap();
        //检查机构数、处罚机构数、罚款金额、吊销资质数
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
    @GetMapping("/option6Detail")
    public List<Twenty> option6Detail(String year) {
        List<Twenty> list = Lists.newArrayList();
        List<String> nationList = DataVisualCacheUtil.getAreaStrChildren("国家",null,null);
        for (String s : nationList) {
            Twenty tmp = new Twenty();
            tmp.setName(s);
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
        }
        return list;
    }
    //表2-35 职业病危害事故统计分析表
    @GetMapping("/option7")
    public Map<String, List<Integer>> option7(String year) {
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);
        int size = areaList.size();

        Map<String, List<Integer>> map = Maps.newHashMap();
        //事故数、事故人数、死亡人数
        List<Integer> list1 = Lists.newArrayList();
        List<Integer> list2 = Lists.newArrayList();
        List<Integer> list3 = Lists.newArrayList();

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
        }
        map.put("list1", list1);
        map.put("list2", list2);
        map.put("list3", list3);
        return map;
    }
    @GetMapping("/option7Detail")
    public List<Twenty> option7Detail(String year) {
        List<Twenty> list = Lists.newArrayList();
        List<String> nationList = DataVisualCacheUtil.getAreaStrChildren("国家",null,null);
        for (String s : nationList) {
            Twenty tmp = new Twenty();
            tmp.setName(s);
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
        }
        return list;
    }
}
