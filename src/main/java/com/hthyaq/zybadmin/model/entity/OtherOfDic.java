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
 * 用于企业、监管部门、技术服务机构的表单下拉项
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("otherOfDic")
public class OtherOfDic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 中文表名称
     */
    @TableField("chineseTableName")
    private String chineseTableName;

    /**
     * 英文表名称
     */
    @TableField("englishTableName")
    private String englishTableName;

    /**
     * 中文字段名称
     */
    @TableField("chineseColumnName")
    private String chineseColumnName;

    /**
     * 英文字段名称
     */
    @TableField("englishColumnName")
    private String englishColumnName;


}
