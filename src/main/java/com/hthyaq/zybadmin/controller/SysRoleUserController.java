package com.hthyaq.zybadmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.SysRoleMenu;
import com.hthyaq.zybadmin.model.entity.SysRoleUser;
import com.hthyaq.zybadmin.model.vo.SysRoleMenuView;
import com.hthyaq.zybadmin.model.vo.SysRoleUserView;
import com.hthyaq.zybadmin.service.SysMenuService;
import com.hthyaq.zybadmin.service.SysRoleMenuService;
import com.hthyaq.zybadmin.service.SysRoleService;
import com.hthyaq.zybadmin.service.SysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

        SysRoleUser sysRoleUser=new SysRoleUser();
        QueryWrapper<SysRoleUser> queryWrapper=new QueryWrapper<>();
        sysRoleUserService.remove(queryWrapper.eq("roleId",id));
        for(int i=1;i<=sysRoleUserView.getCheckbox().size();i++) {
            sysRoleUser.setRoleId(id);
            sysRoleUser.setUserId((Integer) sysRoleUserView.getCheckbox().get(i-1));
            sysRoleUserService.save(sysRoleUser);
        }
        return true;
    }
    @GetMapping("/getById")
    public List<SysRoleUser> getById(Integer id) {
        //demo
        List list1=new ArrayList();
        QueryWrapper<SysRoleUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("roleId",id);
        List<SysRoleUser> list = sysRoleUserService.list(queryWrapper);
        for (SysRoleUser sysRoleUser : list) {
            list1.add(sysRoleUser.getUserId());
        }
        return list1;
    }
}
