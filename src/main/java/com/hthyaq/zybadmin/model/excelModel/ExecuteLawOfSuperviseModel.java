package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class ExecuteLawOfSuperviseModel extends BaseRowModel {
	@ExcelProperty(value = {"年份 "},index=0)
	private Integer year;

	@ExcelProperty(value = {"检查用人单位数"},index=1)
	private Integer personCount;

	@ExcelProperty(value = {"下达执法文书数"},index=2)
	private Integer paperCount;

	@ExcelProperty(value = {"发现问题或隐患数"},index=3)
	private Integer questionCount;

	@ExcelProperty(value = {"责令当场改正数"},index=4)
	private Integer changeCount;

	@ExcelProperty(value = {"责令限期改正数"},index=5)
	private Integer fixCount;

	@ExcelProperty(value = {"罚款用人单位数"},index=6)
	private Integer punishCount;

	@ExcelProperty(value = {"罚款金额"},index=7)
	private Double punishMoney;

	@ExcelProperty(value = {"责令停产整顿用人单位数"},index=8)
	private Integer stopCount;

	@ExcelProperty(value = {"提请关闭用人单位数"},index=9)
	private Integer closeCount;

}
