package com.hthyaq.zybadmin.controller;


import com.hthyaq.zybadmin.model.entity.Herun;
import com.hthyaq.zybadmin.service.HerunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2020-05-15
 */
@RestController
@RequestMapping("/herun")
public class HerunController {
    @Autowired
    public HerunService herunService;

    @GetMapping("/get")
    public boolean get(String data) {
        Herun herun=new Herun();
        herun.setContent(data);
       return herunService.save(herun);
    }
}
