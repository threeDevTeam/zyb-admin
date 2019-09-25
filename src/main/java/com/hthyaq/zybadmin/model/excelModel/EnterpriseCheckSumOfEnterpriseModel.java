package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class EnterpriseCheckSumOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"检测年份"},index=0)
	private Integer year;

	@ExcelProperty(value = {"检测月份"},index=1)
	private Integer month;

	@ExcelProperty(value = {"应检点数"},index=2)
	private Integer shouldNum;

	@ExcelProperty(value = {"实检点数"},index=3)
	private Integer realNum;

	@ExcelProperty(value = {"达标点数"},index=4)
	private Integer passNum;

	@ExcelProperty(value = {"检测率"},index=5)
	private Double checkRate;

	@ExcelProperty(value = {"达标率"},index=6)
	private Double passRate;

	@ExcelProperty(value = {"是否包含存在的全部职业病危害因素"},index=7)
	private String isInclude;

}
