package com.hthyaq.zybadmin.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AccidentStatistics  implements Serializable {
    private int dustCount;
    private int poisonCount;
    private int otherCount;
    private int dustPersonCount;
    private int poisonPersonCount;
    private int otherPersonCount;
    private String dustDieCount;
    private String poisonDieCount;
    private int otherDieCount;
    private double loseMoney;
}
