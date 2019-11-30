package com.hthyaq.zybadmin.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hthyaq.zybadmin.model.entity.SysMenu;
import com.hthyaq.zybadmin.model.entity.SysRole;
import com.hthyaq.zybadmin.model.entity.SysRoleMenu;
import com.hthyaq.zybadmin.model.entity.SysRoleUser;
import com.hthyaq.zybadmin.model.vo.SysRoleMenuView;
import com.hthyaq.zybadmin.service.SysMenuService;
import com.hthyaq.zybadmin.service.SysRoleMenuService;
import com.hthyaq.zybadmin.service.SysRoleService;
import com.hthyaq.zybadmin.service.SysUserService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


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
        //先删除
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        sysRoleMenuService.remove(queryWrapper.eq("roleId", id));
        //后插入
        Set<Integer> menuIdSet = Sets.newHashSet();
        if (ObjectUtil.length(sysRoleMenuView.getCheckbox()) > 0) {
            menuIdSet.addAll(sysRoleMenuView.getCheckbox());
        }
        if (ObjectUtil.length(sysRoleMenuView.getCheckbox2()) > 0) {
            menuIdSet.addAll(sysRoleMenuView.getCheckbox2());
        }
        if (ObjectUtil.length(sysRoleMenuView.getCheckbox3()) > 0) {
            menuIdSet.addAll(sysRoleMenuView.getCheckbox3());
        }
        if (ObjectUtil.length(sysRoleMenuView.getCheckbox4()) > 0) {
            menuIdSet.addAll(sysRoleMenuView.getCheckbox4());
        }

        List<SysRoleMenu> data = Lists.newArrayList();
        for (Integer menuId : menuIdSet) {
            SysRoleMenu tmp = new SysRoleMenu();
            tmp.setRoleId(id).setMenuId(menuId);
            data.add(tmp);
        }
        sysRoleMenuService.saveBatch(data);

/*        //sysRoleMenuService.save(sysRoleMenu);
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        sysRoleMenuService.remove(queryWrapper.eq("roleId", id));
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
        }*/

        return true;
    }

    @GetMapping("/getById")
    public Map<String, List<Integer>> getById(Integer id) {
        Map<String, List<Integer>> map = Maps.newHashMap();
        List<SysRoleMenu> list = new ArrayList<>();
        List<SysRoleMenu> list3 = new ArrayList<>();
        List<SysRoleMenu> list5 = new ArrayList<>();
        List<SysRoleMenu> list7 = new ArrayList<>();
        //supervise
        QueryWrapper<SysMenu> qw = new QueryWrapper<>();
        qw.eq("pid", "1");
        List<SysMenu> list1 = sysMenuService.list(qw);

        for (SysMenu sysMenu : list1) {
            QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("roleId", id);
            queryWrapper.in("menuId", sysMenu.getId());
            SysRoleMenu menuId = sysRoleMenuService.getOne(queryWrapper);
            if(menuId!=null){
                list.add(menuId);
            }
        }
        if (ObjectUtil.length(list) > 0) {

            map.put("supervise", list.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList()));
        }

        //service
        QueryWrapper<SysMenu> qw1 = new QueryWrapper<>();
        qw1.eq("pid", "13");
        List<SysMenu> list2 = sysMenuService.list(qw1);

        for (SysMenu sysMenu : list2) {
            QueryWrapper<SysRoleMenu> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("roleId", id);
            queryWrapper1.in("menuId", sysMenu.getId());
            SysRoleMenu menuId = sysRoleMenuService.getOne(queryWrapper1);
            if(menuId!=null) {
                list3.add(menuId);
            }
        }
        if (ObjectUtil.length(list3) > 0) {
            map.put("service", list3.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList()));
        }


        //enterprise
        QueryWrapper<SysMenu> qw2 = new QueryWrapper<>();
        qw2.eq("pid", "25");
        List<SysMenu> list4 = sysMenuService.list(qw2);

        for (SysMenu sysMenu : list4) {
            QueryWrapper<SysRoleMenu> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("roleId", id);
            queryWrapper2.in("menuId", sysMenu.getId());
            SysRoleMenu menuId = sysRoleMenuService.getOne(queryWrapper2);
            if(menuId!=null) {
                list5.add(menuId);
            }
        }
        if (ObjectUtil.length(list5) > 0) {
            map.put("enterprise", list5.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList()));
        }


        //Management
        QueryWrapper<SysMenu> qw3 = new QueryWrapper<>();
        qw3.eq("pid", "48");
        List<SysMenu> list6 = sysMenuService.list(qw3);

        for (SysMenu sysMenu : list6) {
            QueryWrapper<SysRoleMenu> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("roleId", id);
            queryWrapper3.in("menuId", sysMenu.getId());
            SysRoleMenu menuId = sysRoleMenuService.getOne(queryWrapper3);
            if(menuId!=null) {
                list7.add(menuId);
            }
        }
        if (ObjectUtil.length(list7) > 0) {
            map.put("Management", list7.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList()));
        }

        return map;
    }
}
