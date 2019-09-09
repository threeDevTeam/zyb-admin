package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.entity.EducationOfSupervise;
import com.hthyaq.zybadmin.model.entity.PropagateOfSupervise;
import com.hthyaq.zybadmin.service.EducationOfSuperviseService;
import com.hthyaq.zybadmin.service.PropagateOfSuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 职业健康宣传信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/propagateOfSupervise")
public class PropagateOfSuperviseController {
    @Autowired
    PropagateOfSuperviseService propagateOfSuperviseService;
    @PostMapping("/add")
    public boolean add(@RequestBody PropagateOfSupervise propagateOfSupervise) {
        return propagateOfSuperviseService.save(propagateOfSupervise);
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return propagateOfSuperviseService.removeById(id);
    }
    @GetMapping("/getById")
    public PropagateOfSupervise getById(Integer id) {

        PropagateOfSupervise propagateOfSupervise = propagateOfSuperviseService.getById(id);
        //demoCourse
        QueryWrapper<PropagateOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<PropagateOfSupervise> demoCourseList = propagateOfSuperviseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return propagateOfSupervise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody PropagateOfSupervise propagateOfSupervise) {
        return propagateOfSuperviseService.updateById(propagateOfSupervise);
    }
    @GetMapping("/list")
    public IPage<PropagateOfSupervise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        String acceptCount = jsonObject.getString("acceptCount");
        QueryWrapper<PropagateOfSupervise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(year)) {
            queryWrapper.eq("year", year);
        }
        if (!Strings.isNullOrEmpty(acceptCount)) {
            queryWrapper.eq("acceptCount", acceptCount);
        }

        IPage<PropagateOfSupervise> page = propagateOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
