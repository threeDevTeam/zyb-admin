package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.bean.GlobalResult;
import com.hthyaq.zybadmin.model.entity.EnterpriseOfRegister;
import com.hthyaq.zybadmin.model.entity.ServiceOfRegister;
import com.hthyaq.zybadmin.model.entity.SuperviseOfRegister;
import com.hthyaq.zybadmin.model.entity.SysUser;
import com.hthyaq.zybadmin.model.vo.LoginVo;
import com.hthyaq.zybadmin.model.vo.SysUserpassword;
import com.hthyaq.zybadmin.service.*;
import org.apache.commons.codec.digest.DigestUtils;
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
    @Autowired
    SuperviseOfRegisterService superviseOfRegisterService;
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;

    @PostMapping("/add")
    public boolean add(@RequestBody SysUser sysUser) {
        sysUser.setLoginPassword(DigestUtils.md5Hex(sysUser.getLoginPassword()));
        sysUserService.save(sysUser);
        return true;
    }


    @PostMapping("/login")
    public GlobalResult login(@RequestBody SysUser sysUserForm, HttpSession httpSession) {

        List<SysUser> list = sysUserService.list(new QueryWrapper<SysUser>().eq("loginName", sysUserForm.getLoginName()).eq("loginPassword", DigestUtils.md5Hex(sysUserForm.getLoginPassword())));
        if (list.size() != 1) {
            return GlobalResult.fail();
        }
        SysUser sysUserDb = list.get(0);
        httpSession.setAttribute(GlobalConstants.LOGIN_NAME, sysUserDb);

        LoginVo loginVo = new LoginVo();
        //
        String type = sysUserDb.getType();
        if ("管理员".equals(type)) {
            loginVo.setObj1(sysUserDb);
        } else if ("政府监管部门".equals(type)) {
            SuperviseOfRegister superviseOfRegister = superviseOfRegisterService.getById(sysUserDb.getCompanyId());

            loginVo.setObj1(sysUserDb);
            loginVo.setObj2(superviseOfRegister);
            List<String> areaNameList = Lists.newArrayList();
            String name1 = superviseOfRegister.getProvinceName();
            areaNameList.add(name1);
            String name2 = superviseOfRegister.getCityName();
            if (!Strings.isNullOrEmpty(name2)) {
                areaNameList.add(name2);
            }
            String name3 = superviseOfRegister.getDistrictName();
            if (!Strings.isNullOrEmpty(name3) && !name3.equals(name2)) {
                areaNameList.add(name3);
            }
            loginVo.setAreaNameList(areaNameList);
            loginVo.setName(superviseOfRegister.getName());
        } else if ("企业".equals(type)) {
            EnterpriseOfRegister enterpriseOfRegister = enterpriseOfRegisterService.getById(sysUserDb.getCompanyId());
            loginVo.setObj1(sysUserDb);
            loginVo.setObj2(enterpriseOfRegister);
            List<String> areaNameList = Lists.newArrayList();
            String name1 = enterpriseOfRegister.getProvinceName();
            areaNameList.add(name1);
            String name2 = enterpriseOfRegister.getCityName();
            if (!Strings.isNullOrEmpty(name2)) {
                areaNameList.add(name2);
            }
            String name3 = enterpriseOfRegister.getDistrictName();
            if (!Strings.isNullOrEmpty(name3) && !name3.equals(name2)) {
                areaNameList.add(name3);
            }
            loginVo.setAreaNameList(areaNameList);
            loginVo.setName(enterpriseOfRegister.getName());
        } else if (type.contains("技术服务机构")) {
            ServiceOfRegister serviceOfRegister = serviceOfRegisterService.getById(sysUserDb.getCompanyId());
            loginVo.setObj1(sysUserDb);
            loginVo.setObj2(serviceOfRegister);
            List<String> areaNameList = Lists.newArrayList();
            String name1 = serviceOfRegister.getProvinceName();
            areaNameList.add(name1);
            String name2 = serviceOfRegister.getCityName();
            if (!Strings.isNullOrEmpty(name2)) {
                areaNameList.add(name2);
            }
            String name3 = serviceOfRegister.getDistrictName();
            if (!Strings.isNullOrEmpty(name3) && !name3.equals(name2)) {
                areaNameList.add(name3);
            }
            loginVo.setAreaNameList(areaNameList);
            loginVo.setName(serviceOfRegister.getName());
        }

        return GlobalResult.success("", loginVo);
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
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.getObject("zbry", String.class);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String loginName = jsonObject.getString("loginName");
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(loginName)) {
            queryWrapper.like("loginName",loginName );
        }

        IPage<SysUser> page = sysUserService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

    @PostMapping("/changePassword")
    public boolean out(HttpSession httpSession, @RequestBody SysUserpassword sysUserpassword) {
        System.out.println(sysUserpassword);
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        if (DigestUtils.md5Hex(sysUserpassword.getLoginPassword()).equals(sysUser.getLoginPassword())) {
            sysUser.setLoginPassword(DigestUtils.md5Hex(sysUserpassword.getNewPassword()));
            sysUserService.updateById(sysUser);
            return true;
        }
        return false;
    }
}

