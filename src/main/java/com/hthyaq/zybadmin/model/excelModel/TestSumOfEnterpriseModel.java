package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class TestSumOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"体检年份"},index=0)
	private Integer year;

	@ExcelProperty(value = {"体检月份"},index=1)
	private Integer month;

	@ExcelProperty(value = {"应检人数"},index=2)
	private Integer shouldNum;

	@ExcelProperty(value = {"实检人数"},index=3)
	private Integer realNum;

	@ExcelProperty(value = {"体检率"},index=4)
	private Double testRate;

	@ExcelProperty(value = {"职业禁忌证人数"},index=5)
	private Integer stopNum;

}
