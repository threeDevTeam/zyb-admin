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
import com.hthyaq.zybadmin.service.TijianBasicOfServiceService;
import com.hthyaq.zybadmin.service.TijianDetail2OfServiceService;
import com.hthyaq.zybadmin.service.ZhenduanBasicOfServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 体检机构的具体报告2 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/tijianDetail2OfService")
public class TijianDetail2OfServiceController {
    @Autowired
    TijianDetail2OfServiceService tijianDetail2OfServiceService;
    @Autowired
    TijianBasicOfServiceService tijianBasicOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @PostMapping("/add")
    public boolean add(@RequestBody TijianDetail2OfService tijianDetail2OfService, HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<ServiceOfRegister> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", sysUser.getCompanyId());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper);
        for (ServiceOfRegister serviceOfRegister : list) {
            tijianDetail2OfService.setEnterpriseName(serviceOfRegister.getName());
            tijianDetail2OfService.setEnterpriseCode(serviceOfRegister.getCode());
            tijianDetail2OfService.setProvinceName(serviceOfRegister.getProvinceName());
            tijianDetail2OfService.setProvinceCode(serviceOfRegister.getProvinceCode());
            tijianDetail2OfService.setCityName(serviceOfRegister.getCityName());
            tijianDetail2OfService.setCityCode(serviceOfRegister.getCityCode());
            tijianDetail2OfService.setDistrictName(serviceOfRegister.getDistrictName());
            tijianDetail2OfService.setDistrictCode(serviceOfRegister.getDistrictCode());
            tijianDetail2OfService.setRegisterAddress(serviceOfRegister.getRegisterAddress());
            QueryWrapper<TijianBasicOfService> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", serviceOfRegister.getName());
            List<TijianBasicOfService> list1 = tijianBasicOfServiceService.list(queryWrapper1);
            for (TijianBasicOfService tijianBasicOfService : list1) {
                tijianDetail2OfService.setTijianBasicId(tijianBasicOfService.getId());
                flag=tijianDetail2OfServiceService.save(tijianDetail2OfService);
            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return tijianDetail2OfServiceService.removeById(id);
    }

    @GetMapping("/getById")
    public TijianDetail2OfService getById(Integer id) {

        TijianDetail2OfService tijianDetail2OfService = tijianDetail2OfServiceService.getById(id);
        //demoCourse
        QueryWrapper<TijianDetail2OfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<TijianDetail2OfService> demoCourseList = tijianDetail2OfServiceService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return tijianDetail2OfService;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody TijianDetail2OfService tijianDetail2OfService) {
        return tijianDetail2OfServiceService.updateById(tijianDetail2OfService);
    }

    @GetMapping("/list")
    public IPage<TijianDetail2OfService> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String enterpriseName = jsonObject.getString("enterpriseName");
        String name = jsonObject.getString("name");
        QueryWrapper<TijianDetail2OfService> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(enterpriseName)) {
            queryWrapper.eq("enterpriseName", enterpriseName);
        }
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }

        IPage<TijianDetail2OfService> page = tijianDetail2OfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
