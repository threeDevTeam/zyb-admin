package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class EnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"企业名称"},index=0)
	private String name;

	@ExcelProperty(value = {"统一社会信用代码"},index=1)
	private String code;

	@ExcelProperty(value = {"风险等级"},index=2)
	private String riskLevel;

	@ExcelProperty(value = {"申报年份"},index=3)
	private Integer year;

	@ExcelProperty(value = {"企业规模"},index=4)
	private String size;

	@ExcelProperty(value = {"省的名称"},index=5)
	private String provinceName;

	@ExcelProperty(value = {"省的代码"},index=6)
	private String provinceCode;

	@ExcelProperty(value = {"市的名称"},index=7)
	private String cityName;

	@ExcelProperty(value = {"市的代码"},index=8)
	private String cityCode;

	@ExcelProperty(value = {"区的名称"},index=9)
	private String districtName;

	@ExcelProperty(value = {"区的代码"},index=10)
	private String districtCode;

	@ExcelProperty(value = {"工作场所地址"},index=11)
	private String workAddress;

	@ExcelProperty(value = {"登记注册类型的大类名称"},index=12)
	private String registerBigName;

	@ExcelProperty(value = {"登记注册类型的小类名称"},index=13)
	private String registerSmallName;

	@ExcelProperty(value = {"所属行业的大类名称"},index=14)
	private String industryBigName;

	@ExcelProperty(value = {"所属行业的小类名称"},index=15)
	private String industrySmallName;

	@ExcelProperty(value = {"核定生产能力"},index=16)
	private Integer productionCapacity;

	@ExcelProperty(value = {"生产能力单位类型"},index=17)
	private String unitType;

	@ExcelProperty(value = {"注册资本"},index=18)
	private Double regiterMoney;

	@ExcelProperty(value = {"注册地址"},index=19)
	private String registerAddress;

	@ExcelProperty(value = {"注册时间"},index=20)
	private Integer registerDate;

	@ExcelProperty(value = {"投产时间"},index=21)
	private Integer startDate;

	@ExcelProperty(value = {"资产总额"},index=22)
	private Double propertyMoney;

	@ExcelProperty(value = {"营业收入"},index=23)
	private Double saleMoney;

	@ExcelProperty(value = {"从业人数"},index=24)
	private Integer workerNumber;

	@ExcelProperty(value = {"从业人数中的女工数"},index=25)
	private Integer womenWorkerNumber;

	@ExcelProperty(value = {"劳务派遣用工人数"},index=26)
	private Integer outNumber;

	@ExcelProperty(value = {"劳务派遣的女工数"},index=27)
	private Integer outWomenNumber;

}
