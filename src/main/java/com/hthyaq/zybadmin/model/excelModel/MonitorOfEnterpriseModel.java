package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class MonitorOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"监测周期"},index=0)
	private String cycle;

	@ExcelProperty(value = {"监测时间"},index=1)
	private Long monitorTime;

	@ExcelProperty(value = {"监测结果"},index=2)
	private Double monitorResult;

	@ExcelProperty(value = {"单位"},index=3)
	private String unit;

	@ExcelProperty(value = {"判定结果"},index=4)
	private String decideResult;

}
