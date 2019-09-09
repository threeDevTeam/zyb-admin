package com.hthyaq.zybadmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.hthyaq.zybadmin.model.entity.Gangwei;
import com.hthyaq.zybadmin.model.entity.TreeSelcetData;
import com.hthyaq.zybadmin.model.entity.TreeSelcetData2;
import com.hthyaq.zybadmin.service.GangweiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 数据表1 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-05
 */
@RestController
@RequestMapping("/gangwei")
public class GangweiController {
    @Autowired
    GangweiService gangweiService;
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetData2> TreeSelcetData2() {
        List treeSelcetDatalist1=new ArrayList();
        List<TreeSelcetData2> treeSelcetDatalist=new ArrayList();


        QueryWrapper<Gangwei> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("Pid","-1");
        List<Gangwei> list = gangweiService.list(queryWrapper);
        for (Gangwei gangwei : list) {
            TreeSelcetData2 treeSelcetData=new TreeSelcetData2();
            treeSelcetData.setTitle(gangwei.getIndustryName());
            treeSelcetData.setKey(gangwei.getId());
            treeSelcetData.setValue(gangwei.getId());
            treeSelcetDatalist.add(treeSelcetData);
            treeSelcetData.setChildren(treeSelcetDatalist1);

            QueryWrapper<Gangwei> queryWrapper1=new QueryWrapper<>();
            queryWrapper1.eq("pId",gangwei.getId());
            List<Gangwei> list1 = gangweiService.list(queryWrapper1);
            for (Gangwei gangwei1 : list1) {
                //煤炭开采和洗选业
                TreeSelcetData2 treeSelcetData2=new TreeSelcetData2();
                treeSelcetData2.setTitle(gangwei1.getIndustryName());
                treeSelcetData2.setKey(gangwei1.getId());
                treeSelcetData2.setValue(gangwei1.getId());
                treeSelcetDatalist1.add(treeSelcetData2);
                //煤炭开采和洗选业的children
                List<TreeSelcetData2> chilren= Lists.newArrayList();
                QueryWrapper<Gangwei> queryWrapper2=new QueryWrapper<>();
                queryWrapper2.eq("Pid",gangwei1.getId());
                List<Gangwei> list2 = gangweiService.list(queryWrapper2);

                for (Gangwei gangwei2 : list2) {
                    TreeSelcetData2 treeSelcetData3=new TreeSelcetData2();
                    treeSelcetData3.setTitle(gangwei2.getPostName());
                    treeSelcetData3.setKey(gangwei2.getId());
                    treeSelcetData3.setValue(gangwei2.getId());
                    chilren.add(treeSelcetData3);
                }

                treeSelcetData2.setChildren(chilren);
            }
        }

        return treeSelcetDatalist;
    }
}

