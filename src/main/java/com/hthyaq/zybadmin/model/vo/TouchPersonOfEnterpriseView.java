package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.TouchPersonOfEnterprise;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TouchPersonOfEnterpriseView extends TouchPersonOfEnterprise {
    private String treeSelect;
}
