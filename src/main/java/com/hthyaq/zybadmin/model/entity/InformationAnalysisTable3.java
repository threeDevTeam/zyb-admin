package com.hthyaq.zybadmin.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InformationAnalysisTable3 {
    //存在职业病危害岗位数
    private int nameNum1;
    //设置职业病防护设施岗位数
    private int nameNum2;
    //职业病防护设施设置率
    private double nameNum2L;
    //配备个体防护用品岗位数
    private int  nameNum3;
    //个人防护用品配备率
    private double nameNum3L;
}
