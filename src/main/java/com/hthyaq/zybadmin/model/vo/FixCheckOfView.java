package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.bean.Child3;
import com.hthyaq.zybadmin.model.entity.FixCheckOfEnterprise;
import com.hthyaq.zybadmin.model.entity.FixCheckResultOfEnterprise;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FixCheckOfView extends FixCheckOfEnterprise {
    private String treeSelect;
    private Child3<FixCheckResultOfEnterprise> course;
}
