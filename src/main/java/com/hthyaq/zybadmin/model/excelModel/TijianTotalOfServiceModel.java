package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TijianTotalOfServiceModel extends BaseRowModel {
	@ExcelProperty(value = {"年份"},index=0)
	private Integer year;

	@ExcelProperty(value = {"体检报告数"},index=1)
	private Integer count1;

	@ExcelProperty(value = {"体检人数"},index=2)
	private Integer count2;

	@ExcelProperty(value = {"职业禁忌证人数"},index=3)
	private Integer count3;

	@ExcelProperty(value = {"疑似职业病人数"},index=4)
	private Integer count4;

}
