package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccidentSumOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"职业病危害事故编号"},index=0)
	private String accidentNum;

	@ExcelProperty(value = {"事故发生时间"},index=1)
	private Integer startTime;

	@ExcelProperty(value = {"事故发生地点"},index=2)
	private String place;

	@ExcelProperty(value = {"导致事故的职业病危害因素大类名称"},index=3)
	private String dangerBigName;

	@ExcelProperty(value = {"导致事故的职业病危害因素小类名称"},index=4)
	private String dangerSmallName;

	@ExcelProperty(value = {"发病人数"},index=5)
	private Integer sickCount;

	@ExcelProperty(value = {"送医院治疗人数"},index=6)
	private Integer treatCount;

	@ExcelProperty(value = {"死亡人数"},index=7)
	private Integer dieCount;

	@ExcelProperty(value = {"直接经济损失"},index=8)
	private Double directLose;

	@ExcelProperty(value = {"间接经济损失"},index=9)
	private Double indirectLose;

	@ExcelProperty(value = {"事故原因"},index=10)
	private String reason;

	@ExcelProperty(value = {"事故经过"},index=11)
	private String process;

	@ExcelProperty(value = {"是否向有关部门报告"},index=12)
	private String isReport;

}
