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
import com.hthyaq.zybadmin.service.PersonOfEnterpriseService;
import com.hthyaq.zybadmin.service.ProcuctionOfEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 生产工艺信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/procuctionOfEnterprise")
public class ProcuctionOfEnterpriseController {
    @Autowired
    ProcuctionOfEnterpriseService procuctionOfEnterpriseService;
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    EnterpriseService enterpriseService;
    @PostMapping("/add")
    public boolean add(@RequestBody ProcuctionOfEnterprise procuctionOfEnterprise, HttpSession httpSession) {
        System.out.println(procuctionOfEnterprise);
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<EnterpriseOfRegister> queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",sysUser.getCompanyId());
        List<EnterpriseOfRegister> list = enterpriseOfRegisterService.list(queryWrapper);
        for (EnterpriseOfRegister enterpriseOfRegister : list) {
            QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("name",enterpriseOfRegister.getName());
            List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list1) {
                procuctionOfEnterprise.setEnterpriseId(enterprise.getId());
                procuctionOfEnterpriseService.save(procuctionOfEnterprise);
            }
        }
        return true;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return procuctionOfEnterpriseService.removeById(id);
    }
    @GetMapping("/getById")
    public ProcuctionOfEnterprise getById(Integer id) {

        ProcuctionOfEnterprise procuctionOfEnterprise = procuctionOfEnterpriseService.getById(id);
        //demoCourse
        QueryWrapper<ProcuctionOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<ProcuctionOfEnterprise> demoCourseList = procuctionOfEnterpriseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return procuctionOfEnterprise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody ProcuctionOfEnterprise procuctionOfEnterprise) {
        return procuctionOfEnterpriseService.updateById(procuctionOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<ProcuctionOfEnterprise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        String productionType = jsonObject.getString("productionType");
        QueryWrapper<ProcuctionOfEnterprise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        if (!Strings.isNullOrEmpty(productionType)) {
            queryWrapper.eq("productionType", productionType);
        }
        IPage<ProcuctionOfEnterprise> page = procuctionOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
