package com.hthyaq.zybadmin.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Data
@Accessors(chain = true)
public class SuperviseOfUserView {
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
    /**
     * 单位名称
     */
    private String companyName;

//    @TableField("provinceName")
////    private String provinceName;
////
////    /**
////     * 省的代码
////     */
////    @TableField("provinceCode")
////    private String provinceCode;
////
////    /**
////     * 市的名称
////     */
////    @TableField("cityName")
////    private String cityName;
////
////    /**
////     * 市的代码
////     */
////    @TableField("cityCode")
////    private String cityCode;
////
////    /**
////     * 区的名称
////     */
////    @TableField("districtName")
////    private String districtName;
////
////    /**
////     * 区的代码
////     */
////    @TableField("districtCode")
////    private String districtCode;
////
    @TableField("Cascader")
    private ArrayList cascader;
    /**
     * registerAddress
     */
    @TableField("registerAddress")
    private String registerAddress;


}