package com.hthyaq.zybadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthInspectionInstitutions")
public class HealthInspectionInstitutionsController {
    @Autowired
    TijianBasicOfServiceService tijianBasicOfServiceService;
    @Autowired
    JianceBasicOfServiceService jianceBasicOfServiceService;
    @Autowired
    JianceTotalOfServiceService jianceTotalOfServiceService;
    @Autowired
    TijianDetail1OfServiceService tijianDetail1OfServiceService;
    @Autowired
    TijianTotalOfServiceService tijianTotalOfServiceService;
    @GetMapping("/list")
    public HealthInspectionInstitutions list(Integer year, String provinceCode, String cityCode, String districtCode, String registerBigName, String registerSmallName, String hospitalLevel) {
        if (year != null) {
            HealthInspectionInstitutions healthInspectionInstitutions=new HealthInspectionInstitutions();
           //体检机构数
            QueryWrapper<TijianBasicOfService> queryWrapper = new QueryWrapper();
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
            if(hospitalLevel!=null){
                queryWrapper.eq("hospitalLevel", hospitalLevel);
            }
            healthInspectionInstitutions.setCount0(tijianBasicOfServiceService.count(queryWrapper));

            //	医护人员数量
            QueryWrapper<TijianBasicOfService> queryWrapper1 = new QueryWrapper();
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
            if(hospitalLevel!=null){
                queryWrapper1.eq("hospitalLevel", hospitalLevel);
            }
            healthInspectionInstitutions.setCount1(tijianBasicOfServiceService.count(queryWrapper1));

            //取证人员数量
            QueryWrapper<TijianBasicOfService> queryWrapper2 = new QueryWrapper();
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
            if(hospitalLevel!=null){
                queryWrapper2.eq("hospitalLevel", hospitalLevel);
            }
            healthInspectionInstitutions.setCount2(tijianBasicOfServiceService.count(queryWrapper2));

            //检查项目数量
            QueryWrapper<TijianBasicOfService> queryWrapper3 = new QueryWrapper();
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
            if(hospitalLevel!=null){
                queryWrapper3.eq("hospitalLevel", hospitalLevel);
            }
            healthInspectionInstitutions.setProjectCount1((float)tijianBasicOfServiceService.count(queryWrapper3)/healthInspectionInstitutions.getCount0());

//            //计量认证项目数
//            QueryWrapper<JianceBasicOfService> queryWrapper4 = new QueryWrapper();
//            queryWrapper4.eq("year", year);
//            healthInspectionInstitutions.setProjectCount2(jianceBasicOfServiceService.count(queryWrapper4));
//
//
//            //报告完成数
//            QueryWrapper<JianceTotalOfService> queryWrapper5 = new QueryWrapper();
//            queryWrapper5.eq("year", year);
//            healthInspectionInstitutions.setCount123(jianceTotalOfServiceService.count(queryWrapper5));


            //检查报告数
            QueryWrapper<TijianDetail1OfService> queryWrapper6 = new QueryWrapper();
            queryWrapper6.eq("checkYear", year);
            QueryWrapper<TijianBasicOfService> qw=new QueryWrapper<>();
            if(provinceCode!=null){
                qw.eq("provinceCode", provinceCode);
                TijianBasicOfService tijianBasicOfService=tijianBasicOfServiceService.getOne(qw);
                queryWrapper6.eq("tijianBasicId",tijianBasicOfService.getId());
            }
            if(cityCode!=null){
                qw.eq("cityCode", cityCode);
                TijianBasicOfService tijianBasicOfService=tijianBasicOfServiceService.getOne(qw);
                queryWrapper6.eq("tijianBasicId",tijianBasicOfService.getId());

            }
            if(districtCode!=null){
                qw.eq("districtCode", districtCode);
                TijianBasicOfService tijianBasicOfService=tijianBasicOfServiceService.getOne(qw);
                queryWrapper6.eq("tijianBasicId",tijianBasicOfService.getId());

            }
            if(registerBigName!=null){
                qw.eq("registerBigName", registerBigName);
                TijianBasicOfService tijianBasicOfService=tijianBasicOfServiceService.getOne(qw);
                queryWrapper6.eq("tijianBasicId",tijianBasicOfService.getId());

            }
            if(registerSmallName!=null){
                qw.eq("registerSmallName", registerSmallName);
                TijianBasicOfService tijianBasicOfService=tijianBasicOfServiceService.getOne(qw);
                queryWrapper6.eq("tijianBasicId",tijianBasicOfService.getId());

            }
            if(hospitalLevel!=null){
                qw.eq("hospitalLevel", hospitalLevel);
                TijianBasicOfService tijianBasicOfService=tijianBasicOfServiceService.getOne(qw);
                queryWrapper6.eq("tijianBasicId",tijianBasicOfService.getId());

            }
            healthInspectionInstitutions.setIdNum(tijianDetail1OfServiceService.count(queryWrapper6));


            //体检人数
            QueryWrapper<TijianTotalOfService> queryWrapper7= new QueryWrapper();
            queryWrapper7.eq("year", year);
            QueryWrapper<TijianBasicOfService> qw1=new QueryWrapper<>();
            if(provinceCode!=null){
                qw1.eq("provinceCode", provinceCode);
                TijianBasicOfService tijianBasicOfService=tijianBasicOfServiceService.getOne(qw1);
                queryWrapper7.eq("tijianBasicId",tijianBasicOfService.getId());
            }
            if(cityCode!=null){
                qw1.eq("cityCode", cityCode);
                TijianBasicOfService tijianBasicOfService=tijianBasicOfServiceService.getOne(qw1);
                queryWrapper7.eq("tijianBasicId",tijianBasicOfService.getId());

            }
            if(districtCode!=null){
                qw1.eq("districtCode", districtCode);
                TijianBasicOfService tijianBasicOfService=tijianBasicOfServiceService.getOne(qw1);
                queryWrapper7.eq("tijianBasicId",tijianBasicOfService.getId());

            }
            if(registerBigName!=null){
                qw1.eq("registerBigName", registerBigName);
                TijianBasicOfService tijianBasicOfService=tijianBasicOfServiceService.getOne(qw1);
                queryWrapper7.eq("tijianBasicId",tijianBasicOfService.getId());

            }
            if(registerSmallName!=null){
                qw1.eq("registerSmallName", registerSmallName);
                TijianBasicOfService tijianBasicOfService=tijianBasicOfServiceService.getOne(qw1);
                queryWrapper7.eq("tijianBasicId",tijianBasicOfService.getId());

            }
            if(hospitalLevel!=null){
                qw1.eq("hospitalLevel", hospitalLevel);
                TijianBasicOfService tijianBasicOfService=tijianBasicOfServiceService.getOne(qw1);
                queryWrapper7.eq("tijianBasicId",tijianBasicOfService.getId());

            }
            healthInspectionInstitutions.setCount3(tijianTotalOfServiceService.count(queryWrapper7));

            return healthInspectionInstitutions;
        }
        return null;
    }
}
