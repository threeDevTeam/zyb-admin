package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.MonitorOfEnterprise;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class MonitorOfEnterpriseView extends MonitorOfEnterprise {
    private String treeSelect;
}
