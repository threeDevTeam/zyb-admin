package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * “三同时”监督检查信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/threeCheckOfSupervise")
public class ThreeCheckOfSuperviseController {
    @Autowired
    SuperviseOfRegisterService superviseOfRegisterService;
    @Autowired
    SuperviseService superviseService;
    @Autowired
    ThreeCheckOfSuperviseService threeCheckOfSuperviseService;
    @PostMapping("/add")
    public boolean add(@RequestBody ThreeCheckOfSupervise threeCheckOfSupervise, HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<SuperviseOfRegister> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", sysUser.getCompanyId());
        List<SuperviseOfRegister> list = superviseOfRegisterService.list(queryWrapper);
        for (SuperviseOfRegister superviseOfRegister : list) {
            QueryWrapper<Supervise> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", superviseOfRegister.getName());
            List<Supervise> list1 = superviseService.list(queryWrapper1);
            for (Supervise supervise : list1) {
                threeCheckOfSupervise.setSuperviseId(supervise.getId());
                flag = threeCheckOfSuperviseService.save(threeCheckOfSupervise);
            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return threeCheckOfSuperviseService.removeById(id);
    }
    @GetMapping("/getById")
    public ThreeCheckOfSupervise getById(Integer id) {

        ThreeCheckOfSupervise threeCheckOfSupervise = threeCheckOfSuperviseService.getById(id);
        //demoCourse
        QueryWrapper<ThreeCheckOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<ThreeCheckOfSupervise> demoCourseList = threeCheckOfSuperviseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return threeCheckOfSupervise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody ThreeCheckOfSupervise threeCheckOfSupervise) {
        return threeCheckOfSuperviseService.updateById(threeCheckOfSupervise);
    }
    @GetMapping("/list")
    public IPage<ThreeCheckOfSupervise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        String pulishMoney = jsonObject.getString("pulishMoney");
        QueryWrapper<Supervise> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<Supervise> list = superviseService.list(queryWrapper1);
        for (Supervise supervise : list) {
            list1.clear();
            Long id = supervise.getId();
            list1.add(id);
        }
        QueryWrapper<ThreeCheckOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("superviseId",list1.get(0));
        if (!Strings.isNullOrEmpty(year)) {
            queryWrapper.eq("year", year);
        }
        if (!Strings.isNullOrEmpty(pulishMoney)) {
            queryWrapper.eq("pulishMoney", pulishMoney);
        }


        IPage<ThreeCheckOfSupervise> page = threeCheckOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
