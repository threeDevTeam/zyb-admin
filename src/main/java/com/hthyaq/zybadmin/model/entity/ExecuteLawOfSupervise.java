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
 * 监督执法信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("executeLawOfSupervise")
public class ExecuteLawOfSupervise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 检查用人单位数
     */
    @TableField("personCount")
    private Integer personCount;

    /**
     * 下达执法文书数
     */
    @TableField("paperCount")
    private Integer paperCount;

    /**
     * 发现问题或隐患数
     */
    @TableField("questionCount")
    private Integer questionCount;

    /**
     * 责令当场改正数
     */
    @TableField("changeCount")
    private Integer changeCount;

    /**
     * 责令限期改正数
     */
    @TableField("fixCount")
    private Integer fixCount;

    /**
     * 罚款用人单位数
     */
    @TableField("punishCount")
    private Integer punishCount;

    /**
     * 罚款金额
     */
    @TableField("punishMoney")
    private Double punishMoney;

    /**
     * 责令停产整顿用人单位数
     */
    @TableField("stopCount")
    private Integer stopCount;

    /**
     * 提请关闭用人单位数
     */
    @TableField("closeCount")
    private Integer closeCount;

    /**
     * 外键
     */
    @TableField("superviseId")
    private Long superviseId;


}
