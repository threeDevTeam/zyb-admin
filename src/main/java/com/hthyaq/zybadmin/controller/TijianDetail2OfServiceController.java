package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.entity.TijianDetail2OfService;
import com.hthyaq.zybadmin.model.entity.ZhenduanBasicOfService;
import com.hthyaq.zybadmin.service.TijianDetail2OfServiceService;
import com.hthyaq.zybadmin.service.ZhenduanBasicOfServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 体检机构的具体报告2 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/tijianDetail2OfService")
public class TijianDetail2OfServiceController {
    @Autowired
    TijianDetail2OfServiceService tijianDetail2OfServiceService;

    @PostMapping("/add")
    public boolean add(@RequestBody TijianDetail2OfService tijianDetail2OfService) {
        return tijianDetail2OfServiceService.save(tijianDetail2OfService);
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return tijianDetail2OfServiceService.removeById(id);
    }

    @GetMapping("/getById")
    public TijianDetail2OfService getById(Integer id) {

        TijianDetail2OfService tijianDetail2OfService = tijianDetail2OfServiceService.getById(id);
        //demoCourse
        QueryWrapper<TijianDetail2OfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<TijianDetail2OfService> demoCourseList = tijianDetail2OfServiceService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return tijianDetail2OfService;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody TijianDetail2OfService tijianDetail2OfService) {
        return tijianDetail2OfServiceService.updateById(tijianDetail2OfService);
    }

    @GetMapping("/list")
    public IPage<TijianDetail2OfService> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String enterpriseName = jsonObject.getString("enterpriseName");
        String name = jsonObject.getString("name");
        QueryWrapper<TijianDetail2OfService> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(enterpriseName)) {
            queryWrapper.eq("enterpriseName", enterpriseName);
        }
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }

        IPage<TijianDetail2OfService> page = tijianDetail2OfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
