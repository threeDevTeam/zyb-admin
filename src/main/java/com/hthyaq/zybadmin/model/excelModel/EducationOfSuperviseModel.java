package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EducationOfSuperviseModel extends BaseRowModel {
	@ExcelProperty(value = {"年份 "},index=0)
	private Integer year;

	@ExcelProperty(value = {"培训监管人员数"},index=1)
	private Integer markCount;

	@ExcelProperty(value = {"培训用人单位数"},index=2)
	private Integer personCount;

	@ExcelProperty(value = {"培训用人单位主要负责人数"},index=3)
	private Integer mainCount;

	@ExcelProperty(value = {"培训用人单位职业健康管理人员数"},index=4)
	private Integer manageCount;

	@ExcelProperty(value = {"培训接触职业病危害的劳动者数"},index=5)
	private Integer workerCount;

}
