package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.AccidentPersonOfEnterpriseService;
import com.hthyaq.zybadmin.service.AccidentSumOfEnterpriseService;
import com.hthyaq.zybadmin.service.EnterpriseOfRegisterService;
import com.hthyaq.zybadmin.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 职业病危害事故信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/accidentSumOfEnterprise")
public class AccidentSumOfEnterpriseController {
    @Autowired
    AccidentSumOfEnterpriseService accidentSumOfEnterpriseService;
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    EnterpriseService enterpriseService;

    @PostMapping("/add")
    public boolean add(@RequestBody AccidentSumOfEnterprise accidentSumOfEnterprise, HttpSession httpSession) {
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
                accidentSumOfEnterprise.setEnterpriseId(enterprise.getId());
                flag = accidentSumOfEnterpriseService.save(accidentSumOfEnterprise);
            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return accidentSumOfEnterpriseService.removeById(id);
    }

    @GetMapping("/getById")
    public AccidentSumOfEnterprise getById(Integer id) {

        AccidentSumOfEnterprise accidentSumOfEnterprise = accidentSumOfEnterpriseService.getById(id);
        //demoCourse
        QueryWrapper<AccidentSumOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<AccidentSumOfEnterprise> demoCourseList = accidentSumOfEnterpriseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return accidentSumOfEnterprise;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody AccidentSumOfEnterprise accidentSumOfEnterprise) {
        return accidentSumOfEnterpriseService.updateById(accidentSumOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<AccidentSumOfEnterprise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String accidentNum = jsonObject.getString("accidentNum");
        QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<Enterprise> list = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list) {
            list1.clear();
            Long id = enterprise.getId();
            list1.add(id);
        }
        QueryWrapper<AccidentSumOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enterpriseId",list1.get(0));
        if (!Strings.isNullOrEmpty(accidentNum)) {
            queryWrapper.eq("accidentNum", accidentNum);
        }
        IPage<AccidentSumOfEnterprise> page = accidentSumOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
