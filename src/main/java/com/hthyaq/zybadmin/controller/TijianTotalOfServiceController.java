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
import com.hthyaq.zybadmin.model.excelModel.TijianBasicOfServiceModel;
import com.hthyaq.zybadmin.model.excelModel.TijianTotalOfServiceModel;
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
    @Autowired
    SysRoleUserService sysRoleUserService;
    @Autowired
    TijianDetail1OfServiceService tijianDetail1OfServiceService;
    @Autowired
    TijianDetail2OfServiceService tijianDetail2OfServiceService;
    @PostMapping("/add")
    public boolean add(@RequestBody TijianTotalOfService tijianTotalOfService, HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<ServiceOfRegister> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", sysUser.getCompanyId());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper);
        for (ServiceOfRegister serviceOfRegister : list) {
            if (serviceOfRegister.getType().equals("体检机构")) {
                QueryWrapper<TijianBasicOfService> queryWrapper1 = new QueryWrapper();
                queryWrapper1.eq("name", serviceOfRegister.getName());
                List<TijianBasicOfService> list1 = tijianBasicOfServiceService.list(queryWrapper1);
                for (TijianBasicOfService tijianBasicOfService : list1) {
                    tijianTotalOfService.setTijianBasicId(tijianBasicOfService.getId());
                    int count = tijianTotalOfServiceService.count();
                    if(count != 0){
                    tijianTotalOfService.setCount1(count+1);
                    }
                    int count1 = tijianDetail1OfServiceService.count(new QueryWrapper<TijianDetail1OfService>().eq("result", "职业禁忌证"));
                    if(count1 != 0){
                        tijianTotalOfService.setCount3(count1);
                    }

                    int count2 = tijianDetail2OfServiceService.count();
                    if(count2 != 0) {
                        tijianTotalOfService.setCount4(count2);
                    }
                    flag = tijianTotalOfServiceService.save(tijianTotalOfService);
                }
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
    public IPage<TijianTotalOfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        QueryWrapper<SysRoleUser> qw = new QueryWrapper<>();
        qw.eq("userId", sysUser.getId());
        SysRoleUser sysRoleUser = sysRoleUserService.getOne(qw);
        if (sysRoleUser.getRoleId() == 1) {
            QueryWrapper<TijianTotalOfService> queryWrapper = new QueryWrapper();
            if (!Strings.isNullOrEmpty(year)) {
                queryWrapper.like("year", year);
            }
            queryWrapper.orderByDesc("id");
            IPage<TijianTotalOfService> page = tijianTotalOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        } else {
            QueryWrapper<TijianTotalOfService> queryWrapper = new QueryWrapper();
            QueryWrapper<ServiceOfRegister> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", sysUser.getCompanyName());
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
            queryWrapper.eq("tijianBasicId", list1.get(0));
            if (!Strings.isNullOrEmpty(year)) {
                queryWrapper.like("year", year);
            }
            queryWrapper.orderByDesc("id");
            IPage<TijianTotalOfService> page = tijianTotalOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0] = TijianTotalOfServiceModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files, modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<TijianTotalOfService> dataList = getDataList(modelList, type, httpSession);
            flag = tijianTotalOfServiceService.saveBatch(dataList);
        }
        return flag;
    }

    private List<TijianTotalOfService> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<TijianTotalOfService> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            TijianTotalOfServiceModel tijianTotalOfServiceModel = (TijianTotalOfServiceModel) object;
            //业务处理
            TijianTotalOfService tijianTotalOfService = new TijianTotalOfService();
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
                    }
                    BeanUtils.copyProperties(tijianTotalOfServiceModel, tijianTotalOfService);
                    dataList.add(tijianTotalOfService);
                }
            }
            return dataList;
        }
    }
