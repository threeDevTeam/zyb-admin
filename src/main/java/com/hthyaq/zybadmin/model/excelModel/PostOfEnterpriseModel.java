package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class PostOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"工作场所名称"},index=0)
	private String name;

	@ExcelProperty(value = {"岗位的小类名称"},index=1)
	private String postSmallName;

}
