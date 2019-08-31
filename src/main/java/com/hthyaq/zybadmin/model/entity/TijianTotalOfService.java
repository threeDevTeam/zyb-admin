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
 * 体检机构的总体信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tijianTotalOfService")
public class TijianTotalOfService implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 体检报告数
     */
    private Integer count1;

    /**
     * 体检人数
     */
    private Integer count2;

    /**
     * 职业禁忌证人数
     */
    private Integer count3;

    /**
     * 疑似职业病人数
     */
    private Integer count4;

    /**
     * 外键
     */
    @TableField("tijianBasicId")
    private Long tijianBasicId;


}
