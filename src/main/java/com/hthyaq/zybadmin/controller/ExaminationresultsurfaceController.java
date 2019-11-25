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
    public Examinationresultsurface list(Integer year){
        if (year != null) {
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
            examinationresultsurface.setZybEnterpriserate((float)examinationresultsurface.getIdNum1()/examinationresultsurface.getCount1());
            return examinationresultsurface;
        }
        return null;
    }
}
