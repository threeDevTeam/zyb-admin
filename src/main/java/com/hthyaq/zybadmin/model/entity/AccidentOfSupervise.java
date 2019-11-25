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
 * 职业病危害事故信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("accidentOfSupervise")
public class AccidentOfSupervise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 尘肺病事故数
     */
    @TableField("dustCount")
    private Integer dustCount;

    /**
     * 中毒事故数
     */
    @TableField("poisonCount")
    private Integer poisonCount;

    /**
     * 其它事故数
     */
    @TableField("otherCount")
    private Integer otherCount;

    /**
     * 尘肺病事故人数
     */
    @TableField("dustPersonCount")
    private Integer dustPersonCount;

    /**
     * 中毒事故人数
     */
    @TableField("poisonPersonCount")
    private Integer poisonPersonCount;

    /**
     * 其它事故人数
     */
    @TableField("otherPersonCount")
    private Integer otherPersonCount;

    /**
     * 尘肺病事故死亡人数
     */
    @TableField("dustDieCount")
    private Integer dustDieCount;

    /**
     * 中毒事故死亡人数
     */
    @TableField("poisonDieCount")
    private Integer poisonDieCount;

    /**
     * 其它事故死亡人数
     */
    @TableField("otherDieCount")
    private Integer otherDieCount;

    /**
     * 直接经济损失
     */
    @TableField("loseMoney")
    private Double loseMoney;

    /**
     * 外键
     */
    @TableField("superviseId")
    private Long superviseId;


}
