package com.hthyaq.zybadmin.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InformationAnalysisTable5 {
    //签订劳动合同人数
    private int nameNum1;
    //劳动合同签订率
    private double nameNum1l;
    //缴纳工伤保险人数
    private  int nameNum2;
    //工伤保险参保率
    private double nameNum2l;
}
