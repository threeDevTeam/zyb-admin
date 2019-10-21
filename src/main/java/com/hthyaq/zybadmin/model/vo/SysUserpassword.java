package com.hthyaq.zybadmin.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SysUserpassword")
public class SysUserpassword {
    @TableField("loginPassword")
    private String loginPassword;
    @TableField("newPassword")
    private String newPassword;
}
