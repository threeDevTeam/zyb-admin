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
@TableName("accidentSumOfEnterprise")
public class AccidentSumOfEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 职业病危害事故编号
     */
    @TableField("accidentNum")
    private String accidentNum;

    /**
     * 事故发生时间，例如11111111111
     */
    @TableField("startTime")
    private Integer startTime;

    /**
     * 事故发生地点
     */
    private String place;

    /**
     * 导致事故的职业病危害因素大类名称
     */
    @TableField("dangerBigName")
    private String dangerBigName;

    /**
     * 导致事故的职业病危害因素小类名称
     */
    @TableField("dangerSmallName")
    private String dangerSmallName;

    /**
     * 发病人数
     */
    @TableField("sickCount")
    private Integer sickCount;

    /**
     * 送医院治疗人数
     */
    @TableField("treatCount")
    private Integer treatCount;

    /**
     * 死亡人数
     */
    @TableField("dieCount")
    private Integer dieCount;

    /**
     * 直接经济损失
     */
    @TableField("directLose")
    private Double directLose;

    /**
     * 间接经济损失
     */
    @TableField("indirectLose")
    private Double indirectLose;

    /**
     * 事故原因
     */
    private String reason;

    /**
     * 事故经过
     */
    private String process;

    /**
     * 是否向有关部门报告，例如是、否
     */
    @TableField("isReport")
    private String isReport;

    /**
     * 外键
     */
    @TableField("enterpriseId")
    private Long enterpriseId;


}
