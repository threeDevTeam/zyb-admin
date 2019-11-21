package com.hthyaq.zybadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping({"/","/visual/**","/user/**", "/*supervise", "/*Supervise", "/*Service", "/*enterprise", "/*Enterprise","/changePassword","/otherOfDic","/*Management", "/dataVisualSwitchFlag"})
    public String index() {
        System.out.println("index");
        return "index";
    }

}
