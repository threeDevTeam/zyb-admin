package com.hthyaq.zybadmin.controller;


import com.google.common.collect.Maps;
import com.hthyaq.zybadmin.common.excle.MyExcelUtil;
import com.hthyaq.zybadmin.model.entity.IndustryOfDic;
import com.hthyaq.zybadmin.model.excelModel.IndustryOfDicModel;
import com.hthyaq.zybadmin.service.IndustryOfDicService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 行业表 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/industryOfDic")
public class IndustryOfDicController {
    @Autowired
    private IndustryOfDicService industryOfDicService;

    private void compute() {
        /*
            临时存储的map，存放以下数据
                         key    value
             level=1      1    该对象的id
             level=2      2    该对象的id
         */
        Map<Integer, Integer> map = Maps.newHashMap();
        //1.取出数据，pid、topId没有值
        List<IndustryOfDic> list = industryOfDicService.list();
        for (IndustryOfDic industryOfDic : list) {
            double tmp = industryOfDic.getSort();
            int level = (int) tmp;
            int id = industryOfDic.getId();
            //2.给pid、topId设置值
            if (level == 1) {
                //清空map
                map.clear();
                //存储
                map.put(1, id);
            }
            if (level == 2) {
                map.put(2, id);
                //取出level=1
                int pid = map.get(1);
                int topid = map.get(1);
                //设置
                industryOfDic.setPid(pid);
                industryOfDic.setTopid(topid);
            }
            if (level == 3) {
                int pid = map.get(2);
                int topid = map.get(1);
                //设置
                industryOfDic.setPid(pid);
                industryOfDic.setTopid(topid);
            }
        }
        //3.将pid、topId有值更新到db
        industryOfDicService.updateBatchById(list);
    }


    @PostMapping("/moreSheetExcel")
    public Boolean moreSheetExcel(MultipartFile[] files) {
        boolean flag = true;
        //excel->model
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files, IndustryOfDicModel.class);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<IndustryOfDic> dataList = getDataList(modelList, type);
            flag = industryOfDicService.saveBatch(dataList);
        }
        //设置pid、topid
        this.compute();
        return flag;
    }

    private List<IndustryOfDic> getDataList(List<Object> modelList, String type) {
        List<IndustryOfDic> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            IndustryOfDicModel industryOfDicModel = (IndustryOfDicModel) object;
            String level = industryOfDicModel.getLevel();
            String name = industryOfDicModel.getTypeName2();
            //业务处理
            IndustryOfDic industryOfDic = new IndustryOfDic();
            industryOfDic.setName(name);
            industryOfDic.setSort(Double.parseDouble(level));
            dataList.add(industryOfDic);
        }
        return dataList;
    }
}
