package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.entity.TijianDetail1OfService;
import com.hthyaq.zybadmin.model.entity.TijianTotalOfService;
import com.hthyaq.zybadmin.service.TijianDetail1OfServiceService;
import com.hthyaq.zybadmin.service.TijianTotalOfServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 体检机构的具体报告1 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/tijianDetail1OfService")
public class TijianDetail1OfServiceController {
    @Autowired
    TijianDetail1OfServiceService tijianDetail1OfServiceService;
    @PostMapping("/add")
    public boolean add(@RequestBody TijianDetail1OfService tijianDetail1OfService) {
        return tijianDetail1OfServiceService.save(tijianDetail1OfService);
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return tijianDetail1OfServiceService.removeById(id);
    }
    @GetMapping("/getById")
    public TijianDetail1OfService getById(Integer id) {

        TijianDetail1OfService tijianDetail1OfService = tijianDetail1OfServiceService.getById(id);
        //demoCourse
        QueryWrapper<TijianDetail1OfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<TijianDetail1OfService> demoCourseList = tijianDetail1OfServiceService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return tijianDetail1OfService;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody TijianDetail1OfService tijianDetail1OfService) {
        return tijianDetail1OfServiceService.updateById(tijianDetail1OfService);
    }

    @GetMapping("/list")
    public IPage<TijianDetail1OfService> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        String enterpriseName = jsonObject.getString("enterpriseName");
        QueryWrapper<TijianDetail1OfService> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        if (!Strings.isNullOrEmpty(enterpriseName)) {
            queryWrapper.eq("enterpriseName", enterpriseName);
        }
        IPage<TijianDetail1OfService> page = tijianDetail1OfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
