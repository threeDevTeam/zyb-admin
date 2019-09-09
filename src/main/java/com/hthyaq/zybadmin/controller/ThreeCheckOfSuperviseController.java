package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.entity.ServiceSuperviseOfSupervise;
import com.hthyaq.zybadmin.model.entity.ThreeCheckOfSupervise;
import com.hthyaq.zybadmin.service.ServiceSuperviseOfSuperviseService;
import com.hthyaq.zybadmin.service.ThreeCheckOfSuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * “三同时”监督检查信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/threeCheckOfSupervise")
public class ThreeCheckOfSuperviseController {
    @Autowired
    ThreeCheckOfSuperviseService threeCheckOfSuperviseService;
    @PostMapping("/add")
    public boolean add(@RequestBody ThreeCheckOfSupervise threeCheckOfSupervise) {
        return threeCheckOfSuperviseService.save(threeCheckOfSupervise);
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return threeCheckOfSuperviseService.removeById(id);
    }
    @GetMapping("/getById")
    public ThreeCheckOfSupervise getById(Integer id) {

        ThreeCheckOfSupervise threeCheckOfSupervise = threeCheckOfSuperviseService.getById(id);
        //demoCourse
        QueryWrapper<ThreeCheckOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<ThreeCheckOfSupervise> demoCourseList = threeCheckOfSuperviseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return threeCheckOfSupervise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody ThreeCheckOfSupervise threeCheckOfSupervise) {
        return threeCheckOfSuperviseService.updateById(threeCheckOfSupervise);
    }
    @GetMapping("/list")
    public IPage<ThreeCheckOfSupervise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        String pulishMoney = jsonObject.getString("pulishMoney");
        QueryWrapper<ThreeCheckOfSupervise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(year)) {
            queryWrapper.eq("year", year);
        }
        if (!Strings.isNullOrEmpty(pulishMoney)) {
            queryWrapper.eq("pulishMoney", pulishMoney);
        }


        IPage<ThreeCheckOfSupervise> page = threeCheckOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
