package com.hthyaq.zybadmin.controller.dataVisual.nationyes;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hthyaq.zybadmin.common.utils.DoubleUtil;
import com.hthyaq.zybadmin.common.utils.cache.DataVisualCacheUtil;
import com.hthyaq.zybadmin.controller.dataVisual.vo.*;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.EscapedErrors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/nationServiceVisual/yes")
public class NationServiceVisualYes {
    @Autowired
    JianceDetailOfServiceService jianceDetailOfServiceService;
    @Autowired
    JianceBasicOfServiceService jianceBasicOfServiceService;
    @Autowired
    TijianTotalOfServiceService tijianTotalOfServiceService;
    @Autowired
    TijianDetail1OfServiceService tijianDetail1OfServiceService;
    @Autowired
    TijianDetail2OfServiceService tijianDetail2OfServiceService;
    @Autowired
    TijianBasicOfServiceService tijianBasicOfServiceService;
    @Autowired
    ZhenduanDetailOfServiceService zhenduanDetailOfServiceService;
    @Autowired
    ZhenduanTotalOfServiceService zhenduanTotalOfServiceService;
    @Autowired
    ZhenduanBasicOfServiceService zhenduanBasicOfServiceService;
    @Autowired
    JianceTotalOfServiceService jianceTotalOfServiceService;

    //危害因素
    //    表1  作业场所职业病危害因素检测情况统计分析表
    @GetMapping("/option11")
    public List<NameValueDouble> option11(String year, String type) {
        List<NameValueDouble> list = Lists.newArrayList();
        int count = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "粉尘").eq("checkYear", year));
        int count1 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "粉尘").eq("checkYear", year).eq("decideResult", "合格"));
        if(count==0){
            double d1 = 0;
            list.add(new NameValueDouble("粉尘", DoubleUtil.get(d1)));
        }else {
            double d1 = count1 / count;
            list.add(new NameValueDouble("粉尘", DoubleUtil.get(d1)));
        }

        int counth1 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "化学因素").eq("checkYear", year));
        int counth2 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "化学因素").eq("checkYear", year).eq("decideResult", "合格"));
        if(counth1==0){
            double d2 = 0;
            list.add(new NameValueDouble("化学因素", DoubleUtil.get(d2)));
        }else {
            double d2 = counth2 / counth1;
            list.add(new NameValueDouble("化学因素", DoubleUtil.get(d2)));
        }

        int counthw1 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "物理因素").eq("checkYear", year));
        int counthw2 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "物理因素").eq("checkYear", year).eq("decideResult", "合格"));
        if(counthw1==0){
            double d3 = 0;
            list.add(new NameValueDouble("物理因素", DoubleUtil.get(d3)));
        }else {
            double d3 = counthw2 / counthw1;
            list.add(new NameValueDouble("物理因素", DoubleUtil.get(d3)));
        }

        int counthf1 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "放射性因素").eq("checkYear", year));
        int counthf2 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "放射性因素").eq("checkYear", year).eq("decideResult", "合格"));
        if(counthf1==0){
            double d4 = 0;
            list.add(new NameValueDouble("放射性因素", DoubleUtil.get(d4)));
        }else {
            double d4 = counthf2 / counthf1;
            list.add(new NameValueDouble("放射性因素", DoubleUtil.get(d4)));
        }

        int counths1 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "生物因素").eq("checkYear", year));
        int counths2 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "生物因素").eq("checkYear", year).eq("decideResult", "合格"));
        if(counths1==0){
            double d5 = 0;
            list.add(new NameValueDouble("生物因素", DoubleUtil.get(d5)));
        }else {
            double d5 = counths2 / counths1;
            list.add(new NameValueDouble("生物因素", DoubleUtil.get(d5)));
        }

        return list;
    }

    @GetMapping("/option11Detail")
    public List<Twenty> option11Detail(String year, String type) {
        List<Twenty> list = Lists.newArrayList();
        List<String> list2 = null;
        if ("危害因素".equals(type)) {
            list2 = DataVisualCacheUtil.getDangerList();
        } else if ("行政区划".equals(type)) {
            list2 = DataVisualCacheUtil.getAreaStrChildren("国家", null, null);
        } else if ("登记注册类型".equals(type)) {
            list2 = DataVisualCacheUtil.getRegisterTypeStrList();
        }
        for (String s : list2) {
            Twenty tmp = new Twenty();
            if ("危害因素".equals(type)) {
                tmp.setName(s);
                int count = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", s).eq("checkYear", year));
                int count1 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", s).eq("checkYear", year).eq("decideResult", "合格"));
                if(count==0&&count1==0){
                    tmp.setVar1(0);
                    tmp.setVar2(0);
                    tmp.setVar3(0 + "%");
                    list.add(tmp);
                }else {
                    tmp.setVar1(count);
                    tmp.setVar2(count1);
                    tmp.setVar3(count1 / count * 100 + "%");
                    list.add(tmp);
                }

            } else if ("行政区划".equals(type)) {
                tmp.setName(s);
                int count = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("provinceName", s).eq("checkYear", year));
                int count1 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("provinceName", s).eq("checkYear", year).eq("decideResult", "合格"));
                if(count==0&&count1==0){
                    tmp.setVar1(0);
                    tmp.setVar2(0);
                    tmp.setVar3(0 + "%");
                    list.add(tmp);
                }else {
                    tmp.setVar1(count);
                    tmp.setVar2(count1);
                    tmp.setVar3(count1 / count * 100 + "%");
                    list.add(tmp);
                }
            } else if ("登记注册类型".equals(type)) {
                tmp.setName(s);
                int count = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("registerBigName", s).eq("checkYear", year));
                int count1 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("registerBigName", s).eq("checkYear", year).eq("decideResult", "合格"));
                if(count==0&&count1==0){
                    tmp.setVar1(0);
                    tmp.setVar2(0);
                    tmp.setVar3(0 + "%");
                    list.add(tmp);
                }else {
                    tmp.setVar1(count);
                    tmp.setVar2(count1);
                    tmp.setVar3(count1 / count * 100 + "%");
                    list.add(tmp);
                }
            }
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
        int count = tijianTotalOfServiceService.count();
        int count1 = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().eq("checkYear", year).eq("result", "职业禁忌证"));
        if(count==0){
            double d1 = 0;
            list1.add(DoubleUtil.get(d1));
        }else {
            double d1 = count1 / count;
            list1.add(DoubleUtil.get(d1));
            //
        }

        int count2 = tijianDetail2OfServiceService.count(new QueryWrapper<TijianDetail2OfService>().eq("checkYear", year));
        if(count==0){
            double d2 =0;
            list2.add(DoubleUtil.get(d2));
        }else {
            double d2 = count2 / count;
            list2.add(DoubleUtil.get(d2));
        }

        //
        int enterpriseCode = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().eq("checkYear", year));
        int enterpriseCode2 = tijianDetail2OfServiceService.count(new QueryWrapper<TijianDetail2OfService>().eq("checkYear", year));
        if(enterpriseCode==0){
            double d3 = 0;
            list3.add(DoubleUtil.get(d3));
        }else {
            double d3 = enterpriseCode2 / enterpriseCode;
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
            list2 = DataVisualCacheUtil.getDangerList();
        } else if ("行政区划".equals(type)) {
            list2 = DataVisualCacheUtil.getAreaStrChildren("国家", null, null);
        } else if ("登记注册类型".equals(type)) {
            list2 = DataVisualCacheUtil.getRegisterTypeStrList();
        }
        for (String s : list2) {
            if ("危害因素".equals(type)) {
                Twenty tmp = new Twenty();
                tmp.setName(s);
                int enterpriseCode = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where scope="+"\""+s+"\"").eq("checkYear", year));
                int count = tijianTotalOfServiceService.count(new QueryWrapper<TijianTotalOfService>().inSql("tijianBasicId","select id from tijianBasicOfService where scope="+"\""+s+"\"").eq("year", year));
                int count1 = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where scope="+"\""+s+"\"").eq("result", "职业禁忌证").eq("checkYear", year));
                int count2 = tijianDetail2OfServiceService.count(new QueryWrapper<TijianDetail2OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where scope="+"\""+s+"\"").eq("checkYear", year));
                int count3 = tijianDetail2OfServiceService.count(new QueryWrapper<TijianDetail2OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where scope="+"\""+s+"\"").eq("checkYear", year));
                if(count==0&&enterpriseCode==0&&count1==0&&count2==0&&count3==0){
                        tmp.setVar1(0);
                        tmp.setVar2(0);
                        tmp.setVar3(0);
                        tmp.setVar4(0 + "%");
                        tmp.setVar6(0 + "%");
                        tmp.setVar8(0 + "%");
                        tmp.setVar5(0);
                        tmp.setVar7(0);
                    }else {
                        tmp.setVar1(enterpriseCode);
                        tmp.setVar2(count);
                        tmp.setVar3(count1);
                        tmp.setVar4(count1 / count * 100 + "%");
                        tmp.setVar6(count2 / count * 100 + "%");
                        tmp.setVar8(count3 / count * 100 + "%");
                        tmp.setVar5(count2);
                        tmp.setVar7(count3);
                    }


                list.add(tmp);
            } else if ("行政区划".equals(type)) {
                Twenty tmp = new Twenty();
                tmp.setName(s);

                int enterpriseCode = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where provinceName="+"\""+s+"\"").eq("checkYear", year));
                int count = tijianTotalOfServiceService.count(new QueryWrapper<TijianTotalOfService>().inSql("tijianBasicId","select id from tijianBasicOfService where provinceName="+"\""+s+"\"").eq("year", year));
                int count1 = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where provinceName="+"\""+s+"\"").eq("result", "职业禁忌证").eq("checkYear", year));
                int count2 = tijianDetail2OfServiceService.count(new QueryWrapper<TijianDetail2OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where provinceName="+"\""+s+"\"").eq("checkYear", year));
                int count3 = tijianDetail2OfServiceService.count(new QueryWrapper<TijianDetail2OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where provinceName="+"\""+s+"\"").eq("checkYear", year));

                if(count==0&&enterpriseCode==0&&count1==0&&count2==0&&count3==0){
                        tmp.setVar1(0);
                        tmp.setVar2(0);
                        tmp.setVar3(0);
                        tmp.setVar4(0 + "%");
                        tmp.setVar6(0 + "%");
                        tmp.setVar8(0 + "%");
                        tmp.setVar5(0);
                        tmp.setVar7(0);
                    }else {
                        tmp.setVar1(enterpriseCode);
                        tmp.setVar2(count);
                        tmp.setVar3(count1);
                        tmp.setVar4(count1 / count * 100 + "%");
                        tmp.setVar6(count2 / count * 100 + "%");
                        tmp.setVar8(count3 / count * 100 + "%");
                        tmp.setVar5(count2);
                        tmp.setVar7(count3);
                    }

                list.add(tmp);
            } else if ("登记注册类型".equals(type)) {
                Twenty tmp = new Twenty();
                tmp.setName(s);
                int enterpriseCode = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where registerBigName="+"\""+s+"\"").eq("checkYear", year));
                int count = tijianTotalOfServiceService.count(new QueryWrapper<TijianTotalOfService>().inSql("tijianBasicId","select id from tijianBasicOfService where registerBigName="+"\""+s+"\"").eq("year", year));
                int count1 = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where registerBigName="+"\""+s+"\"").eq("result", "职业禁忌证").eq("checkYear", year));
                int count2 = tijianDetail2OfServiceService.count(new QueryWrapper<TijianDetail2OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where registerBigName="+"\""+s+"\"").eq("checkYear", year));
                int count3 = tijianDetail2OfServiceService.count(new QueryWrapper<TijianDetail2OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where registerBigName="+"\""+s+"\"").eq("checkYear", year));
                    if(count==0&&enterpriseCode==0&&count1==0&&count2==0&&count3==0){
                        tmp.setVar1(0);
                        tmp.setVar2(0);
                        tmp.setVar3(0);
                        tmp.setVar4(0 + "%");
                        tmp.setVar6(0 + "%");
                        tmp.setVar8(0 + "%");
                        tmp.setVar5(0);
                        tmp.setVar7(0);
                    }else {
                        tmp.setVar1(enterpriseCode);
                        tmp.setVar2(count);
                        tmp.setVar3(count1);
                        tmp.setVar4(count1 / count * 100 + "%");
                        tmp.setVar6(count2 / count * 100 + "%");
                        tmp.setVar8(count3 / count * 100 + "%");
                        tmp.setVar5(count2);
                        tmp.setVar7(count3);
                    }
                list.add(tmp);
            }

        }
        return list;
    }

    //表3 职业病诊断情况统计分析表
    @GetMapping("/option13")
    public Map<String, List<Double>> option13(String year, String type) {
        Map<String, List<Double>> map = Maps.newHashMap();
        //职业病诊断率
        List<Double> list1 = Lists.newArrayList();
        int count = zhenduanDetailOfServiceService.count(new QueryWrapper<ZhenduanDetailOfService>().eq("checkYear", year));
        int count1 = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().eq("Year", year));
       if(count1==0){
           double d1 =0;
           list1.add(DoubleUtil.get(d1));
       }else{
           double d1 = count / count1;
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
            list2 = DataVisualCacheUtil.getDangerList();
        } else if ("行政区划".equals(type)) {
            list2 = DataVisualCacheUtil.getAreaStrChildren("国家", null, null);
        } else if ("登记注册类型".equals(type)) {
            list2 = DataVisualCacheUtil.getRegisterTypeStrList();
        }
        for (String s : list2) {
            if ("危害因素".equals(type)) {
                Twenty tmp = new Twenty();
                tmp.setName(s);

                    int count2 = zhenduanDetailOfServiceService.count(new QueryWrapper<ZhenduanDetailOfService>().inSql("zhenduanBasicId","select id from zhenduanBasicOfService where scope="+"\""+s+"\"").eq("checkYear", year));
                    int enterpriseCode = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().inSql("zhenduanBasicId","select id from zhenduanBasicOfService where scope="+"\""+s+"\"").eq("year", year));
                    int count1 = zhenduanDetailOfServiceService.count(new QueryWrapper<ZhenduanDetailOfService>().inSql("zhenduanBasicId", "select id from zhenduanBasicOfService where scope="+"\""+s+"\"").eq("checkYear", year));
                    int idNum = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().inSql("zhenduanBasicId", "select id from zhenduanBasicOfService where scope="+"\""+s+"\"").eq("year", year));
                if(count2==0&&enterpriseCode==0&&count1==0&&idNum==0){

                    tmp.setVar1(0);
                    tmp.setVar2(0);
                    tmp.setVar3(0);
                    tmp.setVar4(0);
                    tmp.setVar5(0 + "%");
                    list.add(tmp);
                }else {

                    tmp.setVar1(count2);
                    tmp.setVar2(enterpriseCode);
                    tmp.setVar3(count1);
                    tmp.setVar4(idNum);
                    tmp.setVar5(idNum / count1 * 100 + "%");
                    list.add(tmp);
                }

            } else if ("行政区划".equals(type)) {
                Twenty tmp = new Twenty();
                tmp.setName(s);

                int count2 = zhenduanDetailOfServiceService.count(new QueryWrapper<ZhenduanDetailOfService>().inSql("zhenduanBasicId","select id from zhenduanBasicOfService where provinceName="+"\""+s+"\"").eq("checkYear", year));
                int enterpriseCode = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().inSql("zhenduanBasicId","select id from zhenduanBasicOfService where provinceName="+"\""+s+"\"").eq("year", year));
                int count1 = zhenduanDetailOfServiceService.count(new QueryWrapper<ZhenduanDetailOfService>().inSql("zhenduanBasicId", "select id from zhenduanBasicOfService where provinceName="+"\""+s+"\"").eq("checkYear", year));
                int idNum = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().inSql("zhenduanBasicId", "select id from zhenduanBasicOfService where provinceName="+"\""+s+"\"").eq("year", year));
                if(count2==0&&enterpriseCode==0&&count1==0&&idNum==0){

                    tmp.setVar1(0);
                    tmp.setVar2(0);
                    tmp.setVar3(0);
                    tmp.setVar4(0);
                    tmp.setVar5(0 + "%");
                    list.add(tmp);
                }else {

                    tmp.setVar1(count2);
                    tmp.setVar2(enterpriseCode);
                    tmp.setVar3(count1);
                    tmp.setVar4(idNum);
                    tmp.setVar5(idNum / count1 * 100 + "%");
                    list.add(tmp);
                }

            } else if ("登记注册类型".equals(type)) {
                Twenty tmp = new Twenty();
                tmp.setName(s); int count2 = zhenduanDetailOfServiceService.count(new QueryWrapper<ZhenduanDetailOfService>().inSql("zhenduanBasicId","select id from zhenduanBasicOfService where registerBigName="+"\""+s+"\"").eq("checkYear", year));
                int enterpriseCode = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().inSql("zhenduanBasicId","select id from zhenduanBasicOfService where registerBigName="+"\""+s+"\"").eq("year", year));
                int count1 = zhenduanDetailOfServiceService.count(new QueryWrapper<ZhenduanDetailOfService>().inSql("zhenduanBasicId", "select id from zhenduanBasicOfService where registerBigName="+"\""+s+"\"").eq("checkYear", year));
                int idNum = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().inSql("zhenduanBasicId", "select id from zhenduanBasicOfService where registerBigName="+"\""+s+"\"").eq("year", year));

                if(count2==0&&enterpriseCode==0&&count1==0&&idNum==0){

                    tmp.setVar1(0);
                    tmp.setVar2(0);
                    tmp.setVar3(0);
                    tmp.setVar4(0);
                    tmp.setVar5(0 + "%");
                    list.add(tmp);
                }else {

                    tmp.setVar1(count2);
                    tmp.setVar2(enterpriseCode);
                    tmp.setVar3(count1);
                    tmp.setVar4(idNum);
                    tmp.setVar5(idNum / count1 * 100 + "%");
                    list.add(tmp);
                }
            }
        }
        return list;
    }

    //行政区划、登记注册类型
    @GetMapping("/option21")
    public Map<String, List<Double>> option21(String year, String type) {
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家", null, null);
        List<BaseOfDic> registerList = DataVisualCacheUtil.getRegisterTypeList();

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
            int count = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "粉尘").eq("checkYear", year));
            int count1 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "粉尘").eq("checkYear", year).eq("decideResult", "合格"));
            if(count==0){
                double d1 = 0;
                list1.add(DoubleUtil.get(d1));
            }else {
                double d1 = count1 / count;
                list1.add(DoubleUtil.get(d1));
            }

            //
            int counth = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "化学因素").eq("checkYear", year));
            int counth1 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "化学因素").eq("checkYear", year).eq("decideResult", "合格"));
            if(counth==0){
                double d2 = 0;
                list2.add(DoubleUtil.get(d2));
            }else {
                double d2 = counth1 / counth;
                list2.add(DoubleUtil.get(d2));
            }

            //
            int countw = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "物理因素").eq("checkYear", year));
            int countw1 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "物理因素").eq("checkYear", year).eq("decideResult", "合格"));
            if(countw==0){
                double d3 = 0;
                list3.add(DoubleUtil.get(d3));
            }else{
                double d3 = countw1 / countw;
                list3.add(DoubleUtil.get(d3));
            }

            //
            int countf = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "放射性因素").eq("checkYear", year));
            int countf1 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "放射性因素").eq("checkYear", year).eq("decideResult", "合格"));
            if(countf==0){
                double d4 =0;
                list4.add(DoubleUtil.get(d4));
            }else {
                double d4 = countf1 / countf;
                list4.add(DoubleUtil.get(d4));
            }

            //
            int counts = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "生物因素").eq("checkYear", year));
            int counts1 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", "生物因素").eq("checkYear", year).eq("decideResult", "合格"));
            if(counts==0){
                double d5 = 0;
                list5.add(DoubleUtil.get(d5));
            }else{
                double d5 = counts1 / counts;
                list5.add(DoubleUtil.get(d5));
            }

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
            list2 = DataVisualCacheUtil.getDangerList();
        } else if ("行政区划".equals(type)) {
            list2 = DataVisualCacheUtil.getAreaStrChildren("国家", null, null);
        } else if ("登记注册类型".equals(type)) {
            list2 = DataVisualCacheUtil.getRegisterTypeStrList();
        }
        for (String s : list2) {
            Twenty tmp = new Twenty();
            if ("危害因素".equals(type)) {
                tmp.setName(s);
                int count = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", s).eq("checkYear", year));
                int count1 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("dangerBigName", s).eq("checkYear", year).eq("decideResult", "合格"));
                if(count==0&&count1==0){
                    tmp.setVar1(0);
                    tmp.setVar2(0);
                    tmp.setVar3(0 + "%");
                    list.add(tmp);
                }else {
                    tmp.setVar1(count);
                    tmp.setVar2(count1);
                    tmp.setVar3(count1 / count * 100 + "%");
                    list.add(tmp);
                }
            } else if ("行政区划".equals(type)) {
                tmp.setName(s);
                int count = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("provinceName", s).eq("checkYear", year));
                int count1 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("provinceName", s).eq("checkYear", year).eq("decideResult", "合格"));
                if(count==0&&count1==0){
                    tmp.setVar1(0);
                    tmp.setVar2(0);
                    tmp.setVar3(0 + "%");
                    list.add(tmp);
                }else {
                    tmp.setVar1(count);
                    tmp.setVar2(count1);
                    tmp.setVar3(count1 / count * 100 + "%");
                    list.add(tmp);
                }

            } else if ("登记注册类型".equals(type)) {
                tmp.setName(s);
                int count = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("registerBigName", s).eq("checkYear", year));
                int count1 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("registerBigName", s).eq("checkYear", year).eq("decideResult", "合格"));
                if(count==0&&count1==0){
                    tmp.setVar1(0);
                    tmp.setVar2(0);
                    tmp.setVar3(0 + "%");
                    list.add(tmp);
                }else {
                    tmp.setVar1(count);
                    tmp.setVar2(count1);
                    tmp.setVar3(count1 / count * 100 + "%");
                    list.add(tmp);
                }
            }
        }
        return list;
    }

    @GetMapping("/option22")
    public Map<String, List<Double>> option22(String year, String type) {
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家", null, null);
        List<BaseOfDic> registerList = DataVisualCacheUtil.getRegisterTypeList();

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
            int count = tijianTotalOfServiceService.count();
            int count1 = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().eq("checkYear", year).eq("result", "职业禁忌证"));

            if(count==0){
                double d1 = 0;
                list1.add(DoubleUtil.get(d1));
            }else {
                double d1 = count1 / count;
                list1.add(DoubleUtil.get(d1));
            }
            //
            int count2 = tijianDetail2OfServiceService.count(new QueryWrapper<TijianDetail2OfService>().eq("checkYear", year));
            if(count==0){
                double d2 = 0;
                list2.add(DoubleUtil.get(d2));
            }else {
                double d2 = count2 / count;
                list2.add(DoubleUtil.get(d2));
            }

            //
            int enterpriseCode = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().eq("checkYear", year));
            int enterpriseCode2 = tijianDetail2OfServiceService.count(new QueryWrapper<TijianDetail2OfService>().eq("checkYear", year));
            if(enterpriseCode==0){
                double d3 = 0;
                list3.add(DoubleUtil.get(d3));
            }else {
                double d3 = enterpriseCode2 / enterpriseCode;
                list3.add(DoubleUtil.get(d3));
            }

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
            list2 = DataVisualCacheUtil.getDangerList();
        } else if ("行政区划".equals(type)) {
            list2 = DataVisualCacheUtil.getAreaStrChildren("国家", null, null);
        } else if ("登记注册类型".equals(type)) {
            list2 = DataVisualCacheUtil.getRegisterTypeStrList();
        }
        for (String s : list2) {
            if ("危害因素".equals(type)) {
                Twenty tmp = new Twenty();
                tmp.setName(s);
                List<TijianBasicOfService> listt = tijianBasicOfServiceService.list(new QueryWrapper<TijianBasicOfService>().eq("scope", s));
                for (TijianBasicOfService tijianBasicOfService : listt) {
                    int enterpriseCode = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where scope="+"\""+s+"\"").eq("checkYear", year));
                    int count = tijianTotalOfServiceService.count(new QueryWrapper<TijianTotalOfService>().inSql("tijianBasicId","select id from tijianBasicOfService where scope="+"\""+s+"\"").eq("year", year));
                    int count1 = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where scope="+"\""+s+"\"").eq("result", "职业禁忌证").eq("checkYear", year));
                    int count2 = tijianDetail2OfServiceService.count(new QueryWrapper<TijianDetail2OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where scope="+"\""+s+"\"").eq("checkYear", year));
                    int count3 = tijianDetail2OfServiceService.count(new QueryWrapper<TijianDetail2OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where scope="+"\""+s+"\"").eq("checkYear", year));

                    if(count==0&&enterpriseCode==0&&count1==0&&count2==0&&count3==0){
                        tmp.setVar1(0);
                        tmp.setVar2(0);
                        tmp.setVar3(0);
                        tmp.setVar4(0 + "%");
                        tmp.setVar6(0 + "%");
                        tmp.setVar8(0 + "%");
                        tmp.setVar5(0);
                        tmp.setVar7(0);
                    }else {
                        tmp.setVar1(enterpriseCode);
                        tmp.setVar2(count);
                        tmp.setVar3(count1);
                        tmp.setVar4(count1 / count * 100 + "%");
                        tmp.setVar6(count2 / count * 100 + "%");
                        tmp.setVar8(count3 / count * 100 + "%");
                        tmp.setVar5(count2);
                        tmp.setVar7(count3);
                    }


                    list.add(tmp);
                }
                list.add(tmp);
            } else if ("行政区划".equals(type)) {
                Twenty tmp = new Twenty();
                tmp.setName(s);
                int enterpriseCode = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where provinceName="+"\""+s+"\"").eq("checkYear", year));
                int count = tijianTotalOfServiceService.count(new QueryWrapper<TijianTotalOfService>().inSql("tijianBasicId","select id from tijianBasicOfService where provinceName="+"\""+s+"\"").eq("year", year));
                int count1 = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where provinceName="+"\""+s+"\"").eq("result", "职业禁忌证").eq("checkYear", year));
                int count2 = tijianDetail2OfServiceService.count(new QueryWrapper<TijianDetail2OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where provinceName="+"\""+s+"\"").eq("checkYear", year));
                int count3 = tijianDetail2OfServiceService.count(new QueryWrapper<TijianDetail2OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where provinceName="+"\""+s+"\"").eq("checkYear", year));

                if(count==0&&enterpriseCode==0&&count1==0&&count2==0&&count3==0){
                    tmp.setVar1(0);
                    tmp.setVar2(0);
                    tmp.setVar3(0);
                    tmp.setVar4(0 + "%");
                    tmp.setVar6(0 + "%");
                    tmp.setVar8(0 + "%");
                    tmp.setVar5(0);
                    tmp.setVar7(0);
                }else {
                    tmp.setVar1(enterpriseCode);
                    tmp.setVar2(count);
                    tmp.setVar3(count1);
                    tmp.setVar4(count1 / count * 100 + "%");
                    tmp.setVar6(count2 / count * 100 + "%");
                    tmp.setVar8(count3 / count * 100 + "%");
                    tmp.setVar5(count2);
                    tmp.setVar7(count3);
                }

                list.add(tmp);
            } else if ("登记注册类型".equals(type)) {
                Twenty tmp = new Twenty();
                tmp.setName(s);
                int enterpriseCode = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where registerBigName="+"\""+s+"\"").eq("checkYear", year));
                int count = tijianTotalOfServiceService.count(new QueryWrapper<TijianTotalOfService>().inSql("tijianBasicId","select id from tijianBasicOfService where registerBigName="+"\""+s+"\"").eq("year", year));
                int count1 = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where registerBigName="+"\""+s+"\"").eq("result", "职业禁忌证").eq("checkYear", year));
                int count2 = tijianDetail2OfServiceService.count(new QueryWrapper<TijianDetail2OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where registerBigName="+"\""+s+"\"").eq("checkYear", year));
                int count3 = tijianDetail2OfServiceService.count(new QueryWrapper<TijianDetail2OfService>().inSql("tijianBasicId","select id from tijianBasicOfService where registerBigName="+"\""+s+"\"").eq("checkYear", year));
                if(count==0&&enterpriseCode==0&&count1==0&&count2==0&&count3==0){
                    tmp.setVar1(0);
                    tmp.setVar2(0);
                    tmp.setVar3(0);
                    tmp.setVar4(0 + "%");
                    tmp.setVar6(0 + "%");
                    tmp.setVar8(0 + "%");
                    tmp.setVar5(0);
                    tmp.setVar7(0);
                }else {
                    tmp.setVar1(enterpriseCode);
                    tmp.setVar2(count);
                    tmp.setVar3(count1);
                    tmp.setVar4(count1 / count * 100 + "%");
                    tmp.setVar6(count2 / count * 100 + "%");
                    tmp.setVar8(count3 / count * 100 + "%");
                    tmp.setVar5(count2);
                    tmp.setVar7(count3);
                }
                list.add(tmp);
            }
        }
        return list;
    }

    @GetMapping("/option23")
    public Map<String, List<Double>> option23(String year, String type) {
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家", null, null);
        List<BaseOfDic> registerList = DataVisualCacheUtil.getRegisterTypeList();

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
            int count = zhenduanDetailOfServiceService.count(new QueryWrapper<ZhenduanDetailOfService>().eq("checkYear", year));
            int count1 = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().eq("year", year));
            if(count1==0){
                double d1 =0;
                list1.add(DoubleUtil.get(d1));
            }else {
                double d1 = count / count1;
                list1.add(DoubleUtil.get(d1));
            }

        }
        map.put("list1", list1);
        return map;
    }

    @GetMapping("/option23Detail")
    public List<Twenty> option23Detail(String year, String type) {
        List<Twenty> list = Lists.newArrayList();
        List<String> list2 = null;
        if ("危害因素".equals(type)) {
            list2 = DataVisualCacheUtil.getDangerList();
        } else if ("行政区划".equals(type)) {
            list2 = DataVisualCacheUtil.getAreaStrChildren("国家", null, null);
        } else if ("登记注册类型".equals(type)) {
            list2 = DataVisualCacheUtil.getRegisterTypeStrList();
        }
        for (String s : list2) {
            if ("危害因素".equals(type)) {
                Twenty tmp = new Twenty();
                tmp.setName(s);
                int count2 = zhenduanDetailOfServiceService.count(new QueryWrapper<ZhenduanDetailOfService>().inSql("zhenduanBasicId","select id from zhenduanBasicOfService where scope="+"\""+s+"\"").eq("checkYear", year));
                int enterpriseCode = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().inSql("zhenduanBasicId","select id from zhenduanBasicOfService where scope="+"\""+s+"\"").eq("year", year));
                int count1 = zhenduanDetailOfServiceService.count(new QueryWrapper<ZhenduanDetailOfService>().inSql("zhenduanBasicId", "select id from zhenduanBasicOfService where scope="+"\""+s+"\"").eq("checkYear", year));
                int idNum = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().inSql("zhenduanBasicId", "select id from zhenduanBasicOfService where scope="+"\""+s+"\"").eq("year", year));
                if(count2==0&&enterpriseCode==0&&count1==0&&idNum==0){

                    tmp.setVar1(0);
                    tmp.setVar2(0);
                    tmp.setVar3(0);
                    tmp.setVar4(0);
                    tmp.setVar5(0 + "%");
                    list.add(tmp);
                }else {

                    tmp.setVar1(count2);
                    tmp.setVar2(enterpriseCode);
                    tmp.setVar3(count1);
                    tmp.setVar4(idNum);
                    tmp.setVar5(idNum / count1 * 100 + "%");
                    list.add(tmp);
                }

            } else if ("行政区划".equals(type)) {
                Twenty tmp = new Twenty();
                tmp.setName(s);

                int count2 = zhenduanDetailOfServiceService.count(new QueryWrapper<ZhenduanDetailOfService>().inSql("zhenduanBasicId","select id from zhenduanBasicOfService where provinceName="+"\""+s+"\"").eq("checkYear", year));
                int enterpriseCode = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().inSql("zhenduanBasicId","select id from zhenduanBasicOfService where provinceName="+"\""+s+"\"").eq("year", year));
                int count1 = zhenduanDetailOfServiceService.count(new QueryWrapper<ZhenduanDetailOfService>().inSql("zhenduanBasicId", "select id from zhenduanBasicOfService where provinceName="+"\""+s+"\"").eq("checkYear", year));
                int idNum = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().inSql("zhenduanBasicId", "select id from zhenduanBasicOfService where provinceName="+"\""+s+"\"").eq("year", year));
                if(count2==0&&enterpriseCode==0&&count1==0&&idNum==0){

                    tmp.setVar1(0);
                    tmp.setVar2(0);
                    tmp.setVar3(0);
                    tmp.setVar4(0);
                    tmp.setVar5(0 + "%");
                    list.add(tmp);
                }else {

                    tmp.setVar1(count2);
                    tmp.setVar2(enterpriseCode);
                    tmp.setVar3(count1);
                    tmp.setVar4(idNum);
                    tmp.setVar5(idNum / count1 * 100 + "%");
                    list.add(tmp);
                }

            } else if ("登记注册类型".equals(type)) {
                Twenty tmp = new Twenty();
                tmp.setName(s); int count2 = zhenduanDetailOfServiceService.count(new QueryWrapper<ZhenduanDetailOfService>().inSql("zhenduanBasicId","select id from zhenduanBasicOfService where registerBigName="+"\""+s+"\"").eq("checkYear", year));
                int enterpriseCode = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().inSql("zhenduanBasicId","select id from zhenduanBasicOfService where registerBigName="+"\""+s+"\"").eq("year", year));
                int count1 = zhenduanDetailOfServiceService.count(new QueryWrapper<ZhenduanDetailOfService>().inSql("zhenduanBasicId", "select id from zhenduanBasicOfService where registerBigName="+"\""+s+"\"").eq("checkYear", year));
                int idNum = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().inSql("zhenduanBasicId", "select id from zhenduanBasicOfService where registerBigName="+"\""+s+"\"").eq("year", year));

                if(count2==0&&enterpriseCode==0&&count1==0&&idNum==0){

                    tmp.setVar1(0);
                    tmp.setVar2(0);
                    tmp.setVar3(0);
                    tmp.setVar4(0);
                    tmp.setVar5(0 + "%");
                    list.add(tmp);
                }else {

                    tmp.setVar1(count2);
                    tmp.setVar2(enterpriseCode);
                    tmp.setVar3(count1);
                    tmp.setVar4(idNum);
                    tmp.setVar5(idNum / count1 * 100 + "%");
                    list.add(tmp);
                }
            }
        }
        return list;
    }

    //表4-职业卫生技术服务机构统计分析表
    @GetMapping("/option24")
    public List<GovEight> option24(String year, String type) {
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家", null, null);
        List<BaseOfDic> registerList = DataVisualCacheUtil.getRegisterTypeList();

        List<GovEight> list = Lists.newArrayList();
        if ("行政区划".equals(type)) {
            for (AreaOfDic areaOfDic : areaList) {
                GovEight govEight = new GovEight();
                govEight.setArea(areaOfDic.getName());
                //检测机构数
                int count = jianceBasicOfServiceService.count(new QueryWrapper<JianceBasicOfService>().eq("year",year).eq("provinceName",type));
                if(count==0){
                    govEight.setVar1(0);
                }else {
                    govEight.setVar1(count);
                }

                //专业技术人员数
                int technologyCount = jianceBasicOfServiceService.count(new QueryWrapper<JianceBasicOfService>().eq("year",year).eq("provinceName",type));
                if(technologyCount==0) {
                    govEight.setVar2(0);
                }else {
                    govEight.setVar2(technologyCount);
                }

                //经培训合格数
               int passCount= jianceBasicOfServiceService.count(new QueryWrapper<JianceBasicOfService>().eq("year",year).eq("provinceName",type));
                if(passCount==0) {
                    govEight.setVar3(0);
                }else {
                    govEight.setVar3(passCount);
                }

                //检测仪器台套数
                int equipmentCount= jianceBasicOfServiceService.count(new QueryWrapper<JianceBasicOfService>().eq("year",year).eq("provinceName",type));
                if(passCount==0) {
                    govEight.setVar4(0);
                }else {
                    govEight.setVar4(equipmentCount);
                }

                //计量认证项目数
                int projectCount=jianceBasicOfServiceService.count(new QueryWrapper<JianceBasicOfService>().eq("year",year).eq("provinceName",type));
                if(projectCount==0) {
                    govEight.setVar5(0);
                }else {
                    govEight.setVar5(projectCount);
                }
                    int count1 = jianceTotalOfServiceService.count(new QueryWrapper<JianceTotalOfService>().eq("year",year).inSql("jianceBasicId","select id from jianceBasicOfService where provinceName="+"\""+areaOfDic.getName()+"\""));
                if(count1==0) {
                    govEight.setVar6(0);
                }else {
                    govEight.setVar6(count1);
                }

                    int count2 =jianceTotalOfServiceService.count(new QueryWrapper<JianceTotalOfService>().eq("year",year).inSql("jianceBasicId","select id from jianceBasicOfService where provinceName="+"\""+areaOfDic.getName()+"\""));
                if(count1==0) {
                    govEight.setVar7(0);
                }else {
                    govEight.setVar7(count2);
                }
                int count3 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("checkYear",year).eq("provinceName",areaOfDic.getName()));
                if(count3==0) {
                    govEight.setVar8(0);
                }else {
                    govEight.setVar8(count3);
                }

                list.add(govEight);
            }
        } else if ("登记注册类型".equals(type)) {
            for (BaseOfDic baseOfDic : registerList) {
                GovEight govEight = new GovEight();
                govEight.setArea(baseOfDic.getBigName());
                //检测机构数
                int count = jianceBasicOfServiceService.count(new QueryWrapper<JianceBasicOfService>().eq("year",year).eq("provinceName",type));
                if(count==0){
                    govEight.setVar1(0);
                }else {
                    govEight.setVar1(count);
                }

                //专业技术人员数
                int technologyCount = jianceBasicOfServiceService.count(new QueryWrapper<JianceBasicOfService>().eq("year",year).eq("provinceName",type));
                if(technologyCount==0) {
                    govEight.setVar2(0);
                }else {
                    govEight.setVar2(technologyCount);
                }

                //经培训合格数
                int passCount= jianceBasicOfServiceService.count(new QueryWrapper<JianceBasicOfService>().eq("year",year).eq("provinceName",type));
                if(passCount==0) {
                    govEight.setVar3(0);
                }else {
                    govEight.setVar3(passCount);
                }

                //检测仪器台套数
                int equipmentCount= jianceBasicOfServiceService.count(new QueryWrapper<JianceBasicOfService>().eq("year",year).eq("provinceName",type));
                if(passCount==0) {
                    govEight.setVar4(0);
                }else {
                    govEight.setVar4(equipmentCount);
                }

                //计量认证项目数
                int projectCount=jianceBasicOfServiceService.count(new QueryWrapper<JianceBasicOfService>().eq("year",year).eq("provinceName",type));
                if(projectCount==0) {
                    govEight.setVar5(0);
                }else {
                    govEight.setVar5(projectCount);
                }
                int count1 = jianceTotalOfServiceService.count(new QueryWrapper<JianceTotalOfService>().eq("year",year).inSql("jianceBasicId","select id from jianceBasicOfService where registerBigName="+"\""+baseOfDic.getBigName()+"\""));
                if(count1==0) {
                    govEight.setVar6(0);
                }else {
                    govEight.setVar6(count1);
                }

                int count2 =jianceTotalOfServiceService.count(new QueryWrapper<JianceTotalOfService>().eq("year",year).inSql("jianceBasicId","select id from jianceBasicOfService where registerBigName="+"\""+baseOfDic.getBigName()+"\""));
                if(count1==0) {
                    govEight.setVar7(0);
                }else {
                    govEight.setVar7(count2);
                }
                int count3 = jianceDetailOfServiceService.count(new QueryWrapper<JianceDetailOfService>().eq("checkYear",year).eq("provinceName",baseOfDic.getBigName()));
                if(count3==0) {
                    govEight.setVar8(0);
                }else {
                    govEight.setVar8(count3);
                }

                list.add(govEight);
            }
        }
        return list;
    }

    //表5-职业健康检查机构统计分析表
    @GetMapping("/option25")
    public List<GovSix> option25(String year, String type) {
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家", null, null);
        List<BaseOfDic> registerList = DataVisualCacheUtil.getRegisterTypeList();

        List<GovSix> list = Lists.newArrayList();
        if ("行政区划".equals(type)) {
            for (AreaOfDic areaOfDic : areaList) {
                GovSix govSix = new GovSix();
                govSix.setArea(areaOfDic.getName());
                int count = tijianBasicOfServiceService.count(new QueryWrapper<TijianBasicOfService>().eq("year", year).eq("provinceName", areaOfDic.getName()));


                int count1 = tijianBasicOfServiceService.count(new QueryWrapper<TijianBasicOfService>().eq("year", year).eq("provinceName", areaOfDic.getName()));

                int count2 = tijianBasicOfServiceService.count(new QueryWrapper<TijianBasicOfService>().eq("year", year).eq("provinceName", areaOfDic.getName()));

                int projectCount = tijianBasicOfServiceService.count(new QueryWrapper<TijianBasicOfService>().eq("year", year).eq("provinceName", areaOfDic.getName()));

                int idNum = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().eq("checkYear", year).eq("provinceName", areaOfDic.getName()));

                int count3 = tijianTotalOfServiceService.count(new QueryWrapper<TijianTotalOfService>().eq("year", year));
                if(count==0&&count1==0&&count2==0&&projectCount==0&&idNum==0&&count3==0){
                    govSix.setVar1(0);
                    govSix.setVar2(0);
                    govSix.setVar3(0);
                    govSix.setVar4(0);
                    govSix.setVar5(0);
                    govSix.setVar6(0);
                    list.add(govSix);
                }else {
                    govSix.setVar1(count);
                    govSix.setVar2(count1);
                    govSix.setVar3(count2);
                    govSix.setVar4(projectCount);
                    govSix.setVar5(idNum);
                    govSix.setVar6(count3);
                    list.add(govSix);
                }


            }
        } else if ("登记注册类型".equals(type)) {
            for (BaseOfDic baseOfDic : registerList) {
                GovSix govSix = new GovSix();
                govSix.setArea(baseOfDic.getBigName());
                int count = tijianBasicOfServiceService.count(new QueryWrapper<TijianBasicOfService>().eq("year", year).eq("registerBigName", baseOfDic.getBigName()));

                int count1 = tijianBasicOfServiceService.count(new QueryWrapper<TijianBasicOfService>().eq("year", year).eq("registerBigName", baseOfDic.getBigName()));

                int count2 = tijianBasicOfServiceService.count(new QueryWrapper<TijianBasicOfService>().eq("year", year).eq("registerBigName", baseOfDic.getBigName()));

                int projectCount = tijianBasicOfServiceService.count(new QueryWrapper<TijianBasicOfService>().eq("year", year).eq("registerBigName", baseOfDic.getBigName()));

                int idNum = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().eq("checkYear", year).eq("registerBigName",baseOfDic.getBigName()));

                int count3 = tijianTotalOfServiceService.count(new QueryWrapper<TijianTotalOfService>().eq("year", year));
                if(count==0&&count1==0&&count2==0&&projectCount==0&&idNum==0&&count3==0){
                    govSix.setVar1(0);
                    govSix.setVar2(0);
                    govSix.setVar3(0);
                    govSix.setVar4(0);
                    govSix.setVar5(0);
                    govSix.setVar6(0);
                    list.add(govSix);
                }else {
                    govSix.setVar1(count);
                    govSix.setVar2(count1);
                    govSix.setVar3(count2);
                    govSix.setVar4(projectCount);
                    govSix.setVar5(idNum);
                    govSix.setVar6(count3);
                    list.add(govSix);
                }
            }
        }
        return list;
    }

    //表6-职业病诊断机构统计分析表
    @GetMapping("/option26")
    public List<ServiceFive> option26(String year, String type) {
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家", null, null);
        List<BaseOfDic> registerList = DataVisualCacheUtil.getRegisterTypeList();

        List<ServiceFive> list = Lists.newArrayList();
        if ("行政区划".equals(type)) {
            for (AreaOfDic areaOfDic : areaList) {
                ServiceFive serviceFive = new ServiceFive();
                serviceFive.setArea(areaOfDic.getName());
                int count = zhenduanBasicOfServiceService.count(new QueryWrapper<ZhenduanBasicOfService>().eq("year", year).eq("provinceName", areaOfDic.getName()));

                int doctorNum = zhenduanBasicOfServiceService.count(new QueryWrapper<ZhenduanBasicOfService>().eq("year", year).eq("provinceName", areaOfDic.getName()));

                int projectCount = zhenduanBasicOfServiceService.count(new QueryWrapper<ZhenduanBasicOfService>().eq("year", year).eq("provinceName", areaOfDic.getName()));

                int count1 = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().eq("year", year));

                int idNum = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().eq("year", year));

                if(count==0&&doctorNum==0&&projectCount==0&&count1==0&&idNum==0){
                    serviceFive.setVar1(0);
                    serviceFive.setVar2(0);
                    serviceFive.setVar3(0);
                    serviceFive.setVar4(0);
                    serviceFive.setVar5(0);
                    list.add(serviceFive);
                }else {
                    serviceFive.setVar1(count);
                    serviceFive.setVar2(doctorNum);
                    serviceFive.setVar3(projectCount);
                    serviceFive.setVar4(count1);
                    serviceFive.setVar5(idNum);
                    list.add(serviceFive);
                }

            }
        } else if ("登记注册类型".equals(type)) {
            for (BaseOfDic baseOfDic : registerList) {
                ServiceFive serviceFive = new ServiceFive();
                serviceFive.setArea(baseOfDic.getBigName());
                int count = zhenduanBasicOfServiceService.count(new QueryWrapper<ZhenduanBasicOfService>().eq("year", year).eq("registerBigName", baseOfDic.getBigName()));

                int doctorNum = zhenduanBasicOfServiceService.count(new QueryWrapper<ZhenduanBasicOfService>().eq("year", year).eq("registerBigName", baseOfDic.getBigName()));

                int projectCount = zhenduanBasicOfServiceService.count(new QueryWrapper<ZhenduanBasicOfService>().eq("year", year).eq("registerBigName", baseOfDic.getBigName()));

                int count1 = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().eq("year", year));

                int idNum = zhenduanTotalOfServiceService.count(new QueryWrapper<ZhenduanTotalOfService>().eq("year", year));
                if(count==0&&doctorNum==0&&projectCount==0&&count1==0&&idNum==0){
                    serviceFive.setVar1(0);
                    serviceFive.setVar2(0);
                    serviceFive.setVar3(0);
                    serviceFive.setVar4(0);
                    serviceFive.setVar5(0);
                    list.add(serviceFive);
                }else {
                    serviceFive.setVar1(count);
                    serviceFive.setVar2(doctorNum);
                    serviceFive.setVar3(projectCount);
                    serviceFive.setVar4(count1);
                    serviceFive.setVar5(idNum);
                    list.add(serviceFive);
                }
                list.add(serviceFive);
            }
        }
        return list;
    }
}
