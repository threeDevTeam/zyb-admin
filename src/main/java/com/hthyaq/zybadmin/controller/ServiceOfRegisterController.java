package com.hthyaq.zybadmin.controller;


import com.hthyaq.zybadmin.model.entity.EnterpriseOfRegister;
import com.hthyaq.zybadmin.model.entity.ServiceOfRegister;
import com.hthyaq.zybadmin.model.entity.SysUser;
import com.hthyaq.zybadmin.model.vo.ServiceOfUserView;
import com.hthyaq.zybadmin.service.EnterpriseOfRegisterService;
import com.hthyaq.zybadmin.service.ServiceOfRegisterService;
import com.hthyaq.zybadmin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-12
 */
@RestController
@RequestMapping("/serviceOfRegister")
public class ServiceOfRegisterController {
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    SysUserService sysUserService;
    @PostMapping("/add")
    public boolean add(@RequestBody ServiceOfUserView serviceOfUserView) {
        System.out.println(serviceOfUserView);

        ServiceOfRegister serviceOfRegister=new ServiceOfRegister();
        serviceOfRegister.setName(serviceOfUserView.getName());
        serviceOfRegister.setCode(serviceOfUserView.getCode());
        serviceOfRegister.setProvinceName(serviceOfUserView.getProvinceName());
        serviceOfRegister.setProvinceCode(serviceOfUserView.getProvinceCode());
        serviceOfRegister.setCityName(serviceOfUserView.getCityName());
        serviceOfRegister.setCityCode(serviceOfUserView.getCityCode());
        serviceOfRegister.setDistrictName(serviceOfUserView.getDistrictName());
        serviceOfRegister.setDistrictCode(serviceOfUserView.getDistrictCode());
        serviceOfRegister.setRegisterAddress(serviceOfUserView.getRegisterAddress());
        serviceOfRegister.setType(serviceOfUserView.getType2());
        serviceOfRegister.setEmail(serviceOfUserView.getEmail());
        serviceOfRegister.setMobile(serviceOfUserView.getMobile());
        serviceOfRegisterService.save(serviceOfRegister);
        SysUser sysUser=new SysUser();
        sysUser.setLoginName(serviceOfUserView.getLoginName());
        sysUser.setLoginPassword(serviceOfUserView.getLoginPassword());
        sysUser.setEmail(serviceOfUserView.getEmail());
        sysUser.setMobile(serviceOfUserView.getMobile());
        sysUser.setType(serviceOfUserView.getType()+"-"+serviceOfUserView.getType2());
        sysUser.setCompanyId(serviceOfRegister.getId());
        sysUserService.save(sysUser);
        return true;
    }
}
