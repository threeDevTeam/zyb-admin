package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EquipmentOfSuperviseModel extends BaseRowModel {
	@ExcelProperty(value = {"装备名称"},index=0)
	private String name;

	@ExcelProperty(value = {"规格型号"},index=1)
	private String num;

	@ExcelProperty(value = {"数量"},index=2)
	private Integer amount;

	@ExcelProperty(value = {"购置时间"},index=3)
	private Integer buyDate;

	@ExcelProperty(value = {"装备状态"},index=4)
	private String status;

}
