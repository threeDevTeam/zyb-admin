package com.hthyaq.zybadmin.controller;


import cn.hutool.system.UserInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.common.excle.MyExcelUtil;
import com.hthyaq.zybadmin.model.entity.IndustryOfDic;
import com.hthyaq.zybadmin.model.entity.TableMapInfo;
import com.hthyaq.zybadmin.model.excelModel.IndustryOfDicModel;
import com.hthyaq.zybadmin.model.excelModel.TableMapInfoModel;
import com.hthyaq.zybadmin.service.TableMapInfoService;
import org.apache.catalina.UserDatabase;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 中英文表名、字段名的映射信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-01
 */
@RestController
@RequestMapping("/tableMapInfo")
public class TableMapInfoController {
    @Autowired
    TableMapInfoService tableMapInfoService;

    @PostMapping("/tableMapInfoExcel")
    public Boolean moreSheetExcel(MultipartFile[] files) {
        boolean flag = true;
        //excel->model
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files, TableMapInfoModel.class);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<TableMapInfo> dataList = getDataList(modelList, type);
            flag = tableMapInfoService.saveBatch(dataList);
        }
        return flag;
    }

    private List<TableMapInfo> getDataList(List<Object> modelList, String type) {
        List<TableMapInfo> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            TableMapInfoModel TableMapInfoModel = (TableMapInfoModel) object;
            String chineseTableName = TableMapInfoModel.getChineseTableName();
            String englishTableName = TableMapInfoModel.getEnglishTableName();
            String chineseColumnName = TableMapInfoModel.getChineseColumnName();
            String englishColumnName = TableMapInfoModel.getEnglishColumnName();
            String dataType = TableMapInfoModel.getDataType();
            //业务处理
            TableMapInfo tableMapInfo = new TableMapInfo();
            tableMapInfo.setChineseTableName(chineseTableName);
            tableMapInfo.setEnglishTableName(englishTableName);
            tableMapInfo.setChineseColumnName(chineseColumnName);
          tableMapInfo.setEnglishColumnName(englishColumnName);
          tableMapInfo.setDataType(dataType);
            dataList.add(tableMapInfo);
        }
        return dataList;
    }

    @GetMapping("/generateExcelModel")
    public boolean generateExcelModel() throws IOException {
        QueryWrapper<TableMapInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.groupBy("englishTableName");
        List<TableMapInfo> list = tableMapInfoService.list(queryWrapper);
        for (TableMapInfo tableMapInfo : list) {
            generateExcelModelByTableName(tableMapInfo.getEnglishTableName());
        }
        return true;
    }

    //根据表名生成ExcelModel
    private void generateExcelModelByTableName(String tableName) throws IOException {
        //将表名的首字母转为大写
        String excelModelFileName = WordUtils.capitalize(tableName);

        List<String> result = Lists.newArrayList();
        result.add("package com.hthyaq.zybadmin.model.excelModel;" + "\r\n");
        result.add("import com.alibaba.excel.annotation.ExcelProperty;");
        result.add("import com.alibaba.excel.metadata.BaseRowModel;");
        result.add("import lombok.Data;" + "\r\n");
        result.add("@Data");
        result.add("public class " + excelModelFileName + "Model extends BaseRowModel {");

        //拼接一行一行java代码
        List<TableMapInfo> list = tableMapInfoService.list(new QueryWrapper<TableMapInfo>().eq("englishTableName", tableName).notIn("chineseColumnName", "主键", "外键"));
        for (int i = 0; i < list.size(); i++) {
            TableMapInfo tableMapInfo = list.get(i);
            String chineseColumnName = tableMapInfo.getChineseColumnName();
            String englishColumnName = tableMapInfo.getEnglishColumnName();
            String dataType = tableMapInfo.getDataType();
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
        IOUtils.writeLines(result, "\r\n", new FileOutputStream(excelModelPath + excelModelFileName + "Model2.java"), "utf8");
    }



    @GetMapping("/generatejsModel")
    public boolean generatejsModel() throws IOException {
        QueryWrapper<TableMapInfo> queryWrapper=new QueryWrapper<>(); 
        queryWrapper.groupBy("englishTableName");
        List<TableMapInfo> list = tableMapInfoService.list(queryWrapper);
        for (TableMapInfo tableMapInfo : list) {
            generatejsExcelModelByTableName(tableMapInfo.getEnglishTableName());
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
        List<TableMapInfo> list = tableMapInfoService.list(new QueryWrapper<TableMapInfo>().eq("englishTableName", tableName).notIn("chineseColumnName", "主键", "外键"));
        for (int i = 0; i < list.size(); i++) {
            TableMapInfo tableMapInfo = list.get(i);
            String chineseColumnName = tableMapInfo.getChineseColumnName();
            String englishColumnName = tableMapInfo.getEnglishColumnName();
            String dataType = tableMapInfo.getDataType();
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
            TableMapInfo tableMapInfo = list.get(i);
            String chineseColumnName = tableMapInfo.getChineseColumnName();
            String englishColumnName = tableMapInfo.getEnglishColumnName();
            String dataType = tableMapInfo.getDataType();
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



    @PostMapping("/exportExcel")
    public boolean ExportExcel (HttpServletResponse response) throws IOException  {
        String fileName = "总数据.xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ";filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8"));
        response.flushBuffer();
        List <TableMapInfo> tableMapInfo =tableMapInfoService.list();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        int rowNum = 0;
        String[] headers = {"id", "chineseTableName", "englishTableName", "chineseColumnName", "englishColumnName","dataType"};
        HSSFRow row = sheet.createRow(rowNum);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        for (TableMapInfo mapInfo : tableMapInfo) {
            rowNum++;
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(mapInfo.getId());
            row1.createCell(1).setCellValue(mapInfo.getChineseTableName());
            row1.createCell(2).setCellValue(mapInfo.getEnglishTableName());
            row1.createCell(3).setCellValue(mapInfo.getChineseColumnName());
            row1.createCell(4).setCellValue(mapInfo.getEnglishColumnName());
            row1.createCell(5).setCellValue(mapInfo.getDataType());
        }
        OutputStream os=new BufferedOutputStream(response.getOutputStream());
        workbook.write(os);
        os.flush();
        return true;
    }
}
