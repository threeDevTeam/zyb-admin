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
import com.hthyaq.zybadmin.model.excelModel.SuperviseModel;
import com.hthyaq.zybadmin.model.excelModel.ThreeCheckOfSuperviseModel;
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
 * “三同时”监督检查信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/threeCheckOfSupervise")
public class ThreeCheckOfSuperviseController {
    @Autowired
    SuperviseOfRegisterService superviseOfRegisterService;
    @Autowired
    SuperviseService superviseService;
    @Autowired
    ThreeCheckOfSuperviseService threeCheckOfSuperviseService;
    @Autowired
    SysRoleUserService sysRoleUserService;

    @PostMapping("/add")
    public boolean add(@RequestBody ThreeCheckOfSupervise threeCheckOfSupervise, HttpSession httpSession) {
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
                threeCheckOfSupervise.setSuperviseId(supervise.getId());
                flag = threeCheckOfSuperviseService.save(threeCheckOfSupervise);
            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return threeCheckOfSuperviseService.removeById(id);
    }

    @GetMapping("/getById")
    public ThreeCheckOfSupervise getById(Integer id) {

        ThreeCheckOfSupervise threeCheckOfSupervise = threeCheckOfSuperviseService.getById(id);
        //demoCourse
        QueryWrapper<ThreeCheckOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<ThreeCheckOfSupervise> demoCourseList = threeCheckOfSuperviseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return threeCheckOfSupervise;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody ThreeCheckOfSupervise threeCheckOfSupervise) {
        return threeCheckOfSuperviseService.updateById(threeCheckOfSupervise);
    }

    @GetMapping("/list")
    public IPage<ThreeCheckOfSupervise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        String pulishMoney = jsonObject.getString("pulishMoney");
        QueryWrapper<SysRoleUser> qw = new QueryWrapper<>();
        qw.eq("userId", sysUser.getId());
        SysRoleUser sysRoleUser = sysRoleUserService.getOne(qw);
        if (sysRoleUser.getRoleId() == 1) {
            QueryWrapper<Supervise> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("name", sysUser.getCompanyName());
            QueryWrapper<ThreeCheckOfSupervise> queryWrapper = new QueryWrapper<>();
            if (!Strings.isNullOrEmpty(year)) {
                queryWrapper.eq("year", year);
            }
            if (!Strings.isNullOrEmpty(pulishMoney)) {
                queryWrapper.eq("pulishMoney", pulishMoney);
            }

            queryWrapper.orderByDesc("id");
            IPage<ThreeCheckOfSupervise> page = threeCheckOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        } else {
            QueryWrapper<Supervise> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("name", sysUser.getCompanyName());
            List<Supervise> list = superviseService.list(queryWrapper1);
            for (Supervise supervise : list) {
                list1.clear();
                Long id = supervise.getId();
                list1.add(id);
            }
            QueryWrapper<ThreeCheckOfSupervise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("superviseId", list1.get(0));
            if (!Strings.isNullOrEmpty(year)) {
                queryWrapper.eq("year", year);
            }
            if (!Strings.isNullOrEmpty(pulishMoney)) {
                queryWrapper.eq("pulishMoney", pulishMoney);
            }

            queryWrapper.orderByDesc("id");
            IPage<ThreeCheckOfSupervise> page = threeCheckOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= ThreeCheckOfSuperviseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files, modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<ThreeCheckOfSupervise> dataList = getDataList(modelList, type, httpSession);
            flag = threeCheckOfSuperviseService.saveBatch(dataList);
        }
        return flag;
    }

    private List<ThreeCheckOfSupervise> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<ThreeCheckOfSupervise> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            ThreeCheckOfSuperviseModel threeCheckOfSuperviseModel = (ThreeCheckOfSuperviseModel) object;
            //业务处理
            ThreeCheckOfSupervise threeCheckOfSupervise = new ThreeCheckOfSupervise();
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<SuperviseOfRegister> queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", sysUser.getCompanyId());
            List<SuperviseOfRegister> list = superviseOfRegisterService.list(queryWrapper);
            for (SuperviseOfRegister superviseOfRegister : list) {
                QueryWrapper<Supervise> queryWrapper1 = new QueryWrapper();
                queryWrapper1.eq("name", superviseOfRegister.getName());
                List<Supervise> list1 = superviseService.list(queryWrapper1);
                for (Supervise supervise : list1) {
                    threeCheckOfSupervise.setSuperviseId(supervise.getId());
                }
            }
            BeanUtils.copyProperties(threeCheckOfSuperviseModel, threeCheckOfSupervise);
            dataList.add(threeCheckOfSupervise);
        }
        return dataList;
    }
}
