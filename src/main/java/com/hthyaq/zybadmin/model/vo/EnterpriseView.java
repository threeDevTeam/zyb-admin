package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.Enterprise;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
@Data
@Accessors(chain = true)
public class EnterpriseView extends Enterprise {
    private ArrayList cascader;
}
