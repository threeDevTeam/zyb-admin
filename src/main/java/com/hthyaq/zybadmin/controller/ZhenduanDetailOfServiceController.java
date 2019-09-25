package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.ServiceOfRegisterService;
import com.hthyaq.zybadmin.service.ZhenduanBasicOfServiceService;
import com.hthyaq.zybadmin.service.ZhenduanDetailOfServiceService;
import com.hthyaq.zybadmin.service.ZhenduanTotalOfServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 诊断机构的具体报告 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/zhenduanDetailOfService")
public class ZhenduanDetailOfServiceController {
    @Autowired
    ZhenduanDetailOfServiceService zhenduanDetailOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    ZhenduanBasicOfServiceService zhenduanBasicOfServiceService;
    @PostMapping("/add")
    public boolean add(@RequestBody ZhenduanDetailOfService zhenduanDetailOfService, HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<ServiceOfRegister> queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",sysUser.getCompanyId());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper);
        for (ServiceOfRegister serviceOfRegister : list) {
            zhenduanDetailOfService.setEnterpriseName(serviceOfRegister.getName());
            zhenduanDetailOfService.setEnterpriseCode(serviceOfRegister.getCode());
            zhenduanDetailOfService.setProvinceName(serviceOfRegister.getProvinceName());
            zhenduanDetailOfService.setProvinceCode(serviceOfRegister.getProvinceCode());
            zhenduanDetailOfService.setCityName(serviceOfRegister.getCityName());
            zhenduanDetailOfService.setCityCode(serviceOfRegister.getCityCode());
            zhenduanDetailOfService.setDistrictName(serviceOfRegister.getDistrictName());
            zhenduanDetailOfService.setDistrictCode(serviceOfRegister.getDistrictCode());
            zhenduanDetailOfService.setRegisterAddress(serviceOfRegister.getRegisterAddress());
            QueryWrapper<ZhenduanBasicOfService> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", serviceOfRegister.getName());
            List<ZhenduanBasicOfService> list1 = zhenduanBasicOfServiceService.list(queryWrapper1);
            for (ZhenduanBasicOfService zhenduanBasicOfService : list1) {
                zhenduanDetailOfService.setZhenduanBasicId(zhenduanBasicOfService.getId());
                flag=zhenduanDetailOfServiceService.save(zhenduanDetailOfService);
            }
        }
        return  flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return zhenduanDetailOfServiceService.removeById(id);
    }

    @GetMapping("/getById")
    public ZhenduanDetailOfService getById(Integer id) {

        ZhenduanDetailOfService zhenduanDetailOfService = zhenduanDetailOfServiceService.getById(id);
        //demoCourse
        QueryWrapper<ZhenduanDetailOfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<ZhenduanDetailOfService> demoCourseList = zhenduanDetailOfServiceService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return zhenduanDetailOfService;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody ZhenduanDetailOfService zhenduanDetailOfService) {
        return zhenduanDetailOfServiceService.updateById(zhenduanDetailOfService);
    }

    @GetMapping("/list")
    public IPage<ZhenduanDetailOfService> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String enterpriseName = jsonObject.getString("enterpriseName");
        String name = jsonObject.getString("name");
        QueryWrapper<ZhenduanDetailOfService> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(enterpriseName)) {
            queryWrapper.eq("enterpriseName", enterpriseName);
        }
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }

        IPage<ZhenduanDetailOfService> page = zhenduanDetailOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}

