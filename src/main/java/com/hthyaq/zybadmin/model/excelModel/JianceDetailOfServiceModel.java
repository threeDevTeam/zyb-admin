package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class JianceDetailOfServiceModel extends BaseRowModel {
	@ExcelProperty(value = {"检测时间"},index=0)
	private Integer checkDate;

	@ExcelProperty(value = {"检测年份"},index=1)
	private Integer checkYear;

	@ExcelProperty(value = {"检测月份"},index=2)
	private Integer checkMonth;

	@ExcelProperty(value = {"检测报告编号"},index=3)
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

	@ExcelProperty(value = {"登记注册类型的小类名称"},index=13)
	private String registerSmallName;

	@ExcelProperty(value = {"所属行业的大类名称"},index=14)
	private String industryBigName;

	@ExcelProperty(value = {"所属行业的小类名称"},index=15)
	private String industrySmallName;

	@ExcelProperty(value = {"工作场所地址"},index=16)
	private String workAddress;

	@ExcelProperty(value = {"工作场所名称"},index=17)
	private String workplaceName;

	@ExcelProperty(value = {"工作场所编码"},index=18)
	private Integer workplaceCode;

	@ExcelProperty(value = {"岗位的大类名称"},index=19)
	private String postBigName;

	@ExcelProperty(value = {"岗位的小类名称"},index=20)
	private Integer postSmallName;

	@ExcelProperty(value = {"职业病危害因素大类名称"},index=21)
	private String dangerBigName;

	@ExcelProperty(value = {"职业病危害因素小类名称"},index=22)
	private String dangerSmallName;

	@ExcelProperty(value = {"判定结果"},index=23)
	private String decideResult;

	@ExcelProperty(value = {"超标原因"},index=24)
	private String reason;

	@ExcelProperty(value = {"体检时间"},index=25)
	private Integer checkDate;

	@ExcelProperty(value = {"体检年份"},index=26)
	private Integer checkYear;

	@ExcelProperty(value = {"体检月份"},index=27)
	private Integer checkMonth;

	@ExcelProperty(value = {"体检报告编号"},index=28)
	private String num;

	@ExcelProperty(value = {"企业名称"},index=29)
	private String enterpriseName;

	@ExcelProperty(value = {"统一社会信用代码"},index=30)
	private String enterpriseCode;

	@ExcelProperty(value = {"省的名称"},index=31)
	private String provinceName;

	@ExcelProperty(value = {"省的代码"},index=32)
	private String provinceCode;

	@ExcelProperty(value = {"市的名称"},index=33)
	private String cityName;

	@ExcelProperty(value = {"市的代码"},index=34)
	private String cityCode;

	@ExcelProperty(value = {"区的名称"},index=35)
	private String districtName;

	@ExcelProperty(value = {"区的代码"},index=36)
	private String districtCode;

	@ExcelProperty(value = {"注册地址"},index=37)
	private String registerAddress;

	@ExcelProperty(value = {"登记注册类型的大类名称"},index=38)
	private String registerBigName;

	@ExcelProperty(value = {"登记注册类型的小类名称"},index=39)
	private String registerSmallName;

	@ExcelProperty(value = {"所属行业的大类名称"},index=40)
	private String industryBigName;

	@ExcelProperty(value = {"所属行业的小类名称"},index=41)
	private String industrySmallName;

	@ExcelProperty(value = {"工作场所地址"},index=42)
	private String workAddress;

	@ExcelProperty(value = {"工作场所名称"},index=43)
	private String workplaceName;

	@ExcelProperty(value = {"工作场所编码"},index=44)
	private Integer workplaceCode;

	@ExcelProperty(value = {"岗位的大类名称"},index=45)
	private String postBigName;

	@ExcelProperty(value = {"岗位的小类名称"},index=46)
	private String postSmallName;

	@ExcelProperty(value = {"姓名"},index=47)
	private String name;

	@ExcelProperty(value = {"身份证号"},index=48)
	private String idNum;

	@ExcelProperty(value = {"性别"},index=49)
	private String gender;

	@ExcelProperty(value = {"年龄"},index=50)
	private Integer age;

	@ExcelProperty(value = {"工龄"},index=51)
	private Integer workYear;

	@ExcelProperty(value = {"体检类别"},index=52)
	private String tijianType;

	@ExcelProperty(value = {"体检结果"},index=53)
	private String result;

}
