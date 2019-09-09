package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.entity.EducationOfSupervise;
import com.hthyaq.zybadmin.model.entity.ExecuteLawOfSupervise;
import com.hthyaq.zybadmin.model.entity.ServiceSuperviseOfSupervise;
import com.hthyaq.zybadmin.model.entity.Supervise;
import com.hthyaq.zybadmin.service.EducationOfSuperviseService;
import com.hthyaq.zybadmin.service.ExecuteLawOfSuperviseService;
import com.hthyaq.zybadmin.service.ServiceSuperviseOfSuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 监督执法信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/executeLawOfSupervise")
public class ExecuteLawOfSuperviseController {
    @Autowired
    ExecuteLawOfSuperviseService ExecuteLawOfSuperviseService;
    @PostMapping("/add")
    public boolean add(@RequestBody ExecuteLawOfSupervise executeLawOfSupervise) {
        return ExecuteLawOfSuperviseService.save(executeLawOfSupervise);
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return ExecuteLawOfSuperviseService.removeById(id);
    }
    @GetMapping("/getById")
    public ExecuteLawOfSupervise getById(Integer id) {

        ExecuteLawOfSupervise executeLawOfSupervise = ExecuteLawOfSuperviseService.getById(id);
        //demoCourse
        QueryWrapper<ExecuteLawOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<ExecuteLawOfSupervise> demoCourseList = ExecuteLawOfSuperviseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return executeLawOfSupervise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody ExecuteLawOfSupervise executeLawOfSupervise) {
        return ExecuteLawOfSuperviseService.updateById(executeLawOfSupervise);
    }

    @GetMapping("/list")
    public IPage<ExecuteLawOfSupervise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        QueryWrapper<ExecuteLawOfSupervise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(year)) {
            queryWrapper.eq("year", year);
        }

        IPage<ExecuteLawOfSupervise> page = ExecuteLawOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
