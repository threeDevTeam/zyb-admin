package com.hthyaq.zybadmin.model.entity;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;
@Data
public class TreeSelcetData {
    private String value;
    private String label;
    private List<TreeSelcetData> children = Lists.newArrayList();
}
