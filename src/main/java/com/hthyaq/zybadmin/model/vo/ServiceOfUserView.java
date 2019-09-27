package com.hthyaq.zybadmin.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Data
@Accessors(chain = true)
public class ServiceOfUserView {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 登录名
     */
    @TableField("loginName")
    private String loginName;

    /**
     * 登录密码
     */
    @TableField("loginPassword")
    private String loginPassword;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户类型
     */
    private String type;

    /**
     * 外键
     */
    @TableField("companyId")
    private Long companyId;
    private String name;

    /**
     * code
     */
    private String code;

    @TableField("Cascader")
    private ArrayList cascader;

    /**
     * 注册地址
     */
    @TableField("registerAddress")
    private String registerAddress;

    /**
     * 机构类型,例如检测机构、体检机构、诊断机构
     */
    private String type2;

}
