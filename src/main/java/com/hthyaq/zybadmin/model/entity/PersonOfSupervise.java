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
 * 监管人员信息









 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("personOfSupervise")
public class PersonOfSupervise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别 例如：男、女
     */
    private String gender;

    /**
     * 身份证号
     */
    @TableField("idNum")
    private String idNum;

    /**
     * 出生日期 例如：20190101
     */
    private Long birth;

    /**
     * 职务
     */
    private String job;

    /**
     * 所学专业
     */
    private String major;

    /**
     * 是否取得执法资格证书 例如：是、否
     */
    @TableField("isGet")
    private String isGet;

    /**
     * 外键
     */
    @TableField("superviseId")
    private Long superviseId;


}
