package com.hthyaq.zybadmin;

import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.bean.GlobalResult;
import com.hthyaq.zybadmin.model.entity.SysUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SessionDemo {

    //设置session
    @GetMapping("/user/login")
    public GlobalResult set(HttpSession httpSession) {
        SysUser sysUser=new SysUser();
        sysUser.setLoginName("uname");
        sysUser.setLoginPassword("pwd");
        httpSession.setAttribute(GlobalConstants.LOGIN_NAME, sysUser);
        return GlobalResult.success("设置session", httpSession.getId());
    }

    //获取session
    @GetMapping("/get")
    public GlobalResult get(HttpSession httpSession) {
//        String data = httpSession.getId() + "," + httpSession.getAttribute("a");
        return GlobalResult.success("获取session", httpSession.getAttribute(GlobalConstants.LOGIN_NAME));
    }
}
