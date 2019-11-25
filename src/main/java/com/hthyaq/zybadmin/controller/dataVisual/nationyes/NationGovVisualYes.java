package com.hthyaq.zybadmin.controller.dataVisual.nationyes;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hthyaq.zybadmin.common.utils.cache.DataVisualCacheUtil;
import com.hthyaq.zybadmin.controller.dataVisual.vo.GovEight;
import com.hthyaq.zybadmin.controller.dataVisual.vo.GovSeven;
import com.hthyaq.zybadmin.controller.dataVisual.vo.GovSix;
import com.hthyaq.zybadmin.controller.dataVisual.vo.Twenty;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/nationGovVisual/yes")
public class NationGovVisualYes {
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    SuperviseService superviseService;
    @Autowired
    PersonOfSuperviseService personOfSuperviseService;
    @Autowired
    EquipmentOfSuperviseService equipmentOfSuperviseService;
    @Autowired
    ExecuteLawOfSuperviseService executeLawOfSuperviseService;
    @Autowired
    ThreeCheckOfSuperviseService threeCheckOfSuperviseService;
    @Autowired
    LawOfSuperviseService lawOfSuperviseService;
    @Autowired
    ServiceOfSuperviseService serviceOfSuperviseService;
    @Autowired
    AccidentOfSuperviseService accidentOfSuperviseService;

    //表2-29 职业健康监管资源统计分析表

    @GetMapping("/option1")
    public List<GovSix> option1(String year) {
        List<GovSix> list = Lists.newArrayList();
        //6个字段
        //行政区划、行政区划内用人单位数（个）、行政区划内从业人员数（人）、职业健康监管人员数（人）、取得执法资格证书职业健康监管人员数（人）、职业健康监管装备数量（台/套）、在用职业健康监管装备数量（台/套）
        List<Enterprise> areaList = enterpriseService.list(new QueryWrapper<Enterprise>().eq("year",year));
        for (Enterprise enterprise : areaList) {
            GovSix govSeven = new GovSix();
            //行政区划
            govSeven.setArea(enterprise.getProvinceName());
            //行政区划内用人单位数（个）
            int pcont = enterpriseService.count(new QueryWrapper<Enterprise>().eq("provinceName", enterprise.getProvinceName()).eq("year",year));
            govSeven.setVar1(pcont);
            //行政区划内从业人员数（人）
            govSeven.setVar2(enterprise.getWorkerNumber());
            //职业健康监管人员数（人）
            List<Supervise> list1 = superviseService.list(new QueryWrapper<Supervise>().eq("year", year));
            for (Supervise supervise : list1) {
                govSeven.setVar3(supervise.getManageCount());
            }
            //取得执法资格证书职业健康监管人员数（人）
            int count = personOfSuperviseService.count(new QueryWrapper<PersonOfSupervise>().eq("isGet", "是"));
            govSeven.setVar4(count);
            //职业健康监管装备数量（台/套）
            int equipmentOfSupervisecount = equipmentOfSuperviseService.count();
            govSeven.setVar5(equipmentOfSupervisecount);
            //在用职业健康监管装备数量（台/套）
            int equipmentOfSupervisecount1 = equipmentOfSuperviseService.count(new QueryWrapper<EquipmentOfSupervise>().eq("status", "在用"));
            govSeven.setVar6(equipmentOfSupervisecount1);
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
            List<LawOfSupervise> list4 = lawOfSuperviseService.list(new QueryWrapper<LawOfSupervise>().eq("year", year));
            for (LawOfSupervise lawOfSupervise : list4) {
                list1.add(lawOfSupervise.getRuleSum());
                list2.add(lawOfSupervise.getFileSum());
                list3.add(lawOfSupervise.getStartdardSum());
            }
        }
        map.put("list1", list1);
        map.put("list2", list2);
        map.put("list3", list3);
        return map;
    }

    @GetMapping("/option2Detail")
    public List<Twenty> option2Detail(String year) {
        List<Twenty> list = Lists.newArrayList();
        List<LawOfSupervise> list1 = lawOfSuperviseService.list(new QueryWrapper<LawOfSupervise>().eq("year", year));
        for (LawOfSupervise lawOfSupervise : list1) {
            Twenty tmp = new Twenty();
            //行政区划
            Supervise sup = superviseService.getById(lawOfSupervise.getSuperviseId());
            tmp.setName(sup.getProvinceName());
            //印发法律法规数新增
            tmp.setVar1(lawOfSupervise.getRuleIncrease());
            //印发法律法规的累计
            tmp.setVar2(lawOfSupervise.getRuleSum());
            //印发规范性文件的新增
            tmp.setVar3(lawOfSupervise.getFileIncrease());
            //印发规范性文件的累计
            tmp.setVar4(lawOfSupervise.getFileSum());
            //印发标准的新增
            tmp.setVar5(lawOfSupervise.getStartdardIncrease());
            //印发标准的累计
            tmp.setVar6(lawOfSupervise.getStartdardSum());
            list.add(tmp);
        }
        return list;
    }

    //表2-31 职业健康监督执法统计分析表
    @GetMapping("/option3")
    public List<GovEight> option3(String year) {
        List<GovEight> list = Lists.newArrayList();
        //8个字段
        List<ExecuteLawOfSupervise> list1 = executeLawOfSuperviseService.list(new QueryWrapper<ExecuteLawOfSupervise>().eq("year", year));
        for (ExecuteLawOfSupervise executeLawOfSupervise : list1) {
            GovEight govEight = new GovEight();
            //行政区划
            Supervise sup = superviseService.getById(executeLawOfSupervise.getSuperviseId());
            govEight.setArea(sup.getProvinceName());
            //检查用人单位数（个)
            govEight.setVar1(executeLawOfSupervise.getPersonCount());
            //下达执法文书数 （份）
            govEight.setVar2(executeLawOfSupervise.getPaperCount());
            //发现问题或隐患数
            govEight.setVar3(executeLawOfSupervise.getQuestionCount());
            //责令改正用人单位
            govEight.setVar4(executeLawOfSupervise.getChangeCount());
            //罚款用人单位数
            govEight.setVar5(executeLawOfSupervise.getPunishCount());
            //罚款金额
            govEight.setVar6(executeLawOfSupervise.getPunishMoney());
            //责令停产整顿用人单位数
            govEight.setVar7(executeLawOfSupervise.getStopCount());
            //提请关闭用人单位数
            govEight.setVar8(executeLawOfSupervise.getCloseCount());
            list.add(govEight);
        }
        return list;
    }

    //表2-32 职业卫生“三同时”监督执法统计分析表
    @GetMapping("/option4")
    public List<GovSeven> option4(String year) {
        List<GovSeven> list = Lists.newArrayList();
        //7个字段
        List<ThreeCheckOfSupervise> list1 = threeCheckOfSuperviseService.list(new QueryWrapper<ThreeCheckOfSupervise>().eq("year", year));
        for (ThreeCheckOfSupervise threeCheckOfSupervise : list1) {
            GovSeven govSeven = new GovSeven();
            //行政区划
            Supervise sup = superviseService.getById(threeCheckOfSupervise.getSuperviseId());
            govSeven.setArea(sup.getProvinceName());
            //检查建设单位数
            govSeven.setVar1(threeCheckOfSupervise.getOrgCount());
            //下达执法文书数
            govSeven.setVar2(threeCheckOfSupervise.getPaperCount());
            //给予警告责令限期整改单位数
            govSeven.setVar3(threeCheckOfSupervise.getChangeCount());
            //罚款建设单位数
            govSeven.setVar4(threeCheckOfSupervise.getPulishCount());
            //罚款金额
            govSeven.setVar5(threeCheckOfSupervise.getPulishMoney());
            //责令停止产生职业病危害作业单位数
            govSeven.setVar6(threeCheckOfSupervise.getStopCount());
            //提请责令停建或关闭单位数数
            govSeven.setVar7(threeCheckOfSupervise.getCloseCount());
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
                int i1 = RandomUtil.randomInt(1, 10000);
                list1.add(i1);
                //
                int i2 = RandomUtil.randomInt(1, 10000);
                list2.add(i2);
                //
                int i3 = RandomUtil.randomInt(1, 10000);
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
           List<AccidentOfSupervise> lista = accidentOfSuperviseService.list(new QueryWrapper<AccidentOfSupervise>().eq("year", year));
           for (AccidentOfSupervise accidentOfSupervise : lista) {
               int i1 = accidentOfSupervise.getDustCount()+accidentOfSupervise.getPoisonCount()+accidentOfSupervise.getOtherCount();
               list1.add(i1);
               //
               int i2 = accidentOfSupervise.getDustPersonCount()+accidentOfSupervise.getOtherPersonCount()+accidentOfSupervise.getPoisonPersonCount();
               list2.add(i2);
               //
               int i3 = accidentOfSupervise.getOtherDieCount()+accidentOfSupervise.getDustDieCount()+accidentOfSupervise.getPoisonDieCount();
               list3.add(i3);
           }
        }
        map.put("list1", list1);
        map.put("list2", list2);
        map.put("list3", list3);
        return map;
    }
    @GetMapping("/option7Detail")
    public List<Twenty> option7Detail(String year) {
        List<Twenty> list = Lists.newArrayList();
        List<AccidentOfSupervise> lista = accidentOfSuperviseService.list(new QueryWrapper<AccidentOfSupervise>().eq("year", year));
        for (AccidentOfSupervise accidentOfSupervise : lista) {
            Twenty tmp = new Twenty();
            Supervise sup = superviseService.getById(accidentOfSupervise.getSuperviseId());
            tmp.setName(sup.getProvinceName());
            tmp.setVar1(accidentOfSupervise.getDustCount()+accidentOfSupervise.getPoisonCount()+accidentOfSupervise.getOtherCount());
            tmp.setVar2(accidentOfSupervise.getDustCount());
            tmp.setVar3(accidentOfSupervise.getPoisonCount());
            tmp.setVar4(accidentOfSupervise.getOtherCount());
            tmp.setVar5(accidentOfSupervise.getDustPersonCount()+accidentOfSupervise.getOtherPersonCount()+accidentOfSupervise.getPoisonPersonCount());
            tmp.setVar6(accidentOfSupervise.getDustPersonCount());
            tmp.setVar7(accidentOfSupervise.getOtherPersonCount());
            tmp.setVar8(accidentOfSupervise.getPoisonPersonCount());
            tmp.setVar9(accidentOfSupervise.getOtherDieCount()+accidentOfSupervise.getDustDieCount()+accidentOfSupervise.getPoisonDieCount());
            tmp.setVar10(accidentOfSupervise.getLoseMoney());
            list.add(tmp);
        }
        return list;
    }
}
