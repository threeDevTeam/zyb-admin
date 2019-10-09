package com.hthyaq.zybadmin.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ServiceOfSuperviseStatistics2 implements Serializable {
    private int jianceCount;
    private int tijianCount;
    private int zhenduanCount;
    private int jiancePunishCount;
    private int tijianPunishCount;
    private int zhenduanPunishCount;
    private double jianceMoney;
    private double tijianMoney;
    private double zhenduanMoney;
    private int jianceCancelCount;
    private int tiijanCancelCount;
    private int zhenduanCancelCount;
}
