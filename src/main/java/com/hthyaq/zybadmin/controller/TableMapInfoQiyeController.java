package com.hthyaq.zybadmin.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.TableMapInfoJishu;
import com.hthyaq.zybadmin.model.entity.TableMapInfoQiye;
import com.hthyaq.zybadmin.service.TableMapInfoJishuService;
import com.hthyaq.zybadmin.service.TableMapInfoQiyeService;
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
 * @since 2019-09-17
 */
@RestController
@RequestMapping("/tableMapInfoQiye")
public class TableMapInfoQiyeController {
    @Autowired
    TableMapInfoQiyeService tableMapInfoQiyeService;

    @GetMapping("/add")
    public void add() {
        ExcelReader reader = ExcelUtil.getReader("D:/Qiye.xlsx");
        List<TableMapInfoQiye> all = reader.readAll(TableMapInfoQiye.class);

        for(TableMapInfoQiye tableMapInfoQiye: all) {

            tableMapInfoQiyeService.save(tableMapInfoQiye);
        }
    }
    @GetMapping("/generateExcelModel")
    public boolean generateExcelModel() throws IOException {
        List<TableMapInfoQiye> list1 = tableMapInfoQiyeService.list();
        for (TableMapInfoQiye tableMapInfoQiye : list1) {
            String englishTableName = tableMapInfoQiye.getEnglishTableName();
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
        List<TableMapInfoQiye> list = tableMapInfoQiyeService.list(new QueryWrapper<TableMapInfoQiye>().eq("englishTableName", englishTableName).notIn("chineseColumnName", "主键", "外键"));
        for (int i = 0; i < list.size(); i++) {
            TableMapInfoQiye tableMapInfoQiye = list.get(i);
            String chineseColumnName = tableMapInfoQiye.getChineseColumnName();
            String englishColumnName = tableMapInfoQiye.getEnglishColumnName();
            String dataType = tableMapInfoQiye.getDataType();
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
    @GetMapping("/generatejsModel")
    public boolean generatejsModel() throws IOException {
        QueryWrapper<TableMapInfoQiye> queryWrapper=new QueryWrapper<>();
        queryWrapper.groupBy("englishTableName");
        List<TableMapInfoQiye> list = tableMapInfoQiyeService.list(queryWrapper);
        for (TableMapInfoQiye tableMapInfoQiye : list) {
            generatejsExcelModelByTableName(tableMapInfoQiye.getEnglishTableName());
        }

        return true;
    }

    private void generatejsExcelModelByTableName(String tableName) throws IOException {

        String jsFileName = WordUtils.capitalize(tableName);

        List<String> result = Lists.newArrayList();
        result.add("import React, {PureComponent} from 'react'");
        result.add("import Form, {FormItem, FormCore} from 'noform'");
        result.add("import {Input,InputNumber} from 'nowrapper/lib/antd'");
        result.add("const validate = {");
        List<TableMapInfoQiye> list = tableMapInfoQiyeService.list(new QueryWrapper<TableMapInfoQiye>().eq("englishTableName", tableName).notIn("chineseColumnName", "主键", "外键"));
        for (int i = 0; i < list.size(); i++) {
            TableMapInfoQiye tableMapInfoQiye = list.get(i);
            String chineseColumnName = tableMapInfoQiye.getChineseColumnName();
            String englishColumnName = tableMapInfoQiye.getEnglishColumnName();
            String dataType = tableMapInfoQiye.getDataType();
            if(dataType.equals("String")){
                String annotationStr=englishColumnName+": {type: \"string\", required: true, message: '"+chineseColumnName+"不能为空'},";
                result.add(annotationStr);
            }else{
                String annotationStr=englishColumnName+": {type: \"number\", required: true, message: '"+chineseColumnName+"不能为空'},";
                result.add(annotationStr);
            }
        }
        result.add("");
        result.add("}");

        result.add("class "+jsFileName+"DemoForm extends PureComponent {");
        result.add(" state = {}");
        result.add(" constructor(props) {");
        result.add("  super(props);");
        result.add("this.core = new FormCore({validateConfig: validate});");
        result.add(" }");
        result.add("componentWillMount() {");
        result.add(" }");
        result.add(" render() {");
        result.add("  return (");
        result.add(" <Form core={this.core} layout={{label: 4, control: 20}}>");
        result.add(" <FormItem style={{display: 'none'}} name=\"id\"><Input/></FormItem>");
        for (int i = 0; i < list.size(); i++) {
            TableMapInfoQiye tableMapInfoQiye = list.get(i);
            String chineseColumnName = tableMapInfoQiye.getChineseColumnName();
            String englishColumnName = tableMapInfoQiye.getEnglishColumnName();
            String dataType = tableMapInfoQiye.getDataType();
            if(dataType.equals("String")) {
                String Formdata = " <FormItem label=\"" + chineseColumnName + "\" name=\"" + englishColumnName + "\"><Input/></FormItem>";
                result.add(Formdata);
            }else{
                String Formdata = " <FormItem label=\"" + chineseColumnName + "\" name=\"" + englishColumnName + "\"><InputNumber/></FormItem>";
                result.add(Formdata);
            }
        }
        result.add(" </Form>");
        result.add(" )");
        result.add(" }");
        result.add(" }");
        result.add("export default "+jsFileName+"DemoForm");
        String jsModelPath = System.getProperty("user.dir") + "/src/main/java/com/hthyaq/zybadmin/model/js/";
        IOUtils.writeLines(result, "\r\n", new FileOutputStream(jsModelPath + jsFileName + "DemoForm.js"), "utf8");
    }
}
