package com.hthyaq.zybadmin.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.hthyaq.zybadmin.model.entity.AreaOfDic;
import com.hthyaq.zybadmin.model.entity.IndustryOfDic;
import com.hthyaq.zybadmin.service.AreaOfDicService;
import com.hthyaq.zybadmin.service.BaseOfDicService;
import com.hthyaq.zybadmin.service.IndustryOfDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VisualUtil {
    @Autowired
    AreaOfDicService areaOfDicService;
    @Autowired
    IndustryOfDicService industryOfDicService;
    @Autowired
    BaseOfDicService baseOfDicService;

    //areaNames=nation、安徽、安徽,合肥市、安徽省,合肥市,庐江县
    public List<AreaOfDic> getAreaList(String areaNames) {
        if ("nation".equals(areaNames)) {
            List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1).notIn("name", "澳门", "台湾"));
            return areaList;
        } else {
            List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1).notIn("name", "澳门", "台湾"));
            return areaList;
        }
    }

    public List<String> getAreaStrList(String areaNames) {
        List<String> list = Lists.newArrayList();
        if ("nation".equals(areaNames)) {
            List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1).notIn("name", "澳门", "台湾"));
            areaList.forEach(tmp -> list.add(tmp.getName()));
            return list;
        } else {
            List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1).notIn("name", "澳门", "台湾"));
            areaList.forEach(tmp -> list.add(tmp.getName()));
            return list;
        }
    }

    //登记注册类型
    public List<com.hthyaq.zybadmin.model.entity.BaseOfDic> getRegisterTypeList() {
        List<com.hthyaq.zybadmin.model.entity.BaseOfDic> registerList = baseOfDicService.list(new QueryWrapper<com.hthyaq.zybadmin.model.entity.BaseOfDic>().eq("flag", "登记注册类型"));
        return registerList;
    }

    public List<String> getRegisterTypeStrList() {
        List<String> list = Lists.newArrayList();
        List<com.hthyaq.zybadmin.model.entity.BaseOfDic> registerList = baseOfDicService.list(new QueryWrapper<com.hthyaq.zybadmin.model.entity.BaseOfDic>().eq("flag", "登记注册类型"));
        registerList.forEach(tmp -> list.add(tmp.getBigName()));
        return list;
    }

    //行业
    public List<IndustryOfDic> getIndustryList() {
        List<IndustryOfDic> industryList = industryOfDicService.list(new QueryWrapper<IndustryOfDic>().eq("topid", -1));
        return industryList;
    }

    public List<String> getIndustryStrList() {
        List<String> list = Lists.newArrayList();
        List<IndustryOfDic> industryList = industryOfDicService.list(new QueryWrapper<IndustryOfDic>().eq("topid", -1));
        industryList.forEach(tmp -> list.add(tmp.getName()));
        return list;
    }

    //危害因素
    public List<String> getDangerList() {
        List<String> list = Lists.newArrayList("粉尘", "化学因素", "物理因素", "放射性因素", "生物因素");
        return list;
    }

    //企业规模
    public List<String> getEnterpriseSize() {
        List<String> list = Lists.newArrayList("微型", "小型", "中型", "大型");
        return list;
    }
}
