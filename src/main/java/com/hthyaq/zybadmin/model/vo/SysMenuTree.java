package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.TreeSelcetData2;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@Data
@Accessors(chain = true)
public class SysMenuTree {
    private  String label;
    private long value;
    private List<TreeSelcetData2> children = Lists.newArrayList();
}
