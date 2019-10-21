package com.hthyaq.zybadmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.vo.EnterpriseUserView;
import com.hthyaq.zybadmin.service.*;
import org.apache.commons.codec.digest.DigestUtils;
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
    @Autowired
    SysRoleUserService sysRoleUserService;
    @PostMapping("/add")
    public boolean add(@RequestBody EnterpriseUserView enterpriseUserView) {
        System.out.println(enterpriseUserView);
        EnterpriseOfRegister enterpriseOfRegister = new EnterpriseOfRegister();

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",enterpriseUserView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            enterpriseOfRegister.setProvinceName(String.valueOf(areaOfDic.getName()));
            enterpriseOfRegister.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }

        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", enterpriseUserView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            enterpriseOfRegister.setCityName(String.valueOf(areaOfDic.getName()));
            enterpriseOfRegister.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (enterpriseUserView.getCascader().size() !=3) {
           QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("id", enterpriseUserView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                enterpriseOfRegister.setDistrictName(String.valueOf(areaOfDic.getName()));
                enterpriseOfRegister.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            enterpriseOfRegister.setDistrictName((String) enterpriseUserView.getCascader().get(2));
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", (String) enterpriseUserView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                enterpriseOfRegister.setProvinceName(String.valueOf(areaOfDic.getName()));
                enterpriseOfRegister.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }


        System.out.println(enterpriseOfRegister);
        enterpriseOfRegister.setName(enterpriseUserView.getCompanyName());
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
        sysUser.setLoginPassword( DigestUtils.md5Hex(enterpriseUserView.getLoginPassword()));

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
        sysUser.setCompanyName(enterpriseUserView.getCompanyName());
        sysUserService.save(sysUser);
        SysRoleUser sysRoleUser=new SysRoleUser();
        sysRoleUser.setRoleId(2);
        sysRoleUser.setUserId(Integer.parseInt(String.valueOf(sysUser.getId())));
        sysRoleUserService.save(sysRoleUser);


        return true;
    }
}
