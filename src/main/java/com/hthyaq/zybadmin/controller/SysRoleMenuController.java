package com.hthyaq.zybadmin.controller;


import com.hthyaq.zybadmin.model.entity.SysRole;
import com.hthyaq.zybadmin.model.entity.SysRoleMenu;
import com.hthyaq.zybadmin.model.vo.SysRoleMenuView;
import com.hthyaq.zybadmin.service.SysMenuService;
import com.hthyaq.zybadmin.service.SysRoleMenuService;
import com.hthyaq.zybadmin.service.SysRoleService;
import com.hthyaq.zybadmin.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 给角色分配菜单 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-10-15
 */
@RestController
@RequestMapping("/sysRoleMenu")
public class SysRoleMenuController {
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    SysRoleMenuService sysRoleMenuService;

    @PostMapping("/add")
    public boolean add(@RequestBody SysRoleMenuView sysRoleMenuView, Integer id) {
        //sysRoleMenuService.save(sysRoleMenu);
        System.out.println(id);
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        if (sysRoleMenuView.getCheckbox() != null) {
            for (int i = 1; i <= sysRoleMenuView.getCheckbox().size(); i++) {
                sysRoleMenu.setRoleId(id);
                sysRoleMenu.setMenuId((Integer) sysRoleMenuView.getCheckbox().get(i - 1));
                sysRoleMenuService.save(sysRoleMenu);
            }
        }
        if (sysRoleMenuView.getCheckbox2() != null) {
            for (int i = 1; i <= sysRoleMenuView.getCheckbox2().size(); i++) {
                sysRoleMenu.setRoleId(id);
                sysRoleMenu.setMenuId((Integer) sysRoleMenuView.getCheckbox2().get(i - 1));
                sysRoleMenuService.save(sysRoleMenu);
            }
        }
        if (sysRoleMenuView.getCheckbox3() != null) {
            for (int i = 1; i <= sysRoleMenuView.getCheckbox3().size(); i++) {
                sysRoleMenu.setRoleId(id);
                sysRoleMenu.setMenuId((Integer) sysRoleMenuView.getCheckbox3().get(i - 1));
                sysRoleMenuService.save(sysRoleMenu);
            }
        }
        if (sysRoleMenuView.getCheckbox4() != null) {
            for (int i = 1; i <= sysRoleMenuView.getCheckbox4().size(); i++) {
                sysRoleMenu.setRoleId(id);
                sysRoleMenu.setMenuId((Integer) sysRoleMenuView.getCheckbox4().get(i - 1));
                sysRoleMenuService.save(sysRoleMenu);
            }
        }

        return true;
    }
}
