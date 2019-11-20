package com.hthyaq.zybadmin.controller.dataVisual.other3;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hthyaq.zybadmin.common.utils.DoubleUtil;
import com.hthyaq.zybadmin.common.utils.cache.DataVisualCacheUtil;
import com.hthyaq.zybadmin.controller.dataVisual.vo.Twenty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enterpriseVisual/no")
public class EnterpriseVisualNo {
    //危害因素、企业规模
    /*
    表2-1 基础信息统计分析表（按危害因素）
    type=危害因素、企业规模
     */
    @GetMapping("/option11")
    public List<Double> option11(String year, String type, String name1, String name2, String name3, String name) {
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
    public List<Twenty> option11Detail(String year, String type, String name1, String name2, String name3, String name) {
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
            List<String> enterpriseSizeList = DataVisualCacheUtil.getEnterpriseSize();
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
    public Map<String, List<Double>> option12(String year, String type, String name1, String name2, String name3, String name) {
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
    public List<Twenty> option12Detail(String year, String type, String name1, String name2, String name3, String name) {
        List<Twenty> list = Lists.newArrayList();
        if ("危害因素".equals(type)) {
            List<String> dangerList = DataVisualCacheUtil.getDangerList();
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
            List<String> enterpriseSizeList = DataVisualCacheUtil.getEnterpriseSize();
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
    public Map<String, List<Double>> option13(String year, String type, String name1, String name2, String name3, String name) {
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
    public List<Twenty> option13Detail(String year, String type, String name1, String name2, String name3, String name) {
        List<Twenty> list = Lists.newArrayList();
        if ("危害因素".equals(type)) {
            List<String> dangerList = DataVisualCacheUtil.getDangerList();
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
            List<String> enterpriseSizeList = DataVisualCacheUtil.getEnterpriseSize();
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
    public Map<String, List<Double>> option14(String year, String type, String name1, String name2, String name3, String name) {
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
    public List<Twenty> option14Detail(String year, String type, String name1, String name2, String name3, String name) {
        List<Twenty> list = Lists.newArrayList();
        if ("危害因素".equals(type)) {
            List<String> dangerList = DataVisualCacheUtil.getDangerList();
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
            List<String> enterpriseSizeList = DataVisualCacheUtil.getEnterpriseSize();
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

}
