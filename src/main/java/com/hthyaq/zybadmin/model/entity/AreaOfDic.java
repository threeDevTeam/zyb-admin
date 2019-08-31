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
 * 省市县三级的行政区域数据
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("areaOfDic")
public class AreaOfDic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 行政区域代码
     */
    private Integer code;

    /**
     * 区域名称
     */
    private String name;

    /**
     * 级别，省市县/市区，province/city/district
     */
    private String level;

    /**
     * 有多少个直接子区域
     */
    private Integer childNum;

    /**
     * 上级节点的id
     */
    private Integer pid;

    /**
     * 地图需要的数据
     */
    @TableField("geoJson")
    private String geoJson;

    /**
     * 排序
     */
    private Double sort;


}
