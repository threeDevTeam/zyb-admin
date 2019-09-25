package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.AccidentOfSupervise;
import com.hthyaq.zybadmin.model.entity.JianceBasicOfService;
import com.hthyaq.zybadmin.model.entity.ServiceOfRegister;
import com.hthyaq.zybadmin.model.entity.SysUser;
import com.hthyaq.zybadmin.service.AccidentOfSuperviseService;
import com.hthyaq.zybadmin.service.JianceBasicOfServiceService;
import com.hthyaq.zybadmin.service.ServiceOfRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 检测机构的基本信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/jianceBasicOfService")
public class JianceBasicOfServiceController {
    @Autowired
    JianceBasicOfServiceService jianceBasicOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @PostMapping("/add")
    public boolean add(@RequestBody JianceBasicOfService jianceBasicOfService, HttpSession httpSession) {
        boolean flag=false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<ServiceOfRegister> queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",sysUser.getCompanyId());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper);
        for (ServiceOfRegister serviceOfRegister : list) {
            jianceBasicOfService.setName(serviceOfRegister.getName());
            jianceBasicOfService.setCode(serviceOfRegister.getCode());
            jianceBasicOfService.setProvinceName(serviceOfRegister.getProvinceName());
            jianceBasicOfService.setProvinceCode(serviceOfRegister.getProvinceCode());
            jianceBasicOfService.setCityName(serviceOfRegister.getCityName());
            jianceBasicOfService.setCityCode(serviceOfRegister.getCityCode());
            jianceBasicOfService.setDistrictName(serviceOfRegister.getDistrictName());
            jianceBasicOfService.setDistrictCode(serviceOfRegister.getDistrictCode());
            jianceBasicOfService.setRegisterAddress(serviceOfRegister.getRegisterAddress());
            flag=jianceBasicOfServiceService.save(jianceBasicOfService);
        }
        return flag;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return jianceBasicOfServiceService.removeById(id);
    }
    @GetMapping("/getById")
    public JianceBasicOfService getById(Integer id) {

        JianceBasicOfService jianceBasicOfService = jianceBasicOfServiceService.getById(id);
        //demoCourse
        QueryWrapper<JianceBasicOfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<JianceBasicOfService> demoCourseList = jianceBasicOfServiceService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return jianceBasicOfService;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody JianceBasicOfService jianceBasicOfService) {
        return jianceBasicOfServiceService.updateById(jianceBasicOfService);
    }

    @GetMapping("/list")
    public IPage<JianceBasicOfService> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        String code = jsonObject.getString("code");
        QueryWrapper<JianceBasicOfService> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        if (!Strings.isNullOrEmpty(code)) {
            queryWrapper.eq("code", code);
        }

        IPage<JianceBasicOfService> page = jianceBasicOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
