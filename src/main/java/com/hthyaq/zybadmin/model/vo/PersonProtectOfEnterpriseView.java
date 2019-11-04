package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.PersonProtectOfEnterprise;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class PersonProtectOfEnterpriseView extends PersonProtectOfEnterprise {
    private String treeSelect;
}
