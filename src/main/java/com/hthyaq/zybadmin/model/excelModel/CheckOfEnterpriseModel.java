package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CheckOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"年份"},index=0)
	private Integer year;

	@ExcelProperty(value = {"是否接受过相关部门检查"},index=1)
	private String isAccept;

	@ExcelProperty(value = {"检查时间"},index=2)
	private Integer checkDate;

	@ExcelProperty(value = {"检查部门"},index=3)
	private String org;

	@ExcelProperty(value = {"检查内容"},index=4)
	private String content;

	@ExcelProperty(value = {"发现问题"},index=5)
	private String question;

	@ExcelProperty(value = {"是否被行政处罚"},index=6)
	private String isPunish;

	@ExcelProperty(value = {"行政处罚类别"},index=7)
	private String type;

	@ExcelProperty(value = {"罚款金额"},index=8)
	private Double money;

	@ExcelProperty(value = {"是否落实整改"},index=9)
	private String isChange;

}
