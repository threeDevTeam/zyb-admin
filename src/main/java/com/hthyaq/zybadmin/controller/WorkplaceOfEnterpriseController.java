package com.hthyaq.zybadmin.controller;


import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.common.excle.MyExcelUtil;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.excelModel.EnterpriseModel;
import com.hthyaq.zybadmin.model.excelModel.WorkplaceOfEnterpriseModel;
import com.hthyaq.zybadmin.service.*;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工作场所 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/workplaceOfEnterprise")
public class WorkplaceOfEnterpriseController {
    @Autowired
    WorkplaceOfEnterpriseService workplaceOfEnterpriseService;
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    SysRoleUserService sysRoleUserService;
    @PostMapping("/add")
    public boolean add(@RequestBody WorkplaceOfEnterprise workplaceOfEnterprise, HttpSession httpSession) {
        boolean flag=false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("name",sysUser.getCompanyName());
            List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list1) {
                workplaceOfEnterprise.setEnterpriseId(enterprise.getId());
                flag=  workplaceOfEnterpriseService.save(workplaceOfEnterprise);
        }
        return flag;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return workplaceOfEnterpriseService.removeById(id);
    }
    @GetMapping("/getById")
    public WorkplaceOfEnterprise getById(Integer id) {

        WorkplaceOfEnterprise workplaceOfEnterprise = workplaceOfEnterpriseService.getById(id);
        //demoCourse
        QueryWrapper<WorkplaceOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<WorkplaceOfEnterprise> demoCourseList = workplaceOfEnterpriseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return workplaceOfEnterprise;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody WorkplaceOfEnterprise workplaceOfEnterprise) {
        return workplaceOfEnterpriseService.updateById(workplaceOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<WorkplaceOfEnterprise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String username = jsonObject.getString("username");
        QueryWrapper<SysRoleUser> qw = new QueryWrapper<>();
        qw.eq("userId", sysUser.getId());
        SysRoleUser sysRoleUser = sysRoleUserService.getOne(qw);
        if (sysRoleUser.getRoleId() == 1) {
            QueryWrapper<WorkplaceOfEnterprise> queryWrapper = new QueryWrapper<>();
            if (!Strings.isNullOrEmpty(username)) {
                queryWrapper.like("username", username);
            }
            queryWrapper.orderByDesc("id");
            IPage<WorkplaceOfEnterprise> page = workplaceOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        } else {
            QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("name", sysUser.getCompanyName());
            List<Enterprise> list = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list) {
                list1.clear();
                Long id = enterprise.getId();
                list1.add(id);
            }
            QueryWrapper<WorkplaceOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("enterpriseId", list1.get(0));
            if (!Strings.isNullOrEmpty(username)) {
                queryWrapper.like("username", username);
            }
            queryWrapper.orderByDesc("id");
            IPage<WorkplaceOfEnterprise> page = workplaceOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= WorkplaceOfEnterpriseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<WorkplaceOfEnterprise> dataList = getDataList(modelList, type,httpSession);
            flag = workplaceOfEnterpriseService.saveBatch(dataList);
        }
        return flag;
    }
    private List<WorkplaceOfEnterprise> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<WorkplaceOfEnterprise> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            WorkplaceOfEnterpriseModel workplaceOfEnterpriseModel = (WorkplaceOfEnterpriseModel) object;
            //业务处理
            WorkplaceOfEnterprise workplaceOfEnterprise = new WorkplaceOfEnterprise();
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("name",sysUser.getCompanyName());
            List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list1) {
                workplaceOfEnterprise.setEnterpriseId(enterprise.getId());}
            BeanUtils.copyProperties(workplaceOfEnterpriseModel, workplaceOfEnterprise);
            dataList.add(workplaceOfEnterprise);
        }
        return dataList;
    }
}
