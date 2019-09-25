package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class DiseaseDangerSumOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"接触职业病危害总人数"},index=0)
	private Integer total;

	@ExcelProperty(value = {"接触粉尘人数"},index=1)
	private Integer dust;

	@ExcelProperty(value = {"接触化学因素人数"},index=2)
	private Integer chemistry ;

	@ExcelProperty(value = {"接触物理因素人数"},index=3)
	private Integer physical ;

	@ExcelProperty(value = {"接触放射性因素人数"},index=4)
	private Integer radioactivity;

	@ExcelProperty(value = {"接触生物因素人数"},index=5)
	private Integer biology;

	@ExcelProperty(value = {"年份"},index=6)
	private Integer year;

	@ExcelProperty(value = {"月份"},index=7)
	private Integer month;

}
