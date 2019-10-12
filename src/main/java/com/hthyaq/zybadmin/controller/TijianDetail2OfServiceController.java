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
import com.hthyaq.zybadmin.model.excelModel.TijianDetail2OfServiceModel;
import com.hthyaq.zybadmin.model.vo.TijianDetail1OfServiceView;
import com.hthyaq.zybadmin.model.vo.TijianDetail2OfServiceView;
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
 * 体检机构的具体报告2 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/tijianDetail2OfService")
public class TijianDetail2OfServiceController {
    @Autowired
    TijianDetail2OfServiceService tijianDetail2OfServiceService;
    @Autowired
    TijianBasicOfServiceService tijianBasicOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
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
    ZybnameService zybnameService;

    @PostMapping("/add")
    public boolean add(@RequestBody TijianDetail2OfServiceView tijianDetail2OfServiceView, HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<ServiceOfRegister> qw = new QueryWrapper();
        qw.eq("name", sysUser.getCompanyName());
        List<ServiceOfRegister> list4 = serviceOfRegisterService.list(qw);
        for (ServiceOfRegister serviceOfRegister : list4) {
            if (serviceOfRegister.getType().equals("体检机构")) {
                //demo
                TijianDetail2OfService tijianDetail2OfService = new TijianDetail2OfService();
                BeanUtils.copyProperties(tijianDetail2OfServiceView, tijianDetail2OfService);
                QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", tijianDetail2OfServiceView.getCascader().get(0));
                List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
                for (AreaOfDic areaOfDic : list) {
                    tijianDetail2OfService.setProvinceName(String.valueOf(areaOfDic.getName()));
                    tijianDetail2OfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
                }

                QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id", tijianDetail2OfServiceView.getCascader().get(1));
                List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
                for (AreaOfDic areaOfDic : list1) {
                    tijianDetail2OfService.setCityName(String.valueOf(areaOfDic.getName()));
                    tijianDetail2OfService.setCityCode(String.valueOf(areaOfDic.getCode()));
                }

                if (tijianDetail2OfServiceView.getCascader().size() != 3) {
                    QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
                    queryWrapper3.eq("id", tijianDetail2OfServiceView.getCascader().get(1));
                    List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
                    for (AreaOfDic areaOfDic : list3) {
                        tijianDetail2OfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                        tijianDetail2OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
                    }
                } else {
                    QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("id", tijianDetail2OfServiceView.getCascader().get(2));
                    List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
                    for (AreaOfDic areaOfDic : list2) {
                        tijianDetail2OfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                        tijianDetail2OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
                    }
                }
                //登记注册类型
                QueryWrapper<Typesofregistration> qT = new QueryWrapper<>();
                qT.eq("id", tijianDetail2OfServiceView.getCascaded1().get(0));
                List<Typesofregistration> listT = typesofregistrationService.list(qT);
                for (Typesofregistration typesofregistration : listT) {
                    tijianDetail2OfService.setRegisterBigName(typesofregistration.getName());
                }
                if (tijianDetail2OfServiceView.getCascaded1().size() == 2) {
                    QueryWrapper<Typesofregistration> qw1 = new QueryWrapper<>();
                    qw1.eq("id", tijianDetail2OfServiceView.getCascaded1().get(1));
                    List<Typesofregistration> list2 = typesofregistrationService.list(qw1);
                    for (Typesofregistration typesofregistration : list2) {
                        tijianDetail2OfService.setRegisterSmallName(typesofregistration.getName());
                    }
                } else {
                    tijianDetail2OfService.setRegisterSmallName("无");
                }
                //所属行业名称
                QueryWrapper<IndustryOfDic> qw2 = new QueryWrapper<>();
                qw2.eq("id", tijianDetail2OfServiceView.getCascaded2().get(0));
                List<IndustryOfDic> list2 = industryOfDicService.list(qw2);
                for (IndustryOfDic industryOfDic : list2) {
                    tijianDetail2OfService.setIndustryBigName(industryOfDic.getName());
                }
                if (tijianDetail2OfServiceView.getCascaded2().size() == 2) {
                    QueryWrapper<IndustryOfDic> qw1 = new QueryWrapper<>();
                    qw1.eq("id", tijianDetail2OfServiceView.getCascaded2().get(1));
                    List<IndustryOfDic> list3 = industryOfDicService.list(qw1);
                    for (IndustryOfDic industryOfDic : list3) {
                        tijianDetail2OfService.setIndustrySmallName(industryOfDic.getName());
                    }
                }
                if (tijianDetail2OfServiceView.getCascaded2().size() == 3) {
                    QueryWrapper<IndustryOfDic> qw1 = new QueryWrapper<>();
                    qw1.eq("id", tijianDetail2OfServiceView.getCascaded2().get(2));
                    List<IndustryOfDic> listT1 = industryOfDicService.list(qw1);
                    for (IndustryOfDic industryOfDic : listT1) {
                        tijianDetail2OfService.setIndustrySmallName(industryOfDic.getName());
                    }
                } else {
                    tijianDetail2OfService.setIndustrySmallName("无");
                }

                //岗位名称
                QueryWrapper<Gangwei> qw3 = new QueryWrapper<>();
                qw3.eq("id", tijianDetail2OfServiceView.getCascaded3().get(0));
                List<Gangwei> list3 = gangweiService.list(qw3);
                for (Gangwei gangwei : list3) {
                    tijianDetail2OfService.setPostBigName(gangwei.getName());
                }
                if (tijianDetail2OfServiceView.getCascaded3().size() == 2) {
                    QueryWrapper<Gangwei> qw1 = new QueryWrapper<>();
                    qw1.eq("id", tijianDetail2OfServiceView.getCascaded3().get(1));
                    List<Gangwei> list5 = gangweiService.list(qw1);
                    for (Gangwei gangwei : list5) {
                        tijianDetail2OfService.setPostSmallName(gangwei.getName());
                    }
                } else {
                    tijianDetail2OfService.setPostSmallName("无");
                }
                //职业病危害因素名称
                QueryWrapper<Hazardousfactors> qw4 = new QueryWrapper<>();
                qw4.eq("id", tijianDetail2OfServiceView.getCascaded4().get(0));
                List<Hazardousfactors> list5 = hazardousfactorsService.list(qw4);
                for (Hazardousfactors hazardousfactors : list5) {
                    tijianDetail2OfService.setDangerBigName(hazardousfactors.getName());
                }
                if (tijianDetail2OfServiceView.getCascaded4().size() == 2) {
                    QueryWrapper<Hazardousfactors> qw1 = new QueryWrapper<>();
                    qw1.eq("id", tijianDetail2OfServiceView.getCascaded4().get(1));
                    List<Hazardousfactors> list6 = hazardousfactorsService.list(qw1);
                    for (Hazardousfactors hazardousfactors : list6) {
                        tijianDetail2OfService.setDangerSmallName(hazardousfactors.getName());
                    }
                } else {
                    tijianDetail2OfService.setDangerSmallName("无");
                }
                //职业病名称
                QueryWrapper<Zybname> qwZ = new QueryWrapper<>();
                qwZ.eq("id", tijianDetail2OfServiceView.getCascaded5().get(0));
                List<Zybname> listZ = zybnameService.list(qwZ);
                for (Zybname zybname : listZ) {
                    tijianDetail2OfService.setSickBigName(zybname.getName());
                }
                if (tijianDetail2OfServiceView.getCascaded5().size() == 2) {
                    QueryWrapper<Zybname> qw1 = new QueryWrapper<>();
                    qw1.eq("id", tijianDetail2OfServiceView.getCascaded5().get(1));
                    List<Zybname> list6 = zybnameService.list(qw1);
                    for (Zybname zybname : list6) {
                        tijianDetail2OfService.setSickSmallName(zybname.getName());
                    }
                } else {
                    tijianDetail2OfService.setDangerSmallName("无");
                }

                QueryWrapper<TijianBasicOfService> qw1 = new QueryWrapper();
                qw1.eq("name", serviceOfRegister.getName());
                List<TijianBasicOfService> listTj = tijianBasicOfServiceService.list(qw1);
                for (TijianBasicOfService tijianBasicOfService : listTj) {
                    tijianDetail2OfService.setTijianBasicId(tijianBasicOfService.getId());
                }
                flag = tijianDetail2OfServiceService.save(tijianDetail2OfService);
            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return tijianDetail2OfServiceService.removeById(id);
    }

    @GetMapping("/getById")
    public TijianDetail2OfService getById(Integer id) {

        List list = new ArrayList();
        List listc1 = new ArrayList();
        List listc2 = new ArrayList();
        List listc3 = new ArrayList();
        List listc4 = new ArrayList();
        List listc5 = new ArrayList();
        TijianDetail2OfServiceView tijianDetail2OfServiceView = new TijianDetail2OfServiceView();
        TijianDetail2OfService tijianDetail2OfService = tijianDetail2OfServiceService.getById(id);
        BeanUtils.copyProperties(tijianDetail2OfService, tijianDetail2OfServiceView);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", tijianDetail2OfService.getProvinceCode());
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list1) {
            list.add(areaOfDic.getId());
        }
        QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("code", tijianDetail2OfService.getCityCode());
        List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
        for (AreaOfDic areaOfDic : list2) {
            list.add(areaOfDic.getId());
        }


        if (tijianDetail2OfService.getCityName().equals(tijianDetail2OfService.getDistrictName())) {
            for (AreaOfDic areaOfDic : list2) {
                list.add(areaOfDic.getId());
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("code", tijianDetail2OfService.getDistrictCode());
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                list.add(areaOfDic.getId());
            }
        }

        tijianDetail2OfServiceView.setCascader((ArrayList) list);

        //登记注册类型
        QueryWrapper<Typesofregistration> qT = new QueryWrapper<>();
        qT.eq("name", tijianDetail2OfService.getRegisterBigName());
        List<Typesofregistration> listT = typesofregistrationService.list(qT);
        for (Typesofregistration typesofregistration : listT) {
            listc1.add(typesofregistration.getId());
        }
        QueryWrapper<Typesofregistration> qw2 = new QueryWrapper<>();
        qw2.eq("name", tijianDetail2OfService.getRegisterSmallName());
        List<Typesofregistration> listTS = typesofregistrationService.list(qw2);
        for (Typesofregistration typesofregistration : listTS) {
            listc1.add(typesofregistration.getId());
        }
        tijianDetail2OfServiceView.setCascaded1((ArrayList) listc1);
        //所属行业名称
        QueryWrapper<IndustryOfDic> qw = new QueryWrapper<>();
        qw.eq("name", tijianDetail2OfService.getIndustryBigName());
        List<IndustryOfDic> listI = industryOfDicService.list(qw);
        for (IndustryOfDic industryOfDic : listI) {
            listc2.add(industryOfDic.getId());
        }
        QueryWrapper<IndustryOfDic> qw3 = new QueryWrapper<>();
        qw3.eq("name", tijianDetail2OfService.getIndustrySmallName());
        List<IndustryOfDic> listc = industryOfDicService.list(qw3);
        for (IndustryOfDic industryOfDic : listc) {
            listc2.add(industryOfDic.getId());
        }
        tijianDetail2OfServiceView.setCascaded2((ArrayList) listc2);

        //岗位名称
        QueryWrapper<Gangwei> gw1 = new QueryWrapper<>();
        gw1.eq("name", tijianDetail2OfService.getPostBigName());
        List<Gangwei> gw = gangweiService.list(gw1);
        for (Gangwei gangwei : gw) {
            listc3.add(gangwei.getId());
        }
        QueryWrapper<Gangwei> qwQ = new QueryWrapper<>();
        qwQ.eq("name", tijianDetail2OfService.getPostSmallName());
        List<Gangwei> listQ = gangweiService.list(qwQ);
        for (Gangwei gangwei : listQ) {
            listc1.add(gangwei.getId());
        }
        tijianDetail2OfServiceView.setCascaded3((ArrayList) listc3);

        //职业病危害因素名称
        QueryWrapper<Hazardousfactors> qw6 = new QueryWrapper<>();
        qw6.eq("name", tijianDetail2OfService.getDangerBigName());
        List<Hazardousfactors> hd = hazardousfactorsService.list(qw6);
        for (Hazardousfactors hazardousfactors : hd) {
            listc4.add(hazardousfactors.getId());
        }
        QueryWrapper<Hazardousfactors> qw7 = new QueryWrapper<>();
        qw7.eq("name", tijianDetail2OfService.getDangerSmallName());
        List<Hazardousfactors> hd2 = hazardousfactorsService.list(qw7);
        for (Hazardousfactors hazardousfactors : hd2) {
            listc4.add(hazardousfactors.getId());
        }
        tijianDetail2OfServiceView.setCascaded4((ArrayList) listc4);
        //职业病名称
        QueryWrapper<Zybname> qwZ = new QueryWrapper<>();
        qwZ.eq("name", tijianDetail2OfService.getSickBigName());
        List<Zybname> hdZ1 = zybnameService.list(qwZ);
        for (Zybname zybname : hdZ1) {
            listc5.add(zybname.getId());
        }
        QueryWrapper<Zybname> qwZ2 = new QueryWrapper<>();
        qwZ2.eq("name", tijianDetail2OfService.getSickSmallName());
        List<Zybname> hdZ = zybnameService.list(qwZ2);
        for (Zybname zybname : hdZ) {
            listc5.add(zybname.getId());
        }
        tijianDetail2OfServiceView.setCascaded5((ArrayList) listc5);
        return tijianDetail2OfServiceView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody TijianDetail2OfServiceView tijianDetail2OfServiceView) {
        TijianDetail2OfService tijianDetail2OfService = new TijianDetail2OfServiceView();

        BeanUtils.copyProperties(tijianDetail2OfServiceView, tijianDetail2OfService);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", tijianDetail2OfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            tijianDetail2OfService.setProvinceName(String.valueOf(areaOfDic.getName()));
            tijianDetail2OfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }
        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", tijianDetail2OfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            tijianDetail2OfService.setCityName(String.valueOf(areaOfDic.getName()));

            tijianDetail2OfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (tijianDetail2OfServiceView.getCascader().size() != 3) {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("id", tijianDetail2OfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                tijianDetail2OfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                tijianDetail2OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", tijianDetail2OfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                tijianDetail2OfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                tijianDetail2OfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }
        //登记注册类型
        QueryWrapper<Typesofregistration> qw = new QueryWrapper<>();
        qw.eq("id", tijianDetail2OfServiceView.getCascaded1().get(0));
        List<Typesofregistration> list3 = typesofregistrationService.list(qw);
        for (Typesofregistration typesofregistration : list3) {
            tijianDetail2OfService.setRegisterBigName(typesofregistration.getName());
        }
        if (tijianDetail2OfServiceView.getCascaded1().size() == 2) {
            QueryWrapper<Typesofregistration> qw1 = new QueryWrapper<>();
            qw1.eq("id", tijianDetail2OfServiceView.getCascaded1().get(1));
            List<Typesofregistration> list2 = typesofregistrationService.list(qw1);
            for (Typesofregistration typesofregistration : list2) {
                tijianDetail2OfService.setRegisterSmallName(typesofregistration.getName());
            }
        } else {
            tijianDetail2OfService.setRegisterSmallName("无");
        }
        //所属行业名称
        QueryWrapper<IndustryOfDic> qw2 = new QueryWrapper<>();
        qw2.eq("id", tijianDetail2OfServiceView.getCascaded2().get(0));
        List<IndustryOfDic> list2 = industryOfDicService.list(qw2);
        for (IndustryOfDic industryOfDic : list2) {
            tijianDetail2OfService.setIndustryBigName(industryOfDic.getName());
        }
        if (tijianDetail2OfServiceView.getCascaded2().size() == 2) {
            QueryWrapper<IndustryOfDic> qw1 = new QueryWrapper<>();
            qw1.eq("id", tijianDetail2OfServiceView.getCascaded2().get(1));
            List<IndustryOfDic> listI = industryOfDicService.list(qw1);
            for (IndustryOfDic industryOfDic : listI) {
                tijianDetail2OfService.setIndustrySmallName(industryOfDic.getName());
            }
        }
        if (tijianDetail2OfServiceView.getCascaded2().size() == 3) {
            QueryWrapper<IndustryOfDic> qw1 = new QueryWrapper<>();
            qw1.eq("id", tijianDetail2OfServiceView.getCascaded2().get(2));
            List<IndustryOfDic> listT1 = industryOfDicService.list(qw1);
            for (IndustryOfDic industryOfDic : listT1) {
                tijianDetail2OfService.setIndustrySmallName(industryOfDic.getName());
            }
        } else {
            tijianDetail2OfService.setIndustrySmallName("无");
        }

        //岗位名称
        QueryWrapper<Gangwei> qw3 = new QueryWrapper<>();
        qw3.eq("id", tijianDetail2OfServiceView.getCascaded3().get(0));
        List<Gangwei> listG = gangweiService.list(qw3);
        for (Gangwei gangwei : listG) {
            tijianDetail2OfService.setPostBigName(gangwei.getName());
        }
        if (tijianDetail2OfServiceView.getCascaded3().size() == 2) {
            QueryWrapper<Gangwei> qw1 = new QueryWrapper<>();
            qw1.eq("id", tijianDetail2OfServiceView.getCascaded3().get(1));
            List<Gangwei> list5 = gangweiService.list(qw1);
            for (Gangwei gangwei : list5) {
                tijianDetail2OfService.setPostSmallName(gangwei.getName());
            }
        } else {
            tijianDetail2OfService.setPostSmallName("无");
        }
        //职业病危害因素名称
        QueryWrapper<Hazardousfactors> qw4 = new QueryWrapper<>();
        qw4.eq("id", tijianDetail2OfServiceView.getCascaded4().get(0));
        List<Hazardousfactors> list5 = hazardousfactorsService.list(qw4);
        for (Hazardousfactors hazardousfactors : list5) {
            tijianDetail2OfService.setDangerBigName(hazardousfactors.getName());
        }
        if (tijianDetail2OfServiceView.getCascaded4().size() == 2) {
            QueryWrapper<Hazardousfactors> qw1 = new QueryWrapper<>();
            qw1.eq("id", tijianDetail2OfServiceView.getCascaded4().get(1));
            List<Hazardousfactors> list6 = hazardousfactorsService.list(qw1);
            for (Hazardousfactors hazardousfactors : list6) {
                tijianDetail2OfService.setDangerSmallName(hazardousfactors.getName());
            }
        } else {
            tijianDetail2OfService.setDangerSmallName("无");
        }
        //职业病名称
        QueryWrapper<Zybname> qwZ = new QueryWrapper<>();
        qwZ.eq("id", tijianDetail2OfServiceView.getCascaded5().get(0));
        List<Zybname> listZ = zybnameService.list(qwZ);
        for (Zybname zybname : listZ) {
            tijianDetail2OfService.setSickBigName(zybname.getName());
        }
        if (tijianDetail2OfServiceView.getCascaded5().size() == 2) {
            QueryWrapper<Zybname> qw1 = new QueryWrapper<>();
            qw1.eq("id", tijianDetail2OfServiceView.getCascaded5().get(1));
            List<Zybname> list6 = zybnameService.list(qw1);
            for (Zybname zybname : list6) {
                tijianDetail2OfService.setSickSmallName(zybname.getName());
            }
        } else {
            tijianDetail2OfService.setSickSmallName("无");
        }

        return tijianDetail2OfServiceService.updateById(tijianDetail2OfService);
    }

    @GetMapping("/list")
    public IPage<TijianDetail2OfService> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String enterpriseName = jsonObject.getString("enterpriseName");
        String name = jsonObject.getString("name");
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

        QueryWrapper<TijianDetail2OfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tijianBasicId", list1.get(0));
        if (!Strings.isNullOrEmpty(enterpriseName)) {
            queryWrapper.eq("enterpriseName", enterpriseName);
        }
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }

        IPage<TijianDetail2OfService> page = tijianDetail2OfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0] = TijianDetail2OfServiceModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files, modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<TijianDetail2OfService> dataList = getDataList(modelList, type,httpSession);
            flag = tijianDetail2OfServiceService.saveBatch(dataList);
        }
        return flag;
    }

    private List<TijianDetail2OfService> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<TijianDetail2OfService> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            TijianDetail2OfServiceModel tijianDetail2OfServiceModel = (TijianDetail2OfServiceModel) object;
            //业务处理
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<ServiceOfRegister> qw = new QueryWrapper();
            qw.eq("name", sysUser.getCompanyName());
            List<ServiceOfRegister> list4 = serviceOfRegisterService.list(qw);
            for (ServiceOfRegister serviceOfRegister : list4) {

                TijianDetail2OfService tijianDetail2OfService = new TijianDetail2OfService();

                QueryWrapper<TijianBasicOfService> qw1 = new QueryWrapper();
                qw1.eq("name", serviceOfRegister.getName());
                List<TijianBasicOfService> listTj = tijianBasicOfServiceService.list(qw1);
                for (TijianBasicOfService tijianBasicOfService : listTj) {
                    tijianDetail2OfService.setTijianBasicId(tijianBasicOfService.getId());
                }
                BeanUtils.copyProperties(tijianDetail2OfServiceModel, tijianDetail2OfService);
                dataList.add(tijianDetail2OfService);
            }
        }
        return dataList;
    }
}