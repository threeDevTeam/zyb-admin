package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.bean.Child;
import com.hthyaq.zybadmin.model.bean.Child2;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.vo.DemoView;
import com.hthyaq.zybadmin.model.vo.JianceDetailOfServiceView;
import com.hthyaq.zybadmin.service.JianceBasicOfServiceService;
import com.hthyaq.zybadmin.service.JianceDetailOfServiceService;
import com.hthyaq.zybadmin.service.JianceDetailResultOfServiceService;
import com.hthyaq.zybadmin.service.ServiceOfRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @PostMapping("/add")
    public boolean add(@RequestBody JianceDetailOfServiceView jianceDetailOfServiceView, HttpSession httpSession) {
        return jianceDetailOfServiceService.saveData(jianceDetailOfServiceView,httpSession);
    }


    @GetMapping("/delete")
    public boolean delete(String id) {
        return jianceDetailOfServiceService.deleteData(id);
    }

    @GetMapping("/getById")
    public JianceDetailOfServiceView getById(Integer id) {
        JianceDetailOfServiceView jianceDetailOfServiceView = new JianceDetailOfServiceView();

        //demo
        JianceDetailOfService jianceDetailOfService = jianceDetailOfServiceService.getById(id);
        //将demo的数据设置到demoData
        BeanUtils.copyProperties(jianceDetailOfService, jianceDetailOfServiceView);

        //demoCourse
        QueryWrapper<JianceDetailResultOfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("jianceDetailId", id);
        List<JianceDetailResultOfService> demoCourseList = jianceDetailResultOfServiceService.list(queryWrapper);

        //将demoCourse的数据设置到demoData
        jianceDetailOfServiceView.setCourse(new Child2<>(demoCourseList));
        return jianceDetailOfServiceView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody JianceDetailOfServiceView jianceDetailOfServiceView) {
        return jianceDetailOfServiceService.editData(jianceDetailOfServiceView);
    }


    @GetMapping("/list")
    public IPage<JianceDetailOfService> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String enterpriseName = jsonObject.getString("enterpriseName");
        String decideResult = jsonObject.getString("decideResult");
        QueryWrapper<JianceDetailOfService> queryWrapper = new QueryWrapper<>();
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

