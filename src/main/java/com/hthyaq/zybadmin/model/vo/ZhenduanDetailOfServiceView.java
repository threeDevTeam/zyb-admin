package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.ZhenduanDetailOfService;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class ZhenduanDetailOfServiceView extends ZhenduanDetailOfService {
    private ArrayList cascader;
}
