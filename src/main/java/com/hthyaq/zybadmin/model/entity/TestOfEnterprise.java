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
 * 岗位职业健康检查信息
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("testOfEnterprise")
public class TestOfEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    @TableField("idNum")
    private String idNum;

    /**
     * 体检类别,例如上岗前、在岗期间、离岗时
     */
    private String type;

    /**
     * 体检结果，例如正常、异常、职业禁忌证、疑似职业病
     */
    private String result;

    /**
     * 处理意见
     */
    private String note;

    /**
     * 落实情况
     */
    private String implement;

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
