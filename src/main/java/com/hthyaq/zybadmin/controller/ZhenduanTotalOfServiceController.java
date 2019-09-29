package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.ExecuteLawOfSuperviseService;
import com.hthyaq.zybadmin.service.ServiceOfRegisterService;
import com.hthyaq.zybadmin.service.ZhenduanBasicOfServiceService;
import com.hthyaq.zybadmin.service.ZhenduanTotalOfServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 诊断机构的总体信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/zhenduanTotalOfService")
public class ZhenduanTotalOfServiceController {
    @Autowired
    ZhenduanTotalOfServiceService zhenduanTotalOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    ZhenduanBasicOfServiceService zhenduanBasicOfServiceService;
    @PostMapping("/add")
    public boolean add(@RequestBody ZhenduanTotalOfService zhenduanTotalOfService, HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<ServiceOfRegister> queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",sysUser.getCompanyId());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper);
        for (ServiceOfRegister serviceOfRegister : list) {
            QueryWrapper<ZhenduanBasicOfService> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", serviceOfRegister.getName());
            List<ZhenduanBasicOfService> list1 = zhenduanBasicOfServiceService.list(queryWrapper1);
            for (ZhenduanBasicOfService zhenduanBasicOfService : list1) {
                zhenduanTotalOfService.setZhenduanBasicId(zhenduanBasicOfService.getId());
                flag=zhenduanTotalOfServiceService.save(zhenduanTotalOfService);
            }
        }
        return  flag;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return zhenduanTotalOfServiceService.removeById(id);
    }
    @GetMapping("/getById")
    public ZhenduanTotalOfService getById(Integer id) {

        ZhenduanTotalOfService zhenduanTotalOfService = zhenduanTotalOfServiceService.getById(id);
        //demoCourse
        QueryWrapper<ZhenduanTotalOfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<ZhenduanTotalOfService> demoCourseList = zhenduanTotalOfServiceService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return zhenduanTotalOfService;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody ZhenduanTotalOfService zhenduanTotalOfService) {
        return zhenduanTotalOfServiceService.updateById(zhenduanTotalOfService);
    }

    @GetMapping("/list")
    public IPage<ZhenduanTotalOfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);

        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        QueryWrapper<ZhenduanTotalOfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",sysUser.getCompanyId());
        if (!Strings.isNullOrEmpty(year)) {
            queryWrapper.eq("year", year);
        }

        IPage<ZhenduanTotalOfService> page = zhenduanTotalOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}



