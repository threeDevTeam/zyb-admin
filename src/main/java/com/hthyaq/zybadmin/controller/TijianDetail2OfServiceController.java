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
 * 体检机构的具体报告2 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/tijianDetail2OfService")
public class TijianDetail2OfServiceController {
    @Autowired
    TijianDetail2OfServiceService tijianDetail2OfServiceService;
    @Autowired
    TijianBasicOfServiceService tijianBasicOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    AreaOfDicService areaOfDicService;

    @PostMapping("/add")
    public boolean add(@RequestBody TijianDetail2OfServiceView tijianDetail2OfServiceView, HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);


        TijianDetail2OfService tijianDetail2OfService = new TijianDetail2OfServiceView();
        BeanUtils.copyProperties(tijianDetail2OfServiceView, tijianDetail2OfService);
        tijianDetail2OfService.setProvinceName((String) tijianDetail2OfServiceView.getCascader().get(0));

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", (String) tijianDetail2OfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            tijianDetail2OfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }

        tijianDetail2OfService.setCityName((String) tijianDetail2OfServiceView.getCascader().get(1));

        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", (String) tijianDetail2OfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            tijianDetail2OfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (tijianDetail2OfServiceView.getCascader().size() != 3) {
            tijianDetail2OfService.setDistrictName((String) tijianDetail2OfServiceView.getCascader().get(1));
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("name", (String) tijianDetail2OfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                tijianDetail2OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            tijianDetail2OfService.setDistrictName((String) tijianDetail2OfServiceView.getCascader().get(2));
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("name", (String) tijianDetail2OfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                tijianDetail2OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
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
                tijianDetail2OfService.setTijianBasicId(tijianBasicOfService.getId());
                flag = tijianDetail2OfServiceService.save(tijianDetail2OfService);
            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return tijianDetail2OfServiceService.removeById(id);
    }

    @GetMapping("/getById")
    public TijianDetail2OfService getById(Integer id) {

        List list = new ArrayList();
        TijianDetail2OfServiceView tijianDetail2OfServiceView = new TijianDetail2OfServiceView();
        TijianDetail2OfService tijianDetail2OfService = tijianDetail2OfServiceService.getById(id);
        BeanUtils.copyProperties(tijianDetail2OfService, tijianDetail2OfServiceView);
        String provinceName = tijianDetail2OfService.getProvinceName();
        String cityName = tijianDetail2OfService.getCityName();
        String districtName = tijianDetail2OfService.getDistrictName();
        list.add(provinceName);
        if (cityName.equals(districtName)) {
            list.add(cityName);
        } else {
            list.add(cityName);
            list.add(districtName);
        }

        tijianDetail2OfServiceView.setCascader((ArrayList) list);
        return tijianDetail2OfServiceView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody TijianDetail2OfServiceView tijianDetail2OfServiceView) {
        TijianDetail2OfService tijianDetail2OfService = new TijianDetail2OfServiceView();

        BeanUtils.copyProperties(tijianDetail2OfServiceView, tijianDetail2OfService);

        tijianDetail2OfService.setProvinceName((String) tijianDetail2OfServiceView.getCascader().get(0));

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", (String) tijianDetail2OfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            tijianDetail2OfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }

        tijianDetail2OfService.setCityName((String) tijianDetail2OfServiceView.getCascader().get(1));

        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", (String) tijianDetail2OfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            tijianDetail2OfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (tijianDetail2OfServiceView.getCascader().size() != 3) {
            tijianDetail2OfService.setDistrictName((String) tijianDetail2OfServiceView.getCascader().get(1));
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("name", (String) tijianDetail2OfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                tijianDetail2OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            tijianDetail2OfService.setDistrictName((String) tijianDetail2OfServiceView.getCascader().get(2));
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("name", (String) tijianDetail2OfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                tijianDetail2OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }


        return tijianDetail2OfServiceService.updateById(tijianDetail2OfService);
    }

    @GetMapping("/list")
    public IPage<TijianDetail2OfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);

        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String enterpriseName = jsonObject.getString("enterpriseName");
        String name = jsonObject.getString("name");
        QueryWrapper<TijianDetail2OfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", sysUser.getCompanyId());
        if (!Strings.isNullOrEmpty(enterpriseName)) {
            queryWrapper.eq("enterpriseName", enterpriseName);
        }
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }

        IPage<TijianDetail2OfService> page = tijianDetail2OfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }


}