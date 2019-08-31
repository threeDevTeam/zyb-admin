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
 * 职业健康宣传信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("propagateOfSupervise")
public class PropagateOfSupervise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 新闻报道数
     */
    @TableField("newsCount")
    private Integer newsCount;

    /**
     * 印发宣传材料数
     */
    @TableField("paperCount")
    private Integer paperCount;

    /**
     * 制作和发放专题宣传片（视频）数
     */
    @TableField("videoCount")
    private Integer videoCount;

    /**
     * 出动宣传人员数
     */
    @TableField("outCount")
    private Integer outCount;

    /**
     * 宣传受众人数
     */
    @TableField("acceptCount")
    private Integer acceptCount;

    /**
     * 外键
     */
    @TableField("superviseId")
    private Long superviseId;


}
