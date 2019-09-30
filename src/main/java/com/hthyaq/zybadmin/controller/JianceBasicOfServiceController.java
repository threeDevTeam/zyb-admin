package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.common.utils.cascade.CascadeUtil;
import com.hthyaq.zybadmin.common.utils.cascade.CascadeView;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.vo.JianceBasicOfView;
import com.hthyaq.zybadmin.service.AreaOfDicService;
import com.hthyaq.zybadmin.service.JianceBasicOfServiceService;
import com.hthyaq.zybadmin.service.ServiceOfRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 检测机构的基本信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/jianceBasicOfService")
public class JianceBasicOfServiceController {
    @Autowired
    JianceBasicOfServiceService jianceBasicOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    AreaOfDicService areaOfDicService;

    @PostMapping("/add")
    public boolean add(@RequestBody JianceBasicOfService jianceBasicOfService, HttpSession httpSession) {
        boolean flag=false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<ServiceOfRegister> queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",sysUser.getCompanyName());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper);
        for (ServiceOfRegister serviceOfRegister : list) {
            if(serviceOfRegister.getType().equals("检测机构")){
                jianceBasicOfService.setName(serviceOfRegister.getName());
                jianceBasicOfService.setCode(serviceOfRegister.getCode());
                jianceBasicOfService.setProvinceName(serviceOfRegister.getProvinceName());
                jianceBasicOfService.setProvinceCode(serviceOfRegister.getProvinceCode());
                jianceBasicOfService.setCityName(serviceOfRegister.getCityName());
                jianceBasicOfService.setCityCode(serviceOfRegister.getCityCode());
                jianceBasicOfService.setDistrictName(serviceOfRegister.getDistrictName());
                jianceBasicOfService.setDistrictCode(serviceOfRegister.getDistrictCode());
                jianceBasicOfService.setRegisterAddress(serviceOfRegister.getRegisterAddress());
                flag=jianceBasicOfServiceService.save(jianceBasicOfService);
            }
        }
        return flag;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return jianceBasicOfServiceService.removeById(id);
    }
    @GetMapping("/getById")
    public JianceBasicOfService getById(Integer id) {
        List list=new ArrayList();
        JianceBasicOfView jianceBasicOfView=new JianceBasicOfView();
        JianceBasicOfService jianceBasicOfService = jianceBasicOfServiceService.getById(id);
        BeanUtils.copyProperties(jianceBasicOfService, jianceBasicOfView);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", jianceBasicOfService.getProvinceCode());
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list1) {
            list.add(areaOfDic.getId());
        }
        QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("code",jianceBasicOfService.getCityCode());
        List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
        for (AreaOfDic areaOfDic : list2) {
            list.add(areaOfDic.getId());
        }


        if (jianceBasicOfService.getCityName().equals(jianceBasicOfService.getDistrictName())) {
            for (AreaOfDic areaOfDic : list2) {
                list.add(areaOfDic.getId());
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("code",jianceBasicOfService.getDistrictCode());
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                list.add(areaOfDic.getId());
            }
        }

        jianceBasicOfView.setCascader((ArrayList) list);
        return jianceBasicOfView;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody JianceBasicOfView jianceBasicOfView) {
        JianceBasicOfService jianceBasicOfService = new JianceBasicOfView();

        BeanUtils.copyProperties(jianceBasicOfView, jianceBasicOfService);

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",jianceBasicOfView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            jianceBasicOfService.setProvinceName(String.valueOf(areaOfDic.getName()));
            jianceBasicOfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }
        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", jianceBasicOfView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            jianceBasicOfService.setCityName(String.valueOf(areaOfDic.getName()));

            jianceBasicOfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (jianceBasicOfView.getCascader().size() !=3) {
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("id", jianceBasicOfView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                jianceBasicOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                jianceBasicOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", jianceBasicOfView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                jianceBasicOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                jianceBasicOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }
        return jianceBasicOfServiceService.updateById(jianceBasicOfService);
    }

    @GetMapping("/list")
    public IPage<JianceBasicOfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        String code = jsonObject.getString("code");
        QueryWrapper<ServiceOfRegister> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("name",sysUser.getCompanyName());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper1);
        for (ServiceOfRegister serviceOfRegister : list) {
            if(serviceOfRegister.getType().equals("检测机构")){
                list1.clear();
                list1.add(serviceOfRegister.getName());
            }
        }
        QueryWrapper<JianceBasicOfService> queryWrapper=new QueryWrapper();
        queryWrapper.eq("name", list1.get(0));
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        if (!Strings.isNullOrEmpty(code)) {
            queryWrapper.eq("code", code);
        }

        IPage<JianceBasicOfService> page = jianceBasicOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

}
