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
 * 定期检测信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("fixCheckOfEnterprise")
public class FixCheckOfEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 检测时间，例如20190101
     */
    @TableField("checkDate")
    private Integer checkDate;

    /**
     * 检测年份，例如2019
     */
    @TableField("checkYear")
    private Integer checkYear;

    /**
     * 检测月份，例如1
     */
    @TableField("checkMonth")
    private Integer checkMonth;

    /**
     * 检测机构
     */
    private String org;

    /**
     * 检测机构的社会统一代码
     */
    private String code;

    /**
     * 检测报告编号
     */
    private String num;

    /**
     * 判定结果,例如合格、不合格
     */
    @TableField("decideResult")
    private String decideResult;

    /**
     * 超标原因
     */
    private String reason;

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
