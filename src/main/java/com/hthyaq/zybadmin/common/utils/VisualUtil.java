package com.hthyaq.zybadmin.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    public List<com.hthyaq.zybadmin.model.entity.BaseOfDic> getRegisterTypeList() {
        List<com.hthyaq.zybadmin.model.entity.BaseOfDic> registerList = baseOfDicService.list(new QueryWrapper<com.hthyaq.zybadmin.model.entity.BaseOfDic>().eq("flag", "登记注册类型"));
        return registerList;
    }

    //行业
    public List<IndustryOfDic> getIndustryList() {
        List<IndustryOfDic> industryList = industryOfDicService.list(new QueryWrapper<IndustryOfDic>().eq("topid", -1));
        return industryList;
    }
}
