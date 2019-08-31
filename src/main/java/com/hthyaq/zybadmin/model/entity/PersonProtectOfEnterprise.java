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
 * 个体防护信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("personProtectOfEnterprise")
public class PersonProtectOfEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 是否配备个人防护用品，例如是、否
     */
    @TableField("isSet")
    private String isSet;

    /**
     * 个人防护用品名称
     */
    private String name;

    /**
     * 防护用品型号
     */
    @TableField("modelNum")
    private String modelNum;

    /**
     * 发放数量
     */
    private String count;

    /**
     * 发放周期
     */
    private Integer cycle;

    /**
     * 劳动者是否正确佩戴使用防护用品，例如是、否
     */
    @TableField("isCorrect")
    private String isCorrect;

    /**
     * 是否定期更换个人防护用品，例如是、否
     */
    @TableField("isReplace")
    private String isReplace;

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
