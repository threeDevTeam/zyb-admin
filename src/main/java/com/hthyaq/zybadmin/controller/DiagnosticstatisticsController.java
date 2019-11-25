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
    public Diagnosticstatistics list(Integer year) {
        if (year != null) {
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
            diagnosticstatistics.setDiagnosticrate(String.format("%.1f",c)+"%");
            return diagnosticstatistics;
        }

        return null;
    }
}
