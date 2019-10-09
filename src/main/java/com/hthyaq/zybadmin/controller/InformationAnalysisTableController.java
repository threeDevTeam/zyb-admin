package com.hthyaq.zybadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/informationAnalysisTable")
public class InformationAnalysisTableController {
    @Autowired
    TouchPersonOfEnterpriseService touchPersonOfEnterpriseService;
    @Autowired
    PersonOfEnterpriseService personOfEnterpriseService;
    @Autowired
    PostDangerOfEnterpriseService postDangerOfEnterpriseService;
    @Autowired
    EnterpriseService enterpriseService;
    public InformationAnalysisTable list(Integer year, String provinceCode, String cityCode, String districtCode, String name, String code, String size, String dangerBigName, String dangerSmallName, String registerBigName, String registerSmallName, String industryBigName) {
        if (year != null) {
            InformationAnalysisTable informationAnalysisTable=new InformationAnalysisTable();
           //用人单位数
            QueryWrapper<TouchPersonOfEnterprise> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("year",year);
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
            if(dangerBigName!=null){
                QueryWrapper<PostDangerOfEnterprise> qw=new QueryWrapper<>();
                qw.eq("dangerBigName",dangerBigName);
                PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",postDangerOfEnterprise.getId());
            }
            if(dangerSmallName!=null){
                QueryWrapper<PostDangerOfEnterprise> qw=new QueryWrapper<>();
                qw.eq("dangerSmallName",dangerSmallName);
                PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",postDangerOfEnterprise.getId());
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
            informationAnalysisTable.setIdNum(touchPersonOfEnterpriseService.count(queryWrapper));
            //从业人数
            QueryWrapper<PersonOfEnterprise> queryWrapper1=new QueryWrapper<>();
            queryWrapper1.eq("year",year);
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
            if(dangerBigName!=null){
                QueryWrapper<PostDangerOfEnterprise> qw=new QueryWrapper<>();
                qw.eq("dangerBigName",dangerBigName);
                PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",postDangerOfEnterprise.getId());
            }
            if(dangerSmallName!=null){
                QueryWrapper<PostDangerOfEnterprise> qw=new QueryWrapper<>();
                qw.eq("dangerSmallName",dangerSmallName);
                PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",postDangerOfEnterprise.getId());
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
            informationAnalysisTable.setEngagedNum(personOfEnterpriseService.count(queryWrapper1));

            //接触粉尘危害人数
            QueryWrapper<PostDangerOfEnterprise> queryWrapper2=new QueryWrapper<>();
            queryWrapper2.eq("year",year).eq("dangerBigName","粉尘");
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
            if(dangerBigName!=null){
                QueryWrapper<PostDangerOfEnterprise> qw=new QueryWrapper<>();
                qw.eq("dangerBigName",dangerBigName);
                PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",postDangerOfEnterprise.getId());
            }
            if(dangerSmallName!=null){
                QueryWrapper<PostDangerOfEnterprise> qw=new QueryWrapper<>();
                qw.eq("dangerSmallName",dangerSmallName);
                PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",postDangerOfEnterprise.getId());
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
            informationAnalysisTable.setHazardsNumf(postDangerOfEnterpriseService.count(queryWrapper2));
            //接触化学因素危害人数
            QueryWrapper<PostDangerOfEnterprise> queryWrapper3=new QueryWrapper<>();
            queryWrapper3.eq("year",year).eq("dangerBigName","化学因素");
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
            if(dangerBigName!=null){
                QueryWrapper<PostDangerOfEnterprise> qw=new QueryWrapper<>();
                qw.eq("dangerBigName",dangerBigName);
                PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",postDangerOfEnterprise.getId());
            }
            if(dangerSmallName!=null){
                QueryWrapper<PostDangerOfEnterprise> qw=new QueryWrapper<>();
                qw.eq("dangerSmallName",dangerSmallName);
                PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",postDangerOfEnterprise.getId());
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
            informationAnalysisTable.setHazardsNumh(postDangerOfEnterpriseService.count(queryWrapper3));
            //接触物理因素危害人数
            QueryWrapper<PostDangerOfEnterprise> queryWrapper4=new QueryWrapper<>();
            queryWrapper4.eq("year",year).eq("dangerBigName","物理因素");
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
            if(dangerBigName!=null){
                QueryWrapper<PostDangerOfEnterprise> qw=new QueryWrapper<>();
                qw.eq("dangerBigName",dangerBigName);
                PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",postDangerOfEnterprise.getId());
            }
            if(dangerSmallName!=null){
                QueryWrapper<PostDangerOfEnterprise> qw=new QueryWrapper<>();
                qw.eq("dangerSmallName",dangerSmallName);
                PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",postDangerOfEnterprise.getId());
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
            informationAnalysisTable.setHazardsNumw(postDangerOfEnterpriseService.count(queryWrapper4));
            //接触放射性因素危害人数
            QueryWrapper<PostDangerOfEnterprise> queryWrapper5=new QueryWrapper<>();
            queryWrapper5.eq("year",year).eq("dangerBigName","放射性因素");
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
            if(dangerBigName!=null){
                QueryWrapper<PostDangerOfEnterprise> qw=new QueryWrapper<>();
                qw.eq("dangerBigName",dangerBigName);
                PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",postDangerOfEnterprise.getId());
            }
            if(dangerSmallName!=null){
                QueryWrapper<PostDangerOfEnterprise> qw=new QueryWrapper<>();
                qw.eq("dangerSmallName",dangerSmallName);
                PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",postDangerOfEnterprise.getId());
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
            informationAnalysisTable.setHazardsNumS(postDangerOfEnterpriseService.count(queryWrapper5));
            //接触生物因素危害人数
            QueryWrapper<PostDangerOfEnterprise> queryWrapper6=new QueryWrapper<>();
            queryWrapper6.eq("year",year).eq("dangerBigName","生物因素");
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
            if(dangerBigName!=null){
                QueryWrapper<PostDangerOfEnterprise> qw=new QueryWrapper<>();
                qw.eq("dangerBigName",dangerBigName);
                PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",postDangerOfEnterprise.getId());
            }
            if(dangerSmallName!=null){
                QueryWrapper<PostDangerOfEnterprise> qw=new QueryWrapper<>();
                qw.eq("dangerSmallName",dangerSmallName);
                PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getOne(qw);
                queryWrapper.eq("enterpriseId",postDangerOfEnterprise.getId());
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
            informationAnalysisTable.setHazardsNumsw(postDangerOfEnterpriseService.count(queryWrapper6));


            //接触职业病危害人数
            informationAnalysisTable.setHazardsNum(informationAnalysisTable.getHazardsNumf()+informationAnalysisTable.getHazardsNumh()+informationAnalysisTable.getHazardsNumS()+informationAnalysisTable.getHazardsNumsw()+informationAnalysisTable.getHazardsNumw());
            // 接害率
            informationAnalysisTable.setDamagerate1((float)informationAnalysisTable.getHazardsNum()/informationAnalysisTable.getIdNum());
            //接尘率
            informationAnalysisTable.setDamagerate2((float)informationAnalysisTable.getHazardsNumf()/informationAnalysisTable.getIdNum());
            //接毒率
            informationAnalysisTable.setDamagerate3((float)informationAnalysisTable.getHazardsNumh()/informationAnalysisTable.getIdNum());
            //接触物理因素危害率
            informationAnalysisTable.setDamagerate4((float)informationAnalysisTable.getHazardsNumw()/informationAnalysisTable.getIdNum());
            //接触放射性因素危害率
            informationAnalysisTable.setDamagerate5((float)informationAnalysisTable.getHazardsNumS()/informationAnalysisTable.getIdNum());
            //接触生物因素危害率
            informationAnalysisTable.setDamagerate6((float)informationAnalysisTable.getHazardsNumsw()/informationAnalysisTable.getIdNum());

            return informationAnalysisTable;
        }
        return null;
    }
}