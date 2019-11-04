package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ZhenduanTotalOfServiceModel extends BaseRowModel {
	@ExcelProperty(value = {"年份"},index=0)
	private Integer year;

	@ExcelProperty(value = {"诊断人数"},index=1)
	private Integer count1;

	@ExcelProperty(value = {"诊断职业病企业数"},index=2)
	private Integer count2;

	@ExcelProperty(value = {"诊断报告数"},index=3)
	private Integer count3;

}
