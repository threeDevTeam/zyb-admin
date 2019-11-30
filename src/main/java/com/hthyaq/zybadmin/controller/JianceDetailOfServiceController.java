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
import com.hthyaq.zybadmin.common.utils.AntdDateUtil;
import com.hthyaq.zybadmin.common.utils.cascade.CascadeUtil;
import com.hthyaq.zybadmin.common.utils.cascade.CascadeView;
import com.hthyaq.zybadmin.model.bean.Child2;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.excelModel.JianceBasicOfServiceModel;
import com.hthyaq.zybadmin.model.excelModel.JianceDetailOfServiceModel;
import com.hthyaq.zybadmin.model.excelModel.JianceDetailResultOfServiceModel;
import com.hthyaq.zybadmin.model.vo.FuView;
import com.hthyaq.zybadmin.model.vo.JianceDetailOfServiceView;
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
 * 检测机构的具体报告
 * <p>
 * 检测机构的具体报告
 * 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/jianceDetailOfService")
public class JianceDetailOfServiceController {
    @Autowired
    JianceDetailOfServiceService jianceDetailOfServiceService;
    @Autowired
    JianceDetailResultOfServiceService jianceDetailResultOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    JianceBasicOfServiceService jianceBasicOfServiceService;
    @Autowired
    AreaOfDicService areaOfDicService;
    @Autowired
    TypesofregistrationService typesofregistrationService;
    @Autowired
    GangweiService gangweiService;
    @Autowired
    IndustryOfDicService industryOfDicService;
    @Autowired
    HazardousfactorsService hazardousfactorsService;
    @Autowired
    SysRoleUserService sysRoleUserService;

    @PostMapping("/add")
    public boolean add(@RequestBody JianceDetailOfServiceView jianceDetailOfServiceView, HttpSession httpSession) {
        return jianceDetailOfServiceService.saveData(jianceDetailOfServiceView, httpSession);
    }


    @GetMapping("/delete")
    public boolean delete(String id) {
        return jianceDetailOfServiceService.deleteData(id);
    }

    @GetMapping("/getById")
    public JianceDetailOfServiceView getById(Integer id) {
        JianceDetailOfServiceView jianceDetailOfServiceView = new JianceDetailOfServiceView();
        List list = new ArrayList();
        List listc1 = new ArrayList();
        List listc2 = new ArrayList();
        List listc3 = new ArrayList();
        List listc4 = new ArrayList();
        //demo
        JianceDetailOfService jianceDetailOfService = jianceDetailOfServiceService.getById(id);
        //将demo的数据设置到demoData
        BeanUtils.copyProperties(jianceDetailOfService, jianceDetailOfServiceView);
        jianceDetailOfServiceView.setCheckDateStr( AntdDateUtil.getString(jianceDetailOfService.getCheckDate()));

        //登记注册类型
        QueryWrapper<Typesofregistration> qwt = new QueryWrapper<>();
        qwt.eq("name", jianceDetailOfService.getRegisterBigName());
        List<Typesofregistration> listT1 = typesofregistrationService.list(qwt);
        for (Typesofregistration typesofregistration : listT1) {
            listc1.add(typesofregistration.getId());
        }
        QueryWrapper<Typesofregistration> qw2 = new QueryWrapper<>();
        qw2.eq("name", jianceDetailOfService.getRegisterSmallName());
        List<Typesofregistration> listTS = typesofregistrationService.list(qw2);
        for (Typesofregistration typesofregistration : listTS) {
            listc1.add(typesofregistration.getId());
        }
        jianceDetailOfServiceView.setCascaded1((ArrayList) listc1);




        //所属行业名称
        QueryWrapper<IndustryOfDic> qw = new QueryWrapper<>();
        qw.eq("name", jianceDetailOfService.getIndustryBigName());
        List<IndustryOfDic> listT = industryOfDicService.list(qw);
        for (IndustryOfDic industryOfDic : listT) {
            listc2.add(industryOfDic.getId());
        }
        QueryWrapper<IndustryOfDic> qw3 = new QueryWrapper<>();
        qw3.eq("name", jianceDetailOfService.getIndustrySmallName());
        List<IndustryOfDic> listc = industryOfDicService.list(qw3);
        for (IndustryOfDic industryOfDic : listc) {
            listc2.add(industryOfDic.getId());
        }
        jianceDetailOfServiceView.setCascaded2((ArrayList) listc2);

        //岗位名称
        QueryWrapper<Gangwei> gw1 = new QueryWrapper<>();
        gw1.eq("name", jianceDetailOfService.getPostBigName());
        List<Gangwei> gw = gangweiService.list(gw1);
        for (Gangwei gangwei : gw) {
            listc3.add(gangwei.getId());
        }
        QueryWrapper<Gangwei> gw5 = new QueryWrapper<>();
        gw5.eq("name", jianceDetailOfService.getPostSmallName());
        List<Gangwei> gw3 = gangweiService.list(gw5);
        for (Gangwei gangwei : gw3) {
            listc3.add(gangwei.getId());
        }
        jianceDetailOfServiceView.setCascaded3((ArrayList) listc3);

        //职业病危害因素名称
        QueryWrapper<Hazardousfactors> qw6 = new QueryWrapper<>();
        qw6.eq("name", jianceDetailOfService.getDangerBigName());
        List<Hazardousfactors> hd = hazardousfactorsService.list(qw6);
        for (Hazardousfactors hazardousfactors : hd) {
            listc4.add(hazardousfactors.getId());
        }
        QueryWrapper<Hazardousfactors> qw7 = new QueryWrapper<>();
        qw7.eq("name", jianceDetailOfService.getDangerSmallName());
        List<Hazardousfactors> hd2 = hazardousfactorsService.list(qw7);
        for (Hazardousfactors hazardousfactors : hd2) {
            listc4.add(hazardousfactors.getId());
        }
        jianceDetailOfServiceView.setCascaded4((ArrayList) listc4);


        //省/市/区
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", jianceDetailOfService.getProvinceCode());
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list1) {
            list.add(areaOfDic.getId());
        }
        QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("code", jianceDetailOfService.getCityCode());
        List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
        for (AreaOfDic areaOfDic : list2) {
            list.add(areaOfDic.getId());
        }


        if (jianceDetailOfService.getCityName().equals(jianceDetailOfService.getDistrictName())) {
            for (AreaOfDic areaOfDic : list2) {
                list.add(areaOfDic.getId());
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("code", jianceDetailOfService.getDistrictCode());
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                list.add(areaOfDic.getId());
            }
        }
        jianceDetailOfServiceView.setCascader((ArrayList) list);


        //demoCourse
        QueryWrapper<JianceDetailResultOfService> queryWrapper4 = new QueryWrapper<>();
        queryWrapper4.eq("jianceDetailId", id);
        List<JianceDetailResultOfService> demoCourseList = jianceDetailResultOfServiceService.list(queryWrapper4);

        //将demoCourse的数据设置到demoData
        jianceDetailOfServiceView.setCourse(new Child2<>(demoCourseList));
        return jianceDetailOfServiceView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody JianceDetailOfServiceView jianceDetailOfServiceView) {
        return jianceDetailOfServiceService.editData(jianceDetailOfServiceView);
    }


    @GetMapping("/list")
    public IPage<JianceDetailOfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String enterpriseName = jsonObject.getString("enterpriseName");
        String decideResult = jsonObject.getString("decideResult");
        QueryWrapper<SysRoleUser> qw = new QueryWrapper<>();
        qw.eq("userId", sysUser.getId());
        SysRoleUser sysRoleUser = sysRoleUserService.getOne(qw);
        if (sysRoleUser.getRoleId() == 1) {
            QueryWrapper<JianceDetailOfService> queryWrapper = new QueryWrapper<>();
            if (!Strings.isNullOrEmpty(enterpriseName)) {
                queryWrapper.like("enterpriseName", enterpriseName);
            }
            if (!Strings.isNullOrEmpty(decideResult)) {
                queryWrapper.like("decideResult", decideResult);
            }
            queryWrapper.orderByDesc("id");
            IPage<JianceDetailOfService> page = jianceDetailOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        } else {
            QueryWrapper<ServiceOfRegister> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", sysUser.getCompanyName());
            List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper1);
            for (ServiceOfRegister serviceOfRegister : list) {
                if (serviceOfRegister.getType().equals("检测机构")) {
                    QueryWrapper<JianceBasicOfService> queryWrapper2 = new QueryWrapper();
                    queryWrapper2.eq("name", serviceOfRegister.getName());
                    List<JianceBasicOfService> list2 = jianceBasicOfServiceService.list(queryWrapper2);
                    for (JianceBasicOfService jianceBasicOfService : list2) {
                        list1.clear();
                        list1.add(jianceBasicOfService.getId());
                    }
                }
            }

            QueryWrapper<JianceDetailOfService> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("jianceBasicId", list1.get(0));
            if (!Strings.isNullOrEmpty(enterpriseName)) {
                queryWrapper.like("enterpriseName", enterpriseName);
            }
            if (!Strings.isNullOrEmpty(decideResult)) {
                queryWrapper.like("decideResult", decideResult);
            }
            queryWrapper.orderByDesc("id");
            IPage<JianceDetailOfService> page = jianceDetailOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @GetMapping("/cascadeData1")
    public List cascadeData() {
        List<Typesofregistration> list = typesofregistrationService.list();
        return CascadeUtil.get(list);
    }

    @GetMapping("/cascadeData2")
    public List<CascadeView> cascadeData2() {
        List<IndustryOfDic> list = industryOfDicService.list();
        return CascadeUtil.get(list);
    }

    @GetMapping("/cascadeData3")
    public List<CascadeView> cascadeData3() {
        List<Gangwei> list = gangweiService.list();
        return CascadeUtil.get(list);
    }

    @GetMapping("/cascadeData4")
    public List<CascadeView> cascadeData4() {
        List<Hazardousfactors> list = hazardousfactorsService.list();
        return CascadeUtil.get(list);
    }

    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[2];
        modelClassArr[0] = JianceDetailOfServiceModel.class;
        modelClassArr[1] = JianceDetailResultOfServiceModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files, modelClassArr);
        //model->entity
        long id = 0;
        long fid = 0;
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            if (entry.getKey().equals("1")) {
                String type = entry.getKey();
                List<Object> modelList = entry.getValue();
                JianceDetailOfService jianceDetailOfService = getDataList(modelList, type, httpSession);
                jianceDetailOfServiceService.save(jianceDetailOfService);
                id = jianceDetailOfService.getId();
                fid = jianceDetailOfService.getJianceBasicId();
            } else if (entry.getKey().equals("2")) {
                String type = entry.getKey();
                List<Object> modelList = entry.getValue();
                JianceDetailResultOfService jianceDetailResultOfService = getDataList1(modelList, type, httpSession);
                jianceDetailResultOfService.setJianceBasicId(fid);
                jianceDetailResultOfService.setJianceDetailId(id);
                jianceDetailResultOfServiceService.save(jianceDetailResultOfService);
            }
        }
        return flag;
    }

    private JianceDetailResultOfService getDataList1(List<Object> modelList, String type, HttpSession httpSession) {

        for (Object object : modelList) {
            JianceDetailResultOfServiceModel jianceDetailResultOfServiceModel = (JianceDetailResultOfServiceModel) object;
            //业务处理
            JianceDetailResultOfService jianceDetailResultOfService = new JianceDetailResultOfService();
            BeanUtils.copyProperties(jianceDetailResultOfServiceModel, jianceDetailResultOfService);
            if(jianceDetailResultOfServiceModel.getType().equals("CSTEL") ||jianceDetailResultOfServiceModel.getType().equals("超限倍数") ||jianceDetailResultOfServiceModel.getType().equals("其他")
                    ||jianceDetailResultOfServiceModel.getType().equals("CTWA")||jianceDetailResultOfServiceModel.getType().equals("CMAC")){
                jianceDetailResultOfService.setType(jianceDetailResultOfServiceModel.getType());
            }else{
                return null;
            }
            return jianceDetailResultOfService;
        }
        return null;
    }

    private JianceDetailOfService getDataList(List<Object> modelList, String type, HttpSession httpSession) {

        for (Object object : modelList) {
            JianceDetailOfServiceModel jianceDetailOfServiceModel = (JianceDetailOfServiceModel) object;
            //业务处理
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<ServiceOfRegister> qw = new QueryWrapper();
            qw.eq("name", sysUser.getCompanyName());
            List<ServiceOfRegister> list4 = serviceOfRegisterService.list(qw);
            for (ServiceOfRegister serviceOfRegister : list4) {
                JianceDetailOfService jianceDetailOfService = new JianceDetailOfService();
                QueryWrapper<JianceBasicOfService> qw1 = new QueryWrapper();
                qw1.eq("name", serviceOfRegister.getName());
                List<JianceBasicOfService> list7 = jianceBasicOfServiceService.list(qw1);
                for (JianceBasicOfService jianceBasicOfService : list7) {
                    jianceDetailOfService.setJianceBasicId(jianceBasicOfService.getId());
                }

                BeanUtils.copyProperties(jianceDetailOfServiceModel, jianceDetailOfService);
                return jianceDetailOfService;
            }
        }
        return null;
    }


}

