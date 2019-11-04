package com.hthyaq.zybadmin.model.excelModel;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TableMapInfoModel extends BaseRowModel {

    @ExcelProperty(value = {"中文表名"},index=0)
    private String chineseTableName;
    @ExcelProperty(value = {"英文表名--传输"},index=1)
    private String englishTableName;
    @ExcelProperty(value = {"中文名称"},index=2)
    private String chineseColumnName;
    @ExcelProperty(value = {"英文表名--传输"},index=3)
    private String englishColumnName;
    @ExcelProperty(value = {"Java数据类型"},index=4)
    private String dataType;
}
