package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class JianceTotalOfServiceModel extends BaseRowModel {
	@ExcelProperty(value = {"年份"},index=0)
	private Integer year;

	@ExcelProperty(value = {"职业病危害预评价报告数"},index=1)
	private String count1;

	@ExcelProperty(value = {"控制效果评价报告数"},index=2)
	private String count2;

	@ExcelProperty(value = {"现状评价报告数"},index=3)
	private String count3;

	@ExcelProperty(value = {"检测报告数"},index=4)
	private String count4;

	@ExcelProperty(value = {"检测点数"},index=5)
	private String count5;

	@ExcelProperty(value = {"达标点数"},index=6)
	private String count6;

}
