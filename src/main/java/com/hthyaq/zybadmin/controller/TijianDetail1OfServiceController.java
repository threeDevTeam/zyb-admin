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
import com.hthyaq.zybadmin.model.bean.Child2;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.vo.TijianBasicOfServiceView;
import com.hthyaq.zybadmin.model.vo.TijianDetail1OfServiceView;
import com.hthyaq.zybadmin.service.*;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.formula.functions.T;
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
        QueryWrapper<ServiceOfRegister> qw = new QueryWrapper();
        qw.eq("name", sysUser.getCompanyName());
        List<ServiceOfRegister> list4 = serviceOfRegisterService.list(qw);
        for (ServiceOfRegister serviceOfRegister : list4) {
            if (serviceOfRegister.getType().equals("体检机构")) {
                //demo
                TijianDetail1OfService tijianDetail1OfService = new TijianDetail1OfService();
                BeanUtils.copyProperties(tijianDetail1OfServiceView, tijianDetail1OfService);
                QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", tijianDetail1OfServiceView.getCascader().get(0));
                List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
                for (AreaOfDic areaOfDic : list) {
                    tijianDetail1OfService.setProvinceName(String.valueOf(areaOfDic.getName()));
                    tijianDetail1OfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
                }

                QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id", tijianDetail1OfServiceView.getCascader().get(1));
                List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
                for (AreaOfDic areaOfDic : list1) {
                    tijianDetail1OfService.setCityName(String.valueOf(areaOfDic.getName()));
                    tijianDetail1OfService.setCityCode(String.valueOf(areaOfDic.getCode()));
                }

                if (tijianDetail1OfServiceView.getCascader().size() != 3) {
                    QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
                    queryWrapper3.eq("id", tijianDetail1OfServiceView.getCascader().get(1));
                    List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
                    for (AreaOfDic areaOfDic : list3) {
                        tijianDetail1OfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                        tijianDetail1OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
                    }
                } else {
                    QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("id", tijianDetail1OfServiceView.getCascader().get(2));
                    List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
                    for (AreaOfDic areaOfDic : list2) {
                        tijianDetail1OfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                        tijianDetail1OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
                    }
                }
                QueryWrapper<TijianBasicOfService> qw1 = new QueryWrapper();
                qw1.eq("name", serviceOfRegister.getName());
                List<TijianBasicOfService> list5 = tijianBasicOfServiceService.list(qw1);
                for (TijianBasicOfService tijianBasicOfService : list5) {
                    tijianDetail1OfService.setTijianBasicId(tijianBasicOfService.getId());
                }
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
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", tijianDetail1OfService.getProvinceCode());
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list1) {
            list.add(areaOfDic.getId());
        }
        QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("code",tijianDetail1OfService.getCityCode());
        List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
        for (AreaOfDic areaOfDic : list2) {
            list.add(areaOfDic.getId());
        }


        if (tijianDetail1OfService.getCityName().equals(tijianDetail1OfService.getDistrictName())) {
            for (AreaOfDic areaOfDic : list2) {
                list.add(areaOfDic.getId());
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("code",tijianDetail1OfService.getDistrictCode());
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                list.add(areaOfDic.getId());
            }
        }

        tijianDetail1OfServiceView.setCascader((ArrayList) list);
        return tijianDetail1OfServiceView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody TijianDetail1OfServiceView tijianDetail1OfServiceView) {
        TijianDetail1OfService tijianDetail1OfService = new TijianDetail1OfServiceView();

        BeanUtils.copyProperties(tijianDetail1OfServiceView, tijianDetail1OfService);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",tijianDetail1OfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            tijianDetail1OfService.setProvinceName(String.valueOf(areaOfDic.getName()));
            tijianDetail1OfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }
        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", tijianDetail1OfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            tijianDetail1OfService.setCityName(String.valueOf(areaOfDic.getName()));

            tijianDetail1OfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (tijianDetail1OfServiceView.getCascader().size() !=3) {
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("id", tijianDetail1OfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                tijianDetail1OfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                tijianDetail1OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", tijianDetail1OfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                tijianDetail1OfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                tijianDetail1OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }
        return tijianDetail1OfServiceService.updateById(tijianDetail1OfService);
    }
    @GetMapping("/list")
    public IPage<TijianDetail1OfService> list(String json, HttpSession httpSession) {
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

        QueryWrapper<TijianDetail1OfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tijianBasicId", list1.get(0));
        if (!Strings.isNullOrEmpty(enterpriseName)) {
            queryWrapper.eq("enterpriseName", enterpriseName);
        }
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }

        IPage<TijianDetail1OfService> page = tijianDetail1OfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
