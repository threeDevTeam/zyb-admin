package com.hthyaq.zybadmin.controller.demo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.bean.Child;
import com.hthyaq.zybadmin.model.bean.GlobalResult;
import com.hthyaq.zybadmin.model.entity.Demo;
import com.hthyaq.zybadmin.model.entity.DemoCourse;
import com.hthyaq.zybadmin.model.vo.DemoView;
import com.hthyaq.zybadmin.service.DemoCourseService;
import com.hthyaq.zybadmin.service.DemoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-10
 */
@RestController
@Controller
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    DemoService demoService;
    @Autowired
    DemoCourseService demoCourseService;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/test")
    public GlobalResult test(){
        ResponseEntity<String> response = restTemplate.getForEntity("https://www.cnblogs.com/harrychinese/p/springboot_resttemplate.html", String.class);
        return GlobalResult.success("msg",response.getBody());
    }

    @PostMapping("/add")
    public boolean add(@RequestBody DemoView demoView) {
        return demoService.saveData(demoView);
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return demoService.deleteData(id);
    }

    @GetMapping("/getById")
    public DemoView getById(Integer id) {
        DemoView demoView = new DemoView();

        //demo
        Demo demo = demoService.getById(id);
        //将demo的数据设置到demoData
        BeanUtils.copyProperties(demo, demoView);

        //demoCourse
        QueryWrapper<DemoCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("demo_id", id);
        List<DemoCourse> demoCourseList = demoCourseService.list(queryWrapper);

        //将demoCourse的数据设置到demoData
        demoView.setCourse(new Child<>(demoCourseList));
        return demoView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody DemoView demoView) {
        return demoService.editData(demoView);
    }


    @GetMapping("/list")
    public IPage<Demo> list(String json) {
        System.out.println(redisTemplate);
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);jsonObject.getObject("zbry",String.class);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String username = jsonObject.getString("username");
        QueryWrapper<Demo> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(username)) {
            queryWrapper.eq("username", username);
        }

        IPage<Demo> page = demoService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
