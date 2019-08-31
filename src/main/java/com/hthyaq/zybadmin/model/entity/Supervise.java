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
 * 监管部门信息













 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Supervise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 申报年份 例如:2019
     */
    private Integer year;

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
     * 单位名称
     */
    private String name;

    /**
     * 是否独立设置职业健康监管部门 例如：是、否
     */
    @TableField("isSet")
    private String isSet;

    /**
     * 职业健康监管人员编制数
     */
    @TableField("markCount")
    private Integer markCount;

    /**
     * 在岗职业健康监管人员数
     */
    @TableField("manageCount")
    private Integer manageCount;


}
