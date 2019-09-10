package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.entity.JianceTotalOfService;
import com.hthyaq.zybadmin.model.entity.TijianBasicOfService;
import com.hthyaq.zybadmin.service.JianceTotalOfServiceService;
import com.hthyaq.zybadmin.service.TijianBasicOfServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 体检机构的基本信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/tijianBasicOfService")
public class TijianBasicOfServiceController {
    @Autowired
    TijianBasicOfServiceService tijianBasicOfServiceService;
    @PostMapping("/add")
    public boolean add(@RequestBody TijianBasicOfService tijianBasicOfService) {
        return tijianBasicOfServiceService.save(tijianBasicOfService);
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return tijianBasicOfServiceService.removeById(id);
    }
    @GetMapping("/getById")
    public TijianBasicOfService getById(Integer id) {

        TijianBasicOfService tijianBasicOfService = tijianBasicOfServiceService.getById(id);
        //demoCourse
        QueryWrapper<TijianBasicOfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<TijianBasicOfService> demoCourseList = tijianBasicOfServiceService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return tijianBasicOfService;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody TijianBasicOfService tijianBasicOfService) {
        return tijianBasicOfServiceService.updateById(tijianBasicOfService);
    }

    @GetMapping("/list")
    public IPage<TijianBasicOfService> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        String hospitalLevel = jsonObject.getString("hospitalLevel");
        QueryWrapper<TijianBasicOfService> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        if (!Strings.isNullOrEmpty(hospitalLevel)) {
            queryWrapper.eq("hospitalLevel", hospitalLevel);
        }
        IPage<TijianBasicOfService> page = tijianBasicOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
