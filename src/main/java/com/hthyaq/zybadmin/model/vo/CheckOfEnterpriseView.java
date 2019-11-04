package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.CheckOfEnterprise;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class CheckOfEnterpriseView extends CheckOfEnterprise {
    private String checkDateStr;
}
