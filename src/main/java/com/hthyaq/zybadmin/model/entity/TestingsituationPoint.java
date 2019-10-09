package com.hthyaq.zybadmin.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TestingsituationPoint {
    private int Detectionpoints;
    private int Targetnumber;
    private double Compliancerate;
}
