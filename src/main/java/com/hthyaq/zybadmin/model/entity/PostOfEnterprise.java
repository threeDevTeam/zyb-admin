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
 * 岗位
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("postOfEnterprise")
public class PostOfEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 岗位的大类名称
     */
    @TableField("postBigName")
    private String postBigName;

    /**
     * 岗位的小类名称
     */
    @TableField("postSmallName")
    private String postSmallName;

    /**
     * 外键，例如关联-企业
     */
    @TableField("enterpriseId")
    private Long enterpriseId;

    /**
     * 外键，例如关联-工作场所
     */
    @TableField("workplaceId")
    private Long workplaceId;


}
