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
import com.hthyaq.zybadmin.model.vo.TijianDetail1OfServiceView;
import com.hthyaq.zybadmin.model.vo.TijianDetail2OfServiceView;
import com.hthyaq.zybadmin.model.vo.ZhenduanBasicOfServiceView;
import com.hthyaq.zybadmin.service.*;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 诊断机构的基本信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/zhenduanBasicOfService")
public class ZhenduanBasicOfServiceController {
    @Autowired
    ZhenduanBasicOfServiceService zhenduanBasicOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    AreaOfDicService areaOfDicService;
    @PostMapping("/add")
    public boolean add(@RequestBody ZhenduanBasicOfService zhenduanBasicOfService ,HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<ServiceOfRegister> queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",sysUser.getCompanyId());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper);
        for (ServiceOfRegister serviceOfRegister : list) {
            if (serviceOfRegister.getType().equals("诊断机构")) {
                zhenduanBasicOfService.setName(serviceOfRegister.getName());
                zhenduanBasicOfService.setCode(serviceOfRegister.getCode());
                zhenduanBasicOfService.setProvinceName(serviceOfRegister.getProvinceName());
                zhenduanBasicOfService.setProvinceCode(serviceOfRegister.getProvinceCode());
                zhenduanBasicOfService.setCityName(serviceOfRegister.getCityName());
                zhenduanBasicOfService.setCityCode(serviceOfRegister.getCityCode());
                zhenduanBasicOfService.setDistrictName(serviceOfRegister.getDistrictName());
                zhenduanBasicOfService.setDistrictCode(serviceOfRegister.getDistrictCode());
                zhenduanBasicOfService.setRegisterAddress(serviceOfRegister.getRegisterAddress());
                flag = zhenduanBasicOfServiceService.save(zhenduanBasicOfService);
            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return zhenduanBasicOfServiceService.removeById(id);
    }

    @GetMapping("/getById")
    public ZhenduanBasicOfService getById(Integer id) {

        List list=new ArrayList();
        ZhenduanBasicOfServiceView zhenduanBasicOfServiceView=new ZhenduanBasicOfServiceView();
        ZhenduanBasicOfService zhenduanBasicOfService = zhenduanBasicOfServiceService.getById(id);
        BeanUtils.copyProperties(zhenduanBasicOfService, zhenduanBasicOfServiceView);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", zhenduanBasicOfService.getProvinceCode());
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list1) {
            list.add(areaOfDic.getId());
        }
        QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("code",zhenduanBasicOfService.getCityCode());
        List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
        for (AreaOfDic areaOfDic : list2) {
            list.add(areaOfDic.getId());
        }


        if (zhenduanBasicOfService.getCityName().equals(zhenduanBasicOfService.getDistrictName())) {
            for (AreaOfDic areaOfDic : list2) {
                list.add(areaOfDic.getId());
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("code",zhenduanBasicOfService.getDistrictCode());
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                list.add(areaOfDic.getId());
            }
        }


        zhenduanBasicOfServiceView.setCascader((ArrayList) list);
        return zhenduanBasicOfServiceView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody ZhenduanBasicOfServiceView zhenduanBasicOfServiceView) {


        ZhenduanBasicOfService zhenduanBasicOfService=new ZhenduanBasicOfService();

        BeanUtils.copyProperties(zhenduanBasicOfServiceView, zhenduanBasicOfService);

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",zhenduanBasicOfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            zhenduanBasicOfService.setProvinceName(String.valueOf(areaOfDic.getName()));
            zhenduanBasicOfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }
        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", zhenduanBasicOfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            zhenduanBasicOfService.setCityName(String.valueOf(areaOfDic.getName()));

            zhenduanBasicOfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (zhenduanBasicOfServiceView.getCascader().size() !=3) {
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("id", zhenduanBasicOfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                zhenduanBasicOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                zhenduanBasicOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", zhenduanBasicOfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                zhenduanBasicOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                zhenduanBasicOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }

        return zhenduanBasicOfServiceService.updateById(zhenduanBasicOfService);
    }

    @GetMapping("/list")
    public IPage<ZhenduanBasicOfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");

        QueryWrapper<ServiceOfRegister> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("name",sysUser.getCompanyName());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper1);
        for (ServiceOfRegister serviceOfRegister : list) {
            if(serviceOfRegister.getType().equals("诊断机构")){
                list1.clear();
                list1.add(serviceOfRegister.getName());
            }
        }
        QueryWrapper<ZhenduanBasicOfService> queryWrapper=new QueryWrapper();
        queryWrapper.eq("name", list1.get(0));
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }


        IPage<ZhenduanBasicOfService> page = zhenduanBasicOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

}

