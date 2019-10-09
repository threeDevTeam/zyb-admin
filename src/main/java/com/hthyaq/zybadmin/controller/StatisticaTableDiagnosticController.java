package com.hthyaq.zybadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.TijianTotalOfServiceService;
import com.hthyaq.zybadmin.service.ZhenduanBasicOfServiceService;
import com.hthyaq.zybadmin.service.ZhenduanDetailOfServiceService;
import com.hthyaq.zybadmin.service.ZhenduanTotalOfServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statisticaTableDiagnostic")
public class StatisticaTableDiagnosticController {
    @Autowired
    ZhenduanBasicOfServiceService zhenduanBasicOfServiceService;
    @Autowired
    ZhenduanTotalOfServiceService zhenduanTotalOfServiceService;
    @Autowired
    ZhenduanDetailOfServiceService zhenduanDetailOfServiceService;
    @GetMapping("/list")
    public StatisticaTableDiagnostic list(Integer year, String provinceCode, String cityCode, String districtCode, String registerBigName, String registerSmallName, String hospitalLevel) {
        if (year != null) {
            StatisticaTableDiagnostic statisticaTableDiagnostic=new StatisticaTableDiagnostic();
            //诊断机构数
            QueryWrapper<ZhenduanBasicOfService> queryWrapper = new QueryWrapper();
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
            statisticaTableDiagnostic.setCount(zhenduanBasicOfServiceService.count(queryWrapper));

            //诊断医师数量
            QueryWrapper<ZhenduanBasicOfService> queryWrapper1 = new QueryWrapper();
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
            statisticaTableDiagnostic.setDoctorNum(zhenduanBasicOfServiceService.count(queryWrapper1));


            //诊断项目数量
            QueryWrapper<ZhenduanBasicOfService> queryWrapper2 = new QueryWrapper();
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
            statisticaTableDiagnostic.setProjectCount((float)zhenduanBasicOfServiceService.count(queryWrapper2)/statisticaTableDiagnostic.getCount());

            //诊断人数
            QueryWrapper<ZhenduanTotalOfService> queryWrapper3 = new QueryWrapper();
            queryWrapper3.eq("year", year);
            QueryWrapper<ZhenduanBasicOfService> qw=new QueryWrapper<>();
            if(provinceCode!=null){
                qw.eq("provinceCode", provinceCode);
                ZhenduanBasicOfService zhenduanBasicOfService=zhenduanBasicOfServiceService.getOne(qw);
                queryWrapper3.eq("zhenduanBasicId",zhenduanBasicOfService.getId());
            }
            if(cityCode!=null){
                qw.eq("cityCode", cityCode);
                ZhenduanBasicOfService zhenduanBasicOfService=zhenduanBasicOfServiceService.getOne(qw);
                queryWrapper3.eq("zhenduanBasicId",zhenduanBasicOfService.getId());

            }
            if(districtCode!=null){
                qw.eq("districtCode", districtCode);
                ZhenduanBasicOfService zhenduanBasicOfService=zhenduanBasicOfServiceService.getOne(qw);
                queryWrapper3.eq("zhenduanBasicId",zhenduanBasicOfService.getId());

            }
            if(registerBigName!=null){
                qw.eq("registerBigName", registerBigName);
                ZhenduanBasicOfService zhenduanBasicOfService=zhenduanBasicOfServiceService.getOne(qw);
                queryWrapper3.eq("zhenduanBasicId",zhenduanBasicOfService.getId());

            }
            if(registerSmallName!=null){
                qw.eq("registerSmallName", registerSmallName);
                ZhenduanBasicOfService zhenduanBasicOfService=zhenduanBasicOfServiceService.getOne(qw);
                queryWrapper3.eq("zhenduanBasicId",zhenduanBasicOfService.getId());

            }
            if(hospitalLevel!=null){
                qw.eq("hospitalLevel", hospitalLevel);
                ZhenduanBasicOfService zhenduanBasicOfService=zhenduanBasicOfServiceService.getOne(qw);
                queryWrapper3.eq("zhenduanBasicId",zhenduanBasicOfService.getId());

            }
            statisticaTableDiagnostic.setCount1(zhenduanTotalOfServiceService.count(queryWrapper3));

            //诊断病人数
            QueryWrapper<ZhenduanDetailOfService> queryWrapper4 = new QueryWrapper();
            queryWrapper4.eq("checkYear", year);
            QueryWrapper<ZhenduanBasicOfService> qw2=new QueryWrapper<>();
            if(provinceCode!=null){
                qw2.eq("provinceCode", provinceCode);
                ZhenduanBasicOfService zhenduanBasicOfService=zhenduanBasicOfServiceService.getOne(qw2);
                queryWrapper3.eq("zhenduanBasicId",zhenduanBasicOfService.getId());
            }
            if(cityCode!=null){
                qw2.eq("cityCode", cityCode);
                ZhenduanBasicOfService zhenduanBasicOfService=zhenduanBasicOfServiceService.getOne(qw2);
                queryWrapper3.eq("zhenduanBasicId",zhenduanBasicOfService.getId());

            }
            if(districtCode!=null){
                qw2.eq("districtCode", districtCode);
                ZhenduanBasicOfService zhenduanBasicOfService=zhenduanBasicOfServiceService.getOne(qw2);
                queryWrapper3.eq("zhenduanBasicId",zhenduanBasicOfService.getId());

            }
            if(registerBigName!=null){
                qw2.eq("registerBigName", registerBigName);
                ZhenduanBasicOfService zhenduanBasicOfService=zhenduanBasicOfServiceService.getOne(qw2);
                queryWrapper3.eq("zhenduanBasicId",zhenduanBasicOfService.getId());

            }
            if(registerSmallName!=null){
                qw2.eq("registerSmallName", registerSmallName);
                ZhenduanBasicOfService zhenduanBasicOfService=zhenduanBasicOfServiceService.getOne(qw2);
                queryWrapper3.eq("zhenduanBasicId",zhenduanBasicOfService.getId());

            }
            if(hospitalLevel!=null){
                qw2.eq("hospitalLevel", hospitalLevel);
                ZhenduanBasicOfService zhenduanBasicOfService=zhenduanBasicOfServiceService.getOne(qw2);
                queryWrapper3.eq("zhenduanBasicId",zhenduanBasicOfService.getId());

            }
            statisticaTableDiagnostic.setIdNum(zhenduanDetailOfServiceService.count(queryWrapper4));

            return statisticaTableDiagnostic;
        }
        return null;
    }
}
