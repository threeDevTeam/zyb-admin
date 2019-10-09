package com.hthyaq.zybadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.HealthyBuild;
import com.hthyaq.zybadmin.model.entity.LawOfSupervise;
import com.hthyaq.zybadmin.model.entity.ResourceStatistics;
import com.hthyaq.zybadmin.model.entity.Supervise;
import com.hthyaq.zybadmin.service.LawOfSuperviseService;
import com.hthyaq.zybadmin.service.SuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//职业健康法规标准建设统计分析表
@RestController
@RequestMapping("/healthyBuild")
public class HealthyBuildController {
    @Autowired
    SuperviseService superviseService;
    @Autowired
    LawOfSuperviseService lawOfSuperviseService;
    @GetMapping("/list")
    public HealthyBuild list(Integer year, String provinceCode, String cityCode, String districtCode) {
        if(year!=null){
            HealthyBuild healthyBuild = new HealthyBuild();
            QueryWrapper<LawOfSupervise> queryWrapper = new QueryWrapper();
            queryWrapper.select("sum(ruleIncrease) ruleIncrease").eq("year",year);
            QueryWrapper<Supervise> queryWrapper1=new QueryWrapper<>();
            if(provinceCode!=null){
                queryWrapper1.eq("provinceCode",provinceCode);
                Supervise supervise=superviseService.getOne(queryWrapper1);
                queryWrapper.eq("superviseId",supervise.getId());
            }
            if(cityCode!=null){
                queryWrapper1.eq("cityCode",cityCode);
                Supervise supervise=superviseService.getOne(queryWrapper1);
                queryWrapper.eq("superviseId",supervise.getId());
            }
            if(districtCode!=null){
                queryWrapper1.eq("districtCode",districtCode);
                Supervise supervise=superviseService.getOne(queryWrapper1);
                queryWrapper.eq("superviseId",supervise.getId());
            }
            LawOfSupervise lawOfSupervise1=lawOfSuperviseService.getOne(queryWrapper);
            healthyBuild.setRuleIncrease(lawOfSupervise1.getRuleIncrease());
            healthyBuild.setRuleSum(lawOfSupervise1.getRuleIncrease());
            //FileIncrease
            QueryWrapper<LawOfSupervise> queryWrapper2 = new QueryWrapper();
            queryWrapper2.select("sum(fileIncrease) fileIncrease").eq("year",year);
            if(provinceCode!=null){
                queryWrapper1.eq("provinceCode",provinceCode);
                Supervise supervise=superviseService.getOne(queryWrapper1);
                queryWrapper2.eq("superviseId",supervise.getId());
            }
            if(cityCode!=null){
                queryWrapper1.eq("cityCode",cityCode);
                Supervise supervise=superviseService.getOne(queryWrapper1);
                queryWrapper2.eq("superviseId",supervise.getId());
            }
            if(districtCode!=null){
                queryWrapper1.eq("districtCode",districtCode);
                Supervise supervise=superviseService.getOne(queryWrapper1);
                queryWrapper2.eq("superviseId",supervise.getId());
            }
            LawOfSupervise lawOfSupervise2=lawOfSuperviseService.getOne(queryWrapper2);
            healthyBuild.setFileIncrease(lawOfSupervise2.getFileIncrease());
            healthyBuild.setFileSum(lawOfSupervise2.getFileIncrease());

            //StartdardIncrease

            QueryWrapper<LawOfSupervise> queryWrapper3 = new QueryWrapper();
            queryWrapper3.select("sum(startdardIncrease) startdardIncrease").eq("year",year);
            if(provinceCode!=null){
                queryWrapper1.eq("provinceCode",provinceCode);
                Supervise supervise=superviseService.getOne(queryWrapper1);
                queryWrapper3.eq("superviseId",supervise.getId());
            }
            if(cityCode!=null){
                queryWrapper1.eq("cityCode",cityCode);
                Supervise supervise=superviseService.getOne(queryWrapper1);
                queryWrapper3.eq("superviseId",supervise.getId());
            }
            if(districtCode!=null){
                queryWrapper1.eq("districtCode",districtCode);
                Supervise supervise=superviseService.getOne(queryWrapper1);
                queryWrapper3.eq("superviseId",supervise.getId());
            }
            LawOfSupervise lawOfSupervise3=lawOfSuperviseService.getOne(queryWrapper3);
            healthyBuild.setStartdardIncrease(lawOfSupervise3.getStartdardIncrease());
            healthyBuild.setStartdardSum(lawOfSupervise3.getStartdardIncrease());
            return healthyBuild;
        }
        return null;
    }
}
