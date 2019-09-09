package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.entity.EducationOfSupervise;
import com.hthyaq.zybadmin.model.entity.Supervise;
import com.hthyaq.zybadmin.service.EducationOfSuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 教育培训情况 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/educationOfSupervise")
public class EducationOfSuperviseController {
    @Autowired
    EducationOfSuperviseService educationOfSuperviseService;
    @PostMapping("/add")
    public boolean add(@RequestBody EducationOfSupervise educationOfSupervise) {
        return educationOfSuperviseService.save(educationOfSupervise);
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return educationOfSuperviseService.removeById(id);
    }
    @GetMapping("/getById")
    public EducationOfSupervise getById(Integer id) {

        EducationOfSupervise educationOfSupervise = educationOfSuperviseService.getById(id);
        //demoCourse
        QueryWrapper<EducationOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<EducationOfSupervise> demoCourseList = educationOfSuperviseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return educationOfSupervise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody EducationOfSupervise educationOfSupervise) {
        return educationOfSuperviseService.updateById(educationOfSupervise);
    }

    @GetMapping("/list")
    public IPage<EducationOfSupervise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        String personCount = jsonObject.getString("personCount");
        QueryWrapper<EducationOfSupervise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(year)) {
            queryWrapper.eq("year", year);
        }
        if (!Strings.isNullOrEmpty(personCount)) {
            queryWrapper.eq("personCount", personCount);
        }

        IPage<EducationOfSupervise> page = educationOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
