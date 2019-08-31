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
 * 疑似职业病病人信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("alikeSickOfEnterprise")
public class AlikeSickOfEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    @TableField("idNum")
    private String idNum;

    /**
     * 检查机构
     */
    private String org;

    /**
     * 检查日期，例如20190101
     */
    @TableField("checkDate")
    private Integer checkDate;

    /**
     * 检查年份，例如2019
     */
    @TableField("checkYear")
    private Integer checkYear;

    /**
     * 检查月份，例如1
     */
    @TableField("checkMonth")
    private Integer checkMonth;

    /**
     * 发病工龄
     */
    @TableField("sickYear")
    private String sickYear;

    /**
     * 是否进行了疑似职业病病人报告，例如是、否
     */
    @TableField("isReport")
    private String isReport;

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
