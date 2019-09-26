package com.hthyaq.zybadmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.bean.GlobalResult;
import com.hthyaq.zybadmin.model.entity.SysUser;
import com.hthyaq.zybadmin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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
    public GlobalResult register(@RequestBody SysUser sysUserForm, HttpSession httpSession) {
        List<SysUser> list = sysUserService.list(new QueryWrapper<SysUser>().eq("loginName", sysUserForm.getLoginName()).eq("loginPassword", sysUserForm.getLoginPassword()));
        if (list.size() != 1) {
            return GlobalResult.fail();
        }
        SysUser sysUserDb = list.get(0);
        httpSession.setAttribute(GlobalConstants.LOGIN_NAME, sysUserDb);
        return GlobalResult.success("", sysUserDb);
    }
}

