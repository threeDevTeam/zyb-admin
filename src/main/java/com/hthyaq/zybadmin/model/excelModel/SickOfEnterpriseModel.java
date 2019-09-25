package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class SickOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"姓名"},index=0)
	private String name;

	@ExcelProperty(value = {"身份证号"},index=1)
	private String idNum;

	@ExcelProperty(value = {"职业病大类名称"},index=2)
	private String sickBigName;

	@ExcelProperty(value = {"职业病小类名称"},index=3)
	private String sickSmallName;

	@ExcelProperty(value = {"病人类别"},index=4)
	private String type;

	@ExcelProperty(value = {"诊断机构"},index=5)
	private String org;

	@ExcelProperty(value = {"诊断日期"},index=6)
	private Integer checkDate;

	@ExcelProperty(value = {"诊断年份"},index=7)
	private Integer checkYear;

	@ExcelProperty(value = {"诊断月份"},index=8)
	private Integer checkMonth;

	@ExcelProperty(value = {"发病工龄"},index=9)
	private Integer sickYear;

	@ExcelProperty(value = {"是否进行了职业病病人报告"},index=10)
	private String isReport;

	@ExcelProperty(value = {"职业病损失工作日"},index=11)
	private Integer workDay;

	@ExcelProperty(value = {"新增"},index=12)
	private String increase;

	@ExcelProperty(value = {"累计"},index=13)
	private String total;

	@ExcelProperty(value = {"转归情况"},index=14)
	private String transform;

	@ExcelProperty(value = {"死亡日期"},index=15)
	private Integer dieDate;

	@ExcelProperty(value = {"死亡年份"},index=16)
	private Integer dieYear;

	@ExcelProperty(value = {"死亡月份"},index=17)
	private Integer dieMonth;

}
