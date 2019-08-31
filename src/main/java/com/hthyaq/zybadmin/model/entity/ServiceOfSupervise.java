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
 * 检测机构信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("serviceOfSupervise")
public class ServiceOfSupervise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 检测机构的资质等级 例如：甲级、乙级、丙级
     */
    @TableField("jianceLevel")
    private String jianceLevel;

    /**
     * 检测机构的新增
     */
    @TableField("jianceIncrease")
    private Integer jianceIncrease;

    /**
     * 检测机构的累计
     */
    @TableField("jianceSum")
    private Integer jianceSum;

    /**
     * 体检机构的新增
     */
    @TableField("tijianIncrease")
    private Integer tijianIncrease;

    /**
     * 体检机构的累计
     */
    @TableField("tijianSum")
    private Integer tijianSum;

    /**
     * 诊断机构的新增
     */
    @TableField("zhenduanIncrease")
    private Integer zhenduanIncrease;

    /**
     * 诊断机构的累计
     */
    @TableField("zhenduanSum")
    private Integer zhenduanSum;

    /**
     * 外键
     */
    @TableField("superviseId")
    private Long superviseId;


}
