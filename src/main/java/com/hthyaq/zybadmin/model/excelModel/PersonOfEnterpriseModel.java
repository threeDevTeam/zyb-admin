package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PersonOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"姓名"},index=0)
	private String name;

	@ExcelProperty(value = {"身份证号"},index=1)
	private String idNum;

	@ExcelProperty(value = {"性别"},index=2)
	private String gender;

	@ExcelProperty(value = {"年龄"},index=3)
	private Integer age;

    @ExcelProperty(value = {"工作场所"},index=4)
    private String workplaceId;

    @ExcelProperty(value = {"岗位"},index=5)
    private String postId;
}
