package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.entity.PersonOfSupervise;
import com.hthyaq.zybadmin.model.entity.Supervise;
import com.hthyaq.zybadmin.service.PersonOfSuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 监管人员信息








 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/personOfSupervise")
public class PersonOfSuperviseController {
    @Autowired
    PersonOfSuperviseService personOfSuperviseService;
    @PostMapping("/add")
    public boolean add(@RequestBody PersonOfSupervise personOfSupervise) {
        return personOfSuperviseService.save(personOfSupervise);
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return personOfSuperviseService.removeById(id);
    }
    @GetMapping("/getById")
    public PersonOfSupervise getById(Integer id) {

        PersonOfSupervise personOfSupervise = personOfSuperviseService.getById(id);
        //demoCourse
        QueryWrapper<PersonOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<PersonOfSupervise> demoCourseList = personOfSuperviseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return personOfSupervise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody PersonOfSupervise personOfSupervise) {
        return personOfSuperviseService.updateById(personOfSupervise);
    }

    @GetMapping("/list")
    public IPage<PersonOfSupervise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String username = jsonObject.getString("username");
        QueryWrapper<PersonOfSupervise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(username)) {
            queryWrapper.eq("username", username);
        }

        IPage<PersonOfSupervise> page = personOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
