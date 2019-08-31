package com.hthyaq.zybadmin.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 生产工艺信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Enterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 企业名称
     */
    private String name;

    /**
     * 统一社会信用代码，例如91210200710931264L
     */
    private String code;

    /**
     * 风险等级，例如 I级、Ⅱ级、Ⅲ级
     */
    @TableField("riskLevel")
    private String riskLevel;

    /**
     * 申报年份，例如2019
     */
    private Integer year;

    /**
     * 企业规模，例如大型、小型、中型、微型
     */
    private String size;

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
     * workAddress
     */
    @TableField("workAddress")
    private String workAddress;

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
     * 核定生产能力
     */
    @TableField("productionCapacity")
    private Integer productionCapacity;

    /**
     * 生产能力单位类型，例如万件、万吨、万立方米、万千瓦时
     */
    @TableField("unitType")
    private String unitType;

    /**
     * 注册资本，例如10000000.99
     */
    @TableField("regiterMoney")
    private Double regiterMoney;

    /**
     * 注册时间，例如20190808
     */
    @TableField("registerDate")
    private Integer registerDate;

    /**
     * 投产时间，例如20190808
     */
    @TableField("startDate")
    private Integer startDate;

    /**
     * 资产总额，例如10000000.99
     */
    @TableField("propertyMoney")
    private Double propertyMoney;

    /**
     * 营业收入，例如10000000.99
     */
    @TableField("saleMoney")
    private Double saleMoney;

    /**
     * 从业人数
     */
    @TableField("workerNumber")
    private Integer workerNumber;

    /**
     * 从业人数中的女工数
     */
    @TableField("womenWorkerNumber")
    private Integer womenWorkerNumber;

    /**
     * 劳务派遣的女工数
     */
    @TableField("outNumber")
    private Integer outNumber;

    /**
     * 劳务派遣的女工数
     */
    @TableField("outWomenNumber")
    private Integer outWomenNumber;


}
