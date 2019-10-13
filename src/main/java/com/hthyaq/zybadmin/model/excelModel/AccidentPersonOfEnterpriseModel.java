package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class AccidentPersonOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"职业病危害事故编号"},index=0)
	private String accidentNum;

	@ExcelProperty(value = {"姓名"},index=1)
	private String name;

	@ExcelProperty(value = {"身份证号"},index=2)
	private String idNum;

	@ExcelProperty(value = {"性别"},index=3)
	private String gender;

	@ExcelProperty(value = {"年龄"},index=4)
	private Integer age;

	@ExcelProperty(value = {"是否死亡"},index=5)
	private String isDie;

	@ExcelProperty(value = {"死亡日期"},index=6)
	private Integer dieDate;
    @ExcelProperty(value = {"工作场所"},index=7)
    private String workplaceId;

    @ExcelProperty(value = {"岗位"},index=8)
    private String postId;
}
