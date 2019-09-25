package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.SickOfEnterprise;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SickOfEnterpriseView extends SickOfEnterprise {
    private String treeSelect;
}
