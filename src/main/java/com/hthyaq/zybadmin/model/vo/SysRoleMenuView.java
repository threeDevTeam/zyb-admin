package com.hthyaq.zybadmin.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Data
@Accessors(chain = true)
public class SysRoleMenuView {
    private ArrayList checkbox;
    private ArrayList checkbox2;
    private ArrayList checkbox3;
    private ArrayList checkbox4;
}
