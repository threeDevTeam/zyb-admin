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
 * 诊断机构的总体信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("zhenduanTotalOfService")
public class ZhenduanTotalOfService implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 诊断人数
     */
    private Integer count1;

    /**
     * 诊断职业病企业数
     */
    private Integer count2;

    /**
     * 诊断报告数
     */
    private Integer count3;

    /**
     * 外键
     */
    @TableField("zhenduanBasicId")
    private Long zhenduanBasicId;


}
