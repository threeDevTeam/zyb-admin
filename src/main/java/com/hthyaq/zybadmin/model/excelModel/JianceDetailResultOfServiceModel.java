package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class JianceDetailResultOfServiceModel extends BaseRowModel {
	@ExcelProperty(value = {"检测结果"},index=0)
	private Double checkResult;

	@ExcelProperty(value = {"类别"},index=1)
	private String type;

	@ExcelProperty(value = {"单位"},index=2)
	private String unit;

}
