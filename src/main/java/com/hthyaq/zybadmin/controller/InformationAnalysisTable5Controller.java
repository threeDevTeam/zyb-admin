package com.hthyaq.zybadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.EnterpriseService;
import com.hthyaq.zybadmin.service.PersonOfEnterpriseService;
import com.hthyaq.zybadmin.service.PostDangerOfEnterpriseService;
import com.hthyaq.zybadmin.service.TouchPersonOfEnterpriseService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/informationAnalysisTable5")
public class InformationAnalysisTable5Controller {
    @Autowired
    TouchPersonOfEnterpriseService touchPersonOfEnterpriseService;
    @Autowired
    PersonOfEnterpriseService personOfEnterpriseService;
    @Autowired
    PostDangerOfEnterpriseService postDangerOfEnterpriseService;
    @Autowired
    EnterpriseService enterpriseService;
    public InformationAnalysisTable5 list(Integer year, String provinceCode, String cityCode, String districtCode, String name, String code, String size, String registerBigName, String registerSmallName, String industryBigName) {
        if (year != null) {
            InformationAnalysisTable5 informationAnalysisTable5=new InformationAnalysisTable5();
            //签订劳动合同人数
            QueryWrapper<TouchPersonOfEnterprise> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("year",year).eq("isSign","是");
            if(provinceCode!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("provinceCode",provinceCode);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }
            if(cityCode!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("cityCode",cityCode);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }
            if(districtCode!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("districtCode",districtCode);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }
            if(name!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("name",name);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }
            if(code!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("code",code);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }
            if(size!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("size",size);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }
            if(registerBigName!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("registerBigName",registerBigName);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }

            if(registerSmallName!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("registerSmallName",registerSmallName);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }

            if(industryBigName!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("industryBigName",industryBigName);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }
            informationAnalysisTable5.setNameNum1(touchPersonOfEnterpriseService.count(queryWrapper));
            //劳动合同签订率
            QueryWrapper<TouchPersonOfEnterprise> queryWrapper1=new QueryWrapper<>();
            queryWrapper1.eq("year",year);
            int count = touchPersonOfEnterpriseService.count(queryWrapper1);
            informationAnalysisTable5.setNameNum1l((float)informationAnalysisTable5.getNameNum1()/count);
            //缴纳工伤保险人数
            QueryWrapper<TouchPersonOfEnterprise> queryWrapper3=new QueryWrapper<>();
            queryWrapper3.eq("year",year).eq("isBuy","是");
            if(provinceCode!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("provinceCode",provinceCode);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }
            if(cityCode!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("cityCode",cityCode);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }
            if(districtCode!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("districtCode",districtCode);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }
            if(name!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("name",name);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }
            if(code!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("code",code);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }
            if(size!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("size",size);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }
            if(registerBigName!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("registerBigName",registerBigName);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }

            if(registerSmallName!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("registerSmallName",registerSmallName);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }

            if(industryBigName!=null){
                QueryWrapper<Enterprise> qw=new QueryWrapper<>();
                qw.eq("industryBigName",industryBigName);
                Enterprise enterprise = enterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",enterprise.getId());
            }
            informationAnalysisTable5.setNameNum2(touchPersonOfEnterpriseService.count(queryWrapper3));
            informationAnalysisTable5.setNameNum2l((float)informationAnalysisTable5.getNameNum2()/count);

        }
    return null;
    }

}
