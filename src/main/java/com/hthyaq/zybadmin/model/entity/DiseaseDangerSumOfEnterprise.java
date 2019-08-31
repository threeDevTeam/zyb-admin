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
 * 
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("diseaseDangerSumOfEnterprise")
public class DiseaseDangerSumOfEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 接触职业病危害总人数
     */
    private Integer total;

    /**
     * 接触粉尘人数
     */
    private Integer dust;

    /**
     * 接触化学因素人数
     */
    private Integer chemistry;

    /**
     * 接触物理因素人数
     */
    private Integer physical;

    /**
     * 接触放射性因素人数
     */
    private Integer radioactivity;

    /**
     * 接触生物因素人数
     */
    private Integer biology;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 月份
     */
    private Integer month;

    /**
     * 外键
     */
    @TableField("enterpriseId")
    private Long enterpriseId;


}
