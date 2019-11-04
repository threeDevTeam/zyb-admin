package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.AccidentPersonOfEnterprise;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class AccidentPersonOfEnterpriseView extends AccidentPersonOfEnterprise {
    private String treeSelect;
    private String dieDateStr;
}
