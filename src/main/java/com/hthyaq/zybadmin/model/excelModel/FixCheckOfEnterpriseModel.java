package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class FixCheckOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"检测时间"},index=0)
	private Integer checkDate;

	@ExcelProperty(value = {"检测年份"},index=1)
	private Integer checkYear;

	@ExcelProperty(value = {"检测月份"},index=2)
	private Integer checkMonth;

	@ExcelProperty(value = {"检测机构"},index=3)
	private String org;

	@ExcelProperty(value = {"检测机构的社会统一代码"},index=4)
	private String code;

	@ExcelProperty(value = {"检测报告编号"},index=5)
	private String num;

	@ExcelProperty(value = {"判定结果"},index=6)
	private String decideResult;

	@ExcelProperty(value = {"超标原因"},index=7)
	private String reason;

	@ExcelProperty(value = {"危害程度级别"},index=8)
	private String dangerLevel;
    @ExcelProperty(value = {"工作场所"},index=9)
    private String workplaceId;

    @ExcelProperty(value = {"岗位"},index=10)
    private String postId;

    @ExcelProperty(value = {"岗位危害信息"},index=11)
    private String postDangerId;
}
