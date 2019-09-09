package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class ZhenduanBasicOfServiceModel extends BaseRowModel {
	@ExcelProperty(value = {"机构名称"},index=0)
	private String name;

	@ExcelProperty(value = {"社会统一代码"},index=1)
	private String code;

	@ExcelProperty(value = {"申报年份"},index=2)
	private Integer year;

	@ExcelProperty(value = {"省的名称"},index=3)
	private String provinceName;

	@ExcelProperty(value = {"省的代码"},index=4)
	private String provinceCode;

	@ExcelProperty(value = {"市的名称"},index=5)
	private String cityName;

	@ExcelProperty(value = {"市的代码"},index=6)
	private String cityCode;

	@ExcelProperty(value = {"区的名称"},index=7)
	private String districtName;

	@ExcelProperty(value = {"区的代码"},index=8)
	private String districtCode;

	@ExcelProperty(value = {"注册地址"},index=9)
	private String registerAddress;

	@ExcelProperty(value = {"登记注册类型的大类名称"},index=10)
	private String registerBigName;

	@ExcelProperty(value = {"登记注册类型的小类名称"},index=11)
	private String registerSmallName;

	@ExcelProperty(value = {"资质等级"},index=12)
	private String level;

	@ExcelProperty(value = {"资质证书编号"},index=13)
	private String num;

	@ExcelProperty(value = {"诊断医师数量"},index=14)
	private Integer doctorNum;

	@ExcelProperty(value = {"诊断项目数量"},index=15)
	private Integer projectCount;

	@ExcelProperty(value = {"诊断能力"},index=16)
	private String scope;

	@ExcelProperty(value = {"医院等级"},index=17)
	private String hospitalLevel;

}
