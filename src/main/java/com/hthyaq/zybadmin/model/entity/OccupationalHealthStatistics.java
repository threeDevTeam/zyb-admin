package com.hthyaq.zybadmin.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OccupationalHealthStatistics implements Serializable {
    private int orgCount;
    private int paperCount;
    private int changeCount;
    private int pulishCount;
    private double punishMoney;
    private int stopCount;
    private int closeCount;
}
