package com.hthyaq.zybadmin.controller;

import org.springframework.stereotype.Controller;

@Controller
public class IndexController {
//    @GetMapping({"/","/visual/**","/user/**", "/*supervise", "/*Supervise", "/*Service", "/*enterprise", "/*Enterprise","/*Management"})
    public String index() {
        System.out.println("index");
        return "index";
    }

}
