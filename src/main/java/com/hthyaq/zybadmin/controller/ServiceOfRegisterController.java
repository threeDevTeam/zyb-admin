package com.hthyaq.zybadmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.vo.ServiceOfUserView;
import com.hthyaq.zybadmin.service.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-12
 */
@RestController
@RequestMapping("/serviceOfRegister")
public class ServiceOfRegisterController {
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    AreaOfDicService areaOfDicService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    SysRoleUserService sysRoleUserService;
    @PostMapping("/add")
    public boolean add(@RequestBody ServiceOfUserView serviceOfUserView) {
        System.out.println(serviceOfUserView);

        ServiceOfRegister serviceOfRegister=new ServiceOfRegister();
        serviceOfRegister.setName(serviceOfUserView.getCompanyName());
        serviceOfRegister.setCode(serviceOfUserView.getCode());


     QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", serviceOfUserView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            serviceOfRegister.setProvinceName(String.valueOf(areaOfDic.getName()));
            serviceOfRegister.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }

    QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id",serviceOfUserView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            serviceOfRegister.setCityName(String.valueOf(areaOfDic.getName()));
            serviceOfRegister.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (serviceOfUserView.getCascader().size() !=3) {
           QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("id",serviceOfUserView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                serviceOfRegister.setDistrictName(String.valueOf(areaOfDic.getName()));
                serviceOfRegister.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", serviceOfUserView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                serviceOfRegister.setDistrictName(String.valueOf(areaOfDic.getName()));
                serviceOfRegister.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }


        serviceOfRegister.setRegisterAddress(serviceOfUserView.getRegisterAddress());
        serviceOfRegister.setType(serviceOfUserView.getType2());
        serviceOfRegister.setEmail(serviceOfUserView.getEmail());
        serviceOfRegister.setMobile(serviceOfUserView.getMobile());
        serviceOfRegisterService.save(serviceOfRegister);
        SysUser sysUser=new SysUser();
        sysUser.setLoginName(serviceOfUserView.getLoginName());
        sysUser.setLoginPassword( DigestUtils.md5Hex(serviceOfUserView.getLoginPassword()));

        sysUser.setEmail(serviceOfUserView.getEmail());

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("18256542305@163.com");
        simpleMailMessage.setTo(serviceOfUserView.getEmail());
        simpleMailMessage.setSubject("账户信息");
        simpleMailMessage.setText("用户名:"+serviceOfUserView.getLoginName()+"  "+"密码:"+serviceOfUserView.getLoginPassword());
        try {
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sysUser.setMobile(serviceOfUserView.getMobile());
        sysUser.setType(serviceOfUserView.getType()+"-"+serviceOfUserView.getType2());
        sysUser.setCompanyId(serviceOfRegister.getId());
        sysUser.setCompanyName(serviceOfUserView.getCompanyName());
        sysUserService.save(sysUser);
        if(serviceOfUserView.getType2().equals("检测机构")){
            SysRoleUser sysRoleUser=new SysRoleUser();
            sysRoleUser.setRoleId(4);
            sysRoleUser.setUserId(Integer.parseInt(String.valueOf(sysUser.getId())));
            sysRoleUserService.save(sysRoleUser);
        }
        else if(serviceOfUserView.getType2().equals("体检机构")){
            SysRoleUser sysRoleUser=new SysRoleUser();
            sysRoleUser.setRoleId(5);
            sysRoleUser.setUserId(Integer.parseInt(String.valueOf(sysUser.getId())));
            sysRoleUserService.save(sysRoleUser);
        }
        else if(serviceOfUserView.getType2().equals("诊断机构")){
            SysRoleUser sysRoleUser=new SysRoleUser();
            sysRoleUser.setRoleId(6);
            sysRoleUser.setUserId(Integer.parseInt(String.valueOf(sysUser.getId())));
            sysRoleUserService.save(sysRoleUser);
        }
        return true;
    }
}
