package com.hthyaq.zybadmin.controller;


import com.hthyaq.zybadmin.model.entity.ServiceOfRegister;
import com.hthyaq.zybadmin.model.entity.SuperviseOfRegister;
import com.hthyaq.zybadmin.model.entity.SysUser;
import com.hthyaq.zybadmin.model.vo.SuperviseOfUserView;
import com.hthyaq.zybadmin.service.ServiceOfRegisterService;
import com.hthyaq.zybadmin.service.SuperviseOfRegisterService;
import com.hthyaq.zybadmin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 监管部门信息的注册表 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-12
 */
@RestController
@RequestMapping("/superviseOfRegister")
public class SuperviseOfRegisterController {
    @Autowired
    SuperviseOfRegisterService superviseOfRegisterService;
    @Autowired
    SysUserService sysUserService;
    @PostMapping("/add")
    public boolean add(@RequestBody SuperviseOfUserView superviseOfUserView) {
        System.out.println(superviseOfUserView);
        SysUser sysUser=new SysUser();
        sysUser.setLoginName(superviseOfUserView.getLoginName());
        sysUser.setLoginPassword(superviseOfUserView.getLoginPassword());
        sysUser.setEmail(superviseOfUserView.getEmail());
        sysUser.setMobile(superviseOfUserView.getMobile());
        sysUser.setType(superviseOfUserView.getType());
        sysUserService.save(sysUser);
        SuperviseOfRegister superviseOfRegister=new SuperviseOfRegister();
        superviseOfRegister.setProvinceName(superviseOfUserView.getProvinceName());
        superviseOfRegister.setProvinceCode(superviseOfUserView.getProvinceCode());
        superviseOfRegister.setCityName(superviseOfUserView.getCityName());
        superviseOfRegister.setCityCode(superviseOfUserView.getCityCode());
        superviseOfRegister.setDistrictName(superviseOfUserView.getDistrictName());
        superviseOfRegister.setDistrictCode(superviseOfUserView.getDistrictCode());
        superviseOfRegister.setRegisterAddress(superviseOfUserView.getRegisterAddress());
        superviseOfRegister.setName(superviseOfUserView.getName());
        superviseOfRegister.setEmail(superviseOfUserView.getEmail());
        superviseOfRegister.setMobile(superviseOfUserView.getMobile());
        superviseOfRegisterService.save(superviseOfRegister);
        return true;
    }
}
