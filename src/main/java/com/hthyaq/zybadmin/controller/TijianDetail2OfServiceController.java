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
        QueryWrapper<ServiceOfRegister> qw = new QueryWrapper();
        qw.eq("name", sysUser.getCompanyName());
        List<ServiceOfRegister> list4 = serviceOfRegisterService.list(qw);
        for (ServiceOfRegister serviceOfRegister : list4) {
            if (serviceOfRegister.getType().equals("体检机构")) {
                //demo
                TijianDetail2OfService tijianDetail2OfService = new TijianDetail2OfService();
                BeanUtils.copyProperties(tijianDetail2OfServiceView, tijianDetail2OfService);
                QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", tijianDetail2OfServiceView.getCascader().get(0));
                List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
                for (AreaOfDic areaOfDic : list) {
                    tijianDetail2OfService.setProvinceName(String.valueOf(areaOfDic.getName()));
                    tijianDetail2OfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
                }

                QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id", tijianDetail2OfServiceView.getCascader().get(1));
                List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
                for (AreaOfDic areaOfDic : list1) {
                    tijianDetail2OfService.setCityName(String.valueOf(areaOfDic.getName()));
                    tijianDetail2OfService.setCityCode(String.valueOf(areaOfDic.getCode()));
                }

                if (tijianDetail2OfServiceView.getCascader().size() != 3) {
                    QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
                    queryWrapper3.eq("id", tijianDetail2OfServiceView.getCascader().get(1));
                    List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
                    for (AreaOfDic areaOfDic : list3) {
                        tijianDetail2OfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                        tijianDetail2OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
                    }
                } else {
                    QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("id", tijianDetail2OfServiceView.getCascader().get(2));
                    List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
                    for (AreaOfDic areaOfDic : list2) {
                        tijianDetail2OfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                        tijianDetail2OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
                    }
                }
                QueryWrapper<TijianBasicOfService> qw1 = new QueryWrapper();
                qw1.eq("name", serviceOfRegister.getName());
                List<TijianBasicOfService> list5 = tijianBasicOfServiceService.list(qw1);
                for (TijianBasicOfService tijianBasicOfService : list5) {
                    tijianDetail2OfService.setTijianBasicId(tijianBasicOfService.getId());
                }
                flag=tijianDetail2OfServiceService.save(tijianDetail2OfService);
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

        List list=new ArrayList();
        TijianDetail2OfServiceView tijianDetail2OfServiceView=new TijianDetail2OfServiceView();
        TijianDetail2OfService tijianDetail2OfService = tijianDetail2OfServiceService.getById(id);
        BeanUtils.copyProperties(tijianDetail2OfService, tijianDetail2OfServiceView);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", tijianDetail2OfService.getProvinceCode());
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list1) {
            list.add(areaOfDic.getId());
        }
        QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("code",tijianDetail2OfService.getCityCode());
        List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
        for (AreaOfDic areaOfDic : list2) {
            list.add(areaOfDic.getId());
        }


        if (tijianDetail2OfService.getCityName().equals(tijianDetail2OfService.getDistrictName())) {
            for (AreaOfDic areaOfDic : list2) {
                list.add(areaOfDic.getId());
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("code",tijianDetail2OfService.getDistrictCode());
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                list.add(areaOfDic.getId());
            }
        }

        tijianDetail2OfServiceView.setCascader((ArrayList) list);
        return tijianDetail2OfServiceView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody TijianDetail2OfServiceView tijianDetail2OfServiceView) {
        TijianDetail2OfService tijianDetail2OfService = new TijianDetail2OfServiceView();

        BeanUtils.copyProperties(tijianDetail2OfServiceView, tijianDetail2OfService);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",tijianDetail2OfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            tijianDetail2OfService.setProvinceName(String.valueOf(areaOfDic.getName()));
            tijianDetail2OfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }
        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", tijianDetail2OfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            tijianDetail2OfService.setCityName(String.valueOf(areaOfDic.getName()));

            tijianDetail2OfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (tijianDetail2OfServiceView.getCascader().size() !=3) {
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("id", tijianDetail2OfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                tijianDetail2OfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                tijianDetail2OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", tijianDetail2OfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                tijianDetail2OfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                tijianDetail2OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }
        return tijianDetail2OfServiceService.updateById(tijianDetail2OfService);
    }

    @GetMapping("/list")
    public IPage<TijianDetail2OfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String enterpriseName = jsonObject.getString("enterpriseName");
        String name = jsonObject.getString("name");
        QueryWrapper<ServiceOfRegister> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("name",sysUser.getCompanyName());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper1);
        for (ServiceOfRegister serviceOfRegister : list) {
            if (serviceOfRegister.getType().equals("体检机构")) {
                QueryWrapper<TijianBasicOfService> queryWrapper2 = new QueryWrapper();
                queryWrapper2.eq("name", serviceOfRegister.getName());
                List<TijianBasicOfService> list2 = tijianBasicOfServiceService.list(queryWrapper2);
                for (TijianBasicOfService tijianBasicOfService : list2) {
                    list1.clear();
                    list1.add(tijianBasicOfService.getId());
                }
            }
        }

        QueryWrapper<TijianDetail2OfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tijianBasicId", list1.get(0));
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