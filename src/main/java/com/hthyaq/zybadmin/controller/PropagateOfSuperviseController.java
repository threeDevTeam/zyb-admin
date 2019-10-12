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
import com.hthyaq.zybadmin.model.excelModel.EducationOfSuperviseModel;
import com.hthyaq.zybadmin.model.excelModel.PropagateOfSuperviseModel;
import com.hthyaq.zybadmin.model.excelModel.SuperviseModel;
import com.hthyaq.zybadmin.service.EducationOfSuperviseService;
import com.hthyaq.zybadmin.service.PropagateOfSuperviseService;
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
 * 职业健康宣传信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/propagateOfSupervise")
public class PropagateOfSuperviseController {
    @Autowired
    SuperviseOfRegisterService superviseOfRegisterService;
    @Autowired
    SuperviseService superviseService;
    @Autowired
    PropagateOfSuperviseService propagateOfSuperviseService;

    @PostMapping("/add")
    public boolean add(@RequestBody PropagateOfSupervise propagateOfSupervise, HttpSession httpSession) {
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
                propagateOfSupervise.setSuperviseId(supervise.getId());
                flag = propagateOfSuperviseService.save(propagateOfSupervise);
            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return propagateOfSuperviseService.removeById(id);
    }

    @GetMapping("/getById")
    public PropagateOfSupervise getById(Integer id) {

        PropagateOfSupervise propagateOfSupervise = propagateOfSuperviseService.getById(id);
        //demoCourse
        QueryWrapper<PropagateOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<PropagateOfSupervise> demoCourseList = propagateOfSuperviseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return propagateOfSupervise;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody PropagateOfSupervise propagateOfSupervise) {
        return propagateOfSuperviseService.updateById(propagateOfSupervise);
    }

    @GetMapping("/list")
    public IPage<PropagateOfSupervise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        String acceptCount = jsonObject.getString("acceptCount");
        QueryWrapper<Supervise> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<Supervise> list = superviseService.list(queryWrapper1);
        for (Supervise supervise : list) {
            list1.clear();
            Long id = supervise.getId();
            list1.add(id);
        }
        QueryWrapper<PropagateOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("superviseId", list1.get(0));
        if (!Strings.isNullOrEmpty(year)) {
            queryWrapper.eq("year", year);
        }
        if (!Strings.isNullOrEmpty(acceptCount)) {
            queryWrapper.eq("acceptCount", acceptCount);
        }

        IPage<PropagateOfSupervise> page = propagateOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= PropagateOfSuperviseModel.class;
        Map<String, List<Object>> modelMap =MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<PropagateOfSupervise> dataList = getDataList(modelList, type, httpSession);
            flag = propagateOfSuperviseService.saveBatch(dataList);
        }
        return flag;
    }

    private List<PropagateOfSupervise> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<PropagateOfSupervise> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            PropagateOfSuperviseModel propagateOfSuperviseModel = (PropagateOfSuperviseModel) object;
            //业务处理
            PropagateOfSupervise propagateOfSupervise = new PropagateOfSupervise();
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<SuperviseOfRegister> queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", sysUser.getCompanyId());
            List<SuperviseOfRegister> list = superviseOfRegisterService.list(queryWrapper);
            for (SuperviseOfRegister superviseOfRegister : list) {
                QueryWrapper<Supervise> queryWrapper1 = new QueryWrapper();
                queryWrapper1.eq("name", superviseOfRegister.getName());
                List<Supervise> list1 = superviseService.list(queryWrapper1);
                for (Supervise supervise : list1) {
                    propagateOfSupervise.setSuperviseId(supervise.getId());
                }
            }
            BeanUtils.copyProperties(propagateOfSuperviseModel, propagateOfSupervise);
            dataList.add(propagateOfSupervise);
        }
        return dataList;
    }
}
