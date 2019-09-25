package com.hthyaq.zybadmin.model.excelModel;

import com.google.common.collect.Lists;
import com.hthyaq.zybadmin.model.entity.TreeSelcetDataPersonOfEnterprise;
import lombok.Data;

import java.util.List;
@Data
public class TreeSelcetDataTouchPersonOfEnterprise {
    private String title;
    private String  value;
    private String  key;
    private List<TreeSelcetDataTouchPersonOfEnterprise> children = Lists.newArrayList();
}
