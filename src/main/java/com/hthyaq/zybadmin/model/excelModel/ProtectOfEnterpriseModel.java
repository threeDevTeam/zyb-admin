package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProtectOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"是否设置防护设施"},index=0)
	private String isSet;

	@ExcelProperty(value = {"防护设施名称"},index=1)
	private String name;

	@ExcelProperty(value = {"防护设施类别"},index=2)
	private String type;

	@ExcelProperty(value = {"运行状态"},index=3)
	private String status;

	@ExcelProperty(value = {"是否定期进行维护检修保养"},index=4)
	private String isFix;

	@ExcelProperty(value = {"工程防护效果"},index=5)
	private String protectEffect;
    @ExcelProperty(value = {"工作场所"},index=6)
    private String workplaceId;

    @ExcelProperty(value = {"岗位"},index=7)
    private String postId;

    @ExcelProperty(value = {"岗位危害信息"},index=8)
    private String postDangerId;
}
