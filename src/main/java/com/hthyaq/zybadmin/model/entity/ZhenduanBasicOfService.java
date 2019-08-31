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
 * 诊断机构的基本信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("zhenduanBasicOfService")
public class ZhenduanBasicOfService implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 社会统一代码
     */
    private String code;

    /**
     * 申报年份 例如：2019
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
     * 资质等级 例如：甲级、乙级、丙级
     */
    private String level;

    /**
     * 资质证书编号
     */
    private String num;

    /**
     * 诊断医师数量
     */
    @TableField("doctorNum")
    private Integer doctorNum;

    /**
     * 诊断项目数量
     */
    @TableField("projectCount")
    private Integer projectCount;

    /**
     * 诊断能力 例如：粉尘，化学因素，物理因素，放射性因素，生物因素
     */
    private String scope;

    /**
     * 医院等级 例如：一级、二级、三级
     */
    @TableField("hospitalLevel")
    private String hospitalLevel;


}
