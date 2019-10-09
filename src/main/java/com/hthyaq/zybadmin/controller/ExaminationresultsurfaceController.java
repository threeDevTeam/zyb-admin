package com.hthyaq.zybadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



//职业健康检查结果统计分析表
@RestController
@RequestMapping("/examinationresultsurface")
public class ExaminationresultsurfaceController {
    @Autowired
    TijianDetail1OfServiceService tijianDetail1OfServiceService;
    @Autowired
    TijianBasicOfServiceService tijianBasicOfServiceService;
    @Autowired
    TijianDetail2OfServiceService tijianDetail2OfServiceService;
    @Autowired
    TijianTotalOfServiceService tijianTotalOfServiceService;
    @GetMapping("/list")
    public Examinationresultsurface list(Integer year, String provinceCode, String cityCode, String districtCode, String dangerBigName, String dangerSmallName, String registerBigName, String registerSmallName, String industryBigName){
        if (year != null) {
            Examinationresultsurface examinationresultsurface=new Examinationresultsurface();
           //职业健康检查企业数
            QueryWrapper<TijianDetail1OfService> queryWrapper = new QueryWrapper();
            queryWrapper.eq("checkYear", year);
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
            examinationresultsurface.setEnterpriseCode1(tijianDetail1OfServiceService.count(queryWrapper));

            //体检报告数
            QueryWrapper<TijianTotalOfService> queryWrapper1 = new QueryWrapper();
            QueryWrapper<TijianDetail1OfService> qw = new QueryWrapper();
            queryWrapper1.eq("year", year);
            if (provinceCode != null) {
                qw.eq("provinceCode",provinceCode);
                TijianDetail1OfService tijianDetail1OfService=tijianDetail1OfServiceService.getOne(qw);
                queryWrapper1.eq("superviseId",tijianDetail1OfService.getId());
            }
            if (cityCode != null) {
                qw.eq("cityCode",cityCode);
                TijianDetail1OfService tijianDetail1OfService=tijianDetail1OfServiceService.getOne(qw);
                queryWrapper1.eq("superviseId",tijianDetail1OfService.getId());
            }
            if (districtCode != null) {
                qw.eq("districtCode",districtCode);
                TijianDetail1OfService tijianDetail1OfService=tijianDetail1OfServiceService.getOne(qw);
                queryWrapper1.eq("superviseId",tijianDetail1OfService.getId());
            }
            if (dangerBigName != null) {
                qw.eq("dangerBigName",dangerBigName);
                TijianDetail1OfService tijianDetail1OfService=tijianDetail1OfServiceService.getOne(qw);
                queryWrapper1.eq("superviseId",tijianDetail1OfService.getId());
            }
            if (dangerSmallName != null) {
                qw.eq("dangerSmallName",dangerSmallName);
                TijianDetail1OfService tijianDetail1OfService=tijianDetail1OfServiceService.getOne(qw);
                queryWrapper1.eq("superviseId",tijianDetail1OfService.getId());
            }
            if (registerBigName != null) {
                qw.eq("registerBigName",registerBigName);
                TijianDetail1OfService tijianDetail1OfService=tijianDetail1OfServiceService.getOne(qw);
                queryWrapper1.eq("superviseId",tijianDetail1OfService.getId());
            }
            if (registerSmallName != null) {
                qw.eq("registerSmallName",registerSmallName);
                TijianDetail1OfService tijianDetail1OfService=tijianDetail1OfServiceService.getOne(qw);
                queryWrapper1.eq("superviseId",tijianDetail1OfService.getId());
            }
            if (industryBigName != null) {
                qw.eq("industryBigName",industryBigName);
                TijianDetail1OfService tijianDetail1OfService=tijianDetail1OfServiceService.getOne(qw);
                queryWrapper1.eq("superviseId",tijianDetail1OfService.getId());
            }

            examinationresultsurface.setCount1(tijianTotalOfServiceService.count(queryWrapper1));

            //职业禁忌证人数
            QueryWrapper<TijianDetail1OfService> queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("result","职业禁忌证").eq("checkYear", year);
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

            examinationresultsurface.setIdNum1(tijianDetail1OfServiceService.count(queryWrapper2));

            //疑似职业病人数
            QueryWrapper<TijianDetail2OfService> queryWrapper3 = new QueryWrapper();
            queryWrapper3.eq("checkYear", year);
            if (provinceCode != null) {
                queryWrapper3.eq("provinceCode", provinceCode);
            }
            if (cityCode != null) {
                queryWrapper3.eq("cityCode", cityCode);
            }
            if (districtCode != null) {
                queryWrapper3.eq("districtCode", districtCode);
            }
            if (dangerBigName != null) {
                queryWrapper3.eq("dangerBigName", dangerBigName);
            }
            if (dangerSmallName != null) {
                queryWrapper3.eq("dangerSmallName", dangerSmallName);
            }
            if (registerBigName != null) {
                queryWrapper3.eq("registerBigName", registerBigName);
            }
            if (registerSmallName != null) {
                queryWrapper3.eq("registerSmallName", registerSmallName);
            }
            if (industryBigName != null) {
                queryWrapper3.eq("industryBigName", industryBigName);
            }
            examinationresultsurface.setIdNum2(tijianDetail2OfServiceService.count(queryWrapper3));

            //检出疑似职业病企业数
            QueryWrapper<TijianDetail2OfService> queryWrapper4 = new QueryWrapper();
            queryWrapper4.eq("checkYear", year);
            if (provinceCode != null) {
                queryWrapper4.eq("provinceCode", provinceCode);
            }
            if (cityCode != null) {
                queryWrapper4.eq("cityCode", cityCode);
            }
            if (districtCode != null) {
                queryWrapper4.eq("districtCode", districtCode);
            }
            if (dangerBigName != null) {
                queryWrapper4.eq("dangerBigName", dangerBigName);
            }
            if (dangerSmallName != null) {
                queryWrapper4.eq("dangerSmallName", dangerSmallName);
            }
            if (registerBigName != null) {
                queryWrapper4.eq("registerBigName", registerBigName);
            }
            if (registerSmallName != null) {
                queryWrapper4.eq("registerSmallName", registerSmallName);
            }
            if (industryBigName != null) {
                queryWrapper4.eq("industryBigName", industryBigName);
            }
            examinationresultsurface.setEnterpriseCode2(tijianDetail2OfServiceService.count(queryWrapper4));
            examinationresultsurface.setZybEnterpriserate((float)examinationresultsurface.getIdNum1()/examinationresultsurface.getCount1());
            return examinationresultsurface;
        }
        return null;
    }
}
