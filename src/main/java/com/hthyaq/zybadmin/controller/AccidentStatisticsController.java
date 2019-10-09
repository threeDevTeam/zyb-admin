package com.hthyaq.zybadmin.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.AccidentOfSuperviseService;
import com.hthyaq.zybadmin.service.ServiceSuperviseOfSuperviseService;
import com.hthyaq.zybadmin.service.SuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;


//功能7.表2-35 职业病危害事故统计分析表
@RestController
@RequestMapping("/accidentStatistics")
public class AccidentStatisticsController {
    private void queryCondition(String columnName, Serializable columnValue, String foreignKeyName, String fieldName, IService service, QueryWrapper<?> queryWrapper1, QueryWrapper<?> queryWrapper2) {
        if (ObjectUtil.isNotEmpty(columnValue) && ObjectUtil.isNotNull(columnValue)) {
            queryWrapper1.eq(columnName, columnValue);
            Object obj = service.getOne(queryWrapper1);
            queryWrapper2.eq(foreignKeyName, ReflectUtil.getFieldValue(obj, fieldName));
        }
    }
    @Autowired
    AccidentOfSuperviseService accidentOfSuperviseService;
    @Autowired
    SuperviseService superviseService;
    @GetMapping("/list")
    public AccidentStatistics list(Integer year, String provinceCode, String cityCode, String districtCode) {
        if (year != null) {
            AccidentStatistics accidentStatistics = new AccidentStatistics();
            //dustCount
            QueryWrapper<AccidentOfSupervise> queryWrapper = new QueryWrapper();
            queryWrapper.select("sum(dustCount) dustCount").eq("year", year);
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
            AccidentOfSupervise accidentOfSupervise = accidentOfSuperviseService.getOne(queryWrapper);
            accidentStatistics.setDustCount(accidentOfSupervise.getDustCount());

            //poisonCount
            QueryWrapper<AccidentOfSupervise> queryWrapper2 = new QueryWrapper();
            queryWrapper2.select("sum(poisonCount) poisonCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper2);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper2);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper2);
            }
            AccidentOfSupervise accidentOfSupervise2 = accidentOfSuperviseService.getOne(queryWrapper2);
            accidentStatistics.setPoisonCount(accidentOfSupervise2.getPoisonCount());


            //otherCount
            QueryWrapper<AccidentOfSupervise> queryWrapper3 = new QueryWrapper();
            queryWrapper3.select("sum(otherCount) otherCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper3);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper3);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper3);
            }
            AccidentOfSupervise accidentOfSupervise3= accidentOfSuperviseService.getOne(queryWrapper3);
            accidentStatistics.setOtherCount(accidentOfSupervise3.getOtherCount());

            //dustPersonCount
            QueryWrapper<AccidentOfSupervise> queryWrapper4 = new QueryWrapper();
            queryWrapper4.select("sum(dustPersonCount) dustPersonCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper4);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper4);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper4);
            }
            AccidentOfSupervise accidentOfSupervise4= accidentOfSuperviseService.getOne(queryWrapper4);
            accidentStatistics.setDustPersonCount(accidentOfSupervise4.getDustPersonCount());

            //poisonPersonCount
            QueryWrapper<AccidentOfSupervise> queryWrapper5 = new QueryWrapper();
            queryWrapper5.select("sum(poisonPersonCount) poisonPersonCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper5);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper5);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper5);
            }
            AccidentOfSupervise accidentOfSupervise5= accidentOfSuperviseService.getOne(queryWrapper5);
            accidentStatistics.setPoisonPersonCount(accidentOfSupervise5.getPoisonPersonCount());


            //otherPersonCount
            QueryWrapper<AccidentOfSupervise> queryWrapper6 = new QueryWrapper();
            queryWrapper6.select("sum(otherPersonCount) otherPersonCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper6);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper6);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper6);
            }
            AccidentOfSupervise accidentOfSupervise6= accidentOfSuperviseService.getOne(queryWrapper6);
            accidentStatistics.setOtherPersonCount(accidentOfSupervise6.getOtherPersonCount());

            //dustDieCount
            QueryWrapper<AccidentOfSupervise> queryWrapper7 = new QueryWrapper();
            queryWrapper7.select("sum(dustDieCount) dustDieCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper7);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper7);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper7);
            }
            AccidentOfSupervise accidentOfSupervise7= accidentOfSuperviseService.getOne(queryWrapper7);
            accidentStatistics.setDustDieCount(accidentOfSupervise7.getDustDieCount());

            //poisonDieCount
            QueryWrapper<AccidentOfSupervise> queryWrapper8 = new QueryWrapper();
            queryWrapper8.select("sum(poisonDieCount) poisonDieCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper8);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper8);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper8);
            }
            AccidentOfSupervise accidentOfSupervise8= accidentOfSuperviseService.getOne(queryWrapper8);
            accidentStatistics.setPoisonDieCount(accidentOfSupervise8.getPoisonDieCount());

            //otherDieCount
            QueryWrapper<AccidentOfSupervise> queryWrapper9 = new QueryWrapper();
            queryWrapper9.select("sum(otherDieCount) otherDieCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper9);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper9);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper9);
            }
            AccidentOfSupervise accidentOfSupervise9= accidentOfSuperviseService.getOne(queryWrapper9);
            accidentStatistics.setOtherDieCount(accidentOfSupervise9.getOtherDieCount());

            //loseMoney
            QueryWrapper<AccidentOfSupervise> queryWrapper10 = new QueryWrapper();
            queryWrapper10.select("sum(loseMoney) loseMoney").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper10);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper10);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper10);
            }
            AccidentOfSupervise accidentOfSupervise10= accidentOfSuperviseService.getOne(queryWrapper10);
            accidentStatistics.setLoseMoney(accidentOfSupervise10.getLoseMoney());

            return accidentStatistics;
        }
        return null;
    }
}
