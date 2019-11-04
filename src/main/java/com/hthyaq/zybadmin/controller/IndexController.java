package com.hthyaq.zybadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping({"/user/**", "/visual/**", "/*supervise", "/*Supervise", "/*Service", "/*enterprise", "/*Enterprise", "/Login/**", "/Systemsetup/**"})
    public String index() {
        return "index";
    }

}
