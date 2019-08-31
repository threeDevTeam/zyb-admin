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
 * “三同时”监督检查信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("threeCheckOfSupervise")
public class ThreeCheckOfSupervise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 验收方案上报数
     */
    @TableField("upCount")
    private Integer upCount;

    /**
     * 职业病危害严重建设项目控制效果评价和防护设施验收工作过程报告数
     */
    @TableField("reportCount")
    private Integer reportCount;

    /**
     * 检查建设单位数
     */
    @TableField("orgCount")
    private Integer orgCount;

    /**
     * 下达执法文书数
     */
    @TableField("paperCount")
    private Integer paperCount;

    /**
     * 给予警告责令限期整改单位数
     */
    @TableField("changeCount")
    private Integer changeCount;

    /**
     * 责令停止产生职业病危害作业单位数
     */
    @TableField("stopCount")
    private Integer stopCount;

    /**
     * 提请责令停建或关闭单位数
     */
    @TableField("closeCount")
    private Integer closeCount;

    /**
     * 罚款建设单位数
     */
    @TableField("pulishCount")
    private Integer pulishCount;

    /**
     * 罚款金额
     */
    @TableField("pulishMoney")
    private Double pulishMoney;

    /**
     * 外键
     */
    @TableField("superviseId")
    private Long superviseId;


}
