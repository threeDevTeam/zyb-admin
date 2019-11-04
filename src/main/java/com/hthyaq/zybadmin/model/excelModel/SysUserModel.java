package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserModel extends BaseRowModel {
	@ExcelProperty(value = {"登录名"},index=0)
	private String loginName;

	@ExcelProperty(value = {"登录密码"},index=1)
	private String loginPassword;

	@ExcelProperty(value = {"电子邮箱"},index=2)
	private String email;

	@ExcelProperty(value = {"手机号码"},index=3)
	private String mobile;

	@ExcelProperty(value = {"用户类型"},index=4)
	private String type;

}
