package com.hthyaq.zybadmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.AccidentOfSupervise;
import com.hthyaq.zybadmin.model.entity.AreaOfDic;
import com.hthyaq.zybadmin.model.entity.EnterpriseOfRegister;
import com.hthyaq.zybadmin.model.entity.SysUser;
import com.hthyaq.zybadmin.model.vo.EnterpriseUserView;
import com.hthyaq.zybadmin.service.AccidentOfSuperviseService;
import com.hthyaq.zybadmin.service.AreaOfDicService;
import com.hthyaq.zybadmin.service.EnterpriseOfRegisterService;
import com.hthyaq.zybadmin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * <p>
 * 企业的注册表 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-12
 */
@RestController
@RequestMapping("/enterpriseOfRegister")
public class EnterpriseOfRegisterController {
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    AreaOfDicService areaOfDicService;
    @Autowired
    private JavaMailSender javaMailSender;
    @PostMapping("/add")
    public boolean add(@RequestBody EnterpriseUserView enterpriseUserView) {
        System.out.println(enterpriseUserView);
        EnterpriseOfRegister enterpriseOfRegister = new EnterpriseOfRegister();
        enterpriseOfRegister.setProvinceName((String) enterpriseUserView.getCascader().get(0));

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", (String) enterpriseUserView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            enterpriseOfRegister.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }

        enterpriseOfRegister.setCityName((String) enterpriseUserView.getCascader().get(1));

        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", (String) enterpriseUserView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            enterpriseOfRegister.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (enterpriseUserView.getCascader().size() !=3) {
            enterpriseOfRegister.setDistrictName((String) enterpriseUserView.getCascader().get(1));
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("name", (String) enterpriseUserView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                enterpriseOfRegister.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            enterpriseOfRegister.setDistrictName((String) enterpriseUserView.getCascader().get(2));
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("name", (String) enterpriseUserView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                enterpriseOfRegister.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }


        System.out.println(enterpriseOfRegister);
        enterpriseOfRegister.setName(enterpriseUserView.getName());
        enterpriseOfRegister.setCode(enterpriseUserView.getCode());
        enterpriseOfRegister.setProductionCapacity(enterpriseUserView.getProductionCapacity());
        enterpriseOfRegister.setUnitType(enterpriseUserView.getUnitType());
        enterpriseOfRegister.setRegiterMoney(enterpriseUserView.getRegiterMoney());
        enterpriseOfRegister.setRegisterAddress(enterpriseUserView.getRegisterAddress());
        enterpriseOfRegister.setRegisterDate(enterpriseUserView.getRegisterDate());
        enterpriseOfRegister.setStartDate(enterpriseUserView.getStartDate());
        enterpriseOfRegister.setPropertyMoney(enterpriseUserView.getPropertyMoney());
        enterpriseOfRegister.setEmail(enterpriseUserView.getEmail());
        enterpriseOfRegister.setMobile(enterpriseUserView.getMobile());
        enterpriseOfRegisterService.save(enterpriseOfRegister);
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(enterpriseUserView.getLoginName());
        sysUser.setLoginPassword(enterpriseUserView.getLoginPassword());


        sysUser.setEmail(enterpriseUserView.getEmail());
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("18256542305@163.com");
        simpleMailMessage.setTo(enterpriseUserView.getEmail());
        simpleMailMessage.setSubject("账户信息");
        simpleMailMessage.setText("用户名:"+enterpriseUserView.getLoginName()+"  "+"密码:"+enterpriseUserView.getLoginPassword());
        try {
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }



        sysUser.setMobile(enterpriseUserView.getMobile());
        sysUser.setType(enterpriseUserView.getType());
        sysUser.setCompanyId(enterpriseOfRegister.getId());
        sysUserService.save(sysUser);
        return true;
    }
}
