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
 * 检测机构的具备报告的结果
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jianceDetailResultOfService")
public class JianceDetailResultOfService implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 检测结果  例如：0.99
     */
    @TableField("checkResult")
    private Double checkResult;

    /**
     * 类别 例如：CMAC、CTWA、CSTEL、超限倍数、其他
     */
    private String type;

    /**
     * 单位 例如：mg/m3、kV、...
     */
    private String unit;

    /**
     * 外键
     */
    @TableField("jianceBasicId")
    private Long jianceBasicId;

    /**
     * 外键
     */
    @TableField("jianceDetailId")
    private Long jianceDetailId;


}
