package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseOfDicModel extends BaseRowModel {
    @ExcelProperty(value = {"大类名称"})
    private String bigName;

    @ExcelProperty(value = {"小类名称"})
    private String smallName;

    @ExcelProperty(value = {"级别"})
    private Integer level;
}
