package com.hthyaq.zybadmin.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HealthOfEnterpriseModel extends BaseRowModel {
	@ExcelProperty(value = {"是否制定职业病防治计划和实施方案"},index=0)
	private String isA;

	@ExcelProperty(value = {"是否建立职业病防治责任制度"},index=1)
	private String isB;

	@ExcelProperty(value = {"是否建立职业病危害警示与告知制度"},index=2)
	private String isC;

	@ExcelProperty(value = {"是否建立职业病危害项目申报制度"},index=3)
	private String isD;

	@ExcelProperty(value = {"是否建立职业病防治宣传教育培训制度"},index=4)
	private String isE;

	@ExcelProperty(value = {"是否建立职业病防护设施维护检修制度"},index=5)
	private String isF;

	@ExcelProperty(value = {"是否建立职业病防护用品管理制度"},index=6)
	private String isG;

	@ExcelProperty(value = {"是否建立职业病危害监测及评价管理制度"},index=7)
	private String isH;

	@ExcelProperty(value = {"是否建立建设项目职业病防护设施“三同时”管理制度"},index=8)
	private String isI;

	@ExcelProperty(value = {"是否建立劳动者职业健康监护及其档案管理制度"},index=9)
	private String isJ;

	@ExcelProperty(value = {"是否建立职业病危害事故处置与报告制度"},index=10)
	private String isK;

	@ExcelProperty(value = {"是否建立职业病危害应急救援与管理制度"},index=11)
	private String isL;

	@ExcelProperty(value = {"是否建立岗位职业卫生操作规程"},index=12)
	private String isM;

	@ExcelProperty(value = {"是否设置或指定职业卫生管理机构"},index=13)
	private String isN;

	@ExcelProperty(value = {"是否配备了专职或兼职职业卫生管理人员"},index=14)
	private String isO;

	@ExcelProperty(value = {"单位负责人是否培训合格"},index=15)
	private String isP;

	@ExcelProperty(value = {"职业卫生管理人员是否培训合格"},index=16)
	private String isQ;

	@ExcelProperty(value = {"接触职业病危害员工是否培训合格"},index=17)
	private String isR;

	@ExcelProperty(value = {"是否建立健全职业卫生档案"},index=18)
	private String isS;

	@ExcelProperty(value = {"是否进行了职业病危害项目申报"},index=19)
	private String isT;

	@ExcelProperty(value = {"是否落实了建设项目职业病防护设施“三同时”"},index=20)
	private String isU;

	@ExcelProperty(value = {"是否在醒目位置设置公告栏公布职业病防治相关信息"},index=21)
	private String isV;

	@ExcelProperty(value = {"是否在存在职业病危害作业场所、岗位、设备的醒目位置设置了警示标识"},index=22)
	private String isW;

	@ExcelProperty(value = {"是否实施了职业病危害因素日常监测"},index=23)
	private String isX;

	@ExcelProperty(value = {"是否实施工作场所职业病危害因素定期检测"},index=24)
	private String isY;

	@ExcelProperty(value = {"是否与劳动者签订合同并进行危害告知"},index=25)
	private String isZ;

}
