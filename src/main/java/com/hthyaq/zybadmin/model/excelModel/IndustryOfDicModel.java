package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class IndustryOfDicModel extends BaseRowModel {

    @ExcelProperty(value = {"类别名称2"},index=4)
    private String typeName2;

    @ExcelProperty(value = {"level"},index=5)
    private String level;
}
