package com.hthyaq.zybadmin.controller;


import com.hthyaq.zybadmin.model.entity.SysRoleMenu;
import com.hthyaq.zybadmin.model.entity.SysRoleUser;
import com.hthyaq.zybadmin.model.vo.SysRoleMenuView;
import com.hthyaq.zybadmin.model.vo.SysRoleUserView;
import com.hthyaq.zybadmin.service.SysMenuService;
import com.hthyaq.zybadmin.service.SysRoleMenuService;
import com.hthyaq.zybadmin.service.SysRoleService;
import com.hthyaq.zybadmin.service.SysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 给角色绑定用户 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-10-15
 */
@RestController
@RequestMapping("/sysRoleUser")
public class SysRoleUserController {
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    SysRoleUserService sysRoleUserService;

    @PostMapping("/add")
    public boolean add(@RequestBody SysRoleUserView sysRoleUserView, Integer id) {
        //sysRoleMenuService.save(sysRoleMenu);
        System.out.println(id);
        SysRoleUser sysRoleUser=new SysRoleUser();
        for(int i=1;i<=sysRoleUserView.getCheckbox().size();i++) {
            sysRoleUser.setRoleId(id);
            sysRoleUser.setUserId((Integer) sysRoleUserView.getCheckbox().get(i-1));
            sysRoleUserService.save(sysRoleUser);
        }
        return true;
    }
}
