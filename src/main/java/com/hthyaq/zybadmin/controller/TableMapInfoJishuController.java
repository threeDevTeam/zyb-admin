package com.hthyaq.zybadmin.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.Gangwei;
import com.hthyaq.zybadmin.model.entity.TableMapInfoJishu;
import com.hthyaq.zybadmin.model.entity.TablemapinfoCopy;
import com.hthyaq.zybadmin.service.TableMapInfoJishuService;
import com.hthyaq.zybadmin.service.TablemapinfoCopyService;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 中英文表名、字段名的映射信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-09
 */
@RestController
@RequestMapping("/tableMapInfoJishu")
public class TableMapInfoJishuController {
    @Autowired
    TableMapInfoJishuService tableMapInfoJishuService;

    @GetMapping("/add")
    public void add() {
        ExcelReader reader = ExcelUtil.getReader("D:/JISHU.xlsx");
        List<TableMapInfoJishu> all = reader.readAll(TableMapInfoJishu.class);
        System.out.println(all);
        for(TableMapInfoJishu tableMapInfoJishu: all) {
            System.out.println(tableMapInfoJishu);
            tableMapInfoJishuService.save(tableMapInfoJishu);
        }
    }
    @GetMapping("/generateExcelModel")
    public boolean generateExcelModel() throws IOException {
        List<TableMapInfoJishu> list1 = tableMapInfoJishuService.list();
        for (TableMapInfoJishu tableMapInfoJishu : list1) {
            String englishTableName = tableMapInfoJishu.getEnglishTableName();
            generateExcelModelByTableName(englishTableName);
        }
        return true;
    }
    //根据表名生成ExcelModel
    private void generateExcelModelByTableName(String englishTableName) throws IOException {
        //将表名的首字母转为大写
        String excelModelFileName = WordUtils.capitalize(englishTableName);

        List<String> result = Lists.newArrayList();
        result.add("package com.hthyaq.zybadmin.model.excelModel;" + "\r\n");
        result.add("import com.alibaba.excel.annotation.ExcelProperty;");
        result.add("import com.alibaba.excel.metadata.BaseRowModel;");
        result.add("import lombok.Data;" + "\r\n");
        result.add("@Data");
        result.add("public class " + excelModelFileName + "Model extends BaseRowModel {");

        //拼接一行一行java代码
        List<TableMapInfoJishu> list = tableMapInfoJishuService.list(new QueryWrapper<TableMapInfoJishu>().eq("englishTableName", englishTableName).notIn("chineseColumnName", "主键", "外键"));
        for (int i = 0; i < list.size(); i++) {
            TableMapInfoJishu tableMapInfoJishu = list.get(i);
            String chineseColumnName = tableMapInfoJishu.getChineseColumnName();
            String englishColumnName = tableMapInfoJishu.getEnglishColumnName();
            String dataType = tableMapInfoJishu.getDataType();
            //拼接
            String annotationStr = "\t" + "@ExcelProperty(value = {\"" + chineseColumnName + "\"},index=" + i + ")";
            String propertyStr = "\t" + "private " + dataType + " " + englishColumnName + ";" + "\r\n";
            result.add(annotationStr);
            result.add(propertyStr);
        }

        result.add("}");
        //获取zyb-admin的execelModel的路径
        String excelModelPath = System.getProperty("user.dir") + "/src/main/java/com/hthyaq/zybadmin/model/excelModel/";
        //生成excelModel的java文件
        IOUtils.writeLines(result, "\r\n", new FileOutputStream(excelModelPath + excelModelFileName + "Model.java"), "utf8");
    }
}
