package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.AlikeSickOfEnterprise;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AlikeSickOfEnterpriseView extends AlikeSickOfEnterprise {
    private String treeSelect;
}
