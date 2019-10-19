package com.hthyaq.zybadmin.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author zhangqiang
 * @since 2019-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sysMenu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 上级节点的id
     */
    private Integer pid;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限的url
     */
    private String url;

    /**
     * 菜单的图标
     */
    private String icon;

    /**
     * 等级，方便通过等级字段查询出权限
     */
    private String level;

    /**
     * 排序
     */
    private Double sort;


}
