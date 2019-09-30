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
        QueryWrapper<ServiceOfRegister> qw = new QueryWrapper();
        qw.eq("name", sysUser.getCompanyName());
        List<ServiceOfRegister> list4 = serviceOfRegisterService.list(qw);
        for (ServiceOfRegister serviceOfRegister : list4) {
            if (serviceOfRegister.getType().equals("诊断机构")) {
                //demo
                ZhenduanDetailOfService zhenduanDetailOfService = new ZhenduanDetailOfService();
                BeanUtils.copyProperties(zhenduanDetailOfService, zhenduanDetailOfServiceView);
                QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", zhenduanDetailOfServiceView.getCascader().get(0));
                List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
                for (AreaOfDic areaOfDic : list) {
                    zhenduanDetailOfService.setProvinceName(String.valueOf(areaOfDic.getName()));
                    zhenduanDetailOfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
                }

                QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id", zhenduanDetailOfServiceView.getCascader().get(1));
                List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
                for (AreaOfDic areaOfDic : list1) {
                    zhenduanDetailOfService.setCityName(String.valueOf(areaOfDic.getName()));
                    zhenduanDetailOfService.setCityCode(String.valueOf(areaOfDic.getCode()));
                }

                if (zhenduanDetailOfServiceView.getCascader().size() != 3) {
                    QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
                    queryWrapper3.eq("id", zhenduanDetailOfServiceView.getCascader().get(1));
                    List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
                    for (AreaOfDic areaOfDic : list3) {
                        zhenduanDetailOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                        zhenduanDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
                    }
                } else {
                    QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("id", zhenduanDetailOfServiceView.getCascader().get(2));
                    List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
                    for (AreaOfDic areaOfDic : list2) {
                        zhenduanDetailOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                        zhenduanDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
                    }
                }
                QueryWrapper<ZhenduanBasicOfService> qw1 = new QueryWrapper();
                qw1.eq("name", serviceOfRegister.getName());
                List<ZhenduanBasicOfService> list5 = zhenduanBasicOfServiceService.list(qw1);
                for (ZhenduanBasicOfService zhenduanBasicOfService : list5) {
                    zhenduanDetailOfService.setZhenduanBasicId(zhenduanBasicOfService.getId());
                }
                flag=zhenduanDetailOfServiceService.save(zhenduanDetailOfService);
            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return zhenduanDetailOfServiceService.removeById(id);
    }

    @GetMapping("/getById")
    public ZhenduanDetailOfService getById(Integer id) {
        List list=new ArrayList();
        ZhenduanDetailOfServiceView zhenduanDetailOfServiceView=new ZhenduanDetailOfServiceView();
        ZhenduanDetailOfService zhenduanDetailOfService = zhenduanDetailOfServiceService.getById(id);
        BeanUtils.copyProperties(zhenduanDetailOfService, zhenduanDetailOfServiceView);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", zhenduanDetailOfService.getProvinceCode());
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list1) {
            list.add(areaOfDic.getId());
        }
        QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("code",zhenduanDetailOfService.getCityCode());
        List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
        for (AreaOfDic areaOfDic : list2) {
            list.add(areaOfDic.getId());
        }


        if (zhenduanDetailOfService.getCityName().equals(zhenduanDetailOfService.getDistrictName())) {
            for (AreaOfDic areaOfDic : list2) {
                list.add(areaOfDic.getId());
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("code",zhenduanDetailOfService.getDistrictCode());
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                list.add(areaOfDic.getId());
            }
        }

        zhenduanDetailOfServiceView.setCascader((ArrayList) list);
        return zhenduanDetailOfServiceView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody ZhenduanDetailOfServiceView zhenduanDetailOfServiceView) {

        ZhenduanDetailOfService zhenduanDetailOfService=new ZhenduanDetailOfServiceView();

        BeanUtils.copyProperties(zhenduanDetailOfServiceView, zhenduanDetailOfService);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",zhenduanDetailOfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            zhenduanDetailOfService.setProvinceName(String.valueOf(areaOfDic.getName()));
            zhenduanDetailOfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }
        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", zhenduanDetailOfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            zhenduanDetailOfService.setCityName(String.valueOf(areaOfDic.getName()));

            zhenduanDetailOfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (zhenduanDetailOfServiceView.getCascader().size() !=3) {
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("id", zhenduanDetailOfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                zhenduanDetailOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                zhenduanDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", zhenduanDetailOfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                zhenduanDetailOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                zhenduanDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }
        return zhenduanDetailOfServiceService.updateById(zhenduanDetailOfService);
    }

    @GetMapping("/list")
    public IPage<ZhenduanDetailOfService> list(String json, HttpSession httpSession) {
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
            if (serviceOfRegister.getType().equals("诊断机构")) {
                QueryWrapper<ZhenduanBasicOfService> queryWrapper2 = new QueryWrapper();
                queryWrapper2.eq("name", serviceOfRegister.getName());
                List<ZhenduanBasicOfService> list2 = zhenduanBasicOfServiceService.list(queryWrapper2);
                for (ZhenduanBasicOfService zhenduanBasicOfService : list2) {
                    list1.clear();
                    list1.add(zhenduanBasicOfService.getId());
                }
            }
        }

        QueryWrapper<ZhenduanDetailOfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("zhenduanBasicId", list1.get(0));
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

