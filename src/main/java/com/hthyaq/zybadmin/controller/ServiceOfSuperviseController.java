package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.entity.AccidentOfSupervise;
import com.hthyaq.zybadmin.model.entity.PropagateOfSupervise;
import com.hthyaq.zybadmin.model.entity.ServiceOfSupervise;
import com.hthyaq.zybadmin.model.entity.Supervise;
import com.hthyaq.zybadmin.service.PropagateOfSuperviseService;
import com.hthyaq.zybadmin.service.ServiceOfSuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 检测机构信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/serviceOfSupervise")
public class ServiceOfSuperviseController {
    @Autowired
    ServiceOfSuperviseService serviceOfSuperviseService;
    @PostMapping("/add")
    public boolean add(@RequestBody    ServiceOfSupervise serviceOfSupervise) {
        return serviceOfSuperviseService.save(serviceOfSupervise);
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return serviceOfSuperviseService.removeById(id);
    }

    @GetMapping("/getById")
    public ServiceOfSupervise getById(Integer id) {

        ServiceOfSupervise serviceOfSupervise = serviceOfSuperviseService.getById(id);
        //demoCourse
        QueryWrapper<ServiceOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<ServiceOfSupervise> demoCourseList = serviceOfSuperviseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return serviceOfSupervise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody ServiceOfSupervise serviceOfSupervise) {
        return serviceOfSuperviseService.editData(serviceOfSupervise);
    }






    @GetMapping("/list")
    public IPage<ServiceOfSupervise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        String jianceLevel = jsonObject.getString("jianceLevel");
        QueryWrapper<ServiceOfSupervise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(year)) {
            queryWrapper.eq("year", year);
        }
        if (!Strings.isNullOrEmpty(jianceLevel)) {
            queryWrapper.eq("jianceLevel", jianceLevel);
        }

        IPage<ServiceOfSupervise> page = serviceOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
