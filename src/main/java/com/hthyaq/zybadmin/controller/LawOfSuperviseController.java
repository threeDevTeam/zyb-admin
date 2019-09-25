package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.LawOfSuperviseService;
import com.hthyaq.zybadmin.service.PersonOfSuperviseService;
import com.hthyaq.zybadmin.service.SuperviseOfRegisterService;
import com.hthyaq.zybadmin.service.SuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 法规标准建设信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/lawOfSupervise")
public class LawOfSuperviseController {
    @Autowired
    SuperviseOfRegisterService superviseOfRegisterService;
    @Autowired
    SuperviseService superviseService;
    @Autowired
    LawOfSuperviseService lawOfSuperviseService;
    @PostMapping("/add")
    public boolean add(@RequestBody LawOfSupervise lawOfSupervise, HttpSession httpSession) {
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
                lawOfSupervise.setSuperviseId(supervise.getId());
                flag = lawOfSuperviseService.save(lawOfSupervise);
            }
        }
        return flag;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return lawOfSuperviseService.removeById(id);
    }
    @GetMapping("/getById")
    public LawOfSupervise getById(Integer id) {

        LawOfSupervise lawOfSupervise = lawOfSuperviseService.getById(id);
        //demoCourse
        QueryWrapper<LawOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<LawOfSupervise> demoCourseList = lawOfSuperviseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return lawOfSupervise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody LawOfSupervise lawOfSupervise) {
        return lawOfSuperviseService.updateById(lawOfSupervise);
    }

    @GetMapping("/list")
    public IPage<LawOfSupervise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        QueryWrapper<LawOfSupervise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(year)) {
            queryWrapper.eq("year", year);
        }

        IPage<LawOfSupervise> page = lawOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
