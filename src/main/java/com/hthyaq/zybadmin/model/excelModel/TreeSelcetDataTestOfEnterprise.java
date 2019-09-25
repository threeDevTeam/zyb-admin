package com.hthyaq.zybadmin.model.excelModel;

import com.google.common.collect.Lists;
import lombok.Data;
import java.util.List;
@Data
public class TreeSelcetDataTestOfEnterprise {
    private String title;
    private String  value;
    private String  key;
    private List<TreeSelcetDataTestOfEnterprise> children = Lists.newArrayList();
}