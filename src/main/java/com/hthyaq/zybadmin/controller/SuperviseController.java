package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.entity.Supervise;
import com.hthyaq.zybadmin.model.vo.DemoView;
import com.hthyaq.zybadmin.service.SuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 监管部门信息












 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/supervise")

public class SuperviseController {
    @Autowired
    SuperviseService superviseService;
    @PostMapping("/add")
    public boolean add(@RequestBody Supervise supervise) {
        return superviseService.saveData(supervise);
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return superviseService.deleteData(id);
    }
    @GetMapping("/getById")
    public Supervise getById(Integer id) {

        Supervise supervise = superviseService.getById(id);
        //demoCourse
        QueryWrapper<Supervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<Supervise> demoCourseList = superviseService.list(queryWrapper);
        return supervise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody Supervise supervise) {
        return superviseService.editData(supervise);
    }












    @GetMapping("/list")
    public IPage<Supervise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        String name = jsonObject.getString("name");
        QueryWrapper<Supervise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(year)) {
            queryWrapper.eq("year", year);
        }
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }

        IPage<Supervise> page = superviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
