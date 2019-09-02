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
 * 接害人员信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("touchPersonOfEnterprise")
public class TouchPersonOfEnterprise implements Serializable {

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
     * 性别，例如男、女
     */
    private String gender;

    /**
     * 出生日期，例如20190101
     */
    private Integer birth;

    /**
     * 上岗时间，例如20190101
     */
    @TableField("startDate")
    private Integer startDate;

    /**
     * 离岗时间，例如20190101
     */
    @TableField("leaveDate")
    private Integer leaveDate;

    /**
     * 接害工龄
     */
    @TableField("touchYear")
    private Integer touchYear;

    /**
     * 是否缴纳工伤保险，例如是、否
     */
    @TableField("isBuy")
    private String isBuy;

    /**
     * 是否签订劳动合同，例如是、否
     */
    @TableField("isSign")
    private String isSign;

    /**
     * 是否参加职业卫生培训 例如：是、否
     */
    @TableField("isPractice")
    private String isPractice;

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
