package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class ServiceOfSuperviseModel extends BaseRowModel {
	@ExcelProperty(value = {"年份 "},index=0)
	private Integer year;

	@ExcelProperty(value = {"检测机构的资质等级"},index=1)
	private String jianceLevel;

	@ExcelProperty(value = {"检测机构的新增"},index=2)
	private Integer jianceIncrease;

	@ExcelProperty(value = {"检测机构的累计"},index=3)
	private Integer jianceSum;

	@ExcelProperty(value = {"体检机构的新增"},index=4)
	private Integer tijianIncrease;

	@ExcelProperty(value = {"体检机构的累计"},index=5)
	private Integer tijianSum;

	@ExcelProperty(value = {"诊断机构的新增"},index=6)
	private Integer zhenduanIncrease;

	@ExcelProperty(value = {"诊断机构的累计"},index=7)
	private Integer zhenduanSum;

}
