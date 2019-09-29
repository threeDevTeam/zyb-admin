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
import com.hthyaq.zybadmin.model.vo.TijianDetail1OfServiceView;
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
 * 体检机构的具体报告1 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/tijianDetail1OfService")
public class TijianDetail1OfServiceController {
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    TijianDetail1OfServiceService tijianDetail1OfServiceService;
    @Autowired
    TijianBasicOfServiceService tijianBasicOfServiceService;
    @Autowired
    AreaOfDicService areaOfDicService;
    @PostMapping("/add")
    public boolean add(@RequestBody TijianDetail1OfServiceView tijianDetail1OfServiceView, HttpSession httpSession) {
       boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);

        TijianDetail1OfService tijianDetail1OfService = new TijianDetail1OfServiceView();
        BeanUtils.copyProperties(tijianDetail1OfServiceView, tijianDetail1OfService);
        tijianDetail1OfService.setProvinceName((String) tijianDetail1OfServiceView.getCascader().get(0));

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", (String) tijianDetail1OfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            tijianDetail1OfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }

        tijianDetail1OfService.setCityName((String) tijianDetail1OfServiceView.getCascader().get(1));

        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", (String) tijianDetail1OfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            tijianDetail1OfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (tijianDetail1OfServiceView.getCascader().size() !=3) {
            tijianDetail1OfService.setDistrictName((String) tijianDetail1OfServiceView.getCascader().get(1));
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("name", (String) tijianDetail1OfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                tijianDetail1OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            tijianDetail1OfService.setDistrictName((String) tijianDetail1OfServiceView.getCascader().get(2));
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("name", (String) tijianDetail1OfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                tijianDetail1OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }


        QueryWrapper<ServiceOfRegister> queryWrapper2 = new QueryWrapper();
        queryWrapper2.eq("id", sysUser.getCompanyId());
        List<ServiceOfRegister> list2 = serviceOfRegisterService.list(queryWrapper2);
        for (ServiceOfRegister serviceOfRegister : list2) {
            QueryWrapper<TijianBasicOfService> queryWrapper3 = new QueryWrapper();
            queryWrapper3.eq("name", serviceOfRegister.getName());
            List<TijianBasicOfService> list3 = tijianBasicOfServiceService.list(queryWrapper3);
            for (TijianBasicOfService tijianBasicOfService : list3) {
                tijianDetail1OfService.setTijianBasicId(tijianBasicOfService.getId());
                flag=tijianDetail1OfServiceService.save(tijianDetail1OfService);
            }
        }
        return flag;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return tijianDetail1OfServiceService.removeById(id);
    }
    @GetMapping("/getById")
    public TijianDetail1OfService getById(Integer id) {

        List list=new ArrayList();
        TijianDetail1OfServiceView tijianDetail1OfServiceView=new TijianDetail1OfServiceView();
        TijianDetail1OfService tijianDetail1OfService = tijianDetail1OfServiceService.getById(id);
        BeanUtils.copyProperties(tijianDetail1OfService, tijianDetail1OfServiceView);
        String provinceName = tijianDetail1OfService.getProvinceName();
        String cityName = tijianDetail1OfService.getCityName();
        String districtName = tijianDetail1OfService.getDistrictName();
        list.add(provinceName);
        if(cityName.equals(districtName)){
            list.add(cityName);
        }else {
            list.add(cityName);
            list.add(districtName);
        }

        tijianDetail1OfServiceView.setCascader((ArrayList) list);
        return tijianDetail1OfServiceView;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody TijianDetail1OfServiceView tijianDetail1OfServiceView) {
        TijianDetail1OfService tijianDetail1OfService = new TijianDetail1OfServiceView();

        BeanUtils.copyProperties(tijianDetail1OfServiceView, tijianDetail1OfService);

        tijianDetail1OfService.setProvinceName((String) tijianDetail1OfServiceView.getCascader().get(0));

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", (String) tijianDetail1OfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            tijianDetail1OfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }

        tijianDetail1OfService.setCityName((String) tijianDetail1OfServiceView.getCascader().get(1));

        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", (String) tijianDetail1OfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            tijianDetail1OfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (tijianDetail1OfServiceView.getCascader().size() !=3) {
            tijianDetail1OfService.setDistrictName((String) tijianDetail1OfServiceView.getCascader().get(1));
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("name", (String) tijianDetail1OfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                tijianDetail1OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            tijianDetail1OfService.setDistrictName((String) tijianDetail1OfServiceView.getCascader().get(2));
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("name", (String) tijianDetail1OfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                tijianDetail1OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }


        return tijianDetail1OfServiceService.updateById(tijianDetail1OfService);
    }

}
