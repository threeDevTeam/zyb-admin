package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class PersonProtectOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"是否配备个人防护用品"},index=0)
	private String isSet;

	@ExcelProperty(value = {"个人防护用品名称"},index=1)
	private String name;

	@ExcelProperty(value = {"防护用品型号"},index=2)
	private String modelNum;

	@ExcelProperty(value = {"发放数量"},index=3)
	private String count;

	@ExcelProperty(value = {"发放周期"},index=4)
	private Integer cycle;

	@ExcelProperty(value = {"劳动者是否正确佩戴使用防护用品"},index=5)
	private String isCorrect;

	@ExcelProperty(value = {"是否定期更换个人防护用品"},index=6)
	private String isReplace;
    @ExcelProperty(value = {"工作场所"},index=7)
    private String workplaceId;

    @ExcelProperty(value = {"岗位"},index=8)
    private String postId;

    @ExcelProperty(value = {"岗位危害信息"},index=9)
    private String postDangerId;
}

