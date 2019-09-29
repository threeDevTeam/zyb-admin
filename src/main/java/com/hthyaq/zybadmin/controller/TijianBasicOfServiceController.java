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
            tijianBasicOfService.setName(serviceOfRegister.getName());
            tijianBasicOfService.setCode(serviceOfRegister.getCode());
            tijianBasicOfService.setProvinceName(serviceOfRegister.getProvinceName());
            tijianBasicOfService.setProvinceCode(serviceOfRegister.getProvinceCode());
            tijianBasicOfService.setCityName(serviceOfRegister.getCityName());
            tijianBasicOfService.setCityCode(serviceOfRegister.getCityCode());
            tijianBasicOfService.setDistrictName(serviceOfRegister.getDistrictName());
            tijianBasicOfService.setDistrictCode(serviceOfRegister.getDistrictCode());
            tijianBasicOfService.setRegisterAddress(serviceOfRegister.getRegisterAddress());
       flag=tijianBasicOfServiceService.save(tijianBasicOfService);
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
        String provinceName = tijianBasicOfService.getProvinceName();
        String cityName = tijianBasicOfService.getCityName();
        String districtName = tijianBasicOfService.getDistrictName();
        list.add(provinceName);
        if(cityName.equals(districtName)){
            list.add(cityName);
        }else {
            list.add(cityName);
            list.add(districtName);
        }

        tijianBasicOfServiceView.setCascader(list);
        return tijianBasicOfServiceView;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody TijianBasicOfService tijianBasicOfService) {
        return tijianBasicOfServiceService.updateById(tijianBasicOfService);
    }

    @GetMapping("/list")
    public IPage<TijianBasicOfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);

        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        String hospitalLevel = jsonObject.getString("hospitalLevel");
        QueryWrapper<TijianBasicOfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",sysUser.getCompanyId());
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
