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
import com.hthyaq.zybadmin.model.excelModel.JianceBasicOfServiceModel;
import com.hthyaq.zybadmin.model.excelModel.TijianBasicOfServiceModel;
import com.hthyaq.zybadmin.model.vo.JianceBasicOfView;
import com.hthyaq.zybadmin.model.vo.TijianBasicOfServiceView;
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


@RestController
@RequestMapping("/tijianBasicOfService")
public class TijianBasicOfServiceController {
    @Autowired
    TijianBasicOfServiceService tijianBasicOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    AreaOfDicService areaOfDicService;
    @Autowired
    TypesofregistrationService typesofregistrationService;
    @Autowired
    SysRoleUserService sysRoleUserService;

    @PostMapping("/add")
    public boolean add(@RequestBody TijianBasicOfServiceView tijianBasicOfServiceView, HttpSession httpSession) {
        boolean flag=false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<ServiceOfRegister> queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",sysUser.getCompanyId());

        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper);
        for (ServiceOfRegister serviceOfRegister : list) {
            if (serviceOfRegister.getType().equals("体检机构")) {
                TijianBasicOfService tijianBasicOfService=new TijianBasicOfServiceView();
                BeanUtils.copyProperties(tijianBasicOfServiceView, tijianBasicOfService);

                QueryWrapper<Typesofregistration> qw = new QueryWrapper<>();
                qw.eq("id", tijianBasicOfServiceView.getCascaded1().get(0));
                List<Typesofregistration> list1 = typesofregistrationService.list(qw);
                for (Typesofregistration typesofregistration : list1) {
                    tijianBasicOfService.setRegisterBigName(typesofregistration.getName());
                }
                if(tijianBasicOfServiceView.getCascaded1().size()==2){
                    QueryWrapper<Typesofregistration> qw1= new QueryWrapper<>();
                    qw1.eq("id",tijianBasicOfServiceView.getCascaded1().get(1));
                    List<Typesofregistration> list2= typesofregistrationService.list(qw1);
                    for (Typesofregistration typesofregistration : list2) {
                        tijianBasicOfService.setRegisterSmallName(typesofregistration.getName());
                    }
                }else{
                    tijianBasicOfService.setRegisterSmallName("无");
                }

                tijianBasicOfService.setName(serviceOfRegister.getName());
                tijianBasicOfService.setCode(serviceOfRegister.getCode());
                tijianBasicOfService.setProvinceName(serviceOfRegister.getProvinceName());
                tijianBasicOfService.setProvinceCode(serviceOfRegister.getProvinceCode());
                tijianBasicOfService.setCityName(serviceOfRegister.getCityName());
                tijianBasicOfService.setCityCode(serviceOfRegister.getCityCode());
                tijianBasicOfService.setDistrictName(serviceOfRegister.getDistrictName());
                tijianBasicOfService.setDistrictCode(serviceOfRegister.getDistrictCode());
                tijianBasicOfService.setRegisterAddress(serviceOfRegister.getRegisterAddress());
                flag = tijianBasicOfServiceService.save(tijianBasicOfService);
            }
        }
        return flag;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return tijianBasicOfServiceService.removeById(id);
    }
    @GetMapping("/getById")
    public TijianBasicOfService getById(Integer id) {
        List list4=new ArrayList();
        List list=new ArrayList();
        TijianBasicOfServiceView tijianBasicOfServiceView=new TijianBasicOfServiceView();
        TijianBasicOfService tijianBasicOfService = tijianBasicOfServiceService.getById(id);
        BeanUtils.copyProperties(tijianBasicOfService, tijianBasicOfServiceView);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", tijianBasicOfService.getProvinceCode());
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list1) {
            list.add(areaOfDic.getId());
        }
        QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("code",tijianBasicOfService.getCityCode());
        List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
        for (AreaOfDic areaOfDic : list2) {
            list.add(areaOfDic.getId());
        }


        if (tijianBasicOfService.getCityName().equals(tijianBasicOfService.getDistrictName())) {
            for (AreaOfDic areaOfDic : list2) {
                list.add(areaOfDic.getId());
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("code",tijianBasicOfService.getDistrictCode());
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                list.add(areaOfDic.getId());
            }
        }

        tijianBasicOfServiceView.setCascader((ArrayList) list);

        QueryWrapper<Typesofregistration> qw = new QueryWrapper<>();
        qw.eq("name", tijianBasicOfService.getRegisterBigName());
        List<Typesofregistration> listT = typesofregistrationService.list(qw);
        for (Typesofregistration typesofregistration : listT) {
            list4.add(typesofregistration.getId());
        }
        QueryWrapper<Typesofregistration> qw2 = new QueryWrapper<>();
        qw2.eq("name", tijianBasicOfService.getRegisterSmallName());
        List<Typesofregistration> listTS = typesofregistrationService.list(qw2);
        for (Typesofregistration typesofregistration : listTS) {
            list4.add(typesofregistration.getId());
        }
        tijianBasicOfServiceView.setCascaded1((ArrayList) list4);
        return tijianBasicOfServiceView;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody TijianBasicOfServiceView tijianBasicOfServiceView) {
        TijianBasicOfService tijianBasicOfService = new TijianBasicOfService();

        BeanUtils.copyProperties(tijianBasicOfServiceView, tijianBasicOfService);

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",tijianBasicOfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            tijianBasicOfService.setProvinceName(String.valueOf(areaOfDic.getName()));
            tijianBasicOfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }
        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", tijianBasicOfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            tijianBasicOfService.setCityName(String.valueOf(areaOfDic.getName()));

            tijianBasicOfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (tijianBasicOfServiceView.getCascader().size() !=3) {
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("id", tijianBasicOfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                tijianBasicOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                tijianBasicOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", tijianBasicOfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                tijianBasicOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                tijianBasicOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }
        QueryWrapper<Typesofregistration> qw = new QueryWrapper<>();
        qw.eq("id", tijianBasicOfServiceView.getCascaded1().get(0));
        List<Typesofregistration> list3 = typesofregistrationService.list(qw);
        for (Typesofregistration typesofregistration : list3) {
            tijianBasicOfService.setRegisterBigName(typesofregistration.getName());
        }
        if(tijianBasicOfServiceView.getCascaded1().size()==2){
            QueryWrapper<Typesofregistration> qw1= new QueryWrapper<>();
            qw1.eq("id",tijianBasicOfServiceView.getCascaded1().get(1));
            List<Typesofregistration> list2= typesofregistrationService.list(qw1);
            for (Typesofregistration typesofregistration : list2) {
                tijianBasicOfService.setRegisterSmallName(typesofregistration.getName());
            }
        }else{
            tijianBasicOfService.setRegisterSmallName("无");
        }
        return tijianBasicOfServiceService.updateById(tijianBasicOfService);
    }

    @GetMapping("/list")
    public IPage<TijianBasicOfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        String hospitalLevel = jsonObject.getString("hospitalLevel");
        QueryWrapper<SysRoleUser> qw = new QueryWrapper<>();
        qw.eq("userId", sysUser.getId());
        SysRoleUser sysRoleUser = sysRoleUserService.getOne(qw);
        if (sysRoleUser.getRoleId() == 1) {
            QueryWrapper<TijianBasicOfService> queryWrapper = new QueryWrapper();
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.like("name", name);
            }
            if (!Strings.isNullOrEmpty(hospitalLevel)) {
                queryWrapper.like("hospitalLevel", hospitalLevel);
            }
            queryWrapper.orderByDesc("id");
            IPage<TijianBasicOfService> page = tijianBasicOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        } else {
            QueryWrapper<ServiceOfRegister> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", sysUser.getCompanyName());
            List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper1);
            for (ServiceOfRegister serviceOfRegister : list) {
                if (serviceOfRegister.getType().equals("体检机构")) {
                    list1.clear();
                    list1.add(serviceOfRegister.getName());
                }
            }
            QueryWrapper<TijianBasicOfService> queryWrapper = new QueryWrapper();
            queryWrapper.eq("name", list1.get(0));
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.like("name", name);
            }
            if (!Strings.isNullOrEmpty(hospitalLevel)) {
                queryWrapper.like("hospitalLevel", hospitalLevel);
            }
            queryWrapper.orderByDesc("id");
            IPage<TijianBasicOfService> page = tijianBasicOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= TijianBasicOfServiceModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<TijianBasicOfService> dataList = getDataList(modelList, type);
            flag = tijianBasicOfServiceService.saveBatch(dataList);
        }
        return flag;
    }
    private List<TijianBasicOfService> getDataList(List<Object> modelList, String type) {
        List<TijianBasicOfService> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            TijianBasicOfServiceModel tijianBasicOfServiceModel = (TijianBasicOfServiceModel) object;
            //业务处理
            TijianBasicOfService tijianBasicOfService = new TijianBasicOfService();
            BeanUtils.copyProperties(tijianBasicOfServiceModel, tijianBasicOfService);
            if(tijianBasicOfServiceModel.getScope().equals("粉尘") ||tijianBasicOfServiceModel.getScope().equals("化学因素") ||tijianBasicOfServiceModel.getScope().equals("物理因素")
                    ||tijianBasicOfServiceModel.getScope().equals("放射性因素")||tijianBasicOfServiceModel.getScope().equals("生物因素")){
                tijianBasicOfService.setScope(tijianBasicOfServiceModel.getScope());
            }else{
                return null;
            }
            if(tijianBasicOfServiceModel.getHospitalLevel().equals("一级") ||tijianBasicOfServiceModel.getHospitalLevel().equals("二级") ||tijianBasicOfServiceModel.getHospitalLevel().equals("三级")){
                tijianBasicOfService.setHospitalLevel(tijianBasicOfServiceModel.getHospitalLevel());
            }else{
                return null;
            }

            dataList.add(tijianBasicOfService);
        }
        return dataList;
    }
}
