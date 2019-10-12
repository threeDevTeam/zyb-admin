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
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
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
       //登记注册类型
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
            queryWrapper.eq("enterpriseName", enterpriseName);
        }
        if (!Strings.isNullOrEmpty(decideResult)) {
            queryWrapper.eq("decideResult", decideResult);
        }

        IPage<JianceDetailOfService> page = jianceDetailOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
    @GetMapping("/cascadeData1")
    public List cascadeData() {
        List list1=new ArrayList();

        QueryWrapper<Typesofregistration> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("level",2);
        List<Typesofregistration> list = typesofregistrationService.list(queryWrapper);
        for (Typesofregistration typesofregistration : list) {
            FuView fuView=new FuView();
            fuView.setLabel(typesofregistration.getName());
            fuView.setValue(Integer.parseInt(String.valueOf(typesofregistration.getId())));
            list1.add(fuView);
        }
        return list1;
    }
    @GetMapping("/cascadeData2")
    public List<CascadeView> cascadeData2() {
        List<IndustryOfDic> list = industryOfDicService.list();
        System.out.println(list);
        return CascadeUtil.get(list);
    }
    @GetMapping("/cascadeData3")
    public List<CascadeView> cascadeData3() {
        List<Gangwei> list = gangweiService.list();
        System.out.println(list);
        return CascadeUtil.get(list);
    }
    @GetMapping("/cascadeData4")
    public List<CascadeView> cascadeData4() {
        List<Hazardousfactors> list = hazardousfactorsService.list();
        System.out.println(list);
        return CascadeUtil.get(list);
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[2];
        modelClassArr[1]=JianceDetailOfServiceModel.class;
        modelClassArr[2]=JianceDetailResultOfServiceModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<JianceDetailOfService> dataList = getDataList(modelList, type,httpSession);
             jianceDetailOfServiceService.saveBatch(dataList);
            List<JianceDetailResultOfService> dataList1 = getDataList1(modelList, type,httpSession);
            jianceDetailResultOfServiceService.saveBatch(dataList1);
        }
        return flag;
    }

    private List<JianceDetailOfService> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<JianceDetailOfService> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            JianceDetailOfServiceModel jianceDetailOfServiceModel = (JianceDetailOfServiceModel) object;
            //业务处理
            JianceDetailOfService jianceDetailOfService = new JianceDetailOfService();
            BeanUtils.copyProperties(jianceDetailOfServiceModel, jianceDetailOfService);
            dataList.add(jianceDetailOfService);
        }
        return dataList;
    }
    private List<JianceDetailResultOfService> getDataList1(List<Object> modelList, String type, HttpSession httpSession) {
        List<JianceDetailResultOfService> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            JianceDetailResultOfServiceModel jianceDetailResultOfServiceModel = (JianceDetailResultOfServiceModel) object;
            //业务处理
            JianceDetailResultOfService jianceDetailResultOfService = new JianceDetailResultOfService();
            BeanUtils.copyProperties(jianceDetailResultOfServiceModel, jianceDetailResultOfService);
            dataList.add(jianceDetailResultOfService);
        }
        return dataList;
    }
    }

