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
import com.hthyaq.zybadmin.model.excelModel.PropagateOfSuperviseModel;
import com.hthyaq.zybadmin.model.excelModel.ServiceOfSuperviseModel;
import com.hthyaq.zybadmin.model.excelModel.SuperviseModel;
import com.hthyaq.zybadmin.service.PropagateOfSuperviseService;
import com.hthyaq.zybadmin.service.ServiceOfSuperviseService;
import com.hthyaq.zybadmin.service.SuperviseOfRegisterService;
import com.hthyaq.zybadmin.service.SuperviseService;
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
 * 检测机构信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/serviceOfSupervise")
public class ServiceOfSuperviseController {
    @Autowired
    SuperviseOfRegisterService superviseOfRegisterService;
    @Autowired
    SuperviseService superviseService;
    @Autowired
    ServiceOfSuperviseService serviceOfSuperviseService;

    @PostMapping("/add")
    public boolean add(@RequestBody ServiceOfSupervise serviceOfSupervise, HttpSession httpSession) {
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
                serviceOfSupervise.setSuperviseId(supervise.getId());
                flag = serviceOfSuperviseService.save(serviceOfSupervise);
            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return serviceOfSuperviseService.removeById(id);
    }

    @GetMapping("/getById")
    public ServiceOfSupervise getById(Integer id) {

        ServiceOfSupervise serviceOfSupervise = serviceOfSuperviseService.getById(id);
        //demoCourse
        QueryWrapper<ServiceOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<ServiceOfSupervise> demoCourseList = serviceOfSuperviseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return serviceOfSupervise;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody ServiceOfSupervise serviceOfSupervise) {
        return serviceOfSuperviseService.editData(serviceOfSupervise);
    }


    @GetMapping("/list")
    public IPage<ServiceOfSupervise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        String jianceLevel = jsonObject.getString("jianceLevel");
        QueryWrapper<Supervise> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<Supervise> list = superviseService.list(queryWrapper1);
        for (Supervise supervise : list) {
            list1.clear();
            Long id = supervise.getId();
            list1.add(id);
        }
        QueryWrapper<ServiceOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("superviseId", list1.get(0));
        if (!Strings.isNullOrEmpty(year)) {
            queryWrapper.eq("year", year);
        }
        if (!Strings.isNullOrEmpty(jianceLevel)) {
            queryWrapper.eq("jianceLevel", jianceLevel);
        }

        IPage<ServiceOfSupervise> page = serviceOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= ServiceOfSuperviseModel.class;
        Map<String, List<Object>> modelMap =MyExcelUtil.readMoreSheetExcel(files, modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<ServiceOfSupervise> dataList = getDataList(modelList, type, httpSession);
            flag = serviceOfSuperviseService.saveBatch(dataList);
        }
        return flag;
    }

    private List<ServiceOfSupervise> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<ServiceOfSupervise> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            ServiceOfSuperviseModel serviceOfSuperviseModel = (ServiceOfSuperviseModel) object;
            //业务处理
            ServiceOfSupervise serviceOfSupervise = new ServiceOfSupervise();
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<SuperviseOfRegister> queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", sysUser.getCompanyId());
            List<SuperviseOfRegister> list = superviseOfRegisterService.list(queryWrapper);
            for (SuperviseOfRegister superviseOfRegister : list) {
                QueryWrapper<Supervise> queryWrapper1 = new QueryWrapper();
                queryWrapper1.eq("name", superviseOfRegister.getName());
                List<Supervise> list1 = superviseService.list(queryWrapper1);
                for (Supervise supervise : list1) {
                    serviceOfSupervise.setSuperviseId(supervise.getId());
                }
            }
            BeanUtils.copyProperties(serviceOfSuperviseModel, serviceOfSupervise);
            dataList.add(serviceOfSupervise);
        }
        return dataList;
    }
}
