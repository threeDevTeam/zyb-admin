package com.hthyaq.zybadmin.controller;


import com.hthyaq.zybadmin.model.entity.AccidentOfSupervise;
import com.hthyaq.zybadmin.model.entity.EnterpriseOfRegister;
import com.hthyaq.zybadmin.model.entity.SysUser;
import com.hthyaq.zybadmin.model.vo.EnterpriseUserView;
import com.hthyaq.zybadmin.service.AccidentOfSuperviseService;
import com.hthyaq.zybadmin.service.EnterpriseOfRegisterService;
import com.hthyaq.zybadmin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 * <p>
 * 企业的注册表 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-12
 */
@RestController
@RequestMapping("/enterpriseOfRegister")
public class EnterpriseOfRegisterController {
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    SysUserService sysUserService;
    @PostMapping("/add")
    public boolean add(@RequestBody EnterpriseUserView enterpriseUserView) {
        System.out.println(enterpriseUserView);
        SysUser sysUser=new SysUser();
        sysUser.setLoginName(enterpriseUserView.getLoginName());
        sysUser.setLoginPassword(enterpriseUserView.getLoginPassword());
        sysUser.setEmail(enterpriseUserView.getEmail());
        sysUser.setMobile(enterpriseUserView.getMobile());
        sysUser.setType(enterpriseUserView.getType());
        sysUserService.save(sysUser);
        EnterpriseOfRegister enterpriseOfRegister=new EnterpriseOfRegister();
        enterpriseOfRegister.setName(enterpriseUserView.getName());
        enterpriseOfRegister.setCode(enterpriseUserView.getCode());
        enterpriseOfRegister.setProvinceName(enterpriseUserView.getProvinceName());
        enterpriseOfRegister.setProvinceCode(enterpriseUserView.getProvinceCode());
        enterpriseOfRegister.setCityName(enterpriseUserView.getCityName());
        enterpriseOfRegister.setCityCode(enterpriseUserView.getCityCode());
        enterpriseOfRegister.setDistrictName(enterpriseUserView.getDistrictName());
        enterpriseOfRegister.setDistrictCode(enterpriseUserView.getDistrictCode());
        enterpriseOfRegister.setProductionCapacity(enterpriseUserView.getProductionCapacity());
        enterpriseOfRegister.setUnitType(enterpriseUserView.getUnitType());
        enterpriseOfRegister.setRegiterMoney(enterpriseUserView.getRegiterMoney());
        enterpriseOfRegister.setRegisterAddress(enterpriseUserView.getRegisterAddress());
        enterpriseOfRegister.setRegisterDate(enterpriseUserView.getRegisterDate());
        enterpriseOfRegister.setStartDate(enterpriseUserView.getStartDate());
        enterpriseOfRegister.setPropertyMoney(enterpriseUserView.getPropertyMoney());
        enterpriseOfRegister.setEmail(enterpriseUserView.getEmail());
        enterpriseOfRegister.setMobile(enterpriseUserView.getMobile());
        enterpriseOfRegisterService.save(enterpriseOfRegister);
        return true;
    }
}
