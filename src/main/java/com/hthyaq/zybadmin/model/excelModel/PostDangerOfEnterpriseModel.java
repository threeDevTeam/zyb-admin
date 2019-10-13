package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class PostDangerOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"申报时间"},index=0)
	private Integer upDatee;

	@ExcelProperty(value = {"申报年份"},index=1)
	private Integer upYear;

	@ExcelProperty(value = {"申报月份"},index=2)
	private Integer upMonth;

	@ExcelProperty(value = {"职业病危害因素大类名称"},index=3)
	private String dangerBigName;

	@ExcelProperty(value = {"职业病危害因素小类名称"},index=4)
	private String dangerSmallName;

	@ExcelProperty(value = {"可能导致的职业病大类名称"},index=5)
	private String sickBigName;

	@ExcelProperty(value = {"可能导致的职业病小类名称"},index=6)
	private String sickSmallName;

	@ExcelProperty(value = {"可能引起的急性职业伤害"},index=7)
	private String hurt;

	@ExcelProperty(value = {"接害人数"},index=8)
	private Integer touchNum;

	@ExcelProperty(value = {"工作时间"},index=9)
	private Integer workTime;

	@ExcelProperty(value = {"接触时间"},index=10)
	private Integer touchTime;

	@ExcelProperty(value = {"接触频次"},index=11)
	private Integer touchFrequency;

	@ExcelProperty(value = {"作业方式"},index=12)
	private String touchMode;

    @ExcelProperty(value = {"工作场所"},index=13)
    private String workplaceId;

    @ExcelProperty(value = {"岗位"},index=14)
    private String postId;
}
