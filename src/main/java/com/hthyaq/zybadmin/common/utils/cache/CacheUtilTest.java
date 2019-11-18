package com.hthyaq.zybadmin.common.utils.cache;

import com.hthyaq.zybadmin.model.entity.AreaOfDic;
import com.hthyaq.zybadmin.model.entity.BaseOfDic;
import com.hthyaq.zybadmin.model.entity.IndustryOfDic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cacheUtilTest")
public class CacheUtilTest {
    @GetMapping("/getAreaSelf")
    public AreaOfDic getAreaSelf(String name1, String name2, String name3) {
        return DataVisualCacheUtil.getAreaSelf(name1, name2, name3);
    }

    @GetMapping("/getAreaChildren")
    public List<AreaOfDic> getAreaChildren(String name1, String name2, String name3) {
        return DataVisualCacheUtil.getAreaChildren(name1, name2, name3);
    }

    @GetMapping("/getAreaStrChildren")
    public List<String> getAreaStrChildren(String name1, String name2, String name3) {
        return DataVisualCacheUtil.getAreaStrChildren(name1, name2, name3);
    }

    @GetMapping("/getAreaReverseStrChildren")
    public List<String> getAreaReverseStrChildren(String name1, String name2, String name3) {
        return DataVisualCacheUtil.getAreaReverseStrChildren(name1, name2, name3);
    }

    @GetMapping("/getRegisterTypeList")
    public List<BaseOfDic> getRegisterTypeList() {
        return DataVisualCacheUtil.getRegisterTypeList();
    }

    @GetMapping("/getRegisterTypeStrList")
    public List<String> getRegisterTypeStrList() {
        return DataVisualCacheUtil.getRegisterTypeStrList();
    }

    @GetMapping("/getIndustryList")
    public List<IndustryOfDic> getIndustryList() {
        return DataVisualCacheUtil.getIndustryList();
    }

    @GetMapping("/getIndustryStrList")
    public List<String> getIndustryStrList() {
        return DataVisualCacheUtil.getIndustryStrList();
    }

    @GetMapping("/getDangerList")
    public List<String> getDangerList() {
        return DataVisualCacheUtil.getDangerList();
    }

    @GetMapping("/getEnterpriseSize")
    public List<String> getEnterpriseSize() {
        return DataVisualCacheUtil.getEnterpriseSize();
    }
}
