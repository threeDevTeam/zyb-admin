package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.entity.ServiceOfSupervise;
import com.hthyaq.zybadmin.model.entity.ServiceSuperviseOfSupervise;
import com.hthyaq.zybadmin.service.ServiceOfSuperviseService;
import com.hthyaq.zybadmin.service.ServiceSuperviseOfSuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 检测机构监督信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/serviceSuperviseOfSupervise")
public class ServiceSuperviseOfSuperviseController {
    @Autowired
    ServiceSuperviseOfSuperviseService serviceSuperviseOfSuperviseService;
    @PostMapping("/add")
    public boolean add(@RequestBody ServiceSuperviseOfSupervise serviceSuperviseOfSupervise) {
        return serviceSuperviseOfSuperviseService.save(serviceSuperviseOfSupervise);
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return serviceSuperviseOfSuperviseService.removeById(id);
    }
    @GetMapping("/getById")
    public ServiceSuperviseOfSupervise getById(Integer id) {

        ServiceSuperviseOfSupervise serviceSuperviseOfSupervise = serviceSuperviseOfSuperviseService.getById(id);
        //demoCourse
        QueryWrapper<ServiceSuperviseOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<ServiceSuperviseOfSupervise> demoCourseList = serviceSuperviseOfSuperviseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return serviceSuperviseOfSupervise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody ServiceSuperviseOfSupervise serviceSuperviseOfSupervise) {
        return serviceSuperviseOfSuperviseService.updateById(serviceSuperviseOfSupervise);
    }
    @GetMapping("/list")
    public IPage<ServiceSuperviseOfSupervise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String username = jsonObject.getString("username");
        QueryWrapper<ServiceSuperviseOfSupervise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(username)) {
            queryWrapper.eq("username", username);
        }

        IPage<ServiceSuperviseOfSupervise> page = serviceSuperviseOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}

