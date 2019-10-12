package com.hthyaq.zybadmin.controller;


import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.common.excle.MyExcelUtil;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.excelModel.AccidentOfSuperviseModel;
import com.hthyaq.zybadmin.model.excelModel.JianceDetailOfServiceModel;
import com.hthyaq.zybadmin.model.excelModel.SuperviseModel;
import com.hthyaq.zybadmin.service.AccidentOfSuperviseService;
import com.hthyaq.zybadmin.service.ServiceSuperviseOfSuperviseService;
import com.hthyaq.zybadmin.service.SuperviseOfRegisterService;
import com.hthyaq.zybadmin.service.SuperviseService;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.formula.functions.T;
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
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        String loseMoney = jsonObject.getString("loseMoney");
        QueryWrapper<Supervise> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<Supervise> list = superviseService.list(queryWrapper1);
        for (Supervise supervise : list) {
            list1.clear();
            Long id = supervise.getId();
            list1.add(id);
        }
        QueryWrapper<AccidentOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("superviseId", list1.get(0));
        if (!Strings.isNullOrEmpty(year)) {
            queryWrapper.eq("year", year);
        }
        if (!Strings.isNullOrEmpty(loseMoney)) {
            queryWrapper.eq("loseMoney", loseMoney);
        }

        IPage<AccidentOfSupervise> page = accidentOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]=AccidentOfSuperviseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files, modelClassArr);

        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<AccidentOfSupervise> dataList = getDataList(modelList, type, httpSession);
            flag = accidentOfSuperviseService.saveBatch(dataList);
        }
        return flag;
    }

    private List<AccidentOfSupervise> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<AccidentOfSupervise> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            AccidentOfSuperviseModel accidentOfSuperviseModel = (AccidentOfSuperviseModel) object;
            //业务处理
            AccidentOfSupervise accidentOfSupervise = new AccidentOfSupervise();
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
                }
            }
            BeanUtils.copyProperties(accidentOfSuperviseModel, accidentOfSupervise);
            dataList.add(accidentOfSupervise);
        }
        return dataList;
    }

}
