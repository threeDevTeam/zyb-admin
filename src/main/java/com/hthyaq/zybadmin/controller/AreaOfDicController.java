package com.hthyaq.zybadmin.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.model.bean.GlobalResult;
import com.hthyaq.zybadmin.model.entity.AreaOfDic;
import com.hthyaq.zybadmin.service.AreaOfDicService;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
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

    @GetMapping("/getGeoJsonByName")
    public GlobalResult getGeoJsonByName(String name) {
        List<AreaOfDic> list = areaOfDicService.list(new QueryWrapper<AreaOfDic>().likeRight("name", name));
        if (ObjectUtil.length(list) != 1) {
            throw new RuntimeException("根据" + name + "查询时出错误了");
        }

        return GlobalResult.success("", list.get(0).getGeoJson());
    }

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
        //4个直辖市
        shi();
        //省份、自治区
        sheng();
        return true;
    }

    private void sheng() {
        //1.list=取出所有省,select * from area where adcode like '%0000' and name not like '%市'
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeLeft("adcode", "0000").notLike("name", "市");
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        //2.遍历list
        for (AreaOfDic area : list) {
            //设置level
            area.setLevel("1");

            Integer id = area.getId();
            int adcode = area.getCode();
            //通过adcode，得到adcode2
            String adcode1 = adcode + "";
            adcode1 = adcode1.substring(0, 3) + "_00";
            //取出安徽省的下级节点,list2=select * from area where adcode like '340_00' and adcode !=340000
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.likeLeft("adcode", adcode1).ne("adcode", adcode);
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            //遍历list2
            for (AreaOfDic area2 : list2) {
                //设置pid、level
                area2.setPid(id);
                area2.setLevel("2");

                Integer id2 = area2.getId();
                String adcode2 = area2.getCode() + "";
                adcode2 = adcode2.substring(0, 4);
                //根据adcode获取合肥市下的所有节点,list3=select * from area where adcode like '3401%' and adcode != 340100
                QueryWrapper<AreaOfDic> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.likeRight("adcode", adcode2).ne("adcode", area2.getCode());
                List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
                //遍历list3
                for (AreaOfDic area3 : list3) {
                    //设置pid、level
                    area3.setPid(id2);
                    area3.setLevel("3");
                }
                //更新list3
                if (CollectionUtil.isNotEmpty(list3)) {
                    areaOfDicService.updateBatchById(list3);
                }
                //设置area2的childNum
                area2.setChildNum(list3.size());
            }
            if (CollectionUtil.isNotEmpty(list2)) {
                //更新list2
                areaOfDicService.updateBatchById(list2);
            }

            //设置area的childNum
            area.setChildNum(list2.size());
        }
        areaOfDicService.updateBatchById(list);
    }

    private void shi() {
        //1.list=取出4个直辖市,select * from area where adcode like '%0000' and name like '%市'
        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeLeft("adcode", "0000").likeLeft("name", "市");
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        //2.遍历list
        for (AreaOfDic area : list) {
            //设置level
            area.setLevel("1");

            Integer id = area.getId();
            Integer adcode = area.getCode();
            String adcode2 = adcode + "";
            adcode2 = adcode2.substring(0, 3);
            //根据adcode获取北京市下的区, select * from area where adcode like '11%' and adcode!=110000
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.likeRight("adcode", adcode2).ne("adcode", adcode);
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic tmp : list2) {
                //设置pid
                tmp.setPid(id);
                tmp.setLevel("2");
            }
            //批量更新
            areaOfDicService.updateBatchById(list2);

            //设置childNum
            area.setChildNum(list2.size());
        }

        //3.批量更新
        areaOfDicService.updateBatchById(list);
    }
}
