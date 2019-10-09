package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.ZhenduanBasicOfService;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class ZhenduanBasicOfServiceView extends ZhenduanBasicOfService {
    private ArrayList cascader;
    private ArrayList cascaded1;
}
