package com.hthyaq.zybadmin.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据表1
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Gangwei implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;

    /**
     * 父编号
     */
    @TableField("Pid")
    private Integer Pid;

    /**
     * 行业名
     */
    @TableField("industryName")
    private String industryName;

    /**
     * 岗位名
     */
    @TableField("postName")
    private String postName;

    /**
     * 顶级父节点
     */
    @TableField("topId")
    private Integer topId;

    /**
     * 门类
     */
    private Integer leiid;


}
