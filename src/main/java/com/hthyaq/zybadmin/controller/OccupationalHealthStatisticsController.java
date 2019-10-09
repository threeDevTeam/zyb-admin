package com.hthyaq.zybadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.Enterprise;
import com.hthyaq.zybadmin.model.entity.OccupationalHealthStatistics;
import com.hthyaq.zybadmin.model.entity.ResourceStatistics;
import com.hthyaq.zybadmin.model.entity.ThreeCheckOfSupervise;
import com.hthyaq.zybadmin.service.EnterpriseService;
import com.hthyaq.zybadmin.service.ThreeCheckOfSuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



//职业卫生“三同时”监督执法统计分析表
@RestController
@RequestMapping("/occupationalHealthStatistics")
public class OccupationalHealthStatisticsController {
    @Autowired
    ThreeCheckOfSuperviseService threeCheckOfSuperviseService;

    @GetMapping("/list")
    public OccupationalHealthStatistics list(Integer year, String provinceCode, String cityCode, String districtCode) {
        if (year != null) {
            OccupationalHealthStatistics occupationalHealthStatistics = new OccupationalHealthStatistics();
            //	检查建设单位数
            QueryWrapper<ThreeCheckOfSupervise> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("sum(orgCount) orgCount");
            queryWrapper.eq("year", year);
            ThreeCheckOfSupervise threeCheckOfSupervise = threeCheckOfSuperviseService.getOne(queryWrapper);
            occupationalHealthStatistics.setOrgCount(threeCheckOfSupervise.getOrgCount());

            //下达执法文书数
            QueryWrapper<ThreeCheckOfSupervise> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.select("sum(paperCount) paperCount");
            queryWrapper.eq("year", year);
            ThreeCheckOfSupervise threeCheckOfSupervise1 = threeCheckOfSuperviseService.getOne(queryWrapper1);
            occupationalHealthStatistics.setPaperCount(threeCheckOfSupervise1.getPaperCount());

            //给予警告责令限期整改单位数
            QueryWrapper<ThreeCheckOfSupervise> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.select("sum(changeCount) changeCount");
            queryWrapper.eq("year", year);
            ThreeCheckOfSupervise threeCheckOfSupervise2 = threeCheckOfSuperviseService.getOne(queryWrapper2);
            occupationalHealthStatistics.setChangeCount(threeCheckOfSupervise2.getChangeCount());

            //罚款建设单位数
            QueryWrapper<ThreeCheckOfSupervise> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.select("sum(pulishCount) pulishCount");
            queryWrapper.eq("year", year);
            ThreeCheckOfSupervise threeCheckOfSupervise3 = threeCheckOfSuperviseService.getOne(queryWrapper3);
            occupationalHealthStatistics.setPulishCount(threeCheckOfSupervise3.getPulishCount());

            //	罚款金额
            QueryWrapper<ThreeCheckOfSupervise> queryWrapper4 = new QueryWrapper<>();
            queryWrapper4.select("sum(pulishMoney) pulishMoney");
            queryWrapper.eq("year", year);
            ThreeCheckOfSupervise threeCheckOfSupervise4 = threeCheckOfSuperviseService.getOne(queryWrapper4);
            occupationalHealthStatistics.setPunishMoney(threeCheckOfSupervise4.getPulishMoney());

            //责令停止产生职业病危害作业单位数
            QueryWrapper<ThreeCheckOfSupervise> queryWrapper5 = new QueryWrapper<>();
            queryWrapper5.select("sum(stopCount) stopCount");
            queryWrapper.eq("year", year);
            ThreeCheckOfSupervise threeCheckOfSupervise5 = threeCheckOfSuperviseService.getOne(queryWrapper5);
            occupationalHealthStatistics.setStopCount(threeCheckOfSupervise5.getStopCount());


            //提请责令停建或关闭单位数
            QueryWrapper<ThreeCheckOfSupervise> queryWrapper6 = new QueryWrapper<>();
            queryWrapper6.select("sum(closeCount) closeCount");
            queryWrapper.eq("year", year);
            ThreeCheckOfSupervise threeCheckOfSupervise6 = threeCheckOfSuperviseService.getOne(queryWrapper6);
            occupationalHealthStatistics.setCloseCount(threeCheckOfSupervise6.getCloseCount());

            return occupationalHealthStatistics;
        }
        return null;
    }
}
