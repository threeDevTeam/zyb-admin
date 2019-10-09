package com.hthyaq.zybadmin.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SuperviseStatistics implements Serializable {
    private int personCount;
    private int paperCount;
    private int questionCount;
    private int changefixCount;
    private int punishCount;
    private double punishMoney;
    private int stopCount;
    private int closeCount;

}
