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

            zhenduanBasicOfService.setName(serviceOfRegister.getName());
            zhenduanBasicOfService.setCode(serviceOfRegister.getCode());
            zhenduanBasicOfService.setProvinceName(serviceOfRegister.getProvinceName());
            zhenduanBasicOfService.setProvinceCode(serviceOfRegister.getProvinceCode());
            zhenduanBasicOfService.setCityName(serviceOfRegister.getCityName());
            zhenduanBasicOfService.setCityCode(serviceOfRegister.getCityCode());
            zhenduanBasicOfService.setDistrictName(serviceOfRegister.getDistrictName());
            zhenduanBasicOfService.setDistrictCode(serviceOfRegister.getDistrictCode());
            zhenduanBasicOfService.setRegisterAddress(serviceOfRegister.getRegisterAddress());
            flag=zhenduanBasicOfServiceService.save(zhenduanBasicOfService);
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
        String provinceName = zhenduanBasicOfService.getProvinceName();
        String cityName = zhenduanBasicOfService.getCityName();
        String districtName = zhenduanBasicOfService.getDistrictName();
        list.add(provinceName);
        if(cityName.equals(districtName)){
            list.add(cityName);
        }else {
            list.add(cityName);
            list.add(districtName);
        }

        zhenduanBasicOfServiceView.setCascader((ArrayList) list);
        return zhenduanBasicOfServiceView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody ZhenduanBasicOfServiceView zhenduanBasicOfServiceView) {


        ZhenduanBasicOfService zhenduanBasicOfService=new ZhenduanBasicOfService();

        BeanUtils.copyProperties(zhenduanBasicOfServiceView, zhenduanBasicOfService);

        zhenduanBasicOfService.setProvinceName((String) zhenduanBasicOfServiceView.getCascader().get(0));

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", (String) zhenduanBasicOfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            zhenduanBasicOfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }

        zhenduanBasicOfService.setCityName((String) zhenduanBasicOfServiceView.getCascader().get(1));

        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", (String) zhenduanBasicOfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            zhenduanBasicOfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (zhenduanBasicOfServiceView.getCascader().size() !=3) {
            zhenduanBasicOfService.setDistrictName((String) zhenduanBasicOfServiceView.getCascader().get(1));
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("name", (String) zhenduanBasicOfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                zhenduanBasicOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            zhenduanBasicOfService.setDistrictName((String) zhenduanBasicOfServiceView.getCascader().get(2));
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("name", (String) zhenduanBasicOfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                zhenduanBasicOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }


        return zhenduanBasicOfServiceService.updateById(zhenduanBasicOfService);
    }

    @GetMapping("/list")
    public IPage<ZhenduanBasicOfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);

        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");

        QueryWrapper<ZhenduanBasicOfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",sysUser.getCompanyId());
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }


        IPage<ZhenduanBasicOfService> page = zhenduanBasicOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

}

