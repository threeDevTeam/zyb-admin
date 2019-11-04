package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PersonOfSuperviseModel extends BaseRowModel {
	@ExcelProperty(value = {"姓名"},index=0)
	private String name;

	@ExcelProperty(value = {"性别"},index=1)
	private String gender;

	@ExcelProperty(value = {"身份证号"},index=2)
	private String idNum;

	@ExcelProperty(value = {"出生日期"},index=3)
	private Long birth;

	@ExcelProperty(value = {"职务"},index=4)
	private String job;

	@ExcelProperty(value = {"所学专业"},index=5)
	private String major;

	@ExcelProperty(value = {"是否取得执法资格证书"},index=6)
	private String isGet;

}
