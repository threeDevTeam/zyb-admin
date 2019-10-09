package com.hthyaq.zybadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.AccidentOfSupervise;
import com.hthyaq.zybadmin.model.entity.AccidentStatistics;
import com.hthyaq.zybadmin.model.entity.JianceDetailOfService;
import com.hthyaq.zybadmin.model.entity.TestingsituationPoint;
import com.hthyaq.zybadmin.service.AccidentOfSuperviseService;
import com.hthyaq.zybadmin.service.JianceDetailOfServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//作业场所职业病危害因素检测情况统计分析表
@RestController
@RequestMapping("/testingsituationPoint")
public class TestingsituationPointController {
    @Autowired
    JianceDetailOfServiceService jianceDetailOfServiceService;

    //粉尘
    @GetMapping("/list1")
    public TestingsituationPoint list1(Integer year, String provinceCode, String cityCode, String districtCode, String dangerBigName, String dangerSmallName, String registerBigName, String registerSmallName, String industryBigName) {
        if (year != null) {
            TestingsituationPoint testingsituationPoint = new TestingsituationPoint();
            //Detectionpoints
            QueryWrapper<JianceDetailOfService> queryWrapper = new QueryWrapper();
            queryWrapper.eq("dangerBigName", "粉尘").eq("checkYear", year);
            if (provinceCode != null) {
                queryWrapper.eq("provinceCode", provinceCode);
            }
            if (cityCode != null) {
                queryWrapper.eq("cityCode", cityCode);
            }
            if (districtCode != null) {
                queryWrapper.eq("districtCode", districtCode);
            }
            if (dangerSmallName != null) {
                queryWrapper.eq("dangerSmallName", dangerSmallName);
            }
            if (registerBigName != null) {
                queryWrapper.eq("registerBigName", registerBigName);
            }
            if (registerSmallName != null) {
                queryWrapper.eq("registerSmallName", registerSmallName);
            }
            if (industryBigName != null) {
                queryWrapper.eq("industryBigName", industryBigName);
            }
            testingsituationPoint.setDetectionpoints(jianceDetailOfServiceService.count(queryWrapper));


            //Targetnumber
            QueryWrapper<JianceDetailOfService> queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("dangerBigName", "粉尘").eq("decideResult", "合格").eq("checkYear", year);
            if (provinceCode != null) {
                queryWrapper2.eq("provinceCode", provinceCode);
            }
            if (cityCode != null) {
                queryWrapper2.eq("cityCode", cityCode);
            }
            if (districtCode != null) {
                queryWrapper2.eq("districtCode", districtCode);
            }
            if (dangerSmallName != null) {
                queryWrapper2.eq("dangerSmallName", dangerSmallName);
            }
            if (registerBigName != null) {
                queryWrapper2.eq("registerBigName", registerBigName);
            }
            if (registerSmallName != null) {
                queryWrapper2.eq("registerSmallName", registerSmallName);
            }
            if (industryBigName != null) {
                queryWrapper2.eq("industryBigName", industryBigName);
            }
            testingsituationPoint.setTargetnumber(jianceDetailOfServiceService.count(queryWrapper2));


            //Compliancerate
            testingsituationPoint.setCompliancerate((float) testingsituationPoint.getTargetnumber() / testingsituationPoint.getDetectionpoints());


            return testingsituationPoint;
        }
        return null;
    }

    //化学因素
    @GetMapping("/list2")
    public TestingsituationPoint list2(Integer year, String provinceCode, String cityCode, String districtCode, String dangerBigName, String dangerSmallName, String registerBigName, String registerSmallName, String industryBigName) {
        if (year != null) {
            TestingsituationPoint testingsituationPoint = new TestingsituationPoint();
            //Detectionpoints
            QueryWrapper<JianceDetailOfService> queryWrapper = new QueryWrapper();
            queryWrapper.eq("dangerBigName", "化学因素").eq("checkYear", year);
            if (provinceCode != null) {
                queryWrapper.eq("provinceCode", provinceCode);
            }
            if (cityCode != null) {
                queryWrapper.eq("cityCode", cityCode);
            }
            if (districtCode != null) {
                queryWrapper.eq("districtCode", districtCode);
            }
            if (dangerSmallName != null) {
                queryWrapper.eq("dangerSmallName", dangerSmallName);
            }
            if (registerBigName != null) {
                queryWrapper.eq("registerBigName", registerBigName);
            }
            if (registerSmallName != null) {
                queryWrapper.eq("registerSmallName", registerSmallName);
            }
            if (industryBigName != null) {
                queryWrapper.eq("industryBigName", industryBigName);
            }
            testingsituationPoint.setDetectionpoints(jianceDetailOfServiceService.count(queryWrapper));


            //Targetnumber
            QueryWrapper<JianceDetailOfService> queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("dangerBigName", "化学因素").eq("decideResult", "合格").eq("checkYear", year);
            if (provinceCode != null) {
                queryWrapper2.eq("provinceCode", provinceCode);
            }
            if (cityCode != null) {
                queryWrapper2.eq("cityCode", cityCode);
            }
            if (districtCode != null) {
                queryWrapper2.eq("districtCode", districtCode);
            }
            if (dangerBigName != null) {
                queryWrapper2.eq("dangerBigName", dangerBigName);
            }
            if (dangerSmallName != null) {
                queryWrapper2.eq("dangerSmallName", dangerSmallName);
            }
            if (registerBigName != null) {
                queryWrapper2.eq("registerBigName", registerBigName);
            }
            if (registerSmallName != null) {
                queryWrapper2.eq("registerSmallName", registerSmallName);
            }
            if (industryBigName != null) {
                queryWrapper2.eq("industryBigName", industryBigName);
            }
            testingsituationPoint.setTargetnumber(jianceDetailOfServiceService.count(queryWrapper2));


            //Compliancerate
            testingsituationPoint.setCompliancerate((float) testingsituationPoint.getTargetnumber() / testingsituationPoint.getDetectionpoints());


            return testingsituationPoint;
        }
        return null;
    }

    //物理因素
    @GetMapping("/list3")
    public TestingsituationPoint list3(Integer year, String provinceCode, String cityCode, String districtCode, String dangerBigName, String dangerSmallName, String registerBigName, String registerSmallName, String industryBigName) {
        if (year != null) {
            TestingsituationPoint testingsituationPoint = new TestingsituationPoint();
            //Detectionpoints
            QueryWrapper<JianceDetailOfService> queryWrapper = new QueryWrapper();
            queryWrapper.eq("dangerBigName", "物理因素").eq("checkYear", year);
            if (provinceCode != null) {
                queryWrapper.eq("provinceCode", provinceCode);
            }
            if (cityCode != null) {
                queryWrapper.eq("cityCode", cityCode);
            }
            if (districtCode != null) {
                queryWrapper.eq("districtCode", districtCode);
            }
            if (dangerSmallName != null) {
                queryWrapper.eq("dangerSmallName", dangerSmallName);
            }
            if (registerBigName != null) {
                queryWrapper.eq("registerBigName", registerBigName);
            }
            if (registerSmallName != null) {
                queryWrapper.eq("registerSmallName", registerSmallName);
            }
            if (industryBigName != null) {
                queryWrapper.eq("industryBigName", industryBigName);
            }
            testingsituationPoint.setDetectionpoints(jianceDetailOfServiceService.count(queryWrapper));


            //Targetnumber
            QueryWrapper<JianceDetailOfService> queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("dangerBigName", "物理因素").eq("decideResult", "合格").eq("checkYear", year);
            if (provinceCode != null) {
                queryWrapper2.eq("provinceCode", provinceCode);
            }
            if (cityCode != null) {
                queryWrapper2.eq("cityCode", cityCode);
            }
            if (districtCode != null) {
                queryWrapper2.eq("districtCode", districtCode);
            }
            if (dangerBigName != null) {
                queryWrapper2.eq("dangerBigName", dangerBigName);
            }
            if (dangerSmallName != null) {
                queryWrapper2.eq("dangerSmallName", dangerSmallName);
            }
            if (registerBigName != null) {
                queryWrapper2.eq("registerBigName", registerBigName);
            }
            if (registerSmallName != null) {
                queryWrapper2.eq("registerSmallName", registerSmallName);
            }
            if (industryBigName != null) {
                queryWrapper2.eq("industryBigName", industryBigName);
            }
            testingsituationPoint.setTargetnumber(jianceDetailOfServiceService.count(queryWrapper2));


            //Compliancerate
            testingsituationPoint.setCompliancerate((float) testingsituationPoint.getTargetnumber() / testingsituationPoint.getDetectionpoints());


            return testingsituationPoint;
        }
        return null;
    }

    //放射性因素
    @GetMapping("/list4")
    public TestingsituationPoint list4(Integer year, String provinceCode, String cityCode, String districtCode, String dangerBigName, String dangerSmallName, String registerBigName, String registerSmallName, String industryBigName) {
        if (year != null) {
            TestingsituationPoint testingsituationPoint = new TestingsituationPoint();
            //Detectionpoints
            QueryWrapper<JianceDetailOfService> queryWrapper = new QueryWrapper();
            queryWrapper.eq("dangerBigName", "放射性因素").eq("checkYear", year);
            if (provinceCode != null) {
                queryWrapper.eq("provinceCode", provinceCode);
            }
            if (cityCode != null) {
                queryWrapper.eq("cityCode", cityCode);
            }
            if (districtCode != null) {
                queryWrapper.eq("districtCode", districtCode);
            }
            if (dangerSmallName != null) {
                queryWrapper.eq("dangerSmallName", dangerSmallName);
            }
            if (registerBigName != null) {
                queryWrapper.eq("registerBigName", registerBigName);
            }
            if (registerSmallName != null) {
                queryWrapper.eq("registerSmallName", registerSmallName);
            }
            if (industryBigName != null) {
                queryWrapper.eq("industryBigName", industryBigName);
            }
            testingsituationPoint.setDetectionpoints(jianceDetailOfServiceService.count(queryWrapper));


            //Targetnumber
            QueryWrapper<JianceDetailOfService> queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("dangerBigName", "放射性因素").eq("decideResult", "合格").eq("checkYear", year);
            if (provinceCode != null) {
                queryWrapper2.eq("provinceCode", provinceCode);
            }
            if (cityCode != null) {
                queryWrapper2.eq("cityCode", cityCode);
            }
            if (districtCode != null) {
                queryWrapper2.eq("districtCode", districtCode);
            }
            if (dangerBigName != null) {
                queryWrapper2.eq("dangerBigName", dangerBigName);
            }
            if (dangerSmallName != null) {
                queryWrapper2.eq("dangerSmallName", dangerSmallName);
            }
            if (registerBigName != null) {
                queryWrapper2.eq("registerBigName", registerBigName);
            }
            if (registerSmallName != null) {
                queryWrapper2.eq("registerSmallName", registerSmallName);
            }
            if (industryBigName != null) {
                queryWrapper2.eq("industryBigName", industryBigName);
            }
            testingsituationPoint.setTargetnumber(jianceDetailOfServiceService.count(queryWrapper2));


            //Compliancerate
            testingsituationPoint.setCompliancerate((float) testingsituationPoint.getTargetnumber() / testingsituationPoint.getDetectionpoints());


            return testingsituationPoint;
        }
        return null;
    }


    //生物因素
    @GetMapping("/list5")
    public TestingsituationPoint list5(Integer year, String provinceCode, String cityCode, String districtCode, String dangerBigName, String dangerSmallName, String registerBigName, String registerSmallName, String industryBigName) {
        if (year != null) {
            TestingsituationPoint testingsituationPoint = new TestingsituationPoint();
            //Detectionpoints
            QueryWrapper<JianceDetailOfService> queryWrapper = new QueryWrapper();
            queryWrapper.eq("dangerBigName", "生物因素").eq("checkYear", year);
            if (provinceCode != null) {
                queryWrapper.eq("provinceCode", provinceCode);
            }
            if (cityCode != null) {
                queryWrapper.eq("cityCode", cityCode);
            }
            if (districtCode != null) {
                queryWrapper.eq("districtCode", districtCode);
            }
            if (dangerSmallName != null) {
                queryWrapper.eq("dangerSmallName", dangerSmallName);
            }
            if (registerBigName != null) {
                queryWrapper.eq("registerBigName", registerBigName);
            }
            if (registerSmallName != null) {
                queryWrapper.eq("registerSmallName", registerSmallName);
            }
            if (industryBigName != null) {
                queryWrapper.eq("industryBigName", industryBigName);
            }
            testingsituationPoint.setDetectionpoints(jianceDetailOfServiceService.count(queryWrapper));


            //Targetnumber
            QueryWrapper<JianceDetailOfService> queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("dangerBigName", "生物因素").eq("decideResult", "合格").eq("checkYear", year);
            if (provinceCode != null) {
                queryWrapper2.eq("provinceCode", provinceCode);
            }
            if (cityCode != null) {
                queryWrapper2.eq("cityCode", cityCode);
            }
            if (districtCode != null) {
                queryWrapper2.eq("districtCode", districtCode);
            }
            if (dangerBigName != null) {
                queryWrapper2.eq("dangerBigName", dangerBigName);
            }
            if (dangerSmallName != null) {
                queryWrapper2.eq("dangerSmallName", dangerSmallName);
            }
            if (registerBigName != null) {
                queryWrapper2.eq("registerBigName", registerBigName);
            }
            if (registerSmallName != null) {
                queryWrapper2.eq("registerSmallName", registerSmallName);
            }
            if (industryBigName != null) {
                queryWrapper2.eq("industryBigName", industryBigName);
            }
            testingsituationPoint.setTargetnumber(jianceDetailOfServiceService.count(queryWrapper2));


            //Compliancerate
            testingsituationPoint.setCompliancerate((float) testingsituationPoint.getTargetnumber() / testingsituationPoint.getDetectionpoints());


            return testingsituationPoint;
        }
        return null;
    }


    //其他因素
    @GetMapping("/list6")
    public TestingsituationPoint list6(Integer year, String provinceCode, String cityCode, String districtCode, String dangerBigName, String dangerSmallName, String registerBigName, String registerSmallName, String industryBigName) {
        if (year != null) {
            TestingsituationPoint testingsituationPoint = new TestingsituationPoint();
            //Detectionpoints
            QueryWrapper<JianceDetailOfService> queryWrapper = new QueryWrapper();
            queryWrapper.eq("dangerBigName", "其他因素").eq("checkYear", year);
            if (provinceCode != null) {
                queryWrapper.eq("provinceCode", provinceCode);
            }
            if (cityCode != null) {
                queryWrapper.eq("cityCode", cityCode);
            }
            if (districtCode != null) {
                queryWrapper.eq("districtCode", districtCode);
            }
            if (dangerSmallName != null) {
                queryWrapper.eq("dangerSmallName", dangerSmallName);
            }
            if (registerBigName != null) {
                queryWrapper.eq("registerBigName", registerBigName);
            }
            if (registerSmallName != null) {
                queryWrapper.eq("registerSmallName", registerSmallName);
            }
            if (industryBigName != null) {
                queryWrapper.eq("industryBigName", industryBigName);
            }
            testingsituationPoint.setDetectionpoints(jianceDetailOfServiceService.count(queryWrapper));


            //Targetnumber
            QueryWrapper<JianceDetailOfService> queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("dangerBigName", "其他因素").eq("decideResult", "合格").eq("checkYear", year);
            if (provinceCode != null) {
                queryWrapper2.eq("provinceCode", provinceCode);
            }
            if (cityCode != null) {
                queryWrapper2.eq("cityCode", cityCode);
            }
            if (districtCode != null) {
                queryWrapper2.eq("districtCode", districtCode);
            }
            if (dangerBigName != null) {
                queryWrapper2.eq("dangerBigName", dangerBigName);
            }
            if (dangerSmallName != null) {
                queryWrapper2.eq("dangerSmallName", dangerSmallName);
            }
            if (registerBigName != null) {
                queryWrapper2.eq("registerBigName", registerBigName);
            }
            if (registerSmallName != null) {
                queryWrapper2.eq("registerSmallName", registerSmallName);
            }
            if (industryBigName != null) {
                queryWrapper2.eq("industryBigName", industryBigName);
            }
            testingsituationPoint.setTargetnumber(jianceDetailOfServiceService.count(queryWrapper2));


            //Compliancerate
            testingsituationPoint.setCompliancerate((float) testingsituationPoint.getTargetnumber() / testingsituationPoint.getDetectionpoints());


            return testingsituationPoint;
        }
        return null;
    }
}
