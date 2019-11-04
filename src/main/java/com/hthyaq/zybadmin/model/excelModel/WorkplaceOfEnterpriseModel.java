package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WorkplaceOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"工作场所名称"},index=0)
	private String name;

	@ExcelProperty(value = {"工作场所编码"},index=1)
	private Integer code;

}
