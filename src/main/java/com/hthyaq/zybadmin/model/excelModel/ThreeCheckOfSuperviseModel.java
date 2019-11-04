package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ThreeCheckOfSuperviseModel extends BaseRowModel {
	@ExcelProperty(value = {"年份 "},index=0)
	private Integer year;

	@ExcelProperty(value = {"验收方案上报数"},index=1)
	private Integer upCount;

	@ExcelProperty(value = {"职业病危害严重建设项目控制效果评价和防护设施验收工作过程报告数"},index=2)
	private Integer reportCount;

	@ExcelProperty(value = {"检查建设单位数"},index=3)
	private Integer orgCount;

	@ExcelProperty(value = {"下达执法文书数"},index=4)
	private Integer paperCount;

	@ExcelProperty(value = {"给予警告责令限期整改单位数"},index=5)
	private Integer changeCount;

	@ExcelProperty(value = {"责令停止产生职业病危害作业单位数"},index=6)
	private Integer stopCount;

	@ExcelProperty(value = {"提请责令停建或关闭单位数"},index=7)
	private Integer closeCount;

	@ExcelProperty(value = {"罚款建设单位数"},index=8)
	private Integer pulishCount;

	@ExcelProperty(value = {"罚款金额"},index=9)
	private Double pulishMoney;

}
