package com.hthyaq.zybadmin.model.entity;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class TreeSelcetDataPostDangerOfEnterprise {
    private String title;
    private String  value;
    private String  key;
    private List<TreeSelcetDataPostDangerOfEnterprise> children = Lists.newArrayList();
}
