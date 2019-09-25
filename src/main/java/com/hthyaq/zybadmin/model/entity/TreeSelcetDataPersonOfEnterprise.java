package com.hthyaq.zybadmin.model.entity;

import com.google.common.collect.Lists;
import com.hthyaq.zybadmin.model.entity.TreeSelcetData;
import lombok.Data;

import java.util.List;
@Data
public class TreeSelcetDataPersonOfEnterprise {
    private String title;
    private String  value;
    private String  key;
    private List<TreeSelcetDataPersonOfEnterprise> children = Lists.newArrayList();
}
