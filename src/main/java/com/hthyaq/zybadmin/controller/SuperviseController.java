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
import com.hthyaq.zybadmin.common.utils.cascade.CascadeUtil;
import com.hthyaq.zybadmin.common.utils.cascade.CascadeView;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.excelModel.AccidentOfSuperviseModel;
import com.hthyaq.zybadmin.model.excelModel.SuperviseModel;
import com.hthyaq.zybadmin.model.excelModel.TableMapInfoModel;
import com.hthyaq.zybadmin.model.vo.SuperviseView;
import com.hthyaq.zybadmin.service.AreaOfDicService;
import com.hthyaq.zybadmin.service.SuperviseOfRegisterService;
import com.hthyaq.zybadmin.service.SuperviseService;
import com.hthyaq.zybadmin.service.SysRoleUserService;
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
 * 监管部门信息
 * <p>
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/supervise")

public class SuperviseController {
    @Autowired
    SuperviseService superviseService;
    @Autowired
    AreaOfDicService areaOfDicService;
    @Autowired
    SuperviseOfRegisterService superviseOfRegisterService;

    @Autowired
    SysRoleUserService sysRoleUserService;

    @PostMapping("/add")
    public boolean add(@RequestBody Supervise supervise, HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<SuperviseOfRegister> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", sysUser.getCompanyId());
        List<SuperviseOfRegister> list = superviseOfRegisterService.list(queryWrapper);
        for (SuperviseOfRegister superviseOfRegister : list) {
            supervise.setProvinceName(superviseOfRegister.getProvinceName());
            supervise.setProvinceCode(superviseOfRegister.getProvinceCode());
            supervise.setCityName(superviseOfRegister.getCityName());
            supervise.setCityCode(superviseOfRegister.getCityCode());
            supervise.setDistrictName(superviseOfRegister.getDistrictName());
            supervise.setDistrictCode(superviseOfRegister.getDistrictCode());
            supervise.setRegisterAddress(superviseOfRegister.getRegisterAddress());
            supervise.setName(superviseOfRegister.getName());
            flag = superviseService.saveData(supervise);
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return superviseService.deleteData(id);
    }

    @GetMapping("/getById")
    public Supervise getById(Integer id) {
        List list = new ArrayList();
        SuperviseView superviseView = new SuperviseView();
        Supervise supervise = superviseService.getById(id);
        BeanUtils.copyProperties(supervise, superviseView);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", supervise.getProvinceCode());
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list1) {
            list.add(areaOfDic.getId());
        }
        QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("code", supervise.getCityCode());
        List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
        for (AreaOfDic areaOfDic : list2) {
            list.add(areaOfDic.getId());
        }


        if (supervise.getCityName().equals(supervise.getDistrictName())) {
            for (AreaOfDic areaOfDic : list2) {
                list.add(areaOfDic.getId());
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("code", supervise.getDistrictCode());
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                list.add(areaOfDic.getId());
            }
        }


        superviseView.setCascader((ArrayList) list);
        return superviseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody SuperviseView superviseView) {
        Supervise supervise = new Supervise();

        BeanUtils.copyProperties(superviseView, supervise);

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", superviseView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            supervise.setProvinceName(String.valueOf(areaOfDic.getName()));
            supervise.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }
        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", superviseView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            supervise.setCityName(String.valueOf(areaOfDic.getName()));

            supervise.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (superviseView.getCascader().size() != 3) {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("id", superviseView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                supervise.setDistrictName(String.valueOf(areaOfDic.getName()));
                supervise.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", superviseView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                supervise.setDistrictName(String.valueOf(areaOfDic.getName()));
                supervise.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }

        return superviseService.updateById(supervise);
    }

    @GetMapping("/list")
    public IPage<Supervise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);

        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        String name = jsonObject.getString("name");
        QueryWrapper<SysRoleUser> qw = new QueryWrapper<>();
        qw.eq("userId", sysUser.getId());
        SysRoleUser sysRoleUser = sysRoleUserService.getOne(qw);
        if (sysRoleUser.getRoleId() == 1) {
            QueryWrapper<Supervise> queryWrapper = new QueryWrapper<>();
            if (!Strings.isNullOrEmpty(year)) {
                queryWrapper.eq("year", year);
            }
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.eq("name", name);
            }

            IPage<Supervise> page = superviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        } else {
            QueryWrapper<Supervise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", sysUser.getCompanyName());
            if (!Strings.isNullOrEmpty(year)) {
                queryWrapper.eq("year", year);
            }
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.eq("name", name);
            }

            IPage<Supervise> page = superviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }

    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0] = SuperviseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files, modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<Supervise> dataList = getDataList(modelList, type);
            flag = superviseService.saveBatch(dataList);
        }
        return flag;
    }

    private List<Supervise> getDataList(List<Object> modelList, String type) {
        List<Supervise> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            SuperviseModel superviseModel = (SuperviseModel) object;
            //业务处理
            Supervise supervise = new Supervise();
            BeanUtils.copyProperties(superviseModel, supervise);
            dataList.add(supervise);
        }
        return dataList;
    }

}
