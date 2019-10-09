package com.hthyaq.zybadmin.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Diagnosticstatistics {
    //职业病诊断企业数
    private int count2;
    //诊断出职业病病人企业数
    private int enterpriseCode;
    //职业病诊断人数
    private int count1;
    //报告职业病人数
    private int idNum;

    private String Diagnosticrate;
}
