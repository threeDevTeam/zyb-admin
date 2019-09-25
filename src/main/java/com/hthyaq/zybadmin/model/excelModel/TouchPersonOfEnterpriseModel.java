package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class TouchPersonOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"姓名"},index=0)
	private String name;

	@ExcelProperty(value = {"身份证号"},index=1)
	private String idNum;

	@ExcelProperty(value = {"性别"},index=2)
	private String gender;

	@ExcelProperty(value = {"出生日期"},index=3)
	private Integer birth;

	@ExcelProperty(value = {"上岗时间"},index=4)
	private Integer startDate;

	@ExcelProperty(value = {"离岗时间"},index=5)
	private Integer leaveDate;

	@ExcelProperty(value = {"接害工龄"},index=6)
	private Integer touchYear;

	@ExcelProperty(value = {"是否缴纳工伤保险"},index=7)
	private String isBuy;

	@ExcelProperty(value = {"是否签订劳动合同"},index=8)
	private String isSign;

	@ExcelProperty(value = {"是否参加职业卫生培训"},index=9)
	private String isPractice;

}
