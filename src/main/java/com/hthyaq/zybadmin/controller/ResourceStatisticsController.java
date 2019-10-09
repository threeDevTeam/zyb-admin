package com.hthyaq.zybadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.*;
import org.apache.poi.hwpf.sprm.SprmUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.List;



//功能1.表2-29 职业健康监管资源统计分析表
@RestController
@RequestMapping("/resourceStatistics")
public class ResourceStatisticsController {
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    PersonOfSuperviseService personOfSuperviseService;
    @Autowired
    EquipmentOfSuperviseService equipmentOfSuperviseService;
    @Autowired
    SuperviseService superviseService;
    @GetMapping("/list")
    public ResourceStatistics list(Integer year,String provinceCode,String cityCode,String districtCode) {
        if(year!=null){
        ResourceStatistics resourceStatistics = new ResourceStatistics();
//enterprise
        QueryWrapper<Enterprise> qw=new QueryWrapper<>();
        qw.eq("year",year);
        if(provinceCode!=null){
            qw.eq("provinceCode",provinceCode);
        }
        if(cityCode!=null){
            qw.eq("cityCode",cityCode);
        }
        if(districtCode!=null){
            qw.eq("districtCode",districtCode);
        }
        resourceStatistics.setId(enterpriseService.count(qw));
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sum(workerNumber) workerNumber");
        queryWrapper.eq("year",year);
            if(provinceCode!=null){
                queryWrapper.eq("provinceCode",provinceCode);
            }
            if(cityCode!=null){
                queryWrapper.eq("cityCode",cityCode);
            }
            if(districtCode!=null){
                queryWrapper.eq("districtCode",districtCode);
            }
        Enterprise enterprise = enterpriseService.getOne(queryWrapper);
        System.out.println(enterprise.getWorkerNumber());
        resourceStatistics.setWorkerNumber(enterprise.getWorkerNumber());

//personOfSupervise
        QueryWrapper<Supervise> qw1=new QueryWrapper<>();
            qw1.eq("year",year);
            if(provinceCode!=null){
                qw1.eq("provinceCode",provinceCode);
            }
            if(cityCode!=null){
                qw1.eq("cityCode",cityCode);
            }
            if(districtCode!=null){
                qw1.eq("districtCode",districtCode);
            }
        Supervise supervise= superviseService.getOne(qw1);

        resourceStatistics.setIdNum(personOfSuperviseService.count(new QueryWrapper<PersonOfSupervise>().eq("superviseId", supervise.getId())));
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("isGet", "是");
        queryWrapper1.eq("superviseId", supervise.getId());
        resourceStatistics.setIdNum2(personOfSuperviseService.count(queryWrapper1));

//equipmentOfSupervise
        QueryWrapper<EquipmentOfSupervise> queryWrapper3 = new QueryWrapper();
        queryWrapper3.select("sum(amount) amount");
        queryWrapper3.eq("superviseId", supervise.getId());
        EquipmentOfSupervise equipmentOfSupervise = equipmentOfSuperviseService.getOne(queryWrapper3);
        resourceStatistics.setAmount(equipmentOfSupervise.getAmount());

        QueryWrapper queryWrapper4 = new QueryWrapper();
        queryWrapper4.select("sum(amount) amount").eq("status","在用");
        queryWrapper4.eq("superviseId", supervise.getId());
        EquipmentOfSupervise equipmentOfSupervise1 = equipmentOfSuperviseService.getOne(queryWrapper4);
        resourceStatistics.setAmount2(equipmentOfSupervise.getAmount());
        return resourceStatistics;
    }
        return null;
    }

}
