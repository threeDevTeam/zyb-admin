package com.hthyaq.zybadmin.model.excelModel;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;
/**
 * @author Administrator
 */
@Data
public class TreeSelcetDataEnterpriseCheckSumOfEnterprise {
    private String title;
    private String  value;
    private String  key;
    private List<TreeSelcetDataEnterpriseCheckSumOfEnterprise> children = Lists.newArrayList();

}
