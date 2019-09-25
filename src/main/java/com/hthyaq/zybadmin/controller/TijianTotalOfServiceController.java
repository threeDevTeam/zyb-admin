package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.ServiceOfRegister;
import com.hthyaq.zybadmin.model.entity.SysUser;
import com.hthyaq.zybadmin.model.entity.TijianBasicOfService;
import com.hthyaq.zybadmin.model.entity.TijianTotalOfService;
import com.hthyaq.zybadmin.service.ServiceOfRegisterService;
import com.hthyaq.zybadmin.service.TijianBasicOfServiceService;
import com.hthyaq.zybadmin.service.TijianTotalOfServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 体检机构的总体信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/tijianTotalOfService")
public class TijianTotalOfServiceController {
    @Autowired
    TijianTotalOfServiceService tijianTotalOfServiceService;
    @Autowired
    TijianBasicOfServiceService tijianBasicOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @PostMapping("/add")
    public boolean add(@RequestBody TijianTotalOfService tijianTotalOfService, HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<ServiceOfRegister> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", sysUser.getCompanyId());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper);
        for (ServiceOfRegister serviceOfRegister : list) {
            QueryWrapper<TijianBasicOfService> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", serviceOfRegister.getName());
            List<TijianBasicOfService> list1 = tijianBasicOfServiceService.list(queryWrapper1);
            for (TijianBasicOfService tijianBasicOfService : list1) {
                tijianTotalOfService.setTijianBasicId(tijianBasicOfService.getId());
                flag=tijianTotalOfServiceService.save(tijianTotalOfService);
            }
        }
        return flag;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return tijianTotalOfServiceService.removeById(id);
    }
    @GetMapping("/getById")
    public TijianTotalOfService getById(Integer id) {

        TijianTotalOfService tijianTotalOfService = tijianTotalOfServiceService.getById(id);
        //demoCourse
        QueryWrapper<TijianTotalOfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<TijianTotalOfService> demoCourseList = tijianTotalOfServiceService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return tijianTotalOfService;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody TijianTotalOfService tijianTotalOfService) {
        return tijianTotalOfServiceService.updateById(tijianTotalOfService);
    }

    @GetMapping("/list")
    public IPage<TijianTotalOfService> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        QueryWrapper<TijianTotalOfService> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(year)) {
            queryWrapper.eq("year", year);
        }
        IPage<TijianTotalOfService> page = tijianTotalOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
