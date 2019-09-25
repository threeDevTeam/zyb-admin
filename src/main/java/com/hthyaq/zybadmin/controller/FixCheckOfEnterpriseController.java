package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.bean.Child;
import com.hthyaq.zybadmin.model.bean.Child3;
import com.hthyaq.zybadmin.model.entity.Demo;
import com.hthyaq.zybadmin.model.entity.DemoCourse;
import com.hthyaq.zybadmin.model.entity.FixCheckOfEnterprise;
import com.hthyaq.zybadmin.model.entity.FixCheckResultOfEnterprise;
import com.hthyaq.zybadmin.model.vo.DemoView;
import com.hthyaq.zybadmin.model.vo.FixCheckOfView;
import com.hthyaq.zybadmin.service.DemoCourseService;
import com.hthyaq.zybadmin.service.DemoService;
import com.hthyaq.zybadmin.service.FixCheckOfEnterpriseService;
import com.hthyaq.zybadmin.service.FixCheckResultOfEnterpriseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 定期检测信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-02
 */
@RestController
@RequestMapping("/fixCheckOfEnterprise")
public class FixCheckOfEnterpriseController {
    @Autowired
    FixCheckOfEnterpriseService fixCheckOfEnterpriseService;
    @Autowired
    FixCheckResultOfEnterpriseService fixCheckResultOfEnterpriseService;
    @PostMapping("/add")
    public boolean add(@RequestBody FixCheckOfView fixCheckOfView) {
        return fixCheckOfEnterpriseService.saveData(fixCheckOfView);
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return fixCheckOfEnterpriseService.deleteData(id);
    }

    @GetMapping("/getById")
    public FixCheckOfView getById(Integer id) {
        FixCheckOfView fixCheckOfView = new FixCheckOfView();

        //demo
        FixCheckOfEnterprise fixCheckOfEnterprise = fixCheckOfEnterpriseService.getById(id);
        //将demo的数据设置到demoData
        BeanUtils.copyProperties(fixCheckOfEnterprise, fixCheckOfView);

        //demoCourse
        QueryWrapper<FixCheckResultOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("demo_id", id);
        List<FixCheckResultOfEnterprise> demoCourseList = fixCheckResultOfEnterpriseService.list(queryWrapper);

        //将demoCourse的数据设置到demoData
        fixCheckOfView.setCourse(new Child3<>(demoCourseList));
        return fixCheckOfView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody FixCheckOfView fixCheckOfView) {
        return fixCheckOfEnterpriseService.editData(fixCheckOfView);
    }


    @GetMapping("/list")
    public IPage< FixCheckOfEnterprise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);jsonObject.getObject("zbry",String.class);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String decideResult = jsonObject.getString("decideResult");
        QueryWrapper< FixCheckOfEnterprise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(decideResult)) {
            queryWrapper.eq("decideResult", decideResult);
        }

        IPage< FixCheckOfEnterprise> page = fixCheckOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
