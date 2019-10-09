package com.hthyaq.zybadmin.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HealthyBuild implements Serializable {
    private int ruleIncrease;
    private int ruleSum;
    private int fileIncrease;
    private int fileSum;
    private int startdardIncrease;
    private int startdardSum;
}
