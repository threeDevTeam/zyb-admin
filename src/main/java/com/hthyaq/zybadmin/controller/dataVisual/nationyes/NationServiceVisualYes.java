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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/nationServiceVisual/yes")
public class NationServiceVisualYes {
    @Autowired
    JianceDetailOfServiceService jianceDetailOfServiceService;
    @Autowired
    PostDangerOfEnterpriseService postDangerOfEnterpriseService;

    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    TijianDetail2OfServiceService tijianDetail2OfServiceService;
    @Autowired
    TijianDetail1OfServiceService tijianDetail1OfServiceService;
    @Autowired
    TijianTotalOfServiceService tijianTotalOfServiceService;
    @Autowired
    ZhenduanTotalOfServiceService zhenduanTotalOfServiceService;
    @Autowired
    ZhenduanDetailOfServiceService zhenduanDetailOfServiceService;
    //危害因素
    //    表1  作业场所职业病危害因素检测情况统计分析表
    @GetMapping("/option11")
    public List<NameValueDouble> option11(String year, String type) {
        List<NameValueDouble> list = Lists.newArrayList();


        TestingsituationPoint testingsituationPoint = new TestingsituationPoint();
        //Detectionpoints
        QueryWrapper<JianceDetailOfService> queryWrapper = new QueryWrapper();
        queryWrapper.eq("dangerBigName", "粉尘").eq("checkYear", year);
        testingsituationPoint.setDetectionpoints(jianceDetailOfServiceService.count(queryWrapper));
        //Targetnumber
        QueryWrapper<JianceDetailOfService> queryWrapper2 = new QueryWrapper();
        queryWrapper2.eq("dangerBigName", "粉尘").eq("decideResult", "合格").eq("checkYear", year);
        testingsituationPoint.setTargetnumber(jianceDetailOfServiceService.count(queryWrapper2));
        //Compliancerate
        testingsituationPoint.setCompliancerate((float) testingsituationPoint.getTargetnumber() / testingsituationPoint.getDetectionpoints());
        double d1 = testingsituationPoint.getCompliancerate();
        list.add(new NameValueDouble("粉尘", DoubleUtil.get(d1)));



        QueryWrapper<JianceDetailOfService> queryWrappeh1= new QueryWrapper();
        queryWrappeh1.eq("dangerBigName", "化学因素").eq("checkYear", year);
        testingsituationPoint.setDetectionpoints(jianceDetailOfServiceService.count(queryWrappeh1));
        //Targetnumber
        QueryWrapper<JianceDetailOfService> queryWrapperh2 = new QueryWrapper();
        queryWrapperh2.eq("dangerBigName", "化学因素").eq("decideResult", "合格").eq("checkYear", year);
        testingsituationPoint.setTargetnumber(jianceDetailOfServiceService.count(queryWrapperh2));
        //Compliancerate
        testingsituationPoint.setCompliancerate((float) testingsituationPoint.getTargetnumber() / testingsituationPoint.getDetectionpoints());
        double d2 = testingsituationPoint.getCompliancerate();
        list.add(new NameValueDouble("化学因素", DoubleUtil.get(d2)));


        QueryWrapper<JianceDetailOfService> queryWrapperw = new QueryWrapper();
        queryWrapperw.eq("dangerBigName", "物理因素").eq("checkYear", year);
        testingsituationPoint.setDetectionpoints(jianceDetailOfServiceService.count(queryWrapperw));
        //Targetnumber
        QueryWrapper<JianceDetailOfService> queryWrapperw2 = new QueryWrapper();
        queryWrapperw2.eq("dangerBigName", "物理因素").eq("decideResult", "合格").eq("checkYear", year);
        testingsituationPoint.setTargetnumber(jianceDetailOfServiceService.count(queryWrapperw2));
        //Compliancerate
        testingsituationPoint.setCompliancerate((float) testingsituationPoint.getTargetnumber() / testingsituationPoint.getDetectionpoints());
        double d3 = testingsituationPoint.getCompliancerate();
        list.add(new NameValueDouble("物理因素", DoubleUtil.get(d3)));



        QueryWrapper<JianceDetailOfService> queryWrappef = new QueryWrapper();
        queryWrappef.eq("dangerBigName", "放射性因素").eq("checkYear", year);
        testingsituationPoint.setDetectionpoints(jianceDetailOfServiceService.count(queryWrappef));
        //Targetnumber
        QueryWrapper<JianceDetailOfService> queryWrapperf2 = new QueryWrapper();
        queryWrapperf2.eq("dangerBigName", "放射性因素").eq("decideResult", "合格").eq("checkYear", year);
        testingsituationPoint.setTargetnumber(jianceDetailOfServiceService.count(queryWrapperf2));
        //Compliancerate
        testingsituationPoint.setCompliancerate((float) testingsituationPoint.getTargetnumber() / testingsituationPoint.getDetectionpoints());
        double d4 = testingsituationPoint.getCompliancerate();
        list.add(new NameValueDouble("放射性因素", DoubleUtil.get(d4)));




        QueryWrapper<JianceDetailOfService> queryWrappers = new QueryWrapper();
        queryWrappers.eq("dangerBigName", "生物因素").eq("checkYear", year);

        testingsituationPoint.setDetectionpoints(jianceDetailOfServiceService.count(queryWrappers));
        //Targetnumber
        QueryWrapper<JianceDetailOfService> queryWrappers2 = new QueryWrapper();
        queryWrappers2.eq("dangerBigName", "生物因素").eq("decideResult", "合格").eq("checkYear", year);
        testingsituationPoint.setTargetnumber(jianceDetailOfServiceService.count(queryWrappers2));
        //Compliancerat
        testingsituationPoint.setCompliancerate((float) testingsituationPoint.getTargetnumber() / testingsituationPoint.getDetectionpoints());

        double d5 =  testingsituationPoint.getCompliancerate();
        list.add(new NameValueDouble("生物因素", DoubleUtil.get(d5)));
        return list;
    }

    @GetMapping("/option11Detail")
    public List<Twenty> option11Detail(String year, String type) {
        List<Twenty> list = Lists.newArrayList();
        List<String> list2 = null;
        if ("危害因素".equals(type)) {
            List<PostDangerOfEnterprise> lp = postDangerOfEnterpriseService.list(new QueryWrapper<PostDangerOfEnterprise>().eq("year", year));
            list2 = lp.stream().map(PostDangerOfEnterprise::getDangerBigName).collect(Collectors.toList());
        } else if ("行政区划".equals(type)) {
            List<Enterprise> le = enterpriseService.list(new QueryWrapper<Enterprise>().eq("year", year));
            list2 =le.stream().map(Enterprise::getProvinceName).collect(Collectors.toList());
        } else if ("登记注册类型".equals(type)) {
            List<Enterprise> le = enterpriseService.list(new QueryWrapper<Enterprise>().eq("year", year));
            list2 = le.stream().map(Enterprise::getRegisterBigName).collect(Collectors.toList());
        }
        for (String s : list2) {
            Twenty tmp = new Twenty();
            tmp.setName(s);
            //粉尘
            TestingsituationPoint testingsituationPoint = new TestingsituationPoint();
            //Detectionpoints
            QueryWrapper<JianceDetailOfService> queryWrapper = new QueryWrapper();
            queryWrapper.eq("dangerBigName", "粉尘").eq("checkYear", year);
            testingsituationPoint.setDetectionpoints(jianceDetailOfServiceService.count(queryWrapper));
            //Targetnumber
            QueryWrapper<JianceDetailOfService> queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("dangerBigName", "粉尘").eq("decideResult", "合格").eq("checkYear", year);
            testingsituationPoint.setTargetnumber(jianceDetailOfServiceService.count(queryWrapper2));
            //Compliancerate
            testingsituationPoint.setCompliancerate((float) testingsituationPoint.getTargetnumber() / testingsituationPoint.getDetectionpoints());
            tmp.setVar1(testingsituationPoint.getDetectionpoints());
            tmp.setVar2(testingsituationPoint.getTargetnumber());
            tmp.setVar3(testingsituationPoint.getCompliancerate()*100 + "%");

            //化学
            QueryWrapper<JianceDetailOfService> queryWrappeh1= new QueryWrapper();
            queryWrappeh1.eq("dangerBigName", "化学因素").eq("checkYear", year);
            testingsituationPoint.setDetectionpoints(jianceDetailOfServiceService.count(queryWrappeh1));
            //Targetnumber
            QueryWrapper<JianceDetailOfService> queryWrapperh2 = new QueryWrapper();
            queryWrapperh2.eq("dangerBigName", "化学因素").eq("decideResult", "合格").eq("checkYear", year);
            testingsituationPoint.setTargetnumber(jianceDetailOfServiceService.count(queryWrapperh2));
            //Compliancerate
            testingsituationPoint.setCompliancerate((float) testingsituationPoint.getTargetnumber() / testingsituationPoint.getDetectionpoints());
            tmp.setVar4(testingsituationPoint.getDetectionpoints());
            tmp.setVar5(testingsituationPoint.getTargetnumber());
            tmp.setVar6(testingsituationPoint.getCompliancerate()*100 + "%");

            //物理
            QueryWrapper<JianceDetailOfService> queryWrapperw = new QueryWrapper();
            queryWrapperw.eq("dangerBigName", "物理因素").eq("checkYear", year);
            testingsituationPoint.setDetectionpoints(jianceDetailOfServiceService.count(queryWrapperw));
            //Targetnumber
            QueryWrapper<JianceDetailOfService> queryWrapperw2 = new QueryWrapper();
            queryWrapperw2.eq("dangerBigName", "物理因素").eq("decideResult", "合格").eq("checkYear", year);
            testingsituationPoint.setTargetnumber(jianceDetailOfServiceService.count(queryWrapperw2));
            //Compliancerate
            testingsituationPoint.setCompliancerate((float) testingsituationPoint.getTargetnumber() / testingsituationPoint.getDetectionpoints());
            tmp.setVar7(testingsituationPoint.getDetectionpoints());
            tmp.setVar8(testingsituationPoint.getTargetnumber());
            tmp.setVar9(testingsituationPoint.getCompliancerate() + "%");

            //放射性
            QueryWrapper<JianceDetailOfService> queryWrappef = new QueryWrapper();
            queryWrappef.eq("dangerBigName", "放射性因素").eq("checkYear", year);
            testingsituationPoint.setDetectionpoints(jianceDetailOfServiceService.count(queryWrappef));
            //Targetnumber
            QueryWrapper<JianceDetailOfService> queryWrapperf2 = new QueryWrapper();
            queryWrapperf2.eq("dangerBigName", "放射性因素").eq("decideResult", "合格").eq("checkYear", year);
            testingsituationPoint.setTargetnumber(jianceDetailOfServiceService.count(queryWrapperf2));
            //Compliancerate
            testingsituationPoint.setCompliancerate((float) testingsituationPoint.getTargetnumber() / testingsituationPoint.getDetectionpoints());
            tmp.setVar10(testingsituationPoint.getDetectionpoints());
            tmp.setVar11(testingsituationPoint.getTargetnumber());
            tmp.setVar12(testingsituationPoint.getCompliancerate() + "%");

            //生物
            QueryWrapper<JianceDetailOfService> queryWrappers = new QueryWrapper();
            testingsituationPoint.setDetectionpoints(jianceDetailOfServiceService.count(queryWrappers));
            //Targetnumber
            QueryWrapper<JianceDetailOfService> queryWrappers2 = new QueryWrapper();
            queryWrappers2.eq("dangerBigName", "生物因素").eq("decideResult", "合格").eq("checkYear", year);
            testingsituationPoint.setTargetnumber(jianceDetailOfServiceService.count(queryWrappers2));
            //Compliancerat
            testingsituationPoint.setCompliancerate((float) testingsituationPoint.getTargetnumber() / testingsituationPoint.getDetectionpoints());
            tmp.setVar13(testingsituationPoint.getDetectionpoints());
            tmp.setVar14(testingsituationPoint.getTargetnumber());
            tmp.setVar15(testingsituationPoint.getCompliancerate()*100 + "%");

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
            Examinationresultsurface examinationresultsurface=new Examinationresultsurface();
            //职业健康检查企业数
            QueryWrapper<TijianDetail1OfService> queryWrapper = new QueryWrapper();
            queryWrapper.eq("checkYear", year);

            examinationresultsurface.setEnterpriseCode1(tijianDetail1OfServiceService.count(queryWrapper));

            //体检报告数
            QueryWrapper<TijianTotalOfService> queryWrapper1 = new QueryWrapper();
            QueryWrapper<TijianDetail1OfService> qw = new QueryWrapper();
            queryWrapper1.eq("year", year);


            examinationresultsurface.setCount1(tijianTotalOfServiceService.count(queryWrapper1));

            //职业禁忌证人数
            QueryWrapper<TijianDetail1OfService> queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("result","职业禁忌证").eq("checkYear", year);

            examinationresultsurface.setIdNum1(tijianDetail1OfServiceService.count(queryWrapper2));

            //疑似职业病人数
            QueryWrapper<TijianDetail2OfService> queryWrapper3 = new QueryWrapper();
            queryWrapper3.eq("checkYear", year);

            examinationresultsurface.setIdNum2(tijianDetail2OfServiceService.count(queryWrapper3));

            //检出疑似职业病企业数
            QueryWrapper<TijianDetail2OfService> queryWrapper4 = new QueryWrapper();
            queryWrapper4.eq("checkYear", year);

            examinationresultsurface.setEnterpriseCode2(tijianDetail2OfServiceService.count(queryWrapper4));

            double d1 = examinationresultsurface.getIdNum1()/examinationresultsurface.getCount1();
            list1.add(DoubleUtil.get(d1));
            //
            double d2 = examinationresultsurface.getIdNum2()/examinationresultsurface.getCount1();
            list2.add(DoubleUtil.get(d2));
            //
            double d3 = examinationresultsurface.getEnterpriseCode2()/examinationresultsurface.getEnterpriseCode1();
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
            List<PostDangerOfEnterprise> lp = postDangerOfEnterpriseService.list(new QueryWrapper<PostDangerOfEnterprise>().eq("year", year));
            list2 = lp.stream().map(PostDangerOfEnterprise::getDangerBigName).collect(Collectors.toList());
        } else if ("行政区划".equals(type)) {
            List<Enterprise> le = enterpriseService.list(new QueryWrapper<Enterprise>().eq("year", year));
            list2 =le.stream().map(Enterprise::getProvinceName).collect(Collectors.toList());
        } else if ("登记注册类型".equals(type)) {
            List<Enterprise> le = enterpriseService.list(new QueryWrapper<Enterprise>().eq("year", year));
            list2 = le.stream().map(Enterprise::getRegisterBigName).collect(Collectors.toList());
        }
        for (String s : list2) {
            Twenty tmp = new Twenty();
            tmp.setName(s);
            Examinationresultsurface examinationresultsurface=new Examinationresultsurface();
            //职业健康检查企业数
            QueryWrapper<TijianDetail1OfService> queryWrapper = new QueryWrapper();
            queryWrapper.eq("checkYear", year);
            examinationresultsurface.setEnterpriseCode1(tijianDetail1OfServiceService.count(queryWrapper));

            //体检报告数
            QueryWrapper<TijianTotalOfService> queryWrapper1 = new QueryWrapper();
            QueryWrapper<TijianDetail1OfService> qw = new QueryWrapper();
            queryWrapper1.eq("year", year);
            examinationresultsurface.setCount1(tijianTotalOfServiceService.count(queryWrapper1));

            //疑似职业病人数
            QueryWrapper<TijianDetail2OfService> queryWrapper3 = new QueryWrapper();
            queryWrapper3.eq("checkYear", year);
            examinationresultsurface.setIdNum2(tijianDetail2OfServiceService.count(queryWrapper3));

            //检出疑似职业病企业数
            QueryWrapper<TijianDetail2OfService> queryWrapper4 = new QueryWrapper();
            queryWrapper4.eq("checkYear", year);
            examinationresultsurface.setEnterpriseCode2(tijianDetail2OfServiceService.count(queryWrapper4));


            tmp.setVar1(examinationresultsurface.getEnterpriseCode1());
            tmp.setVar2(examinationresultsurface.getCount1());
            tmp.setVar3(examinationresultsurface.getIdNum1());
            tmp.setVar4(examinationresultsurface.getIdNum1()/examinationresultsurface.getCount1()*100 + "%");
            tmp.setVar5(examinationresultsurface.getIdNum2());
            tmp.setVar6(examinationresultsurface.getIdNum2()/examinationresultsurface.getCount1()*100 + "%");
            tmp.setVar7(examinationresultsurface.getEnterpriseCode2());
            tmp.setVar8(examinationresultsurface.getEnterpriseCode2()/examinationresultsurface.getEnterpriseCode1()*100+ "%");
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
            Diagnosticstatistics diagnosticstatistics=new Diagnosticstatistics();
            //职业病诊断人数
            QueryWrapper<ZhenduanTotalOfService> queryWrapper2 = new QueryWrapper();
            QueryWrapper<ZhenduanDetailOfService> qw1=new QueryWrapper<>();
            queryWrapper2.eq("year",year);
            diagnosticstatistics.setCount1(zhenduanTotalOfServiceService.count(queryWrapper2));
            //报告职业病人数
            QueryWrapper<ZhenduanDetailOfService> queryWrapper3 = new QueryWrapper();
            queryWrapper3.eq("checkyear",year);
            diagnosticstatistics.setIdNum(zhenduanDetailOfServiceService.count(queryWrapper3));
            double c=(double)diagnosticstatistics.getIdNum()/diagnosticstatistics.getCount1();
            list1.add(DoubleUtil.get(c));
        }
        map.put("list1", list1);
        return map;
    }

    @GetMapping("/option13Detail")
    public List<Twenty> option13Detail(String year, String type) {
        List<Twenty> list = Lists.newArrayList();
        List<String> list2 = null;
        if ("危害因素".equals(type)) {
            List<PostDangerOfEnterprise> lp = postDangerOfEnterpriseService.list(new QueryWrapper<PostDangerOfEnterprise>().eq("year", year));
            list2 = lp.stream().map(PostDangerOfEnterprise::getDangerBigName).collect(Collectors.toList());
        } else if ("行政区划".equals(type)) {
            List<Enterprise> le = enterpriseService.list(new QueryWrapper<Enterprise>().eq("year", year));
            list2 =le.stream().map(Enterprise::getProvinceName).collect(Collectors.toList());
        } else if ("登记注册类型".equals(type)) {
            List<Enterprise> le = enterpriseService.list(new QueryWrapper<Enterprise>().eq("year", year));
            list2 = le.stream().map(Enterprise::getRegisterBigName).collect(Collectors.toList());
        }
        for (String s : list2) {
            Twenty tmp = new Twenty();
            tmp.setName(s);
            Diagnosticstatistics diagnosticstatistics=new Diagnosticstatistics();

            //职业病诊断企业数
            QueryWrapper<ZhenduanTotalOfService> queryWrapper = new QueryWrapper();
            QueryWrapper<ZhenduanDetailOfService> qw=new QueryWrapper<>();
            queryWrapper.eq("year",year);
            diagnosticstatistics.setCount2(zhenduanTotalOfServiceService.count(queryWrapper));


            //诊断出职业病病人企业数
            QueryWrapper<ZhenduanDetailOfService> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("checkyear",year);
            diagnosticstatistics.setEnterpriseCode(zhenduanDetailOfServiceService.count(queryWrapper1));


            //职业病诊断人数
            QueryWrapper<ZhenduanTotalOfService> queryWrapper2 = new QueryWrapper();
            QueryWrapper<ZhenduanDetailOfService> qw1=new QueryWrapper<>();
            queryWrapper2.eq("year",year);
            diagnosticstatistics.setCount1(zhenduanTotalOfServiceService.count(queryWrapper2));


            //报告职业病人数
            QueryWrapper<ZhenduanDetailOfService> queryWrapper3 = new QueryWrapper();
            queryWrapper3.eq("checkyear",year);
            diagnosticstatistics.setIdNum(zhenduanDetailOfServiceService.count(queryWrapper3));
            double c=(double)diagnosticstatistics.getIdNum()/diagnosticstatistics.getCount1()*100;

            tmp.setVar1(diagnosticstatistics.getCount2());
            tmp.setVar2(diagnosticstatistics.getEnterpriseCode());
            tmp.setVar3(diagnosticstatistics.getCount1());
            tmp.setVar4(diagnosticstatistics.getIdNum());
            tmp.setVar5(c + "%");
            list.add(tmp);
        }
        return list;
    }

    //行政区划、登记注册类型
    @GetMapping("/option21")
    public Map<String, List<Double>> option21(String year, String type) {
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);
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
            list2 = DataVisualCacheUtil.getDangerList();
        } else if ("行政区划".equals(type)) {
            list2 = DataVisualCacheUtil.getAreaStrChildren("国家",null,null);
        } else if ("登记注册类型".equals(type)) {
            list2 = DataVisualCacheUtil.getRegisterTypeStrList();
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
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);
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
            list2 = DataVisualCacheUtil.getDangerList();
        } else if ("行政区划".equals(type)) {
            list2 = DataVisualCacheUtil.getAreaStrChildren("国家",null,null);
        } else if ("登记注册类型".equals(type)) {
            list2 = DataVisualCacheUtil.getRegisterTypeStrList();
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
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);
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
            list2 = DataVisualCacheUtil.getDangerList();
        } else if ("行政区划".equals(type)) {
            list2 = DataVisualCacheUtil.getAreaStrChildren("国家",null,null);
        } else if ("登记注册类型".equals(type)) {
            list2 = DataVisualCacheUtil.getRegisterTypeStrList();
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
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);
        List<BaseOfDic> registerList = DataVisualCacheUtil.getRegisterTypeList();

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
                govEight.setVar6((double) RandomUtil.randomInt(1, 10000));
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
                govEight.setVar6((double) RandomUtil.randomInt(1, 10000));
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
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);
        List<BaseOfDic> registerList = DataVisualCacheUtil.getRegisterTypeList();

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
        List<AreaOfDic> areaList = DataVisualCacheUtil.getAreaChildren("国家",null,null);
        List<BaseOfDic> registerList = DataVisualCacheUtil.getRegisterTypeList();

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
