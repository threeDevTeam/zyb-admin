package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.AlikeSickOfEnterprise;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class AlikeSickOfEnterpriseView extends AlikeSickOfEnterprise {
    private String treeSelect;
    private String checkDateStr;
}
