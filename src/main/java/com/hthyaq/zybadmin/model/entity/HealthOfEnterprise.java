package com.hthyaq.zybadmin.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 职业卫生管理信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("healthOfEnterprise")
public class HealthOfEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 是否制定职业病防治计划和实施方案，例如是、否
     */
    @TableField("isA")
    private String isA;

    /**
     * 是否建立职业病防治责任制度，例如是、否
     */
    @TableField("isB")
    private String isB;

    /**
     * 是否建立职业病危害警示与告知制度，例如是、否
     */
    @TableField("isC")
    private String isC;

    /**
     * 是否建立职业病危害项目申报制度，例如是、否
     */
    @TableField("isD")
    private String isD;

    /**
     * 是否建立职业病防治宣传教育培训制度，例如是、否
     */
    @TableField("isE")
    private String isE;

    /**
     * 是否建立职业病防护设施维护检修制度，例如是、否
     */
    @TableField("isF")
    private String isF;

    /**
     * 是否建立职业病防护用品管理制度，例如是、否
     */
    @TableField("isG")
    private String isG;

    /**
     * 是否建立职业病危害监测及评价管理制度，例如是、否
     */
    @TableField("isH")
    private String isH;

    /**
     * 是否建立建设项目职业病防护设施“三同时”管理制度，例如是、否
     */
    @TableField("isI")
    private String isI;

    /**
     * 是否建立劳动者职业健康监护及其档案管理制度，例如是、否
     */
    @TableField("isJ")
    private String isJ;

    /**
     * 是否建立职业病危害事故处置与报告制度，例如是、否
     */
    @TableField("isK")
    private String isK;

    /**
     * 是否建立职业病危害应急救援与管理制度，例如是、否
     */
    @TableField("isL")
    private String isL;

    /**
     * 是否建立岗位职业卫生操作规程，例如是、否
     */
    @TableField("isM")
    private String isM;

    /**
     * 是否设置或指定职业卫生管理机构，例如是、否
     */
    @TableField("isN")
    private String isN;

    /**
     * 是否配备了专职或兼职职业卫生管理人员，例如是、否
     */
    @TableField("isO")
    private String isO;

    /**
     * 单位负责人是否培训合格，例如是、否
     */
    @TableField("isP")
    private String isP;

    /**
     * 职业卫生管理人员是否培训合格，例如是、否
     */
    @TableField("isQ")
    private String isQ;

    /**
     * 接触职业病危害员工是否培训合格，例如是、否
     */
    @TableField("isR")
    private String isR;

    /**
     * 是否建立健全职业卫生档案，例如是、否
     */
    @TableField("isS")
    private String isS;

    /**
     * 是否进行了职业病危害项目申报，例如是、否
     */
    @TableField("isT")
    private String isT;

    /**
     * 是否落实了建设项目职业病防护设施“三同时”，例如是、否
     */
    @TableField("isU")
    private String isU;

    /**
     * 是否在醒目位置设置公告栏公布职业病防治相关信息，例如是、否
     */
    @TableField("isV")
    private String isV;

    /**
     * 是否在存在职业病危害作业场所、岗位、设备的醒目位置设置了警示标识，例如是、否
     */
    @TableField("isW")
    private String isW;

    /**
     * 是否实施了职业病危害因素日常监测，例如是、否
     */
    @TableField("isX")
    private String isX;

    /**
     * 是否实施工作场所职业病危害因素定期检测，例如是、否
     */
    @TableField("isY")
    private String isY;

    /**
     * 是否与劳动者签订合同并进行危害告知，例如是、否
     */
    @TableField("isZ")
    private String isZ;

    /**
     * 外键
     */
    @TableField("enterpriseId")
    private Long enterpriseId;


}
