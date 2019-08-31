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
 * 日常监测信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("monitorOfEnterprise")
public class MonitorOfEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 监测周期,例如秒、分、时、天、周
     */
    private String cycle;

    /**
     * 监测时间，例如11111111
     */
    @TableField("monitorTime")
    private Long monitorTime;

    /**
     * 监测结果，例如0.99
     */
    @TableField("monitorResult")
    private Double monitorResult;

    /**
     * 单位,例如mg/m3、kV、…
     */
    private String unit;

    /**
     * 判定结果，例如合格、不合格
     */
    @TableField("decideResult")
    private String decideResult;

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

    /**
     * 外键
     */
    @TableField("postDangerId")
    private Long postDangerId;


}
