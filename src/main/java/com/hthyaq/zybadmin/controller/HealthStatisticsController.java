package com.hthyaq.zybadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.JianceBasicOfServiceService;
import com.hthyaq.zybadmin.service.JianceDetailOfServiceService;
import com.hthyaq.zybadmin.service.JianceTotalOfServiceService;
import com.hthyaq.zybadmin.service.TijianTotalOfServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthStatistics")
public class HealthStatisticsController {
    @Autowired
    JianceBasicOfServiceService jianceBasicOfServiceService;
    @Autowired
    JianceTotalOfServiceService jianceTotalOfServiceService;
    @Autowired
    JianceDetailOfServiceService jianceDetailOfServiceService;

    @GetMapping("/list")
    public HealthStatistics list(Integer year, String provinceCode, String cityCode, String districtCode, String registerBigName, String registerSmallName,String level) {
        if (year != null) {
            HealthStatistics healthStatistics = new HealthStatistics();
            //code
            QueryWrapper<JianceBasicOfService> queryWrapper = new QueryWrapper();
            queryWrapper.eq("year", year);
            if(provinceCode!=null){
                queryWrapper.eq("provinceCode", provinceCode);
            }
            if(cityCode!=null){
                queryWrapper.eq("cityCode", cityCode);
            }
            if(districtCode!=null){
                queryWrapper.eq("districtCode", districtCode);
            }
            if(registerBigName!=null){
                queryWrapper.eq("registerBigName", registerBigName);
            }
            if(registerSmallName!=null){
                queryWrapper.eq("registerSmallName", registerSmallName);
            }
            if(level!=null){
                queryWrapper.eq("level", level);
            }
            healthStatistics.setCode(jianceBasicOfServiceService.count(queryWrapper));


            //technologyCount
            QueryWrapper<JianceBasicOfService> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("year", year);
            if(provinceCode!=null){
                queryWrapper1.eq("provinceCode", provinceCode);
            }
            if(cityCode!=null){
                queryWrapper1.eq("cityCode", cityCode);
            }
            if(districtCode!=null){
                queryWrapper1.eq("districtCode", districtCode);
            }
            if(registerBigName!=null){
                queryWrapper1.eq("registerBigName", registerBigName);
            }
            if(registerSmallName!=null){
                queryWrapper1.eq("registerSmallName", registerSmallName);
            }
            if(level!=null){
                queryWrapper1.eq("level", level);
            }
            healthStatistics.setTechnologyCount(jianceBasicOfServiceService.count(queryWrapper1));

            //passCount
            QueryWrapper<JianceBasicOfService> queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("year", year);
            if(provinceCode!=null){
                queryWrapper2.eq("provinceCode", provinceCode);
            }
            if(cityCode!=null){
                queryWrapper2.eq("cityCode", cityCode);
            }
            if(districtCode!=null){
                queryWrapper2.eq("districtCode", districtCode);
            }
            if(registerBigName!=null){
                queryWrapper2.eq("registerBigName", registerBigName);
            }
            if(registerSmallName!=null){
                queryWrapper2.eq("registerSmallName", registerSmallName);
            }
            if(level!=null){
                queryWrapper2.eq("level", level);
            }
            healthStatistics.setPassCount(jianceBasicOfServiceService.count(queryWrapper2));


            //equipmentCount
            QueryWrapper<JianceBasicOfService> queryWrapper3= new QueryWrapper();
            queryWrapper3.eq("year", year);
            if(provinceCode!=null){
                queryWrapper3.eq("provinceCode", provinceCode);
            }
            if(cityCode!=null){
                queryWrapper3.eq("cityCode", cityCode);
            }
            if(districtCode!=null){
                queryWrapper3.eq("districtCode", districtCode);
            }
            if(registerBigName!=null){
                queryWrapper3.eq("registerBigName", registerBigName);
            }
            if(registerSmallName!=null){
                queryWrapper3.eq("registerSmallName", registerSmallName);
            }
            if(level!=null){
                queryWrapper3.eq("level", level);
            }
            healthStatistics.setEquipmentCount(jianceBasicOfServiceService.count(queryWrapper3));


            //projectCount
            QueryWrapper<JianceBasicOfService> queryWrapper4= new QueryWrapper();
            queryWrapper4.eq("year", year);
            if(provinceCode!=null){
                queryWrapper4.eq("provinceCode", provinceCode);
            }
            if(cityCode!=null){
                queryWrapper4.eq("cityCode", cityCode);
            }
            if(districtCode!=null){
                queryWrapper4.eq("districtCode", districtCode);
            }
            if(registerBigName!=null){
                queryWrapper4.eq("registerBigName", registerBigName);
            }
            if(registerSmallName!=null){
                queryWrapper4.eq("registerSmallName", registerSmallName);
            }
            if(level!=null){
                queryWrapper4.eq("level", level);
            }
            healthStatistics.setProjectCount((float)jianceBasicOfServiceService.count(queryWrapper4)/healthStatistics.getCode());

            //count123
            QueryWrapper<JianceTotalOfService> queryWrapper5= new QueryWrapper();
            queryWrapper5.eq("year", year);
            if(provinceCode!=null){
                queryWrapper.eq("provinceCode", provinceCode);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper5.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            if(cityCode!=null){
                queryWrapper.eq("cityCode", cityCode);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper5.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            if(districtCode!=null){
                queryWrapper.eq("districtCode", districtCode);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper5.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            if(registerBigName!=null){
                queryWrapper.eq("registerBigName", registerBigName);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper5.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            if(registerSmallName!=null){
                queryWrapper.eq("registerSmallName", registerSmallName);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper5.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            if(level!=null){
                queryWrapper.eq("level", level);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper5.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            healthStatistics.setCount123(jianceTotalOfServiceService.count(queryWrapper5));

            //count4
            QueryWrapper<JianceTotalOfService> queryWrapper6= new QueryWrapper();
            queryWrapper6.eq("year", year);
            if(provinceCode!=null){
                queryWrapper.eq("provinceCode", provinceCode);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper6.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            if(cityCode!=null){
                queryWrapper.eq("cityCode", cityCode);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper6.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            if(districtCode!=null){
                queryWrapper.eq("districtCode", districtCode);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper6.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            if(registerBigName!=null){
                queryWrapper.eq("registerBigName", registerBigName);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper6.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            if(registerSmallName!=null){
                queryWrapper.eq("registerSmallName", registerSmallName);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper6.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            if(level!=null){
                queryWrapper.eq("level", level);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper6.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            healthStatistics.setCount4(jianceTotalOfServiceService.count(queryWrapper6));


            //count5
            QueryWrapper<JianceDetailOfService> queryWrapper7= new QueryWrapper();
            queryWrapper7.eq("checkYear", year);
            if(provinceCode!=null){
                queryWrapper.eq("provinceCode", provinceCode);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper7.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            if(cityCode!=null){
                queryWrapper.eq("cityCode", cityCode);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper7.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            if(districtCode!=null){
                queryWrapper.eq("districtCode", districtCode);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper7.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            if(registerBigName!=null){
                queryWrapper.eq("registerBigName", registerBigName);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper7.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            if(registerSmallName!=null){
                queryWrapper.eq("registerSmallName", registerSmallName);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper7.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            if(level!=null){
                queryWrapper.eq("level", level);
                JianceBasicOfService jianceBasicOfService=jianceBasicOfServiceService.getOne(queryWrapper);
                queryWrapper7.eq("jianceBasicId",jianceBasicOfService.getId());
            }
            healthStatistics.setCount5(jianceDetailOfServiceService.count(queryWrapper7));

            return healthStatistics;
        }
        return null;
    }
}
