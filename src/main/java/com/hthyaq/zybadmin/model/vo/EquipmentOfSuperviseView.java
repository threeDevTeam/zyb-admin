package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.EquipmentOfSupervise;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EquipmentOfSuperviseView extends EquipmentOfSupervise {
    private String buyDateStr;
}
