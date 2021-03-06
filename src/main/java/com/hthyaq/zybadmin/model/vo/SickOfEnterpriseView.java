package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.SickOfEnterprise;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class SickOfEnterpriseView extends SickOfEnterprise {
    private String treeSelect;
    private ArrayList cascaded1;
    private String checkDateStr;
    private String   dieDateStr;
}
