package com.hthyaq.zybadmin.controller.demo;

import cn.hutool.core.util.ReflectUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.hthyaq.zybadmin.common.excle.AllModel;
import com.hthyaq.zybadmin.common.excle.MyExcelUtil2;
import com.hthyaq.zybadmin.model.bean.GlobalResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
@Controller
@RequestMapping("/excel")
public class ExcelController {
    @GetMapping("/test")
    public GlobalResult test() {
        File file = new File("D:\\aa\\~HYS-XS-2018-017-BJ-wy北七筒改造项目设备.xlsx");

        List<File> list = Lists.newArrayList();
        list.add(file);

        File[] files = new File[list.size()];

        list.toArray(files);

        Map<String, List<Object>> map = MyExcelUtil2.readMoreSheetExcel(files, AllModel.class);
        for (Map.Entry<String, List<Object>> entry : map.entrySet()) {
            String sheetName = entry.getKey();
            List<Object> dataList = entry.getValue();
            Object obj = (Object) dataList;
            List<AllModel> data = (List<AllModel>) obj;
            StringBuffer sb = new StringBuffer();
            for (AllModel tmp : data) {
                for (int i = 0; i <= 30; i++) {
                    String name = "var" + i;
                    String value = (String) ReflectUtil.getFieldValue(tmp, name);
                    if (!Strings.isNullOrEmpty(value)) {
//                        System.out.println(value);
                        sb.append(value);
                    }
                }
            }
            //处理数据
            System.out.println(sheetName);
            System.out.println(sb.toString());
        }

        return GlobalResult.success("msg", "abcde");
    }
}
