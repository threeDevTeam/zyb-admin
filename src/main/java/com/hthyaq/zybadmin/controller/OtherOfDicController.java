package com.hthyaq.zybadmin.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.service.OtherOfDicService;
import com.hthyaq.zybadmin.service.TableMapInfoQiyeService;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 用于企业、监管部门、技术服务机构的表单下拉项 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-18
 */
@RestController
@RequestMapping("/otherOfDic")
public class OtherOfDicController {
    @Autowired
    OtherOfDicService otherOfDicService;

//    @GetMapping("/add")
//    public void add() {
//        ExcelReader reader = ExcelUtil.getReader("D:/OtherOfDic.xlsx");
//        List<OtherOfDic> all = reader.readAll(OtherOfDic.class);
//        System.out.println(all);
//        for(OtherOfDic otherOfDic: all) {
//            System.out.println(otherOfDic);
//            otherOfDicService.save(otherOfDic);
//        }
//    }
    @PostMapping("/add")
    public boolean add(@RequestBody  OtherOfDic  otherOfDic) {
        return otherOfDicService.save(otherOfDic);
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return otherOfDicService.removeById(id);
    }


    @GetMapping("/getById")
    public OtherOfDic getById(Integer id) {

        OtherOfDic otherOfDic = otherOfDicService.getById(id);
        //demoCourse
        QueryWrapper<OtherOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<OtherOfDic> demoCourseList = otherOfDicService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return otherOfDic;
    }


    @PostMapping("/edit")
    public boolean edit(@RequestBody OtherOfDic otherOfDic) {
        return otherOfDicService.updateById(otherOfDic);
    }

    @GetMapping("/list")
    public IPage<OtherOfDic> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String chineseColumnName = jsonObject.getString("chineseColumnName");

        QueryWrapper<OtherOfDic> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(chineseColumnName)) {
            queryWrapper.like("chineseColumnName", chineseColumnName);
        }


        IPage<OtherOfDic> page = otherOfDicService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
}
