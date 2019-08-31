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
 * 职业健康监护信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("testSumOfEnterprise")
public class TestSumOfEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 体检年份
     */
    private Integer year;

    /**
     * 体检月份
     */
    private Integer month;

    /**
     * 应检人数
     */
    @TableField("shouldNum")
    private Integer shouldNum;

    /**
     * 实检人数
     */
    @TableField("realNum")
    private Integer realNum;

    /**
     * 体检率
     */
    @TableField("testRate")
    private Double testRate;

    /**
     * 职业禁忌证人数
     */
    @TableField("stopNum")
    private Integer stopNum;

    /**
     * 外键
     */
    @TableField("enterpriseId")
    private Long enterpriseId;


}
