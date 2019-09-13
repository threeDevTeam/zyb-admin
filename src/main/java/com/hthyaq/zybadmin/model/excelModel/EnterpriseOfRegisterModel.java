package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class EnterpriseOfRegisterModel extends BaseRowModel {
	@ExcelProperty(value = {"企业名称"},index=0)
	private String name;

	@ExcelProperty(value = {"统一社会信用代码"},index=1)
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

	@ExcelProperty(value = {"核定生产能力"},index=8)
	private String productionCapacity;

	@ExcelProperty(value = {"生产能力单位类型"},index=9)
	private String unitType;

	@ExcelProperty(value = {"注册资本"},index=10)
	private String regiterMoney;

	@ExcelProperty(value = {"注册地址"},index=11)
	private String registerAddress;

	@ExcelProperty(value = {"注册时间"},index=12)
	private String registerDate;

	@ExcelProperty(value = {"投产时间"},index=13)
	private String startDate;

	@ExcelProperty(value = {"资产总额"},index=14)
	private String propertyMoney;

	@ExcelProperty(value = {"电子邮箱"},index=15)
	private String email;

	@ExcelProperty(value = {"手机号码"},index=16)
	private String mobile;

}
