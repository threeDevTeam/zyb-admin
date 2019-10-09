package com.hthyaq.zybadmin.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HealthStatistics {
    private int code;
    private int technologyCount;
    private int passCount;
    private double equipmentCount;
    private double projectCount;
    private int count123;
    private int count4;
    private int count5;
}
