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
 * 防护设施信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("protectOfEnterprise")
public class ProtectOfEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 是否设置防护设施,例如是、否
     */
    @TableField("isSet")
    private String isSet;

    /**
     * 防护设施名称
     */
    private String name;

    /**
     * 防护设施类别,例如防护设施类别：防尘设施、防毒设施、防噪声设施、防高温设施、防辐射设施、其他
     */
    private String type;

    /**
     * 运行状态，例如正常、维修、故障、停用、报废、其他
     */
    private String status;

    /**
     * 是否定期进行维护检修保养，例如是、否
     */
    @TableField("isFix")
    private String isFix;

    /**
     * 工程防护效果 例如：良好、一般、差
     */
    @TableField("protectEffect")
    private String protectEffect;

    /**
     * 外键
     */
    @TableField("enterpriseId")
    private Long enterpriseId;

    /**
     * 外键
     */
    @TableField("workplaceId")
    private Long workplaceId;

    /**
     * 外键
     */
    @TableField("postId")
    private Long postId;

    /**
     * 外键
     */
    @TableField("postDangerId")
    private Long postDangerId;


}
