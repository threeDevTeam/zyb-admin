package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class ServiceOfRegisterModel extends BaseRowModel {
	@ExcelProperty(value = {"机构名称"},index=0)
	private String name;

	@ExcelProperty(value = {"社会统一代码"},index=1)
	private String code;

	@ExcelProperty(value = {"省的名称"},index=2)
	private String provinceName;

	@ExcelProperty(value = {"省的代码"},index=3)
	private String provinceCode;

	@ExcelProperty(value = {"市的名称"},index=4)
	private String cityName;

	@ExcelProperty(value = {"市的代码"},index=5)
	private String cityCode;

	@ExcelProperty(value = {"区的名称"},index=6)
	private String districtName;

	@ExcelProperty(value = {"区的代码"},index=7)
	private String districtCode;

	@ExcelProperty(value = {"注册地址"},index=8)
	private String registerAddress;

	@ExcelProperty(value = {"机构类型"},index=9)
	private String type;

	@ExcelProperty(value = {"电子邮箱"},index=10)
	private String email;

	@ExcelProperty(value = {"手机号码"},index=11)
	private String mobile;

}
