package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.EducationOfSupervise;
import com.hthyaq.zybadmin.model.entity.Supervise;
import com.hthyaq.zybadmin.model.entity.SuperviseOfRegister;
import com.hthyaq.zybadmin.model.entity.SysUser;
import com.hthyaq.zybadmin.service.EducationOfSuperviseService;
import com.hthyaq.zybadmin.service.SuperviseOfRegisterService;
import com.hthyaq.zybadmin.service.SuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 教育培训情况 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/educationOfSupervise")
public class EducationOfSuperviseController {
    @Autowired
    SuperviseOfRegisterService superviseOfRegisterService;
    @Autowired
    SuperviseService superviseService;
    @Autowired
    EducationOfSuperviseService educationOfSuperviseService;
    @PostMapping("/add")
    public boolean add(@RequestBody EducationOfSupervise educationOfSupervise, HttpSession httpSession) {
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
                educationOfSupervise.setSuperviseId(supervise.getId());
                flag = educationOfSuperviseService.save(educationOfSupervise);
            }
        }
        return flag;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return educationOfSuperviseService.removeById(id);
    }
    @GetMapping("/getById")
    public EducationOfSupervise getById(Integer id) {

        EducationOfSupervise educationOfSupervise = educationOfSuperviseService.getById(id);
        //demoCourse
        QueryWrapper<EducationOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<EducationOfSupervise> demoCourseList = educationOfSuperviseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return educationOfSupervise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody EducationOfSupervise educationOfSupervise) {
        return educationOfSuperviseService.updateById(educationOfSupervise);
    }

    @GetMapping("/list")
    public IPage<EducationOfSupervise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);

        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        String personCount = jsonObject.getString("personCount");
        QueryWrapper<EducationOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("superviseId",sysUser.getCompanyId());
        if (!Strings.isNullOrEmpty(year)) {
            queryWrapper.eq("year", year);
        }
        if (!Strings.isNullOrEmpty(personCount)) {
            queryWrapper.eq("personCount", personCount);
        }

        IPage<EducationOfSupervise> page = educationOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
