package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.bean.Child;
import com.hthyaq.zybadmin.model.bean.GlobalResult;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.vo.DemoView;
import com.hthyaq.zybadmin.model.vo.SysUserpassword;
import com.hthyaq.zybadmin.service.SysRoleUserService;
import com.hthyaq.zybadmin.service.SysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    SysRoleUserService sysRoleUserService;
    @PostMapping("/add")
    public boolean add(@RequestBody SysUser sysUser) {
        sysUser.setLoginPassword( DigestUtils.md5Hex(sysUser.getLoginPassword()));
        sysUserService.save(sysUser);
        return true;
    }


    @PostMapping("/register")
    public GlobalResult register(@RequestBody SysUser sysUserForm, HttpSession httpSession) {

        List<SysUser> list = sysUserService.list(new QueryWrapper<SysUser>().eq("loginName", sysUserForm.getLoginName()).eq("loginPassword",  DigestUtils.md5Hex(sysUserForm.getLoginPassword())));
        if (list.size() != 1) {
            return GlobalResult.fail();
        }
        SysUser sysUserDb = list.get(0);
        httpSession.setAttribute(GlobalConstants.LOGIN_NAME, sysUserDb);
        return GlobalResult.success("", sysUserDb);
    }
    @GetMapping("/getById")
    public SysUser getById(Integer id) {
        //demo
        SysUser sysUser = sysUserService.getById(id);
        return sysUser;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody SysUser sysUser) {
        return sysUserService.updateById(sysUser);
    }
    @GetMapping("/list")
    public IPage<SysUser> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);jsonObject.getObject("zbry",String.class);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String loginName = jsonObject.getString("loginName");
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(loginName)) {
            queryWrapper.eq("loginName", loginName);
        }

        IPage<SysUser> page = sysUserService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
    @PostMapping("/changePassword")
    public boolean out( HttpSession httpSession, @RequestBody SysUserpassword sysUserpassword) {
    System.out.println(sysUserpassword);
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        if(DigestUtils.md5Hex(sysUserpassword.getLoginPassword()).equals(sysUser.getLoginPassword())){
            sysUser.setLoginPassword(DigestUtils.md5Hex(sysUserpassword.getNewPassword()));
            sysUserService.updateById(sysUser);
            return true;
        }
        return false;
    }
}

