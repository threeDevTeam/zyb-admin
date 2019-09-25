package com.hthyaq.zybadmin.model.excelModel;
import lombok.Data;
import com.google.common.collect.Lists;

import java.util.List;
@Data
public class TreeSelcetDataAccidentPersonOfEnterprise {
    private String title;
    private String  value;
    private String  key;
    private List<TreeSelcetDataAccidentPersonOfEnterprise> children = Lists.newArrayList();

}
