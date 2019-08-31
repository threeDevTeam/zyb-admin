package com.hthyaq.zybadmin;

import com.hthyaq.zybadmin.model.bean.GlobalResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SessionDemo {

    //设置session
    @GetMapping("/set")
    public GlobalResult set(HttpSession httpSession) {
        httpSession.setAttribute("a", "a");
        return GlobalResult.success("设置session", httpSession.getId());
    }

    //获取session
    @GetMapping("/get")
    public GlobalResult get(HttpSession httpSession) {
        String data = httpSession.getId() + "," + httpSession.getAttribute("a");
        return GlobalResult.success("获取session", data);
    }
}
