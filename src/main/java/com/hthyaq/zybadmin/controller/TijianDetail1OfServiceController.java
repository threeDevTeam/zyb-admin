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
import com.hthyaq.zybadmin.model.excelModel.TijianBasicOfServiceModel;
import com.hthyaq.zybadmin.model.excelModel.TijianDetail1OfServiceModel;
import com.hthyaq.zybadmin.model.vo.TijianBasicOfServiceView;
import com.hthyaq.zybadmin.model.vo.TijianDetail1OfServiceView;
import com.hthyaq.zybadmin.service.*;
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
 * 体检机构的具体报告1 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/tijianDetail1OfService")
public class TijianDetail1OfServiceController {
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    TijianDetail1OfServiceService tijianDetail1OfServiceService;
    @Autowired
    TijianBasicOfServiceService tijianBasicOfServiceService;
    @Autowired
    AreaOfDicService areaOfDicService;
    @Autowired
    TypesofregistrationService typesofregistrationService;
    @Autowired
    IndustryOfDicService industryOfDicService;
    @Autowired
    GangweiService gangweiService;
    @Autowired
    HazardousfactorsService hazardousfactorsService;
    @Autowired
    SysRoleUserService sysRoleUserService;

    @PostMapping("/add")
    public boolean add(@RequestBody TijianDetail1OfServiceView tijianDetail1OfServiceView, HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<ServiceOfRegister> qw = new QueryWrapper();
        qw.eq("name", sysUser.getCompanyName());
        List<ServiceOfRegister> list4 = serviceOfRegisterService.list(qw);
        for (ServiceOfRegister serviceOfRegister : list4) {
            if (serviceOfRegister.getType().equals("体检机构")) {
                //demo
                TijianDetail1OfService tijianDetail1OfService = new TijianDetail1OfService();
                BeanUtils.copyProperties(tijianDetail1OfServiceView, tijianDetail1OfService);
                tijianDetail1OfService.setCheckDate(AntdDateUtil.getInteger(tijianDetail1OfServiceView.getCheckDateStr()));


                QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", tijianDetail1OfServiceView.getCascader().get(0));
                List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
                for (AreaOfDic areaOfDic : list) {
                    tijianDetail1OfService.setProvinceName(String.valueOf(areaOfDic.getName()));
                    tijianDetail1OfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
                }

                QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id", tijianDetail1OfServiceView.getCascader().get(1));
                List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
                for (AreaOfDic areaOfDic : list1) {
                    tijianDetail1OfService.setCityName(String.valueOf(areaOfDic.getName()));
                    tijianDetail1OfService.setCityCode(String.valueOf(areaOfDic.getCode()));
                }

                if (tijianDetail1OfServiceView.getCascader().size() != 3) {
                    QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
                    queryWrapper3.eq("id", tijianDetail1OfServiceView.getCascader().get(1));
                    List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
                    for (AreaOfDic areaOfDic : list3) {
                        tijianDetail1OfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                        tijianDetail1OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
                    }
                } else {
                    QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("id", tijianDetail1OfServiceView.getCascader().get(2));
                    List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
                    for (AreaOfDic areaOfDic : list2) {
                        tijianDetail1OfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                        tijianDetail1OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
                    }
                }
                //登记注册类型
                QueryWrapper<Typesofregistration> qt = new QueryWrapper<>();
                qt.eq("id", tijianDetail1OfServiceView.getCascaded1().get(0));
                List<Typesofregistration> list3 = typesofregistrationService.list(qt);
                for (Typesofregistration typesofregistration : list3) {
                    tijianDetail1OfService.setRegisterBigName(typesofregistration.getName());
                }
                if (tijianDetail1OfServiceView.getCascaded1().size() == 2) {
                    QueryWrapper<Typesofregistration> qw1 = new QueryWrapper<>();
                    qw1.eq("id", tijianDetail1OfServiceView.getCascaded1().get(1));
                    List<Typesofregistration> list2 = typesofregistrationService.list(qw1);
                    for (Typesofregistration typesofregistration : list2) {
                        tijianDetail1OfService.setRegisterSmallName(typesofregistration.getName());
                    }
                } else {
                    tijianDetail1OfService.setRegisterSmallName("无");
                }
                //所属行业名称
                QueryWrapper<IndustryOfDic> qw2 = new QueryWrapper<>();
                qw2.eq("id", tijianDetail1OfServiceView.getCascaded2().get(0));
                List<IndustryOfDic> list2 = industryOfDicService.list(qw2);
                for (IndustryOfDic industryOfDic : list2) {
                    tijianDetail1OfService.setIndustryBigName(industryOfDic.getName());
                }
                if (tijianDetail1OfServiceView.getCascaded2().size() == 2) {
                    QueryWrapper<IndustryOfDic> qw1 = new QueryWrapper<>();
                    qw1.eq("id", tijianDetail1OfServiceView.getCascaded2().get(1));
                    List<IndustryOfDic> listI = industryOfDicService.list(qw1);
                    for (IndustryOfDic industryOfDic : listI) {
                        tijianDetail1OfService.setIndustrySmallName(industryOfDic.getName());
                    }
                }
                if (tijianDetail1OfServiceView.getCascaded2().size() == 3) {
                    QueryWrapper<IndustryOfDic> qw1 = new QueryWrapper<>();
                    qw1.eq("id", tijianDetail1OfServiceView.getCascaded2().get(2));
                    List<IndustryOfDic> listT1 = industryOfDicService.list(qw1);
                    for (IndustryOfDic industryOfDic : listT1) {
                        tijianDetail1OfService.setIndustrySmallName(industryOfDic.getName());
                    }
                } else {
                    tijianDetail1OfService.setIndustrySmallName("无");
                }


                //岗位名称
                QueryWrapper<Gangwei> qw3 = new QueryWrapper<>();
                qw3.eq("id", tijianDetail1OfServiceView.getCascaded3().get(0));
                List<Gangwei> listG = gangweiService.list(qw3);
                for (Gangwei gangwei : listG) {
                    tijianDetail1OfService.setPostBigName(gangwei.getName());
                }
                if (tijianDetail1OfServiceView.getCascaded3().size() == 2) {
                    QueryWrapper<Gangwei> qw1 = new QueryWrapper<>();
                    qw1.eq("id", tijianDetail1OfServiceView.getCascaded3().get(1));
                    List<Gangwei> list5 = gangweiService.list(qw1);
                    for (Gangwei gangwei : list5) {
                        tijianDetail1OfService.setPostSmallName(gangwei.getName());
                    }
                } else {
                    tijianDetail1OfService.setPostSmallName("无");
                }

                QueryWrapper<TijianBasicOfService> qw1 = new QueryWrapper();
                qw1.eq("name", serviceOfRegister.getName());
                List<TijianBasicOfService> list5 = tijianBasicOfServiceService.list(qw1);
                for (TijianBasicOfService tijianBasicOfService : list5) {
                    tijianDetail1OfService.setTijianBasicId(tijianBasicOfService.getId());
                }
                flag = tijianDetail1OfServiceService.save(tijianDetail1OfService);
            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return tijianDetail1OfServiceService.removeById(id);
    }

    @GetMapping("/getById")
    public TijianDetail1OfService getById(Integer id) {
        List list = new ArrayList();
        List listc1 = new ArrayList();
        List listc2 = new ArrayList();
        List listc3 = new ArrayList();
        TijianDetail1OfServiceView tijianDetail1OfServiceView = new TijianDetail1OfServiceView();
        TijianDetail1OfService tijianDetail1OfService = tijianDetail1OfServiceService.getById(id);
        BeanUtils.copyProperties(tijianDetail1OfService, tijianDetail1OfServiceView);

        tijianDetail1OfServiceView.setCheckDateStr( AntdDateUtil.getString(tijianDetail1OfService.getCheckDate()));

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", tijianDetail1OfService.getProvinceCode());
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list1) {
            list.add(areaOfDic.getId());
        }
        QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("code", tijianDetail1OfService.getCityCode());
        List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
        for (AreaOfDic areaOfDic : list2) {
            list.add(areaOfDic.getId());
        }


        if (tijianDetail1OfService.getCityName().equals(tijianDetail1OfService.getDistrictName())) {
            for (AreaOfDic areaOfDic : list2) {
                list.add(areaOfDic.getId());
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("code", tijianDetail1OfService.getDistrictCode());
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                list.add(areaOfDic.getId());
            }
        }
        tijianDetail1OfServiceView.setCascader((ArrayList) list);
        //登记注册类型
        QueryWrapper<Typesofregistration> qwT1 = new QueryWrapper<>();
        qwT1.eq("name", tijianDetail1OfService.getRegisterBigName());
        List<Typesofregistration> listTS = typesofregistrationService.list(qwT1);
        for (Typesofregistration typesofregistration : listTS) {
            listc1.add(typesofregistration.getId());
        }
        QueryWrapper<Typesofregistration> qwT2 = new QueryWrapper<>();
        qwT2.eq("name", tijianDetail1OfService.getRegisterSmallName());
        List<Typesofregistration> listT = typesofregistrationService.list(qwT2);
        for (Typesofregistration typesofregistration : listT) {
            listc1.add(typesofregistration.getId());
        }
        tijianDetail1OfServiceView.setCascaded1((ArrayList) listc1);


        //所属行业名称
        QueryWrapper<IndustryOfDic> qw = new QueryWrapper<>();
        qw.eq("name", tijianDetail1OfService.getIndustryBigName());
        List<IndustryOfDic> listI = industryOfDicService.list(qw);
        for (IndustryOfDic industryOfDic : listI) {
            listc2.add(industryOfDic.getId());
        }
        QueryWrapper<IndustryOfDic> qw3 = new QueryWrapper<>();
        qw3.eq("name", tijianDetail1OfService.getIndustrySmallName());
        List<IndustryOfDic> listc = industryOfDicService.list(qw3);
        for (IndustryOfDic industryOfDic : listc) {
            listc2.add(industryOfDic.getId());
        }
        tijianDetail1OfServiceView.setCascaded2((ArrayList) listc2);

        //岗位名称
        QueryWrapper<Gangwei> gw1 = new QueryWrapper<>();
        gw1.eq("name", tijianDetail1OfService.getPostBigName());
        List<Gangwei> gw = gangweiService.list(gw1);
        for (Gangwei gangwei : gw) {
            listc3.add(gangwei.getId());
        }
        QueryWrapper<Gangwei> gw5 = new QueryWrapper<>();
        gw5.eq("name", tijianDetail1OfService.getPostSmallName());
        List<Gangwei> gw3 = gangweiService.list(gw5);
        for (Gangwei gangwei : gw3) {
            listc3.add(gangwei.getId());
        }
        tijianDetail1OfServiceView.setCascaded3((ArrayList) listc3);

        return tijianDetail1OfServiceView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody TijianDetail1OfServiceView tijianDetail1OfServiceView) {
        TijianDetail1OfService tijianDetail1OfService = new TijianDetail1OfServiceView();

        BeanUtils.copyProperties(tijianDetail1OfServiceView, tijianDetail1OfService);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", tijianDetail1OfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            tijianDetail1OfService.setProvinceName(String.valueOf(areaOfDic.getName()));
            tijianDetail1OfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }
        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", tijianDetail1OfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            tijianDetail1OfService.setCityName(String.valueOf(areaOfDic.getName()));

            tijianDetail1OfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (tijianDetail1OfServiceView.getCascader().size() != 3) {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("id", tijianDetail1OfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                tijianDetail1OfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                tijianDetail1OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", tijianDetail1OfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                tijianDetail1OfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                tijianDetail1OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }
        QueryWrapper<Typesofregistration> qw = new QueryWrapper<>();
        qw.eq("id", tijianDetail1OfServiceView.getCascaded1().get(0));
        List<Typesofregistration> list3 = typesofregistrationService.list(qw);
        for (Typesofregistration typesofregistration : list3) {
            tijianDetail1OfService.setRegisterBigName(typesofregistration.getName());
        }
        if (tijianDetail1OfServiceView.getCascaded1().size() == 2) {
            QueryWrapper<Typesofregistration> qw1 = new QueryWrapper<>();
            qw1.eq("id", tijianDetail1OfServiceView.getCascaded1().get(1));
            List<Typesofregistration> list2 = typesofregistrationService.list(qw1);
            for (Typesofregistration typesofregistration : list2) {
                tijianDetail1OfService.setRegisterSmallName(typesofregistration.getName());
            }
        } else {
            tijianDetail1OfService.setRegisterSmallName("无");
        }
        //所属行业名称
        QueryWrapper<IndustryOfDic> qw2 = new QueryWrapper<>();
        qw2.eq("id", tijianDetail1OfServiceView.getCascaded2().get(0));
        List<IndustryOfDic> list2 = industryOfDicService.list(qw2);
        for (IndustryOfDic industryOfDic : list2) {
            tijianDetail1OfService.setIndustryBigName(industryOfDic.getName());
        }
        if (tijianDetail1OfServiceView.getCascaded2().size() == 2) {
            QueryWrapper<IndustryOfDic> qw1 = new QueryWrapper<>();
            qw1.eq("id", tijianDetail1OfServiceView.getCascaded2().get(1));
            List<IndustryOfDic> listI = industryOfDicService.list(qw1);
            for (IndustryOfDic industryOfDic : listI) {
                tijianDetail1OfService.setIndustrySmallName(industryOfDic.getName());
            }
        }
        if (tijianDetail1OfServiceView.getCascaded2().size() == 3) {
            QueryWrapper<IndustryOfDic> qw1 = new QueryWrapper<>();
            qw1.eq("id", tijianDetail1OfServiceView.getCascaded2().get(2));
            List<IndustryOfDic> listT1 = industryOfDicService.list(qw1);
            for (IndustryOfDic industryOfDic : listT1) {
                tijianDetail1OfService.setIndustrySmallName(industryOfDic.getName());
            }
        } else {
            tijianDetail1OfService.setIndustrySmallName("无");
        }

        //岗位名称
        QueryWrapper<Gangwei> qw3 = new QueryWrapper<>();
        qw3.eq("id", tijianDetail1OfServiceView.getCascaded3().get(0));
        List<Gangwei> listG = gangweiService.list(qw3);
        for (Gangwei gangwei : listG) {
            tijianDetail1OfService.setPostBigName(gangwei.getName());
        }
        if (tijianDetail1OfServiceView.getCascaded3().size() == 2) {
            QueryWrapper<Gangwei> qw1 = new QueryWrapper<>();
            qw1.eq("id", tijianDetail1OfServiceView.getCascaded3().get(1));
            List<Gangwei> list5 = gangweiService.list(qw1);
            for (Gangwei gangwei : list5) {
                tijianDetail1OfService.setPostSmallName(gangwei.getName());
            }
            return tijianDetail1OfServiceService.updateById(tijianDetail1OfService);
        }
        tijianDetail1OfService.setPostSmallName("无");
        return tijianDetail1OfServiceService.updateById(tijianDetail1OfService);
    }

    @GetMapping("/list")
    public IPage<TijianDetail1OfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String enterpriseName = jsonObject.getString("enterpriseName");
        String name = jsonObject.getString("name");
        QueryWrapper<SysRoleUser> qw = new QueryWrapper<>();
        qw.eq("userId", sysUser.getId());
        SysRoleUser sysRoleUser = sysRoleUserService.getOne(qw);
        if (sysRoleUser.getRoleId() == 1) {
            QueryWrapper<TijianDetail1OfService> queryWrapper = new QueryWrapper<>();
            if (!Strings.isNullOrEmpty(enterpriseName)) {
                queryWrapper.eq("enterpriseName", enterpriseName);
            }
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.eq("name", name);
            }
            queryWrapper.orderByDesc("id");
            IPage<TijianDetail1OfService> page = tijianDetail1OfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        } else {
            QueryWrapper<ServiceOfRegister> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", sysUser.getCompanyName());
            List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper1);
            for (ServiceOfRegister serviceOfRegister : list) {
                if (serviceOfRegister.getType().equals("体检机构")) {
                    QueryWrapper<TijianBasicOfService> queryWrapper2 = new QueryWrapper();
                    queryWrapper2.eq("name", serviceOfRegister.getName());
                    List<TijianBasicOfService> list2 = tijianBasicOfServiceService.list(queryWrapper2);
                    for (TijianBasicOfService tijianBasicOfService : list2) {
                        list1.clear();
                        list1.add(tijianBasicOfService.getId());
                    }
                }
            }

            QueryWrapper<TijianDetail1OfService> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("tijianBasicId", list1.get(0));
            if (!Strings.isNullOrEmpty(enterpriseName)) {
                queryWrapper.eq("enterpriseName", enterpriseName);
            }
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.eq("name", name);
            }
            queryWrapper.orderByDesc("id");
            IPage<TijianDetail1OfService> page = tijianDetail1OfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0] = TijianDetail1OfServiceModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files, modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<TijianDetail1OfService> dataList = getDataList(modelList, type, httpSession);
            flag = tijianDetail1OfServiceService.saveBatch(dataList);
        }
        return flag;
    }

    private List<TijianDetail1OfService> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<TijianDetail1OfService> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            TijianDetail1OfServiceModel tijianDetail1OfServiceModel = (TijianDetail1OfServiceModel) object;
            //业务处理
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<ServiceOfRegister> qw = new QueryWrapper();
            qw.eq("name", sysUser.getCompanyName());
            List<ServiceOfRegister> list4 = serviceOfRegisterService.list(qw);
            for (ServiceOfRegister serviceOfRegister : list4) {
                TijianDetail1OfService tijianDetail1OfService = new TijianDetail1OfService();
                QueryWrapper<TijianBasicOfService> qw1 = new QueryWrapper();
                qw1.eq("name", serviceOfRegister.getName());
                List<TijianBasicOfService> list5 = tijianBasicOfServiceService.list(qw1);
                for (TijianBasicOfService tijianBasicOfService : list5) {
                    tijianDetail1OfService.setTijianBasicId(tijianBasicOfService.getId());
                }

                BeanUtils.copyProperties(tijianDetail1OfServiceModel, tijianDetail1OfService);
                if(tijianDetail1OfServiceModel.getTijianType().equals("上岗前") ||tijianDetail1OfServiceModel.getTijianType().equals("离岗时") ||tijianDetail1OfServiceModel.getTijianType().equals("应急")
                        ||tijianDetail1OfServiceModel.getTijianType().equals("在岗期间")){
                    tijianDetail1OfService.setTijianType(tijianDetail1OfServiceModel.getTijianType());
                }else{
                    return null;
                }
                if(tijianDetail1OfServiceModel.getResult().equals("复查") ||tijianDetail1OfServiceModel.getResult().equals("目前未见异常") ||tijianDetail1OfServiceModel.getResult().equals("其他疾病")
                        ||tijianDetail1OfServiceModel.getResult().equals("职业禁忌证")){
                    tijianDetail1OfService.setResult(tijianDetail1OfServiceModel.getResult());
                }else{
                    return null;
                }
                dataList.add(tijianDetail1OfService);
            }
        }
        return dataList;
    }
}
