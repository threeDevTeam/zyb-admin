package com.hthyaq.zybadmin.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Examinationresultsurface {
    //职业健康检查企业数
    private int enterpriseCode1;
    //体检报告数
    private int count1;
    //职业禁忌证人数
    private int idNum1;
    //疑似职业病人数
    private int idNum2;
    //检出疑似职业病企业数
    private int enterpriseCode2;

    private double zybEnterpriserate;
}
