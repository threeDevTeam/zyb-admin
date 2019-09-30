package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.JianceBasicOfService;
import com.hthyaq.zybadmin.model.entity.JianceTotalOfService;
import com.hthyaq.zybadmin.model.entity.ServiceOfRegister;
import com.hthyaq.zybadmin.model.entity.SysUser;
import com.hthyaq.zybadmin.service.JianceBasicOfServiceService;
import com.hthyaq.zybadmin.service.JianceTotalOfServiceService;
import com.hthyaq.zybadmin.service.ServiceOfRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 检测机构的总体信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/jianceTotalOfService")
public class JianceTotalOfServiceController {
    @Autowired
    JianceTotalOfServiceService jianceTotalOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    JianceBasicOfServiceService jianceBasicOfServiceService;

    @PostMapping("/add")
    public boolean add(@RequestBody JianceTotalOfService jianceTotalOfService, HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<ServiceOfRegister> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", sysUser.getCompanyName());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper);
        for (ServiceOfRegister serviceOfRegister : list) {
            if (serviceOfRegister.getType().equals("检测机构")) {
                QueryWrapper<JianceBasicOfService> queryWrapper1 = new QueryWrapper();
                queryWrapper.eq("name", serviceOfRegister.getName());
                List<JianceBasicOfService> list1 = jianceBasicOfServiceService.list(queryWrapper1);
                for (JianceBasicOfService jianceBasicOfService : list1) {
                    jianceTotalOfService.setJianceBasicId(jianceBasicOfService.getId());
                }
                return flag = jianceTotalOfServiceService.save(jianceTotalOfService);

            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return jianceTotalOfServiceService.removeById(id);
    }

    @GetMapping("/getById")
    public JianceTotalOfService getById(Integer id) {

        JianceTotalOfService jianceTotalOfService = jianceTotalOfServiceService.getById(id);
        //demoCourse
        QueryWrapper<JianceTotalOfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<JianceTotalOfService> demoCourseList = jianceTotalOfServiceService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return jianceTotalOfService;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody JianceTotalOfService jianceTotalOfService) {
        return jianceTotalOfServiceService.updateById(jianceTotalOfService);
    }

    @GetMapping("/list")
    public IPage<JianceTotalOfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        QueryWrapper<JianceTotalOfService> queryWrapper = new QueryWrapper();
        QueryWrapper<ServiceOfRegister> queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper1);
        for (ServiceOfRegister serviceOfRegister : list) {
            if (serviceOfRegister.getType().equals("检测机构")) {
                QueryWrapper<JianceBasicOfService> queryWrapper2 = new QueryWrapper();
                queryWrapper2.eq("name", serviceOfRegister.getName());
                List<JianceBasicOfService> list2 = jianceBasicOfServiceService.list(queryWrapper2);
                for (JianceBasicOfService jianceBasicOfService : list2) {
                    list1.clear();
                    list1.add(jianceBasicOfService.getId());
                }
            }
        }
        queryWrapper.eq("jianceBasicId", list1.get(0));
        if (!Strings.isNullOrEmpty(year)) {
            queryWrapper.eq("year", year);
        }
        IPage<JianceTotalOfService> page = jianceTotalOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}

