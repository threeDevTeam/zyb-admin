package com.hthyaq.zybadmin.controller;


import com.hthyaq.zybadmin.model.entity.EnterpriseOfRegister;
import com.hthyaq.zybadmin.model.entity.SysUser;
import com.hthyaq.zybadmin.service.EnterpriseOfRegisterService;
import com.hthyaq.zybadmin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-12
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    SysUserService sysUserService;

    @PostMapping("/add")
    public boolean add(@RequestBody SysUser sysUser) {
        sysUserService.save(sysUser);
        return true;
    }

    @PostMapping("/register")
    public boolean register(@RequestBody SysUser sysUser) {
        System.out.println(sysUser.getLoginName());
        System.out.println(sysUser.getLoginPassword());
        List<SysUser> list = sysUserService.list();
        boolean flag = false;
        for (SysUser user : list) {
            if (sysUser.getLoginName().equals(user.getLoginName())
                    && sysUser.getLoginPassword().equals(user.getLoginPassword())
            ) {
                flag=true;
            }
        }
        return flag;
    }
}
