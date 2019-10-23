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
import com.hthyaq.zybadmin.model.excelModel.EnterpriseModel;
import com.hthyaq.zybadmin.model.excelModel.SuperviseModel;
import com.hthyaq.zybadmin.model.vo.EnterpriseView;
import com.hthyaq.zybadmin.model.vo.JianceBasicOfView;
import com.hthyaq.zybadmin.model.vo.SuperviseView;
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
 * 生产工艺信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    AreaOfDicService areaOfDicService;
    @Autowired
    TypesofregistrationService typesofregistrationService;
    @Autowired
    IndustryOfDicService industryOfDicService;
    @PostMapping("/add")
    public boolean add(@RequestBody  EnterpriseView enterpriseView, HttpSession httpSession) {
        boolean flag=false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<EnterpriseOfRegister> queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",sysUser.getCompanyId());
        List<EnterpriseOfRegister> list = enterpriseOfRegisterService.list(queryWrapper);
        for (EnterpriseOfRegister enterpriseOfRegister : list) {

            Enterprise enterprise=new Enterprise();
            BeanUtils.copyProperties(enterpriseView, enterprise);
            QueryWrapper<Typesofregistration> qw = new QueryWrapper<>();
            qw.eq("id", enterpriseView.getCascaded1().get(0));
            List<Typesofregistration> list1 = typesofregistrationService.list(qw);
            for (Typesofregistration typesofregistration : list1) {
                enterprise.setRegisterBigName(typesofregistration.getName());
            }
            if(enterpriseView.getCascaded1().size()==2){
                QueryWrapper<Typesofregistration> qw1= new QueryWrapper<>();
                qw1.eq("id",enterpriseView.getCascaded1().get(1));
                List<Typesofregistration> list2= typesofregistrationService.list(qw1);
                for (Typesofregistration typesofregistration : list2) {
                    enterprise.setRegisterSmallName(typesofregistration.getName());
                }
            }else{
                enterprise.setRegisterSmallName("无");
            }

            //所属行业名称
            QueryWrapper<IndustryOfDic> qw2 = new QueryWrapper<>();
            qw2.eq("id", enterpriseView.getCascaded2().get(0));
            List<IndustryOfDic> list2 = industryOfDicService.list(qw2);
            for (IndustryOfDic industryOfDic : list2) {
                enterprise.setIndustryBigName(industryOfDic.getName());
            }
            if(enterpriseView.getCascaded2().size()==2){
                QueryWrapper<IndustryOfDic> qw1= new QueryWrapper<>();
                qw1.eq("id",enterpriseView.getCascaded2().get(1));
                List<IndustryOfDic> list3= industryOfDicService.list(qw1);
                for (IndustryOfDic industryOfDic : list3) {
                    enterprise.setIndustrySmallName(industryOfDic.getName());
                }
            }if(enterpriseView.getCascaded2().size()==3){
                QueryWrapper<IndustryOfDic> qw1= new QueryWrapper<>();
                qw1.eq("id",enterpriseView.getCascaded2().get(2));
                List<IndustryOfDic> listT1= industryOfDicService.list(qw1);
                for (IndustryOfDic industryOfDic : listT1) {
                    enterprise.setIndustrySmallName(industryOfDic.getName());
                }
            }else{
                enterprise.setIndustrySmallName("无");
            }

            enterprise.setName(enterpriseOfRegister.getName());
            enterprise.setCode(enterpriseOfRegister.getCode());
            enterprise.setProvinceName(enterpriseOfRegister.getProvinceName());
            enterprise.setProvinceCode(enterpriseOfRegister.getProvinceCode());
            enterprise.setCityName(enterpriseOfRegister.getCityName());
            enterprise.setCityCode(enterpriseOfRegister.getCityCode());
            enterprise.setDistrictName(enterpriseOfRegister.getDistrictName());
            enterprise.setDistrictCode(enterpriseOfRegister.getDistrictCode());
            enterprise.setProductionCapacity(Integer.parseInt(enterpriseOfRegister.getProductionCapacity()));
            enterprise.setUnitType(enterpriseOfRegister.getUnitType());
            enterprise.setRegiterMoney(Double.parseDouble(enterpriseOfRegister.getRegiterMoney()));
            enterprise.setRegisterAddress(enterpriseOfRegister.getRegisterAddress());
            enterprise.setRegisterDate(Integer.parseInt(enterpriseOfRegister.getRegisterDate()));
            enterprise.setStartDate(Integer.parseInt(enterpriseOfRegister.getStartDate()));
            enterprise.setPropertyMoney(Double.parseDouble(enterpriseOfRegister.getPropertyMoney()));
            if(enterpriseView.getWomenWorkerNumber()>= 1000&&enterpriseView.getSaleMoney()>=40000){
                enterprise.setSize("大型");
            }else if(enterpriseView.getWomenWorkerNumber()>=300&&enterpriseView.getWomenWorkerNumber()<1000&&enterpriseView.getSaleMoney()>=2000&&enterpriseView.getSaleMoney()<40000){
                enterprise.setSize("中型");
            }else if(enterpriseView.getWomenWorkerNumber()>=20&&enterpriseView.getWomenWorkerNumber()<300&&enterpriseView.getSaleMoney()>= 300&&enterpriseView.getSaleMoney()<2000) {
                enterprise.setSize("小型");
            }else if(enterpriseView.getWomenWorkerNumber()<20&&enterpriseView.getSaleMoney()<300) {
                enterprise.setSize("微型");
            }else {
                enterprise.setSize("无");
            }

                flag=enterpriseService.save(enterprise);
        }
        return flag;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return enterpriseService.removeById(id);
    }
    @GetMapping("/getById")
    public Enterprise getById(Integer id) {
        List list = new ArrayList();
        List listc1 = new ArrayList();
        List listc2 = new ArrayList();
        EnterpriseView enterpriseView = new EnterpriseView();
        Enterprise enterprise = enterpriseService.getById(id);
        BeanUtils.copyProperties(enterprise, enterpriseView);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", enterprise.getProvinceCode());
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list1) {
            list.add(areaOfDic.getId());
        }
        QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("code",enterprise.getCityCode());
        List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
        for (AreaOfDic areaOfDic : list2) {
            list.add(areaOfDic.getId());
        }


        if (enterprise.getCityName().equals(enterprise.getDistrictName())) {
            for (AreaOfDic areaOfDic : list2) {
                list.add(areaOfDic.getId());
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("code",enterprise.getDistrictCode());
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                list.add(areaOfDic.getId());
            }
        }
        enterpriseView.setCascader((ArrayList) list);



        QueryWrapper<Typesofregistration> qw = new QueryWrapper<>();
        qw.eq("name", enterprise.getRegisterBigName());
        List<Typesofregistration> listT = typesofregistrationService.list(qw);
        for (Typesofregistration typesofregistration : listT) {
            listc1.add(typesofregistration.getId());
        }
        QueryWrapper<Typesofregistration> qw2 = new QueryWrapper<>();
        qw2.eq("name", enterprise.getRegisterSmallName());
        List<Typesofregistration> listTS = typesofregistrationService.list(qw2);
        for (Typesofregistration typesofregistration : listTS) {
            listc1.add(typesofregistration.getId());
        }
        enterpriseView.setCascaded1((ArrayList) listc1);

        //所属行业名称
        QueryWrapper<IndustryOfDic> qwI = new QueryWrapper<>();
        qwI.eq("name", enterprise.getIndustryBigName());
        List<IndustryOfDic> listI = industryOfDicService.list(qwI);
        for (IndustryOfDic industryOfDic : listI) {
            listc2.add(industryOfDic.getId());
        }
        QueryWrapper<IndustryOfDic> qw3 = new QueryWrapper<>();
        qw3.eq("name", enterprise.getIndustrySmallName());
        List<IndustryOfDic> listc = industryOfDicService.list(qw3);
        for (IndustryOfDic industryOfDic : listc) {
            listc2.add(industryOfDic.getId());
        }
        enterpriseView.setCascaded2((ArrayList) listc2);
        return enterpriseView;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody EnterpriseView enterpriseView) {
        Enterprise enterprise = new EnterpriseView();

        BeanUtils.copyProperties(enterpriseView, enterprise);

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",enterpriseView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            enterprise.setProvinceName(String.valueOf(areaOfDic.getName()));
            enterprise.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }
        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", enterpriseView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            enterprise.setCityName(String.valueOf(areaOfDic.getName()));

            enterprise.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (enterpriseView.getCascader().size() !=3) {
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("id", enterpriseView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                enterprise.setDistrictName(String.valueOf(areaOfDic.getName()));
                enterprise.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", enterpriseView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                enterprise.setDistrictName(String.valueOf(areaOfDic.getName()));
                enterprise.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }
        QueryWrapper<Typesofregistration> qw = new QueryWrapper<>();
        qw.eq("id", enterpriseView.getCascaded1().get(0));
        List<Typesofregistration> list3 = typesofregistrationService.list(qw);
        for (Typesofregistration typesofregistration : list3) {
            enterprise.setRegisterBigName(typesofregistration.getName());
        }
        if(enterpriseView.getCascaded1().size()==2){
            QueryWrapper<Typesofregistration> qw1= new QueryWrapper<>();
            qw1.eq("id",enterpriseView.getCascaded1().get(1));
            List<Typesofregistration> list2= typesofregistrationService.list(qw1);
            for (Typesofregistration typesofregistration : list2) {
                enterprise.setRegisterSmallName(typesofregistration.getName());
            }
        }else{
            enterprise.setRegisterSmallName("无");
        }

        //所属行业名称
        QueryWrapper<IndustryOfDic> qw2 = new QueryWrapper<>();
        qw2.eq("id", enterpriseView.getCascaded2().get(0));
        List<IndustryOfDic> list2 = industryOfDicService.list(qw2);
        for (IndustryOfDic industryOfDic : list2) {
            enterprise.setIndustryBigName(industryOfDic.getName());
        }
        if(enterpriseView.getCascaded2().size()==2){
            QueryWrapper<IndustryOfDic> qw1= new QueryWrapper<>();
            qw1.eq("id",enterpriseView.getCascaded2().get(1));
            List<IndustryOfDic> listI= industryOfDicService.list(qw1);
            for (IndustryOfDic industryOfDic : listI) {
                enterprise.setIndustrySmallName(industryOfDic.getName());
            }
        }if(enterpriseView.getCascaded2().size()==3){
            QueryWrapper<IndustryOfDic> qw1= new QueryWrapper<>();
            qw1.eq("id",enterpriseView.getCascaded2().get(2));
            List<IndustryOfDic> listT1= industryOfDicService.list(qw1);
            for (IndustryOfDic industryOfDic : listT1) {
                enterprise.setIndustrySmallName(industryOfDic.getName());
            }
        }else{
            enterprise.setIndustrySmallName("无");
        }

        return enterpriseService.updateById(enterprise);
    }

    @GetMapping("/list")
    public IPage<Enterprise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);

        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", sysUser.getCompanyName());
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        IPage<Enterprise> page = enterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= EnterpriseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<Enterprise> dataList = getDataList(modelList, type);
            flag = enterpriseService.saveBatch(dataList);
        }
        return flag;
    }
    private List<Enterprise> getDataList(List<Object> modelList, String type) {
        List<Enterprise> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            EnterpriseModel enterpriseModel = (EnterpriseModel) object;
            //业务处理
            Enterprise enterprise = new Enterprise();
            BeanUtils.copyProperties(enterpriseModel, enterprise);
            if(enterpriseModel.getRiskLevel().equals("I级") ||enterpriseModel.getRiskLevel().equals("Ⅱ级") ||enterpriseModel.getRiskLevel().equals("Ⅲ级")){
                enterprise.setRiskLevel(enterpriseModel.getRiskLevel());
            }else{
                return null;
            }
            if(enterpriseModel.getSize().equals("大型") ||enterpriseModel.getSize().equals("小型") ||enterpriseModel.getSize().equals("中型")||enterpriseModel.getSize().equals("微型")){
                enterprise.setSize(enterpriseModel.getSize());
            }else{
                return null;
            }
            dataList.add(enterprise);
        }
        return dataList;
    }
}
