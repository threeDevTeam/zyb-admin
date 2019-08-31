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
 * 统计时的基础信息表
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("baseOfDic")
public class BaseOfDic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 大类名称
     */
    @TableField("bigName")
    private String bigName;

    /**
     * 小类名称
     */
    @TableField("smallName")
    private String smallName;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 标志，例如危害因素、职业病名称、岗位、登记注册类型
     */
    private String flag;

    /**
     * 排序
     */
    private Double sort;


}
