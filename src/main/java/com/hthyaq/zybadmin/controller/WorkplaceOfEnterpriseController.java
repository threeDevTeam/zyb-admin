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
import com.hthyaq.zybadmin.service.ProcuctionOfEnterpriseService;
import com.hthyaq.zybadmin.service.WorkplaceOfEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 工作场所 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/workplaceOfEnterprise")
public class WorkplaceOfEnterpriseController {
    @Autowired
    WorkplaceOfEnterpriseService workplaceOfEnterpriseService;
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    EnterpriseService enterpriseService;
    @PostMapping("/add")
    public boolean add(@RequestBody WorkplaceOfEnterprise workplaceOfEnterprise, HttpSession httpSession) {
        boolean flag=false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<EnterpriseOfRegister> queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",sysUser.getCompanyId());
        List<EnterpriseOfRegister> list = enterpriseOfRegisterService.list(queryWrapper);
        for (EnterpriseOfRegister enterpriseOfRegister : list) {
            QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("name",enterpriseOfRegister.getName());
            List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list1) {
                workplaceOfEnterprise.setEnterpriseId(enterprise.getId());
                flag=  workplaceOfEnterpriseService.save(workplaceOfEnterprise);
            }
        }
        return flag;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return workplaceOfEnterpriseService.removeById(id);
    }
    @GetMapping("/getById")
    public WorkplaceOfEnterprise getById(Integer id) {

        WorkplaceOfEnterprise workplaceOfEnterprise = workplaceOfEnterpriseService.getById(id);
        //demoCourse
        QueryWrapper<WorkplaceOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<WorkplaceOfEnterprise> demoCourseList = workplaceOfEnterpriseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return workplaceOfEnterprise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody WorkplaceOfEnterprise workplaceOfEnterprise) {
        return workplaceOfEnterpriseService.updateById(workplaceOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<WorkplaceOfEnterprise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String username = jsonObject.getString("username");
        QueryWrapper<WorkplaceOfEnterprise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(username)) {
            queryWrapper.eq("username", username);
        }
        IPage<WorkplaceOfEnterprise> page = workplaceOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
