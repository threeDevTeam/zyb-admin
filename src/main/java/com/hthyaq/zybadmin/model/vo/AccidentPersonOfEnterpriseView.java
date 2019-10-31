package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.AccidentPersonOfEnterprise;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AccidentPersonOfEnterpriseView extends AccidentPersonOfEnterprise {
    private String treeSelect;
    private String dieDateStr;
}
