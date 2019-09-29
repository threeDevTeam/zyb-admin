package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.EquipmentOfSuperviseService;
import com.hthyaq.zybadmin.service.SuperviseOfRegisterService;
import com.hthyaq.zybadmin.service.SuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    SuperviseOfRegisterService superviseOfRegisterService;
    @Autowired
    SuperviseService superviseService;
    @Autowired
    EquipmentOfSuperviseService equipmentOfSuperviseService;
    @PostMapping("/add")
    public boolean add(@RequestBody EquipmentOfSupervise educationOfSupervise, HttpSession httpSession) {
        boolean flag=false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<SuperviseOfRegister> queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",sysUser.getCompanyId());
        List<SuperviseOfRegister> list = superviseOfRegisterService.list(queryWrapper);
        for (SuperviseOfRegister superviseOfRegister : list) {
            QueryWrapper<Supervise> queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("name",superviseOfRegister.getName());
            List<Supervise> list1 = superviseService.list(queryWrapper1);
            for (Supervise supervise : list1) {
                educationOfSupervise.setSuperviseId(supervise.getId());
                flag=  equipmentOfSuperviseService.save(educationOfSupervise);
            }
        }
        return flag;
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
    public IPage<EquipmentOfSupervise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        String num = jsonObject.getString("num");
        QueryWrapper<Supervise> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<Supervise> list = superviseService.list(queryWrapper1);
        for (Supervise supervise : list) {
            list1.clear();
            Long id = supervise.getId();
            list1.add(id);
        }
        QueryWrapper<EquipmentOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("superviseId",list1.get(0));
        if (!Strings.isNullOrEmpty(name)) {

            queryWrapper.eq("name", name);
        }
        if (!Strings.isNullOrEmpty(num)) {
            queryWrapper.eq("num", num);
        }

        IPage<EquipmentOfSupervise> page = equipmentOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
