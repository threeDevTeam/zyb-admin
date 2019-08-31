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
 * 职业病病人信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sickOfEnterprise")
public class SickOfEnterprise implements Serializable {

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
     * 职业病大类名称
     */
    @TableField("sickBigName")
    private String sickBigName;

    /**
     * 职业病小类名称
     */
    @TableField("sickSmallName")
    private String sickSmallName;

    /**
     * 病人类别，例如新病例、首次晋期、再次晋期
     */
    private String type;

    /**
     * 诊断机构
     */
    private String org;

    /**
     * 诊断日期，例如20190101
     */
    @TableField("checkDate")
    private Integer checkDate;

    /**
     * 诊断年份，例如2019
     */
    @TableField("checkYear")
    private Integer checkYear;

    /**
     * 诊断月份，例如1
     */
    @TableField("checkMonth")
    private Integer checkMonth;

    /**
     * 发病工龄
     */
    @TableField("sickYear")
    private Integer sickYear;

    /**
     * 是否进行了职业病病人报告,例如是、否
     */
    @TableField("isReport")
    private String isReport;

    /**
     * 职业病损失工作日
     */
    @TableField("workDay")
    private Integer workDay;

    /**
     * 新增
     */
    private String increase;

    /**
     * 累计
     */
    private String total;

    /**
     * 转归情况，例如治愈中、康复、死亡
     */
    private String transform;

    /**
     * 死亡日期,例如20190101
     */
    @TableField("dieDate")
    private Integer dieDate;

    /**
     * 死亡年份,例如2019
     */
    @TableField("dieYear")
    private Integer dieYear;

    /**
     * 死亡月份,例如1
     */
    @TableField("dieMonth")
    private Integer dieMonth;

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
