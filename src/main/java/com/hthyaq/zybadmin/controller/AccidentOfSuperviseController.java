package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.entity.AccidentOfSupervise;
import com.hthyaq.zybadmin.model.entity.ServiceSuperviseOfSupervise;
import com.hthyaq.zybadmin.model.entity.Supervise;
import com.hthyaq.zybadmin.service.AccidentOfSuperviseService;
import com.hthyaq.zybadmin.service.ServiceSuperviseOfSuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 职业病危害事故信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/accidentOfSupervise")
public class AccidentOfSuperviseController {
    @Autowired
    AccidentOfSuperviseService accidentOfSuperviseService;
    @PostMapping("/add")
    public boolean add(@RequestBody AccidentOfSupervise accidentOfSupervise) {
        return accidentOfSuperviseService.save(accidentOfSupervise);
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return accidentOfSuperviseService.removeById(id);
    }
    @GetMapping("/getById")
    public AccidentOfSupervise getById(Integer id) {

        AccidentOfSupervise accidentOfSupervise = accidentOfSuperviseService.getById(id);
        //demoCourse
        QueryWrapper<AccidentOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<AccidentOfSupervise> demoCourseList = accidentOfSuperviseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return accidentOfSupervise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody AccidentOfSupervise accidentOfSupervise) {
        return accidentOfSuperviseService.updateById(accidentOfSupervise);
    }

    @GetMapping("/list")
    public IPage<AccidentOfSupervise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String username = jsonObject.getString("username");
        QueryWrapper<AccidentOfSupervise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(username)) {
            queryWrapper.eq("username", username);
        }

        IPage<AccidentOfSupervise> page = accidentOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
