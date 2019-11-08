package com.hthyaq.zybadmin.controller.dataVisual.nation;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hthyaq.zybadmin.common.utils.DoubleUtil;
import com.hthyaq.zybadmin.common.utils.VisualUtil;
import com.hthyaq.zybadmin.controller.dataVisual.nation.vo.Twenty;
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
@RequestMapping("/nationEnterpriseVisual/no")
public class NationEnterpriseVisualNo {
    @Autowired
    AreaOfDicService areaOfDicService;
    @Autowired
    IndustryOfDicService industryOfDicService;
    @Autowired
    BaseOfDicService baseOfDicService;
    @Autowired
    VisualUtil visualUtil;

    //危害因素、企业规模
    /*
    表2-1 基础信息统计分析表（按危害因素）
    type=危害因素、企业规模
     */
    @GetMapping("/option11")
    public List<Double> option11(String year, String type) {
        List<Double> list = Lists.newArrayList();
        if ("危害因素".equals(type)) {
            //接害率
            double tmp = 0.0;
            for (int i = 0; i < 6; i++) {
                if (i == 0) {
                    tmp = RandomUtil.randomDouble(40, 100.0);
                    list.add(DoubleUtil.get(tmp));
                } else {
                    //此处遗留一个问题
                    double d = RandomUtil.randomDouble(1.0, 100.0);
                    list.add(DoubleUtil.get(d));
                }
            }
        } else {
            //接害率
            double tmp = 0.0;
            for (int i = 0; i < 5; i++) {
                if (i == 0) {
                    tmp = RandomUtil.randomDouble(40, 100.0);
                    list.add(DoubleUtil.get(tmp));
                } else {
                    //此处遗留一个问题
                    double d = RandomUtil.randomDouble(1.0, 100.0);
                    list.add(DoubleUtil.get(d));
                }
            }
        }
        return list;
    }

    @GetMapping("/option11Detail")
    public List<Twenty> option11Detail(String year, String type) {
        List<Twenty> list = Lists.newArrayList();
        if ("危害因素".equals(type)) {
            Twenty tmp = new Twenty();
            tmp.setVar1(RandomUtil.randomInt(1, 10000));
            tmp.setVar2(RandomUtil.randomInt(1, 10000));
            tmp.setVar3(RandomUtil.randomInt(1, 10000));
            tmp.setVar4(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            tmp.setVar5(RandomUtil.randomInt(1, 10000));
            tmp.setVar6(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            tmp.setVar7(RandomUtil.randomInt(1, 10000));
            tmp.setVar8(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            tmp.setVar9(RandomUtil.randomInt(1, 10000));
            tmp.setVar10(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            tmp.setVar11(RandomUtil.randomInt(1, 10000));
            tmp.setVar12(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            tmp.setVar13(RandomUtil.randomInt(1, 10000));
            tmp.setVar14(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            list.add(tmp);
        } else if ("企业规模".equals(type)) {
            List<String> enterpriseSizeList = visualUtil.getEnterpriseSize();
            for (String s : enterpriseSizeList) {
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
                tmp.setVar9(RandomUtil.randomInt(1, 10000));
                tmp.setVar10(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
                tmp.setVar11(RandomUtil.randomInt(1, 10000));
                tmp.setVar12(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
                tmp.setVar13(RandomUtil.randomInt(1, 10000));
                tmp.setVar14(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
                list.add(tmp);
            }
        }
        return list;
    }

    /*
    表2-2 基础信息统计分析表（按危害因素续1）
       type=危害因素、企业规模
    */
    @GetMapping("/option12")
    public Map<String, List<Double>> option12(String year, String type) {
        Map<String, List<Double>> map = Maps.newHashMap();
        //检测率=达标率+损失率
        List<Double> list1 = Lists.newArrayList();
        //达标率
        List<Double> list2 = Lists.newArrayList();
        //损失率
        List<Double> list3 = Lists.newArrayList();

        //这里要注意，需要倒序
        if ("危害因素".equals(type)) {
            //'生物因素', '放射性因素', '物理因素', '化学因素', '粉尘'
            for (int i = 0; i < 5; i++) {
                //检测率
                double d1 = RandomUtil.randomDouble(1.0, 100.0);
                double d11 = DoubleUtil.get(d1);
                list1.add(d11);
                //达标率
                double d2 = RandomUtil.randomDouble(1.0, d1);
                double d21 = DoubleUtil.get(d2);
                list2.add(d21);
                //损失率
                list3.add(DoubleUtil.get(d21 - d11));
            }
        } else {
            //'大型', '中型', '小型', '微型'
            for (int i = 0; i < 4; i++) {
                //检测率
                double d1 = RandomUtil.randomDouble(1.0, 100.0);
                double d11 = DoubleUtil.get(d1);
                list1.add(d11);
                //达标率
                double d2 = RandomUtil.randomDouble(1.0, d1);
                double d21 = DoubleUtil.get(d2);
                list2.add(d21);
                //损失率
                list3.add(DoubleUtil.get(d21 - d11));
            }
        }
        map.put("list1", list1);
        map.put("list2", list2);
        map.put("list3", list3);
        return map;
    }

    @GetMapping("/option12Detail")
    public List<Twenty> option12Detail(String year, String type) {
        List<Twenty> list = Lists.newArrayList();
        if ("危害因素".equals(type)) {
            List<String> dangerList = visualUtil.getDangerList();
            for (String s : dangerList) {
                Twenty tmp = new Twenty();
                tmp.setName(s);
                tmp.setVar1(RandomUtil.randomInt(1, 10000));
                tmp.setVar2(RandomUtil.randomInt(1, 10000));
                double d = DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0));
                tmp.setVar3(d + "%");
                tmp.setVar4(RandomUtil.randomInt(1, 10000));
                tmp.setVar5(DoubleUtil.get(RandomUtil.randomDouble(1.0, d)) + "%");
                list.add(tmp);
            }
        } else if ("企业规模".equals(type)) {
            List<String> enterpriseSizeList = visualUtil.getEnterpriseSize();
            for (String s : enterpriseSizeList) {
                Twenty tmp = new Twenty();
                tmp.setName(s);
                tmp.setVar1(RandomUtil.randomInt(1, 10000));
                tmp.setVar2(RandomUtil.randomInt(1, 10000));
                double d = DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0));
                tmp.setVar3(d + "%");
                tmp.setVar4(RandomUtil.randomInt(1, 10000));
                tmp.setVar5(DoubleUtil.get(RandomUtil.randomDouble(1.0, d)) + "%");
                list.add(tmp);
            }
        }
        return list;
    }

    /*
        表2-3 基础信息统计分析表（按危害因素续2）
           type=危害因素、企业规模
     */
    @GetMapping("/option13")
    public Map<String, List<Double>> option13(String year, String type) {
        Map<String, List<Double>> map = Maps.newHashMap();
        if ("危害因素".equals(type)) {
            //粉尘、化学因素、物理因素、放射性因素、生物因素
            List<Double> list0 = Lists.newArrayList();
            List<Double> list1 = Lists.newArrayList();
            List<Double> list2 = Lists.newArrayList();
            List<Double> list3 = Lists.newArrayList();
            List<Double> list4 = Lists.newArrayList();
            for (int i = 0; i < 2; i++) {
                //
                double d0 = RandomUtil.randomDouble(1.0, 100.0);
                list0.add(DoubleUtil.get(d0));
                //
                double d1 = RandomUtil.randomDouble(1.0, 100.0);
                list1.add(DoubleUtil.get(d1));
                //
                double d2 = RandomUtil.randomDouble(1.0, 100.0);
                list2.add(DoubleUtil.get(d2));
                //
                double d3 = RandomUtil.randomDouble(1.0, 100.0);
                list3.add(DoubleUtil.get(d3));
                //
                double d4 = RandomUtil.randomDouble(1.0, 100.0);
                list4.add(DoubleUtil.get(d4));
            }
            map.put("list0", list0);
            map.put("list1", list1);
            map.put("list2", list2);
            map.put("list3", list3);
            map.put("list4", list4);
        } else {
            //微型、小型、中型、大型
            List<Double> list0 = Lists.newArrayList();
            List<Double> list1 = Lists.newArrayList();
            List<Double> list2 = Lists.newArrayList();
            List<Double> list3 = Lists.newArrayList();
            for (int i = 0; i < 2; i++) {
                //
                double d0 = RandomUtil.randomDouble(1.0, 100.0);
                list0.add(DoubleUtil.get(d0));
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
            map.put("list0", list0);
            map.put("list1", list1);
            map.put("list2", list2);
            map.put("list3", list3);
        }

        return map;
    }

    @GetMapping("/option13Detail")
    public List<Twenty> option13Detail(String year, String type) {
        List<Twenty> list = Lists.newArrayList();
        if ("危害因素".equals(type)) {
            List<String> dangerList = visualUtil.getDangerList();
            for (String s : dangerList) {
                Twenty tmp = new Twenty();
                tmp.setName(s);
                tmp.setVar1(RandomUtil.randomInt(1, 10000));
                tmp.setVar2(RandomUtil.randomInt(1, 10000));
                tmp.setVar3(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
                tmp.setVar4(RandomUtil.randomInt(1, 10000));
                tmp.setVar5(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
                list.add(tmp);
            }
        } else if ("企业规模".equals(type)) {
            List<String> enterpriseSizeList = visualUtil.getEnterpriseSize();
            for (String s : enterpriseSizeList) {
                Twenty tmp = new Twenty();
                tmp.setName(s);
                tmp.setVar1(RandomUtil.randomInt(1, 10000));
                tmp.setVar2(RandomUtil.randomInt(1, 10000));
                tmp.setVar3(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
                tmp.setVar4(RandomUtil.randomInt(1, 10000));
                tmp.setVar5(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
                list.add(tmp);
            }
        }
        return list;
    }

    /*
    表2-3 基础信息统计分析表（按危害因素续3）
       type=危害因素、企业规模
 */
    @GetMapping("/option14")
    public Map<String, List<Double>> option14(String year, String type) {
        Map<String, List<Double>> map = Maps.newHashMap();
        //体检率、累计职业病患病率、累计职业病病死率
        List<Double> list1 = Lists.newArrayList();
        List<Double> list2 = Lists.newArrayList();
        List<Double> list3 = Lists.newArrayList();
        if ("危害因素".equals(type)) {
            for (int i = 0; i < 5; i++) {
                //体检率
                double d1 = RandomUtil.randomDouble(1.0, 100.0);
                list1.add(DoubleUtil.get(d1));
                //
                double d2 = RandomUtil.randomDouble(1.0, d1);
                list2.add(DoubleUtil.get(d2));
                //
                double d3 = RandomUtil.randomDouble(1.0, d2);
                list3.add(DoubleUtil.get(d3));
            }
        } else {
            for (int i = 0; i < 4; i++) {
                //体检率
                double d1 = RandomUtil.randomDouble(1.0, 100.0);
                list1.add(DoubleUtil.get(d1));
                //
                double d2 = RandomUtil.randomDouble(1.0, d1);
                list2.add(DoubleUtil.get(d2));
                //
                double d3 = RandomUtil.randomDouble(1.0, d2);
                list3.add(DoubleUtil.get(d3));
            }
        }
        map.put("list1", list1);
        map.put("list2", list2);
        map.put("list3", list3);
        return map;
    }

    @GetMapping("/option14Detail")
    public List<Twenty> option14Detail(String year, String type) {
        List<Twenty> list = Lists.newArrayList();
        if ("危害因素".equals(type)) {
            List<String> dangerList = visualUtil.getDangerList();
            for (String s : dangerList) {
                Twenty tmp = new Twenty();
                tmp.setName(s);
                tmp.setVar1(RandomUtil.randomInt(1, 10000));
                tmp.setVar2(RandomUtil.randomInt(1, 10000) + "%");
                tmp.setVar3(RandomUtil.randomInt(1, 10000));
                tmp.setVar4(RandomUtil.randomInt(1, 10000));
                tmp.setVar5(RandomUtil.randomInt(1, 10000));
                tmp.setVar6(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
                tmp.setVar7(RandomUtil.randomInt(1, 10000));
                tmp.setVar8(RandomUtil.randomInt(1, 10000));
                tmp.setVar9(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
                tmp.setVar10(RandomUtil.randomInt(1, 10000));
                list.add(tmp);
            }
        } else if ("企业规模".equals(type)) {
            List<String> enterpriseSizeList = visualUtil.getEnterpriseSize();
            for (String s : enterpriseSizeList) {
                Twenty tmp = new Twenty();
                tmp.setName(s);
                tmp.setVar1(RandomUtil.randomInt(1, 10000));
                tmp.setVar2(RandomUtil.randomInt(1, 10000) + "%");
                tmp.setVar3(RandomUtil.randomInt(1, 10000));
                tmp.setVar4(RandomUtil.randomInt(1, 10000));
                tmp.setVar5(RandomUtil.randomInt(1, 10000));
                tmp.setVar6(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
                tmp.setVar7(RandomUtil.randomInt(1, 10000));
                tmp.setVar8(RandomUtil.randomInt(1, 10000));
                tmp.setVar9(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
                tmp.setVar10(RandomUtil.randomInt(1, 10000));
                list.add(tmp);
            }
        }
        return list;
    }

    //行政区划、登记注册类型、所属行业
    @GetMapping("/option21")
    public Map<String, List<Double>> option21(String year, String type) {
        List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1).notIn("name", "澳门", "台湾"));
        List<BaseOfDic> registerList = baseOfDicService.list(new QueryWrapper<BaseOfDic>().eq("flag", "登记注册类型"));
        List<IndustryOfDic> industryList = industryOfDicService.list(new QueryWrapper<IndustryOfDic>().eq("topid", -1));

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
        } else if ("所属行业".equals(type)) {
            size = industryList.size();
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
        if ("行政区划".equals(type)) {
            list2 = visualUtil.getAreaStrList("nation");
        } else if ("登记注册类型".equals(type)) {
            list2 = visualUtil.getRegisterTypeStrList();
        } else if ("所属行业".equals(type)) {
            list2 = visualUtil.getIndustryStrList();
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
            tmp.setVar9(RandomUtil.randomInt(1, 10000));
            tmp.setVar10(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            tmp.setVar11(RandomUtil.randomInt(1, 10000));
            tmp.setVar12(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            tmp.setVar13(RandomUtil.randomInt(1, 10000));
            tmp.setVar14(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            list.add(tmp);
        }
        return list;
    }

    //这里的省份是倒序的
    //行政区划、登记注册类型、所属行业
    @GetMapping("/option22")
    public Map<String, List<Double>> option22(String year, String type) {
        List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1).notIn("name", "澳门", "台湾"));
        List<BaseOfDic> registerList = baseOfDicService.list(new QueryWrapper<BaseOfDic>().eq("flag", "登记注册类型"));
        List<IndustryOfDic> industryList = industryOfDicService.list(new QueryWrapper<IndustryOfDic>().eq("topid", -1));

        Map<String, List<Double>> map = Maps.newHashMap();
        //['检测率', '达标率', '损失率']
        List<Double> list1 = Lists.newArrayList();
        List<Double> list2 = Lists.newArrayList();
        List<Double> list3 = Lists.newArrayList();

        int size = 0;
        if ("行政区划".equals(type)) {
            size = areaList.size();
        } else if ("登记注册类型".equals(type)) {
            size = registerList.size();
        } else if ("所属行业".equals(type)) {
            size = industryList.size();
        }

        for (int i = 0; i < size; i++) {
            //
            double d1 = RandomUtil.randomDouble(1.0, 100.0);
            double d12 = DoubleUtil.get(d1);
            list1.add(d12);
            //
            double d2 = RandomUtil.randomDouble(1.0, d12);
            double d21 = DoubleUtil.get(d2);
            list2.add(d21);
            //
            double d3 = -DoubleUtil.get(d12 - d21);
            list3.add(d3);
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
        if ("行政区划".equals(type)) {
            list2 = visualUtil.getAreaStrList("nation");
        } else if ("登记注册类型".equals(type)) {
            list2 = visualUtil.getRegisterTypeStrList();
        } else if ("所属行业".equals(type)) {
            list2 = visualUtil.getIndustryStrList();
        }
        for (String s : list2) {
            Twenty tmp = new Twenty();
            tmp.setName(s);
            tmp.setVar1(RandomUtil.randomInt(1, 10000));
            tmp.setVar2(RandomUtil.randomInt(1, 10000));
            double d = DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0));
            tmp.setVar3(d + "%");
            tmp.setVar4(RandomUtil.randomInt(1, 10000));
            tmp.setVar5(DoubleUtil.get(RandomUtil.randomDouble(1.0, d)) + "%");
            list.add(tmp);
        }
        return list;
    }

    //行政区划、登记注册类型、所属行业
    @GetMapping("/option23")
    public Map<String, List<Double>> option23(String year, String type) {
        List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1).notIn("name", "澳门", "台湾"));
        List<BaseOfDic> registerList = baseOfDicService.list(new QueryWrapper<BaseOfDic>().eq("flag", "登记注册类型"));
        List<IndustryOfDic> industryList = industryOfDicService.list(new QueryWrapper<IndustryOfDic>().eq("topid", -1));

        Map<String, List<Double>> map = Maps.newHashMap();

        int size = 0;
        if ("行政区划".equals(type)) {
            size = areaList.size();
        } else if ("登记注册类型".equals(type)) {
            size = registerList.size();
        } else if ("所属行业".equals(type)) {
            size = industryList.size();
        }

        //职业病防护设施设置率、个人防护用品配备率
        for (int i = 0; i < size; i++) {
            List<Double> list = Lists.newArrayList();
            //
            double d1 = RandomUtil.randomDouble(1.0, 100.0);
            list.add(DoubleUtil.get(d1));
            //
            double d2 = RandomUtil.randomDouble(1.0, 100.0);
            list.add(DoubleUtil.get(d2));
            //
            map.put("list" + i, list);
        }
        return map;
    }

    @GetMapping("/option23Detail")
    public List<Twenty> option23Detail(String year, String type) {
        List<Twenty> list = Lists.newArrayList();
        List<String> list2 = null;
        if ("行政区划".equals(type)) {
            list2 = visualUtil.getAreaStrList("nation");
        } else if ("登记注册类型".equals(type)) {
            list2 = visualUtil.getRegisterTypeStrList();
        } else if ("所属行业".equals(type)) {
            list2 = visualUtil.getIndustryStrList();
        }
        for (String s : list2) {
            Twenty tmp = new Twenty();
            tmp.setName(s);
            tmp.setVar1(RandomUtil.randomInt(1, 10000));
            tmp.setVar2(RandomUtil.randomInt(1, 10000));
            tmp.setVar3(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            tmp.setVar4(RandomUtil.randomInt(1, 10000));
            tmp.setVar5(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            list.add(tmp);
        }
        return list;
    }


    //行政区划、登记注册类型、所属行业
    @GetMapping("/option24")
    public Map<String, List<Double>> option24(String year, String type) {
        List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1).notIn("name", "澳门", "台湾"));
        List<BaseOfDic> registerList = baseOfDicService.list(new QueryWrapper<BaseOfDic>().eq("flag", "登记注册类型"));
        List<IndustryOfDic> industryList = industryOfDicService.list(new QueryWrapper<IndustryOfDic>().eq("topid", -1));

        Map<String, List<Double>> map = Maps.newHashMap();
        //体检率、累计职业病患病率、累计职业病病死率
        List<Double> list1 = Lists.newArrayList();
        List<Double> list2 = Lists.newArrayList();
        List<Double> list3 = Lists.newArrayList();

        int size = 0;
        if ("行政区划".equals(type)) {
            size = areaList.size();
        } else if ("登记注册类型".equals(type)) {
            size = registerList.size();
        } else if ("所属行业".equals(type)) {
            size = industryList.size();
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
        }
        map.put("list1", list1);
        map.put("list2", list2);
        map.put("list3", list3);
        return map;
    }

    @GetMapping("/option24Detail")
    public List<Twenty> option24Detail(String year, String type) {
        List<Twenty> list = Lists.newArrayList();
        List<String> list2 = null;
        if ("行政区划".equals(type)) {
            list2 = visualUtil.getAreaStrList("nation");
        } else if ("登记注册类型".equals(type)) {
            list2 = visualUtil.getRegisterTypeStrList();
        } else if ("所属行业".equals(type)) {
            list2 = visualUtil.getIndustryStrList();
        }
        for (String s : list2) {
            Twenty tmp = new Twenty();
            tmp.setName(s);
            tmp.setVar1(RandomUtil.randomInt(1, 10000));
            tmp.setVar2(RandomUtil.randomInt(1, 10000) + "%");
            tmp.setVar3(RandomUtil.randomInt(1, 10000));
            tmp.setVar4(RandomUtil.randomInt(1, 10000));
            tmp.setVar5(RandomUtil.randomInt(1, 10000));
            tmp.setVar6(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            tmp.setVar7(RandomUtil.randomInt(1, 10000));
            tmp.setVar8(RandomUtil.randomInt(1, 10000));
            tmp.setVar9(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            tmp.setVar10(RandomUtil.randomInt(1, 10000));
            list.add(tmp);
        }
        return list;
    }

    /*
    表2-9 基础信息统计分析表（按行政区划续4）
    type=企业规模、行政区划、登记注册类型、所属行业
     */
    @GetMapping("/option25")
    public Map<String, List<Double>> option25(String year, String type) {
        List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1).notIn("name", "澳门", "台湾"));
        List<BaseOfDic> registerList = baseOfDicService.list(new QueryWrapper<BaseOfDic>().eq("flag", "登记注册类型"));
        List<IndustryOfDic> industryList = industryOfDicService.list(new QueryWrapper<IndustryOfDic>().eq("topid", -1));

        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = industryList.size() - 1; i > -1; i--) {
            String str = "'" + industryList.get(i).getName() + "',";
            sb.append(str);
        }
        sb.append("]");
        System.out.println(sb.toString());

        Map<String, List<Double>> map = Maps.newHashMap();
        //劳动合同签订率、工伤保险参保率
        List<Double> list1 = Lists.newArrayList();
        List<Double> list2 = Lists.newArrayList();

        int size = 0;
        if ("行政区划".equals(type)) {
            size = areaList.size();
        } else if ("登记注册类型".equals(type)) {
            size = registerList.size();
        } else if ("所属行业".equals(type)) {
            size = industryList.size();
        } else if ("企业规模".equals(type)) {
            size = 4;
        }

        for (int i = 0; i < size; i++) {
            //
            double d1 = RandomUtil.randomDouble(1.0, 100.0);
            list1.add(DoubleUtil.get(d1));
            //
            double d2 = RandomUtil.randomDouble(1.0, d1);
            list2.add(DoubleUtil.get(d2));
        }
        map.put("list1", list1);
        map.put("list2", list2);
        return map;
    }

    @GetMapping("/option25Detail")
    public List<Twenty> option25Detail(String year, String type) {
        List<Twenty> list = Lists.newArrayList();
        List<String> list2 = null;
        if ("行政区划".equals(type)) {
            list2 = visualUtil.getAreaStrList("nation");
        } else if ("登记注册类型".equals(type)) {
            list2 = visualUtil.getRegisterTypeStrList();
        } else if ("所属行业".equals(type)) {
            list2 = visualUtil.getIndustryStrList();
        } else if ("危害因素".equals(type)) {
            list2 = visualUtil.getDangerList();
        } else if ("企业规模".equals(type)) {
            list2 = visualUtil.getEnterpriseSize();
        }
        for (String s : list2) {
            Twenty tmp = new Twenty();
            tmp.setName(s);
            tmp.setVar1(RandomUtil.randomInt(1, 10000));
            tmp.setVar2(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            tmp.setVar3(RandomUtil.randomInt(1, 10000));
            tmp.setVar4(DoubleUtil.get(RandomUtil.randomDouble(1.0, 100.0)) + "%");
            list.add(tmp);
        }
        return list;
    }
}
