package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class SuperviseModel extends BaseRowModel {
	@ExcelProperty(value = {"申报年份"},index=0)
	private Integer year;

	@ExcelProperty(value = {"省的名称"},index=1)
	private String provinceName;

	@ExcelProperty(value = {"省的代码"},index=2)
	private String provinceCode;

	@ExcelProperty(value = {"市的名称"},index=3)
	private String cityName;

	@ExcelProperty(value = {"市的代码"},index=4)
	private String cityCode;

	@ExcelProperty(value = {"区的名称"},index=5)
	private String districtName;

	@ExcelProperty(value = {"区的代码"},index=6)
	private String districtCode;

	@ExcelProperty(value = {"注册地址"},index=7)
	private String registerAddress;

	@ExcelProperty(value = {"单位名称"},index=8)
	private String name;

	@ExcelProperty(value = {"是否独立设置职业健康监管部门"},index=9)
	private String isSet;

	@ExcelProperty(value = {"职业健康监管人员编制数"},index=10)
	private Integer markCount;

	@ExcelProperty(value = {"在岗职业健康监管人员数"},index=11)
	private Integer manageCount;

}
