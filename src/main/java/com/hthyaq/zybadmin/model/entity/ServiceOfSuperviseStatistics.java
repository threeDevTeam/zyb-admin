package com.hthyaq.zybadmin.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ServiceOfSuperviseStatistics implements Serializable {
    private int jianceIncrease1;
    private int jianceSum1;
    private int jianceIncrease2;
    private int jianceSum2;
    private int jianceIncrease3;
    private int jianceSum3;
    private int tijianIncrease;
    private int tijianSum;
    private int zhenduanIncrease;
    private int zhenduanSum;

}
