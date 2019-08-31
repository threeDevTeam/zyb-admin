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
 * 检测机构监督信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("serviceSuperviseOfSupervise")
public class ServiceSuperviseOfSupervise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 检测机构的数量
     */
    @TableField("jianceCount")
    private Integer jianceCount;

    /**
     * 体检机构的数量
     */
    private Integer tijianianceount;

    /**
     * 诊断机构的数量
     */
    @TableField("zhenduanCount")
    private Integer zhenduanCount;

    /**
     * 检测机构的被处罚数量
     */
    @TableField("jiancePunishCount")
    private Integer jiancePunishCount;

    /**
     * 体检机构的被处罚数量
     */
    @TableField("tijianPunishCount")
    private Integer tijianPunishCount;

    /**
     * 诊断机构的被处罚数量
     */
    @TableField("zhenduanPunishCount")
    private Integer zhenduanPunishCount;

    /**
     * 检测机构的罚款金额
     */
    @TableField("jianceMoney")
    private Double jianceMoney;

    /**
     * 体检机构的罚款金额
     */
    @TableField("tijianMoney")
    private Double tijianMoney;

    /**
     * 诊断机构的罚款金额
     */
    @TableField("zhenduanMoney")
    private Double zhenduanMoney;

    /**
     * 检测机构的被吊销资质数量
     */
    @TableField("jianceCancelCount")
    private Integer jianceCancelCount;

    /**
     * 体检机构的被吊销资质数量
     */
    @TableField("tijianianceancelCount")
    private Integer tijianianceancelCount;

    /**
     * 诊断机构的被吊销资质数量
     */
    @TableField("zhenduanCancelCount")
    private Integer zhenduanCancelCount;

    /**
     * 外键
     */
    @TableField("superviseId")
    private Long superviseId;


}
