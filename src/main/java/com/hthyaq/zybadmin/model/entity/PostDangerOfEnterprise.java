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
 * 岗位危害信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("postDangerOfEnterprise")
public class PostDangerOfEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 申报时间，例如20190101
     */
    @TableField("upDatee")
    private Integer upDatee;

    /**
     * 申报年份，例如2019
     */
    @TableField("upYear")
    private Integer upYear;

    /**
     * 申报月份，例如1
     */
    @TableField("upMonth")
    private Integer upMonth;

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
     * 可能导致的职业病大类名称
     */
    @TableField("sickBigName")
    private String sickBigName;

    /**
     * 可能导致的职业病小类名称
     */
    @TableField("sickSmallName")
    private String sickSmallName;

    /**
     * 可能引起的急性职业伤害
     */
    private String hurt;

    /**
     * 接害人数
     */
    @TableField("touchNum")
    private Integer touchNum;

    /**
     * 工作时间
     */
    @TableField("workTime")
    private Integer workTime;

    /**
     * 接触时间
     */
    @TableField("touchTime")
    private Integer touchTime;

    /**
     * 接触频次
     */
    @TableField("touchFrequency")
    private Integer touchFrequency;

    /**
     * 作业方式，例如定点作业，巡检作业，手工作业，自动控制
     */
    @TableField("touchMode")
    private String touchMode;

    /**
     * 外键
     */
    @TableField("enterpriseId")
    private Long enterpriseId;

    /**
     * 外键
     */
    @TableField("workplaceId")
    private Long workplaceId;

    /**
     * 外键
     */
    @TableField("postId")
    private Long postId;


}
