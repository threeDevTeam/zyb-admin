package com.hthyaq.zybadmin.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.utils.cache.DataVisualCacheUtil;
import com.hthyaq.zybadmin.common.utils.cascade.CascadeUtil;
import com.hthyaq.zybadmin.common.utils.cascade.CascadeView;
import com.hthyaq.zybadmin.common.utils.treeSelect.TreeSelectUtil;
import com.hthyaq.zybadmin.common.utils.treeSelect.TreeSelectView;
import com.hthyaq.zybadmin.model.bean.GlobalResult;
import com.hthyaq.zybadmin.model.entity.AreaOfDic;
import com.hthyaq.zybadmin.model.entity.TreeSelcetData;
import com.hthyaq.zybadmin.service.AreaOfDicService;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 省市县三级的行政区域数据 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-14
 */
@RestController
@RequestMapping("/areaOfDic")
public class AreaOfDicController {
    @Autowired
    AreaOfDicService areaOfDicService;

    @GetMapping("/init")
    public boolean init() throws IOException {
        List<AreaOfDic> areaList = Lists.newArrayList();
        List<String> list = FileUtils.readLines(new File("d:/201906area.txt"), Charsets.UTF_8);
        for (String s : list) {
            String[] strings = s.split("\\s+");
            AreaOfDic area = new AreaOfDic();
            area.setCode(Integer.parseInt(strings[0].trim()));
            area.setName(strings[1].trim());
            areaList.add(area);
        }
        return areaOfDicService.saveBatch(areaList);
    }

    @GetMapping("/get")
    public int get(String name1, String name2, String name3) {
        AreaOfDic areaOfDic = DataVisualCacheUtil.getAreaSelf(name1, name2, name3);
        return areaOfDic.getChildNum();
    }

    @GetMapping("/getGeoJsonByName")
    public GlobalResult getGeoJsonByName(String name1, String name2) {
        String name = name1;
        if (!Strings.isNullOrEmpty(name2)) {
            name = name2;
        }
        List<AreaOfDic> list = areaOfDicService.list(new QueryWrapper<AreaOfDic>().likeRight("name", name));
        if (ObjectUtil.length(list) != 1) {
            throw new RuntimeException("根据" + name + "查询时出错误了");
        }

        return GlobalResult.success("", list.get(0).getGeoJson());
    }

    //https://datav.aliyun.com/tools/atlas/
    @GetMapping("/getGeoJsonFromRemoteServer")
    public boolean getGeoJsonFromRemoteServer() throws InterruptedException {
        List<AreaOfDic> list = areaOfDicService.list(new QueryWrapper<AreaOfDic>().gt("child_num", 0));
        System.out.println(list.size());
        Random random = new Random();
        int count = 0;
        try {
            for (AreaOfDic area : list) {
                if (area.getChildNum() > 0 && Strings.isNullOrEmpty(area.getGeoJson())) {
                    String url = "https://geo.datav.aliyun.com/areas/bound/" + area.getCode() + "_full.json";
                    String geoJson = HttpUtil.get(url);
                    area.setGeoJson(geoJson);

                    count++;
                    System.out.println(area.getName() + "--" + count);
//                    Thread.sleep(random.nextInt(60000));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            areaOfDicService.updateBatchById(list);
        }

        return true;
    }

    //设置level、childNum、pid
    @GetMapping("/set")
    public boolean set() {
        return areaOfDicService.set();
    }

    @GetMapping("/TreeSelcetData2")
    public List<TreeSelcetData> TreeSelcetData2() {
        List<TreeSelcetData> treeSelcetDatalist = new ArrayList();

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", "-1");
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaCopy : list) {
            List<TreeSelcetData> chilren = Lists.newArrayList();
            TreeSelcetData treeSelcetData = new TreeSelcetData();
            treeSelcetData.setLabel(areaCopy.getName());
            treeSelcetData.setValue(areaCopy.getName());
            treeSelcetData.setChildren(chilren);
            treeSelcetDatalist.add(treeSelcetData);

            QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("pid", areaCopy.getId());
            List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);

            for (AreaOfDic areaCopy1 : list1) {
                TreeSelcetData treeSelcetData1 = new TreeSelcetData();
                treeSelcetData1.setLabel(areaCopy1.getName());
                treeSelcetData1.setValue(areaCopy1.getName());
                chilren.add(treeSelcetData1);
                List<TreeSelcetData> chilren2 = Lists.newArrayList();
                QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("pid", areaCopy1.getId());
                List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);

                for (AreaOfDic areaCopy2 : list2) {
                    TreeSelcetData treeSelcetData2 = new TreeSelcetData();
                    treeSelcetData2.setLabel(areaCopy2.getName());
                    treeSelcetData2.setValue(areaCopy2.getName());
                    chilren2.add(treeSelcetData2);
                }

                treeSelcetData1.setChildren(chilren2);
            }
        }
        return treeSelcetDatalist;
    }

    @GetMapping("/cascadeData")
    public List<CascadeView> cascadeData() {
        List<AreaOfDic> list = areaOfDicService.list();
        return CascadeUtil.get(list);
    }

    @GetMapping("/treeSelcetData")
    public List<TreeSelectView> treeSelcetData() {
        List<AreaOfDic> list = areaOfDicService.list();
        return TreeSelectUtil.get(list);
    }

}
