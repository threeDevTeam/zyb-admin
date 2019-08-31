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
 * 教育培训情况
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("educationOfSupervise")
public class EducationOfSupervise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 培训监管人员数
     */
    @TableField("markCount")
    private Integer markCount;

    /**
     * 培训用人单位数
     */
    @TableField("personCount")
    private Integer personCount;

    /**
     * 培训用人单位主要负责人数
     */
    @TableField("mainCount")
    private Integer mainCount;

    /**
     * 培训用人单位职业健康管理人员数
     */
    @TableField("manageCount")
    private Integer manageCount;

    /**
     * 培训接触职业病危害的劳动者数
     */
    @TableField("workerCount")
    private Integer workerCount;

    /**
     * 外键
     */
    @TableField("superviseId")
    private Long superviseId;


}
