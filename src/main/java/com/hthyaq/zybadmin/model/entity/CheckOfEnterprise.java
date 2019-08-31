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
 * 监督检查信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("checkOfEnterprise")
public class CheckOfEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 是否接受过相关部门检查，例如是、否
     */
    @TableField("isAccept")
    private String isAccept;

    /**
     * 检查时间，例如20190101
     */
    @TableField("checkDate")
    private Integer checkDate;

    /**
     * 检查部门
     */
    private String org;

    /**
     * 检查内容
     */
    private String content;

    /**
     * 发现问题
     */
    private String question;

    /**
     * 是否被行政处罚，例如是、否
     */
    @TableField("isPunish")
    private String isPunish;

    /**
     * 行政处罚类别
     */
    private String type;

    /**
     * 罚款金额
     */
    private Double money;

    /**
     * 是否落实整改
     */
    @TableField("isChange")
    private String isChange;

    /**
     * 外键
     */
    @TableField("enterpriseId")
    private Long enterpriseId;


}
