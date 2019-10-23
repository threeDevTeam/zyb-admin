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
import com.hthyaq.zybadmin.model.excelModel.TijianBasicOfServiceModel;
import com.hthyaq.zybadmin.model.excelModel.ZhenduanBasicOfServiceModel;
import com.hthyaq.zybadmin.model.vo.JianceBasicOfView;
import com.hthyaq.zybadmin.model.vo.TijianDetail1OfServiceView;
import com.hthyaq.zybadmin.model.vo.TijianDetail2OfServiceView;
import com.hthyaq.zybadmin.model.vo.ZhenduanBasicOfServiceView;
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
 * 诊断机构的基本信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/zhenduanBasicOfService")
public class ZhenduanBasicOfServiceController {
    @Autowired
    ZhenduanBasicOfServiceService zhenduanBasicOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    AreaOfDicService areaOfDicService;
    @Autowired
    TypesofregistrationService typesofregistrationService;
    @Autowired
    SysRoleUserService sysRoleUserService;

    @PostMapping("/add")
    public boolean add(@RequestBody ZhenduanBasicOfServiceView zhenduanBasicOfServiceView ,HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<ServiceOfRegister> queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",sysUser.getCompanyId());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper);
        for (ServiceOfRegister serviceOfRegister : list) {
            if (serviceOfRegister.getType().equals("诊断机构")) {
                ZhenduanBasicOfService zhenduanBasicOfService=new ZhenduanBasicOfService();
                BeanUtils.copyProperties(zhenduanBasicOfServiceView, zhenduanBasicOfService);
                QueryWrapper<Typesofregistration> qw = new QueryWrapper<>();
                qw.eq("id", zhenduanBasicOfServiceView.getCascaded1().get(0));
                List<Typesofregistration> list1 = typesofregistrationService.list(qw);
                for (Typesofregistration typesofregistration : list1) {
                    zhenduanBasicOfService.setRegisterBigName(typesofregistration.getName());
                }
                if(zhenduanBasicOfServiceView.getCascaded1().size()==2){
                    QueryWrapper<Typesofregistration> qw1= new QueryWrapper<>();
                    qw1.eq("id",zhenduanBasicOfServiceView.getCascaded1().get(1));
                    List<Typesofregistration> list2= typesofregistrationService.list(qw1);
                    for (Typesofregistration typesofregistration : list2) {
                        zhenduanBasicOfService.setRegisterSmallName(typesofregistration.getName());
                    }
                }else{
                    zhenduanBasicOfService.setRegisterSmallName("无");
                }
                zhenduanBasicOfService.setName(serviceOfRegister.getName());
                zhenduanBasicOfService.setCode(serviceOfRegister.getCode());
                zhenduanBasicOfService.setProvinceName(serviceOfRegister.getProvinceName());
                zhenduanBasicOfService.setProvinceCode(serviceOfRegister.getProvinceCode());
                zhenduanBasicOfService.setCityName(serviceOfRegister.getCityName());
                zhenduanBasicOfService.setCityCode(serviceOfRegister.getCityCode());
                zhenduanBasicOfService.setDistrictName(serviceOfRegister.getDistrictName());
                zhenduanBasicOfService.setDistrictCode(serviceOfRegister.getDistrictCode());
                zhenduanBasicOfService.setRegisterAddress(serviceOfRegister.getRegisterAddress());
                flag = zhenduanBasicOfServiceService.save(zhenduanBasicOfService);
            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return zhenduanBasicOfServiceService.removeById(id);
    }

    @GetMapping("/getById")
    public ZhenduanBasicOfService getById(Integer id) {

        List list=new ArrayList();
        List list4=new ArrayList();
        ZhenduanBasicOfServiceView zhenduanBasicOfServiceView=new ZhenduanBasicOfServiceView();
        ZhenduanBasicOfService zhenduanBasicOfService = zhenduanBasicOfServiceService.getById(id);
        BeanUtils.copyProperties(zhenduanBasicOfService, zhenduanBasicOfServiceView);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", zhenduanBasicOfService.getProvinceCode());
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list1) {
            list.add(areaOfDic.getId());
        }
        QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("code",zhenduanBasicOfService.getCityCode());
        List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
        for (AreaOfDic areaOfDic : list2) {
            list.add(areaOfDic.getId());
        }


        if (zhenduanBasicOfService.getCityName().equals(zhenduanBasicOfService.getDistrictName())) {
            for (AreaOfDic areaOfDic : list2) {
                list.add(areaOfDic.getId());
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("code",zhenduanBasicOfService.getDistrictCode());
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                list.add(areaOfDic.getId());
            }
        }


        zhenduanBasicOfServiceView.setCascader((ArrayList) list);
        QueryWrapper<Typesofregistration> qw = new QueryWrapper<>();
        qw.eq("name", zhenduanBasicOfService.getRegisterBigName());
        List<Typesofregistration> listT = typesofregistrationService.list(qw);
        for (Typesofregistration typesofregistration : listT) {
            list4.add(typesofregistration.getId());
        }
        QueryWrapper<Typesofregistration> qw2 = new QueryWrapper<>();
        qw2.eq("name", zhenduanBasicOfService.getRegisterSmallName());
        List<Typesofregistration> listTS = typesofregistrationService.list(qw2);
        for (Typesofregistration typesofregistration : listTS) {
            list4.add(typesofregistration.getId());
        }
        zhenduanBasicOfServiceView.setCascaded1((ArrayList) list4);
        return zhenduanBasicOfServiceView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody ZhenduanBasicOfServiceView zhenduanBasicOfServiceView) {


        ZhenduanBasicOfService zhenduanBasicOfService=new ZhenduanBasicOfService();

        BeanUtils.copyProperties(zhenduanBasicOfServiceView, zhenduanBasicOfService);

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",zhenduanBasicOfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            zhenduanBasicOfService.setProvinceName(String.valueOf(areaOfDic.getName()));
            zhenduanBasicOfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }
        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", zhenduanBasicOfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            zhenduanBasicOfService.setCityName(String.valueOf(areaOfDic.getName()));

            zhenduanBasicOfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (zhenduanBasicOfServiceView.getCascader().size() !=3) {
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("id", zhenduanBasicOfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                zhenduanBasicOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                zhenduanBasicOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", zhenduanBasicOfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                zhenduanBasicOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                zhenduanBasicOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }
        QueryWrapper<Typesofregistration> qw = new QueryWrapper<>();
        qw.eq("id", zhenduanBasicOfServiceView.getCascaded1().get(0));
        List<Typesofregistration> list3 = typesofregistrationService.list(qw);
        for (Typesofregistration typesofregistration : list3) {
            zhenduanBasicOfService.setRegisterBigName(typesofregistration.getName());
        }
        if(zhenduanBasicOfServiceView.getCascaded1().size()==2){
            QueryWrapper<Typesofregistration> qw1= new QueryWrapper<>();
            qw1.eq("id",zhenduanBasicOfServiceView.getCascaded1().get(1));
            List<Typesofregistration> list2= typesofregistrationService.list(qw1);
            for (Typesofregistration typesofregistration : list2) {
                zhenduanBasicOfService.setRegisterSmallName(typesofregistration.getName());
            }
        }else{
            zhenduanBasicOfService.setRegisterSmallName("无");
        }
        return zhenduanBasicOfServiceService.updateById(zhenduanBasicOfService);
    }

    @GetMapping("/list")
    public IPage<ZhenduanBasicOfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        QueryWrapper<SysRoleUser> qw = new QueryWrapper<>();
        qw.eq("userId", sysUser.getId());
        SysRoleUser sysRoleUser = sysRoleUserService.getOne(qw);
        if (sysRoleUser.getRoleId() == 1) {

            QueryWrapper<ZhenduanBasicOfService> queryWrapper = new QueryWrapper();
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.eq("name", name);
            }
            IPage<ZhenduanBasicOfService> page = zhenduanBasicOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        } else {
            QueryWrapper<ServiceOfRegister> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", sysUser.getCompanyName());
            List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper1);
            for (ServiceOfRegister serviceOfRegister : list) {
                if (serviceOfRegister.getType().equals("诊断机构")) {
                    list1.clear();
                    list1.add(serviceOfRegister.getName());
                }
            }
            QueryWrapper<ZhenduanBasicOfService> queryWrapper = new QueryWrapper();
            queryWrapper.eq("name", list1.get(0));
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.eq("name", name);
            }


            IPage<ZhenduanBasicOfService> page = zhenduanBasicOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= ZhenduanBasicOfServiceModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<ZhenduanBasicOfService> dataList = getDataList(modelList, type);
            flag = zhenduanBasicOfServiceService.saveBatch(dataList);
        }
        return flag;
    }
    private List<ZhenduanBasicOfService> getDataList(List<Object> modelList, String type) {
        List<ZhenduanBasicOfService> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            ZhenduanBasicOfServiceModel zhenduanBasicOfServiceModel = (ZhenduanBasicOfServiceModel) object;
            //业务处理
            ZhenduanBasicOfService zhenduanBasicOfService = new ZhenduanBasicOfService();
            BeanUtils.copyProperties(zhenduanBasicOfServiceModel, zhenduanBasicOfService);
            if(zhenduanBasicOfServiceModel.getLevel().equals("甲级") ||zhenduanBasicOfServiceModel.getLevel().equals("乙级") ||zhenduanBasicOfServiceModel.getLevel().equals("丙级")){
                zhenduanBasicOfService.setLevel(zhenduanBasicOfServiceModel.getLevel());
            }else{
                return null;
            }
            if(zhenduanBasicOfServiceModel.getScope().equals("粉尘") ||zhenduanBasicOfServiceModel.getScope().equals("化学因素") ||zhenduanBasicOfServiceModel.getScope().equals("物理因素")
                    ||zhenduanBasicOfServiceModel.getScope().equals("放射性因素")||zhenduanBasicOfServiceModel.getScope().equals("生物因素")){
                zhenduanBasicOfService.setScope(zhenduanBasicOfServiceModel.getScope());
            }else{
                return null;
            }
            if(zhenduanBasicOfServiceModel.getHospitalLevel().equals("一级") ||zhenduanBasicOfServiceModel.getHospitalLevel().equals("二级") ||zhenduanBasicOfServiceModel.getHospitalLevel().equals("三级")){
                zhenduanBasicOfService.setHospitalLevel(zhenduanBasicOfServiceModel.getHospitalLevel());
            }else{
                return null;
            }
            dataList.add(zhenduanBasicOfService);
        }
        return dataList;
    }
}

