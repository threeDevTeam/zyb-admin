package com.hthyaq.zybadmin.controller.dataVisual.nation;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hthyaq.zybadmin.common.utils.DoubleUtil;
import com.hthyaq.zybadmin.common.utils.VisualUtil;
import com.hthyaq.zybadmin.controller.dataVisual.vo.*;
import com.hthyaq.zybadmin.model.entity.AreaOfDic;
import com.hthyaq.zybadmin.model.entity.BaseOfDic;
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
@RequestMapping("/nationServiceVisual/no")
public class NationServiceVisualNo {
    @Autowired
    AreaOfDicService areaOfDicService;
    @Autowired
    IndustryOfDicService industryOfDicService;
    @Autowired
    BaseOfDicService baseOfDicService;
    @Autowired
    VisualUtil visualUtil;

    //危害因素
    //    表1  作业场所职业病危害因素检测情况统计分析表
    @GetMapping("/option11")
    public List<NameValueDouble> option11(String year, String type) {
        List<NameValueDouble> list = Lists.newArrayList();
        double d1 = RandomUtil.randomDouble(1.0, 100.0);
        list.add(new NameValueDouble("粉尘", DoubleUtil.get(d1)));
        double d2 = RandomUtil.randomDouble(1.0, 100.0);
        list.add(new NameValueDouble("化学因素", DoubleUtil.get(d2)));
        double d3 = RandomUtil.randomDouble(1.0, 100.0);
        list.add(new NameValueDouble("物理因素", DoubleUtil.get(d3)));
        double d4 = RandomUtil.randomDouble(1.0, 100.0);
        list.add(new NameValueDouble("放射性因素", DoubleUtil.get(d4)));
        double d5 = RandomUtil.randomDouble(1.0, 100.0);
        list.add(new NameValueDouble("生物因素", DoubleUtil.get(d5)));
        return list;
    }

    @GetMapping("/option11Detail")
    public List<Twenty> option11Detail(String year, String type) {
        List<Twenty> list = Lists.newArrayList();
        List<String> list2 = null;
        if ("危害因素".equals(type)) {
            list2 = visualUtil.getDangerList();
        } else if ("行政区划".equals(type)) {
            list2 = visualUtil.getAreaStrList("nation");
        } else if ("登记注册类型".equals(type)) {
            list2 = visualUtil.getRegisterTypeStrList();
        }
        for (String s : list2) {
            Twenty tmp = new Twenty();
            tmp.setName(s);
            tmp.setVar1(RandomUtil.randomInt(1, 10000));
            tmp.setVar2(RandomUtil.randomInt(1, 10000));
            tmp.setVar3(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            list.add(tmp);
        }
        return list;
    }

    //表2-职业健康检查结果统计分析表
    @GetMapping("/option12")
    public Map<String, List<Double>> option12(String year, String type) {
        Map<String, List<Double>> map = Maps.newHashMap();
        //['职业禁忌证检出率', '疑似职业病检出率', '检出疑似职业病企业率']
        List<Double> list1 = Lists.newArrayList();
        List<Double> list2 = Lists.newArrayList();
        List<Double> list3 = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            //
            double d1 = RandomUtil.randomDouble(1.0, 100.0);
            list1.add(DoubleUtil.get(d1));
            //
            double d2 = RandomUtil.randomDouble(1.0, 100.0);
            list2.add(DoubleUtil.get(d2));
            //
            double d3 = RandomUtil.randomDouble(1.0, 100.0);
            list3.add(DoubleUtil.get(d3));
        }
        map.put("list1", list1);
        map.put("list2", list2);
        map.put("list3", list3);
        return map;
    }

    @GetMapping("/option12Detail")
    public List<Twenty> option12Detail(String year, String type) {
        List<Twenty> list = Lists.newArrayList();
        List<String> list2 = null;
        if ("危害因素".equals(type)) {
            list2 = visualUtil.getDangerList();
        } else if ("行政区划".equals(type)) {
            list2 = visualUtil.getAreaStrList("nation");
        } else if ("登记注册类型".equals(type)) {
            list2 = visualUtil.getRegisterTypeStrList();
        }
        for (String s : list2) {
            Twenty tmp = new Twenty();
            tmp.setName(s);
            tmp.setVar1(RandomUtil.randomInt(1, 10000));
            tmp.setVar2(RandomUtil.randomInt(1, 10000));
            tmp.setVar3(RandomUtil.randomInt(1, 10000));
            tmp.setVar4(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            tmp.setVar5(RandomUtil.randomInt(1, 10000));
            tmp.setVar6(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            tmp.setVar7(RandomUtil.randomInt(1, 10000));
            tmp.setVar8(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            list.add(tmp);
        }
        return list;
    }

    //表3 职业病诊断情况统计分析表
    @GetMapping("/option13")
    public Map<String, List<Double>> option13(String year, String type) {
        Map<String, List<Double>> map = Maps.newHashMap();
        //职业病诊断率
        List<Double> list1 = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            //
            double d1 = RandomUtil.randomDouble(1.0, 100.0);
            list1.add(DoubleUtil.get(d1));
        }
        map.put("list1", list1);
        return map;
    }

    @GetMapping("/option13Detail")
    public List<Twenty> option13Detail(String year, String type) {
        List<Twenty> list = Lists.newArrayList();
        List<String> list2 = null;
        if ("危害因素".equals(type)) {
            list2 = visualUtil.getDangerList();
        } else if ("行政区划".equals(type)) {
            list2 = visualUtil.getAreaStrList("nation");
        } else if ("登记注册类型".equals(type)) {
            list2 = visualUtil.getRegisterTypeStrList();
        }
        for (String s : list2) {
            Twenty tmp = new Twenty();
            tmp.setName(s);
            tmp.setVar1(RandomUtil.randomInt(1, 10000));
            tmp.setVar2(RandomUtil.randomInt(1, 10000));
            tmp.setVar3(RandomUtil.randomInt(1, 10000));
            tmp.setVar4(RandomUtil.randomInt(1, 10000));
            tmp.setVar5(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            list.add(tmp);
        }
        return list;
    }

    //行政区划、登记注册类型
    @GetMapping("/option21")
    public Map<String, List<Double>> option21(String year, String type) {
        List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1).notIn("name", "澳门", "台湾"));
        List<BaseOfDic> registerList = baseOfDicService.list(new QueryWrapper<BaseOfDic>().eq("flag", "登记注册类型"));

        Map<String, List<Double>> map = Maps.newHashMap();
        //['粉尘', '化学因素', '物理因素', '放射性因素', '生物因素']
        List<Double> list1 = Lists.newArrayList();
        List<Double> list2 = Lists.newArrayList();
        List<Double> list3 = Lists.newArrayList();
        List<Double> list4 = Lists.newArrayList();
        List<Double> list5 = Lists.newArrayList();

        int size = 0;
        if ("行政区划".equals(type)) {
            size = areaList.size();
        } else if ("登记注册类型".equals(type)) {
            size = registerList.size();
        }

        for (int i = 0; i < size; i++) {
            //
            double d1 = RandomUtil.randomDouble(1.0, 100.0);
            list1.add(DoubleUtil.get(d1));
            //
            double d2 = RandomUtil.randomDouble(1.0, d1);
            list2.add(DoubleUtil.get(d2));
            //
            double d3 = RandomUtil.randomDouble(1.0, d2);
            list3.add(DoubleUtil.get(d3));
            //
            double d4 = RandomUtil.randomDouble(1.0, d2);
            list4.add(DoubleUtil.get(d4));
            //
            double d5 = RandomUtil.randomDouble(1.0, d2);
            list5.add(DoubleUtil.get(d5));
        }
        map.put("list1", list1);
        map.put("list2", list2);
        map.put("list3", list3);
        map.put("list4", list4);
        map.put("list5", list5);
        return map;
    }

    @GetMapping("/option21Detail")
    public List<Twenty> option21Detail(String year, String type) {
        List<Twenty> list = Lists.newArrayList();
        List<String> list2 = null;
        if ("危害因素".equals(type)) {
            list2 = visualUtil.getDangerList();
        } else if ("行政区划".equals(type)) {
            list2 = visualUtil.getAreaStrList("nation");
        } else if ("登记注册类型".equals(type)) {
            list2 = visualUtil.getRegisterTypeStrList();
        }
        for (String s : list2) {
            Twenty tmp = new Twenty();
            tmp.setName(s);
            tmp.setVar1(RandomUtil.randomInt(1, 10000));
            tmp.setVar2(RandomUtil.randomInt(1, 10000));
            tmp.setVar3(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            list.add(tmp);
        }
        return list;
    }

    @GetMapping("/option22")
    public Map<String, List<Double>> option22(String year, String type) {
        List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1).notIn("name", "澳门", "台湾"));
        List<BaseOfDic> registerList = baseOfDicService.list(new QueryWrapper<BaseOfDic>().eq("flag", "登记注册类型"));

        int size = 0;
        if ("行政区划".equals(type)) {
            size = areaList.size();
        } else if ("登记注册类型".equals(type)) {
            size = registerList.size();
        }

        Map<String, List<Double>> map = Maps.newHashMap();
        //
        List<Double> list1 = Lists.newArrayList();
        List<Double> list2 = Lists.newArrayList();
        List<Double> list3 = Lists.newArrayList();

        for (int i = 0; i < size; i++) {
            //
            double d1 = RandomUtil.randomDouble(1.0, 100.0);
            list1.add(DoubleUtil.get(d1));
            //
            double d2 = RandomUtil.randomDouble(1.0, d1);
            list2.add(DoubleUtil.get(d2));
            //
            double d3 = RandomUtil.randomDouble(1.0, d2);
            list3.add(DoubleUtil.get(d3));
        }
        map.put("list1", list1);
        map.put("list2", list2);
        map.put("list3", list3);
        return map;
    }

    @GetMapping("/option22Detail")
    public List<Twenty> option22Detail(String year, String type) {
        List<Twenty> list = Lists.newArrayList();
        List<String> list2 = null;
        if ("危害因素".equals(type)) {
            list2 = visualUtil.getDangerList();
        } else if ("行政区划".equals(type)) {
            list2 = visualUtil.getAreaStrList("nation");
        } else if ("登记注册类型".equals(type)) {
            list2 = visualUtil.getRegisterTypeStrList();
        }
        for (String s : list2) {
            Twenty tmp = new Twenty();
            tmp.setName(s);
            tmp.setVar1(RandomUtil.randomInt(1, 10000));
            tmp.setVar2(RandomUtil.randomInt(1, 10000));
            tmp.setVar3(RandomUtil.randomInt(1, 10000));
            tmp.setVar4(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            tmp.setVar5(RandomUtil.randomInt(1, 10000));
            tmp.setVar6(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            tmp.setVar7(RandomUtil.randomInt(1, 10000));
            tmp.setVar8(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            list.add(tmp);
        }
        return list;
    }

    @GetMapping("/option23")
    public Map<String, List<Double>> option23(String year, String type) {
        List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1).notIn("name", "澳门", "台湾"));
        List<BaseOfDic> registerList = baseOfDicService.list(new QueryWrapper<BaseOfDic>().eq("flag", "登记注册类型"));

        int size = 0;
        if ("行政区划".equals(type)) {
            size = areaList.size();
        } else if ("登记注册类型".equals(type)) {
            size = registerList.size();
        }

        Map<String, List<Double>> map = Maps.newHashMap();
        //
        List<Double> list1 = Lists.newArrayList();

        for (int i = 0; i < size; i++) {
            //
            double d1 = RandomUtil.randomDouble(1.0, 100.0);
            list1.add(DoubleUtil.get(d1));
        }
        map.put("list1", list1);
        return map;
    }

    @GetMapping("/option23Detail")
    public List<Twenty> option23Detail(String year, String type) {
        List<Twenty> list = Lists.newArrayList();
        List<String> list2 = null;
        if ("危害因素".equals(type)) {
            list2 = visualUtil.getDangerList();
        } else if ("行政区划".equals(type)) {
            list2 = visualUtil.getAreaStrList("nation");
        } else if ("登记注册类型".equals(type)) {
            list2 = visualUtil.getRegisterTypeStrList();
        }
        for (String s : list2) {
            Twenty tmp = new Twenty();
            tmp.setName(s);
            tmp.setVar1(RandomUtil.randomInt(1, 10000));
            tmp.setVar2(RandomUtil.randomInt(1, 10000));
            tmp.setVar3(RandomUtil.randomInt(1, 10000));
            tmp.setVar4(RandomUtil.randomInt(1, 10000));
            tmp.setVar5(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            list.add(tmp);
        }
        return list;
    }

    //表4-职业卫生技术服务机构统计分析表
    @GetMapping("/option24")
    public List<GovEight> option24(String year, String type) {
        List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1).notIn("name", "澳门", "台湾"));
        List<BaseOfDic> registerList = baseOfDicService.list(new QueryWrapper<BaseOfDic>().eq("flag", "登记注册类型"));

        List<GovEight> list = Lists.newArrayList();
        if ("行政区划".equals(type)) {
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
        } else if ("登记注册类型".equals(type)) {
            for (BaseOfDic baseOfDic : registerList) {
                GovEight govEight = new GovEight();
                govEight.setArea(baseOfDic.getBigName());
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
        }
        return list;
    }

    //表5-职业健康检查机构统计分析表
    @GetMapping("/option25")
    public List<GovSix> option25(String year, String type) {
        List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1).notIn("name", "澳门", "台湾"));
        List<BaseOfDic> registerList = baseOfDicService.list(new QueryWrapper<BaseOfDic>().eq("flag", "登记注册类型"));

        List<GovSix> list = Lists.newArrayList();
        if ("行政区划".equals(type)) {
            for (AreaOfDic areaOfDic : areaList) {
                GovSix govSix = new GovSix();
                govSix.setArea(areaOfDic.getName());
                govSix.setVar1(RandomUtil.randomInt(1, 10000));
                govSix.setVar2(RandomUtil.randomInt(1, 10000));
                govSix.setVar3(RandomUtil.randomInt(1, 10000));
                govSix.setVar4(RandomUtil.randomInt(1, 10000));
                govSix.setVar5(RandomUtil.randomInt(1, 10000));
                govSix.setVar6(RandomUtil.randomInt(1, 10000));
                list.add(govSix);
            }
        } else if ("登记注册类型".equals(type)) {
            for (BaseOfDic baseOfDic : registerList) {
                GovSix govSix = new GovSix();
                govSix.setArea(baseOfDic.getBigName());
                govSix.setVar1(RandomUtil.randomInt(1, 10000));
                govSix.setVar2(RandomUtil.randomInt(1, 10000));
                govSix.setVar3(RandomUtil.randomInt(1, 10000));
                govSix.setVar4(RandomUtil.randomInt(1, 10000));
                govSix.setVar5(RandomUtil.randomInt(1, 10000));
                govSix.setVar6(RandomUtil.randomInt(1, 10000));
                list.add(govSix);
            }
        }
        return list;
    }

    //表6-职业病诊断机构统计分析表
    @GetMapping("/option26")
    public List<ServiceFive> option26(String year, String type) {
        List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1).notIn("name", "澳门", "台湾"));
        List<BaseOfDic> registerList = baseOfDicService.list(new QueryWrapper<BaseOfDic>().eq("flag", "登记注册类型"));

        List<ServiceFive> list = Lists.newArrayList();
        if ("行政区划".equals(type)) {
            for (AreaOfDic areaOfDic : areaList) {
                ServiceFive serviceFive = new ServiceFive();
                serviceFive.setArea(areaOfDic.getName());
                serviceFive.setVar1(RandomUtil.randomInt(1, 10000));
                serviceFive.setVar2(RandomUtil.randomInt(1, 10000));
                serviceFive.setVar3(RandomUtil.randomInt(1, 10000));
                serviceFive.setVar4(RandomUtil.randomInt(1, 10000));
                serviceFive.setVar5(RandomUtil.randomInt(1, 10000));
                list.add(serviceFive);
            }
        } else if ("登记注册类型".equals(type)) {
            for (BaseOfDic baseOfDic : registerList) {
                ServiceFive serviceFive = new ServiceFive();
                serviceFive.setArea(baseOfDic.getBigName());
                serviceFive.setVar1(RandomUtil.randomInt(1, 10000));
                serviceFive.setVar2(RandomUtil.randomInt(1, 10000));
                serviceFive.setVar3(RandomUtil.randomInt(1, 10000));
                serviceFive.setVar4(RandomUtil.randomInt(1, 10000));
                serviceFive.setVar5(RandomUtil.randomInt(1, 10000));
                list.add(serviceFive);
            }
        }
        return list;
    }
}
