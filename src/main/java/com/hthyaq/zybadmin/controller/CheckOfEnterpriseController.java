package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.CheckOfEnterpriseService;
import com.hthyaq.zybadmin.service.EnterpriseCheckSumOfEnterpriseService;
import com.hthyaq.zybadmin.service.EnterpriseOfRegisterService;
import com.hthyaq.zybadmin.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 监督检查信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/checkOfEnterprise")
public class CheckOfEnterpriseController {
    @Autowired
    CheckOfEnterpriseService checkOfEnterpriseService;
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    EnterpriseService enterpriseService;

    @PostMapping("/add")
    public boolean add(@RequestBody CheckOfEnterprise CheckOfEnterprise, HttpSession httpSession) {
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
                CheckOfEnterprise.setEnterpriseId(enterprise.getId());
                flag = checkOfEnterpriseService.save(CheckOfEnterprise);
            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return checkOfEnterpriseService.removeById(id);
    }

    @GetMapping("/getById")
    public CheckOfEnterprise getById(Integer id) {

        CheckOfEnterprise checkOfEnterprise = checkOfEnterpriseService.getById(id);
        //demoCourse
        QueryWrapper<CheckOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<CheckOfEnterprise> demoCourseList = checkOfEnterpriseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return checkOfEnterprise;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody CheckOfEnterprise checkOfEnterprise) {
        return checkOfEnterpriseService.updateById(checkOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<CheckOfEnterprise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String org = jsonObject.getString("org");
        QueryWrapper<CheckOfEnterprise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(org)) {
            queryWrapper.eq("org", org);
        }
        IPage<CheckOfEnterprise> page = checkOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
