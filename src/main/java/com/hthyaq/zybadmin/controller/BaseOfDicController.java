package com.hthyaq.zybadmin.controller;


import com.hthyaq.zybadmin.common.excle.MyExcelUtil;
import com.hthyaq.zybadmin.model.entity.BaseOfDic;
import com.hthyaq.zybadmin.model.excelModel.BaseOfDicModel;
import com.hthyaq.zybadmin.service.BaseOfDicService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 统计时的基础信息表 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/baseOfDic")
public class BaseOfDicController {
    @Autowired
    BaseOfDicService baseOfDicService;

    @PostMapping("/oneSheetExcel")
    public Boolean oneSheetExcel(MultipartFile[] files) {
        //excel->model
        List<Object> modelList = MyExcelUtil.readOneSheetExcel(files, BaseOfDicModel.class);
        //model->entity
        List<BaseOfDic> dataList = getDataList(modelList, "危害因素");
        return baseOfDicService.saveBatch(dataList);
    }

    @PostMapping("/moreSheetExcel")
    public Boolean moreSheetExcel(MultipartFile[] files) {
        boolean flag = true;
        //excel->model
        Map<String, List<Object>> modelMap = (Map<String, List<Object>>) MyExcelUtil.readOneSheetExcel(files, BaseOfDicModel.class);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<BaseOfDic> dataList = getDataList(modelList, type);
            flag = baseOfDicService.saveBatch(dataList);
        }
        return flag;
    }

    private List<BaseOfDic> getDataList(List<Object> modelList, String type) {
        List<BaseOfDic> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            BaseOfDicModel baseOfDicModel = (BaseOfDicModel) object;
            BaseOfDic baseOfDic = new BaseOfDic();
            BeanUtils.copyProperties(baseOfDicModel, baseOfDic);
            //设置flag
            baseOfDic.setFlag(type);
            dataList.add(baseOfDic);
        }
        return dataList;
    }
}
