package com.hthyaq.zybadmin.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.ExecuteLawOfSuperviseService;
import com.hthyaq.zybadmin.service.LawOfSuperviseService;
import com.hthyaq.zybadmin.service.SuperviseService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;


//
//	功能3.表2-31 职业健康监督执法统计分析表
@RestController
@RequestMapping("/superviseStatistics")
public class SuperviseStatisticsController {
    @Autowired
    SuperviseService superviseService;
    @Autowired
    ExecuteLawOfSuperviseService executeLawOfSuperviseService;

    private void queryCondition(String columnName, Serializable columnValue, String foreignKeyName, String fieldName, IService service, QueryWrapper<?> queryWrapper1, QueryWrapper<?> queryWrapper2) {
        if (ObjectUtil.isNotEmpty(columnValue) && ObjectUtil.isNotNull(columnValue)) {
            queryWrapper1.eq(columnName, columnValue);
            Object obj = service.getOne(queryWrapper1);
            queryWrapper2.eq(foreignKeyName, ReflectUtil.getFieldValue(obj, fieldName));
        }
    }

    @GetMapping("/list")
    public SuperviseStatistics list(Integer year, String provinceCode, String cityCode, String districtCode) {
        if (year != null) {
            //1.personCount
            SuperviseStatistics superviseStatistics = new SuperviseStatistics();
            QueryWrapper<ExecuteLawOfSupervise> queryWrapper = new QueryWrapper();
            queryWrapper.select("sum(personCount) personCount").eq("year", year);
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
        /*        if(provinceCode!=null){
            queryWrapper1.eq("provinceCode",provinceCode);
            Supervise supervise=superviseService.getOne(queryWrapper1);
            queryWrapper.eq("superviseId",supervise.getId());
        }*/
//        if(cityCode!=null){
//            queryWrapper1.eq("cityCode",cityCode);
//            Supervise supervise=superviseService.getOne(queryWrapper1);
//            queryWrapper.eq("superviseId",supervise.getId());
//        }
//        if(districtCode!=null){
//            queryWrapper1.eq("districtCode",districtCode);
//            Supervise supervise=superviseService.getOne(queryWrapper1);
//            queryWrapper.eq("superviseId",supervise.getId());
//        }
            ExecuteLawOfSupervise executeLawOfSupervise = executeLawOfSuperviseService.getOne(queryWrapper);
            superviseStatistics.setPersonCount(executeLawOfSupervise.getPersonCount());

            //2.paperCount
            QueryWrapper<ExecuteLawOfSupervise> queryWrapper2 = new QueryWrapper();
            queryWrapper2.select("sum(paperCount) paperCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper2);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper2);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper2);
            }
//        if(provinceCode!=null){
//            queryWrapper3.eq("provinceCode",provinceCode);
//            Supervise supervise=superviseService.getOne(queryWrapper3);
//            queryWrapper2.eq("superviseId",supervise.getId());
//        }
//        if(cityCode!=null){
//            queryWrapper3.eq("cityCode",cityCode);
//            Supervise supervise=superviseService.getOne(queryWrapper3);
//            queryWrapper2.eq("superviseId",supervise.getId());
//        }
//        if(districtCode!=null){
//            queryWrapper3.eq("districtCode",districtCode);
//            Supervise supervise=superviseService.getOne(queryWrapper3);
//            queryWrapper2.eq("superviseId",supervise.getId());
//        }
            ExecuteLawOfSupervise executeLawOfSupervise1 = executeLawOfSuperviseService.getOne(queryWrapper2);
            superviseStatistics.setPaperCount(executeLawOfSupervise1.getPaperCount());

            //3. questionCount
            QueryWrapper<ExecuteLawOfSupervise> queryWrapper3 = new QueryWrapper();
            queryWrapper3.select("sum(questionCount) questionCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper3);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper3);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper3);
            }
            ExecuteLawOfSupervise executeLawOfSupervise2 = executeLawOfSuperviseService.getOne(queryWrapper3);
            superviseStatistics.setQuestionCount(executeLawOfSupervise2.getQuestionCount());

            //4.changeCount+fixCount
            QueryWrapper<ExecuteLawOfSupervise> queryWrapper4 = new QueryWrapper();
            queryWrapper4.select("sum(changeCount+fixCount) changeCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper4);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper4);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper4);
            }
            ExecuteLawOfSupervise executeLawOfSupervise3 = executeLawOfSuperviseService.getOne(queryWrapper4);
            superviseStatistics.setChangefixCount(executeLawOfSupervise3.getChangeCount());

            //5.punishCount
            QueryWrapper<ExecuteLawOfSupervise> queryWrapper5 = new QueryWrapper();
            queryWrapper5.select("sum(punishCount) punishCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper5);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper5);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper5);
            }
            ExecuteLawOfSupervise executeLawOfSupervise4 = executeLawOfSuperviseService.getOne(queryWrapper5);
            superviseStatistics.setPunishCount(executeLawOfSupervise4.getPunishCount());

            //6.punishMoney
            QueryWrapper<ExecuteLawOfSupervise> queryWrapper6 = new QueryWrapper();
            queryWrapper6.select("sum(punishMoney) punishMoney").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper6);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper6);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper6);
            }
            ExecuteLawOfSupervise executeLawOfSupervise5 = executeLawOfSuperviseService.getOne(queryWrapper6);
            superviseStatistics.setPunishMoney(executeLawOfSupervise5.getPunishMoney());

            //7.stopCount
            QueryWrapper<ExecuteLawOfSupervise> queryWrapper7 = new QueryWrapper();
            queryWrapper7.select("sum(stopCount) stopCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper7);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper7);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper7);
            }
            ExecuteLawOfSupervise executeLawOfSupervise6 = executeLawOfSuperviseService.getOne(queryWrapper7);
            superviseStatistics.setStopCount(executeLawOfSupervise6.getStopCount());

            //8.closeCount
            QueryWrapper<ExecuteLawOfSupervise> queryWrapper8 = new QueryWrapper();
            queryWrapper8.select("sum(closeCount) closeCount").eq("year", year);
            if (provinceCode != null) {
                queryCondition("provinceCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper8);
            }
            if (cityCode != null) {
                queryCondition("cityCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper8);
            }
            if (districtCode != null) {
                queryCondition("districtCode", provinceCode, "superviseId", "id", superviseService, queryWrapper1, queryWrapper8);
            }
            ExecuteLawOfSupervise executeLawOfSupervise7 = executeLawOfSuperviseService.getOne(queryWrapper8);
            superviseStatistics.setCloseCount(executeLawOfSupervise7.getCloseCount());

            return superviseStatistics;
        }
        return null;
    }
}
