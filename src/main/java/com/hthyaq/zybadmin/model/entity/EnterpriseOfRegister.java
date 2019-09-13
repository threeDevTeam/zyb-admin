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
 * 企业的注册表
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("enterpriseOfRegister")
public class EnterpriseOfRegister implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 企业名称
     */
    private String name;

    /**
     * 统一社会信用代码
     */
    private String code;

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
     * productionCapacity
     */
    @TableField("productionCapacity")
    private String productionCapacity;

    /**
     * 生产能力单位类型
     */
    @TableField("unitType")
    private String unitType;

    /**
     * regiterMoney
     */
    @TableField("regiterMoney")
    private String regiterMoney;

    /**
     * registerAddress
     */
    @TableField("registerAddress")
    private String registerAddress;

    /**
     * 注册时间
     */
    @TableField("registerDate")
    private String registerDate;

    /**
     * startDate
     */
    @TableField("startDate")
    private String startDate;

    /**
     * 资产总额
     */
    @TableField("propertyMoney")
    private String propertyMoney;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;


}
