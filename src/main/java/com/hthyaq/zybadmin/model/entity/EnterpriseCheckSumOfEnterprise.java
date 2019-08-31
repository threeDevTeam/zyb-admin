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
 * 职业病危害因素检测信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("enterpriseCheckSumOfEnterprise")
public class EnterpriseCheckSumOfEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 检测年份
     */
    private Integer year;

    /**
     * 检测月份
     */
    private Integer month;

    /**
     * 应检点数
     */
    @TableField("shouldNum")
    private Integer shouldNum;

    /**
     * 实检点数
     */
    @TableField("realNum")
    private Integer realNum;

    /**
     * 达标点数
     */
    @TableField("passNum")
    private Integer passNum;

    /**
     * 检测率,例如0.95
     */
    @TableField("checkRate")
    private Double checkRate;

    /**
     * 达标率,例如0.95
     */
    @TableField("passRate")
    private Double passRate;

    /**
     * 是否包含存在的全部职业病危害因素，例如是、否
     */
    @TableField("isInclude")
    private String isInclude;

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
