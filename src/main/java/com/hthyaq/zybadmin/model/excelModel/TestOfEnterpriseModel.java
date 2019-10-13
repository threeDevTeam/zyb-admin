package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class TestOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"姓名"},index=0)
	private String name;

	@ExcelProperty(value = {"身份证号"},index=1)
	private String idNum;

	@ExcelProperty(value = {"体检类别"},index=2)
	private String type;

	@ExcelProperty(value = {"体检结果"},index=3)
	private String result;

	@ExcelProperty(value = {"处理意见"},index=4)
	private String note;

	@ExcelProperty(value = {"落实情况"},index=5)
	private String implement;
    @ExcelProperty(value = {"工作场所"},index=6)
    private String workplaceId;

    @ExcelProperty(value = {"岗位"},index=7)
    private String postId;

    @ExcelProperty(value = {"岗位危害信息"},index=8)
    private String postDangerId;
}
