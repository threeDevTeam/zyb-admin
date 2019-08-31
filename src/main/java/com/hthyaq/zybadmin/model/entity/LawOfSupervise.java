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
 * 法规标准建设信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lawOfSupervise")
public class LawOfSupervise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 印发法律法规的新增
     */
    @TableField("ruleIncrease")
    private Integer ruleIncrease;

    /**
     * 印发法律法规的累计
     */
    @TableField("ruleSum")
    private Integer ruleSum;

    /**
     * 印发规范性文件的新增
     */
    @TableField("fileIncrease")
    private Integer fileIncrease;

    /**
     * 印发规范性文件的累计
     */
    @TableField("fileSum")
    private Integer fileSum;

    /**
     * 印发标准的新增
     */
    @TableField("startdardIncrease")
    private Integer startdardIncrease;

    /**
     * 印发标准的累计
     */
    @TableField("startdardSum")
    private Integer startdardSum;

    /**
     * 外键
     */
    @TableField("superviseId")
    private Long superviseId;


}
