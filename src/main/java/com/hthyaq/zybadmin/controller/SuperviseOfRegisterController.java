package com.hthyaq.zybadmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.vo.SuperviseOfUserView;
import com.hthyaq.zybadmin.service.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 监管部门信息的注册表 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-12
 */
@RestController
@RequestMapping("/superviseOfRegister")
public class SuperviseOfRegisterController {
    @Autowired
    SuperviseOfRegisterService superviseOfRegisterService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    AreaOfDicService areaOfDicService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    SysRoleUserService sysRoleUserService;
    @PostMapping("/add")
    public boolean add(@RequestBody SuperviseOfUserView superviseOfUserView) {
        System.out.println(superviseOfUserView);
        SuperviseOfRegister superviseOfRegister=new SuperviseOfRegister();

     QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",superviseOfUserView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            superviseOfRegister.setProvinceName(String.valueOf(areaOfDic.getName()));
            superviseOfRegister.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }
        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", superviseOfUserView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            superviseOfRegister.setCityName(String.valueOf(areaOfDic.getName()));

            superviseOfRegister.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (superviseOfUserView.getCascader().size() !=3) {
          QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("id", superviseOfUserView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                superviseOfRegister.setDistrictName(String.valueOf(areaOfDic.getName()));
                superviseOfRegister.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
           QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", superviseOfUserView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                superviseOfRegister.setDistrictName(String.valueOf(areaOfDic.getName()));
                superviseOfRegister.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }



        superviseOfRegister.setRegisterAddress(superviseOfUserView.getRegisterAddress());
        superviseOfRegister.setName(superviseOfUserView.getCompanyName());
        superviseOfRegister.setEmail(superviseOfUserView.getEmail());
        superviseOfRegister.setMobile(superviseOfUserView.getMobile());
        superviseOfRegisterService.save(superviseOfRegister);
        SysUser sysUser=new SysUser();
        sysUser.setLoginName(superviseOfUserView.getLoginName());
        sysUser.setLoginPassword( DigestUtils.md5Hex(superviseOfUserView.getLoginPassword()));


        sysUser.setEmail(superviseOfUserView.getEmail());

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("18256542305@163.com");
        simpleMailMessage.setTo(superviseOfUserView.getEmail());
        simpleMailMessage.setSubject("账户信息");
        simpleMailMessage.setText("用户名:"+superviseOfUserView.getLoginName()+"  "+"密码:"+superviseOfUserView.getLoginPassword());
        try {
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sysUser.setMobile(superviseOfUserView.getMobile());
        sysUser.setType(superviseOfUserView.getType());
        sysUser.setCompanyId(superviseOfRegister.getId());
        sysUser.setCompanyName(superviseOfUserView.getCompanyName());
        try {
            sysUserService.save(sysUser);
        } catch (Exception e) {
            throw new RuntimeException("公司名称已存在");
        }
        SysRoleUser sysRoleUser=new SysRoleUser();
        sysRoleUser.setRoleId(3);
        sysRoleUser.setUserId(Integer.parseInt(String.valueOf(sysUser.getId())));
        sysRoleUserService.save(sysRoleUser);
        return true;
    }
}
