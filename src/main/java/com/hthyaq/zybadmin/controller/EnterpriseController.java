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
import com.hthyaq.zybadmin.model.vo.EnterpriseView;
import com.hthyaq.zybadmin.service.AreaOfDicService;
import com.hthyaq.zybadmin.service.EnterpriseOfRegisterService;
import com.hthyaq.zybadmin.service.EnterpriseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 生产工艺信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    AreaOfDicService areaOfDicService;
    @PostMapping("/add")
    public boolean add(@RequestBody  Enterprise enterprise, HttpSession httpSession) {
        boolean flag=false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<EnterpriseOfRegister> queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",sysUser.getCompanyId());
        List<EnterpriseOfRegister> list = enterpriseOfRegisterService.list(queryWrapper);
        for (EnterpriseOfRegister enterpriseOfRegister : list) {
            enterprise.setName(enterpriseOfRegister.getName());
            enterprise.setCode(enterpriseOfRegister.getCode());
            enterprise.setProvinceName(enterpriseOfRegister.getProvinceName());
            enterprise.setProvinceCode(enterpriseOfRegister.getProvinceCode());
            enterprise.setCityName(enterpriseOfRegister.getCityName());
            enterprise.setCityCode(enterpriseOfRegister.getCityCode());
            enterprise.setDistrictName(enterpriseOfRegister.getDistrictName());
            enterprise.setDistrictCode(enterpriseOfRegister.getDistrictCode());
            enterprise.setProductionCapacity(Integer.parseInt(enterpriseOfRegister.getProductionCapacity()));
            enterprise.setUnitType(enterpriseOfRegister.getUnitType());
            enterprise.setRegiterMoney(Double.parseDouble(enterpriseOfRegister.getRegiterMoney()));
            enterprise.setRegisterAddress(enterpriseOfRegister.getRegisterAddress());
            enterprise.setRegisterDate(Integer.parseInt(enterpriseOfRegister.getRegisterDate()));
            enterprise.setStartDate(Integer.parseInt(enterpriseOfRegister.getStartDate()));
            enterprise.setPropertyMoney(Double.parseDouble(enterpriseOfRegister.getPropertyMoney()));

            flag=enterpriseService.save(enterprise);
        }
        return flag;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return enterpriseService.removeById(id);
    }
    @GetMapping("/getById")
    public Enterprise getById(Integer id) {


        List list=new ArrayList();
        EnterpriseView enterpriseView=new EnterpriseView();
        Enterprise enterprise = enterpriseService.getById(id);
        BeanUtils.copyProperties(enterprise, enterpriseView);
        String provinceName = enterprise.getProvinceName();
        String cityName = enterprise.getCityName();
        String districtName = enterprise.getDistrictName();
        list.add(provinceName);
        if(cityName.equals(districtName)){
            list.add(cityName);
        }else {
            list.add(cityName);
            list.add(districtName);
        }

        enterpriseView.setCascader((ArrayList) list);
        return enterpriseView;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody EnterpriseView enterpriseView) {
        Enterprise enterprise=new Enterprise();

        BeanUtils.copyProperties(enterpriseView, enterprise);

        enterprise.setProvinceName((String) enterpriseView.getCascader().get(0));

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", (String) enterpriseView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            enterprise.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }

        enterprise.setCityName((String) enterpriseView.getCascader().get(1));

        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", (String) enterpriseView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            enterprise.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (enterpriseView.getCascader().size() !=3) {
            enterprise.setDistrictName((String) enterpriseView.getCascader().get(1));
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("name", (String) enterpriseView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                enterprise.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            enterprise.setDistrictName((String) enterpriseView.getCascader().get(2));
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("name", (String) enterpriseView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                enterprise.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }


        return enterpriseService.updateById(enterprise);
    }

    @GetMapping("/list")
    public IPage<Enterprise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);

        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",sysUser.getCompanyId());
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        IPage<Enterprise> page = enterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

}
