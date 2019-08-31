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
 * 监管装备信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("equipmentOfSupervise")
public class EquipmentOfSupervise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 装备名称
     */
    private String name;

    /**
     * 规格型号
     */
    private String num;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 购置时间 例如：20190101
     */
    @TableField("buyDate")
    private Integer buyDate;

    /**
     * 装备状态 例如：在用、停用、报废
     */
    private String status;

    /**
     * 外键
     */
    @TableField("superviseId")
    private Long superviseId;


}
