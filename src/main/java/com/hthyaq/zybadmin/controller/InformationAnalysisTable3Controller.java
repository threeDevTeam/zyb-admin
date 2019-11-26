package com.hthyaq.zybadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/informationAnalysisTable3")
public class InformationAnalysisTable3Controller {
    @Autowired
    PostOfEnterpriseService postOfEnterpriseService;
    @Autowired
    ProtectOfEnterpriseService protectOfEnterpriseService;
    @Autowired
    PersonProtectOfEnterpriseService personProtectOfEnterpriseService;
    @Autowired
    TouchPersonOfEnterpriseService touchPersonOfEnterpriseService;
    @Autowired
    PersonOfEnterpriseService personOfEnterpriseService;
    @Autowired
    PostDangerOfEnterpriseService postDangerOfEnterpriseService;
    @Autowired
    EnterpriseService enterpriseService;
    public InformationAnalysisTable3 list(Integer year) {
        if (year != null) {
            InformationAnalysisTable3 informationAnalysisTable3=new InformationAnalysisTable3();
          //存在职业病危害岗位数
            QueryWrapper<PostOfEnterprise> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("year",year);
            informationAnalysisTable3.setNameNum1(postOfEnterpriseService.count(queryWrapper));
            //设置职业病防护设施岗位数
            QueryWrapper<ProtectOfEnterprise> queryWrapper1=new QueryWrapper<>();
            queryWrapper1.eq("year",year).eq("isSet","是");
            informationAnalysisTable3.setNameNum2(protectOfEnterpriseService.count(queryWrapper1));
            //职业病防护设施设置率
            informationAnalysisTable3.setNameNum2L((float)informationAnalysisTable3.getNameNum2()/informationAnalysisTable3.getNameNum1());
            //配备个体防护用品岗位数
            QueryWrapper<PersonProtectOfEnterprise> queryWrapper2=new QueryWrapper<>();
            queryWrapper2.eq("year",year).eq("isSet","是");
            informationAnalysisTable3.setNameNum3(personProtectOfEnterpriseService.count(queryWrapper2));
            //个人防护用品配备率
            informationAnalysisTable3.setNameNum3L((float)informationAnalysisTable3.getNameNum3()/informationAnalysisTable3.getNameNum1());
        return informationAnalysisTable3;
        }
        return null;
    }
}