package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LawOfSuperviseModel extends BaseRowModel {
	@ExcelProperty(value = {"年份 "},index=0)
	private Integer year;

	@ExcelProperty(value = {"印发法律法规的新增"},index=1)
	private Integer ruleIncrease;

	@ExcelProperty(value = {"印发法律法规的累计"},index=2)
	private Integer ruleSum;

	@ExcelProperty(value = {"印发规范性文件的新增"},index=3)
	private Integer fileIncrease;

	@ExcelProperty(value = {"印发规范性文件的累计"},index=4)
	private Integer fileSum;

	@ExcelProperty(value = {"印发标准的新增"},index=5)
	private Integer startdardIncrease;

	@ExcelProperty(value = {"印发标准的累计"},index=6)
	private Integer startdardSum;

}
