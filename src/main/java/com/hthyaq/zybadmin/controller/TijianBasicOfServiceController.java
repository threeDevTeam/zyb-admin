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
import com.hthyaq.zybadmin.model.vo.TijianBasicOfServiceView;
import com.hthyaq.zybadmin.service.AreaOfDicService;
import com.hthyaq.zybadmin.service.ServiceOfRegisterService;
import com.hthyaq.zybadmin.service.TijianBasicOfServiceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 体检机构的基本信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/tijianBasicOfService")
public class TijianBasicOfServiceController {
    @Autowired
    TijianBasicOfServiceService tijianBasicOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    AreaOfDicService areaOfDicService;

    @PostMapping("/add")
    public boolean add(@RequestBody TijianBasicOfService tijianBasicOfService, HttpSession httpSession) {
        boolean flag=false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<ServiceOfRegister> queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",sysUser.getCompanyId());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper);
        for (ServiceOfRegister serviceOfRegister : list) {
            if (serviceOfRegister.getType().equals("体检机构")) {
                tijianBasicOfService.setName(serviceOfRegister.getName());
                tijianBasicOfService.setCode(serviceOfRegister.getCode());
                tijianBasicOfService.setProvinceName(serviceOfRegister.getProvinceName());
                tijianBasicOfService.setProvinceCode(serviceOfRegister.getProvinceCode());
                tijianBasicOfService.setCityName(serviceOfRegister.getCityName());
                tijianBasicOfService.setCityCode(serviceOfRegister.getCityCode());
                tijianBasicOfService.setDistrictName(serviceOfRegister.getDistrictName());
                tijianBasicOfService.setDistrictCode(serviceOfRegister.getDistrictCode());
                tijianBasicOfService.setRegisterAddress(serviceOfRegister.getRegisterAddress());
                flag = tijianBasicOfServiceService.save(tijianBasicOfService);
            }
        }
        return flag;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return tijianBasicOfServiceService.removeById(id);
    }
    @GetMapping("/getById")
    public TijianBasicOfService getById(Integer id) {

        List list=new ArrayList();
        TijianBasicOfServiceView tijianBasicOfServiceView=new TijianBasicOfServiceView();
        TijianBasicOfService tijianBasicOfService = tijianBasicOfServiceService.getById(id);
        BeanUtils.copyProperties(tijianBasicOfService, tijianBasicOfServiceView);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", tijianBasicOfService.getProvinceCode());
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list1) {
            list.add(areaOfDic.getId());
        }
        QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("code",tijianBasicOfService.getCityCode());
        List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
        for (AreaOfDic areaOfDic : list2) {
            list.add(areaOfDic.getId());
        }


        if (tijianBasicOfService.getCityName().equals(tijianBasicOfService.getDistrictName())) {
            for (AreaOfDic areaOfDic : list2) {
                list.add(areaOfDic.getId());
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("code",tijianBasicOfService.getDistrictCode());
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                list.add(areaOfDic.getId());
            }
        }

        tijianBasicOfServiceView.setCascader((ArrayList) list);
        return tijianBasicOfServiceView;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody TijianBasicOfServiceView tijianBasicOfServiceView) {
        TijianBasicOfService tijianBasicOfService = new TijianBasicOfService();

        BeanUtils.copyProperties(tijianBasicOfServiceView, tijianBasicOfService);

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",tijianBasicOfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            tijianBasicOfService.setProvinceName(String.valueOf(areaOfDic.getName()));
            tijianBasicOfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }
        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", tijianBasicOfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            tijianBasicOfService.setCityName(String.valueOf(areaOfDic.getName()));

            tijianBasicOfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (tijianBasicOfServiceView.getCascader().size() !=3) {
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("id", tijianBasicOfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                tijianBasicOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                tijianBasicOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", tijianBasicOfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                tijianBasicOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                tijianBasicOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }
        return tijianBasicOfServiceService.updateById(tijianBasicOfService);
    }

    @GetMapping("/list")
    public IPage<TijianBasicOfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        String hospitalLevel = jsonObject.getString("hospitalLevel");
        QueryWrapper<ServiceOfRegister> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("name",sysUser.getCompanyName());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper1);
        for (ServiceOfRegister serviceOfRegister : list) {
            if(serviceOfRegister.getType().equals("体检机构")){
                list1.clear();
                list1.add(serviceOfRegister.getName());
            }
        }
        QueryWrapper<TijianBasicOfService> queryWrapper=new QueryWrapper();
        queryWrapper.eq("name", list1.get(0));
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        if (!Strings.isNullOrEmpty(hospitalLevel)) {
            queryWrapper.eq("hospitalLevel", hospitalLevel);
        }
        IPage<TijianBasicOfService> page = tijianBasicOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

}
