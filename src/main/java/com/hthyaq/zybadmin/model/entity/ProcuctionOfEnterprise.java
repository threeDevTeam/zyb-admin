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
 * 生产工艺信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("procuctionOfEnterprise")
public class ProcuctionOfEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品状态，例如固态、液态、气态、其他
     */
    private String status;

    /**
     * 产品年产量，例如参考 核定生产能力
     */
    @TableField("yearNumber")
    private Integer yearNumber;

    /**
     * 产量类型，例如参考 生产能力单位类型
     */
    @TableField("productionType")
    private String productionType;

    /**
     * 中间品名称
     */
    @TableField("middleName")
    private String middleName;

    /**
     * 中间品状态
     */
    @TableField("middleStatus")
    private String middleStatus;

    /**
     * 中间品年产量
     */
    @TableField("middleYearNumber")
    private Double middleYearNumber;

    /**
     * 原辅料名称
     */
    @TableField("materialName")
    private String materialName;

    /**
     * 原辅料状态
     */
    @TableField("materialStatus")
    private String materialStatus;

    /**
     * 原辅料年用量
     */
    @TableField("materialYearNumber")
    private Double materialYearNumber;

    /**
     * 用量类型
     */
    @TableField("materialType")
    private String materialType;

    /**
     * 主要生产工艺描述
     */
    private String describee;

    /**
     * 是否存在职业病危害工艺岗位,例如是、否
     */
    @TableField("isExist")
    private String isExist;

    /**
     * 是否优先采用有利于职业病防治和保护劳动者健康的新技术、新工艺和新材料
     */
    @TableField("isFisrt")
    private String isFisrt;

    /**
     * 是否使用国家明令禁止的可能产生职业病危害的设备和材料
     */
    @TableField("isUse")
    private String isUse;

    /**
     * 可能产生职业病危害的设备、化学品是否有中文说明书
     */
    @TableField("isHave")
    private String isHave;

    /**
     * 外键
     */
    @TableField("enterpriseId")
    private Long enterpriseId;


}
