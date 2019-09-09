package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.entity.EquipmentOfSupervise;
import com.hthyaq.zybadmin.model.entity.PersonOfSupervise;
import com.hthyaq.zybadmin.model.entity.Supervise;
import com.hthyaq.zybadmin.service.EquipmentOfSuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 监管装备信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/equipmentOfSupervise")
public class EquipmentOfSuperviseController {
    @Autowired
    EquipmentOfSuperviseService equipmentOfSuperviseService;
    @PostMapping("/add")
    public boolean add(@RequestBody EquipmentOfSupervise educationOfSupervise) {
        return equipmentOfSuperviseService.save(educationOfSupervise);
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return equipmentOfSuperviseService.removeById(id);
    }
    @GetMapping("/getById")
    public EquipmentOfSupervise getById(Integer id) {

        EquipmentOfSupervise educationOfSupervise = equipmentOfSuperviseService.getById(id);
        //demoCourse
        QueryWrapper<EquipmentOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<EquipmentOfSupervise> demoCourseList = equipmentOfSuperviseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return educationOfSupervise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody EquipmentOfSupervise equipmentOfSupervise) {
        return equipmentOfSuperviseService.updateById(equipmentOfSupervise);
    }

    @GetMapping("/list")
    public IPage<EquipmentOfSupervise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String username = jsonObject.getString("username");
        QueryWrapper<EquipmentOfSupervise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(username)) {
            queryWrapper.eq("username", username);
        }

        IPage<EquipmentOfSupervise> page = equipmentOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
