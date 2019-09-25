package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.EnterpriseOfRegisterService;
import com.hthyaq.zybadmin.service.EnterpriseService;
import com.hthyaq.zybadmin.service.HealthOfEnterpriseService;
import com.hthyaq.zybadmin.service.ProtectOfEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 职业卫生管理信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/healthOfEnterprise")
public class HealthOfEnterpriseController {
    @Autowired
    HealthOfEnterpriseService healthOfEnterpriseService;
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    EnterpriseService enterpriseService;

    @PostMapping("/add")
    public boolean add(@RequestBody HealthOfEnterprise healthOfEnterprise, HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<EnterpriseOfRegister> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", sysUser.getCompanyId());
        List<EnterpriseOfRegister> list = enterpriseOfRegisterService.list(queryWrapper);
        for (EnterpriseOfRegister enterpriseOfRegister : list) {
            QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", enterpriseOfRegister.getName());
            List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list1) {
                healthOfEnterprise.setEnterpriseId(enterprise.getId());
                flag = healthOfEnterpriseService.save(healthOfEnterprise);
            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return healthOfEnterpriseService.removeById(id);
    }

    @GetMapping("/getById")
    public HealthOfEnterprise getById(Integer id) {

        HealthOfEnterprise healthOfEnterprise = healthOfEnterpriseService.getById(id);
        //demoCourse
        QueryWrapper<HealthOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<HealthOfEnterprise> demoCourseList = healthOfEnterpriseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return healthOfEnterprise;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody HealthOfEnterprise healthOfEnterprise) {
        return healthOfEnterpriseService.updateById(healthOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<HealthOfEnterprise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String isA = jsonObject.getString("isA");
        QueryWrapper<HealthOfEnterprise> queryWrapper = new QueryWrapper<>();

        if (!Strings.isNullOrEmpty(isA)) {
            queryWrapper.eq("isA", isA);
        }
        IPage<HealthOfEnterprise> page = healthOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
