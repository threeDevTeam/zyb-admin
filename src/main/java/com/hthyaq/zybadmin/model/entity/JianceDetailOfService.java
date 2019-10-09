package com.hthyaq.zybadmin.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 检测机构的具体报告

检测机构的具体报告























 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jianceDetailOfService")
public class JianceDetailOfService implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 检测时间 例如：20190101
     */
    @TableField("checkDate")
    private Integer checkDate;

    /**
     * 检测年份 例如：2019
     */
    @TableField("checkYear")
    private Integer checkYear;

    /**
     * 检测月份 例如：1
     */
    @TableField("checkMonth")
    private Integer checkMonth;

    /**
     * 检测报告编号
     */
    private String num;

    /**
     * 企业名称
     */
    @TableField("enterpriseName")
    private String enterpriseName;

    /**
     * 统一社会信用代码 例如：91210200710931264L
     */
    @TableField("enterpriseCode")
    private String enterpriseCode;

    /**
     * 省的名称
     */
    @TableField("provinceName")
    private String provinceName;

    /**
     * 省的代码
     */
    @TableField("provinceCode")
    private String provinceCode;

    /**
     * 市的名称
     */
    @TableField("cityName")
    private String cityName;

    /**
     * 市的代码
     */
    @TableField("cityCode")
    private String cityCode;

    /**
     * 区的名称
     */
    @TableField("districtName")
    private String districtName;

    /**
     * 区的代码
     */
    @TableField("districtCode")
    private String districtCode;


    /**
     * 注册地址
     */
    @TableField("registerAddress")
    private String registerAddress;

    /**
     * 登记注册类型的小类名称
     */
    @TableField("registerSmallName")
    private String registerSmallName;

    /**
     * 所属行业的大类名称
     */
    @TableField("industryBigName")
    private String industryBigName;

    /**
     * 所属行业的小类名称
     */
    @TableField("industrySmallName")
    private String industrySmallName;

    /**
     * 工作场所地址
     */
    @TableField("workAddress")
    private String workAddress;

    /**
     * 工作场所名称
     */
    @TableField("workplaceName")
    private String workplaceName;

    /**
     * 工作场所编码
     */
    @TableField("workplaceCode")
    private Integer workplaceCode;

    /**
     * 岗位的大类名称
     */
    @TableField("postBigName")
    private String postBigName;

    /**
     * 岗位的小类名称
     */
    @TableField("postSmallName")
    private String postSmallName;

    /**
     * 职业病危害因素大类名称
     */
    @TableField("dangerBigName")
    private String dangerBigName;

    /**
     * 职业病危害因素小类名称
     */
    @TableField("dangerSmallName")
    private String dangerSmallName;

    /**
     * 判定结果 例如：合格、不合格
     */
    @TableField("decideResult")
    private String decideResult;

    /**
     * 超标原因
     */
    private String reason;

    /**
     * 外键
     */
    @TableField("jianceBasicId")
    private Long jianceBasicId;


}
