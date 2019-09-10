package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.entity.ZhenduanBasicOfService;
import com.hthyaq.zybadmin.model.entity.ZhenduanDetailOfService;
import com.hthyaq.zybadmin.service.ZhenduanBasicOfServiceService;
import com.hthyaq.zybadmin.service.ZhenduanDetailOfServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 诊断机构的基本信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/zhenduanBasicOfService")
public class ZhenduanBasicOfServiceController {
    @Autowired
    ZhenduanBasicOfServiceService zhenduanBasicOfServiceService;

    @PostMapping("/add")
    public boolean add(@RequestBody ZhenduanBasicOfService zhenduanBasicOfService) {
        return zhenduanBasicOfServiceService.save(zhenduanBasicOfService);
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return zhenduanBasicOfServiceService.removeById(id);
    }

    @GetMapping("/getById")
    public ZhenduanBasicOfService getById(Integer id) {

        ZhenduanBasicOfService zhenduanBasicOfService = zhenduanBasicOfServiceService.getById(id);
        //demoCourse
        QueryWrapper<ZhenduanBasicOfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<ZhenduanBasicOfService> demoCourseList = zhenduanBasicOfServiceService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return zhenduanBasicOfService;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody ZhenduanBasicOfService zhenduanBasicOfService) {
        return zhenduanBasicOfServiceService.updateById(zhenduanBasicOfService);
    }

    @GetMapping("/list")
    public IPage<ZhenduanBasicOfService> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");

        QueryWrapper<ZhenduanBasicOfService> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }


        IPage<ZhenduanBasicOfService> page = zhenduanBasicOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}

