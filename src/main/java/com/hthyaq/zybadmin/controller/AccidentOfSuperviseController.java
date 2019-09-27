package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.AccidentOfSuperviseService;
import com.hthyaq.zybadmin.service.ServiceSuperviseOfSuperviseService;
import com.hthyaq.zybadmin.service.SuperviseOfRegisterService;
import com.hthyaq.zybadmin.service.SuperviseService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 职业病危害事故信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/accidentOfSupervise")
public class AccidentOfSuperviseController {
    @Autowired
    SuperviseOfRegisterService superviseOfRegisterService;
    @Autowired
    SuperviseService superviseService;
    @Autowired
    AccidentOfSuperviseService accidentOfSuperviseService;
    @PostMapping("/add")
    public boolean add(@RequestBody AccidentOfSupervise accidentOfSupervise, HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<SuperviseOfRegister> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", sysUser.getCompanyId());
        List<SuperviseOfRegister> list = superviseOfRegisterService.list(queryWrapper);
        for (SuperviseOfRegister superviseOfRegister : list) {
            QueryWrapper<Supervise> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", superviseOfRegister.getName());
            List<Supervise> list1 = superviseService.list(queryWrapper1);
            for (Supervise supervise : list1) {
                accidentOfSupervise.setSuperviseId(supervise.getId());
                flag = accidentOfSuperviseService.save(accidentOfSupervise);
            }
        }
        return flag;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return accidentOfSuperviseService.removeById(id);
    }
    @GetMapping("/getById")
    public AccidentOfSupervise getById(Integer id) {

        AccidentOfSupervise accidentOfSupervise = accidentOfSuperviseService.getById(id);
        //demoCourse
        QueryWrapper<AccidentOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<AccidentOfSupervise> demoCourseList = accidentOfSuperviseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return accidentOfSupervise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody AccidentOfSupervise accidentOfSupervise) {
        return accidentOfSuperviseService.updateById(accidentOfSupervise);
    }

    @GetMapping("/list")
    public IPage<AccidentOfSupervise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);

        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        String loseMoney = jsonObject.getString("loseMoney");
        QueryWrapper<AccidentOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("superviseId",sysUser.getCompanyId());
        if (!Strings.isNullOrEmpty(year)) {
            queryWrapper.eq("year", year);
        }
        if (!Strings.isNullOrEmpty(loseMoney)) {
            queryWrapper.eq("loseMoney", loseMoney);
        }

        IPage<AccidentOfSupervise> page = accidentOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
