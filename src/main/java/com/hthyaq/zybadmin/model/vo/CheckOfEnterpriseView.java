package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.CheckOfEnterprise;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CheckOfEnterpriseView extends CheckOfEnterprise {
    private String checkDateStr;
}
