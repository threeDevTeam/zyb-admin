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
 * 检测机构的总体信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jianceTotalOfService")
public class JianceTotalOfService implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 职业病危害预评价报告数
     */
    private String count1;

    /**
     * 控制效果评价报告数
     */
    private String count2;

    /**
     * 现状评价报告数
     */
    private String count3;

    /**
     * 检测报告数
     */
    private String count4;

    /**
     * 检测点数
     */
    private String count5;

    /**
     * 达标点数
     */
    private String count6;

    /**
     * 外键
     */
    @TableField("jianceBasicId")
    private Long jianceBasicId;


}
