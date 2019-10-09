package com.hthyaq.zybadmin.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.ServiceOfSuperviseService;
import com.hthyaq.zybadmin.service.ServiceSuperviseOfSuperviseService;
import com.hthyaq.zybadmin.service.SuperviseService;
import com.hthyaq.zybadmin.service.ThreeCheckOfSuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;


//功能6.表2-34 技术服务机构监管情况统计分析表（续）

@RestController
@RequestMapping("/serviceOfSuperviseStatistics2")
public class ServiceOfSuperviseStatistics2Controller {
    private void queryCondition(String columnName, Serializable columnValue, String foreignKeyName, String fieldName, IService service, QueryWrapper<?> queryWrapper1, QueryWrapper<?> queryWrapper2) {
        if (ObjectUtil.isNotEmpty(columnValue) && ObjectUtil.isNotNull(columnValue)) {
            queryWrapper1.eq(columnName, columnValue);
            Object obj = service.getOne(queryWrapper1);
            queryWrapper2.eq(foreignKeyName, ReflectUtil.getFieldValue(obj, fieldName));
        }
    }
    @Autowired
    ServiceSuperviseOfSuperviseService serviceSuperviseOfSuperviseService;
    @Autowired
    SuperviseService superviseService;

    @GetMapping("/list")
    public ServiceOfSuperviseStatistics2 list(Integer year, String provinceCode, String cityCode, String districtCode) {
        if (year != null) {
        //1.jianceCount
            ServiceOfSuperviseStatistics2 serviceOfSuperviseStatistics2 = new ServiceOfSuperviseStatistics2();
            QueryWrapper<ServiceSuperviseOfSupervise> queryWrapper = new QueryWrapper();
            queryWrapper.select("sum(jianceCount) jianceCount").eq("year", year);
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
            ServiceSuperviseOfSupervise serviceSuperviseOfSupervise = serviceSuperviseOfSuperviseService.getOne(queryWrapper);
            serviceOfSuperviseStatistics2.setJianceCount(serviceSuperviseOfSupervise.getJianceCount());

            //tijianCount
            QueryWrapper<ServiceSuperviseOfSupervise> queryWrapper2 = new QueryWrapper();
            queryWrapper2.select("sum(tijianianceount) tijianianceount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper2);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper2);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper2);
            }
            ServiceSuperviseOfSupervise serviceSuperviseOfSupervise2 = serviceSuperviseOfSuperviseService.getOne(queryWrapper2);
            serviceOfSuperviseStatistics2.setTijianCount(serviceSuperviseOfSupervise2.getTijianianceount());


            //zhenduanCount
            QueryWrapper<ServiceSuperviseOfSupervise> queryWrapper3 = new QueryWrapper();
            queryWrapper3.select("sum(zhenduanCount) zhenduanCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper3);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper3);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper3);
            }
            ServiceSuperviseOfSupervise serviceSuperviseOfSupervise3 = serviceSuperviseOfSuperviseService.getOne(queryWrapper3);
            serviceOfSuperviseStatistics2.setZhenduanCount(serviceSuperviseOfSupervise3.getZhenduanCount());


            //jiancePunishCount
            QueryWrapper<ServiceSuperviseOfSupervise> queryWrapper4 = new QueryWrapper();
            queryWrapper4.select("sum(jiancePunishCount) jiancePunishCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper4);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper4);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper4);
            }
            ServiceSuperviseOfSupervise serviceSuperviseOfSupervise4= serviceSuperviseOfSuperviseService.getOne(queryWrapper4);
            serviceOfSuperviseStatistics2.setJiancePunishCount(serviceSuperviseOfSupervise4.getJiancePunishCount());



            //tijianPunishCount
            QueryWrapper<ServiceSuperviseOfSupervise> queryWrapper5 = new QueryWrapper();
            queryWrapper5.select("sum(tijianPunishCount) tijianPunishCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper5);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper5);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper5);
            }
            ServiceSuperviseOfSupervise serviceSuperviseOfSupervise5= serviceSuperviseOfSuperviseService.getOne(queryWrapper5);
            serviceOfSuperviseStatistics2.setTijianPunishCount(serviceSuperviseOfSupervise5.getTijianPunishCount());


            //zhenduanPunishCount
            QueryWrapper<ServiceSuperviseOfSupervise> queryWrapper6 = new QueryWrapper();
            queryWrapper6.select("sum(zhenduanPunishCount) zhenduanPunishCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper6);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper6);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper6);
            }
            ServiceSuperviseOfSupervise serviceSuperviseOfSupervise6= serviceSuperviseOfSuperviseService.getOne(queryWrapper6);
            serviceOfSuperviseStatistics2.setZhenduanPunishCount(serviceSuperviseOfSupervise6.getZhenduanPunishCount());

            //jianceMoney
            QueryWrapper<ServiceSuperviseOfSupervise> queryWrapper7 = new QueryWrapper();
            queryWrapper7.select("sum(jianceMoney) jianceMoney").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper7);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper7);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper7);
            }
            ServiceSuperviseOfSupervise serviceSuperviseOfSupervise7= serviceSuperviseOfSuperviseService.getOne(queryWrapper7);
            serviceOfSuperviseStatistics2.setJianceMoney(serviceSuperviseOfSupervise7.getJianceMoney());


            //tijianMoney
            QueryWrapper<ServiceSuperviseOfSupervise> queryWrapper8 = new QueryWrapper();
            queryWrapper8.select("sum(tijianMoney) tijianMoney").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper8);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper8);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper8);
            }
            ServiceSuperviseOfSupervise serviceSuperviseOfSupervise8= serviceSuperviseOfSuperviseService.getOne(queryWrapper8);
            serviceOfSuperviseStatistics2.setTijianMoney(serviceSuperviseOfSupervise8.getTijianMoney());

            //zhenduanMoney
            QueryWrapper<ServiceSuperviseOfSupervise> queryWrapper9 = new QueryWrapper();
            queryWrapper9.select("sum(zhenduanMoney) zhenduanMoney").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper9);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper9);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper9);
            }
            ServiceSuperviseOfSupervise serviceSuperviseOfSupervise9= serviceSuperviseOfSuperviseService.getOne(queryWrapper9);
            serviceOfSuperviseStatistics2.setZhenduanMoney(serviceSuperviseOfSupervise9.getZhenduanMoney());

            //jianceCancelCount
            QueryWrapper<ServiceSuperviseOfSupervise> queryWrapper10 = new QueryWrapper();
            queryWrapper10.select("sum(jianceCancelCount) jianceCancelCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper10);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper10);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper10);
            }
            ServiceSuperviseOfSupervise serviceSuperviseOfSupervise10= serviceSuperviseOfSuperviseService.getOne(queryWrapper10);
            serviceOfSuperviseStatistics2.setJianceCancelCount(serviceSuperviseOfSupervise10.getJianceCancelCount());


            //tiijanCancelCount
            QueryWrapper<ServiceSuperviseOfSupervise> queryWrapper11 = new QueryWrapper();
            queryWrapper11.select("sum(tijianianceancelCount) tijianianceancelCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper11);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper11);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper11);
            }
            ServiceSuperviseOfSupervise serviceSuperviseOfSupervise11= serviceSuperviseOfSuperviseService.getOne(queryWrapper11);
            serviceOfSuperviseStatistics2.setTiijanCancelCount(serviceSuperviseOfSupervise11.getTijianianceancelCount());

            //zhenduanCancelCount
            QueryWrapper<ServiceSuperviseOfSupervise> queryWrapper12 = new QueryWrapper();
            queryWrapper12.select("sum(zhenduanCancelCount) zhenduanCancelCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper12);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper12);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper12);
            }
            ServiceSuperviseOfSupervise serviceSuperviseOfSupervise12= serviceSuperviseOfSuperviseService.getOne(queryWrapper12);
            serviceOfSuperviseStatistics2.setZhenduanCancelCount(serviceSuperviseOfSupervise12.getZhenduanCancelCount());

            return serviceOfSuperviseStatistics2;
        }

        return null;
    }
}
