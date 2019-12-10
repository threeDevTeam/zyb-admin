package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.utils.cascade.CascadeUtil;
import com.hthyaq.zybadmin.common.utils.cascade.CascadeView;
import com.hthyaq.zybadmin.model.entity.AreaOfDic;
import com.hthyaq.zybadmin.model.entity.SysMenu;
import com.hthyaq.zybadmin.model.entity.SysRole;
import com.hthyaq.zybadmin.model.entity.SysUser;
import com.hthyaq.zybadmin.model.vo.SysRoleTree;
import com.hthyaq.zybadmin.service.SysMenuService;
import com.hthyaq.zybadmin.service.SysRoleService;
import com.hthyaq.zybadmin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/sysRole")
public class SysRoleController {
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    SysUserService sysUserService;
    @PostMapping("/add")
    public boolean add(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
        return true;
    }
    @GetMapping("/sysRoleTree")
    public List<SysRoleTree> cascadeData() {
       List list1=new ArrayList();
       QueryWrapper<SysMenu> queryWrapper=new QueryWrapper<>();
       queryWrapper.eq("pid","1");
        List<SysMenu> list = sysMenuService.list(queryWrapper);
        for (SysMenu sysMenu : list) {
            SysRoleTree sysRoleTree=new SysRoleTree();
            sysRoleTree.setLabel(sysMenu.getName());
            sysRoleTree.setValue(sysMenu.getId());
            list1.add(sysRoleTree);
        }
        return list1;
    }
    @GetMapping("/sysRoleTree2")
    public List<SysRoleTree> cascadeData2() {
        List list1=new ArrayList();
        QueryWrapper<SysMenu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("pid","13");
        List<SysMenu> list = sysMenuService.list(queryWrapper);
        for (SysMenu sysMenu : list) {
            SysRoleTree sysRoleTree=new SysRoleTree();
            sysRoleTree.setLabel(sysMenu.getName());
            sysRoleTree.setValue(sysMenu.getId());
            list1.add(sysRoleTree);
        }
        return list1;
    }
    @GetMapping("/sysRoleTree3")
    public List<SysRoleTree> cascadeData3() {
        List list1=new ArrayList();
        QueryWrapper<SysMenu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("pid","25");
        List<SysMenu> list = sysMenuService.list(queryWrapper);
        for (SysMenu sysMenu : list) {
            SysRoleTree sysRoleTree=new SysRoleTree();
            sysRoleTree.setLabel(sysMenu.getName());
            sysRoleTree.setValue(sysMenu.getId());
            list1.add(sysRoleTree);
        }
        return list1;
    }
    @GetMapping("/sysRoleTree4")
    public List<SysRoleTree> cascadeData4() {
        List list1=new ArrayList();
        QueryWrapper<SysMenu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("pid","48");
        List<SysMenu> list = sysMenuService.list(queryWrapper);
        for (SysMenu sysMenu : list) {
            SysRoleTree sysRoleTree=new SysRoleTree();
            sysRoleTree.setLabel(sysMenu.getName());
            sysRoleTree.setValue(sysMenu.getId());
            list1.add(sysRoleTree);
        }
        return list1;
    }
    @GetMapping("/sysRoleTree5")
    public List<SysRoleTree> cascadeData5() {
        List list1=new ArrayList();
        QueryWrapper<SysMenu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("pid","54");
        List<SysMenu> list = sysMenuService.list(queryWrapper);
        for (SysMenu sysMenu : list) {
            SysRoleTree sysRoleTree=new SysRoleTree();
            sysRoleTree.setLabel(sysMenu.getName());
            sysRoleTree.setValue(sysMenu.getId());
            list1.add(sysRoleTree);
        }
        return list1;
    }
    @GetMapping("/bindUser")
    public List<SysUser> cascadeData1() {
        List list1=new ArrayList();
        List<SysUser> list = sysUserService.list();
        for (SysUser sysUser : list) {
            SysRoleTree sysRoleTree=new SysRoleTree();
            sysRoleTree.setLabel(sysUser.getLoginName());
            sysRoleTree.setValue(sysUser.getId());
            list1.add(sysRoleTree);
        }
        return list1;
    }



    @GetMapping("/getById")
    public SysRole getById(Integer id) {
        //demo
        SysRole sysRole = sysRoleService.getById(id);
        return sysRole;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody SysRole sysRole) {
        return sysRoleService.updateById(sysRole);
    }

    @GetMapping("/list")
    public IPage<SysRole> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);jsonObject.getObject("zbry",String.class);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.like("name", name);
        }

        IPage<SysRole> page = sysRoleService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

}
