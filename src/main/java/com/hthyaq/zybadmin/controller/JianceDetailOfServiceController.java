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
import com.hthyaq.zybadmin.model.vo.JianceDetailOfServiceView;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 检测机构的具体报告
 * <p>
 * 检测机构的具体报告
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/jianceDetailOfService")
public class JianceDetailOfServiceController {
    @Autowired
    JianceDetailOfServiceService jianceDetailOfServiceService;
    @Autowired
    JianceDetailResultOfServiceService jianceDetailResultOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    JianceBasicOfServiceService jianceBasicOfServiceService;
    @Autowired
    AreaOfDicService areaOfDicService;

    @PostMapping("/add")
    public boolean add(@RequestBody JianceDetailOfServiceView jianceDetailOfServiceView, HttpSession httpSession) {
        return jianceDetailOfServiceService.saveData(jianceDetailOfServiceView, httpSession);
    }


    @GetMapping("/delete")
    public boolean delete(String id) {
        return jianceDetailOfServiceService.deleteData(id);
    }

    @GetMapping("/getById")
    public JianceDetailOfServiceView getById(Integer id) {
        JianceDetailOfServiceView jianceDetailOfServiceView = new JianceDetailOfServiceView();
        List list = new ArrayList();
        //demo
        JianceDetailOfService jianceDetailOfService = jianceDetailOfServiceService.getById(id);
        //将demo的数据设置到demoData
        BeanUtils.copyProperties(jianceDetailOfService, jianceDetailOfServiceView);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", jianceDetailOfService.getProvinceCode());
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list1) {
            list.add(areaOfDic.getId());
        }
        QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("code", jianceDetailOfService.getCityCode());
        List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
        for (AreaOfDic areaOfDic : list2) {
            list.add(areaOfDic.getId());
        }


        if (jianceDetailOfService.getCityName().equals(jianceDetailOfService.getDistrictName())) {
            for (AreaOfDic areaOfDic : list2) {
                list.add(areaOfDic.getId());
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("code", jianceDetailOfService.getDistrictCode());
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                list.add(areaOfDic.getId());
            }
        }


        jianceDetailOfServiceView.setCascader((ArrayList) list);
        //demoCourse
        QueryWrapper<JianceDetailResultOfService> queryWrapper4 = new QueryWrapper<>();
        queryWrapper4.eq("jianceDetailId", id);
        List<JianceDetailResultOfService> demoCourseList = jianceDetailResultOfServiceService.list(queryWrapper4);

        //将demoCourse的数据设置到demoData
        jianceDetailOfServiceView.setCourse(new Child2<>(demoCourseList));
        return jianceDetailOfServiceView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody JianceDetailOfServiceView jianceDetailOfServiceView) {
        return jianceDetailOfServiceService.editData(jianceDetailOfServiceView);
    }


    @GetMapping("/list")
    public IPage<JianceDetailOfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String enterpriseName = jsonObject.getString("enterpriseName");
        String decideResult = jsonObject.getString("decideResult");
        QueryWrapper<ServiceOfRegister> queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper1);
        for (ServiceOfRegister serviceOfRegister : list) {
            if (serviceOfRegister.getType().equals("检测机构")) {
                QueryWrapper<JianceBasicOfService> queryWrapper2 = new QueryWrapper();
                queryWrapper2.eq("name", serviceOfRegister.getName());
                List<JianceBasicOfService> list2 = jianceBasicOfServiceService.list(queryWrapper2);
                for (JianceBasicOfService jianceBasicOfService : list2) {
                    list1.clear();
                    list1.add(jianceBasicOfService.getId());
                }
            }
        }

        QueryWrapper<JianceDetailOfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("jianceBasicId", list1.get(0));
        if (!Strings.isNullOrEmpty(enterpriseName)) {
            queryWrapper.eq("enterpriseName", enterpriseName);
        }
        if (!Strings.isNullOrEmpty(decideResult)) {
            queryWrapper.eq("decideResult", decideResult);
        }

        IPage<JianceDetailOfService> page = jianceDetailOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

}

