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
@RequestMapping("/enterprise")
public class EnterpriseController {
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @PostMapping("/add")
    public boolean add(@RequestBody  Enterprise enterprise, HttpSession httpSession) {
        boolean flag=false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<EnterpriseOfRegister> queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",sysUser.getCompanyId());
        List<EnterpriseOfRegister> list = enterpriseOfRegisterService.list(queryWrapper);
        for (EnterpriseOfRegister enterpriseOfRegister : list) {
            enterprise.setName(enterpriseOfRegister.getName());
            enterprise.setCode(enterpriseOfRegister.getCode());
            enterprise.setProvinceName(enterpriseOfRegister.getProvinceName());
            enterprise.setProvinceCode(enterpriseOfRegister.getProvinceCode());
            enterprise.setCityName(enterpriseOfRegister.getCityName());
            enterprise.setCityCode(enterpriseOfRegister.getCityCode());
            enterprise.setDistrictName(enterpriseOfRegister.getDistrictName());
            enterprise.setDistrictCode(enterpriseOfRegister.getDistrictCode());
            enterprise.setProductionCapacity(Integer.parseInt(enterpriseOfRegister.getProductionCapacity()));
            enterprise.setUnitType(enterpriseOfRegister.getUnitType());
            enterprise.setRegiterMoney(Double.parseDouble(enterpriseOfRegister.getRegiterMoney()));
            enterprise.setRegisterAddress(enterpriseOfRegister.getRegisterAddress());
            enterprise.setRegisterDate(Integer.parseInt(enterpriseOfRegister.getRegisterDate()));
            enterprise.setStartDate(Integer.parseInt(enterpriseOfRegister.getStartDate()));
            enterprise.setPropertyMoney(Double.parseDouble(enterpriseOfRegister.getPropertyMoney()));

            flag=enterpriseService.save(enterprise);
        }
        return flag;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return enterpriseService.removeById(id);
    }
    @GetMapping("/getById")
    public Enterprise getById(Integer id) {

        Enterprise enterprise = enterpriseService.getById(id);
        //demoCourse
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<Enterprise> demoCourseList = enterpriseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return enterprise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody Enterprise enterprise) {
        return enterpriseService.updateById(enterprise);
    }

    @GetMapping("/list")
    public IPage<Enterprise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        IPage<Enterprise> page = enterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
