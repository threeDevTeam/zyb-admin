package com.hthyaq.zybadmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hthyaq.zybadmin.model.entity.CheckOfEnterprise;
import com.hthyaq.zybadmin.model.entity.DataVisualSwitchFlag;
import com.hthyaq.zybadmin.model.entity.SysUser;
import com.hthyaq.zybadmin.model.vo.DataVisualSwitchFlagView;
import com.hthyaq.zybadmin.service.DataVisualSwitchFlagService;
import com.hthyaq.zybadmin.service.SysRoleUserService;
import com.sun.mail.imap.protocol.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 数据可视化的真假数据的开关 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-11-01
 */
@RestController
@RequestMapping("/dataVisualSwitchFlag")
public class DataVisualSwitchFlagController {
    @Autowired
    DataVisualSwitchFlagService dataVisualSwitchFlagService;
    @PostMapping("/up")
    public boolean up( @RequestBody DataVisualSwitchFlag dataVisualSwitchFlag) {
        boolean flag=false;

        dataVisualSwitchFlag.setId(1);
        flag=dataVisualSwitchFlagService.updateById(dataVisualSwitchFlag);
        return flag;
    }
    @GetMapping("/list")
    public DataVisualSwitchFlag list() {
        QueryWrapper<DataVisualSwitchFlag> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",1);
        DataVisualSwitchFlag dataVisualSwitchFlag = dataVisualSwitchFlagService.getOne(queryWrapper);
        return dataVisualSwitchFlag;
    }
}
