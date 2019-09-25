package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class AlikeSickOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"姓名"},index=0)
	private String name;

	@ExcelProperty(value = {"身份证号"},index=1)
	private String idNum;

	@ExcelProperty(value = {"检查机构"},index=2)
	private String org;

	@ExcelProperty(value = {"检查日期"},index=3)
	private Integer checkDate;

	@ExcelProperty(value = {"检查年份"},index=4)
	private Integer checkYear;

	@ExcelProperty(value = {"检查月份"},index=5)
	private Integer checkMonth;

	@ExcelProperty(value = {"发病工龄"},index=6)
	private Integer sickYear;

	@ExcelProperty(value = {"是否进行了疑似职业病病人报告"},index=7)
	private String isReport;

}
