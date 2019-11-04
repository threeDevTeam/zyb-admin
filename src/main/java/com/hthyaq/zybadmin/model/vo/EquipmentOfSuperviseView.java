package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.EquipmentOfSupervise;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class EquipmentOfSuperviseView extends EquipmentOfSupervise {
    private String buyDateStr;
}
