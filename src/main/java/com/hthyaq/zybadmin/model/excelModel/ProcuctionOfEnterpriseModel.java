package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProcuctionOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"产品名称"},index=0)
	private String name;

	@ExcelProperty(value = {"产品状态"},index=1)
	private String status;

	@ExcelProperty(value = {"产品年产量"},index=2)
	private Integer yearNumber;

	@ExcelProperty(value = {"产量类型"},index=3)
	private String productionType;

	@ExcelProperty(value = {"中间品名称"},index=4)
	private String middleName;

	@ExcelProperty(value = {"中间品状态"},index=5)
	private String middleStatus;

	@ExcelProperty(value = {"中间品年产量"},index=6)
	private Double middleYearNumber;

	@ExcelProperty(value = {"原辅料名称"},index=7)
	private String materialName;

	@ExcelProperty(value = {"原辅料状态"},index=8)
	private String materialStatus;

	@ExcelProperty(value = {"原辅料年用量"},index=9)
	private Double materialYearNumber;

	@ExcelProperty(value = {"用量类型"},index=10)
	private String materialType;

	@ExcelProperty(value = {"主要生产工艺描述"},index=11)
	private String describe;

	@ExcelProperty(value = {"是否存在职业病危害工艺岗位"},index=12)
	private String isExist;

	@ExcelProperty(value = {"是否优先采用有利于职业病防治和保护劳动者健康的新技术、新工艺和新材料"},index=13)
	private String isFisrt;

	@ExcelProperty(value = {"是否使用国家明令禁止的可能产生职业病危害的设备和材料"},index=14)
	private String isUse;

	@ExcelProperty(value = {"可能产生职业病危害的设备、化学品是否有中文说明书"},index=15)
	private String isHave;

}
