package com.hthyaq.zybadmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.hthyaq.zybadmin.model.entity.AreaCopy;
import com.hthyaq.zybadmin.model.entity.TreeSelcetData;
import com.hthyaq.zybadmin.service.AreaCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 省市县三级的行政区域数据 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-04
 */
@RestController
@RequestMapping("/areaCopy")

public class AreaCopyController {
    @Autowired
    AreaCopyService areaCopyService;

    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetData> TreeSelcetData() {
        List<TreeSelcetData> treeSelcetDatalist = new ArrayList();

        QueryWrapper<AreaCopy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", "-1");
        List<AreaCopy> list = areaCopyService.list(queryWrapper);
        for (AreaCopy areaCopy : list) {
            List<TreeSelcetData> chilren = Lists.newArrayList();
            TreeSelcetData treeSelcetData = new TreeSelcetData();
            treeSelcetData.setLabel(areaCopy.getName());
            treeSelcetData.setValue(areaCopy.getName());
            treeSelcetData.setChildren(chilren);
            treeSelcetDatalist.add(treeSelcetData);

            QueryWrapper<AreaCopy> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("pid", areaCopy.getId());
            List<AreaCopy> list1 = areaCopyService.list(queryWrapper1);

            for (AreaCopy areaCopy1 : list1) {
                TreeSelcetData treeSelcetData1 = new TreeSelcetData();
                treeSelcetData1.setLabel(areaCopy1.getName());
                treeSelcetData1.setValue(areaCopy1.getName());
                chilren.add(treeSelcetData1);
                List<TreeSelcetData> chilren2= Lists.newArrayList();
                QueryWrapper<AreaCopy> queryWrapper2=new QueryWrapper<>();
                queryWrapper2.eq("pid",areaCopy1.getId());
                List<AreaCopy> list2 = areaCopyService.list(queryWrapper2);

                for (AreaCopy areaCopy2 : list2) {
                    TreeSelcetData treeSelcetData2=new TreeSelcetData();
                    treeSelcetData2.setLabel(areaCopy2.getName());
                    treeSelcetData2.setValue(areaCopy2.getName());
                    chilren2.add(treeSelcetData2);
                }

                treeSelcetData1.setChildren(chilren2);
            }
            }
            return treeSelcetDatalist;
        }
    }
