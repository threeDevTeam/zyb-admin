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
 * 体检机构的具体报告1
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tijianDetail1OfService")
public class TijianDetail1OfService implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 体检时间 例如：20190101
     */
    @TableField("checkDate")
    private Integer checkDate;

    /**
     * 体检年份 例如：2019
     */
    @TableField("checkYear")
    private Integer checkYear;

    /**
     * 体检月份 例如：1
     */
    @TableField("checkMonth")
    private Integer checkMonth;

    /**
     * 体检报告编号
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
     * 性别 例如：男、女
     */
    private String gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 工龄
     */
    @TableField("workYear")
    private Integer workYear;

    /**
     * 体检类别 例如：上岗前、在岗期间、离岗时、应急
     */
    @TableField("tijianType")
    private String tijianType;

    /**
     * 体检结果 例如：复查、目前未见异常、其他疾病、职业禁忌证
     */
    private String result;

    /**
     * 外键
     */
    @TableField("tijianBasicId")
    private Long tijianBasicId;


}
