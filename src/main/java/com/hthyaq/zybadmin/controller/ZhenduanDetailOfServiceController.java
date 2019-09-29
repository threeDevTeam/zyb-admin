package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.vo.TijianDetail1OfServiceView;
import com.hthyaq.zybadmin.model.vo.ZhenduanBasicOfServiceView;
import com.hthyaq.zybadmin.model.vo.ZhenduanDetailOfServiceView;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 诊断机构的具体报告 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/zhenduanDetailOfService")
public class ZhenduanDetailOfServiceController {
    @Autowired
    ZhenduanDetailOfServiceService zhenduanDetailOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    ZhenduanBasicOfServiceService zhenduanBasicOfServiceService;
    @Autowired
    AreaOfDicService areaOfDicService;
    @PostMapping("/add")
    public boolean add(@RequestBody ZhenduanDetailOfServiceView zhenduanDetailOfServiceView, HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);



        ZhenduanDetailOfService zhenduanDetailOfService = new ZhenduanDetailOfServiceView();
        BeanUtils.copyProperties(zhenduanDetailOfServiceView, zhenduanDetailOfService);
        zhenduanDetailOfService.setProvinceName((String) zhenduanDetailOfServiceView.getCascader().get(0));

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", (String) zhenduanDetailOfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            zhenduanDetailOfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }

        zhenduanDetailOfService.setCityName((String) zhenduanDetailOfServiceView.getCascader().get(1));

        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", (String) zhenduanDetailOfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            zhenduanDetailOfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (zhenduanDetailOfServiceView.getCascader().size() !=3) {
            zhenduanDetailOfService.setDistrictName((String) zhenduanDetailOfServiceView.getCascader().get(1));
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("name", (String) zhenduanDetailOfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                zhenduanDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            zhenduanDetailOfService.setDistrictName((String) zhenduanDetailOfServiceView.getCascader().get(2));
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("name", (String) zhenduanDetailOfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                zhenduanDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }



        QueryWrapper<ServiceOfRegister> queryWrapper2=new QueryWrapper();
        queryWrapper2.eq("id",sysUser.getCompanyId());
        List<ServiceOfRegister> list2 = serviceOfRegisterService.list(queryWrapper2);
        for (ServiceOfRegister serviceOfRegister : list2) {
            QueryWrapper<ZhenduanBasicOfService> queryWrapper3 = new QueryWrapper();
            queryWrapper3.eq("name", serviceOfRegister.getName());
            List<ZhenduanBasicOfService> list3 = zhenduanBasicOfServiceService.list(queryWrapper3);
            for (ZhenduanBasicOfService zhenduanBasicOfService : list3) {
                zhenduanDetailOfService.setZhenduanBasicId(zhenduanBasicOfService.getId());
                flag=zhenduanDetailOfServiceService.save(zhenduanDetailOfService);
            }
        }
        return  flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return zhenduanDetailOfServiceService.removeById(id);
    }

    @GetMapping("/getById")
    public ZhenduanDetailOfService getById(Integer id) {

        List list=new ArrayList();
        ZhenduanDetailOfServiceView zhenduanDetailOfServiceView=new ZhenduanDetailOfServiceView();
        ZhenduanDetailOfService ZhenduanDetailOfService = zhenduanDetailOfServiceService.getById(id);
        BeanUtils.copyProperties(ZhenduanDetailOfService, zhenduanDetailOfServiceView);
        String provinceName = ZhenduanDetailOfService.getProvinceName();
        String cityName = ZhenduanDetailOfService.getCityName();
        String districtName = ZhenduanDetailOfService.getDistrictName();
        list.add(provinceName);
        if(cityName.equals(districtName)){
            list.add(cityName);
        }else {
            list.add(cityName);
            list.add(districtName);
        }

        zhenduanDetailOfServiceView.setCascader((ArrayList) list);
        return zhenduanDetailOfServiceView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody ZhenduanDetailOfServiceView zhenduanDetailOfServiceView) {

        ZhenduanDetailOfService zhenduanDetailOfService=new ZhenduanDetailOfServiceView();

        BeanUtils.copyProperties(zhenduanDetailOfServiceView, zhenduanDetailOfService);

        zhenduanDetailOfService.setProvinceName((String) zhenduanDetailOfServiceView.getCascader().get(0));

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", (String) zhenduanDetailOfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            zhenduanDetailOfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }

        zhenduanDetailOfService.setCityName((String) zhenduanDetailOfServiceView.getCascader().get(1));

        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", (String) zhenduanDetailOfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            zhenduanDetailOfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (zhenduanDetailOfServiceView.getCascader().size() !=3) {
            zhenduanDetailOfService.setDistrictName((String) zhenduanDetailOfServiceView.getCascader().get(1));
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("name", (String) zhenduanDetailOfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                zhenduanDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            zhenduanDetailOfService.setDistrictName((String) zhenduanDetailOfServiceView.getCascader().get(2));
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("name", (String) zhenduanDetailOfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                zhenduanDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }


        return zhenduanDetailOfServiceService.updateById(zhenduanDetailOfService);
    }

    @GetMapping("/list")
    public IPage<ZhenduanDetailOfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);

        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String enterpriseName = jsonObject.getString("enterpriseName");
        String name = jsonObject.getString("name");
        QueryWrapper<ZhenduanDetailOfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",sysUser.getCompanyId());
        if (!Strings.isNullOrEmpty(enterpriseName)) {
            queryWrapper.eq("enterpriseName", enterpriseName);
        }
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }

        IPage<ZhenduanDetailOfService> page = zhenduanDetailOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}

