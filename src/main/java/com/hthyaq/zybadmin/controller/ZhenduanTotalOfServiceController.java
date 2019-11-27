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
import com.hthyaq.zybadmin.model.excelModel.ServiceOfRegisterModel;
import com.hthyaq.zybadmin.model.excelModel.ZhenduanBasicOfServiceModel;
import com.hthyaq.zybadmin.model.excelModel.ZhenduanTotalOfServiceModel;
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
 * 诊断机构的总体信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/zhenduanTotalOfService")
public class ZhenduanTotalOfServiceController {
    @Autowired
    ZhenduanTotalOfServiceService zhenduanTotalOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    ZhenduanBasicOfServiceService zhenduanBasicOfServiceService;
    @Autowired
    SysRoleUserService sysRoleUserService;
    @PostMapping("/add")
    public boolean add(@RequestBody ZhenduanTotalOfService zhenduanTotalOfService, HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<ServiceOfRegister> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", sysUser.getCompanyId());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper);
        for (ServiceOfRegister serviceOfRegister : list) {
            if (serviceOfRegister.getType().equals("诊断机构")) {
                QueryWrapper<ZhenduanBasicOfService> queryWrapper1 = new QueryWrapper();
                queryWrapper1.eq("name", serviceOfRegister.getName());
                List<ZhenduanBasicOfService> list1 = zhenduanBasicOfServiceService.list(queryWrapper1);
                for (ZhenduanBasicOfService zhenduanBasicOfService : list1) {
                    zhenduanTotalOfService.setZhenduanBasicId(zhenduanBasicOfService.getId());
                    int count = zhenduanBasicOfServiceService.count();
                    if(count != 0) {
                        zhenduanTotalOfService.setCount3(count);
                    }
                    flag = zhenduanTotalOfServiceService.save(zhenduanTotalOfService);
                }
            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return zhenduanTotalOfServiceService.removeById(id);
    }

    @GetMapping("/getById")
    public ZhenduanTotalOfService getById(Integer id) {

        ZhenduanTotalOfService zhenduanTotalOfService = zhenduanTotalOfServiceService.getById(id);
        //demoCourse
        QueryWrapper<ZhenduanTotalOfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<ZhenduanTotalOfService> demoCourseList = zhenduanTotalOfServiceService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return zhenduanTotalOfService;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody ZhenduanTotalOfService zhenduanTotalOfService) {
        return zhenduanTotalOfServiceService.updateById(zhenduanTotalOfService);
    }

    @GetMapping("/list")
    public IPage<ZhenduanTotalOfService> list(String json, HttpSession httpSession) {
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
            QueryWrapper<ZhenduanTotalOfService> queryWrapper = new QueryWrapper<>();
            if (!Strings.isNullOrEmpty(year)) {
                queryWrapper.like("year", year);
            }
            queryWrapper.orderByDesc("id");
            IPage<ZhenduanTotalOfService> page = zhenduanTotalOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        } else {
            QueryWrapper<ServiceOfRegister> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", sysUser.getCompanyName());
            List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper1);
            for (ServiceOfRegister serviceOfRegister : list) {
                if (serviceOfRegister.getType().equals("诊断机构")) {
                    QueryWrapper<ZhenduanBasicOfService> queryWrapper2 = new QueryWrapper();
                    queryWrapper2.eq("name", serviceOfRegister.getName());
                    List<ZhenduanBasicOfService> list2 = zhenduanBasicOfServiceService.list(queryWrapper2);
                    for (ZhenduanBasicOfService zhenduanBasicOfService : list2) {
                        list1.clear();
                        list1.add(zhenduanBasicOfService.getId());
                    }
                }
            }

            QueryWrapper<ZhenduanTotalOfService> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("zhenduanBasicId", list1.get(0));
            if (!Strings.isNullOrEmpty(year)) {
                queryWrapper.like("year", year);
            }
            queryWrapper.orderByDesc("id");
            IPage<ZhenduanTotalOfService> page = zhenduanTotalOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0] =ZhenduanTotalOfServiceModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files, modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<ZhenduanTotalOfService> dataList = getDataList(modelList, type,httpSession);
            flag = zhenduanTotalOfServiceService.saveBatch(dataList);
        }
        return flag;
    }

    private List<ZhenduanTotalOfService> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<ZhenduanTotalOfService> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            ZhenduanTotalOfServiceModel zhenduanTotalOfServiceModel = (ZhenduanTotalOfServiceModel) object;
            //业务处理
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<ServiceOfRegister> queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", sysUser.getCompanyId());
            ZhenduanTotalOfService zhenduanTotalOfService = new ZhenduanTotalOfService();
            List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper);
            for (ServiceOfRegister serviceOfRegister : list) {
                QueryWrapper<ZhenduanBasicOfService> queryWrapper1 = new QueryWrapper();
                queryWrapper1.eq("name", serviceOfRegister.getName());
                List<ZhenduanBasicOfService> list1 = zhenduanBasicOfServiceService.list(queryWrapper1);
                for (ZhenduanBasicOfService zhenduanBasicOfService : list1) {
                    zhenduanTotalOfService.setZhenduanBasicId(zhenduanBasicOfService.getId());
                }
                BeanUtils.copyProperties(zhenduanTotalOfServiceModel, zhenduanTotalOfService);
                dataList.add(zhenduanTotalOfService);
            }
        }
        return dataList;
    }
}



