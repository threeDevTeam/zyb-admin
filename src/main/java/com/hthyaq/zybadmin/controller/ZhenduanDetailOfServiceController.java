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
import com.hthyaq.zybadmin.model.excelModel.ZhenduanBasicOfServiceModel;
import com.hthyaq.zybadmin.model.excelModel.ZhenduanDetailOfServiceModel;
import com.hthyaq.zybadmin.model.vo.TijianDetail1OfServiceView;
import com.hthyaq.zybadmin.model.vo.ZhenduanBasicOfServiceView;
import com.hthyaq.zybadmin.model.vo.ZhenduanDetailOfServiceView;
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
 * 诊断机构的具体报告 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/zhenduanDetailOfService")
public class ZhenduanDetailOfServiceController {
    @Autowired
    ZhenduanDetailOfServiceService zhenduanDetailOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    ZhenduanBasicOfServiceService zhenduanBasicOfServiceService;
    @Autowired
    AreaOfDicService areaOfDicService;
    @Autowired
    ZybnameService zybnameService;
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
    public boolean add(@RequestBody ZhenduanDetailOfServiceView zhenduanDetailOfServiceView, HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<ServiceOfRegister> qw = new QueryWrapper();
        qw.eq("name", sysUser.getCompanyName());
        List<ServiceOfRegister> list4 = serviceOfRegisterService.list(qw);
        for (ServiceOfRegister serviceOfRegister : list4) {
            if (serviceOfRegister.getType().equals("诊断机构")) {
                //demo
                ZhenduanDetailOfService zhenduanDetailOfService = new ZhenduanDetailOfService();
                BeanUtils.copyProperties(zhenduanDetailOfServiceView, zhenduanDetailOfService);
                QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", zhenduanDetailOfServiceView.getCascader().get(0));
                List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
                for (AreaOfDic areaOfDic : list) {
                    zhenduanDetailOfService.setProvinceName(String.valueOf(areaOfDic.getName()));
                    zhenduanDetailOfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
                }

                QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id", zhenduanDetailOfServiceView.getCascader().get(1));
                List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
                for (AreaOfDic areaOfDic : list1) {
                    zhenduanDetailOfService.setCityName(String.valueOf(areaOfDic.getName()));
                    zhenduanDetailOfService.setCityCode(String.valueOf(areaOfDic.getCode()));
                }

                if (zhenduanDetailOfServiceView.getCascader().size() != 3) {
                    QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
                    queryWrapper3.eq("id", zhenduanDetailOfServiceView.getCascader().get(1));
                    List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
                    for (AreaOfDic areaOfDic : list3) {
                        zhenduanDetailOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                        zhenduanDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
                    }
                } else {
                    QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("id", zhenduanDetailOfServiceView.getCascader().get(2));
                    List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
                    for (AreaOfDic areaOfDic : list2) {
                        zhenduanDetailOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                        zhenduanDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
                    }
                }
                //登记注册类型
                QueryWrapper<Typesofregistration> qT = new QueryWrapper<>();
                qT.eq("id", zhenduanDetailOfServiceView.getCascaded1().get(0));
                List<Typesofregistration> listT = typesofregistrationService.list(qT);
                for (Typesofregistration typesofregistration : listT) {
                    zhenduanDetailOfService.setRegisterBigName(typesofregistration.getName());
                }
                if (zhenduanDetailOfServiceView.getCascaded1().size() == 2) {
                    QueryWrapper<Typesofregistration> qw1 = new QueryWrapper<>();
                    qw1.eq("id", zhenduanDetailOfServiceView.getCascaded1().get(1));
                    List<Typesofregistration> list2 = typesofregistrationService.list(qw1);
                    for (Typesofregistration typesofregistration : list2) {
                        zhenduanDetailOfService.setRegisterSmallName(typesofregistration.getName());
                    }
                } else {
                    zhenduanDetailOfService.setRegisterSmallName("无");
                }
                //所属行业名称
                QueryWrapper<IndustryOfDic> qw2 = new QueryWrapper<>();
                qw2.eq("id", zhenduanDetailOfServiceView.getCascaded2().get(0));
                List<IndustryOfDic> list2 = industryOfDicService.list(qw2);
                for (IndustryOfDic industryOfDic : list2) {
                    zhenduanDetailOfService.setIndustryBigName(industryOfDic.getName());
                }
                if (zhenduanDetailOfServiceView.getCascaded2().size() == 2) {
                    QueryWrapper<IndustryOfDic> qw1 = new QueryWrapper<>();
                    qw1.eq("id", zhenduanDetailOfServiceView.getCascaded2().get(1));
                    List<IndustryOfDic> list3 = industryOfDicService.list(qw1);
                    for (IndustryOfDic industryOfDic : list3) {
                        zhenduanDetailOfService.setIndustrySmallName(industryOfDic.getName());
                    }
                }
                if (zhenduanDetailOfServiceView.getCascaded2().size() == 3) {
                    QueryWrapper<IndustryOfDic> qw1 = new QueryWrapper<>();
                    qw1.eq("id", zhenduanDetailOfServiceView.getCascaded2().get(2));
                    List<IndustryOfDic> list3 = industryOfDicService.list(qw1);
                    for (IndustryOfDic industryOfDic : list3) {
                        zhenduanDetailOfService.setIndustrySmallName(industryOfDic.getName());
                    }
                } else {
                    zhenduanDetailOfService.setIndustrySmallName("无");
                }
                //岗位名称
                QueryWrapper<Gangwei> qw3 = new QueryWrapper<>();
                qw3.eq("id", zhenduanDetailOfServiceView.getCascaded3().get(0));
                List<Gangwei> list3 = gangweiService.list(qw3);
                for (Gangwei gangwei : list3) {
                    zhenduanDetailOfService.setPostBigName(gangwei.getName());
                }
                if (zhenduanDetailOfServiceView.getCascaded3().size() == 2) {
                    QueryWrapper<Gangwei> qw1 = new QueryWrapper<>();
                    qw1.eq("id", zhenduanDetailOfServiceView.getCascaded3().get(1));
                    List<Gangwei> list5 = gangweiService.list(qw1);
                    for (Gangwei gangwei : list5) {
                        zhenduanDetailOfService.setPostSmallName(gangwei.getName());
                    }
                } else {
                    zhenduanDetailOfService.setPostSmallName("无");
                }
                //职业病名称
                QueryWrapper<Zybname> qw4 = new QueryWrapper<>();
                qw4.eq("id", zhenduanDetailOfServiceView.getCascaded5().get(0));
                List<Zybname> list5 = zybnameService.list(qw4);
                for (Zybname zybname : list5) {
                    zhenduanDetailOfService.setSickBigName(zybname.getName());
                }
                if (zhenduanDetailOfServiceView.getCascaded5().size() == 2) {
                    QueryWrapper<Zybname> qw1 = new QueryWrapper<>();
                    qw1.eq("id", zhenduanDetailOfServiceView.getCascaded5().get(1));
                    List<Zybname> list6 = zybnameService.list(qw1);
                    for (Zybname zybname : list6) {
                        zhenduanDetailOfService.setSickSmallName(zybname.getName());
                    }
                } else {
                    zhenduanDetailOfService.setSickSmallName("无");
                }

                //职业病危害因素名称
                QueryWrapper<Hazardousfactors> qwH = new QueryWrapper<>();
                qwH.eq("id", zhenduanDetailOfServiceView.getCascaded4().get(0));
                List<Hazardousfactors> listH = hazardousfactorsService.list(qwH);
                for (Hazardousfactors hazardousfactors : listH) {
                    zhenduanDetailOfService.setDangerBigName(hazardousfactors.getName());
                }
                if (zhenduanDetailOfServiceView.getCascaded4().size() == 2) {
                    QueryWrapper<Hazardousfactors> qw1 = new QueryWrapper<>();
                    qw1.eq("id", zhenduanDetailOfServiceView.getCascaded4().get(1));
                    List<Hazardousfactors> list6 = hazardousfactorsService.list(qw1);
                    for (Hazardousfactors hazardousfactors : list6) {
                        zhenduanDetailOfService.setDangerSmallName(hazardousfactors.getName());
                    }
                } else {
                    zhenduanDetailOfService.setDangerSmallName("无");
                }


                QueryWrapper<ZhenduanBasicOfService> qw1 = new QueryWrapper();
                qw1.eq("name", serviceOfRegister.getName());
                List<ZhenduanBasicOfService> listZ = zhenduanBasicOfServiceService.list(qw1);
                for (ZhenduanBasicOfService zhenduanBasicOfService : listZ) {
                    zhenduanDetailOfService.setZhenduanBasicId(zhenduanBasicOfService.getId());
                }
                flag = zhenduanDetailOfServiceService.save(zhenduanDetailOfService);
            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return zhenduanDetailOfServiceService.removeById(id);
    }

    @GetMapping("/getById")
    public ZhenduanDetailOfService getById(Integer id) {
        List list = new ArrayList();
        List listc1 = new ArrayList();
        List listc2 = new ArrayList();
        List listc3 = new ArrayList();
        List listc4 = new ArrayList();
        List listc5 = new ArrayList();
        ZhenduanDetailOfServiceView zhenduanDetailOfServiceView = new ZhenduanDetailOfServiceView();
        ZhenduanDetailOfService zhenduanDetailOfService = zhenduanDetailOfServiceService.getById(id);
        BeanUtils.copyProperties(zhenduanDetailOfService, zhenduanDetailOfServiceView);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", zhenduanDetailOfService.getProvinceCode());
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list1) {
            list.add(areaOfDic.getId());
        }
        QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("code", zhenduanDetailOfService.getCityCode());
        List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
        for (AreaOfDic areaOfDic : list2) {
            list.add(areaOfDic.getId());
        }


        if (zhenduanDetailOfService.getCityName().equals(zhenduanDetailOfService.getDistrictName())) {
            for (AreaOfDic areaOfDic : list2) {
                list.add(areaOfDic.getId());
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("code", zhenduanDetailOfService.getDistrictCode());
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                list.add(areaOfDic.getId());
            }
        }

        zhenduanDetailOfServiceView.setCascader((ArrayList) list);
        //登记注册类型
        QueryWrapper<Typesofregistration> qT = new QueryWrapper<>();
        qT.eq("name", zhenduanDetailOfService.getRegisterBigName());
        List<Typesofregistration> listT = typesofregistrationService.list(qT);
        for (Typesofregistration typesofregistration : listT) {
            listc1.add(typesofregistration.getId());
        }
        QueryWrapper<Typesofregistration> qw2 = new QueryWrapper<>();
        qw2.eq("name", zhenduanDetailOfService.getRegisterSmallName());
        List<Typesofregistration> listTS = typesofregistrationService.list(qw2);
        for (Typesofregistration typesofregistration : listTS) {
            listc1.add(typesofregistration.getId());
        }
        zhenduanDetailOfServiceView.setCascaded1((ArrayList) listc1);
        //所属行业名称
        QueryWrapper<IndustryOfDic> qw = new QueryWrapper<>();
        qw.eq("name", zhenduanDetailOfService.getIndustryBigName());
        List<IndustryOfDic> listI = industryOfDicService.list(qw);
        for (IndustryOfDic industryOfDic : listI) {
            listc2.add(industryOfDic.getId());
        }
        QueryWrapper<IndustryOfDic> qw3 = new QueryWrapper<>();
        qw3.eq("name", zhenduanDetailOfService.getIndustrySmallName());
        List<IndustryOfDic> listc = industryOfDicService.list(qw3);
        for (IndustryOfDic industryOfDic : listc) {
            listc2.add(industryOfDic.getId());
        }
        zhenduanDetailOfServiceView.setCascaded2((ArrayList) listc2);

        //岗位名称
        QueryWrapper<Gangwei> gw1 = new QueryWrapper<>();
        gw1.eq("name", zhenduanDetailOfService.getPostBigName());
        List<Gangwei> gw = gangweiService.list(gw1);
        for (Gangwei gangwei : gw) {
            listc3.add(gangwei.getId());
        }
        QueryWrapper<Gangwei> qwQ = new QueryWrapper<>();
        qwQ.eq("name", zhenduanDetailOfService.getPostSmallName());
        List<Gangwei> listQ = gangweiService.list(qwQ);
        for (Gangwei gangwei : listQ) {
            listc1.add(gangwei.getId());
        }
        zhenduanDetailOfServiceView.setCascaded3((ArrayList) listc3);

        //职业病名称
        QueryWrapper<Zybname> qwZ = new QueryWrapper<>();
        qwZ.eq("name", zhenduanDetailOfService.getSickBigName());
        List<Zybname> hd = zybnameService.list(qwZ);
        for (Zybname zybname : hd) {
            listc5.add(zybname.getId());
        }
        QueryWrapper<Zybname> qwZ2 = new QueryWrapper<>();
        qwZ2.eq("name", zhenduanDetailOfService.getSickSmallName());
        List<Zybname> hd2 = zybnameService.list(qwZ2);
        for (Zybname zybname : hd2) {
            listc5.add(zybname.getId());
        }
        zhenduanDetailOfServiceView.setCascaded5((ArrayList) listc5);
        //职业病危害因素名称
        QueryWrapper<Hazardousfactors> qw6 = new QueryWrapper<>();
        qw6.eq("name", zhenduanDetailOfService.getDangerBigName());
        List<Hazardousfactors> hdf = hazardousfactorsService.list(qw6);
        for (Hazardousfactors hazardousfactors : hdf) {
            listc4.add(hazardousfactors.getId());
        }
        QueryWrapper<Hazardousfactors> qw7 = new QueryWrapper<>();
        qw7.eq("name", zhenduanDetailOfService.getDangerSmallName());
        List<Hazardousfactors> hdf2 = hazardousfactorsService.list(qw7);
        for (Hazardousfactors hazardousfactors : hdf2) {
            listc4.add(hazardousfactors.getId());
        }
        zhenduanDetailOfServiceView.setCascaded4((ArrayList) listc4);

        return zhenduanDetailOfServiceView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody ZhenduanDetailOfServiceView zhenduanDetailOfServiceView) {

        ZhenduanDetailOfService zhenduanDetailOfService = new ZhenduanDetailOfServiceView();

        BeanUtils.copyProperties(zhenduanDetailOfServiceView, zhenduanDetailOfService);
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", zhenduanDetailOfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            zhenduanDetailOfService.setProvinceName(String.valueOf(areaOfDic.getName()));
            zhenduanDetailOfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }
        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", zhenduanDetailOfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            zhenduanDetailOfService.setCityName(String.valueOf(areaOfDic.getName()));

            zhenduanDetailOfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (zhenduanDetailOfServiceView.getCascader().size() != 3) {
            QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("id", zhenduanDetailOfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                zhenduanDetailOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                zhenduanDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", zhenduanDetailOfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                zhenduanDetailOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                zhenduanDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }
        //登记注册类型
        QueryWrapper<Typesofregistration> qw = new QueryWrapper<>();
        qw.eq("id", zhenduanDetailOfServiceView.getCascaded1().get(0));
        List<Typesofregistration> list3 = typesofregistrationService.list(qw);
        for (Typesofregistration typesofregistration : list3) {
            zhenduanDetailOfService.setRegisterBigName(typesofregistration.getName());
        }
        if (zhenduanDetailOfServiceView.getCascaded1().size() == 2) {
            QueryWrapper<Typesofregistration> qw1 = new QueryWrapper<>();
            qw1.eq("id", zhenduanDetailOfServiceView.getCascaded1().get(1));
            List<Typesofregistration> list2 = typesofregistrationService.list(qw1);
            for (Typesofregistration typesofregistration : list2) {
                zhenduanDetailOfService.setRegisterSmallName(typesofregistration.getName());
            }
        } else {
            zhenduanDetailOfService.setRegisterSmallName("无");
        }
        //所属行业名称
        QueryWrapper<IndustryOfDic> qw2 = new QueryWrapper<>();
        qw2.eq("id", zhenduanDetailOfServiceView.getCascaded2().get(0));
        List<IndustryOfDic> list2 = industryOfDicService.list(qw2);
        for (IndustryOfDic industryOfDic : list2) {
            zhenduanDetailOfService.setIndustryBigName(industryOfDic.getName());
        }
        if (zhenduanDetailOfServiceView.getCascaded2().size() == 2) {
            QueryWrapper<IndustryOfDic> qw1 = new QueryWrapper<>();
            qw1.eq("id", zhenduanDetailOfServiceView.getCascaded2().get(1));
            List<IndustryOfDic> listI = industryOfDicService.list(qw1);
            for (IndustryOfDic industryOfDic : listI) {
                zhenduanDetailOfService.setIndustrySmallName(industryOfDic.getName());
            }
        }
        if (zhenduanDetailOfServiceView.getCascaded2().size() == 3) {
            QueryWrapper<IndustryOfDic> qw1 = new QueryWrapper<>();
            qw1.eq("id", zhenduanDetailOfServiceView.getCascaded2().get(2));
            List<IndustryOfDic> listI = industryOfDicService.list(qw1);
            for (IndustryOfDic industryOfDic : listI) {
                zhenduanDetailOfService.setIndustrySmallName(industryOfDic.getName());
            }
        } else {
            zhenduanDetailOfService.setIndustrySmallName("无");
        }

        //岗位名称
        QueryWrapper<Gangwei> qw3 = new QueryWrapper<>();
        qw3.eq("id", zhenduanDetailOfServiceView.getCascaded3().get(0));
        List<Gangwei> listG = gangweiService.list(qw3);
        for (Gangwei gangwei : listG) {
            zhenduanDetailOfService.setPostBigName(gangwei.getName());
        }
        if (zhenduanDetailOfServiceView.getCascaded3().size() == 2) {
            QueryWrapper<Gangwei> qw1 = new QueryWrapper<>();
            qw1.eq("id", zhenduanDetailOfServiceView.getCascaded3().get(1));
            List<Gangwei> list5 = gangweiService.list(qw1);
            for (Gangwei gangwei : list5) {
                zhenduanDetailOfService.setPostSmallName(gangwei.getName());
            }
        } else {
            zhenduanDetailOfService.setPostSmallName("无");
        }
        //职业病名称
        QueryWrapper<Zybname> qw4 = new QueryWrapper<>();
        qw4.eq("id", zhenduanDetailOfServiceView.getCascaded5().get(0));
        List<Zybname> list5 = zybnameService.list(qw4);
        for (Zybname zybname : list5) {
            zhenduanDetailOfService.setSickBigName(zybname.getName());
        }
        if (zhenduanDetailOfServiceView.getCascaded5().size() == 2) {
            QueryWrapper<Zybname> qw1 = new QueryWrapper<>();
            qw1.eq("id", zhenduanDetailOfServiceView.getCascaded5().get(1));
            List<Zybname> list6 = zybnameService.list(qw1);
            for (Zybname zybname : list6) {
                zhenduanDetailOfService.setSickSmallName(zybname.getName());
            }
        } else {
            zhenduanDetailOfService.setSickSmallName("无");
        }
        //职业病危害因素名称
        QueryWrapper<Hazardousfactors> qwH = new QueryWrapper<>();
        qwH.eq("id", zhenduanDetailOfServiceView.getCascaded4().get(0));
        List<Hazardousfactors> listH = hazardousfactorsService.list(qwH);
        for (Hazardousfactors hazardousfactors : listH) {
            zhenduanDetailOfService.setDangerBigName(hazardousfactors.getName());
        }
        if (zhenduanDetailOfServiceView.getCascaded4().size() == 2) {
            QueryWrapper<Hazardousfactors> qw1 = new QueryWrapper<>();
            qw1.eq("id", zhenduanDetailOfServiceView.getCascaded4().get(1));
            List<Hazardousfactors> list6 = hazardousfactorsService.list(qw1);
            for (Hazardousfactors hazardousfactors : list6) {
                zhenduanDetailOfService.setDangerSmallName(hazardousfactors.getName());
            }
        } else {
            zhenduanDetailOfService.setDangerSmallName("无");
        }
        return zhenduanDetailOfServiceService.updateById(zhenduanDetailOfService);
    }

    @GetMapping("/list")
    public IPage<ZhenduanDetailOfService> list(String json, HttpSession httpSession) {
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
            QueryWrapper<ZhenduanDetailOfService> queryWrapper = new QueryWrapper<>();
            if (!Strings.isNullOrEmpty(enterpriseName)) {
                queryWrapper.eq("enterpriseName", enterpriseName);
            }
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.eq("name", name);
            }
            queryWrapper.orderByDesc("id");
            IPage<ZhenduanDetailOfService> page = zhenduanDetailOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        } else {
            QueryWrapper<ServiceOfRegister> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", sysUser.getCompanyName());
            List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper1);
            for (ServiceOfRegister serviceOfRegister : list) {
                if (serviceOfRegister.getType().equals("诊断机构")) {
                    QueryWrapper<ZhenduanBasicOfService> queryWrapper2 = new QueryWrapper();
                    queryWrapper2.eq("name", serviceOfRegister.getName());
                    List<ZhenduanBasicOfService> list2 = zhenduanBasicOfServiceService.list(queryWrapper2);
                    for (ZhenduanBasicOfService zhenduanBasicOfService : list2) {
                        list1.clear();
                        list1.add(zhenduanBasicOfService.getId());
                    }
                }
            }

            QueryWrapper<ZhenduanDetailOfService> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("zhenduanBasicId", list1.get(0));
            if (!Strings.isNullOrEmpty(enterpriseName)) {
                queryWrapper.eq("enterpriseName", enterpriseName);
            }
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.eq("name", name);
            }
            queryWrapper.orderByDesc("id");
            IPage<ZhenduanDetailOfService> page = zhenduanDetailOfServiceService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @GetMapping("/cascadeData5")
    public List<CascadeView> cascadeData() {
        List<Zybname> list = zybnameService.list();
        System.out.println(list);
        return CascadeUtil.get(list);

    }

    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0] = ZhenduanDetailOfServiceModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files, modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<ZhenduanDetailOfService> dataList = getDataList(modelList, type,httpSession);
            flag = zhenduanDetailOfServiceService.saveBatch(dataList);
        }
        return flag;
    }

    private List<ZhenduanDetailOfService> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<ZhenduanDetailOfService> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            ZhenduanDetailOfServiceModel zhenduanDetailOfServiceModel = (ZhenduanDetailOfServiceModel) object;
            //业务处理
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<ServiceOfRegister> qw = new QueryWrapper();
            qw.eq("name", sysUser.getCompanyName());
            List<ServiceOfRegister> list4 = serviceOfRegisterService.list(qw);
            for (ServiceOfRegister serviceOfRegister : list4) {
                ZhenduanDetailOfService zhenduanDetailOfService = new ZhenduanDetailOfService();
                QueryWrapper<ZhenduanBasicOfService> qw1 = new QueryWrapper();
                qw1.eq("name", serviceOfRegister.getName());
                List<ZhenduanBasicOfService> listZ = zhenduanBasicOfServiceService.list(qw1);
                for (ZhenduanBasicOfService zhenduanBasicOfService : listZ) {
                    zhenduanDetailOfService.setZhenduanBasicId(zhenduanBasicOfService.getId());
                }
                BeanUtils.copyProperties(zhenduanDetailOfServiceModel, zhenduanDetailOfService);
                dataList.add(zhenduanDetailOfService);
            }
        }
        return dataList;
    }
}

