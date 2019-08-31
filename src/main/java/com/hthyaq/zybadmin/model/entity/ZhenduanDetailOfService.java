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
 * 诊断机构的具体报告
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("zhenduanDetailOfService")
public class ZhenduanDetailOfService implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 诊断时间,例如20190101
     */
    @TableField("checkDate")
    private Integer checkDate;

    /**
     * 诊断年份,例如2019
     */
    @TableField("checkYear")
    private Integer checkYear;

    /**
     * 诊断月份,例如1
     */
    @TableField("checkMonth")
    private Integer checkMonth;

    /**
     * 企业名称
     */
    @TableField("enterpriseName")
    private String enterpriseName;

    /**
     * 统一社会信用代码，例如91210200710931264L
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
     * 登记注册类型的大类名称
     */
    @TableField("registerBigName")
    private String registerBigName;

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
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    @TableField("idNum")
    private String idNum;

    /**
     * 性别，例如男、女
     */
    private String gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 职业病大类名称
     */
    @TableField("sickBigName")
    private String sickBigName;

    /**
     * 职业病小类名称
     */
    @TableField("sickSmallName")
    private String sickSmallName;

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
     * 接害工龄
     */
    @TableField("touchYear")
    private Integer touchYear;

    /**
     * 外键
     */
    @TableField("zhenduanBasicId")
    private Long zhenduanBasicId;


}
