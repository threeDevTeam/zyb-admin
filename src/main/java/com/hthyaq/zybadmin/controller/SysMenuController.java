package com.hthyaq.zybadmin.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.bean.Child3;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.vo.*;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-10-15
 */
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleUserService sysRoleUserService;
    @Autowired
    SysRoleMenuService sysRoleMenuService;

//    @GetMapping("/add")
//    public void add() {
//        ExcelReader reader = ExcelUtil.getReader("D:/菜单.xlsx");
//        List<SysMenu> all = reader.readAll(SysMenu.class);
//        System.out.println(all);
//        for (SysMenu sysMenu : all) {
//            System.out.println(sysMenu);
//            sysMenuService.save(sysMenu);
//        }
//    }

//    @GetMapping("/uppid")
//    public void uppid() {
//        List list1=new ArrayList();
//        List<SysMenu> list = sysMenuService.list();
//        for (SysMenu sysMenu : list) {
//            if(sysMenu.getLevel().equals("1")){
//                list1.clear();
//                list1.add(sysMenu.getId());
//            }if(sysMenu.getLevel().equals("2")){
//                sysMenu.setPid((Integer) list1.get(0));
//                sysMenuService.updateById(sysMenu);
//            }
//
//        }
//    }
@PostMapping("/add")
  public void add(@RequestBody SysMenuView sysMenuView) {
    SysMenu sysMenu=new SysMenu();
    BeanUtils.copyProperties(sysMenuView, sysMenu);
    sysMenu.setPid((Integer) sysMenuView.getCascader().get(0));
    sysMenuService.save(sysMenu);
}




    @GetMapping("/list")
    public IPage<SysMenu> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);jsonObject.getObject("zbry",String.class);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String username = jsonObject.getString("username");
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(username)) {
            queryWrapper.eq("username", username);
        }

        IPage<SysMenu> page = sysMenuService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody SysMenuView sysMenuView) {
        SysMenu sysMenu=new SysMenu();
        BeanUtils.copyProperties(sysMenuView, sysMenu);
        sysMenu.setPid((Integer) sysMenuView.getCascader().get(0));
        return sysMenuService.updateById(sysMenu);
    }

    @GetMapping("/getById")
    public SysMenu getById(Integer id) {
        List list = new ArrayList();
        SysMenuView sysMenuView=new SysMenuView();
        //demo
        SysMenu sysMenu = sysMenuService.getById(id);
        BeanUtils.copyProperties(sysMenu, sysMenuView);
        QueryWrapper<SysMenu> queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",id);
        List<SysMenu> list1 = sysMenuService.list(queryWrapper);
        for (SysMenu sysMenu1 : list1) {
            list.add(sysMenu1.getPid());
        }
        sysMenuView.setCascader((ArrayList) list);
        return sysMenuView;
    }
    @GetMapping("/sysMenutree")
    public List<SysMenu> sysMenutree() {
        List list1=new ArrayList();
        List<SysMenu> list = sysMenuService.list(new QueryWrapper<SysMenu>().eq("pid","-1"));
        for (SysMenu sysMenu : list) {
            SysMenuTree sysMenuTree=new SysMenuTree();
            sysMenuTree.setLabel(sysMenu.getName());
            sysMenuTree.setValue(sysMenu.getId());
            list1.add(sysMenuTree);
        }
        return list1;
    }
    @GetMapping("/sysMenulogin")
    public List<SysMenuLoginView> sysMenulogin(String loginName) {
        System.out.println(loginName);
        List list1=new ArrayList();
        List<SysMenu> list=new ArrayList<>();
        SysMenuLoginView sysMenuLoginView=new SysMenuLoginView();
        //sysuserid
        QueryWrapper<SysUser> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("loginName",loginName);
        SysUser sysUser = sysUserService.getOne(queryWrapper1);

        //sysRoleUserid
        QueryWrapper<SysRoleUser> queryWrapper2=new QueryWrapper<>();
        queryWrapper2.eq("userId",sysUser.getId());
        SysRoleUser sysRoleUser= sysRoleUserService.getOne(queryWrapper2);

        //sysRoleMenuid
        QueryWrapper<SysRoleMenu> queryWrapper3=new QueryWrapper<>();
        queryWrapper3.eq("roleId",sysRoleUser.getRoleId());
        List<SysRoleMenu> list2 = sysRoleMenuService.list(queryWrapper3);
        for (SysRoleMenu sysRoleMenu : list2) {
            //sysMenuid
            QueryWrapper<SysMenu> queryWrapper4=new QueryWrapper<>();
            queryWrapper4.eq("id",sysRoleMenu.getMenuId());
            List<SysMenu> list3 = sysMenuService.list(queryWrapper4);
            for (SysMenu sysMenu : list3) {
                list.add(sysMenu);
                //pid
                QueryWrapper<SysMenu> queryWrapper5=new QueryWrapper<>();
                queryWrapper5.eq("id",sysMenu.getPid());
                SysMenu sysMenuf = sysMenuService.getOne(queryWrapper5);
                BeanUtils.copyProperties(sysMenuf, sysMenuLoginView);
            }
        }
        sysMenuLoginView.setChildren((ArrayList) list);
        list1.add(sysMenuLoginView);
        return list1;
    }

}
