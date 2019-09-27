package com.hthyaq.zybadmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.AreaOfDic;
import com.hthyaq.zybadmin.model.entity.ServiceOfRegister;
import com.hthyaq.zybadmin.model.entity.SuperviseOfRegister;
import com.hthyaq.zybadmin.model.entity.SysUser;
import com.hthyaq.zybadmin.model.vo.SuperviseOfUserView;
import com.hthyaq.zybadmin.service.AreaOfDicService;
import com.hthyaq.zybadmin.service.ServiceOfRegisterService;
import com.hthyaq.zybadmin.service.SuperviseOfRegisterService;
import com.hthyaq.zybadmin.service.SysUserService;
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

    @PostMapping("/add")
    public boolean add(@RequestBody SuperviseOfUserView superviseOfUserView) {
        System.out.println(superviseOfUserView);
        SuperviseOfRegister superviseOfRegister=new SuperviseOfRegister();

        superviseOfRegister.setProvinceName((String) superviseOfUserView.getCascader().get(0));

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", (String) superviseOfUserView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            superviseOfRegister.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }

        superviseOfRegister.setCityName((String) superviseOfUserView.getCascader().get(1));

        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", (String) superviseOfUserView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            superviseOfRegister.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (superviseOfUserView.getCascader().size() !=3) {
            superviseOfRegister.setDistrictName((String) superviseOfUserView.getCascader().get(1));
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("name", (String) superviseOfUserView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                superviseOfRegister.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            superviseOfRegister.setDistrictName((String) superviseOfUserView.getCascader().get(2));
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("name", (String) superviseOfUserView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                superviseOfRegister.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }



        superviseOfRegister.setRegisterAddress(superviseOfUserView.getRegisterAddress());
        superviseOfRegister.setName(superviseOfUserView.getName());
        superviseOfRegister.setEmail(superviseOfUserView.getEmail());
        superviseOfRegister.setMobile(superviseOfUserView.getMobile());
        superviseOfRegisterService.save(superviseOfRegister);
        SysUser sysUser=new SysUser();
        sysUser.setLoginName(superviseOfUserView.getLoginName());
        sysUser.setLoginPassword(superviseOfUserView.getLoginPassword());

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
        sysUserService.save(sysUser);
        return true;
    }
}
