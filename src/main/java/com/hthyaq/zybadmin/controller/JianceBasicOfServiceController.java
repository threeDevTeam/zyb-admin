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
import com.hthyaq.zybadmin.model.excelModel.ExecuteLawOfSuperviseModel;
import com.hthyaq.zybadmin.model.excelModel.JianceBasicOfServiceModel;
import com.hthyaq.zybadmin.model.excelModel.SuperviseModel;
import com.hthyaq.zybadmin.model.vo.JianceBasicOfView;
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
 * 检测机构的基本信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/jianceBasicOfService")
public class JianceBasicOfServiceController {
    @Autowired
    JianceBasicOfServiceService jianceBasicOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    AreaOfDicService areaOfDicService;
    @Autowired
    TypesofregistrationService typesofregistrationService;
    @Autowired
    SysRoleUserService sysRoleUserService;

    @PostMapping("/add")
    public boolean add(@RequestBody JianceBasicOfView jianceBasicOfView, HttpSession httpSession) {
        boolean flag=false;

        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<ServiceOfRegister> queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",sysUser.getCompanyName());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper);
        for (ServiceOfRegister serviceOfRegister : list) {
            if(serviceOfRegister.getType().equals("检测机构")){
                JianceBasicOfService jianceBasicOfService=new JianceBasicOfView();
                BeanUtils.copyProperties(jianceBasicOfView, jianceBasicOfService);
                QueryWrapper<Typesofregistration> qw = new QueryWrapper<>();
                qw.eq("id", jianceBasicOfView.getCascaded1().get(0));
                List<Typesofregistration> list1 = typesofregistrationService.list(qw);
                for (Typesofregistration typesofregistration : list1) {
                    jianceBasicOfService.setRegisterBigName(typesofregistration.getName());
                }
                if(jianceBasicOfView.getCascaded1().size()==2){
                    QueryWrapper<Typesofregistration> qw1= new QueryWrapper<>();
                    qw1.eq("id",jianceBasicOfView.getCascaded1().get(1));
                    List<Typesofregistration> list2= typesofregistrationService.list(qw1);
                    for (Typesofregistration typesofregistration : list2) {
                        jianceBasicOfService.setRegisterSmallName(typesofregistration.getName());
                    }
                }else{
                    jianceBasicOfService.setRegisterSmallName("无");
                }
                jianceBasicOfService.setName(serviceOfRegister.getName());
                jianceBasicOfService.setCode(serviceOfRegister.getCode());
                jianceBasicOfService.setProvinceName(serviceOfRegister.getProvinceName());
                jianceBasicOfService.setProvinceCode(serviceOfRegister.getProvinceCode());
                jianceBasicOfService.setCityName(serviceOfRegister.getCityName());
                jianceBasicOfService.setCityCode(serviceOfRegister.getCityCode());
                jianceBasicOfService.setDistrictName(serviceOfRegister.getDistrictName());
                jianceBasicOfService.setDistrictCode(serviceOfRegister.getDistrictCode());
                jianceBasicOfService.setRegisterAddress(serviceOfRegister.getRegisterAddress());
                flag=jianceBasicOfServiceService.save(jianceBasicOfService);
            }
        }
        return flag;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return jianceBasicOfServiceService.removeById(id);
    }
    @GetMapping("/getById")
    public JianceBasicOfService getById(Integer id) {
        List list=new ArrayList();
        List list4=new ArrayList();
        JianceBasicOfView jianceBasicOfView=new JianceBasicOfView();
        JianceBasicOfService jianceBasicOfService = jianceBasicOfServiceService.getById(id);
        BeanUtils.copyProperties(jianceBasicOfService, jianceBasicOfView);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", jianceBasicOfService.getProvinceCode());
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list1) {
            list.add(areaOfDic.getId());
        }
        QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("code", jianceBasicOfService.getCityCode());
        List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
        for (AreaOfDic areaOfDic : list2) {
            list.add(areaOfDic.getId());
        }


        if (jianceBasicOfService.getCityName().equals(jianceBasicOfService.getDistrictName())) {
            for (AreaOfDic areaOfDic : list2) {
                list.add(areaOfDic.getId());
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("code", jianceBasicOfService.getDistrictCode());
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                list.add(areaOfDic.getId());
            }
        }
        jianceBasicOfView.setCascader((ArrayList) list);


        QueryWrapper<Typesofregistration> qw = new QueryWrapper<>();
        qw.eq("name", jianceBasicOfService.getRegisterBigName());
        List<Typesofregistration> listT = typesofregistrationService.list(qw);
        for (Typesofregistration typesofregistration : listT) {
            list4.add(typesofregistration.getId());
        }
        QueryWrapper<Typesofregistration> qw2 = new QueryWrapper<>();
        qw2.eq("name", jianceBasicOfService.getRegisterSmallName());
        List<Typesofregistration> listTS = typesofregistrationService.list(qw2);
        for (Typesofregistration typesofregistration : listTS) {
            list4.add(typesofregistration.getId());
        }
        jianceBasicOfView.setCascaded1((ArrayList) list4);
        return jianceBasicOfView;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody JianceBasicOfView jianceBasicOfView) {
        JianceBasicOfService jianceBasicOfService = new JianceBasicOfView();

        BeanUtils.copyProperties(jianceBasicOfView, jianceBasicOfService);

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",jianceBasicOfView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            jianceBasicOfService.setProvinceName(String.valueOf(areaOfDic.getName()));
            jianceBasicOfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }
        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", jianceBasicOfView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            jianceBasicOfService.setCityName(String.valueOf(areaOfDic.getName()));

            jianceBasicOfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (jianceBasicOfView.getCascader().size() !=3) {
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("id", jianceBasicOfView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                jianceBasicOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                jianceBasicOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", jianceBasicOfView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                jianceBasicOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                jianceBasicOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }

        QueryWrapper<Typesofregistration> qw = new QueryWrapper<>();
        qw.eq("id", jianceBasicOfView.getCascaded1().get(0));
        List<Typesofregistration> list3 = typesofregistrationService.list(qw);
        for (Typesofregistration typesofregistration : list3) {
            jianceBasicOfService.setRegisterBigName(typesofregistration.getName());
        }
        if(jianceBasicOfView.getCascaded1().size()==2){
            QueryWrapper<Typesofregistration> qw1= new QueryWrapper<>();
            qw1.eq("id",jianceBasicOfView.getCascaded1().get(1));
            List<Typesofregistration> list2= typesofregistrationService.list(qw1);
            for (Typesofregistration typesofregistration : list2) {
                jianceBasicOfService.setRegisterSmallName(typesofregistration.getName());
            }
        }else{
            jianceBasicOfService.setRegisterSmallName("无");
        }
        return jianceBasicOfServiceService.updateById(jianceBasicOfService);
    }

    @GetMapping("/list")
    public IPage<JianceBasicOfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        String year = jsonObject.getString("year");
        QueryWrapper<SysRoleUser> qw = new QueryWrapper<>();
        qw.eq("userId", sysUser.getId());
        SysRoleUser sysRoleUser = sysRoleUserService.getOne(qw);
        if (sysRoleUser.getRoleId() == 1) {
            QueryWrapper<JianceBasicOfService> queryWrapper = new QueryWrapper();
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.eq("name", name);
            }
            if (!Strings.isNullOrEmpty(year)) {
                queryWrapper.eq("year", year);
            }
            queryWrapper.orderByDesc("id");
            IPage<JianceBasicOfService> page = jianceBasicOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        } else {
            QueryWrapper<ServiceOfRegister> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", sysUser.getCompanyName());
            List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper1);
            for (ServiceOfRegister serviceOfRegister : list) {
                if (serviceOfRegister.getType().equals("检测机构")) {
                    list1.clear();
                    list1.add(serviceOfRegister.getName());
                }
            }
            QueryWrapper<JianceBasicOfService> queryWrapper = new QueryWrapper();
            queryWrapper.eq("name", list1.get(0));
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.eq("name", name);
            }
            if (!Strings.isNullOrEmpty(year)) {
                queryWrapper.eq("year", year);
            }
            queryWrapper.orderByDesc("id");
            IPage<JianceBasicOfService> page = jianceBasicOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @GetMapping("/cascadeData")
    public List<CascadeView> cascadeData() {
        List<Typesofregistration> list = typesofregistrationService.list();
        System.out.println(list);
        return CascadeUtil.get(list);

    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= JianceBasicOfServiceModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<JianceBasicOfService> dataList = getDataList(modelList, type);
            flag = jianceBasicOfServiceService.saveBatch(dataList);
        }
        return flag;
    }
    private List<JianceBasicOfService> getDataList(List<Object> modelList, String type) {
        List<JianceBasicOfService> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            JianceBasicOfServiceModel jianceBasicOfServiceModel = (JianceBasicOfServiceModel) object;
            //业务处理
            JianceBasicOfService jianceBasicOfService = new JianceBasicOfService();
            BeanUtils.copyProperties(jianceBasicOfServiceModel, jianceBasicOfService);
            if(jianceBasicOfServiceModel.getLevel().equals("甲级") ||jianceBasicOfServiceModel.getLevel().equals("乙级") ||jianceBasicOfServiceModel.getLevel().equals("丙级")){
                jianceBasicOfService.setLevel(jianceBasicOfServiceModel.getLevel());
            }else{
                return null;
            }
            dataList.add(jianceBasicOfService);
        }
        return dataList;
    }
}
