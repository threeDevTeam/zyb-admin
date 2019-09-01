package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class PropagateOfSuperviseModel extends BaseRowModel {
	@ExcelProperty(value = {"年份 "},index=0)
	private Integer year;

	@ExcelProperty(value = {"新闻报道数"},index=1)
	private Integer newsCount;

	@ExcelProperty(value = {"印发宣传材料数"},index=2)
	private Integer paperCount;

	@ExcelProperty(value = {"制作和发放专题宣传片（视频）数"},index=3)
	private Integer videoCount;

	@ExcelProperty(value = {"出动宣传人员数"},index=4)
	private Integer outCount;

	@ExcelProperty(value = {"宣传受众人数"},index=5)
	private Integer acceptCount;

}
