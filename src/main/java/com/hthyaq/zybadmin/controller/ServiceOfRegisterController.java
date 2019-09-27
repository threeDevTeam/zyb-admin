package com.hthyaq.zybadmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.AreaOfDic;
import com.hthyaq.zybadmin.model.entity.EnterpriseOfRegister;
import com.hthyaq.zybadmin.model.entity.ServiceOfRegister;
import com.hthyaq.zybadmin.model.entity.SysUser;
import com.hthyaq.zybadmin.model.vo.ServiceOfUserView;
import com.hthyaq.zybadmin.service.AreaOfDicService;
import com.hthyaq.zybadmin.service.EnterpriseOfRegisterService;
import com.hthyaq.zybadmin.service.ServiceOfRegisterService;
import com.hthyaq.zybadmin.service.SysUserService;
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
    @PostMapping("/add")
    public boolean add(@RequestBody ServiceOfUserView serviceOfUserView) {
        System.out.println(serviceOfUserView);

        ServiceOfRegister serviceOfRegister=new ServiceOfRegister();
        serviceOfRegister.setName(serviceOfUserView.getName());
        serviceOfRegister.setCode(serviceOfUserView.getCode());


        serviceOfRegister.setProvinceName((String) serviceOfUserView.getCascader().get(0));

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", (String) serviceOfUserView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            serviceOfRegister.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }

        serviceOfRegister.setCityName((String) serviceOfUserView.getCascader().get(1));

        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", (String) serviceOfUserView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            serviceOfRegister.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (serviceOfUserView.getCascader().size() !=3) {
            serviceOfRegister.setDistrictName((String) serviceOfUserView.getCascader().get(1));
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("name", (String) serviceOfUserView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                serviceOfRegister.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            serviceOfRegister.setDistrictName((String) serviceOfUserView.getCascader().get(2));
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("name", (String) serviceOfUserView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
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
        sysUser.setLoginPassword(serviceOfUserView.getLoginPassword());
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
        sysUserService.save(sysUser);
        return true;
    }
}
