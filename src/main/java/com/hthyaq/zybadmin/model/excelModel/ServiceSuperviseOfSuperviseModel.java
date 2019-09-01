package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class ServiceSuperviseOfSuperviseModel extends BaseRowModel {
	@ExcelProperty(value = {"年份 "},index=0)
	private Integer year;

	@ExcelProperty(value = {"检测机构的数量"},index=1)
	private Integer jianceCount;

	@ExcelProperty(value = {"体检机构的数量"},index=2)
	private Integer tijianianceount;

	@ExcelProperty(value = {"诊断机构的数量"},index=3)
	private Integer zhenduanCount;

	@ExcelProperty(value = {"检测机构的被处罚数量"},index=4)
	private Integer jiancePunishCount;

	@ExcelProperty(value = {"体检机构的被处罚数量"},index=5)
	private Integer tijianPunishCount;

	@ExcelProperty(value = {"诊断机构的被处罚数量"},index=6)
	private Integer zhenduanPunishCount;

	@ExcelProperty(value = {"检测机构的罚款金额"},index=7)
	private Double jianceMoney;

	@ExcelProperty(value = {"体检机构的罚款金额"},index=8)
	private Double tijianMoney;

	@ExcelProperty(value = {"诊断机构的罚款金额"},index=9)
	private Double zhenduanMoney;

	@ExcelProperty(value = {"检测机构的被吊销资质数量"},index=10)
	private Integer jianceCancelCount;

	@ExcelProperty(value = {"体检机构的被吊销资质数量"},index=11)
	private Integer tijianianceancelCount;

	@ExcelProperty(value = {"诊断机构的被吊销资质数量"},index=12)
	private Integer zhenduanCancelCount;

}
