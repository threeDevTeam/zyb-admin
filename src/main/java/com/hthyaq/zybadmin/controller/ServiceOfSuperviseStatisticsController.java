package com.hthyaq.zybadmin.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.ServiceOfSuperviseService;
import com.hthyaq.zybadmin.service.SuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;


//功能5.表2-33 技术服务机构监管情况统计分析表
@RestController
@RequestMapping("/serviceOfSuperviseStatistics")
public class ServiceOfSuperviseStatisticsController {
    private void queryCondition(String columnName, Serializable columnValue, String foreignKeyName, String fieldName, IService service, QueryWrapper<?> queryWrapper1, QueryWrapper<?> queryWrapper2) {
        if (ObjectUtil.isNotEmpty(columnValue) && ObjectUtil.isNotNull(columnValue)) {
            queryWrapper1.eq(columnName, columnValue);
            Object obj = service.getOne(queryWrapper1);
            queryWrapper2.eq(foreignKeyName, ReflectUtil.getFieldValue(obj, fieldName));
        }
    }

    @Autowired
    SuperviseService superviseService;
    @Autowired
    ServiceOfSuperviseService serviceOfSuperviseService;

    @GetMapping("/list")
    public ServiceOfSuperviseStatistics list(Integer year, String provinceCode, String cityCode, String districtCode) {
        if (year != null) {
            //1.jianceIncrease
            ServiceOfSuperviseStatistics serviceOfSuperviseStatistics = new ServiceOfSuperviseStatistics();
            QueryWrapper<ServiceOfSupervise> queryWrapper = new QueryWrapper();
            queryWrapper.select("sum(jianceIncrease) jianceIncrease").eq("jianceLevel", "甲级").eq("year", year);
            QueryWrapper<Supervise> queryWrapper1 = new QueryWrapper<>();
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper);
            }
            ServiceOfSupervise serviceOfSupervise = serviceOfSuperviseService.getOne(queryWrapper);
            serviceOfSuperviseStatistics.setJianceIncrease1(serviceOfSupervise.getJianceIncrease());


            //2.jianceSum
            QueryWrapper<ServiceOfSupervise> queryWrapper2 = new QueryWrapper();
            queryWrapper2.select("sum(jianceSum) jianceSum").eq("jianceLevel", "甲级").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper2);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper2);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper2);
            }
            ServiceOfSupervise serviceOfSupervise1 = serviceOfSuperviseService.getOne(queryWrapper2);
            serviceOfSuperviseStatistics.setJianceSum1(serviceOfSupervise1.getJianceSum());

            //3.jianceIncrease2
            QueryWrapper<ServiceOfSupervise> queryWrapper3 = new QueryWrapper();
            queryWrapper3.select("sum(jianceIncrease) jianceIncrease").eq("jianceLevel", "乙级").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper3);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper3);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper3);
            }
            ServiceOfSupervise serviceOfSupervise2 = serviceOfSuperviseService.getOne(queryWrapper3);
            serviceOfSuperviseStatistics.setJianceIncrease2(serviceOfSupervise2.getJianceIncrease());



            //4.jianceSum
            QueryWrapper<ServiceOfSupervise> queryWrapper4 = new QueryWrapper();
            queryWrapper4.select("sum(jianceSum) jianceSum").eq("jianceLevel", "乙级").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper4);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper4);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper4);
            }
            ServiceOfSupervise serviceOfSupervise3 = serviceOfSuperviseService.getOne(queryWrapper4);
            serviceOfSuperviseStatistics.setJianceSum2(serviceOfSupervise3.getJianceSum());

            //5.jianceIncrease3
            QueryWrapper<ServiceOfSupervise> queryWrapper5 = new QueryWrapper();
            queryWrapper5.select("sum(jianceIncrease) jianceIncrease").eq("jianceLevel", "丙级").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper5);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper5);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper5);
            }
            ServiceOfSupervise serviceOfSupervise4= serviceOfSuperviseService.getOne(queryWrapper5);
            serviceOfSuperviseStatistics.setJianceIncrease3(serviceOfSupervise4.getJianceIncrease());

            //6.jianceSum
            QueryWrapper<ServiceOfSupervise> queryWrapper6 = new QueryWrapper();
            queryWrapper6.select("sum(jianceSum) jianceSum").eq("jianceLevel", "丙级").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper6);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper6);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper6);
            }
            ServiceOfSupervise serviceOfSupervise5 = serviceOfSuperviseService.getOne(queryWrapper6);
            serviceOfSuperviseStatistics.setJianceSum3(serviceOfSupervise5.getJianceSum());

            //7.tijianIncrease
            QueryWrapper<ServiceOfSupervise> queryWrapper7 = new QueryWrapper();
            queryWrapper7.select("sum(tijianIncrease) tijianIncrease").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper7);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper7);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper7);
            }
            ServiceOfSupervise serviceOfSupervise6 = serviceOfSuperviseService.getOne(queryWrapper7);
            serviceOfSuperviseStatistics.setTijianIncrease(serviceOfSupervise6.getTijianIncrease());


            //8.tijianSum
            QueryWrapper<ServiceOfSupervise> queryWrapper8 = new QueryWrapper();
            queryWrapper8.select("sum(tijianSum) tijianSum").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper8);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper8);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper8);
            }
            ServiceOfSupervise serviceOfSupervise7 = serviceOfSuperviseService.getOne(queryWrapper8);
            serviceOfSuperviseStatistics.setTijianSum(serviceOfSupervise7.getTijianSum());

            //zhenduanIncrease
            QueryWrapper<ServiceOfSupervise> queryWrapper9 = new QueryWrapper();
            queryWrapper9.select("sum(zhenduanIncrease) zhenduanIncrease").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper9);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper9);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper9);
            }
            ServiceOfSupervise serviceOfSupervise8 = serviceOfSuperviseService.getOne(queryWrapper9);
            serviceOfSuperviseStatistics.setZhenduanIncrease(serviceOfSupervise8.getZhenduanIncrease());


            //zhenduanSum
            QueryWrapper<ServiceOfSupervise> queryWrapper10 = new QueryWrapper();
            queryWrapper10.select("sum(zhenduanSum) zhenduanSum").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper10);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper10);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper10);
            }
            ServiceOfSupervise serviceOfSupervise9 = serviceOfSuperviseService.getOne(queryWrapper10);
            serviceOfSuperviseStatistics.setZhenduanSum(serviceOfSupervise9.getZhenduanSum());

            return serviceOfSuperviseStatistics;
        }


        return null;
    }
}
