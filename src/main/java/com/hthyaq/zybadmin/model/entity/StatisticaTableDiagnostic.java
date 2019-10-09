package com.hthyaq.zybadmin.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StatisticaTableDiagnostic {
    private int count;
    private int doctorNum;
    private double projectCount;
    private int count1;
    private int idNum;
}
