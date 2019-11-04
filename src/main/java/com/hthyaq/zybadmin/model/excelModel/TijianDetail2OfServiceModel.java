package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TijianDetail2OfServiceModel extends BaseRowModel {
	@ExcelProperty(value = {"体检时间"},index=0)
	private Integer checkDate;

	@ExcelProperty(value = {"体检年份"},index=1)
	private Integer checkYear;

	@ExcelProperty(value = {"体检月份"},index=2)
	private Integer checkMonth;

	@ExcelProperty(value = {"体检报告编号"},index=3)
	private String num;

	@ExcelProperty(value = {"企业名称"},index=4)
	private String enterpriseName;

	@ExcelProperty(value = {"统一社会信用代码"},index=5)
	private String enterpriseCode;

	@ExcelProperty(value = {"省的名称"},index=6)
	private String provinceName;

	@ExcelProperty(value = {"省的代码"},index=7)
	private String provinceCode;

	@ExcelProperty(value = {"市的名称"},index=8)
	private String cityName;

	@ExcelProperty(value = {"市的代码"},index=9)
	private String cityCode;

	@ExcelProperty(value = {"区的名称"},index=10)
	private String districtName;

	@ExcelProperty(value = {"区的代码"},index=11)
	private String districtCode;

	@ExcelProperty(value = {"注册地址"},index=12)
	private String registerAddress;

	@ExcelProperty(value = {"登记注册类型的大类名称"},index=13)
	private String registerBigName;

	@ExcelProperty(value = {"登记注册类型的小类名称"},index=14)
	private String registerSmallName;

	@ExcelProperty(value = {"所属行业的大类名称"},index=15)
	private String industryBigName;

	@ExcelProperty(value = {"所属行业的小类名称"},index=16)
	private String industrySmallName;

	@ExcelProperty(value = {"工作场所地址"},index=17)
	private String workAddress;

	@ExcelProperty(value = {"工作场所名称"},index=18)
	private String workplaceName;

	@ExcelProperty(value = {"工作场所编码"},index=19)
	private Integer workplaceCode;

	@ExcelProperty(value = {"岗位的大类名称"},index=20)
	private String postBigName;

	@ExcelProperty(value = {"岗位的小类名称"},index=21)
	private String postSmallName;

	@ExcelProperty(value = {"姓名"},index=22)
	private String name;

	@ExcelProperty(value = {"身份证号"},index=23)
	private String idNum;

	@ExcelProperty(value = {"性别"},index=24)
	private String gender;

	@ExcelProperty(value = {"年龄"},index=25)
	private Integer age;

	@ExcelProperty(value = {"工龄"},index=26)
	private Integer workYear;

	@ExcelProperty(value = {"职业病危害因素大类名称"},index=27)
	private String dangerBigName;

	@ExcelProperty(value = {"职业病危害因素小类名称"},index=28)
	private String dangerSmallName;

	@ExcelProperty(value = {"疑似职业病大类名称"},index=29)
	private String sickBigName;

	@ExcelProperty(value = {"疑似职业病小类名称"},index=30)
	private String sickSmallName;

}
