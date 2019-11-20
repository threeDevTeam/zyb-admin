package com.hthyaq.zybadmin.controller.dataVisual;

import com.hthyaq.zybadmin.common.utils.cache.DataVisualCacheUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categoryController")
public class CategoryController {
    //地区
    @GetMapping("/getAreaStrChildren")
    public List<String> getAreaStrChildren(String name1, String name2, String name3) {
        return DataVisualCacheUtil.getAreaStrChildren(name1, name2, name3);
    }

    @GetMapping("/getAreaReverseStrChildren")
    public List<String> getAreaReverseStrChildren(String name1, String name2, String name3) {
        return DataVisualCacheUtil.getAreaReverseStrChildren(name1, name2, name3);
    }

    //登记注册类型
    @GetMapping("/getRegisterTypeStrList")
    public List<String> getRegisterTypeStrList() {
        return DataVisualCacheUtil.getRegisterTypeStrList();
    }

    @GetMapping("/getRegisterTypeReverseStrList")
    public List<String> getRegisterTypeReverseStrList() {
        return DataVisualCacheUtil.getRegisterTypeReverseStrList();
    }

    //所属行业
    @GetMapping("/getIndustryStrList")
    public List<String> getIndustryStrList() {
        return DataVisualCacheUtil.getIndustryStrList();
    }

    @GetMapping("/getIndustryReverseStrList")
    public List<String> getIndustryReverseStrList() {
        return DataVisualCacheUtil.getIndustryReverseStrList();
    }
}
