package com.hthyaq.zybadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.SuperviseService;
import com.hthyaq.zybadmin.service.ZhenduanDetailOfServiceService;
import com.hthyaq.zybadmin.service.ZhenduanTotalOfServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//职业病诊断情况统计分析表
@RestController
@RequestMapping("/diagnosticstatistics")
public class DiagnosticstatisticsController {
    @Autowired
    ZhenduanTotalOfServiceService zhenduanTotalOfServiceService;
    @Autowired
    ZhenduanDetailOfServiceService zhenduanDetailOfServiceService;

    @GetMapping("/list")
    public Diagnosticstatistics list(Integer year, String provinceCode, String cityCode, String districtCode,String dangerBigName, String dangerSmallName, String registerBigName,String registerSmallName, String industryBigName, String sickBigName,String sickSmallName, String touchYear, String gender) {
        if (year != null) {
            Diagnosticstatistics diagnosticstatistics=new Diagnosticstatistics();

            //职业病诊断企业数
            QueryWrapper<ZhenduanTotalOfService> queryWrapper = new QueryWrapper();
            QueryWrapper<ZhenduanDetailOfService> qw=new QueryWrapper<>();
            queryWrapper.eq("year",year);
            if (provinceCode != null) {

                qw.eq("provinceCode",provinceCode);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());
            }
            if (cityCode != null) {
                qw.eq("cityCode",cityCode);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (districtCode != null) {
                qw.eq("districtCode",districtCode);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (dangerBigName != null) {
                qw.eq("dangerBigName",dangerBigName);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (dangerSmallName != null) {
                qw.eq("dangerSmallName",dangerSmallName);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (registerBigName != null) {
                qw.eq("registerBigName",registerBigName);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (registerSmallName != null) {
                qw.eq("registerSmallName",registerSmallName);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (industryBigName != null) {
                qw.eq("industryBigName",industryBigName);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (sickBigName != null) {
                qw.eq("sickBigName",sickBigName);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (sickSmallName != null) {
                qw.eq("sickSmallName",sickSmallName);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (touchYear != null) {
                qw.eq("touchYear",touchYear);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (gender != null) {
                qw.eq("gender",gender);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }

            diagnosticstatistics.setCount2(zhenduanTotalOfServiceService.count(queryWrapper));


            //诊断出职业病病人企业数
            QueryWrapper<ZhenduanDetailOfService> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("checkyear",year);
            if (provinceCode != null) {
                queryWrapper.eq("provinceCode", provinceCode);
            }
            if (cityCode != null) {
                queryWrapper.eq("cityCode", cityCode);
            }
            if (districtCode != null) {
                queryWrapper.eq("districtCode", districtCode);
            }
            if (dangerBigName != null) {
                queryWrapper.eq("dangerBigName", dangerBigName);
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
            if (sickBigName != null) {
                queryWrapper.eq("sickBigName", sickBigName);
            }
            if (sickSmallName != null) {
                queryWrapper.eq("sickSmallName", sickSmallName);
            }
            if (touchYear != null) {
                queryWrapper.eq("touchYear", touchYear);
            }
            if (gender != null) {
                queryWrapper.eq("gender", gender);
            }
            diagnosticstatistics.setEnterpriseCode(zhenduanDetailOfServiceService.count(queryWrapper1));


            //职业病诊断人数
            QueryWrapper<ZhenduanTotalOfService> queryWrapper2 = new QueryWrapper();
            QueryWrapper<ZhenduanDetailOfService> qw1=new QueryWrapper<>();
            queryWrapper2.eq("year",year);
            if (provinceCode != null) {

                qw1.eq("provinceCode",provinceCode);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw1);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());
            }
            if (cityCode != null) {
                qw1.eq("cityCode",cityCode);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw1);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (districtCode != null) {
                qw1.eq("districtCode",districtCode);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw1);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (dangerBigName != null) {
                qw1.eq("dangerBigName",dangerBigName);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw1);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (dangerSmallName != null) {
                qw1.eq("dangerSmallName",dangerSmallName);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw1);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (registerBigName != null) {
                qw1.eq("registerBigName",registerBigName);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw1);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (registerSmallName != null) {
                qw1.eq("registerSmallName",registerSmallName);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw1);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (industryBigName != null) {
                qw.eq("industryBigName",industryBigName);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw1);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (sickBigName != null) {
                qw1.eq("sickBigName",sickBigName);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw1);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (sickSmallName != null) {
                qw1.eq("sickSmallName",sickSmallName);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw1);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (touchYear != null) {
                qw1.eq("touchYear",touchYear);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw1);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            if (gender != null) {
                qw1.eq("gender",gender);
                ZhenduanDetailOfService zhenduanDetailOfService=zhenduanDetailOfServiceService.getOne(qw1);
                queryWrapper.eq("zhenduanBasicId", zhenduanDetailOfService.getZhenduanBasicId());

            }
            diagnosticstatistics.setCount1(zhenduanTotalOfServiceService.count(queryWrapper2));


            //报告职业病人数
            QueryWrapper<ZhenduanDetailOfService> queryWrapper3 = new QueryWrapper();
            queryWrapper3.eq("checkyear",year);
            if (provinceCode != null) {
                queryWrapper.eq("provinceCode", provinceCode);
            }
            if (cityCode != null) {
                queryWrapper.eq("cityCode", cityCode);
            }
            if (districtCode != null) {
                queryWrapper.eq("districtCode", districtCode);
            }
            if (dangerBigName != null) {
                queryWrapper.eq("dangerBigName", dangerBigName);
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
            if (sickBigName != null) {
                queryWrapper.eq("sickBigName", sickBigName);
            }
            if (sickSmallName != null) {
                queryWrapper.eq("sickSmallName", sickSmallName);
            }
            if (touchYear != null) {
                queryWrapper.eq("touchYear", touchYear);
            }
            if (gender != null) {
                queryWrapper.eq("gender", gender);
            }
            diagnosticstatistics.setIdNum(zhenduanDetailOfServiceService.count(queryWrapper3));
            double c=(double)diagnosticstatistics.getIdNum()/diagnosticstatistics.getCount1()*100;
            diagnosticstatistics.setDiagnosticrate(String.format("%.1f",c)+"%");
            return diagnosticstatistics;
        }

        return null;
    }
}
